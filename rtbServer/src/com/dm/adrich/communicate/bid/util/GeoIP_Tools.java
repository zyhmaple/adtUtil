package com.dm.adrich.communicate.bid.util;

import java.util.SortedMap;

public class GeoIP_Tools {
    public static String GetCity(String ip) {
        long ipLong = ipToLong(ip);
        long inputIp = ipLong;

        if (SysParams.allIpMap.isEmpty()) {
            return null;
        }

        if (!SysParams.allIpMap.containsKey(Long.valueOf(ipLong))) {
            SortedMap headMap = SysParams.allIpMap.headMap(Long.valueOf(ipLong));
            ipLong = (headMap.isEmpty() ? (Long) SysParams.allIpMap.firstKey() : (Long) headMap.lastKey()).longValue();
        }

        String endIPAndCity = (String) SysParams.allIpMap.get(Long.valueOf(ipLong));
        String[] endStr = endIPAndCity.split(",");

        if (Long.parseLong(endStr[0]) >= inputIp) {
            return (String) SysParams.allCityMap.get(endStr[1]);
        }

        return null;
    }

    public static long ipToLong(String strIP) {
        long[] ip = new long[4];
        int position1 = strIP.indexOf(".");
        int position2 = strIP.indexOf(".", position1 + 1);
        int position3 = strIP.indexOf(".", position2 + 1);
        ip[0] = Long.parseLong(strIP.substring(0, position1).trim());
        ip[1] = Long.parseLong(strIP.substring(position1 + 1, position2).trim());
        ip[2] = Long.parseLong(strIP.substring(position2 + 1, position3).trim());
        ip[3] = Long.parseLong(strIP.substring(position3 + 1).trim());
        return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
    }
}