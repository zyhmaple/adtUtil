/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  Ice.Communicator
 *  Ice.Identity
 *  Ice.Object
 *  Ice.ObjectAdapter
 *  Ice.ObjectPrx
 *  Ice.Properties
 *  IceBox.Service
 */
package com.dm.adrich.communicate.bid;

import Ice.Communicator;
import Ice.Identity;
import Ice.Object;
import Ice.ObjectAdapter;
import Ice.ObjectPrx;
import Ice.Properties;
import IceBox.Service;
import com.dm.adrich.communicate.bid.GeneralParseRequestServiceI;

public class BidServerIceboxService
implements Service {
    private ObjectAdapter adapter;

    public void start(String name, Communicator communicator, String[] args) {
        this.adapter = communicator.createObjectAdapter("adrichBid-" + name);
        String adrichBidIdentity = communicator.getProperties().getProperty("bidAdapter.Identity");
        this.adapter.add((Object)new GeneralParseRequestServiceI(), communicator.stringToIdentity(adrichBidIdentity));
        this.adapter.activate();
    }

    public void stop() {
        this.adapter.destroy();
    }
}

