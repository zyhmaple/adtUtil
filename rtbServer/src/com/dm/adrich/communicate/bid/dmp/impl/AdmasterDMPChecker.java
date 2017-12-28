/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  IceUtilInternal.Base64
 *  org.apache.log4j.Logger
 *  org.json.simple.JSONArray
 *  org.json.simple.JSONObject
 */
package com.dm.adrich.communicate.bid.dmp.impl;

import IceUtilInternal.Base64;
import com.dm.adrich.communicate.bid.dmp.DMPChecker;
import com.dm.adrich.communicate.bid.model.DMPModel;
import com.dm.adrich.communicate.bid.util.InterfaceConnentUtil;
import com.dm.adrich.communicate.bid.util.SysParams;
import java.io.PrintStream;
import java.net.URLEncoder;
import java.security.Key;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class AdmasterDMPChecker
implements DMPChecker {
    protected static final Logger log = Logger.getLogger(AdmasterDMPChecker.class);
    private String url = SysParams.sysProps.getProperty("admaster.userTagURL");
    private final String HMAC_SHA1 = "HmacSHA1";
    private String accessKeySecret = SysParams.sysProps.getProperty("admaster.accessKeySecret");
    private String accessKeyID = SysParams.sysProps.getProperty("admaster.accessKeyID");
    private String pcMethod = SysParams.sysProps.getProperty("admaster.pcMethod");
    private String mMethod = SysParams.sysProps.getProperty("admaster.mMethod");
    private String successCode = SysParams.sysProps.getProperty("admaster.successCode");
    private String mID = SysParams.sysProps.getProperty("admaster.mID");

    @Override
    public boolean checkDmpTags(DMPModel dmpModel) throws Exception {
        String method = null;
        String userID = dmpModel.getUserID();
        if (dmpModel.getIsPC() == 1) {
            method = this.pcMethod;
            userID = String.valueOf(SysParams.sspMidMap.get(dmpModel.getSspCode())) + "-" + userID;
        } else if (dmpModel.getIsPC() == 2) {
            method = this.mMethod;
            if (userID.length() < 35) {
                userID = userID.toLowerCase();
            }
        } else {
            log.info((Object)"\u8bf7\u8f93\u5165\u65b9\u6cd5\u540d\u79f0\uff01\uff01");
        }
        long ts = System.currentTimeMillis();
        String n = this.getAdmasterN();
        JSONObject json = new JSONObject();
        json.put((Object)"id", (Object)userID);
        json.put((Object)"dealId", (Object)dmpModel.getOrderID());
        JSONArray jsonBody = new JSONArray();
        jsonBody.add((Object)json);
        String s = this.createSign(method, n, ts, jsonBody.toString());
        log.info((Object)("s =" + s));
        log.info((Object)("jsonBody.toString() =" + jsonBody.toString()));
        String requestURL = this.createRequestURL(s, n, ts, method);
        log.info((Object)("requestURL =" + requestURL));
        String jsonStr = InterfaceConnentUtil.getJsonStrAndSendJsonStr(requestURL, jsonBody.toString());
        log.info((Object)("jsonStr = " + jsonStr));
        if (jsonStr != null && !"".equals(jsonStr) && jsonStr.indexOf(this.successCode) > 1) {
            return true;
        }
        return false;
    }

    private String getAdmasterN() {
        StringBuffer sb = new StringBuffer();
        int i = 0;
        while (i < 15) {
            int number = new Random().nextInt(10);
            sb.append(number);
            ++i;
        }
        return sb.toString();
    }

    private String createRequestURL(String s, String n, long ts, String method) {
        StringBuilder finaluri = new StringBuilder();
        finaluri.append(this.url).append(method).append("?s=").append(s).append("&a=").append(this.accessKeyID).append("&n=").append(n).append("&t=").append(ts).append("&v=1&mv=2");
        return finaluri.toString();
    }

    private String createSign(String method, String n, long ts, String body) throws Exception {
        StringBuilder encryptText = new StringBuilder();
        encryptText.append("POST").append("/RTQ/").append(method).append(n).append(ts);
        if (body != null && !"".equals(body)) {
            encryptText.append(body);
        }
        System.out.println("body = " + body);
        System.out.println("encryptText.toString() = " + encryptText.toString());
        return this.getSignature(encryptText.toString());
    }

    private String getSignature(String data) throws Exception {
        SecretKeySpec signingKey = new SecretKeySpec(this.accessKeySecret.getBytes(), "HmacSHA1");
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(signingKey);
        byte[] rawHmac = mac.doFinal(data.getBytes());
        return URLEncoder.encode(Base64.encode((byte[])rawHmac), "UTF-8");
    }

    @Override
    public List getDmpTags(DMPModel dmpModel) throws Exception {
        return null;
    }
}

