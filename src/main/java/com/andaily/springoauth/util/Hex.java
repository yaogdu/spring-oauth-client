/*
 * Copyright 2013-2015 duolabao.com All right reserved. This software is the confidential and proprietary information of
 * duolabao.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with duolabao.com.
 */
package com.andaily.springoauth.util;

/**
 * 类Hex.java的实现描述：HEX转换工具类
 *
 * @author libing 2015年9月11日 下午5:23:54
 */
public class Hex {

    public static String toHex(byte input[]) {
        if (input == null) return null;
        StringBuffer output = new StringBuffer(input.length * 2);
        for (int i = 0; i < input.length; i++) {
            int current = input[i] & 0xff;
            if (current < 16) output.append("0");
            output.append(Integer.toString(current, 16));
        }
        return output.toString();
    }

    public static byte[] fromHex(String input) {
        if (input == null) return null;
        byte output[] = new byte[input.length() / 2];
        for (int i = 0; i < output.length; i++)
            output[i] = (byte) Integer.parseInt(input.substring(i * 2, (i + 1) * 2), 16);
        return output;
    }
}