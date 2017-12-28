/*
 * Decompiled with CFR 0_123.
 */
package com.dm.adrich.communicate.bid.dmp;

import com.dm.adrich.communicate.bid.model.DMPModel;
import java.util.List;

public interface DMPChecker {
    public boolean checkDmpTags(DMPModel var1) throws Exception;

    public List getDmpTags(DMPModel var1) throws Exception;
}

