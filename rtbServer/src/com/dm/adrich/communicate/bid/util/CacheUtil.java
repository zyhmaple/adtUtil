/*
 * Decompiled with CFR 0_123.
 *
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
package com.dm.adrich.communicate.bid.util;

import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.*;

public enum CacheUtil {
    INSTANCE;

    public static final String valueTag = "#valueTag";
    public static final String keyTag = "#keyTag";
    protected static final Logger log;

    static {
        log = Logger.getLogger(CacheUtil.class);
    }

    private Map<String, Object> cache;
    private String dealTime = "";

    public Map<String, Object> getCache() {
        if (this.cache != null) {
            return this.cache;
        }
        log.info((Object) ("cache初始化开始执行！！！time = " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())));
        this.dealTime = "";
        return this.cache;
    }

    public void setCache(Map<String, Object> cache) {
        this.cache = cache;
    }

    public String getEhcache(String key) throws Exception {
        return (String) this.getCache().get(key.trim());
    }

    public String getEhcache(String hashKey, String key) throws Exception {
        return (String) this.getCache().get(String.valueOf(hashKey.trim()) + "#" + key.trim());
    }

    /**
     * 获取ehcachelist，cacheName=hashkey#key组成
     * 两个特例，key，value 代表keyTag和valueTag
     *
     * @param hashKey
     * @param key
     * @return
     * @throws Exception
     */
    public Object getEhcacheList(String hashKey, String key) throws Exception {
        log.info((Object) ("hashKEy  key = " + key));
        if ("key".equals(key.trim())) {
            Set keySet = (Set) this.getCache().get(String.valueOf(hashKey.trim()) + "#keyTag");
            if (keySet != null) {
                return new HashSet(keySet);
            }
            return null;
        }
        if ("value".equals(key)) {
            log.info((Object) ("hashKEy = " + hashKey + "#valueTag"));
            List valueList = (List) this.getCache().get(String.valueOf(hashKey.trim()) + "#valueTag");
            log.info((Object) ("valueList = " + valueList));
            if (valueList != null) {
                return new ArrayList(valueList);
            }
            return null;
        }
        return this.getCache().get(String.valueOf(hashKey.trim()) + "#" + key.trim());
    }

    public String getDealTime() {
        return this.dealTime;
    }

    public void setDealTime(String dealTime) {
        this.dealTime = dealTime;
    }
}

