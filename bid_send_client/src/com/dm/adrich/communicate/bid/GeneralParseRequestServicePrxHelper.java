/*
 * Decompiled with CFR 0_123.
 */
package com.dm.adrich.communicate.bid;

import Ice.AsyncResult;
import Ice.Callback;
import Ice.Exception;
import Ice.FormatType;
import Ice.LocalException;
import Ice.ObjectPrx;
import Ice.ObjectPrxHelperBase;
import Ice.OperationMode;
import Ice.SystemException;
import Ice.TwowayCallbackArg1;
import Ice.UnknownUserException;
import Ice.UserException;
import IceInternal.BasicStream;
import IceInternal.CallbackBase;
import IceInternal.Functional_BoolCallback;
import IceInternal.Functional_GenericCallback1;
import IceInternal.Functional_TwowayCallbackArg1;
import IceInternal.OutgoingAsync;
import com.dm.adrich.communicate.bid.Callback_GeneralParseRequestService_generalDealBidRequest;
import com.dm.adrich.communicate.bid.GeneralParaModel;
import com.dm.adrich.communicate.bid.GeneralParseRequestServicePrx;
import com.dm.adrich.communicate.bid.GeneralResponseModel;
import java.util.Map;

public final class GeneralParseRequestServicePrxHelper
extends ObjectPrxHelperBase
implements GeneralParseRequestServicePrx {
    private static final String __generalDealBidRequest_name = "generalDealBidRequest";
    public static final String[] __ids = new String[]{"::Ice::Object", "::com::dm::adrich::communicate::bid::GeneralParseRequestService"};
    public static final long serialVersionUID = 0;

    @Override
    public GeneralResponseModel generalDealBidRequest(GeneralParaModel gpm, int isPC) {
        return this.generalDealBidRequest(gpm, isPC, null, false);
    }

    @Override
    public GeneralResponseModel generalDealBidRequest(GeneralParaModel gpm, int isPC, Map<String, String> __ctx) {
        return this.generalDealBidRequest(gpm, isPC, __ctx, true);
    }

    private GeneralResponseModel generalDealBidRequest(GeneralParaModel gpm, int isPC, Map<String, String> __ctx, boolean __explicitCtx) {
        this.__checkTwowayOnly("generalDealBidRequest");
        return this.end_generalDealBidRequest(this.begin_generalDealBidRequest(gpm, isPC, __ctx, __explicitCtx, true, null));
    }

    @Override
    public AsyncResult begin_generalDealBidRequest(GeneralParaModel gpm, int isPC) {
        return this.begin_generalDealBidRequest(gpm, isPC, null, false, false, null);
    }

    @Override
    public AsyncResult begin_generalDealBidRequest(GeneralParaModel gpm, int isPC, Map<String, String> __ctx) {
        return this.begin_generalDealBidRequest(gpm, isPC, __ctx, true, false, null);
    }

    @Override
    public AsyncResult begin_generalDealBidRequest(GeneralParaModel gpm, int isPC, Callback __cb) {
        return this.begin_generalDealBidRequest(gpm, isPC, null, false, false, __cb);
    }

    @Override
    public AsyncResult begin_generalDealBidRequest(GeneralParaModel gpm, int isPC, Map<String, String> __ctx, Callback __cb) {
        return this.begin_generalDealBidRequest(gpm, isPC, __ctx, true, false, __cb);
    }

    @Override
    public AsyncResult begin_generalDealBidRequest(GeneralParaModel gpm, int isPC, Callback_GeneralParseRequestService_generalDealBidRequest __cb) {
        return this.begin_generalDealBidRequest(gpm, isPC, null, false, false, __cb);
    }

    @Override
    public AsyncResult begin_generalDealBidRequest(GeneralParaModel gpm, int isPC, Map<String, String> __ctx, Callback_GeneralParseRequestService_generalDealBidRequest __cb) {
        return this.begin_generalDealBidRequest(gpm, isPC, __ctx, true, false, __cb);
    }

    @Override
    public AsyncResult begin_generalDealBidRequest(GeneralParaModel gpm, int isPC, Functional_GenericCallback1<GeneralResponseModel> __responseCb, Functional_GenericCallback1<Exception> __exceptionCb) {
        return this.begin_generalDealBidRequest(gpm, isPC, null, false, false, __responseCb, __exceptionCb, null);
    }

    @Override
    public AsyncResult begin_generalDealBidRequest(GeneralParaModel gpm, int isPC, Functional_GenericCallback1<GeneralResponseModel> __responseCb, Functional_GenericCallback1<Exception> __exceptionCb, Functional_BoolCallback __sentCb) {
        return this.begin_generalDealBidRequest(gpm, isPC, null, false, false, __responseCb, __exceptionCb, __sentCb);
    }

    @Override
    public AsyncResult begin_generalDealBidRequest(GeneralParaModel gpm, int isPC, Map<String, String> __ctx, Functional_GenericCallback1<GeneralResponseModel> __responseCb, Functional_GenericCallback1<Exception> __exceptionCb) {
        return this.begin_generalDealBidRequest(gpm, isPC, __ctx, true, false, __responseCb, __exceptionCb, null);
    }

    @Override
    public AsyncResult begin_generalDealBidRequest(GeneralParaModel gpm, int isPC, Map<String, String> __ctx, Functional_GenericCallback1<GeneralResponseModel> __responseCb, Functional_GenericCallback1<Exception> __exceptionCb, Functional_BoolCallback __sentCb) {
        return this.begin_generalDealBidRequest(gpm, isPC, __ctx, true, false, __responseCb, __exceptionCb, __sentCb);
    }

    private AsyncResult begin_generalDealBidRequest(GeneralParaModel gpm, int isPC, Map<String, String> __ctx, boolean __explicitCtx, boolean __synchronous, Functional_GenericCallback1<GeneralResponseModel> __responseCb, Functional_GenericCallback1<Exception> __exceptionCb, Functional_BoolCallback __sentCb) {
        return this.begin_generalDealBidRequest(gpm, isPC, __ctx, __explicitCtx, __synchronous, new Functional_TwowayCallbackArg1<GeneralResponseModel>(__responseCb, __exceptionCb, __sentCb){

            @Override
            public final void __completed(AsyncResult __result) {
                GeneralParseRequestServicePrxHelper.__generalDealBidRequest_completed(this, __result);
            }
        });
    }

    private AsyncResult begin_generalDealBidRequest(GeneralParaModel gpm, int isPC, Map<String, String> __ctx, boolean __explicitCtx, boolean __synchronous, CallbackBase __cb) {
        this.__checkAsyncTwowayOnly("generalDealBidRequest");
        OutgoingAsync __result = this.getOutgoingAsync("generalDealBidRequest", __cb);
        try {
            __result.prepare("generalDealBidRequest", OperationMode.Normal, __ctx, __explicitCtx, __synchronous);
            BasicStream __os = __result.startWriteParams(FormatType.DefaultFormat);
            GeneralParaModel.__write(__os, gpm);
            __os.writeInt(isPC);
            __result.endWriteParams();
            __result.invoke();
        }
        catch (Exception __ex) {
            __result.abort(__ex);
        }
        return __result;
    }

    @Override
    public GeneralResponseModel end_generalDealBidRequest(AsyncResult __iresult) {
        OutgoingAsync __result = OutgoingAsync.check(__iresult, this, "generalDealBidRequest");
        try {
            if (!__result.__wait()) {
                try {
                    __result.throwUserException();
                }
                catch (UserException __ex) {
                    throw new UnknownUserException(__ex.ice_name(), __ex);
                }
            }
            BasicStream __is = __result.startReadParams();
            GeneralResponseModel __ret = null;
            __ret = GeneralResponseModel.__read(__is, __ret);
            __result.endReadParams();
            GeneralResponseModel generalResponseModel = __ret;
            return generalResponseModel;
        }
        finally {
            if (__result != null) {
                __result.cacheMessageBuffers();
            }
        }
    }

    public static void __generalDealBidRequest_completed(TwowayCallbackArg1<GeneralResponseModel> __cb, AsyncResult __result) {
        GeneralParseRequestServicePrx __proxy = (GeneralParseRequestServicePrx)__result.getProxy();
        GeneralResponseModel __ret = null;
        try {
            __ret = __proxy.end_generalDealBidRequest(__result);
        }
        catch (LocalException __ex) {
            __cb.exception(__ex);
            return;
        }
        catch (SystemException __ex) {
            __cb.exception(__ex);
            return;
        }
        __cb.response(__ret);
    }

    public static GeneralParseRequestServicePrx checkedCast(ObjectPrx __obj) {
        return GeneralParseRequestServicePrxHelper.checkedCastImpl(__obj, GeneralParseRequestServicePrxHelper.ice_staticId(), GeneralParseRequestServicePrx.class, GeneralParseRequestServicePrxHelper.class);
    }

    public static GeneralParseRequestServicePrx checkedCast(ObjectPrx __obj, Map<String, String> __ctx) {
        return GeneralParseRequestServicePrxHelper.checkedCastImpl(__obj, __ctx, GeneralParseRequestServicePrxHelper.ice_staticId(), GeneralParseRequestServicePrx.class, GeneralParseRequestServicePrxHelper.class);
    }

    public static GeneralParseRequestServicePrx checkedCast(ObjectPrx __obj, String __facet) {
        return GeneralParseRequestServicePrxHelper.checkedCastImpl(__obj, __facet, GeneralParseRequestServicePrxHelper.ice_staticId(), GeneralParseRequestServicePrx.class, GeneralParseRequestServicePrxHelper.class);
    }

    public static GeneralParseRequestServicePrx checkedCast(ObjectPrx __obj, String __facet, Map<String, String> __ctx) {
        return GeneralParseRequestServicePrxHelper.checkedCastImpl(__obj, __facet, __ctx, GeneralParseRequestServicePrxHelper.ice_staticId(), GeneralParseRequestServicePrx.class, GeneralParseRequestServicePrxHelper.class);
    }

    public static GeneralParseRequestServicePrx uncheckedCast(ObjectPrx __obj) {
        return GeneralParseRequestServicePrxHelper.uncheckedCastImpl(__obj, GeneralParseRequestServicePrx.class, GeneralParseRequestServicePrxHelper.class);
    }

    public static GeneralParseRequestServicePrx uncheckedCast(ObjectPrx __obj, String __facet) {
        return GeneralParseRequestServicePrxHelper.uncheckedCastImpl(__obj, __facet, GeneralParseRequestServicePrx.class, GeneralParseRequestServicePrxHelper.class);
    }

    public static String ice_staticId() {
        return __ids[1];
    }

    public static void __write(BasicStream __os, GeneralParseRequestServicePrx v) {
        __os.writeProxy(v);
    }

    public static GeneralParseRequestServicePrx __read(BasicStream __is) {
        ObjectPrx proxy = __is.readProxy();
        if (proxy != null) {
            GeneralParseRequestServicePrxHelper result = new GeneralParseRequestServicePrxHelper();
            result.__copyFrom(proxy);
            return result;
        }
        return null;
    }

}

