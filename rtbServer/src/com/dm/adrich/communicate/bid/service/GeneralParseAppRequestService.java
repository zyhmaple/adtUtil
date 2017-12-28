package com.dm.adrich.communicate.bid.service;

import com.dm.adrich.communicate.bid.GeneralParaModel;
import com.dm.adrich.communicate.bid.GeneralResponseModel;
import com.dm.adrich.communicate.bid.model.ResultFromRedisModel;
import com.dm.adrich.communicate.bid.util.CacheUtil;
import com.dm.adrich.communicate.bid.util.Constant;
import com.dm.adrich.communicate.bid.util.GeoIP_Tools;
import com.dm.adrich.communicate.bid.util.JedisUtil;
import com.dm.adrich.communicate.bid.util.PDBDealUtil;
import com.dm.adrich.communicate.bid.util.StringDealUtils;
import com.dm.adrich.communicate.bid.util.SysParams;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

public class GeneralParseAppRequestService
{
  private String mzUserCode = "_001";
  private static final String mzTagFlag = "MiaozhenIndustryTags";
  private static final String noLimitOrder = "NoLimitPlan";
  private static final String exposNum = "ExposNum";
  private static final String clickNum = "ClickNum";
  private static final String userOrderTags = "UserOrderTags";
  private static final String osTypeTag = "OSTypeTag";
  private static final String netTypeTag = "NetTypeTag";
  private static final String investRealNum = "InvestRealNum";
  private static final String mzUserTag = "MZUserTag";
  public static final String isPC = "IsPCMark";
  public static final String isAPP = "IsAPPMark";
  public static final String msTag = "MsTags";
  private static final String siteTypeHashKey = "SiteTypeHashKey";
  public static final String creativeTag = "CreativeTag";
  private Map<String, String> createIDMap = null;
  public static final String noNativePlan = "NoNativePlan";
  protected static final Logger log = Logger.getLogger(GeneralParseAppRequestService.class);

  public GeneralResponseModel generalDealAppRequest(GeneralParaModel gpm)
    throws Exception
  {
    this.mzUserCode = gpm.sspCode;

    this.createIDMap = new HashMap();
    String selectOrder = null;
    String userID = null;
    String userIP = gpm.ip;
    String cityName = "test";
    if ((userIP != null) && (!"".equals(userIP)))
    {
      cityName = GeoIP_Tools.GetCity(userIP);
      if (cityName != null)
      {
        cityName = cityName.toLowerCase();
      }

    }

    log.info("cityName = " + cityName + this.mzUserCode);
    if (gpm.userID != null)
    {
      userID = gpm.userID;

      if ((userID != null) && (!"".equals(userID)))
      {
        GeneralInvestRequestService pirs = new GeneralInvestRequestService(gpm, this.createIDMap, cityName);
        selectOrder = pirs.selectAInvestPlanByTerm();
      }
    }

    if ("404".equals(selectOrder))
    {
      return null;
    }

    if ((selectOrder == null) || (selectOrder.startsWith("200:")) || (selectOrder.startsWith("200#")) || ("400".equals(selectOrder)))
    {
      try
      {
        selectOrder = selectAPlanByTerm(gpm, cityName, selectOrder);
      }
      catch (Exception e) {
        log.error("generalDealAppRequest = " + e.getMessage(), e);
        e.printStackTrace();
      }
    }

    if ((selectOrder != null) && (!selectOrder.startsWith("200:")) && (!selectOrder.startsWith("200#")) && (!"400".equals(selectOrder)))
    {
      log.info("app选择的计划为selectOrder = " + selectOrder);
      return GeneralOrderAndPlanCheckUtil.generalResponse(gpm, cityName, selectOrder, (String)this.createIDMap.get(gpm.requestID));
    }

    return null;
  }

  public String selectAPlanByTerm(GeneralParaModel gapm, String cityName, String investIDListStr)
    throws Exception
  {
    int level = 1;
    do
    {
      String nativeID = "NoNativePlan";

      if ((gapm.buyWay > 3) && (StringDealUtils.checkStrNull(gapm.appID)))
      {
        if (5 == gapm.buyWay)
        {
          nativeID = gapm.appID;
        }
        else if (4 == gapm.buyWay)
        {
          nativeID = "NoNativePlan#" + gapm.appID + gapm.sspCode;
        }
        else
        {
          nativeID = gapm.appID + gapm.sspCode;
        }
      }
      ResultFromRedisModel rfrm = JedisUtil.INSTANCE.getPlanFromRedis(cityName, nativeID, gapm.isBanner, level, gapm.isApp, this.mzUserCode);
      if (rfrm != null)
      {
        level = rfrm.getLevel();

        String selectOrder = selectASuitOrderByTerm(rfrm, gapm, investIDListStr);
        if (selectOrder != null)
        {
          return selectOrder;
        }

        level++;
      }
      else
      {
        level = 15;
      }
    }
    while (
      level < 3);

    return null;
  }

