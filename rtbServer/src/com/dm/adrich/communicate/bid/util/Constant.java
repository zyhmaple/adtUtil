/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.apache.kafka.clients.producer.KafkaProducer
 */
package com.dm.adrich.communicate.bid.util;

import com.dm.adrich.communicate.bid.util.SysParams;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.kafka.clients.producer.KafkaProducer;

public class Constant {
	  public static final String mzUserCode = "_001";
	  public static final String mzTagFlag = "MiaozhenIndustryTags";
	  public static final String noLimitPlan = "NoLimitPlan";
	  public static final String exposNum = "ExposNum";
	  public static final String clickNum = "ClickNum";
	  public static final String userOrderTags = "UserOrderTags";
	  public static final String osTypeTag = "OSTypeTag";
	  public static final String netTypeTag = "NetTypeTag";
	  public static final String investRealNum = "InvestRealNum";
	  public static final String mzUserTag = "MZUserTag";
	  public static final String responseJson = "ResponseJson";
	  public static final String appPlanIDFlag = "AppPlanIDFlag";
	  public static final String isInvestOrder = "IsInvestOrder";
	  public static final String isPC = "IsPCMark";
	  public static final String isAPP = "IsAPPMark";
	  public static final String isOTT = "IsOTTMark";
	  public static final String winNoticePara = "id=${AUCTION_ID}&bidid=${AUCTION_BID_ID}&impid=${AUCTION_IMP_ID}&price=${AUCTION_PRICE}&";
	  public static final String tanxUserCode = "_002";
	  public static final String planIDHashKey = "planIDHashKey";
	  public static final String logTopicName = "logInfoSendTopic";
	  public static final String reqTopicName = "reqSendTopic";
	  public static final String respTopicName = "respSendTopic";
	  public static final String statsGPRTopicName = "statsGPRSendTopic";
	  public static final String winTopicName = "winNoticInfoSendTopic";
	  public static final String allPlanIDMark = "AllPlanIDMark";
	  public static final String msTag = "MsTags";
	  public static final String investTerm = "InvestTerm";
	  public static final String siteTypeHashKey = "SiteTypeHashKey";
	  public static KafkaProducer<byte[], byte[]> logProducer = null;
	  public static KafkaProducer<byte[], byte[]> exposProducer = null;
	  public static KafkaProducer<byte[], byte[]> clickProducer = null;
	  public static KafkaProducer<byte[], byte[]> winProducer = null;
	  public static Properties kafkaProps = null;
	  public static final String bdSSPCode = "_004";
	  public static final String baiduWinNoticePara = "price=%%PRICE%%";
	  public static final String adviewSSPCode = "_005";
	  public static final String creativeTag = "CreativeTag";
	  public static AtomicInteger counter = new AtomicInteger(1);
	  public static final int exposeToClick = Integer.parseInt(SysParams.sspProps.getProperty("adrich.expostToClick", "400"));
	  public static final String dataUpdateTimeFlag = "dataUpdateTimeFlag";
	  public static final String validOrderSelectTermList = "validOrderSelectTermList";
	  public static final String valueTag = "#valueTag";
	  public static final String keyTag = "#keyTag";
	  public static final String bdNameListHashKey = "bdNameListHashKey";
	  public static final String wdNameListHashKey = "wdNameListHashKey";
	  public static final String noNativePlan = "NoNativePlan";
	  public static final String mzTagIDPCKey = "MZTagIDPCKey";
	  public static final String mzTagIDMBKey = "MZTagIDMBKey";
	  public static final String sspAndAdrichTagRel = "SSPAndAdrichTagRel";
	  public static final String investSSPCode = "InvestSSPCode";
	  public static final String mzTagIDLocalKey = "MZTIDLKey";
	  public static final String investIDAndPlanIDList = "InvestIDAndPlanIDList";
	  public static final ConcurrentHashMap<String, AtomicLong> dealCountMap = new ConcurrentHashMap();
	  public static final ConcurrentHashMap<String, Boolean> fillMap = new ConcurrentHashMap();
	  public static final String pdbTimeFlag = "PDBTimeFlag";
	  public static final String mzDmpCode = "002";
	  public static final String admDmpCode = "003";
	  public static final String deepLinkFlag = "deeplink";
	  public static final String allWap = "allWap";
	  public static final String dealFlag = "#DealFlag";
	  public static final String dealIDList = "DealIDList";
	  public static final String dealExposNum = "#DealExposNum";
	  public static final String dealReqNum = "#DealReqNum";
	  public static final String dealRespNum = "#DealRespNum";
	  public static final String channelListFlag = "ChannleListFlag";
	  public static int dealTimeOut = 150;
	  public static final String adrichIPBlackList = "adrichIPBlackList";
}

