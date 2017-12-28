/*
 * Decompiled with CFR 0_123.
 */
package com.dm.adrich.communicate.bid.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.GZIPInputStream;

public class InterfaceConnentUtil {
    public static String getJsonStrAndSendJsonStr(String httpUrl, String jsonStr) throws Exception {
        String resultStr;
        block14 : {
            InputStream input = null;
            HttpURLConnection conn = null;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            resultStr = null;
            try {
                try {
                    byte[] jsonbyte = jsonStr.getBytes("UTF-8");
                    URL url = new URL(httpUrl);
                    conn = (HttpURLConnection)url.openConnection();
                    conn.setConnectTimeout(1000);
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    conn.setUseCaches(false);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Connection", "Keep-Alive");
                    conn.setRequestProperty("Charset", "UTF-8");
                    conn.setRequestProperty("Content-Length", String.valueOf(jsonbyte.length));
                    DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());
                    outStream.write(jsonbyte);
                    outStream.flush();
                    outStream.close();
                    if (conn.getResponseCode() == 200) {
                        int n;
                        input = conn.getInputStream();
                        byte[] buffer = new byte[1025];
                        while ((n = input.read(buffer)) >= 0) {
                            out.write(buffer, 0, n);
                        }
                        resultStr = out.toString("UTF-8");
                        out.close();
                        input.close();
                        break block14;
                    }
                    throw new Exception("\u8fde\u63a5\u8fd4\u56de\u7684\u9519\u8bef\u4ee3\u7801\u4e3a\uff1a" + conn.getResponseCode());
                }
                catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("\u8bf7\u68c0\u67e5\u8fde\u63a5 = " + httpUrl);
                    throw new Exception(e);
                }
            }
            finally {
                if (input != null) {
                    try {
                        input.close();
                    }
                    catch (IOException e) {
                        input = null;
                        e.printStackTrace();
                    }
                }
                if (out != null) {
                    out.close();
                }
                if (conn != null) {
                    conn.disconnect();
                }
            }
        }
        return resultStr;
    }

    public static String sendURLAndGetJsonStr(String httpNewUrl) throws Exception {
        String resultStr;
        URL url = null;
        Object data = null;
        HttpURLConnection httpurlconnection = null;
        InputStream input = null;
        resultStr = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            try {
                int n;
                url = new URL(httpNewUrl);
                httpurlconnection = (HttpURLConnection)url.openConnection();
                httpurlconnection.setDoOutput(true);
                httpurlconnection.setDoInput(true);
                httpurlconnection.setRequestMethod("GET");
                httpurlconnection.setRequestProperty("Connection", "Keep-Alive");
                httpurlconnection.setRequestProperty("Charset", "UTF-8");
                String code = httpurlconnection.getHeaderField("Content-Encoding");
                input = httpurlconnection.getInputStream();
                byte[] buffer = new byte[1025];
                while ((n = input.read(buffer)) >= 0) {
                    out.write(buffer, 0, n);
                }
                resultStr = out.toString("UTF-8");
                out.close();
                input.close();
            }
            catch (Exception e) {
                System.out.println("\u8bf7\u68c0\u67e5\u8fde\u63a5 = " + httpNewUrl);
                e.printStackTrace();
                if (input != null) {
                    try {
                        input.close();
                    }
                    catch (IOException e2) {
                        input = null;
                        e2.printStackTrace();
                    }
                }
                if (out != null) {
                    try {
                        out.close();
                    }
                    catch (IOException e3) {
                        out = null;
                        e3.printStackTrace();
                    }
                }
                if (httpurlconnection != null) {
                    httpurlconnection.disconnect();
                }
            }
        }
        finally {
            if (input != null) {
                try {
                    input.close();
                }
                catch (IOException e) {
                    input = null;
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                }
                catch (IOException e) {
                    out = null;
                    e.printStackTrace();
                }
            }
            if (httpurlconnection != null) {
                httpurlconnection.disconnect();
            }
        }
        return resultStr;
    }

    public static String sendURLAndGetStr(String httpNewUrl, String encoding) throws Exception {
        String resultStr;
        URL url = null;
        Object data = null;
        HttpURLConnection httpurlconnection = null;
        InputStream input = null;
        resultStr = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            try {
                int n;
                url = new URL(httpNewUrl);
                httpurlconnection = (HttpURLConnection)url.openConnection();
                httpurlconnection.setDoOutput(true);
                httpurlconnection.setRequestMethod("GET");
                httpurlconnection.setRequestProperty("Connection", "Keep-Alive");
                httpurlconnection.setRequestProperty("Charset", encoding);
                String code = httpurlconnection.getHeaderField("Content-Encoding");
                input = code != null && "gzip".equals(code.toLowerCase()) ? new GZIPInputStream(httpurlconnection.getInputStream()) : httpurlconnection.getInputStream();
                byte[] buffer = new byte[1025];
                while ((n = input.read(buffer)) >= 0) {
                    out.write(buffer, 0, n);
                }
                resultStr = out.toString("UTF-8");
                out.close();
                input.close();
            }
            catch (Exception e) {
                System.out.println("\u8bf7\u68c0\u67e5\u8fde\u63a5 = " + httpNewUrl);
                e.printStackTrace();
                if (input != null) {
                    try {
                        input.close();
                    }
                    catch (IOException e2) {
                        input = null;
                        e2.printStackTrace();
                    }
                }
                if (out != null) {
                    try {
                        out.close();
                    }
                    catch (IOException e3) {
                        out = null;
                        e3.printStackTrace();
                    }
                }
                if (httpurlconnection != null) {
                    httpurlconnection.disconnect();
                }
            }
        }
        finally {
            if (input != null) {
                try {
                    input.close();
                }
                catch (IOException e) {
                    input = null;
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                }
                catch (IOException e) {
                    out = null;
                    e.printStackTrace();
                }
            }
            if (httpurlconnection != null) {
                httpurlconnection.disconnect();
            }
        }
        return resultStr;
    }

    public static String sendURLAndGetStr(String httpNewUrl) throws Exception {
        return InterfaceConnentUtil.sendURLAndGetStr(httpNewUrl, "UTF-8");
    }

    public static String uncompressToString(byte[] b, String encoding) {
        if (b == null || b.length == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(b);
        try {
            int n;
            GZIPInputStream gunzip = new GZIPInputStream(in);
            byte[] buffer = new byte[256];
            while ((n = gunzip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
            return out.toString(encoding);
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String GetJsonStrByInputStream(InputStream input) {
        String resultStr;
        block21 : {
            resultStr = null;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            try {
                try {
                    int n;
                    byte[] buffer = new byte[1024];
                    while ((n = input.read(buffer)) >= 0) {
                        out.write(buffer, 0, n);
                    }
                    resultStr = out.toString("UTF-8");
                    out.close();
                    input.close();
                }
                catch (Exception e) {
                    e.printStackTrace();
                    if (input != null) {
                        try {
                            input.close();
                        }
                        catch (IOException e2) {
                            input = null;
                            e2.printStackTrace();
                        }
                    }
                    if (out == null) break block21;
                    try {
                        out.close();
                    }
                    catch (IOException e3) {
                        out = null;
                        e3.printStackTrace();
                    }
                }
            }
            finally {
                if (input != null) {
                    try {
                        input.close();
                    }
                    catch (IOException e) {
                        input = null;
                        e.printStackTrace();
                    }
                }
                if (out != null) {
                    try {
                        out.close();
                    }
                    catch (IOException e) {
                        out = null;
                        e.printStackTrace();
                    }
                }
            }
        }
        return resultStr;
    }
}

