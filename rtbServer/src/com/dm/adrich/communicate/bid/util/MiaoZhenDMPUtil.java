package com.dm.adrich.communicate.bid.util;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

// Referenced classes of package com.dm.adrich.communicate.bid.util:
//            SysParams, InterfaceConnentUtil

public class MiaoZhenDMPUtil {

    protected static final Logger log = Logger.getLogger(MiaoZhenDMPUtil.class);

    public static String[] getMZTagID() {
        String result[] = new String[2];
        try {
            StringBuffer sbPC = new StringBuffer();
            StringBuffer sbMB = new StringBuffer();
            String resultStr = InterfaceConnentUtil.sendURLAndGetJsonStr(SysParams.sysProps.getProperty("mz.tagIDURL"));
            if (resultStr != null && !"".equals(resultStr.trim())) {
                JSONParser parser = new JSONParser();
                JSONArray allObject = (JSONArray) parser.parse(resultStr);
                for (Iterator iterator = allObject.iterator(); iterator.hasNext(); ) {
                    Object obj = iterator.next();
                    Map tagMap = (Map) obj;
                    String tagName = (String) tagMap.get("name");
                    long tagID = ((Long) tagMap.get("tid")).longValue();
                    if (tagName.contains("_PC")) {
                        sbPC.append(tagID);
                        sbPC.append(",");
                    } else if (tagName.contains("_MB")) {
                        sbMB.append(tagID);
                        sbMB.append(",");
                    } else {
                        sbPC.append(tagID);
                        sbPC.append(",");
                        sbMB.append(tagID);
                        sbMB.append(",");
                    }
                }

                if (sbPC.length() > 0)
                    result[0] = sbPC.toString().substring(0, sbPC.length() - 1);
                if (sbMB.length() > 0)
                    result[1] = sbMB.toString().substring(0, sbMB.length() - 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
        return result;
    }

    public static List getUserTag(String tagIDStr, String userID, String token) {
        List result = new ArrayList();
        try {
            StringBuffer sb = new StringBuffer();
            sb.append(SysParams.sysProps.getProperty("mz.userTagURL"));
            sb.append(token);
            sb.append("&uuid=");
            sb.append(userID);
            sb.append("&tid=");
            sb.append(tagIDStr);
            log.info((new StringBuilder("mz ulr sb = ")).append(sb.toString()).toString());
            String resultStr = InterfaceConnentUtil.sendURLAndGetJsonStr(sb.toString());
            log.info((new StringBuilder("mz resultStr = ")).append(resultStr).toString());
            if (resultStr != null && !"".equals(resultStr.trim())) {
                JSONParser parser = new JSONParser();
                JSONObject allObject = (JSONObject) parser.parse(resultStr);
                if (allObject != null && allObject.size() > 0) {
                    String tagIDArr[] = tagIDStr.split(",");
                    String as[];
                    int j = (as = tagIDArr).length;
                    for (int i = 0; i < j; i++) {
                        String tagID = as[i];
                        Long value = (Long) allObject.get(tagID);
                        if (value != null && value.longValue() == 1L)
                            result.add(tagID);
                    }

                } else {
                    result.add("90000");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
        return result;
    }

    public static List getUserTagNo90000(String tagIDStr, String userID, String token) {
        List result = new ArrayList();
        try {
            StringBuffer sb = new StringBuffer();
            sb.append(SysParams.sysProps.getProperty("mz.userTagURL"));
            sb.append(token);
            sb.append("&uuid=");
            sb.append(userID);
            sb.append("&tid=");
            sb.append(tagIDStr);
            log.info((new StringBuilder("mz ulr sb = ")).append(sb.toString()).toString());
            String resultStr = InterfaceConnentUtil.sendURLAndGetJsonStr(sb.toString());
            log.info((new StringBuilder("mz resultStr = ")).append(resultStr).toString());
            if (resultStr != null && !"".equals(resultStr.trim())) {
                JSONParser parser = new JSONParser();
                JSONObject allObject = (JSONObject) parser.parse(resultStr);
                if (allObject != null && allObject.size() > 0) {
                    String tagIDArr[] = tagIDStr.split(",");
                    String as[];
                    int j = (as = tagIDArr).length;
                    for (int i = 0; i < j; i++) {
                        String tagID = as[i];
                        Long value = (Long) allObject.get(tagID);
                        if (value != null && value.longValue() == 1L)
                            result.add(tagID);
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
        return result;
    }

}