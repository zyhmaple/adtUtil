/*
 * Decompiled with CFR 0_123.
 */
package com.dm.adrich.communicate.bid.dmp;

import com.dm.adrich.communicate.bid.dmp.DMPChecker;
import com.dm.adrich.communicate.bid.dmp.impl.AdmasterDMPChecker;
import com.dm.adrich.communicate.bid.dmp.impl.MiaoZhenDMPChecker;

public class DMPCheckerFactory {
    public DMPChecker createDMPChecker(String dmpCode) {
        if ("003".equals(dmpCode)) {
            return new AdmasterDMPChecker();
        }
        if ("002".equals(dmpCode)) {
            return new MiaoZhenDMPChecker();
        }
        return null;
    }
}

