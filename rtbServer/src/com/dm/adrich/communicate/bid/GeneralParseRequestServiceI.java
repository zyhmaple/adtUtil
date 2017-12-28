package com.dm.adrich.communicate.bid;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import com.dm.adrich.communicate.bid.service.GeneralParseAppRequestService;
import com.dm.adrich.communicate.bid.service.GeneralParsePCRequestService;
import com.dm.adrich.communicate.bid.service.GneralParseOTTRequestService;
import com.dm.adrich.communicate.bid.util.AdrichRequestHttpUtil;
import com.dm.adrich.communicate.bid.util.CacheUtil;
import com.dm.adrich.communicate.bid.util.GeoIP_Tools;
import com.dm.adrich.communicate.bid.util.InterfaceConnentUtil;
import com.dm.adrich.communicate.bid.util.JedisUtil;
import com.dm.adrich.communicate.bid.util.LogProducer;
import com.dm.adrich.communicate.bid.util.PDBDealUtil;
import com.dm.adrich.communicate.bid.util.StringDealUtils;
import com.dm.adrich.communicate.bid.util.SysParams;

import Ice.Current;

public class GeneralParseRequestServiceI extends _GeneralParseRequestServiceDisp
{
  public static final String mzTagIDPCKey = "MZTagIDPCKey";
  public static final String mzTagIDMBKey = "MZTagIDMBKey";
  public static final String isPCStr = "IsPCMark";
  public static final String isAPPStr = "IsAPPMark";
  public static final String isOTTStr = "IsOTTMark";
  public static final String dealFlag = "#DealFlag";
  public static final String adrichIPBlackList = "adrichIPBlackList";
  protected static final Logger log = Logger.getLogger(GeneralParseRequestServiceI.class);
  private String rtbURL = "http://10.0.0.204/adrichForward";

  public GeneralResponseModel generalDealBidRequest(GeneralParaModel gpm, int isPC, Current __current)
  {
    log.info("通道占用时间为 WWwwwwwwwwwwwww= " + (System.currentTimeMillis() - gpm.startTime));
    long stime = System.currentTimeMillis();

    GeneralResponseModel grm = null;

    if (checkIP(gpm.ip))
    {
      log.info(gpm.ip + " 是黑名单！！！");
      grm = new GeneralResponseModel();
      grm.advertiserID = "adrich3009";
      return grm;
    }

    String isPCStr = "IsPCMark";
    if (2 == isPC)
    {
      isPCStr = "IsAPPMark";
    }
    else if (3 == isPC)
    {
      isPCStr = "IsOTTMark";
    }
    boolean toSendRTB = true;
    if ((gpm.buyWay == 4) || (gpm.buyWay == 6))
    {
      try
      {
        String dealHkey = gpm.appID + gpm.sspCode + "#DealFlag";

        String isExpose = CacheUtil.INSTANCE.getEhcache(dealHkey, "isExpose");
        if ((isExpose != null) && (!"".equals(isExpose)))
        {
          log.info("dealHkey =  ：" + dealHkey + " 是isExpose=" + isExpose);
          PDBDealUtil.addRequestNum(gpm.appID, gpm.sspCode);
          String reqDealStr = StringDealUtils.createReqDealStr(gpm.userID, isPCStr, gpm.appID, gpm.sspCode, gpm.requestID);
          boolean isEStats = false;
          if ("yes".equals(isExpose))
          {
            isEStats = true;
          }
          LogProducer ap = new LogProducer(reqDealStr, 1, isEStats);
          ap.start();
          toSendRTB = false;
        }
      }
      catch (Exception e)
      {
        log.error("sendLogToKafka = " + e.getMessage(), e);
        e.printStackTrace();
      }
    }

    if ((toSendRTB) && ("yes".equals(SysParams.sysProps.getProperty("isPDB"))))
    {
      JSONObject requestJson = AdrichRequestHttpUtil.getAdrichRequestJson(gpm);
      try {
        String respJson = InterfaceConnentUtil.getJsonStrAndSendJsonStr(this.rtbURL, requestJson.toJSONString());
        grm = AdrichRequestHttpUtil.getGeneralResponseModel(respJson);
      }
      catch (Exception e) {
        e.printStackTrace();
      }

    }
    else if (1 == isPC)
    {
      GeneralParsePCRequestService gpprs = new GeneralParsePCRequestService();
      try
      {
        grm = gpprs.generalDealPCRequest(gpm);
      }
      catch (Exception e) {
        log.error("generalDealBidRequest1111 = " + e.getMessage(), e);
        e.printStackTrace();
      }
    }
    else if (2 == isPC)
    {
      GeneralParseAppRequestService gpars = new GeneralParseAppRequestService();
      try {
        grm = gpars.generalDealAppRequest(gpm);
      }
      catch (Exception e) {
        log.error("generalDealBidRequest2222 = " + e.getMessage(), e);
        e.printStackTrace();
      }
    }
    else if (3 == isPC)
    {
      GneralParseOTTRequestService gpars = new GneralParseOTTRequestService();
      try {
        grm = gpars.generalDealAppRequest(gpm);
      }
      catch (Exception e) {
        log.error("generalDealBidRequest2222 = " + e.getMessage(), e);
        e.printStackTrace();
      }
    }

    log.info("系统处理结束占用时间为 WWwwwwwwwwwwwww= " + (System.currentTimeMillis() - stime));
    if (grm != null)
    {
      return grm;
    }

    grm = new GeneralResponseModel();
    grm.advertiserID = "adrich3009";
    return grm;
  }

  public boolean checkIP(String ip)
  {
    try
    {
      if ((ip != null) && (!"".equals(ip)))
      {
        long ipLong = GeoIP_Tools.ipToLong(ip);
        return JedisUtil.INSTANCE.getJedis().hexists("adrichIPBlackList", String.valueOf(ipLong)).booleanValue();
      } 
    }
    catch (Exception e) {
      log.error("serviceI IP异常 = " + e.getMessage(), e);
    }
    return true;
  }
}