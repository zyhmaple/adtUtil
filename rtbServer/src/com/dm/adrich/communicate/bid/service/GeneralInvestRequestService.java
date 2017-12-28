/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  com.drunkmoon.xinhuanet.redis.XinHuaNormalJedis
 *  org.apache.log4j.Logger
 */
package com.dm.adrich.communicate.bid.service;

import com.dm.adrich.communicate.bid.GeneralParaModel;
import com.dm.adrich.communicate.bid.service.GeneralOrderAndPlanCheckUtil;
import com.dm.adrich.communicate.bid.util.CacheUtil;
import com.dm.adrich.communicate.bid.util.InvestJedisUtil;
import com.dm.adrich.communicate.bid.util.JedisUtil;
import com.drunkmoon.xinhuanet.redis.XinHuaNormalJedis;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;

public class GeneralInvestRequestService {
    private String mzUserCode = "_001";
    private static final String mzTagFlag = "MiaozhenIndustryTags";
    private static final String noLimitOrder = "NoLimitPlan";
    private static final String exposNum = "ExposNum";
    private static final String clickNum = "ClickNum";
    private static final String userOrderTags = "UserOrderTags";
    private static final String investRealNum = "InvestRealNum";
    private static final String mzUserTag = "MZUserTag";
    public static final String investTerm = "InvestTerm";
    public static final String creativeTag = "CreativeTag";
    public static final String investSSPCode = "InvestSSPCode";
    private Map dealMap = null;
    private Map<String, String> creativeIDMap = null;
    private GeneralParaModel gpm = null;
    private String city = null;
    protected static final Logger log = Logger.getLogger(GeneralInvestRequestService.class);

    public GeneralInvestRequestService(GeneralParaModel gpm, Map<String, String> createIDMap, String city) {
        this.gpm = gpm;
        this.creativeIDMap = createIDMap;
        this.city = city;
    }

    public String selectAInvestPlanByTerm() {
        String investIDHashKey = String.valueOf(this.gpm.userID) + "#" + this.gpm.isBanner + "#" + "InvestTerm";
        log.info((Object)("\u8ffd\u6295investIDHashKey = " + investIDHashKey));
        String selectOrder = this.selectASuitOrderByAfterInvest(this.gpm.userID, this.gpm.isApp, investIDHashKey, this.gpm.sspCode);
        if (selectOrder != null) {
            log.info((Object)("\u9009\u62e9\u7684\u8ffd\u6295\u8ba1\u5212\u4e3aselectOrder = " + selectOrder));
            return selectOrder;
        }
        return null;
    }

    public String selectASuitOrderByAfterInvest(String userID, String isApp, String investIDHashKey, String sspCode)
    {
      Set<String> investIDList = null;
      try
      {
        investIDList = InvestJedisUtil.INSTANCE.getInvestJedis().hkeys(investIDHashKey);
      }
      catch (Exception e) {
        log.error("selectASuitOrderByAfterInvest111=" + e.getMessage(), e);
        e.printStackTrace();
      }

      if (investIDList != null)
      {
        for (String investID : investIDList)
        {
          try
          {
            Set sspCodeSet = (Set)CacheUtil.INSTANCE.getEhcacheList(investID + "InvestSSPCode", "key");
            if ((sspCodeSet == null) || (sspCodeSet.size() <= 0))
            {
              continue;
            }

            if (sspCodeSet.contains(sspCode));
          }
          catch (Exception e1)
          {
            e1.printStackTrace();

            String redisKey = userID + "_" + investID + "InvestRealNum";

            int realNum = 0;
            try
            {
              String realNumStr = InvestJedisUtil.INSTANCE.getInvestJedis().get(redisKey);
              log.info("追投中的 investID= " + investID + " userID = " + userID);
              log.info("追投中的 investID= " + investID + " realNumStr = " + realNumStr);
              if (realNumStr != null)
              {
                realNum = Integer.parseInt(realNumStr);
              }
            }
            catch (Exception e) {
              log.error("selectASuitOrderByAfterInvest222=" + e.getMessage(), e);
              e.printStackTrace();
            }
            try
            {
              String investNum = InvestJedisUtil.INSTANCE.getInvestJedis().hget(investIDHashKey, investID);
              if ((investNum == null) || ("".equals(investNum)))
              {
                continue;
              }
              int targetNum = Integer.parseInt(investNum);
              log.info("追投中的 investID= " + investID + " targetNum = " + targetNum);
              if (targetNum <= realNum) {
                continue;
              }
              String planIDHashKey = investIDHashKey + investID;

              List planIDList = InvestJedisUtil.INSTANCE.getInvestJedis().hvals(planIDHashKey);

              if ((planIDList == null) || (planIDList.size() <= 0))
                continue;
              String selectPlanID = randomGetAfterInvestPlanID(planIDList, isApp, planIDHashKey);
              planIDList = null;
              if (selectPlanID != null)
              {
                return selectPlanID;
              }

            }
            catch (Exception e)
            {
              log.error("selectASuitOrderByAfterInvest333=" + e.getMessage(), e);
              e.printStackTrace();
            }
          }
        }
      }

      return null;
    }

