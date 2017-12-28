package com.dm.adrich.communicate.bid.util;

import com.dm.adrich.netty.client.NettySender;
import com.dm.adrich.netty.util.NettySenderUtil;
import java.util.Properties;
import org.apache.log4j.Logger;

public class NettyUtil
{
  private static final String investPoolName = SysParams.sysProps.getProperty("netty.investPoolName");
  private static final String dmpPoolName = SysParams.sysProps.getProperty("netty.dmpPoolName");
  protected static final Logger log = Logger.getLogger(NettyUtil.class);

  public static String sendInvestData(String data)
  {
    String investIDListStr = null;
    try {
      NettySender nSender = NettySenderUtil.getNettySender(investPoolName);
      investIDListStr = nSender.sendRequest(data);
      log.info("追投investIDListStr = " + investIDListStr);
    }
    catch (Exception e)
    {
      log.error(e.getMessage(), e);
    }
    return investIDListStr;
  }

  public static String sendDMPData(String data)
  {
    String dmpStr = null;
    try {
      NettySender nSender = NettySenderUtil.getNettySender(dmpPoolName);
      dmpStr = nSender.sendRequest(data);
      log.info("dmp返回 = " + dmpStr);
    }
    catch (Exception e)
    {
      log.error(e.getMessage(), e);
    }
    return dmpStr;
  }
}