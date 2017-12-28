/*
 * Decompiled with CFR 0_123.
 */
package com.dm.adrich.communicate.bid.model;

import java.util.Set;

public class ResultFromRedisModel {
    public String[] planIDArr = null;
    public int level = 1;
    public String planIDHashKey = null;
    public int priNum = 1;
    public Set investIDSet = null;

    public String[] getPlanIDArr() {
        return this.planIDArr;
    }

    public void setPlanIDArr(String[] planIDArr) {
        this.planIDArr = planIDArr;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getPlanIDHashKey() {
        return this.planIDHashKey;
    }

    public void setPlanIDHashKey(String planIDHashKey) {
        this.planIDHashKey = planIDHashKey;
    }

    public int getPriNum() {
        return this.priNum;
    }

    public void setPriNum(int priNum) {
        this.priNum = priNum;
    }

    public Set getInvestIDSet() {
        return this.investIDSet;
    }

    public void setInvestIDSet(Set investIDSet) {
        this.investIDSet = investIDSet;
    }
}

