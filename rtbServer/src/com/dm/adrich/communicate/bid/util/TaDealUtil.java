package com.dm.adrich.communicate.bid.util;

import org.apache.log4j.Logger;

import java.util.*;

public class TaDealUtil {

    public static final String isPC = "IsPCMark";
    public static final String isAPP = "IsAPPMark";
    public static final String isOTT = "IsOTTMark";
    public static final int checkCodeArr[] = {1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 4, 4, 4, 4, 4, 8, 8, 8, 8, 8, 16, 16, 16,
            16, 16, 32, 32, 32, 32, 32, 32, 0};
    protected static final Logger log = Logger.getLogger(TaDealUtil.class);
    public static Map<String, String> dmpMap = new HashMap<String, String>();

    static {

        //初始化map，key是dmpcode#设备类型，value是dmp项目名称
        dmpMap.put("001#pc", "bdmbdmp");
        dmpMap.put("001#mb", "bdmbdmp");
        dmpMap.put("002#mb", "mzmbdmp");//miaozhen
        dmpMap.put("002#pc", "mzpcdmp");
        dmpMap.put("003#mb", "admmbdmp");//admaster
        dmpMap.put("003#pc", "admpcdmp");
        dmpMap.put("005#mb", "tdmbdmp");//talkingdata
        dmpMap.put("005#pc", "tdpcdmp");
        dmpMap.put("006#mb", "jdmbdmp");//jingdong
        dmpMap.put("006#pc", "jdpcdmp");
    }

    public static int dmpTaCheck(String dmpCodeArr, String isPC, String userID, String orderHashKey, String sspCode)
            throws Exception {
        if (dmpCodeArr != null && !"".equals(dmpCodeArr)) {
            String pcFlag = "#pc";
            if ("IsAPPMark".equals(isPC))
                pcFlag = "#mb";
            else
                userID = (new StringBuilder(String.valueOf(userID))).append(sspCode).toString();
            String dmpCodes[] = dmpCodeArr.split(";");//多个dmp；
            int unTas[] = new int[dmpCodes.length];
            for (int i = 0; i < dmpCodes.length; i++) {
                String projectID = (String) dmpMap
                        .get((new StringBuilder(String.valueOf(dmpCodes[i]))).append(pcFlag).toString());
                //项目id#大写userid
                //向dmp发送信息
                String taResult = NettyUtil.sendDMPData((new StringBuilder(String.valueOf(projectID))).append("#")
                        .append(userID.toUpperCase()).toString());
                log.info((new StringBuilder("dmp收到的返回结果 result=")).append(taResult)
                        .toString());

                //有结果
                if (taResult != null && !"e400".equals(taResult) && !"".equals(taResult)) {
                    String taValue = CacheUtil.INSTANCE.getEhcache(orderHashKey, "taValue");
                    String taCheckCode = CacheUtil.INSTANCE.getEhcache(orderHashKey, "taCheckCode");
                    log.info((new StringBuilder("dmpta=")).append(taResult).append("  orderta=").append(taValue)
                            .append("  taCheckCode=").append(taCheckCode).toString());
                    int checkR = dealTa(taResult, Integer.parseInt(taValue), Integer.parseInt(taCheckCode));
                    if (checkR == 3)
                        unTas[i] = 3;
                    else if (checkR == 1)
                        return 1;
                }
            }

            boolean isUNTa = true;
            int ai[];
            int k = (ai = unTas).length;
            for (int j = 0; j < k; j++) {
                int unTa = ai[j];
                if (unTa != 3)
                    isUNTa = false;
            }

            return !isUNTa ? 2 : 3;
        } else {
            return 4;
        }
    }

    /**
     * dmpTacheck
     *
     * @param dmpCode
     * @param taTagList
     * @param orderHashKey
     * @return
     * @throws Exception
     */
    public static int dmpTaCheck(String dmpCode, List taTagList, String orderHashKey) throws Exception {
        if (dmpCode != null && !"".equals(dmpCode)) {
            int dmpTaValue = OtherDMPTagToAdrichUtil.getTaValue(taTagList, dmpCode);
            String taValue = CacheUtil.INSTANCE.getEhcache(orderHashKey, "taValue");
            String taCheckCode = CacheUtil.INSTANCE.getEhcache(orderHashKey, "taCheckCode");
            log.info((new StringBuilder("dmpta=")).append(dmpTaValue).append("  orderta=").append(taValue)
                    .append("  taCheckCode=").append(taCheckCode).toString());
            int checkR = dealTa((new StringBuilder(String.valueOf(dmpTaValue))).toString(), Integer.parseInt(taValue),
                    Integer.parseInt(taCheckCode));
            return checkR;
        } else {
            return 4;
        }
    }

    /**
     * 根据dmp返回的结果，进行约定检查
     *
     * @param result
     * @param tagValue
     * @param taCheckCode
     * @return
     */
    private static int dealTa(String result, int tagValue, int taCheckCode) {
        if (result != null && !"e400".equals(result) && !"".equals(result)) {
            if ("0".equals(result))
                return 3;
            int dmpTa = Integer.parseInt(result);
            int resTa = dmpTa & tagValue;
            log.info((new StringBuilder("resTa = ")).append(resTa).toString());
            int checkTa[] = dealStrToIntArr(resTa);
            int checkCode = 0;
            Set checkSet = new HashSet();
            for (int i = 0; i < 32; i++)
                if (checkTa[i] > 0)
                    checkCode |= checkCodeArr[i];

            return taCheckCode != checkCode ? 2 : 1;
        } else {
            return 4;
        }
    }

    /**
     * 将tavalue反转成数组返回
     * @param taValue
     * @return
     */
    private static int[] dealStrToIntArr(int taValue) {
        String intStr = Integer.toBinaryString(taValue);
        String test[] = intStr.split("");
        int res[] = new int[32];
        int allLength = test.length;
        System.out.println((new StringBuilder("allLength = ")).append(allLength).toString());
        for (int i = 0; i < allLength; i++)
            if (test[i] != null && !"".equals(test[i]))
                res[allLength - 1 - i] = Integer.parseInt(test[i]);

        return res;
    }


}
