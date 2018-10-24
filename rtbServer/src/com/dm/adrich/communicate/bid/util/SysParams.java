package com.dm.adrich.communicate.bid.util;

import java.util.*;

public class SysParams {

    public static final String SYSFILEPATH = "/conf/sys.properties";
    public static final Properties sysProps = new Properties();
    public static final Properties sspProps = new Properties();
    public static final String USERCOOKIEID = "adrichUserCookieID";
    public static final String USERATTRIBUTE = "adrichUserAttribute";
    public static final String USERKEYWORD = "adrichUserKeyword";
    public static final Properties personTagProps = new Properties();
    public static final Properties advertIndustryProps = new Properties();
    public static SortedMap allIpMap = new TreeMap();
    public static Map allCityMap = new HashMap();
    public static Map pageTagMap = new HashMap();
    public static Map displayTypeProps = new HashMap();
    public static Map osTypeProps = new HashMap();
    public static Map netTypeProps = new HashMap();
    public static Map deviceTypeProps = new HashMap();
    public static Map openTypeProps = new HashMap();
    public static Map creativeTypeProps = new HashMap();
    public static Map sspCreativeTypeProps = new HashMap();
    public static Map sspMidMap = new HashMap();
    public static String htmlStr = null;

}