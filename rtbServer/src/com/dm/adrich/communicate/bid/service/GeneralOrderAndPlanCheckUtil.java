package com.dm.adrich.communicate.bid.service;

import com.dm.adrich.communicate.bid.GeneralParaModel;
import com.dm.adrich.communicate.bid.GeneralResponseModel;
import com.dm.adrich.communicate.bid.dmp.DMPChecker;
import com.dm.adrich.communicate.bid.dmp.DMPCheckerFactory;
import com.dm.adrich.communicate.bid.model.DMPModel;
import com.dm.adrich.communicate.bid.util.Base58;
import com.dm.adrich.communicate.bid.util.CacheUtil;
import com.dm.adrich.communicate.bid.util.JedisUtil;
import com.dm.adrich.communicate.bid.util.LogProducer;
import com.dm.adrich.communicate.bid.util.MiaoZhenDMPUtil;
import com.dm.adrich.communicate.bid.util.NettyUtil;
import com.dm.adrich.communicate.bid.util.PDBDealUtil;
import com.dm.adrich.communicate.bid.util.StringDealUtils;
import com.dm.adrich.communicate.bid.util.SysParams;
import com.dm.adrich.communicate.bid.util.TaDealUtil;
import com.drunkmoon.xinhuanet.redis.XinHuaNormalJedis;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;

public class GeneralOrderAndPlanCheckUtil {
	public static final String wordLink = "WordLink";
	public static final String msTag = "MsTags";
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
	private static final String deepLinkFlag = "deeplink";
	public static final String isPC = "IsPCMark";
	public static final String isAPP = "IsAPPMark";
	public static final String isOTT = "IsOTTMark";
	private static final String siteTypeHashKey = "SiteTypeHashKey";
	public static final String creativeTag = "CreativeTag";
	public static final String sspAndAdrichTagRel = "SSPAndAdrichTagRel";
	public static final String mzTagIDPCKey = "MZTagIDPCKey";
	public static final String mzTagIDMBKey = "MZTagIDMBKey";
	public static final String mzTagIDLocalKey = "MZTIDLKey";
	public static final String mzDmpCode = "002";
	public static final String admDmpCode = "003";
	public static final String allWap = "allWap";
	public static final String dealFlag = "#DealFlag";
	private static final String dealExposNumFlag = "#DealExposNum";
	private static final String dealReqNumFlag = "#DealReqNum";
	private static final String dealRespNumFlag = "#DealRespNum";
	protected static final Logger log = Logger.getLogger(GeneralOrderAndPlanCheckUtil.class);

	public static String checkAdTypeAndSize(List sspCTList, String planID, GeneralParaModel gapm) throws Exception {
		if (sspCTList.size() == 0) {
			return null;
		}
		int index = (int) (Math.random() * sspCTList.size());
		String sspCreativeType = String.valueOf(sspCTList.remove(index));
		log.info("计划 ：" + planID + " sspCreativeType = " + sspCreativeType);
		String adrichCreativeType = (String) SysParams.sspCreativeTypeProps.get(sspCreativeType + gapm.sspCode);
		log.info("计划 ：" + planID + " adrichCreativeType = " + adrichCreativeType);

		if ("1".equals(adrichCreativeType)) {
			String hasWL = CacheUtil.INSTANCE.getEhcache(planID, "WordLink_" + adrichCreativeType);
			if ("yes".equals(hasWL)) {
				String planMsHashKey = planID + "WordLink" + "_" + adrichCreativeType + "_" + "MsTags";
				List creativeIDList = (List) CacheUtil.INSTANCE.getEhcacheList(planMsHashKey, "value");
				int cIndex = (int) (Math.random() * creativeIDList.size());
				return (String) creativeIDList.get(cIndex);
			}

		} else {
			String planMsHashKey = planID + gapm.maSize + "_" + adrichCreativeType + "_" + "MsTags";
			if ("6".equals(adrichCreativeType)) {
				String nativeID = gapm.appID;
				if (6 == gapm.buyWay) {
					String[] tnIDArr = nativeID.split("#");
					nativeID = tnIDArr[0];
				}
				if ((gapm.maSize != null) && (!"".endsWith(gapm.maSize))) {
					planMsHashKey = planID + gapm.maSize + "_" + nativeID + "_" + "MsTags";
				} else {
					planMsHashKey = planID + nativeID + "_" + "MsTags";
				}
			}
			log.info("计划 ：" + planID + " maSize = " + gapm.maSize);

			String hasCID = CacheUtil.INSTANCE.getEhcache(planID, planMsHashKey);

			if ("yes".equals(hasCID)) {
				log.info("计划 ：" + planID + " planMsHashKey = " + planMsHashKey);
				String hasWeight = CacheUtil.INSTANCE.getEhcache(planMsHashKey, "hasWeight");
				if ("yes".equals(hasWeight)) {
					String msNum = CacheUtil.INSTANCE.getEhcache(planMsHashKey, "msNum");
					String investID = CacheUtil.INSTANCE.getEhcache(planMsHashKey, "investID");
					if (checkStrNull(investID)) {
						String investNum = NettyUtil.sendInvestData("select#" + investID + "#" + gapm.userID);
						if ((checkStrNull(investNum)) && (!"400".equals(investNum))) {
							int cIndex = Integer.parseInt(investNum) % Integer.parseInt(msNum);
							return CacheUtil.INSTANCE.getEhcache(planMsHashKey, String.valueOf(cIndex));
						}
					}
				} else {
					List creativeIDList = (List) CacheUtil.INSTANCE.getEhcacheList(planMsHashKey, "value");
					creativeIDList.remove("no");
					if ((creativeIDList != null) && (creativeIDList.size() > 0)) {
						if ("video".equals(gapm.isBanner)) {
							String creativeID = checkCreativeTimeLong(new ArrayList(creativeIDList), gapm);
							if (creativeID != null) {
								return creativeID;
							}
						} else {
							int cIndex = (int) (Math.random() * creativeIDList.size());
							return (String) creativeIDList.get(cIndex);
						}
					}
				}
			}

		}

		return checkAdTypeAndSize(sspCTList, planID, gapm);
	}

