/*
 * Decompiled with CFR 0_123.
 */
package com.dm.adrich.communicate.bid;

import Ice.*;
import Ice.Object;
import IceInternal.BasicStream;
import IceInternal.Incoming;

import java.util.Arrays;

public abstract class _GeneralParseRequestServiceDisp
extends ObjectImpl
implements GeneralParseRequestService {
    public static final String[] __ids = new String[]{"::Ice::Object", "::com::dm::adrich::communicate::bid::GeneralParseRequestService"};
    private static final String[] __all = new String[]{"generalDealBidRequest", "ice_id", "ice_ids", "ice_isA", "ice_ping"};
    public static final long serialVersionUID = 0;

    protected void ice_copyStateFrom(Object __obj) throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    @Override
    public boolean ice_isA(String s) {
        if (Arrays.binarySearch(__ids, s) >= 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean ice_isA(String s, Current __current) {
        if (Arrays.binarySearch(__ids, s) >= 0) {
            return true;
        }
        return false;
    }

    @Override
    public String[] ice_ids() {
        return __ids;
    }

    @Override
    public String[] ice_ids(Current __current) {
        return __ids;
    }

    @Override
    public String ice_id() {
        return __ids[1];
    }

    @Override
    public String ice_id(Current __current) {
        return __ids[1];
    }

    public static String ice_staticId() {
        return __ids[1];
    }

    @Override
    public final GeneralResponseModel generalDealBidRequest(GeneralParaModel gpm, int isPC) {
        return this.generalDealBidRequest(gpm, isPC, null);
    }

    public static DispatchStatus ___generalDealBidRequest(GeneralParseRequestService __obj, Incoming __inS, Current __current) {
        __checkMode(OperationMode.Normal, __current.mode);
        BasicStream __is = __inS.startReadParams();
        GeneralParaModel gpm = null;
        gpm = GeneralParaModel.__read(__is, gpm);
        int isPC = __is.readInt();
        __inS.endReadParams();
        GeneralResponseModel __ret = __obj.generalDealBidRequest(gpm, isPC, __current);
        BasicStream __os = __inS.__startWriteParams(FormatType.DefaultFormat);
        GeneralResponseModel.__write(__os, __ret);
        __inS.__endWriteParams(true);
        return DispatchStatus.DispatchOK;
    }

    @Override
    public DispatchStatus __dispatch(Incoming in, Current __current) {
        int pos = Arrays.binarySearch(__all, __current.operation);
        if (pos < 0) {
            throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
        }
        switch (pos) {
            case 0: {
                return _GeneralParseRequestServiceDisp.___generalDealBidRequest(this, in, __current);
            }
            case 1: {
                return _GeneralParseRequestServiceDisp.___ice_id(this, in, __current);
            }
            case 2: {
                return _GeneralParseRequestServiceDisp.___ice_ids(this, in, __current);
            }
            case 3: {
                return _GeneralParseRequestServiceDisp.___ice_isA(this, in, __current);
            }
            case 4: {
                return _GeneralParseRequestServiceDisp.___ice_ping(this, in, __current);
            }
        }
        assert (false);
        throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
    }

    @Override
    protected void __writeImpl(BasicStream __os) {
        __os.startWriteSlice(_GeneralParseRequestServiceDisp.ice_staticId(), -1, true);
        __os.endWriteSlice();
    }

    @Override
    protected void __readImpl(BasicStream __is) {
        __is.startReadSlice();
        __is.endReadSlice();
    }
}

