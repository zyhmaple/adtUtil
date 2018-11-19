package com.dm.adrich.communicate.bid;

import Ice.Application;
import Ice.ObjectAdapter;
import com.dm.adrich.communicate.bid.util.*;
import org.apache.log4j.Logger;

import java.util.Timer;


public class BidServer extends Application
{
  protected static Logger log = null;

  public static void main(String[] args)
  {
    String confFilePath = "E:/adrichAllInstall/99/ice/conf/sys.properties";
    if ((args != null) && (args.length > 0))
    {
      confFilePath = args[0];//sys配置文件
      System.out.println("日志的目录是 = " + args[1]);//日志目录
      System.setProperty("logDir", args[1]);
      log = Logger.getLogger(BidServer.class);
    }
    try
    {
      //初始化sys属性文件
      SystemInit.INSTANCE.contextInitialized(confFilePath);

      Timer timer = new Timer();

      //间隔10分钟更新一次配置文件
      timer.schedule(new DataTimerTask(), 1000L, 600000L);

      Thread.sleep(60000L);

      if ("yes".equals(SysParams.sysProps.getProperty("isPDB")))
      {
        PDBTimeDealThread pdbtdt = new PDBTimeDealThread();

        Thread pdbThread = new Thread(pdbtdt);

        pdbThread.start();
        Thread.sleep(10000L);
      }

      //间隔6小时更新一次文件
      timer.schedule(new ConfigFileTimerTask(), 0L, 21600000L);
    }
    catch (Exception e) {
      log.error(e.getMessage(), e);
      e.printStackTrace();
    }

    BidServer app = new BidServer();
    int status = app.main("BidServer", args);//main中会回调run
    SystemInit.INSTANCE.contextDestroyed();
    System.exit(status);
  }

  @Override
  public int run(String[] args) {
    if (args.length > 3) {
      System.err.println(appName() + ": too many arguments");
      return 1;
    }

    ObjectAdapter adapter = communicator().createObjectAdapter("adrichBid");
    log.info("1111111111111");
    adapter.add(new GeneralParseRequestServiceI(), communicator().stringToIdentity("bidAdapter"));
    adapter.activate();
    communicator().waitForShutdown();
    return 0;
  }
}