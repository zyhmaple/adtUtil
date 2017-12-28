/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.apache.commons.codec.digest.DigestUtils
 *  redis.clients.util.MurmurHash
 */
package com.dm.adrich.communicate.bid.util;

import java.io.PrintStream;
import org.apache.commons.codec.digest.DigestUtils;
import redis.clients.util.MurmurHash;

public class HashUtils {
    public static int[] sha1Offset(String bizId, int hashFunctionCount, int maxBitCount) {
        int[] offsets = new int[hashFunctionCount];
        byte[] sha1 = DigestUtils.sha1((String)bizId);
        int[] hashes = new int[]{sha1[0] & 255 | (sha1[1] & 255) << 8 | (sha1[2] & 255) << 16 | (sha1[3] & 255) << 24, sha1[4] & 255 | (sha1[5] & 255) << 8 | (sha1[6] & 255) << 16 | (sha1[7] & 255) << 24, sha1[8] & 255 | (sha1[9] & 255) << 8 | (sha1[10] & 255) << 16 | (sha1[11] & 255) << 24, sha1[12] & 255 | (sha1[13] & 255) << 8 | (sha1[14] & 255) << 16 | (sha1[15] & 255) << 24};
        int i = 0;
        while (i < offsets.length) {
            offsets[i] = (hashes[i % 2] + i * hashes[2 + (i + i % 2) % 4 / 2]) % maxBitCount;
            ++i;
        }
        return offsets;
    }

    public static int[] murmurHashOffset(String bizId, int hashFunctionCount, int maxBitCount) {
        int[] offsets = new int[hashFunctionCount];
        byte[] b = bizId.getBytes();
        int hash1 = MurmurHash.hash((byte[])b, (int)0);
        int hash2 = MurmurHash.hash((byte[])b, (int)hash1);
        int i = 0;
        while (i < hashFunctionCount) {
            offsets[i] = Math.abs((hash1 + i * hash2) % maxBitCount);
            ++i;
        }
        return offsets;
    }

    public static void main(String[] args) {
        int[] offsets;
        int[] arrn = offsets = HashUtils.murmurHashOffset("awez2016_8587", 6, 1000);
        int n = arrn.length;
        int n2 = 0;
        while (n2 < n) {
            int rs = arrn[n2];
            System.out.println(rs);
            ++n2;
        }
    }
}

