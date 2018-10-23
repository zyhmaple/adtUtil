package com.dm.adrich.communicate.bid.util;

import com.dm.adrich.communicate.bid.model.ResultFromRedisModel;
import com.drunkmoon.xinhuanet.redis.XinHuaNormalJedis;
import com.drunkmoon.xinhuanet.redis.util.RedisUtil;
import org.apache.log4j.Logger;

import java.util.Set;

public enum JedisUtil {
    INSTANCE;

    protected static final Logger log;
    private static final String noLimitOrder = "NoLimitPlan";

    static {
        log = Logger.getLogger(IPLoadUtil.class);
    }

    private XinHuaNormalJedis xhJedis = null;
    private XinHuaNormalJedis xhWriteJedis = null;
    private XinHuaNormalJedis xhInvestJedis = null;

    public XinHuaNormalJedis getJedis() {
        return getWriteJedis();
    }

    public XinHuaNormalJedis getWriteJedis() {
        if (this.xhWriteJedis != null) {
            return this.xhWriteJedis;
        }

        try {
            this.xhWriteJedis = RedisUtil.getMoreMasterNoSlaveJedis(SysParams.sysProps.getProperty("redis.groupWriteName"));
        } catch (Exception e) {
            log.error("getWriteJedis = " + e.getMessage(), e);

            e.printStackTrace();
        }

        System.out.println("redis get object22222222222222222!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + SysParams.sysProps.getProperty("redis.groupWriteName"));

        return this.xhWriteJedis;
    }

    public String getEhcache(String key)
            throws Exception {
        String value = (String) EhcacheUtil.INSTANCE.get(key);
        if (value != null) {
            return value;
        }

        value = getJedis().get(key);
        EhcacheUtil.INSTANCE.put(key, value);
        return value;
    }

    public String getEhcache(String hashKey, String key) throws Exception {
        String value = (String) EhcacheUtil.INSTANCE.get(hashKey + "#" + key);
        if (value != null) {
            return value;
        }

        value = getJedis().hget(hashKey.trim(), key.trim());

        EhcacheUtil.INSTANCE.put(hashKey + "#" + key, value);
        return value;
    }

    public void delEhcache(String key) {
        EhcacheUtil.INSTANCE.remove(key);
    }

    public ResultFromRedisModel getPlanFromRedis(String cityName, String nativeID, String isBananer, int level, String isApp, String sspCode) {
        try {
            log.info("22222222222222221111111 isApp=" + cityName + "#" + nativeID + "#" + isBananer + "#" + isApp + "#" + sspCode);

            String orderListStr = CacheUtil.INSTANCE.getEhcache(cityName + "#" + nativeID + "#" + isBananer + "#" + isApp + "#" + sspCode);
            if ((orderListStr != null) && (!"".equals(orderListStr.trim())) && (level < 2)) {
                ResultFromRedisModel rfrm = new ResultFromRedisModel();
                rfrm.setLevel(1);
                rfrm.setPlanIDHashKey(cityName + "#" + nativeID + "#" + isBananer + "#" + isApp + "#" + sspCode);
                rfrm.setPriNum(Integer.parseInt(orderListStr));

                return rfrm;
            }

            orderListStr = CacheUtil.INSTANCE.getEhcache("NoLimitPlan#" + nativeID + "#" + isBananer + "#" + isApp + "#" + sspCode);
            log.info("22222222222222221111111 isApp=" + orderListStr);
            if ((orderListStr != null) && (!"".equals(orderListStr.trim())) && (level < 3)) {
                ResultFromRedisModel rfrm = new ResultFromRedisModel();
                rfrm.setLevel(2);
                rfrm.setPlanIDHashKey("NoLimitPlan#" + nativeID + "#" + isBananer + "#" + isApp + "#" + sspCode);
                rfrm.setPriNum(Integer.parseInt(orderListStr));
                return rfrm;
            }

        } catch (Exception e) {
            log.error("getPlanFromRedis = " + e.getMessage(), e);
            e.printStackTrace();
        }
        return null;
    }

    public ResultFromRedisModel getAfterInvestPlanFromRedis(String userID, String cityName, String impAdID, String isBananer, int level, String isApp, String sspCode) {
        try {
            Set planIDList = INSTANCE.getJedis().hkeys(userID + "#" + cityName + "#" + impAdID + "#" + isApp + "#" + sspCode);

            if ((planIDList != null) && (planIDList.size() > 0) && (level < 2)) {
                ResultFromRedisModel rfrm = new ResultFromRedisModel();
                rfrm.setLevel(1);
                rfrm.setPlanIDHashKey(userID + "#" + cityName + "#" + impAdID + "#" + isApp + "#" + sspCode);
                rfrm.setInvestIDSet(planIDList);
                return rfrm;
            }

            planIDList = INSTANCE.getJedis().hkeys(userID + "#" + cityName + "#" + isBananer + "#" + isApp + "#" + sspCode);
            if ((planIDList != null) && (planIDList.size() > 0) && (level < 3)) {
                ResultFromRedisModel rfrm = new ResultFromRedisModel();
                rfrm.setLevel(2);
                rfrm.setPlanIDHashKey(userID + "#" + cityName + "#" + isBananer + "#" + isApp + "#" + sspCode);
                rfrm.setInvestIDSet(planIDList);
                return rfrm;
            }

            planIDList = INSTANCE.getJedis().hkeys(userID + "#" + impAdID + "#" + isApp + "#" + sspCode);
            if ((planIDList != null) && (planIDList.size() > 0) && (level < 4)) {
                ResultFromRedisModel rfrm = new ResultFromRedisModel();
                rfrm.setLevel(3);
                rfrm.setPlanIDHashKey(userID + "#" + impAdID + "#" + isApp + "#" + sspCode);
                rfrm.setInvestIDSet(planIDList);
                return rfrm;
            }

            planIDList = INSTANCE.getJedis().hkeys(userID + "#" + isBananer + "#" + isApp + "#" + sspCode);
            if ((planIDList != null) && (planIDList.size() > 0) && (level < 5)) {
                ResultFromRedisModel rfrm = new ResultFromRedisModel();
                rfrm.setLevel(4);
                rfrm.setPlanIDHashKey(userID + "#" + isBananer + "#" + isApp + "#" + sspCode);
                rfrm.setInvestIDSet(planIDList);
                return rfrm;
            }

        } catch (Exception e) {
            log.error("getAfterInvestPlanFromRedis = " + e.getMessage(), e);
            e.printStackTrace();
        }
        return null;
    }

    public ResultFromRedisModel getDealPlanFromRedis(String dealID, String cityName, String impAdID, String isBananer, int level, String isApp) {
        try {
            ResultFromRedisModel rfrm = new ResultFromRedisModel();
            String orderListStr = INSTANCE.getJedis().get(dealID + "#" + cityName + "#" + impAdID + "#" + isApp);
            if ((orderListStr != null) && (!"".equals(orderListStr.trim())) && (level < 2)) {
                rfrm.setLevel(1);
                rfrm.setPlanIDArr(orderListStr.split("\\#"));
                return rfrm;
            }

            orderListStr = INSTANCE.getJedis().get(dealID + "#" + cityName + "#" + isBananer + "#" + isApp);
            if ((orderListStr != null) && (!"".equals(orderListStr.trim())) && (level < 3)) {
                rfrm.setLevel(2);
                rfrm.setPlanIDArr(orderListStr.split("\\#"));
                return rfrm;
            }

            orderListStr = INSTANCE.getJedis().get(dealID + "#" + impAdID + "#" + isApp);
            if ((orderListStr != null) && (!"".equals(orderListStr.trim())) && (level < 4)) {
                rfrm.setLevel(3);
                rfrm.setPlanIDArr(orderListStr.split("\\#"));
                return rfrm;
            }

            orderListStr = INSTANCE.getJedis().get(dealID + "#" + "NoLimitPlan" + "#" + isBananer + "#" + isApp);
            if ((orderListStr != null) && (!"".equals(orderListStr.trim())) && (level < 5)) {
                rfrm.setLevel(4);
                rfrm.setPlanIDArr(orderListStr.split("\\#"));
                return rfrm;
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return null;
    }
}