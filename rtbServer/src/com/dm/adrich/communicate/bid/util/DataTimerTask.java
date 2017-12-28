/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  com.drunkmoon.xinhuanet.redis.XinHuaNormalJedis
 *  org.apache.log4j.Logger
 */
package com.dm.adrich.communicate.bid.util;

import com.dm.adrich.communicate.bid.util.CacheUtil;
import com.dm.adrich.communicate.bid.util.JedisUtil;
import com.drunkmoon.xinhuanet.redis.XinHuaNormalJedis;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimerTask;
import org.apache.log4j.Logger;

public class DataTimerTask
extends TimerTask {
    public static final String dataUpdateTimeFlag = "dataUpdateTimeFlag";
    public static final String validOrderSelectTermList = "validOrderSelectTermList";
    public static final String valueTag = "#valueTag";
    public static final String keyTag = "#keyTag";
    public static final String userOrderTags = "UserOrderTags";
    public static final String msTag = "MsTags";
    public static final String creativeTag = "CreativeTag";
    public static final String bdNameListHashKey = "bdNameListHashKey";
    public static final String wdNameListHashKey = "wdNameListHashKey";
    public static final String mzTagIDPCKey = "MZTagIDPCKey";
    public static final String mzTagIDMBKey = "MZTagIDMBKey";
    public static final String sspAndAdrichTagRel = "SSPAndAdrichTagRel";
    public static final String investSSPCode = "InvestSSPCode";
    protected static final Logger log = Logger.getLogger(DataTimerTask.class);
    private Map<String, Object> newMap = null;

    @Override
    public void run() {
        log.warn((Object)("\u5f00\u59cb\u6267\u884c\uff0c\u6267\u884c\u65f6\u95f4\u4e3a time = " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())));
        try {
            String dealTime = JedisUtil.INSTANCE.getJedis().get("dataUpdateTimeFlag");
            if (dealTime != null && !"".equals(dealTime)) {
                if (!CacheUtil.INSTANCE.getDealTime().equals(dealTime)) {
                    this.newMap = new HashMap<String, Object>();
                    Set<String> termList = JedisUtil.INSTANCE.getJedis().smembers("validOrderSelectTermList");
                    log.info((Object)("termList.size() = " + termList.size()));
                    for (String term : termList) {
                        if (term == null || "".equals(term)) continue;
                        log.info((Object)("term = " + term));
                        String[] termArr = term.split("#@#");
                        log.info((Object)("termArr = " + termArr));
                        List planIDList = JedisUtil.INSTANCE.getJedis().hvals(String.valueOf(termArr[0]) + termArr[1]);
                        this.dealPlanDate(planIDList);
                        String termHashKey = String.valueOf(termArr[0]) + termArr[1] + "#valueTag";
                        if (this.newMap.get(termHashKey) != null) {
                            List oldPlanIDList = (List)this.newMap.get(termHashKey);
                            oldPlanIDList.addAll(planIDList);
                            this.newMap.put(termHashKey, this.dealList(oldPlanIDList));
                        } else {
                            this.newMap.put(termHashKey, planIDList);
                        }
                        String termValue = JedisUtil.INSTANCE.getJedis().get(termArr[0]);
                        this.newMap.put(termArr[0], termValue);
                    }
                    this.newMap.put("MZTagIDPCKey", JedisUtil.INSTANCE.getJedis().get("MZTagIDPCKey"));
                    this.newMap.put("MZTagIDMBKey", JedisUtil.INSTANCE.getJedis().get("MZTagIDMBKey"));
                    this.dealSSPTagIDAndAdrichIDRel();
                    Map<String, Object> oldMap = CacheUtil.INSTANCE.getCache();
                    CacheUtil.INSTANCE.setCache(this.newMap);
                    CacheUtil.INSTANCE.setDealTime(dealTime);
                    this.printlnAll();
                    if (oldMap != null) {
                        oldMap.clear();
                        oldMap = null;
                    }
                } else {
                    String hashkey = "NoLimitPlan#banner#IsAPPMark#_0040";
                    List planIDList = (List)CacheUtil.INSTANCE.getEhcacheList(hashkey, "value");
                    log.info((Object)("33333333333333333aPriorityPlanIDHashKey planIDList = " + planIDList));
                }
            }
        }
        catch (Exception e) {
            log.error((Object)("DataTimerTask = " + e.getMessage()), (Throwable)e);
            e.printStackTrace();
        }
    }

    public void dealPlanDate(List<String> planIDList) throws Exception {
        if (planIDList != null && planIDList.size() > 0) {
            HashSet<String> orderIDSet = new HashSet<String>();
            HashSet<String> bwNameListSet = new HashSet<String>();
            HashSet<String> investIDSet = new HashSet<String>();
            for (String planID : planIDList) {
                Map<String, String> planIDMap = JedisUtil.INSTANCE.getJedis().hgetAll(planID);
                for (Map.Entry entry : planIDMap.entrySet()) {
                    String key = (String)entry.getKey();
                    String value = (String)entry.getValue();
                    if (key == null || value == null) continue;
                    if ("orderID".equals(key)) {
                        orderIDSet.add(String.valueOf(value) + "UserOrderTags");
                    }
                    if ("investID".equals(key)) {
                        investIDSet.add(String.valueOf(value) + "InvestSSPCode");
                    }
                    if ("bdNameListHashKey".equals(key)) {
                        bwNameListSet.add(value);
                    }
                    if ("wdNameListHashKey".equals(key)) {
                        bwNameListSet.add(value);
                    }
                    if (key.endsWith("MsTags") && "yes".equals(value)) {
                        this.dealMaDate(key);
                    }
                    key = String.valueOf(planID) + "#" + key;
                    this.newMap.put(key, value);
                }
            }
            this.dealOrderDate(orderIDSet);
            this.dealBWNameListDate(bwNameListSet);
            this.dealInvestSSPCodeDate(investIDSet);
        }
    }

    public void dealInvestSSPCodeDate(Set<String> investIDSet) throws Exception {
        log.info((Object)("deal invest sspList = " + investIDSet));
        if (investIDSet != null && investIDSet.size() > 0) {
            for (String bwHashKey : investIDSet) {
                if (this.newMap.get(bwHashKey) != null) continue;
                Set sspCodeList = JedisUtil.INSTANCE.getJedis().smembers(bwHashKey);
                this.newMap.put(String.valueOf(bwHashKey) + "#keyTag", sspCodeList);
            }
        }
    }

    public void dealOrderDate(Set<String> bwNameListSet) throws Exception {
        log.info((Object)("deal order orderList = " + bwNameListSet));
        if (bwNameListSet != null && bwNameListSet.size() > 0) {
            for (String bwHashKey : bwNameListSet) {
                Map<String, String> planIDMap = JedisUtil.INSTANCE.getJedis().hgetAll(bwHashKey);
                for (Map.Entry entry : planIDMap.entrySet()) {
                    String key = String.valueOf(bwHashKey) + "#" + (String)entry.getKey();
                    String value = (String)entry.getValue();
                    this.newMap.put(key, value);
                }
            }
        }
    }

    public void dealBWNameListDate(Set<String> orderIDSet) throws Exception {
        if (orderIDSet != null && orderIDSet.size() > 0) {
            for (String orderHashKey : orderIDSet) {
                Map<String, String> planIDMap = JedisUtil.INSTANCE.getJedis().hgetAll(orderHashKey);
                for (Map.Entry entry : planIDMap.entrySet()) {
                    String key = String.valueOf(orderHashKey) + "#" + (String)entry.getKey();
                    String value = (String)entry.getValue();
                    this.newMap.put(key, value);
                }
            }
        }
    }

    public void dealMaDate(String msKey) throws Exception {
        String planMsHashKey = msKey;
        Map<String, String> planMsHashMap = JedisUtil.INSTANCE.getJedis().hgetAll(planMsHashKey);
        String hasWeight = (String)planMsHashMap.get("hasWeight");
        if ("yes".equals(hasWeight)) {
            if (this.newMap.get(String.valueOf(planMsHashKey) + "#keyTag") != null) {
                Set oldSet = (Set)this.newMap.get(String.valueOf(planMsHashKey) + "#keyTag");
                oldSet.addAll(planMsHashMap.keySet());
                this.newMap.put(String.valueOf(planMsHashKey) + "#keyTag", oldSet);
            } else {
                this.newMap.put(String.valueOf(planMsHashKey) + "#keyTag", planMsHashMap.keySet());
            }
        } else if (this.newMap.get(String.valueOf(planMsHashKey) + "#valueTag") != null) {
            List oldList = (List)this.newMap.get(String.valueOf(planMsHashKey) + "#valueTag");
            oldList.addAll(new ArrayList(planMsHashMap.values()));
            this.newMap.put(String.valueOf(planMsHashKey) + "#valueTag", this.dealList(oldList));
        } else {
            this.newMap.put(String.valueOf(planMsHashKey) + "#valueTag", new ArrayList(planMsHashMap.values()));
        }
        for (Map.Entry entry : planMsHashMap.entrySet()) {
            String key = String.valueOf(planMsHashKey) + "#" + (String)entry.getKey();
            String value = (String)entry.getValue();
            this.newMap.put(key, value);
        }
        this.dealCreativeType(new ArrayList<String>(planMsHashMap.values()));
    }

    public void dealCreativeType(List<String> createIDList) throws Exception {
        for (String createID : createIDList) {
            String planCreativeHashKey = String.valueOf(createID) + "CreativeTag";
            Map<String, String> planCreativeHashMap = JedisUtil.INSTANCE.getJedis().hgetAll(planCreativeHashKey);
            for (Map.Entry entry : planCreativeHashMap.entrySet()) {
                String key = String.valueOf(planCreativeHashKey) + "#" + (String)entry.getKey();
                String value = (String)entry.getValue();
                this.newMap.put(key, value);
            }
        }
    }

    public void printlnAll() throws Exception {
        for (Map.Entry<String, Object> entry : this.newMap.entrySet()) {
            log.warn((Object)("key= " + entry.getKey() + " and value= " + entry.getValue()));
        }
        log.warn((Object)("newMap.size()= " + this.newMap.size()));
    }

    public List<String> dealList(List<String> list) {
        HashSet<String> listSet = new HashSet<String>(list);
        return new ArrayList<String>(listSet);
    }

    public void dealSSPTagIDAndAdrichIDRel() throws Exception {
        Map<String, String> stIDAndAdrichTIDMap = JedisUtil.INSTANCE.getJedis().hgetAll("SSPAndAdrichTagRel");
        for (Map.Entry entry : stIDAndAdrichTIDMap.entrySet()) {
            String key = "SSPAndAdrichTagRel#" + (String)entry.getKey();
            String value = (String)entry.getValue();
            if (value == null || "".equals(value)) continue;
            String[] userValueArr = value.split(",");
            ArrayList<String> list = new ArrayList<String>();
            String[] arrstring = userValueArr;
            int n = arrstring.length;
            int n2 = 0;
            while (n2 < n) {
                String userValue = arrstring[n2];
                list.add(userValue);
                ++n2;
            }
            this.newMap.put(key, list);
        }
    }
}

