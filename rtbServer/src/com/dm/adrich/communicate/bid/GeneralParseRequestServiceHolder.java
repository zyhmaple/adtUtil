/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  Ice.Object
 *  Ice.ObjectHolderBase
 *  IceInternal.Ex
 */
package com.dm.adrich.communicate.bid;

import Ice.Object;
import Ice.ObjectHolderBase;
import IceInternal.Ex;
import com.dm.adrich.communicate.bid.GeneralParseRequestService;
import com.dm.adrich.communicate.bid._GeneralParseRequestServiceDisp;

public final class GeneralParseRequestServiceHolder
extends ObjectHolderBase<GeneralParseRequestService> {
    public GeneralParseRequestServiceHolder() {
    }

    public GeneralParseRequestServiceHolder(GeneralParseRequestService value) {
        this.value = value;
    }

    public void patch(Object v) {
        if (v == null || v instanceof GeneralParseRequestService) {
            this.value = (GeneralParseRequestService)v;
        } else {
            Ex.throwUOE((String)this.type(), (Object)v);
        }
    }

    public String type() {
        return _GeneralParseRequestServiceDisp.ice_staticId();
    }
}