	public static String checkCreativeTimeLong(List creativeIDList, GeneralParaModel gapm) throws Exception {
		if (creativeIDList.size() == 0) {
			return null;
		}
		int index = (int) (Math.random() * creativeIDList.size());

		String creativeID = String.valueOf(creativeIDList.remove(index));

		String planMsHashKey = creativeID + "CreativeTag";

		log.info("gpm.maxDuration = " + gapm.maxDuration);
		if (gapm.maxDuration > 0L) {
			String timeLongStr = CacheUtil.INSTANCE.getEhcache(planMsHashKey, "timeLong");
			if ((timeLongStr != null) && (!"".equals(timeLongStr))) {
				int duration = Integer.parseInt(timeLongStr);
				if ((duration >= gapm.minDuration) && (duration <= gapm.maxDuration)) {
					return creativeID;
				}

			}

		}

		return checkCreativeTimeLong(creativeIDList, gapm);
	}

	public static String getWeightMa(Set<String> creativeIDWeight) {
		String[] wArr = new String[100];
		for (String weight : creativeIDWeight) {
			if ((weight == null) || ("hasWeight".equals(weight)))
				continue;
			int num = Integer.parseInt(weight);
			randomArr(wArr, num, 0);
		}

		int cIndex = (int) (Math.random() * 100.0D);
		return wArr[cIndex];
	}

	public static void randomArr(String[] wArr, int num, int count) {
		if (count == num)
			return;

		int cIndex = (int) (Math.random() * 100.0D);
		if (wArr[cIndex] == null) {
			wArr[cIndex] = String.valueOf(num);
			count++;
		}
		randomArr(wArr, num, count);
	}

