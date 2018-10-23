package com.dm.adrich.communicate.bid.util;

import com.dm.adrich.communicate.bid.GeneralParaModel;
import com.dm.adrich.communicate.bid.GeneralResponseModel;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;
import java.util.List;

public class AdrichRequestHttpUtil {
    public static final String ADVERTISER_ID_NO_BID = "adrich3009";

    public static JSONObject getAdrichRequestJson(GeneralParaModel gpm) {
        JSONObject requestJson = new JSONObject();
        requestJson.put("minDuration", Long.valueOf(gpm.minDuration));
        requestJson.put("maxDuration", Long.valueOf(gpm.maxDuration));
        requestJson.put("maSize", gpm.maSize);
        requestJson.put("ip", gpm.ip);
        requestJson.put("impAdID", gpm.impAdID);
        requestJson.put("isBanner", gpm.isBanner);
        requestJson.put("isApp", gpm.isApp);
        requestJson.put("startTime", Long.valueOf(gpm.startTime));
        requestJson.put("siteName", gpm.siteName);
        requestJson.put("sspCode", gpm.sspCode);
        requestJson.put("requestID", gpm.requestID);
        requestJson.put("miniPrice", Integer.valueOf(gpm.miniPrice));
        requestJson.put("displayType", gpm.displayType);
        requestJson.put("pageVertical", gpm.pageVertical);
        requestJson.put("userID", gpm.userID);
        requestJson.put("deviceType", gpm.deviceType);
        requestJson.put("osType", gpm.osType);
        requestJson.put("netType", gpm.netType);
        requestJson.put("lat", Long.valueOf(gpm.lat));
        requestJson.put("lon", Long.valueOf(gpm.lon));
        requestJson.put("buyWay", Integer.valueOf(gpm.buyWay));
        requestJson.put("appID", gpm.appID);
        requestJson.put("text", gpm.text);
        requestJson.put("isWap", gpm.isWap);
        requestJson.put("sspCreativeTypeList", gpm.sspCreativeTypeList);
        requestJson.put("userAttributeList", gpm.userAttributeList);
        return requestJson;
    }

    public static GeneralParaModel getGeneralParaModel(JSONObject adrichRequestJson) {
        GeneralParaModel gpm = new GeneralParaModel();
        gpm.minDuration = ((Long) adrichRequestJson.get("minDuration")).longValue();
        gpm.maxDuration = ((Long) adrichRequestJson.get("maxDuration")).longValue();
        gpm.maSize = ((String) adrichRequestJson.get("maSize"));
        gpm.ip = ((String) adrichRequestJson.get("ip"));
        gpm.impAdID = ((String) adrichRequestJson.get("impAdID"));
        gpm.isBanner = ((String) adrichRequestJson.get("isBanner"));
        gpm.isApp = ((String) adrichRequestJson.get("isApp"));
        gpm.startTime = new Long(adrichRequestJson.get("startTime").toString()).longValue();
        gpm.siteName = ((String) adrichRequestJson.get("siteName"));
        gpm.sspCode = ((String) adrichRequestJson.get("sspCode"));
        gpm.requestID = ((String) adrichRequestJson.get("requestID"));
        gpm.miniPrice = new Integer(adrichRequestJson.get("miniPrice").toString()).intValue();
        gpm.displayType = ((String) adrichRequestJson.get("displayType"));
        gpm.pageVertical = ((String) adrichRequestJson.get("pageVertical"));
        gpm.userID = ((String) adrichRequestJson.get("userID"));
        gpm.deviceType = ((String) adrichRequestJson.get(""));
        gpm.osType = ((String) adrichRequestJson.get("deviceType"));
        gpm.netType = ((String) adrichRequestJson.get("netType"));
        gpm.lat = (adrichRequestJson.get("lat") == null ? null : new Long(adrichRequestJson.get("lat").toString())).longValue();
        gpm.lon = (adrichRequestJson.get("lon") == null ? null : new Long(adrichRequestJson.get("lon").toString())).longValue();
        gpm.buyWay = (adrichRequestJson.get("buyWay") == null ? null : new Integer(adrichRequestJson.get("buyWay").toString())).intValue();
        gpm.appID = ((String) adrichRequestJson.get("appID"));
        gpm.text = ((String) adrichRequestJson.get("text"));
        gpm.isWap = ((String) adrichRequestJson.get("isWap"));
        gpm.sspCreativeTypeList = ((List) adrichRequestJson.get("sspCreativeTypeList"));
        gpm.userAttributeList = ((List) adrichRequestJson.get("userAttributeList"));
        return gpm;
    }

