package com.dm.adrich.communicate.bid.util;

public class RedisBF {
    private static final int k = 7;
    private static final int m = 600000000;

    public static boolean add(String key, String requestID) {
        int[] positions;
        int[] arrn = positions = HashUtils.murmurHashOffset(requestID, 7, 600000000);
        int n = arrn.length;
        int n2 = 0;
        while (n2 < n) {
            int position = arrn[n2];
            RedisBF.setBit(key, position);
            ++n2;
        }
        return true;
    }

    public static boolean contains(String key, String requestID) {
        int[] positions = HashUtils.murmurHashOffset(requestID, 7, 600000000);
        if (positions != null && positions.length > 0) {
            int[] arrn = positions;
            int n = arrn.length;
            int n2 = 0;
            while (n2 < n) {
                int position = arrn[n2];
                if (!RedisBF.getBit(key, position)) {
                    return false;
                }
                ++n2;
            }
            return true;
        }
        return false;
    }

    private static boolean getBit(String key, int index) {
        try {
            return JedisUtil.INSTANCE.getJedis().getbit(key, (long) index);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean setBit(String key, int index) {
        try {
            return JedisUtil.INSTANCE.getJedis().setbit(key, (long) index, true);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

