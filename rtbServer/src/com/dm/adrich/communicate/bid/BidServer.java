/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  Ice.Application
 *  Ice.Identity
 *  Ice.Object
 *  Ice.ObjectAdapter
 *  Ice.ObjectPrx
 *  org.apache.log4j.Logger
 */
package com.dm.adrich.communicate.bid;

import Ice.Application;
import Ice.Identity;
import Ice.Object;
import Ice.ObjectAdapter;
import Ice.ObjectPrx;
import com.dm.adrich.communicate.bid.GeneralParseRequestServiceI;
import com.dm.adrich.communicate.bid.util.DataTimerTask;
import com.dm.adrich.communicate.bid.util.SystemInit;
import java.io.PrintStream;
import java.util.Timer;
import java.util.TimerTask;
import org.apache.log4j.Logger;

public class BidServer
extends Application {
    protected static Logger log = null;

    public int run(String[] args) {
        if (args.length > 3) {
            System.err.println(String.valueOf(BidServer.appName()) + ": too many arguments");
            return 1;
        }
        ObjectAdapter adapter = BidServer.communicator().createObjectAdapter("adrichBid");
        log.info((java.lang.Object)"1111111111111");
        adapter.add((Object)new GeneralParseRequestServiceI(), BidServer.communicator().stringToIdentity("bidAdapter"));
        adapter.activate();
        BidServer.communicator().waitForShutdown();
        return 0;
    }

    public static void main(String[] args) {
        String confFilePath = "E:/adrichAllInstall/99/ice/conf/sys.properties";
        if (args != null && args.length > 0) {
            confFilePath = args[0];
            System.out.println("\u65e5\u5fd7\u7684\u76ee\u5f55\u662f = " + args[1]);
            System.setProperty("logDir", args[1]);
            log = Logger.getLogger(BidServer.class);
        }
        try {
            SystemInit.INSTANCE.contextInitialized(confFilePath);
            Timer timer = new Timer();
            timer.schedule((TimerTask)new DataTimerTask(), 1000, 600000);
        }
        catch (Exception e) {
            log.error((java.lang.Object)e.getMessage(), (Throwable)e);
            e.printStackTrace();
        }
        BidServer app = new BidServer();
        int status = app.main("BidServer", args);
        SystemInit.INSTANCE.contextDestroyed();
        System.exit(status);
    }
}

