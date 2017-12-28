/*
 * Decompiled with CFR 0_123.
 */
package com.dm.adrich.communicate.bid.pool;

import Ice.Communicator;
import Ice.ObjectPrx;
import Ice.Util;
import com.dm.adrich.communicate.bid.GeneralParseRequestServicePrx;
import com.dm.adrich.communicate.bid.GeneralParseRequestServicePrxHelper;
import com.dm.adrich.communicate.bid.pool.PoolPersistData;
import java.io.PrintStream;
import java.util.Map;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

public class IceConnectionFactory
extends BasePooledObjectFactory<Communicator> {
    private String serverIP = null;

    public IceConnectionFactory(String serverIP) {
        this.serverIP = serverIP;
    }

    @Override
    public void destroyObject(PooledObject<Communicator> obj) throws Exception {
        if (obj != null) {
            Communicator comm = obj.getObject();
            comm.destroy();
        }
    }

    @Override
    public boolean validateObject(PooledObject<Communicator> obj) {
        Communicator comm;
        GeneralParseRequestServicePrx gprsp;
        if (obj != null && (gprsp = (GeneralParseRequestServicePrx)PoolPersistData.obj2PrxMap.get(comm = obj.getObject())) != null) {
            return true;
        }
        return false;
    }

    @Override
    public Communicator create() throws Exception {
        String[] initParams = new String[]{this.serverIP};
        Communicator communicator = Util.initialize(initParams);
        ObjectPrx base = communicator.propertyToProxy("bidAdapter.Proxy");
        GeneralParseRequestServicePrx gprsp = GeneralParseRequestServicePrxHelper.checkedCast(base);
        PoolPersistData.obj2PrxMap.put(communicator, gprsp);
        System.out.println("PoolPersistData.obj2PrxMap = " + PoolPersistData.obj2PrxMap.size());
        return communicator;
    }

    @Override
    public PooledObject<Communicator> wrap(Communicator obj) {
        return new DefaultPooledObject<Communicator>(obj);
    }
}

