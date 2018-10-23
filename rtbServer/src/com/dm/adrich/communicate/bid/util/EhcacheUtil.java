/*
 * Decompiled with CFR 0_123.
 *
 * Could not load the following classes:
 *  net.sf.ehcache.Cache
 *  net.sf.ehcache.CacheManager
 *  net.sf.ehcache.Element
 */
package com.dm.adrich.communicate.bid.util;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import java.io.Serializable;
import java.util.List;

public enum EhcacheUtil {
    INSTANCE;

    private CacheManager cacheManager = null;
    private Cache ehcache = null;


    public CacheManager getCacheManager() {
        return this.cacheManager;
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public void put(String key, Object value) {
        Element element = new Element((Object) key, value);
        this.getCache().put(element);
    }

    public Object get(String key) {
        Element element = this.getCache().get((Serializable) ((Object) key));
        return element == null ? null : element.getObjectValue();
    }

    private Cache getCache() {
        if (this.ehcache != null) {
            return this.ehcache;
        }
        return this.cacheManager.getCache(SysParams.sysProps.getProperty("ehcache.name"));
    }

    public void clearAllFromCacheManager() {
        if (this.getCacheManager() != null) {
            this.getCacheManager().clearAll();
            System.out.println("CacheManager was clearAll...");
        }
    }

    public void remove(String key) {
        this.getCache().remove((Serializable) ((Object) key));
    }

    public List getKeys() {
        return this.getCache().getKeys();
    }
}

