package com.dm.adrich.communicate.bid.util;

import org.apache.log4j.Logger;

import java.util.concurrent.atomic.AtomicLong;

public class PDBDealUtil {
    protected static final Logger log;
    private static final String requestFlag = "@reqNum";
    private static final String responseFlag = "@respNum";

    static {
        log = Logger.getLogger(PDBDealUtil.class);
    }

    public static boolean ifFill(String dealID, String ratio, String sspCode, String isExpose, long exposNumInt, long backNumInt) throws Exception {
        String fillKey = dealID + sspCode;

        Boolean isFill = (Boolean) Constant.fillMap.get(fillKey);

        log.info("dealID =  ：" + dealID + " 是isFill=" + isFill);
        if ((isFill != null) && (isFill.booleanValue())) {
            boolean cResult = compareRatio(fillKey, Float.parseFloat(ratio), isExpose, exposNumInt, backNumInt);

            if (cResult) {
                addCounter(fillKey + "@respNum", 1L);
            }
            return cResult;
        }

        return false;
    }

    public static boolean compareRatio(String fillKey, float ratio, String isExpose, long exposNumInt, long backNum) {
        AtomicLong reqNum = (AtomicLong) Constant.dealCountMap.get(fillKey + "@reqNum");
        AtomicLong respNum = (AtomicLong) Constant.dealCountMap.get(fillKey + "@respNum");
        log.info("reqNum = " + reqNum.get() + " respNum = " + respNum.get() + "  ratio = " + ratio);
        double cRatio = reqNum.get() / respNum.get();
        if ("yes".equals(isExpose)) {
            cRatio = (backNum + exposNumInt) / exposNumInt;
        }

        if (cRatio > ratio) {
            return true;
        }

        Constant.fillMap.put(fillKey, Boolean.valueOf(false));
        return false;
    }

    private static void addCounter(String key, long count) {
        if (count <= 0L) return;
        AtomicLong current = (AtomicLong) Constant.dealCountMap.get(key);
        if (current == null) {
            AtomicLong al = new AtomicLong();
            current = (AtomicLong) Constant.dealCountMap.putIfAbsent(key, al);
            if (current == null) current = al;
        }

        assert (current != null);
        current.addAndGet(count);
    }

    public static void addRequestNum(String dealID, String sspCode) {
        addCounter(dealID + sspCode + "@reqNum", 1L);
    }

    public static void addResponsetNum(String ifPriceAndNum, int buyWay, String planID, String dealID, String sspCode) {
        if ((ifPriceAndNum != null) && ("yes".endsWith(ifPriceAndNum))) {
            if ((buyWay == 4) || (buyWay == 6)) {
                if (buyWay == 6) {
                    String[] ids = dealID.split("#");
                    dealID = ids[1];
                }
                addCounter(planID + "#" + dealID + sspCode + "@respNum", 1L);
            }
        }
    }
}