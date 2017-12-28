/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  Ice.AsyncResult
 *  Ice.Callback
 *  Ice.Exception
 *  Ice.ObjectPrx
 *  IceInternal.Functional_BoolCallback
 *  IceInternal.Functional_GenericCallback1
 */
package com.dm.adrich.communicate.bid;

import Ice.AsyncResult;
import Ice.Callback;
import Ice.Exception;
import Ice.ObjectPrx;
import IceInternal.Functional_BoolCallback;
import IceInternal.Functional_GenericCallback1;
import com.dm.adrich.communicate.bid.Callback_GeneralParseRequestService_generalDealBidRequest;
import com.dm.adrich.communicate.bid.GeneralParaModel;
import com.dm.adrich.communicate.bid.GeneralResponseModel;
import java.util.Map;

public interface GeneralParseRequestServicePrx
extends ObjectPrx {
    public GeneralResponseModel generalDealBidRequest(GeneralParaModel var1, int var2);

    public GeneralResponseModel generalDealBidRequest(GeneralParaModel var1, int var2, Map<String, String> var3);

    public AsyncResult begin_generalDealBidRequest(GeneralParaModel var1, int var2);

    public AsyncResult begin_generalDealBidRequest(GeneralParaModel var1, int var2, Map<String, String> var3);

    public AsyncResult begin_generalDealBidRequest(GeneralParaModel var1, int var2, Callback var3);

    public AsyncResult begin_generalDealBidRequest(GeneralParaModel var1, int var2, Map<String, String> var3, Callback var4);

    public AsyncResult begin_generalDealBidRequest(GeneralParaModel var1, int var2, Callback_GeneralParseRequestService_generalDealBidRequest var3);

    public AsyncResult begin_generalDealBidRequest(GeneralParaModel var1, int var2, Map<String, String> var3, Callback_GeneralParseRequestService_generalDealBidRequest var4);

    public AsyncResult begin_generalDealBidRequest(GeneralParaModel var1, int var2, Functional_GenericCallback1<GeneralResponseModel> var3, Functional_GenericCallback1<Exception> var4);

    public AsyncResult begin_generalDealBidRequest(GeneralParaModel var1, int var2, Functional_GenericCallback1<GeneralResponseModel> var3, Functional_GenericCallback1<Exception> var4, Functional_BoolCallback var5);

    public AsyncResult begin_generalDealBidRequest(GeneralParaModel var1, int var2, Map<String, String> var3, Functional_GenericCallback1<GeneralResponseModel> var4, Functional_GenericCallback1<Exception> var5);

    public AsyncResult begin_generalDealBidRequest(GeneralParaModel var1, int var2, Map<String, String> var3, Functional_GenericCallback1<GeneralResponseModel> var4, Functional_GenericCallback1<Exception> var5, Functional_BoolCallback var6);

    public GeneralResponseModel end_generalDealBidRequest(AsyncResult var1);
}

