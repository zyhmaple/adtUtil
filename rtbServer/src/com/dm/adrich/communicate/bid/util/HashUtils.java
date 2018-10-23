package com.dm.adrich.communicate.bid.util;

import org.apache.commons.codec.digest.DigestUtils;
import redis.clients.util.MurmurHash;

public class HashUtils {

    public HashUtils() {
    }

    public static int[] sha1Offset(String bizId, int hashFunctionCount, int maxBitCount) {
        int offsets[] = new int[hashFunctionCount];
        byte sha1[] = DigestUtils.sha1(bizId);
        int hashes[] = new int[4];
        hashes[0] = sha1[0] & 255 | (sha1[1] & 255) << 8 | (sha1[2] & 255) << 16 | (sha1[3] & 255) << 24;
        hashes[1] = sha1[4] & 255 | (sha1[5] & 255) << 8 | (sha1[6] & 255) << 16 | (sha1[7] & 255) << 24;
        hashes[2] = sha1[8] & 255 | (sha1[9] & 255) << 8 | (sha1[10] & 255) << 16 | (sha1[11] & 255) << 24;
        hashes[3] = sha1[12] & 255 | (sha1[13] & 255) << 8 | (sha1[14] & 255) << 16 | (sha1[15] & 255) << 24;
        for (int i = 0; i < offsets.length; i++)
            offsets[i] = (hashes[i % 2] + i * hashes[2 + ((i + i % 2) % 4) / 2]) % maxBitCount;

        return offsets;
    }

    public static int[] murmurHashOffset(String bizId, int hashFunctionCount, int maxBitCount) {
        int offsets[] = new int[hashFunctionCount];
        byte b[] = bizId.getBytes();
        int hash1 = MurmurHash.hash(b, 0);
        int hash2 = MurmurHash.hash(b, hash1);
        for (int i = 0; i < hashFunctionCount; i++)
            offsets[i] = Math.abs((hash1 + i * hash2) % maxBitCount);

        return offsets;
    }

    public static void main(String args[]) {
        int offsets[] = murmurHashOffset("awez2016_8587", 6, 1000);
        int ai[];
        int j = (ai = offsets).length;
        for (int i = 0; i < j; i++) {
            int rs = ai[i];
            System.out.println(rs);
        }

    }
}