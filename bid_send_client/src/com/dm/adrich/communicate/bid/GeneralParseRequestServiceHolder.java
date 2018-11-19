/*
 * Decompiled with CFR 0_123.
 */
package com.dm.adrich.communicate.bid;

import Ice.Object;
import Ice.ObjectHolderBase;
import IceInternal.Ex;

public final class GeneralParseRequestServiceHolder
extends ObjectHolderBase<GeneralParseRequestService> {

    public GeneralParseRequestServiceHolder(GeneralParseRequestService value) {
        this.value = value;
    }

    @Override
    public void patch(Object v) {
        if (v == null || v instanceof GeneralParseRequestService) {
            this.value = (GeneralParseRequestService)v;
        } else {
            Ex.throwUOE(this.type(), v);
        }
    }

    @Override
    public String type() {
        return _GeneralParseRequestServiceDisp.ice_staticId();
    }
}

