package com.dm.adrich.communicate.bid.util;

import org.apache.log4j.Logger;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

public class PDBTimeDealThread
        implements Runnable {
    public static final String pdbTimeFlag = "PDBTimeFlag";
    public static final String dealFlag = "#DealFlag";
    protected static final Logger log = Logger.getLogger(PDBTimeDealThread.class);
    private static final String requestFlag = "@reqNum";
    private static final String responseFlag = "@respNum";
    private static final String exposNum = "ExposNum";
    private static final String dealExposNumFlag = "#DealExposNum";
    private static final String dealReqNumFlag = "#DealReqNum";
    private static final String dealRespNumFlag = "#DealRespNum";
    public static long startTime = System.currentTimeMillis();
    public static long maxNumLimit = 50000000L;
    public static long pdbTimeLong = System.currentTimeMillis();

    public void run() {
        log.info("pdb初始化系统启动时间为startTime = " + startTime + "   pdbTimeLong = " + pdbTimeLong);
        do {
            try {
                String pdbTime = CacheUtil.INSTANCE.getEhcache("PDBTimeFlag");
                if (pdbTime != null && !"".equals(pdbTime))
                    pdbTimeLong = Long.parseLong(pdbTime);
                log.info("系统启动时间为startTime = " + startTime + "   pdbTimeLong = " + pdbTimeLong);
                long currentTime = System.currentTimeMillis();
                if (currentTime - startTime > pdbTimeLong) {
                    Set moveSet = new HashSet();
                    startTime = currentTime;
                    log.info((new StringBuilder("Constant.dealCountMap = ")).append(Constant.dealCountMap)
                            .append("   size = ").append(Constant.dealCountMap.size()).toString());
                    for (Iterator iterator = Constant.dealCountMap.entrySet().iterator(); iterator.hasNext(); ) {
                        java.util.Map.Entry entry = (java.util.Map.Entry) iterator.next();
                        String key = (String) entry.getKey();
                        if (key.endsWith("@reqNum")) {
                            String fillKey = key.split("@")[0];
                            String dealHkey = (new StringBuilder(String.valueOf(fillKey))).append("#DealFlag")
                                    .toString();
                            String fillRatio = CacheUtil.INSTANCE.getEhcache(dealHkey, "fillRatio");
                            String isExpose = CacheUtil.INSTANCE.getEhcache(dealHkey, "isExpose");
                            if (fillRatio != null && !"".equals(fillRatio)) {
                                float ratio = Float.parseFloat(fillRatio);
                                AtomicLong reqNum = (AtomicLong) entry.getValue();
                                AtomicLong respNum = (AtomicLong) Constant.dealCountMap.get(
                                        (new StringBuilder(String.valueOf(fillKey))).append("@respNum").toString());
                                if (respNum == null) {
                                    respNum = new AtomicLong(1L);
                                    Constant.dealCountMap.put(
                                            (new StringBuilder(String.valueOf(fillKey))).append("@respNum").toString(),
                                            respNum);
                                }
                                double cRatio = (double) reqNum.get() / (double) respNum.get();
                                if ("yes".equals(isExpose)) {
                                    long backNum = 0L;
                                    String reqNumStr = JedisUtil.INSTANCE.getJedis()
                                            .get((new StringBuilder(String.valueOf(dealHkey))).append("#DealReqNum")
                                                    .toString());
                                    String respNumStr = JedisUtil.INSTANCE.getJedis()
                                            .get((new StringBuilder(String.valueOf(dealHkey))).append("#DealRespNum")
                                                    .toString());
                                    if (reqNumStr != null && !"".equals(reqNumStr)) {
                                        if (respNumStr != null && !"".equals(respNumStr))
                                            backNum = Long.parseLong(reqNumStr) - Long.parseLong(respNumStr);
                                        else
                                            backNum = Long.parseLong(reqNumStr);
                                        if (backNum < 0L)
                                            backNum = 0L;
                                    }
                                    long dealExposNumInt = 1L;
                                    String dealExposNum = JedisUtil.INSTANCE.getJedis()
                                            .get((new StringBuilder(String.valueOf(dealHkey))).append("#DealExposNum")
                                                    .toString());
                                    if (dealExposNum != null && !"".equals(dealExposNum))
                                        dealExposNumInt = Long.parseLong(dealExposNum);
                                    cRatio = (double) (backNum + dealExposNumInt) / (double) dealExposNumInt;
                                }
                                if (cRatio > (double) ratio) {
                                    if (reqNum.get() > maxNumLimit) {
                                        reqNum.set(1L);
                                        respNum.set(1L);
                                    }
                                    Constant.fillMap.put(fillKey, Boolean.valueOf(true));
                                } else {
                                    Constant.fillMap.put(fillKey, Boolean.valueOf(false));
                                }
                            } else {
                                String endTimeStr = CacheUtil.INSTANCE.getEhcache(dealHkey, "endTime");
                                if (endTimeStr != null && !"".equals(endTimeStr)) {
                                    log.info((new StringBuilder("\u68C0\u67E5\u8BA1\u5212dealHkey = ")).append(fillKey)
                                            .append("   fillRatio = ").append(fillRatio).toString());
                                    log.info("检查计划dealHkey = " + fillKey + "   fillRatio = " + fillRatio);
                                } else {
                                    moveSet.add(fillKey);
                                    log.info((new StringBuilder("\u8BA1\u5212dealHkey = ")).append(fillKey)
                                            .append(" \u5C06\u8981\u88AB\u79FB\u9664 \uFF01\uFF01").toString());
                                    log.info("计划dealHkey = " + fillKey + " 将要被移除 ！！");
                                }
                            }
                        }
                    }

                    String moveKey;
                    for (Iterator iterator1 = moveSet.iterator(); iterator1.hasNext(); Constant.fillMap
                            .remove(moveKey)) {
                        moveKey = (String) iterator1.next();
                        Constant.dealCountMap
                                .remove((new StringBuilder(String.valueOf(moveKey))).append("@reqNum").toString());
                        Constant.dealCountMap
                                .remove((new StringBuilder(String.valueOf(moveKey))).append("@respNum").toString());
                    }

                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (true);
    }
}