    public static JSONObject getAdrichResponseJson(GeneralResponseModel grm) {
        JSONObject responseJson = new JSONObject();
        responseJson.put("planID", grm.planID);
        responseJson.put("exposeCheck", grm.exposeCheck);
        responseJson.put("clickCheck", grm.clickCheck);
        responseJson.put("reachCheck", grm.reachCheck);
        responseJson.put("price", grm.price);
        responseJson.put("maPath", grm.maPath);
        responseJson.put("timeLong", grm.timeLong);
        responseJson.put("creativeID", grm.creativeID);
        responseJson.put("targetURL", grm.targetURL);
        responseJson.put("type", grm.type);
        responseJson.put("category", grm.category);
        responseJson.put("para", grm.para);
        responseJson.put("advertiserID", grm.advertiserID);
        responseJson.put("landPage", grm.landPage);
        responseJson.put("openType", Integer.valueOf(grm.openType));
        responseJson.put("maTitle", grm.maTitle);
        responseJson.put("maDescribe", grm.maDescribe);
        responseJson.put("advertSlogan", grm.advertSlogan);
        responseJson.put("wordLinkTitle", grm.wordLinkTitle);
        responseJson.put("wordLinkURL", grm.wordLinkURL);
        responseJson.put("iconType", grm.iconType);
        responseJson.put("iconURL", grm.iconURL);
        responseJson.put("appName", grm.appName);
        responseJson.put("packageName", grm.packageName);
        responseJson.put("appPhone", grm.appPhone);
        responseJson.put("packageSize", grm.packageSize);
        responseJson.put("appIntroduce", grm.appIntroduce);
        responseJson.put("sspCreativeID", grm.sspCreativeID);
        responseJson.put("sspMaPath", grm.sspMaPath);
        responseJson.put("text", grm.text);
        responseJson.put("ckmpUrl", grm.ckmpUrl);
        return responseJson;
    }

    public static GeneralResponseModel getGeneralResponseModel(JSONObject adrichResponseJson) {
        GeneralResponseModel grm = new GeneralResponseModel();
        grm.planID = ((String) adrichResponseJson.get("planID"));
        grm.exposeCheck = ((String) adrichResponseJson.get("exposeCheck"));
        grm.clickCheck = ((String) adrichResponseJson.get("clickCheck"));
        grm.reachCheck = ((String) adrichResponseJson.get("reachCheck"));
        grm.price = ((String) adrichResponseJson.get("price"));
        grm.maPath = ((String) adrichResponseJson.get("maPath"));
        grm.timeLong = ((String) adrichResponseJson.get("timeLong"));
        grm.creativeID = ((String) adrichResponseJson.get("creativeID"));
        grm.targetURL = ((String) adrichResponseJson.get("targetURL"));
        grm.type = ((String) adrichResponseJson.get("type"));
        grm.category = ((String) adrichResponseJson.get("category"));
        grm.para = ((String) adrichResponseJson.get("para"));
        grm.advertiserID = ((String) adrichResponseJson.get("advertiserID"));
        grm.landPage = ((String) adrichResponseJson.get("landPage"));
        grm.openType = (adrichResponseJson.get("openType") == null ? null : new Integer(adrichResponseJson.get("openType").toString())).intValue();
        grm.maTitle = ((String) adrichResponseJson.get("maTitle"));
        grm.maDescribe = ((String) adrichResponseJson.get("maDescribe"));
        grm.advertSlogan = ((String) adrichResponseJson.get("advertSlogan"));
        grm.wordLinkTitle = ((String) adrichResponseJson.get("wordLinkTitle"));
        grm.wordLinkURL = ((String) adrichResponseJson.get("wordLinkURL"));
        grm.iconType = ((String) adrichResponseJson.get("iconType"));
        grm.iconURL = ((String) adrichResponseJson.get("iconURL"));
        grm.appName = ((String) adrichResponseJson.get("appName"));
        grm.packageName = ((String) adrichResponseJson.get("packageName"));
        grm.appPhone = ((String) adrichResponseJson.get("appPhone"));
        grm.packageSize = ((String) adrichResponseJson.get("packageSize"));
        grm.appIntroduce = ((String) adrichResponseJson.get("appIntroduce"));
        grm.sspCreativeID = ((String) adrichResponseJson.get("sspCreativeID"));
        grm.sspMaPath = ((String) adrichResponseJson.get("sspMaPath"));
        grm.text = ((String) adrichResponseJson.get("text"));
        grm.ckmpUrl = ((String) adrichResponseJson.get("ckmpUrl"));
        return grm;
    }