	public static void sendLogToKafka(String respStr, String isExpose) {
		try {
			boolean isEStats = false;
			if ("yes".equals(isExpose)) {
				isEStats = true;
			}
			LogProducer ap = new LogProducer(respStr, 2, isEStats);
			ap.start();
		} catch (Exception e) {
			log.error("sendLogToKafka = " + e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public static GeneralResponseModel generalResponse(GeneralParaModel gpm, String cityName, String selectOrder,
			String createID) throws Exception {
		String mzUserCode = gpm.sspCode;
		StringBuffer reponseStr = new StringBuffer();

		jointPara(reponseStr, selectOrder);
		jointPara(reponseStr, gpm.impAdID + mzUserCode);

		jointPara(reponseStr, cityName);

		jointPara(reponseStr, gpm.userID);

		jointPara(reponseStr, gpm.isBanner);

		if (gpm.siteName != null) {
			jointPara(reponseStr, Base58.encode(gpm.siteName.getBytes()));
		}
		jointPara(reponseStr, gpm.requestID + mzUserCode);

		String planMsHashKey = createID + "CreativeTag";
		if (createID == null) {
			createID = checkAdTypeAndSize(new ArrayList(gpm.sspCreativeTypeList), selectOrder, gpm);
			planMsHashKey = createID + "CreativeTag";
		}
		String msID = CacheUtil.INSTANCE.getEhcache(planMsHashKey, "msID");
		jointPara(reponseStr, msID);
		jointPara(reponseStr, gpm.sspCode);
		jointPara(reponseStr, gpm.isApp);
		if ((gpm.buyWay == 4) || (gpm.buyWay == 6)) {
			jointPara(reponseStr, String.valueOf(System.currentTimeMillis()));
			reponseStr.append(gpm.appID);
			String dealHkey = gpm.appID + gpm.sspCode + "#DealFlag";
			String isExpose = CacheUtil.INSTANCE.getEhcache(dealHkey, "isExpose");
			if ((isExpose != null) && (!"".equals(isExpose))) {
				sendLogToKafka(reponseStr.toString(), isExpose);
			}
		} else {
			reponseStr.append(System.currentTimeMillis());
		}
		GeneralResponseModel grm = new GeneralResponseModel();
		grm.para = Base58.encode(reponseStr.toString().getBytes());
		String maPath = CacheUtil.INSTANCE.getEhcache(planMsHashKey, "maPath");
		log.info("maPath = " + maPath);
		String price = CacheUtil.INSTANCE.getEhcache(selectOrder, "price");
		String orderID = CacheUtil.INSTANCE.getEhcache(selectOrder, "orderID");
		String clickCheck = CacheUtil.INSTANCE.getEhcache(planMsHashKey, "clickCheck");
		String exposeCheck = CacheUtil.INSTANCE.getEhcache(planMsHashKey, "exposeCheck");
		String reachCheck = CacheUtil.INSTANCE.getEhcache(planMsHashKey, "reachCheck");
		String timeLong = CacheUtil.INSTANCE.getEhcache(planMsHashKey, "timeLong");

		log.info("creativeID = " + createID);
		String advertiserID = CacheUtil.INSTANCE.getEhcache(planMsHashKey, "advertiserID");
		String landPage = CacheUtil.INSTANCE.getEhcache(planMsHashKey, "landPage");
		String type = CacheUtil.INSTANCE.getEhcache(planMsHashKey, "type");
		if ("6".equals(type)) {
			grm.advertSlogan = CacheUtil.INSTANCE.getEhcache(planMsHashKey, "advertSlogan");
			grm.iconURL = CacheUtil.INSTANCE.getEhcache(planMsHashKey, "iconURL");
		}
		if (type != null) {
			type = (String) SysParams.creativeTypeProps.get(type + mzUserCode);
		}
		String category = CacheUtil.INSTANCE.getEhcache(planMsHashKey, "category");
		String maTitle = CacheUtil.INSTANCE.getEhcache(planMsHashKey, "maTitle");
		String maDescribe = CacheUtil.INSTANCE.getEhcache(planMsHashKey, "maDescribe");
		String openType = CacheUtil.INSTANCE.getEhcache(planMsHashKey, "openType");
		String sspCreativeID = CacheUtil.INSTANCE.getEhcache(planMsHashKey, "sspCreativeID");
		String sspMaPath = CacheUtil.INSTANCE.getEhcache(planMsHashKey, "sspMaPath");

		if (openType != null) {
			if ("2".equals(openType)) {
				String deepLink = CacheUtil.INSTANCE.getEhcache(planMsHashKey, "deepLink");
				if ((deepLink != null) && (!"".equals(deepLink))) {
					grm.text = deepLink;
				}
			}
			openType = (String) SysParams.openTypeProps.get(openType + mzUserCode);
		}

		grm.maPath = maPath;
		grm.price = price;
		grm.planID = selectOrder;
		grm.clickCheck = clickCheck;
		grm.exposeCheck = exposeCheck;
		grm.reachCheck = reachCheck;
		grm.timeLong = timeLong;
		grm.creativeID = createID;

		grm.landPage = landPage;
		if (type != null) {
			grm.type = type;
		}
		if (category != null) {
			grm.category = category;
		}
		if (advertiserID != null) {
			grm.advertiserID = advertiserID;
		}
		grm.maTitle = maTitle;
		grm.maDescribe = maDescribe;
		if (openType != null) {
			grm.openType = Integer.parseInt(openType);
		}
		if (sspCreativeID != null) {
			grm.sspCreativeID = sspCreativeID;
		}

		if (sspMaPath != null) {
			grm.sspMaPath = sspMaPath;
		}

		String orderIDHashKey = orderID + "UserOrderTags";
		String cmURL = CacheUtil.INSTANCE.getEhcache(orderIDHashKey, "cmURL");
		grm.ckmpUrl = cmURL;

		return grm;
	}

	public static void jointPara(StringBuffer reponseStr, String para) {
		if (para != null && !"".endsWith(para))
			reponseStr.append(para);
		else
			reponseStr.append("test");
		reponseStr.append(":");
	}

	public static String generalCheck(String planID, GeneralParaModel gapm, HashMap orderTagMap, Map creativeIDMap,
			String investIDListStr) {
		try {
			String creativeID = checkAdTypeAndSize(new ArrayList(gapm.sspCreativeTypeList), planID, gapm);

			if (creativeID == null) {
				log.info("计划 ：" + planID + " 检查创意和广告位尺寸以及时长不通过！！");
				return "false";
			}
			log.info("计划 ：" + planID + " 检查创意和广告位尺寸以及时长通过！！");
			creativeIDMap.put(gapm.requestID, creativeID);

			String planMsHashKey = creativeID + "CreativeTag";
			String openType = CacheUtil.INSTANCE.getEhcache(planMsHashKey, "openType");
			if ("2".equals(openType)) {
				if ((gapm.text != null) && (!"".equals(gapm.text))) {
					if (!gapm.text.contains("deeplink")) {
						log.info("计划 ：" + planID + " 检查deeplink不通过！！");
						return "false";
					}
				} else {
					log.info("计划 ：" + planID + " 检查deeplink不通过！！");
					return "false";
				}

			}

			long startTime = 0L;
			long endTime = 0L;
			String startTimeStr = CacheUtil.INSTANCE.getEhcache(planID, "startTime");
			String endTimeStr = CacheUtil.INSTANCE.getEhcache(planID, "endTime");

			if ((startTimeStr != null) && (!"".equals(startTimeStr))) {
				startTime = Long.parseLong(startTimeStr);
			}
			if ((endTimeStr != null) && (!"".equals(endTimeStr))) {
				endTime = Long.parseLong(endTimeStr);
			}

			long currentTime = System.currentTimeMillis();

			if ((startTime > 0L) && (endTime > 0L)) {
				if ((currentTime > endTime) || (currentTime < startTime)) {
					log.info("计划 ：" + planID + " 检查时间不通过！！");
					return "false";
				}
			} else {
				log.info("计划 ：" + planID + " 检查时间不通过！！");
				return "false";
			}
			log.info("计划 ：" + planID + " 检查时间通过！！");

			int exposLimit = 0;
			String exposObj = CacheUtil.INSTANCE.getEhcache(planID, "exposLimit");
			if (exposObj != null) {
				exposLimit = Integer.parseInt(exposObj);
			}
			int clickLimit = 0;
			String clickObj = CacheUtil.INSTANCE.getEhcache(planID, "clickLimit");
			if (clickObj != null) {
				clickLimit = Integer.parseInt(clickObj);
			}

			String selectDate = CacheUtil.INSTANCE.getEhcache(planID, "planSelectDate");
			if (exposLimit > 0) {
				String exposNumStr = JedisUtil.INSTANCE.getJedis()
						.get(planID + "#" + selectDate + "#" + gapm.isApp + "#" + "ExposNum");
				log.info("计划 ：" + planID + "22222222222222222223333333 exposNumHashKeys=" + planID + "#" + gapm.isApp
						+ "#" + "ExposNum");
				log.info("计划 ：" + planID + "22222222222222222223333333 exposNumStr=" + exposNumStr);
				if (exposNumStr != null) {
					int realExposNum = Integer.parseInt(exposNumStr);

					if (realExposNum >= exposLimit) {
						log.info("计划 ：" + planID + " 检查曝光数不通过！！ exposNumStr=" + exposNumStr);
						return "false";
					}
				}
			}

			log.info("计划 ：" + planID + " 检查曝光数通过！！");

			if (clickLimit > 0) {
				String clickNumStr = JedisUtil.INSTANCE.getJedis()
						.get(planID + "#" + selectDate + "#" + gapm.isApp + "#" + "ClickNum");
				log.info("22222222222222222223333333 clickNumStr=" + clickNumStr);
				if (clickNumStr != null) {
					int realclickNum = Integer.parseInt(clickNumStr);
					if (realclickNum >= clickLimit) {
						log.info("计划 ：" + planID + " 检查点击数不通过！！");
						return "false";
					}
				}
			}

			log.info("计划 ：" + planID + " 检查点击数通过！！");

			String ifPriceAndNum = CacheUtil.INSTANCE.getEhcache(planID, "ifPriceAndNum");
			log.info("计划 ：" + planID + " 是否检查推送比！！=" + ifPriceAndNum);
			if ("yes".endsWith(ifPriceAndNum)) {
				if ((gapm.buyWay == 4) || (gapm.buyWay == 6)) {
					String dealID = gapm.appID;
					if (gapm.buyWay == 6) {
						String[] ids = dealID.split("#");
						dealID = ids[1];
					}
					String dealHkey = dealID + gapm.sspCode + "#DealFlag";
					String fillRatio = CacheUtil.INSTANCE.getEhcache(dealHkey, "fillRatio");
					log.info("计划 ：" + planID + " 是fillRatio=" + fillRatio);
					long backNum = 0L;
					long dealExposNumInt = 1L;
					String isExpose = CacheUtil.INSTANCE.getEhcache(dealHkey, "isExpose");
					log.info("计划 ：" + planID + " 是isExpose=" + isExpose);
					if ("yes".equals(isExpose)) {
						String reqNum = JedisUtil.INSTANCE.getJedis().get(dealHkey + "#DealReqNum");
						String respNum = JedisUtil.INSTANCE.getJedis().get(dealHkey + "#DealRespNum");
						if (checkStrNull(reqNum)) {
							if (checkStrNull(respNum)) {
								backNum = Long.parseLong(reqNum) - Long.parseLong(respNum);
							} else {
								backNum = Long.parseLong(reqNum);
							}

							if (backNum < 0L) {
								backNum = 0L;
							}
						}
						String dealExposNum = JedisUtil.INSTANCE.getJedis().get(dealHkey + "#DealExposNum");
						if (checkStrNull(dealExposNum)) {
							dealExposNumInt = Long.parseLong(dealExposNum);
						}
					}
					boolean ifFill = PDBDealUtil.ifFill(dealID, fillRatio, gapm.sspCode, isExpose, dealExposNumInt,
							backNum);
					log.info("计划 ：" + planID + " 是ifFill=" + ifFill);
					if (ifFill) {
						return "fill";
					}
				}
			}
			String orderID = CacheUtil.INSTANCE.getEhcache(planID, "orderID");
			String orderIDHashKey = orderID + "UserOrderTags";
			String wapStr = CacheUtil.INSTANCE.getEhcache(orderIDHashKey, "isWap");
			if (!"allWap".equals(wapStr)) {
				if ((gapm.isWap != null) && (!"".equals(gapm.isWap))) {
					if (!gapm.isWap.equals(wapStr)) {
						log.info("计划 ：" + planID + " 检查wap流量不通过！！");
						return "false";
					}
				}
			}

			log.info("计划 ：" + planID + " 检查wap流量通过！！");

			String siteName = gapm.siteName;

			if (checkStrNull(siteName)) {
				if (siteName.toLowerCase().startsWith("http")) {
					siteName = getDomainName(siteName);
				} else {
					siteName = gapm.siteName + gapm.sspCode;
				}

			}

			boolean wbCheck = dealWBName(planID, gapm.pageVertical, siteName, gapm.sspCode);
			if (!wbCheck) {
				log.info("计划 ：" + planID + " 检查黑白名单不通过！！");
				return "false";
			}
			log.info("计划 ：" + planID + " 检查黑白名单通过！！");

			String adrichDT = CacheUtil.INSTANCE.getEhcache(planID, "displayType");
			if ((adrichDT != null) && (!"".endsWith(adrichDT))) {
				String myDisplayType = (String) SysParams.displayTypeProps.get(gapm.displayType + gapm.sspCode);

				if (myDisplayType != null) {
					if (!adrichDT.contains(myDisplayType)) {
						log.info("计划 ：" + planID + " 检查展示类型不通过！！");
						return "false";
					}
				} else {
					log.info("计划 ：" + planID + " 检查展示类型不通过！！");
					return "false";
				}
			}
			log.info("计划 ：" + planID + " 检查展示类型通过！！");

			String priceStr = CacheUtil.INSTANCE.getEhcache(planID, "price");
			if ((priceStr != null) && (!"".equals(priceStr))) {
				int price = (int) Double.parseDouble(priceStr);
				if (gapm.miniPrice > price) {
					log.info("计划 ：" + planID + " 检查价格不通过！！ 出价低于最低价格， miniPrice=" + gapm.miniPrice);
					return "false";
				}
				log.info("计划 ：" + planID + " 检查价格通过！！");
			} else {
				log.info("计划 ：" + planID + " 检查价格不通过！！");
				return "false";
			}

			String hasTags = CacheUtil.INSTANCE.getEhcache(orderIDHashKey, "hasTags");
			String userID = gapm.userID;

			if ("yes".equals(hasTags)) {
				String orderTagCheckResult = (String) orderTagMap.get(orderID);
				if (orderTagCheckResult != null) {
					if ("false".equals(orderTagCheckResult)) {
						log.info("计划 ：" + planID + " 检查用户标签不通过！！");
						return "false";
					}

				} else if ((userID != null) && (!"".equals(userID))) {
					boolean checkReust = false;
					String isOnLine = CacheUtil.INSTANCE.getEhcache(planID, "isOnLine");
					if ("2".equals(isOnLine)) {
						checkReust = tagCheck(orderIDHashKey, userID, planID, gapm.isApp, gapm.sspCode, gapm.osType);
					} else {
						String dmpTaType = CacheUtil.INSTANCE.getEhcache(orderIDHashKey, "dmpTaType");

						if ("2".equals(dmpTaType)) {
							String localProjectID = CacheUtil.INSTANCE.getEhcache(planID, "localProjectID");
							checkReust = checkLocalUserIFExsit(localProjectID, userID);
						} else {
							String dmpCodeArr = CacheUtil.INSTANCE.getEhcache(orderIDHashKey, "dmpCodeArr");
							checkReust = checkLocalUserIFMatch(dmpCodeArr, gapm.isApp, userID, orderIDHashKey,
									gapm.sspCode);
						}

					}

					log.info("计划 ：" + planID + " 检查用户标签结果 = " + checkReust);
					if (!checkReust) {
						log.info("计划 ：" + planID + " 检查用户标签不通过！！");
						orderTagMap.put(orderID, "false");
						return "false";
					}

					orderTagMap.put(orderID, "true");
				} else {
					orderTagMap.put(orderID, "false");
					log.info("计划 ：" + planID + " 检查用户标签不通过！！");
					return "false";
				}

			}

			log.info("计划 ：" + planID + " 检查用户标签通过！！");

			String isInvest = CacheUtil.INSTANCE.getEhcache(planID, "isInvest");
			if ("yes".equals(isInvest)) {
				if (gapm.userID != null) {
					log.info("userID = " + userID);
					if ((userID != null) && (!"".equals(userID))) {
						String investNum = CacheUtil.INSTANCE.getEhcache(planID, "investNum");
						if (investNum == null) {
							log.info("计划 ：" + planID + " 检查追投通过！！");

							return "true";
						}

						String investID = CacheUtil.INSTANCE.getEhcache(planID, "investID");
						log.info("检查追投的订单 investNum= " + investNum);
						log.info("检查追投的订单 investID= " + investID + " userID = " + userID);
						log.info("检查追投的订单 investIDListStr= " + investIDListStr);
						if ((investIDListStr != null) && (investIDListStr.contains(investID))) {
							String justExposed = CacheUtil.INSTANCE.getEhcache(planID, "justExposed");
							log.info("检查追投的订单 justExposed= " + justExposed);
							if ("yes".equals(justExposed)) {
								String[] investListArr = investIDListStr.split("#");
								if (!investListArr[0].contains(investID)) {
									log.info("计划 ：" + planID + " 检查追投再追投不通过！！");
									return "false";
								}
							}
						} else {
							log.info("计划 ：" + planID + " 检查追投不通过！！");
							return "false";
						}

					} else {
						log.info("计划 ：" + planID + " 检查追投不通过！！");
						return "false";
					}
				} else {
					log.info("计划 ：" + planID + " 检查追投不通过！！");
					return "false";
				}

			}

			log.info("计划 ：" + planID + " 检查追投通过！！");

			log.info("订单 ：" + orderID + " 检查通过！！");

			return "true";
		} catch (Exception e) {
			log.error("generalCheck = " + e.getMessage(), e);
			e.printStackTrace();
		}

		return "false";
	}

	public static boolean dealWBName(String planID, String siteType, String siteName, String sspCode) {
		try {
			String wbFlag = CacheUtil.INSTANCE.getEhcache(planID, "wbFlag");
			if ("3".equals(wbFlag)) {
				return true;
			}

			log.info("页面分类为 = " + siteType);
			log.info("wbFlag = " + wbFlag);
			String mType = null;
			if ((siteType != null) && (!"".equals(siteType))) {
				mType = (String) SysParams.pageTagMap.get(siteType + sspCode);
			}
			log.info("wbFlag adrich页面分类为mType = " + mType);

			if ("1".equals(wbFlag)) {
				String wmcnStr = CacheUtil.INSTANCE.getEhcache(planID, "wmcnStr");
				String wdNameListHashKey = CacheUtil.INSTANCE.getEhcache(planID, "wdNameListHashKey");
				if ((wmcnStr != null) && (!"".equals(wmcnStr))) {
					if ((mType != null) && (!"".equals(mType))) {
						if (!wmcnStr.contains(mType)) {
							if ((wdNameListHashKey != null) && (!"".equals(wdNameListHashKey))) {
								if (CacheUtil.INSTANCE.getEhcache(wdNameListHashKey, siteName) == null) {
									return false;
								}
							} else {
								return false;
							}
						}
					} else {
						return false;
					}

				} else if ((wdNameListHashKey != null) && (!"".equals(wdNameListHashKey))) {
					if (CacheUtil.INSTANCE.getEhcache(wdNameListHashKey, siteName) == null) {
						return false;
					}

				}

			} else if ("2".equals(wbFlag)) {
				String bmcnStr = CacheUtil.INSTANCE.getEhcache(planID, "bmcnStr");
				String bdNameListHashKey = CacheUtil.INSTANCE.getEhcache(planID, "bdNameListHashKey");
				if ((bmcnStr != null) && (!"".equals(bmcnStr))) {
					if ((mType != null) && (!"".equals(mType))) {
						if (bmcnStr.contains(mType)) {
							return false;
						}

						if ((bdNameListHashKey != null) && (!"".equals(bdNameListHashKey))) {
							if (CacheUtil.INSTANCE.getEhcache(bdNameListHashKey, siteName) != null) {
								return false;
							}

						}

					}

				} else if ((bdNameListHashKey != null) && (!"".equals(bdNameListHashKey))) {
					if (CacheUtil.INSTANCE.getEhcache(bdNameListHashKey, siteName) != null) {
						return false;
					}

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error("dealWBName = " + e.getMessage(), e);
			return false;
		}
		return true;
	}

	public static int parsePesonTag(List baiduTagList, String dmpCode, String orderHashKey) throws Exception {
		return TaDealUtil.dmpTaCheck(dmpCode, baiduTagList, orderHashKey);
	}

	public static Map parsePesonTag(List baiduTagList, String dmpCode) throws Exception {
		Map userTagObj = new HashMap();
		List mzIndustryTags = new ArrayList();
		for (int i = 0; i < baiduTagList.size(); i++) {
			String tagStr = baiduTagList.get(i).toString();
			List adrichTagList = (List) CacheUtil.INSTANCE.getEhcacheList("SSPAndAdrichTagRel",
					(new StringBuilder(String.valueOf(tagStr))).append("_").append(dmpCode).toString());
			log.info((new StringBuilder("adrich adrichTagList = ")).append(adrichTagList).toString());
			if (adrichTagList != null && adrichTagList.size() > 0) {
				for (Iterator iterator = adrichTagList.iterator(); iterator.hasNext();) {
					String adrichTag = (String) iterator.next();
					if (adrichTag != null && !"".endsWith(adrichTag)) {
						int tag = Integer.parseInt(adrichTag);
						if (tag > 1000010000 && tag < 1000020000) {
							List sexList = (List) userTagObj.get("sex");
							if (sexList == null) {
								sexList = new ArrayList();
								userTagObj.put("sex", sexList);
							}
							sexList.add(adrichTag);
						} else if (tag > 1000020000 && tag < 1000030000) {
							List ageList = (List) userTagObj.get("age");
							if (ageList == null) {
								ageList = new ArrayList();
								userTagObj.put("age", ageList);
							}
							ageList.add(adrichTag);
						} else if (tag > 1000030000 && tag < 1000040000) {
							List incomeList = (List) userTagObj.get("income");
							if (incomeList == null) {
								incomeList = new ArrayList();
								userTagObj.put("income", incomeList);
							}
							incomeList.add(adrichTag);
						} else if (tag > 1000040000 && tag < 1000050000) {
							List educationList = (List) userTagObj.get("education");
							if (educationList == null) {
								educationList = new ArrayList();
								userTagObj.put("education", educationList);
							}
							educationList.add(adrichTag);
						} else if (tag > 1000050000 && tag < 1000060000) {
							List stageList = (List) userTagObj.get("stage");
							if (stageList == null) {
								stageList = new ArrayList();
								userTagObj.put("stage", stageList);
							}
							stageList.add(adrichTag);
						} else if (tag > 1000060000 && tag < 1000070000) {
							List professList = (List) userTagObj.get("profess");
							if (professList == null) {
								professList = new ArrayList();
								userTagObj.put("profess", professList);
							}
							professList.add(adrichTag);
						} else if (tag > 2000010000 && tag < 2000020000)
							mzIndustryTags.add(adrichTag);
					}
				}

			}
		}

		userTagObj.put("orderIndustryTags", mzIndustryTags);
		return userTagObj;
	}

	public static boolean checkPersonTags(String orderIDHashKey, Map userTagsObj) throws Exception {
		String personTags = CacheUtil.INSTANCE.getEhcache(orderIDHashKey, "personTags");
		if ("yes".equals(personTags)) {
			if (userTagsObj == null) {
				return false;
			}

			String orderSexList = CacheUtil.INSTANCE.getEhcache(orderIDHashKey, "sex");

			if ((orderSexList != null) && (orderSexList.length() > 0)) {
				boolean sexCheck = comparePersonTag("sex", userTagsObj, orderSexList);
				if (!sexCheck) {
					log.info("44444444444444" + orderIDHashKey + " 性别检查不通过！！");
					return false;
				}
			}
			log.info("44444444444444" + orderIDHashKey + " 性别检查通过！！");

			String ageList = CacheUtil.INSTANCE.getEhcache(orderIDHashKey, "age");
			log.info("444444444111111" + orderIDHashKey + " ageList=" + ageList);
			if ((ageList != null) && (ageList.length() > 0)) {
				log.info("444444444111111" + orderIDHashKey + " otheruserTagsObj=" + userTagsObj.get("age"));

				boolean ageCheck = comparePersonTag("age", userTagsObj, ageList);
				if (!ageCheck) {
					log.info("44444444444444" + orderIDHashKey + " 年龄检查不通过！！");
					return false;
				}
			}
			log.info("44444444444444" + orderIDHashKey + " 年龄检查通过！！");

			String incomeList = CacheUtil.INSTANCE.getEhcache(orderIDHashKey, "income");
			if ((incomeList != null) && (incomeList.length() > 0)) {
				boolean incomeCheck = comparePersonTag("income", userTagsObj, incomeList);
				if (!incomeCheck) {
					log.info("44444444444444" + orderIDHashKey + " 收入检查不通过！！");
					return false;
				}
			}
			log.info("44444444444444" + orderIDHashKey + " 收入检查通过！！");

			String educationList = CacheUtil.INSTANCE.getEhcache(orderIDHashKey, "education");
			log.info("44444444433333" + orderIDHashKey + " educationList=" + educationList);

			if ((educationList != null) && (educationList.length() > 0)) {
				boolean educationCheck = comparePersonTag("education", userTagsObj, educationList);
				if (!educationCheck) {
					log.info("44444444444444 " + orderIDHashKey + " 教育检查不通过！！");
					return false;
				}
			}
			log.info("44444444444444" + orderIDHashKey + " 教育检查通过！！");
			String professList = CacheUtil.INSTANCE.getEhcache(orderIDHashKey, "profess");
			if ((professList != null) && (professList.length() > 0)) {
				boolean professCheck = comparePersonTag("profess", userTagsObj, professList);
				if (!professCheck) {
					return false;
				}
			}
			String stageList = CacheUtil.INSTANCE.getEhcache(orderIDHashKey, "stage");

			if ((stageList != null) && (stageList.length() > 0)) {
				boolean stageCheck = comparePersonTag("stage", userTagsObj, stageList);
				if (!stageCheck) {
					return false;
				}
			}
		}

		return true;
	}

	public static boolean comparePersonTag(String tagName, Map userTagsObj, String adrichTagStr) {
		List tagList = (List) userTagsObj.get(tagName);
		if (tagList != null && tagList.size() > 0) {
			String adrichTagArr[] = adrichTagStr.split(",");
			for (Iterator iterator = tagList.iterator(); iterator.hasNext();) {
				String otherTag = (String) iterator.next();
				String as[];
				int j = (as = adrichTagArr).length;
				for (int i = 0; i < j; i++) {
					String adrichTag = as[i];
					if (otherTag.equals(adrichTag))
						return true;
				}

			}

			return false;
		} else {
			return false;
		}
	}

	public static boolean checkOrderTag(GeneralParaModel gapm, HashMap orderTagMap, String orderID) throws Exception {
		String osTag = (String) orderTagMap.get(orderID + "OSTypeTag");
		if (osTag != null) {
			if ("false".equals(osTag)) {
				return false;
			}
		} else {
			String orderIDHashKey = orderID + "UserOrderTags";
			String osTypeList = CacheUtil.INSTANCE.getEhcache(orderIDHashKey, "osType");
			log.info("瑞恩需要的操作系统类型为 = " + osTypeList);
			if ((osTypeList != null) && (osTypeList.length() > 0)) {
				log.info("ssp的操作系统类型为 = " + gapm.osType + gapm.sspCode);
				String osType = (String) SysParams.osTypeProps.get(gapm.osType + gapm.sspCode);
				if (osType != null) {
					if (!osTypeList.contains(osType)) {
						orderTagMap.put(orderID + "OSTypeTag", "false");
						return false;
					}

					orderTagMap.put(orderID + "OSTypeTag", "true");
				} else {
					orderTagMap.put(orderID + "OSTypeTag", "false");
					return false;
				}
			} else {
				orderTagMap.put(orderID + "OSTypeTag", "true");
			}
		}

		String netTag = (String) orderTagMap.get(orderID + "NetTypeTag");
		if (netTag != null) {
			if ("false".equals(netTag)) {
				return false;
			}
		} else {
			String orderIDHashKey = orderID + "UserOrderTags";
			String netTypeList = CacheUtil.INSTANCE.getEhcache(orderIDHashKey, "netType");
			log.info("瑞恩需要的网络类型为 = " + netTypeList);
			if ((netTypeList != null) && (netTypeList.length() > 0)) {
				log.info("ssp的网络类型为 = " + gapm.netType + gapm.sspCode);
				String netType = (String) SysParams.netTypeProps.get(gapm.netType + gapm.sspCode);
				if (netType != null) {
					if (!netTypeList.contains(netType)) {
						orderTagMap.put(orderID + "NetTypeTag", "false");
						return false;
					}

					orderTagMap.put(orderID + "NetTypeTag", "true");
				} else {
					orderTagMap.put(orderID + "NetTypeTag", "false");
					return false;
				}
			} else {
				orderTagMap.put(orderID + "NetTypeTag", "true");
			}
		}

		return true;
	}

	public static boolean tagCheck(String orderIDHashKey, String userID, String planID, String pcFlag, String sspCode,
			String os) throws Exception {
		String isOr = CacheUtil.INSTANCE.getEhcache(orderIDHashKey, "tagIsOr");

		log.info("33333333333333 isOr=" + isOr);
		Map uesrTagObject = null;

		boolean checkResult = false;

		String dmpCode = CacheUtil.INSTANCE.getEhcache(planID, "dmpCode");
		if ((dmpCode != null) && (!"".equals(dmpCode)) && (!"false".equals(dmpCode))) {
			String hasTagDMP = CacheUtil.INSTANCE.getEhcache(planID, "hasTagDMP");
			DMPCheckerFactory dmpcf = new DMPCheckerFactory();
			DMPChecker dmpc = dmpcf.createDMPChecker(dmpCode);
			DMPModel dmpM = new DMPModel();
			dmpM.setUserID(userID);

			String dmpProjectID = CacheUtil.INSTANCE.getEhcache(planID, "dmpProjectID");

			dmpM.setOrderID(dmpProjectID);
			String token = CacheUtil.INSTANCE.getEhcache(planID, "token");
			dmpM.setToken(token);

			if ("IsPCMark".equals(pcFlag)) {
				dmpM.setIsPC(1);
			} else if ("IsAPPMark".equals(pcFlag)) {
				dmpM.setIsPC(2);
			} else if ("IsOTTMark".equals(pcFlag)) {
				dmpM.setIsPC(3);
			}

			if ("true".equals(hasTagDMP)) {
				List tagList = dmpc.getDmpTags(dmpM);
				if ((tagList != null) && (tagList.size() > 0)) {
					StringDealUtils.printUserLog(userID, pcFlag, dmpCode, sspCode, tagList);
					int checkInt = parsePesonTag(tagList, dmpCode, orderIDHashKey);
					if (1 == checkInt) {
						checkResult = true;
					} else if (3 == checkInt) {
						String unTa = CacheUtil.INSTANCE.getEhcache(orderIDHashKey, "unTa");
						if ("90000".equals(unTa)) {
							checkResult = true;
						}
					}
				}

			} else {
				log.info("开始无返回标签的 dmp检查 ！dmpc = " + dmpc + " dmpCode = " + dmpCode);
				try {
					if ("002".equals(dmpCode)) {
						checkResult = checkRemoteUserIFExsit(dmpProjectID, userID, token);
					} else if ("003".equals(dmpCode)) {
						dmpM.setSspCode(sspCode);
						checkResult = dmpc.checkDmpTags(dmpM);
					}
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}

			}

		}

		log.info("33333333333333111111checkPersonTags  checkResult=" + checkResult);

		if ("yes".equals(isOr)) {
			if (checkResult) {
				return true;
			}

			String hasIndustryTags = CacheUtil.INSTANCE.getEhcache(orderIDHashKey, "hasIndustryTags");
			if ("yes".equals(hasIndustryTags)) {
				String orderIndustryTags = CacheUtil.INSTANCE.getEhcache(orderIDHashKey, "orderIndustryTags");
				Set orderTagSet = new HashSet(Arrays.asList(orderIndustryTags.split(":")));
				int orderTagNum = orderTagSet.size();
				List userindustryTagsList = (List) uesrTagObject.get("orderIndustryTags");
				if ((userindustryTagsList != null) && (userindustryTagsList.size() > 0)) {
					Set userTagSet = new HashSet(userindustryTagsList);
					int userTagNum = userTagSet.size();
					userTagNum += orderTagNum;
					userTagSet.addAll(orderTagSet);

					return userTagNum > userTagSet.size();
				}

				return false;
			}

			return false;
		}

		if (checkResult) {
			String hasIndustryTags = CacheUtil.INSTANCE.getEhcache(orderIDHashKey, "hasIndustryTags");
			if ("yes".equals(hasIndustryTags)) {
				String orderIndustryTags = CacheUtil.INSTANCE.getEhcache(orderIDHashKey, "orderIndustryTags");
				Set orderTagSet = new HashSet(Arrays.asList(orderIndustryTags.split(":")));
				int orderTagNum = orderTagSet.size();
				List userindustryTagsList = (List) uesrTagObject.get("orderIndustryTags");
				if ((userindustryTagsList != null) && (userindustryTagsList.size() > 0)) {
					Set userTagSet = new HashSet(userindustryTagsList);
					int userTagNum = userTagSet.size();
					userTagNum += orderTagNum;
					userTagSet.addAll(orderTagSet);

					return userTagNum > userTagSet.size();
				}

				return false;
			}

			return true;
		}

		return false;
	}

	public static boolean checkLBS(String planID, long lon, long lat) {
		try {
			String lbsLon = CacheUtil.INSTANCE.getEhcache(planID, "lbsLon");
			if (lbsLon != null) {
				String lbsLat = CacheUtil.INSTANCE.getEhcache(planID, "lbsLat");
				String lbsGap = CacheUtil.INSTANCE.getEhcache(planID, "lbsGap");
				long adrichLon = Long.parseLong(lbsLon);
				long adrichLat = Long.parseLong(lbsLat);
				long adrichGap = Long.parseLong(lbsGap);

				return (lon >= adrichLon - adrichGap) && (lon <= adrichLon + adrichGap)
						&& (lat >= adrichLat - adrichGap) && (lat <= adrichLat + adrichGap);
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error("checkLBS = " + e.getMessage(), e);
			return false;
		}
		return true;
	}

	public static boolean checkStrNull(String str) {
		return (str != null) && (!"".equals(str));
	}

	public static String getDomainName(String domainName) {
		if (checkStrNull(domainName)) {
			domainName = domainName.toLowerCase().replace("http://", "");
			int index = domainName.indexOf("/");
			if (index > 0) {
				domainName = domainName.substring(0, index);
			}
			return domainName;
		}

		return domainName;
	}

	public static boolean checkLocalUserIFExsit(String projectID, String userID) {
		String dmpStr = NettyUtil.sendDMPData("noTaValue#" + projectID + "#" + userID.toUpperCase());

		return (dmpStr != null) && ("true".equals(dmpStr));
	}

	public static boolean checkLocalUserIFMatch(String dmpCodeArr, String isPC, String userID, String orderHashKey,
			String sspCode) throws Exception {
		int intRes = TaDealUtil.dmpTaCheck(dmpCodeArr, isPC, userID, orderHashKey, sspCode);

		if (1 == intRes) {
			return true;
		}
		if (3 == intRes) {
			String unTa = CacheUtil.INSTANCE.getEhcache(orderHashKey, "unTa");
			if ("90000".equals(unTa)) {
				return true;
			}
		}

		return false;
	}

	public static boolean checkRemoteUserIFExsit(String mzTID, String userID, String token) {
		List mzPCUserTag = MiaoZhenDMPUtil.getUserTagNo90000(mzTID, userID.toUpperCase(), token);
		log.info("mzPCUserTag = " + mzPCUserTag + "   mzTID = " + mzTID);

		return mzPCUserTag.size() > 0;
	}
}