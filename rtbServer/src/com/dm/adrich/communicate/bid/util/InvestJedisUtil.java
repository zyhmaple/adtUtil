/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  com.drunkmoon.xinhuanet.redis.XinHuaNormalJedis
 *  com.drunkmoon.xinhuanet.redis.util.RedisUtil
 *  org.apache.log4j.Logger
 */
package com.dm.adrich.communicate.bid.util;

import com.dm.adrich.communicate.bid.util.SysParams;
import com.drunkmoon.xinhuanet.redis.XinHuaNormalJedis;
import com.drunkmoon.xinhuanet.redis.util.RedisUtil;
import java.util.Properties;
import org.apache.log4j.Logger;

public enum InvestJedisUtil {
    INSTANCE;
    
    private XinHuaNormalJedis investJedis = null;
    protected static final Logger log;

    static {
        log = Logger.getLogger(InvestJedisUtil.class);
    }


    public XinHuaNormalJedis getInvestJedis() {
        if (this.investJedis != null) {
            return this.investJedis;
        }
        try {
            this.investJedis = RedisUtil.getOneMasterNoSlaveJedis((String)SysParams.sysProps.getProperty("redis.groupInvestName"));
        }
        catch (Exception e) {
            log.error((Object)("getInvestJedis = " + e.getMessage()), (Throwable)e);
            e.printStackTrace();
        }
        return this.investJedis;
    }
}

