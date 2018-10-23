/*
 * Decompiled with CFR 0_123.
 *
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
package com.dm.adrich.communicate.bid.util;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Map;

public class IPLoadUtil {
    protected static final Logger log = Logger.getLogger(IPLoadUtil.class);

    public static void loadIPDataBase() throws Exception {
        IPLoadUtil.loadIPData(SysParams.sysProps.getProperty("gIPFile"));
        IPLoadUtil.loadCityData(SysParams.sysProps.getProperty("gCityFile"));
        IPLoadUtil.printlnAll();
    }

    private static void loadIPData(String conf_filename) throws Exception {
        FileReader fReader = null;
        BufferedReader buffReader = null;
        String line = null;
        fReader = new FileReader(conf_filename);
        buffReader = new BufferedReader(fReader);
        try {
            try {
                while ((line = buffReader.readLine()) != null) {
                    if ((line = line.trim()).length() == 0 || line.charAt(0) == '#') continue;
                    String[] dataArr = line.split(",");
                    long startIP = Long.parseLong(dataArr[0]);
                    SysParams.allIpMap.put(startIP, String.valueOf(dataArr[1]) + "," + dataArr[2]);
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.error((Object) ("loadIPData = " + e.getMessage()), (Throwable) e);
                fReader.close();
            }
        } finally {
            fReader.close();
        }
    }

    private static void loadCityData(String conf_filename) throws Exception {
        FileReader fReader = null;
        BufferedReader buffReader = null;
        String line = null;
        fReader = new FileReader(conf_filename);
        buffReader = new BufferedReader(fReader);
        try {
            try {
                while ((line = buffReader.readLine()) != null) {
                    if (line.length() == 0 || line.charAt(0) == '#') continue;
                    String[] dataArr = line.split(",");
                    SysParams.allCityMap.put(dataArr[0], dataArr[1]);
                }
            } catch (Exception e) {
                log.error((Object) ("loadCityData = " + e.getMessage()), (Throwable) e);
                e.printStackTrace();
                fReader.close();
            }
        } finally {
            fReader.close();
        }
    }

    private static void loadHtmlTemple(String conf_filename) throws Exception {
        StringBuffer sb;
        block6:
        {
            FileReader fReader;
            fReader = null;
            BufferedReader buffReader = null;
            String line = null;
            fReader = new FileReader(conf_filename);
            buffReader = new BufferedReader(fReader);
            sb = new StringBuffer();
            try {
                try {
                    while ((line = buffReader.readLine()) != null) {
                        if (line.length() == 0 || line.charAt(0) == '#') continue;
                        sb.append(line.trim());
                    }
                } catch (Exception e) {
                    log.error((Object) ("loadHtmlTemple = " + e.getMessage()), (Throwable) e);
                    e.printStackTrace();
                    fReader.close();
                    break block6;
                }
            } catch (Throwable throwable) {
                fReader.close();
                throw throwable;
            }
            fReader.close();
        }
        SysParams.htmlStr = sb.toString().trim();
        System.out.println("htmlFileStr = " + SysParams.htmlStr);
    }

    public static void printlnAll() throws Exception {
        Map<String, String> cityMap = SysParams.allCityMap;
        for (Map.Entry<String, String> entry : cityMap.entrySet()) {
            log.warn((Object) ("key= " + entry.getKey() + " and value= " + entry.getValue()));
        }
        log.warn((Object) ("newMap.size()= " + SysParams.allCityMap.size()));
    }
}

