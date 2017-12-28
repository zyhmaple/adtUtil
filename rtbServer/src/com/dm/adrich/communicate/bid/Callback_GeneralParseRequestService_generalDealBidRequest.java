/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  Ice.AsyncResult
 *  Ice.TwowayCallbackArg1
 *  IceInternal.TwowayCallback
 */
package com.dm.adrich.communicate.bid;

import Ice.AsyncResult;
import Ice.TwowayCallbackArg1;
import IceInternal.TwowayCallback;
import com.dm.adrich.communicate.bid.GeneralParseRequestServicePrxHelper;
import com.dm.adrich.communicate.bid.GeneralResponseModel;

public abstract class Callback_GeneralParseRequestService_generalDealBidRequest
extends TwowayCallback
implements TwowayCallbackArg1<GeneralResponseModel> {
    public final void __completed(AsyncResult __result) {
        GeneralParseRequestServicePrxHelper.__generalDealBidRequest_completed(this, __result);
    }
}

