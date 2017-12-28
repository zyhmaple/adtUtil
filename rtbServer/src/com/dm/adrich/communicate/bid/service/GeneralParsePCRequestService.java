package com.dm.adrich.communicate.bid.service;

import com.dm.adrich.communicate.bid.GeneralParaModel;
import com.dm.adrich.communicate.bid.GeneralResponseModel;
import com.dm.adrich.communicate.bid.model.ResultFromRedisModel;
import com.dm.adrich.communicate.bid.util.CacheUtil;
import com.dm.adrich.communicate.bid.util.Constant;
import com.dm.adrich.communicate.bid.util.GeoIP_Tools;
import com.dm.adrich.communicate.bid.util.JedisUtil;
import com.dm.adrich.communicate.bid.util.PDBDealUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

public class GeneralParsePCRequestService
{
  private String mzUserCode = "_001";
  private static final String mzTagFlag = "MiaozhenIndustryTags";
  private static final String noLimitOrder = "NoLimitPlan";
  private static final String exposNum = "ExposNum";
  private static final String clickNum = "ClickNum";
  private static final String userOrderTags = "UserOrderTags";
  private static final String investRealNum = "InvestRealNum";
  private static final String mzUserTag = "MZUserTag";
  public static final String isPC = "IsPCMark";
  public static final String isAPP = "IsAPPMark";
  public static final String msTag = "MsTags";
  private static final String siteTypeHashKey = "SiteTypeHashKey";
  public static final String creativeTag = "CreativeTag";
  private Map<String, String> createIDMap = null;
  public static final String noNativePlan = "NoNativePlan";
  protected static final Logger log = Logger.getLogger(GeneralParsePCRequestService.class);

  public GeneralResponseModel generalDealPCRequest(GeneralParaModel gpm)
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
        log.error("generalDealPCRequest = " + e.getMessage(), e);
        e.printStackTrace();
      }
    }

    if ((selectOrder != null) && (!selectOrder.startsWith("200:")) && (!selectOrder.startsWith("200#")) && (!"400".equals(selectOrder)))
    {
      log.info("选择的计划为7777777777selectOrder = " + selectOrder);

      return GeneralOrderAndPlanCheckUtil.generalResponse(gpm, cityName, selectOrder, (String)this.createIDMap.get(gpm.requestID));
    }

    return null;
  }

  public void jointPara(StringBuffer reponseStr, String para)
  {
    if ((para != null) && (!"".endsWith(para)))
    {
      reponseStr.append(para);
    }
    else
    {
      reponseStr.append("test");
    }
    reponseStr.append(":");
  }

  public String selectAPlanByTerm(GeneralParaModel gpm, String cityName, String investIDListStr)
    throws Exception
  {
    int level = 1;
    do
    {
      log.info("level = " + level);
      String nativeID = "NoNativePlan";
      if (4 == gpm.buyWay)
      {
        nativeID = "NoNativePlan#" + gpm.appID + gpm.sspCode;
      }
      ResultFromRedisModel rfrm = JedisUtil.INSTANCE.getPlanFromRedis(cityName, nativeID, gpm.isBanner, level, gpm.isApp, this.mzUserCode);
      if (rfrm != null)
      {
        level = rfrm.getLevel();

        String selectOrder = selectASuitOrderByTerm(rfrm, gpm, investIDListStr);
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

  public String selectASuitOrderByTerm(ResultFromRedisModel rfrm, GeneralParaModel gpm, String investIDListStr)
    throws Exception
  {
    HashMap orderTagMap = new HashMap();
    for (int j = 0; j < rfrm.getPriNum(); j++)
    {
      String aPriorityPlanIDHashKey = rfrm.getPlanIDHashKey() + j;
      log.info("33333333333333333aPriorityPlanIDHashKey = " + aPriorityPlanIDHashKey);
      List planIDList = (List)CacheUtil.INSTANCE.getEhcacheList(aPriorityPlanIDHashKey, "value");
      log.info("33333333333333333aPriorityPlanIDHashKey planIDList = " + planIDList);
      String selectOrder = randomGetPlanID(planIDList, gpm, orderTagMap, investIDListStr);

      if (selectOrder == null)
        continue;
      releaseMap(orderTagMap);
      return selectOrder;
    }

    releaseMap(orderTagMap);

    return null;
  }

  public String randomGetPlanID(List orderIDArr, GeneralParaModel gpm, HashMap orderTagMap, String investIDListStr)
  {
    if ((orderIDArr == null) || (orderIDArr.size() == 0))
    {
      return null;
    }
    int index = (int)(Math.random() * orderIDArr.size());

    String planID = (String)orderIDArr.remove(index);

    boolean checkResult = checkPlanCondition(planID, gpm, orderTagMap, investIDListStr);

    if (checkResult)
    {
      log.info("有符合的计划了 = " + planID);
      return planID;
    }

    long endTime = System.currentTimeMillis();
    if (endTime - gpm.startTime > Constant.dealTimeOut)
    {
      log.info("#############warn:The processing time is greater than 100ms!!!");
      return null;
    }

    return randomGetPlanID(orderIDArr, gpm, orderTagMap, investIDListStr);
  }

  public boolean checkPlanCondition(String planID, GeneralParaModel gpm, HashMap orderTagMap, String investIDListStr)
  {
    try
    {
      String gCheck = GeneralOrderAndPlanCheckUtil.generalCheck(planID, gpm, orderTagMap, this.createIDMap, investIDListStr);
      if ("false".equals(gCheck))
      {
        return false;
      }
      if ("fill".equals(gCheck))
      {
        return true;
      }

      log.info("计划 ：" + planID + " 检查通过！！");
      String ifPriceAndNum = CacheUtil.INSTANCE.getEhcache(planID, "ifPriceAndNum");
      PDBDealUtil.addResponsetNum(ifPriceAndNum, gpm.buyWay, planID, gpm.appID, gpm.sspCode);
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