    public static GeneralResponseModel getGeneralResponseModel(String adrichResponseJson) {
        GeneralResponseModel grm = null;
        try {
            JSONParser parser = new JSONParser();
            JSONObject dbObject = (JSONObject) parser.parse(adrichResponseJson);
            grm = getGeneralResponseModel(dbObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return grm;
    }

    public static GeneralResponseModel getNoBidDefaultResponse(GeneralParaModel gpm) {
        GeneralResponseModel grm = new GeneralResponseModel();
        grm.advertiserID = "adrich3009";
        return grm;
    }

    public static void main(String[] args) {
        List sspCreativeTypeList = new ArrayList();
        sspCreativeTypeList.add("5");
        sspCreativeTypeList.add("2");
        GeneralParaModel gpm = new GeneralParaModel(14L, 15L, "640x480", "211.3.3.3", "ad01", "video", "IsAPPMark",
                1462118400000L, "爱奇艺", "017", "011111111", 200, "3001", "g", "869552020076337", "1", "2", "2", 0L, 177L,
                1, null, null, null, sspCreativeTypeList, null);
        JSONObject jb = getAdrichRequestJson(gpm);
        System.out.println(jb);
        GeneralParaModel gpm2 = getGeneralParaModel(jb);
        JSONObject jb2 = getAdrichRequestJson(gpm2);
        System.out.println(jb2);
        System.out.println("jb2.toJSONString:" + jb2.toJSONString());
        GeneralResponseModel grm = new GeneralResponseModel("01_001", "http://miaozhen.com?exp=1&id=1", "http://miaozhen.com?click=1&id=1",
                "http://miaozhen.com?click=1&id=1", "100", "http://www.adrich.mg.com", "1111231231", "20180516001",
                "http://www.adrich.com.cn", "1", null, "123", "3008",
                "http://www.adrich.com.cn?x=1", 2, "xxx", "yyy", "雀巢",
                "看这里", "http://www.quecao.com?xx1", "1", "http://icon.com.cn",
                "驾校一点通", "pa.pk", "appphone1", "40000",
                "考驾照", "sspcreate001", "http://ssp.mathPaht.com",
                "g", "http://cookmaping.com");
        JSONObject responseJson = getAdrichResponseJson(grm);
        System.out.println("responseJson=" + responseJson);
        GeneralResponseModel grm2 = getGeneralResponseModel(responseJson);
        System.out.println("grm2=" + grm2);

        GeneralResponseModel noBidDefaultResponse = getNoBidDefaultResponse(gpm);
        JSONObject adrichResponseJson = getAdrichResponseJson(noBidDefaultResponse);
        System.out.println("adrichResponseJson=" + adrichResponseJson);
    }
}