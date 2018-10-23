/*
 * Decompiled with CFR 0_123.
 */
package com.dm.adrich.communicate.bid.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

public class Base58 {
    public static final char[] ALPHABET = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz".toCharArray();
    private static final int[] INDEXES = new int[128];

    static {
        int i = 0;
        while (i < INDEXES.length) {
            Base58.INDEXES[i] = -1;
            ++i;
        }
        i = 0;
        while (i < ALPHABET.length) {
            Base58.INDEXES[Base58.ALPHABET[i]] = i++;
        }
    }

    public static String encode(byte[] input) {
        if (input.length == 0) {
            return "";
        }
        input = Base58.copyOfRange(input, 0, input.length);
        int zeroCount = 0;
        while (zeroCount < input.length && input[zeroCount] == 0) {
            ++zeroCount;
        }
        byte[] temp = new byte[input.length * 2];
        int j = temp.length;
        int startAt = zeroCount;
        while (startAt < input.length) {
            byte mod = Base58.divmod58(input, startAt);
            if (input[startAt] == 0) {
                ++startAt;
            }
            temp[--j] = (byte) ALPHABET[mod];
        }
        while (j < temp.length && temp[j] == ALPHABET[0]) {
            ++j;
        }
        while (--zeroCount >= 0) {
            temp[--j] = (byte) ALPHABET[0];
        }
        byte[] output = Base58.copyOfRange(temp, j, temp.length);
        try {
            return new String(output, "US-ASCII");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] decode(String input) throws IllegalArgumentException {
        if (input.length() == 0) {
            return new byte[0];
        }
        byte[] input58 = new byte[input.length()];
        int i = 0;
        while (i < input.length()) {
            char c = input.charAt(i);
            int digit58 = -1;
            if (c >= '\u0000' && c < '?') {
                digit58 = INDEXES[c];
            }
            if (digit58 < 0) {
                throw new IllegalArgumentException("Illegal character " + c + " at " + i);
            }
            input58[i] = (byte) digit58;
            ++i;
        }
        int zeroCount = 0;
        while (zeroCount < input58.length && input58[zeroCount] == 0) {
            ++zeroCount;
        }
        byte[] temp = new byte[input.length()];
        int j = temp.length;
        int startAt = zeroCount;
        while (startAt < input58.length) {
            byte mod = Base58.divmod256(input58, startAt);
            if (input58[startAt] == 0) {
                ++startAt;
            }
            temp[--j] = mod;
        }
        while (j < temp.length && temp[j] == 0) {
            ++j;
        }
        return Base58.copyOfRange(temp, j - zeroCount, temp.length);
    }

    public static BigInteger decodeToBigInteger(String input) throws IllegalArgumentException {
        return new BigInteger(1, Base58.decode(input));
    }

    private static byte divmod58(byte[] number, int startAt) {
        int remainder = 0;
        int i = startAt;
        while (i < number.length) {
            int digit256 = number[i] & 255;
            int temp = remainder * 256 + digit256;
            number[i] = (byte) (temp / 58);
            remainder = temp % 58;
            ++i;
        }
        return (byte) remainder;
    }

    private static byte divmod256(byte[] number58, int startAt) {
        int remainder = 0;
        int i = startAt;
        while (i < number58.length) {
            int digit58 = number58[i] & 255;
            int temp = remainder * 58 + digit58;
            number58[i] = (byte) (temp / 256);
            remainder = temp % 256;
            ++i;
        }
        return (byte) remainder;
    }

    private static byte[] copyOfRange(byte[] source, int from, int to) {
        byte[] range = new byte[to - from];
        System.arraycopy(source, from, range, 0, range.length);
        return range;
    }
}

