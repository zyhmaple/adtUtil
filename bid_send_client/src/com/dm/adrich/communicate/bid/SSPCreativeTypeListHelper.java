/*
 * Decompiled with CFR 0_123.
 */
package com.dm.adrich.communicate.bid;

import IceInternal.BasicStream;
import java.util.ArrayList;
import java.util.List;

public final class SSPCreativeTypeListHelper {
    public static void write(BasicStream __os, List<String> __v) {
        if (__v == null) {
            __os.writeSize(0);
        } else {
            __os.writeSize(__v.size());
            for (String __elem : __v) {
                __os.writeString(__elem);
            }
        }
    }

    public static List<String> read(BasicStream __is) {
        ArrayList<String> __v = new ArrayList<String>();
        int __len0 = __is.readAndCheckSeqSize(1);
        int __i0 = 0;
        while (__i0 < __len0) {
            String __elem = __is.readString();
            __v.add(__elem);
            ++__i0;
        }
        return __v;
    }
}

