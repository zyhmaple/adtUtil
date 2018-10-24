package com.dm.adrich.communicate.bid.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;

public class InterfaceConnentUtil {

    public static String getJsonStrAndSendJsonStr(String httpUrl, String jsonStr)
            throws Exception {
        InputStream input;
        HttpURLConnection conn;
        ByteArrayOutputStream out;
        String resultStr;
        input = null;
        conn = null;
        out = new ByteArrayOutputStream();
        resultStr = null;
        try {
            byte jsonbyte[] = jsonStr.getBytes("UTF-8");
            URL url = new URL(httpUrl);
            conn = (HttpURLConnection) url.openConnection();
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
                input = conn.getInputStream();
                byte buffer[] = new byte[1024];
                int n;
                while ((n = input.read(buffer)) >= 0)
                    out.write(buffer, 0, n);
                resultStr = out.toString("UTF-8");
                out.close();
                input.close();
            } else {
                //throw new Exception((new StringBuilder("\u8FDE\u63A5\u8FD4\u56DE\u7684\u9519\u8BEF\u4EE3\u7801\u4E3A\uFF1A")).append(conn.getResponseCode()).toString());
                throw new Exception("连接返回的错误代码为：" + conn.getResponseCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
            //System.out.println((new StringBuilder("\u8BF7\u68C0\u67E5\u8FDE\u63A5 = ")).append(httpUrl).toString());
            System.out.println("请检查连接 = " + httpUrl);
            throw new Exception(e);
        } finally {
            if (input != null)
                try {
                    input.close();
                } catch (IOException e) {
                    input = null;
                    e.printStackTrace();
                }
            if (out != null)
                out.close();
            if (conn != null)
                conn.disconnect();
        }
        return resultStr;
    }

    public static String sendURLAndGetJsonStr(String httpNewUrl)
            throws Exception {
        URL url = null;

        byte[] data = null;
        HttpURLConnection httpurlconnection = null;
        InputStream input = null;
        String resultStr = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            url = new URL(httpNewUrl);

            httpurlconnection = (HttpURLConnection) url.openConnection();
            httpurlconnection.setDoOutput(true);
            httpurlconnection.setDoInput(true);
            httpurlconnection.setRequestMethod("GET");
            httpurlconnection.setRequestProperty("Connection", "Keep-Alive");
            httpurlconnection.setRequestProperty("Charset", "UTF-8");
            String code = httpurlconnection.getHeaderField("Content-Encoding");

            input = httpurlconnection.getInputStream();
            byte[] buffer = new byte[1025];
            int n;
            while ((n = input.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
            resultStr = out.toString("UTF-8");
            out.close();

            input.close();
        } catch (Exception e) {
            System.out.println("请检查连接 = " + httpNewUrl);
            e.printStackTrace();
        } finally {
            if (input != null) try {
                input.close();
            } catch (IOException e) {
                input = null;
                e.printStackTrace();
            }
            if (out != null) try {
                out.close();
            } catch (IOException e) {
                out = null;
                e.printStackTrace();
            }
            if (httpurlconnection != null)
                httpurlconnection.disconnect();
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
            int n;
            url = new URL(httpNewUrl);
            httpurlconnection = (HttpURLConnection) url.openConnection();
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
        } catch (Exception e) {
            System.out.println("请检查连接 = " + httpNewUrl);
            e.printStackTrace();
        } finally {
            if (input != null) try {
                input.close();
            } catch (IOException e2) {
                input = null;
                e2.printStackTrace();
            }
            if (out != null) try {
                out.close();
            } catch (IOException e3) {
                out = null;
                e3.printStackTrace();
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
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String GetJsonStrByInputStream(InputStream input) {
        String resultStr = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            int n;
            byte[] buffer = new byte[1024];
            while ((n = input.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
            resultStr = out.toString("UTF-8");
            out.close();
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (input != null)
                try {
                    input.close();
                } catch (IOException e) {
                    input = null;
                    e.printStackTrace();
                }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    out = null;
                    e.printStackTrace();
                }
            }
        }
        return resultStr;
    }


}

