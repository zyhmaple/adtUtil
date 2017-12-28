/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  IceUtilInternal.Base64
 */
package com.dm.adrich.communicate.bid.util;

import IceUtilInternal.Base64;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.Key;
import java.security.MessageDigest;
import java.util.Arrays;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class AuthUtil {
    private static final String MAC_NAME = "HmacSHA1";
    private static final String ENCODING = "UTF-8";
    private static final String CUR_VERSION = "1";
    public static final String METHOD_GET = "GET";
    public static final String METHOD_POST = "POST";
    public static final String MV = "2";

    public static String md5Encrypt(String srcStr) {
        return AuthUtil.encrypt("MD5", srcStr);
    }

    public static String sha1Encrypt(String srcStr) {
        return AuthUtil.encrypt("SHA-1", srcStr);
    }

    public static String sha256Encrypt(String srcStr) {
        return AuthUtil.encrypt("SHA-256", srcStr);
    }

    public static String sha384Encrypt(String srcStr) {
        return AuthUtil.encrypt("SHA-384", srcStr);
    }

    public static String sha512Encrypt(String srcStr) {
        return AuthUtil.encrypt("SHA-512", srcStr);
    }

    private static byte[] HmacSHA1Encrypt(String accessSecretKey, String encryptText) {
        try {
            byte[] data = accessSecretKey.getBytes("UTF-8");
            SecretKeySpec secretKey = new SecretKeySpec(data, "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(secretKey);
            byte[] text = encryptText.getBytes("UTF-8");
            byte[] bytes = mac.doFinal(text);
            return bytes;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String encrypt(String algorithm, String srcStr) {
        try {
            byte[] bytes;
            StringBuilder result = new StringBuilder();
            MessageDigest md = MessageDigest.getInstance(algorithm);
            byte[] arrby = bytes = md.digest(srcStr.getBytes("utf-8"));
            int n = arrby.length;
            int n2 = 0;
            while (n2 < n) {
                byte b = arrby[n2];
                String hex = Integer.toHexString(b & 255);
                if (hex.length() == 1) {
                    result.append("0");
                }
                result.append(hex);
                ++n2;
            }
            return result.toString();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String base64Encode(byte[] bstr) {
        return Base64.encode((byte[])bstr);
    }

    public static byte[] base64Decode(String str) {
        byte[] bt = null;
        try {
            bt = Base64.decode((String)str);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return bt;
    }

    public static String HMACSHA1_SIGN_BASE64(String accessKeySecret, String method, String uri, String nonce, String ts, String[] paras, String body) {
        StringBuilder encryptText = new StringBuilder();
        encryptText.append(method).append(uri).append(nonce).append(ts);
        Arrays.sort(paras);
        String[] arrstring = paras;
        int n = arrstring.length;
        int n2 = 0;
        while (n2 < n) {
            String para = arrstring[n2];
            encryptText.append(para);
            ++n2;
        }
        if (body != null && !"".equals(body)) {
            encryptText.append(body);
        }
        byte[] tmp = AuthUtil.HmacSHA1Encrypt(accessKeySecret, encryptText.toString());
        System.out.println(encryptText.toString());
        String sign = AuthUtil.base64Encode(tmp);
        return sign;
    }

    public static String HMACSHA1_SIGN_BASE64_URLENCODE(String accessKeySecret, String method, String uri, String nonce, String ts, String[] paras, String body) {
        String r = null;
        try {
            String base64 = AuthUtil.HMACSHA1_SIGN_BASE64(accessKeySecret, method, uri, nonce, ts, paras, body);
            r = URLEncoder.encode(base64, "utf-8");
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return r;
    }

    public static String HMACSHA1_SIGN_BUILDURL(String accessKeyId, String accessKeySecret, String method, String uri, String nonce, String ts, String[] paras, String body) {
        String s = AuthUtil.HMACSHA1_SIGN_BASE64_URLENCODE(accessKeySecret, method, uri, nonce, ts, paras, body);
        StringBuilder parasUri = new StringBuilder();
        String[] arrstring = paras;
        int n = arrstring.length;
        int n2 = 0;
        while (n2 < n) {
            String para = arrstring[n2];
            String[] m = para.split("=");
            parasUri.append("&").append(m[0]).append("=");
            String p = null;
            try {
                p = URLEncoder.encode(m[1], "utf-8");
            }
            catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            parasUri.append(p);
            ++n2;
        }
        StringBuilder finaluri = new StringBuilder();
        finaluri.append("?s=").append(s).append("&a=").append(accessKeyId).append("&n=").append(nonce).append("&t=").append(ts).append("&v=").append("1").append("&mv=").append("2").append(parasUri);
        return finaluri.toString();
    }
}

