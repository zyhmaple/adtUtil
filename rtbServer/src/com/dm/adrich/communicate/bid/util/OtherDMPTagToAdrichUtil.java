package com.dm.adrich.communicate.bid.util;

import org.apache.log4j.Logger;

import java.util.*;

public class OtherDMPTagToAdrichUtil {
    public static final String sspAndAdrichTagRel = "SSPAndAdrichTagRel";
    protected static final Logger log = Logger.getLogger(OtherDMPTagToAdrichUtil.class);
    private static final String[] taTags = {"sex", "age", "income", "education", "life", "occupation"};
    private static Map<String, Integer> tagValueMap = new HashMap();

    static {
        tagValueMap.put("1000010001", Integer.valueOf(1));
        tagValueMap.put("1000010002", Integer.valueOf(2));
        tagValueMap.put("1000020001", Integer.valueOf(4));
        tagValueMap.put("1000020002", Integer.valueOf(8));
        tagValueMap.put("1000020003", Integer.valueOf(16));
        tagValueMap.put("1000020004", Integer.valueOf(32));
        tagValueMap.put("1000020005", Integer.valueOf(64));
        tagValueMap.put("1000020006", Integer.valueOf(128));
        tagValueMap.put("1000020007", Integer.valueOf(256));
        tagValueMap.put("1000020009", Integer.valueOf(512));
        tagValueMap.put("1000030001", Integer.valueOf(1024));
        tagValueMap.put("1000030002", Integer.valueOf(2048));
        tagValueMap.put("1000030003", Integer.valueOf(4096));
        tagValueMap.put("1000030004", Integer.valueOf(8192));
        tagValueMap.put("1000030005", Integer.valueOf(16384));
        tagValueMap.put("1000040001", Integer.valueOf(32768));
        tagValueMap.put("1000040002", Integer.valueOf(65536));
        tagValueMap.put("1000040003", Integer.valueOf(131072));
        tagValueMap.put("1000040004", Integer.valueOf(262144));
        tagValueMap.put("1000040005", Integer.valueOf(524288));
        tagValueMap.put("1000050001", Integer.valueOf(1048576));
        tagValueMap.put("1000050002", Integer.valueOf(2097152));
        tagValueMap.put("1000050003", Integer.valueOf(4194304));
        tagValueMap.put("1000050004", Integer.valueOf(8388608));
        tagValueMap.put("1000050005", Integer.valueOf(16777216));
        tagValueMap.put("1000060001", Integer.valueOf(33554432));
        tagValueMap.put("1000060002", Integer.valueOf(67108864));
        tagValueMap.put("1000060004", Integer.valueOf(134217728));
        tagValueMap.put("1000060005", Integer.valueOf(268435456));
        tagValueMap.put("1000060006", Integer.valueOf(536870912));
        tagValueMap.put("1000060003", Integer.valueOf(1073741824));
    }

    public static int getTaValue(List baiduTagList, String dmpCode)
            throws Exception {
        log.info("baiduTagList= " + baiduTagList);
        Set<String> taSets = getUserAdrichTa(baiduTagList, dmpCode);
        log.info("taSets = " + taSets);
        int taValue = 0;
        if (!taSets.isEmpty()) {
            for (String ta : taSets) {
                if ((ta == null) || ("".equals(ta.trim())))
                    continue;
                log.info("ta = " + ta);
                Integer adrichTaValue = (Integer) tagValueMap.get(ta.trim());
                if (adrichTaValue != null) {
                    taValue |= adrichTaValue.intValue();
                } else {
                    log.info("wwwwwwwwwwwwwwwwbad ta = " + ta);
                }

            }

        }

        return taValue;
    }

    private static Set<String> getUserAdrichTa(List baiduTagList, String dmpCode)
            throws Exception {
        Set resSet = new HashSet();
        Map taMap = new HashMap();

        if ((baiduTagList != null) && (!baiduTagList.isEmpty())) {
            for (int i = 0; i < baiduTagList.size(); i++) {
                String tagStr = baiduTagList.get(i).toString();
                List adrichTagList = (List) CacheUtil.INSTANCE.getEhcacheList("SSPAndAdrichTagRel", tagStr + "_" + dmpCode);
                log.info("adrich adrichTagList = " + adrichTagList);
                if ((adrichTagList == null) || (adrichTagList.size() <= 0))
                    continue;
                Map map = otherToAdrichDMP(new HashSet(adrichTagList));
                taMix(taMap, map);
            }

        }
        String as[];
        int k = (as = taTags).length;
        for (int j = 0; j < k; j++) {
            String taTag = as[j];
            Set taSet = (Set) taMap.get(taTag);
            if (taSet != null && !taSet.isEmpty())
                resSet.addAll(taSet);
        }

        return resSet;
    }

    private static Map<String, Set<String>> otherToAdrichDMP(Set<String> set) {
        Map taMap = new HashMap();
        for (String ta : set) {
            if ((ta == null) || ("".equals(ta.trim())))
                continue;
            int taInt = Integer.parseInt(ta);
            if (taInt < 1000020000) {
                Set sexSet = new HashSet();
                sexSet.add(ta);
                taMap.put("sex", sexSet);
            } else if ((1000020000 < taInt) && (taInt < 1000030000)) {
                Set taSet = (Set) taMap.get("age");
                if (taSet == null) {
                    taSet = new HashSet();
                }
                taSet.add(ta);
                taMap.put("age", taSet);
            } else if ((1000030000 < taInt) && (taInt < 1000040000)) {
                Set taSet = (Set) taMap.get("income");
                if (taSet == null) {
                    taSet = new HashSet();
                }
                taSet.add(ta);
                taMap.put("income", taSet);
            } else if ((1000040000 < taInt) && (taInt < 1000050000)) {
                Set taSet = (Set) taMap.get("education");
                if (taSet == null) {
                    taSet = new HashSet();
                }
                taSet.add(ta);
                taMap.put("education", taSet);
            } else if ((1000050000 < taInt) && (taInt < 1000060000)) {
                Set taSet = (Set) taMap.get("life");
                if (taSet == null) {
                    taSet = new HashSet();
                }
                taSet.add(ta);
                taMap.put("life", taSet);
            } else {
                if ((1000060000 >= taInt) || (taInt >= 1000070000))
                    continue;
                Set taSet = (Set) taMap.get("occupation");
                if (taSet == null) {
                    taSet = new HashSet();
                }
                taSet.add(ta);
                taMap.put("occupation", taSet);
            }
        }

        return taMap;
    }

    private static void taMix(Map<String, Set<String>> oldMap, Map<String, Set<String>> newMap) {
        for (String taTag : taTags) {
            Set oldSet = (Set) oldMap.get(taTag);
            Set newSet = (Set) newMap.get(taTag);
            if ((oldSet != null) && (!oldSet.isEmpty())) {
                if ((newSet == null) || (newSet.isEmpty()))
                    continue;
                oldSet.retainAll(newSet);
                oldMap.put(taTag, oldSet);
            } else {
                if ((newSet == null) || (newSet.isEmpty()))
                    continue;
                oldMap.put(taTag, newSet);
            }
        }
    }
}