  public String selectASuitOrderByTerm(ResultFromRedisModel rfrm, GeneralParaModel gapm, String investIDListStr)
    throws Exception
  {
    HashMap orderTagMap = new HashMap();
    for (int j = 0; j < rfrm.getPriNum(); j++)
    {
      String aPriorityPlanIDHashKey = rfrm.getPlanIDHashKey() + j;
      log.info("33333333333333333aPriorityPlanIDHashKey = " + aPriorityPlanIDHashKey);
      List planIDList = (List)CacheUtil.INSTANCE.getEhcacheList(aPriorityPlanIDHashKey, "value");

      log.info("33333333333333333aPriorityPlanIDHashKey planIDList = " + planIDList);
      String selectOrder = randomGetPlanID(planIDList, gapm, orderTagMap, investIDListStr);

      if (selectOrder == null)
        continue;
      releaseMap(orderTagMap);
      return selectOrder;
    }

    releaseMap(orderTagMap);

    return null;
  }

  public String randomGetPlanID(List orderIDArr, GeneralParaModel gapm, HashMap orderTagMap, String investIDListStr)
  {
    if ((orderIDArr == null) || (orderIDArr.size() == 0))
    {
      return null;
    }
    int index = (int)(Math.random() * orderIDArr.size());

    String planID = (String)orderIDArr.remove(index);

    boolean checkResult = checkPlanCondition(planID, gapm, orderTagMap, investIDListStr);

    if (checkResult)
    {
      return planID;
    }

    long endTime = System.currentTimeMillis();
    if (endTime - gapm.startTime > Constant.dealTimeOut)
    {
      log.info("#############warn:The processing time is greater than 100ms!!!");
      return null;
    }

    return randomGetPlanID(orderIDArr, gapm, orderTagMap, investIDListStr);
  }

  public boolean checkPlanCondition(String planID, GeneralParaModel gapm, HashMap orderTagMap, String investIDListStr)
  {
    try
    {
      String gCheck = GeneralOrderAndPlanCheckUtil.generalCheck(planID, gapm, orderTagMap, this.createIDMap, investIDListStr);
      if ("false".equals(gCheck))
      {
        return false;
      }
      if ("fill".equals(gCheck))
      {
        return true;
      }

      String deviceTypeList = CacheUtil.INSTANCE.getEhcache(planID, "deviceType");
      if ((deviceTypeList != null) && (!"".endsWith(deviceTypeList)))
      {
        String myDeviceType = (String)SysParams.deviceTypeProps.get(gapm.deviceType + this.mzUserCode);

        if (myDeviceType != null)
        {
          if (!deviceTypeList.contains(myDeviceType))
          {
            log.info("计划 ：" + planID + " 检查设备类型不通过！！");
            return false;
          }
        }
        else
        {
          log.info("计划 ：" + planID + " 检查设备类型不通过！！");
          return false;
        }
      }
      log.info("计划 ：" + planID + " 检查设备类型通过！！");

      String orderID = CacheUtil.INSTANCE.getEhcache(planID, "orderID");

      boolean osAndNetCheck = GeneralOrderAndPlanCheckUtil.checkOrderTag(gapm, orderTagMap, orderID);
      if (!osAndNetCheck)
      {
        log.info("计划 ：" + planID + " 检查网络及操作系统不通过！！");
        return false;
      }
      log.info("计划 ：" + planID + " 检查网络及操作系统通过！！");

      boolean lbsCheck = GeneralOrderAndPlanCheckUtil.checkLBS(planID, gapm.lon, gapm.lat);
      if (!lbsCheck)
      {
        log.info("计划 ：" + planID + " 检查lbs不通过！！");
        return false;
      }
      log.info("计划 ：" + planID + " 检查lbs通过！！");

      log.info("计划 ：" + planID + " 检查通过！！");
      String ifPriceAndNum = CacheUtil.INSTANCE.getEhcache(planID, "ifPriceAndNum");
      PDBDealUtil.addResponsetNum(ifPriceAndNum, gapm.buyWay, planID, gapm.appID, gapm.sspCode);

      return true;
    }
    catch (Exception e) {
      log.error("checkPlanCondition = " + e.getMessage(), e);
      e.printStackTrace();
    }

    return false;
  }

  public void releaseMap(Map map)
  {
    if (map != null)
    {
      map.clear();
      map = null;
    }
  }
}