    public String randomGetAfterInvestPlanID(List orderIDArr, String isApp, String priHashKey) {
        if (orderIDArr.size() == 0) {
            return null;
        }
        int index = (int)(Math.random() * (double)orderIDArr.size());
        String planID = (String)orderIDArr.remove(index);
        boolean checkResult = this.checkInvestCondition(planID, isApp, priHashKey);
        if (checkResult) {
            return planID;
        }
        return this.randomGetAfterInvestPlanID(orderIDArr, isApp, priHashKey);
    }

    public boolean checkInvestCondition(String planID, String isApp, String priHashKey)
    {
      String orderInfoJson = null;
      try
      {
        String cityList = CacheUtil.INSTANCE.getEhcache(planID, "cityList");

        if ((cityList != null) && (!"".equals(cityList)))
        {
          if (!cityList.contains(this.city))
          {
            return false;
          }
        }

        int exposLimit = 0;
        String exposObj = CacheUtil.INSTANCE.getEhcache(planID, "exposLimit");
        if (exposObj != null)
        {
          exposLimit = Integer.parseInt(exposObj);
        }
        int clickLimit = 0;
        String clickObj = CacheUtil.INSTANCE.getEhcache(planID, "clickLimit");
        if (clickObj != null)
        {
          clickLimit = Integer.parseInt(clickObj);
        }

        long startTime = 0L;
        long endTime = 0L;
        String startTimeStr = CacheUtil.INSTANCE.getEhcache(planID, "startTime");
        String endTimeStr = CacheUtil.INSTANCE.getEhcache(planID, "endTime");

        if ((startTimeStr != null) && (!"".equals(startTimeStr)))
        {
          startTime = Long.parseLong(startTimeStr);
        }
        if ((endTimeStr != null) && (!"".equals(endTimeStr)))
        {
          endTime = Long.parseLong(endTimeStr);
        }
        long currentTime = System.currentTimeMillis();

        if ((startTime > 0L) && (endTime > 0L))
        {
          if ((currentTime > endTime) || (currentTime < startTime))
          {
            return false;
          }
        }
        else
        {
          return false;
        }

        if (exposLimit > 0)
        {
          String exposNumStr = JedisUtil.INSTANCE.getJedis().get(planID + "#" + isApp + "#" + "ExposNum");

          if (exposNumStr != null)
          {
            int realExposNum = Integer.parseInt(exposNumStr);

            if (realExposNum >= exposLimit)
            {
              return false;
            }

          }

        }

        if (clickLimit > 0)
        {
          String clickNumStr = JedisUtil.INSTANCE.getJedis().get(planID + "#" + isApp + "#" + "ClickNum");
          System.out.println("22222222222222222223333333 clickNumStr=" + clickNumStr);
          if (clickNumStr != null)
          {
            int realclickNum = Integer.parseInt(clickNumStr);
            if (realclickNum >= clickLimit)
            {
              return false;
            }

          }

        }

        String creativeID = GeneralOrderAndPlanCheckUtil.checkAdTypeAndSize(new ArrayList(this.gpm.sspCreativeTypeList), planID, this.gpm);

        if (creativeID == null)
        {
          log.info("追投中计划 ：" + planID + " 检查创意和广告位尺寸以及时长不通过！！");
          return false;
        }
        log.info("追投中计划 ：" + planID + " 检查创意和广告位尺寸以及时长通过");

        String isDeal = "no";
        if ("yes".equals(isDeal))
        {
          if (this.dealMap != null)
          {
            this.dealMap.put("planID", "yes");
          }
          else
          {
            this.dealMap = new HashMap();
            this.dealMap.put("planID", "yes");
          }
        }
        this.creativeIDMap.put(this.gpm.requestID, creativeID);

        return true;
      }
      catch (Exception e) {
        log.error("checkInvestCondition=" + e.getMessage(), e);
        e.printStackTrace();
      }

      return false;
    }

    public void releaseMap(Map map) {
        if (map != null) {
            map.clear();
            map = null;
        }
    }
}

