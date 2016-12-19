/*
 * Copyright 2013-2015 duolabao.com All right reserved. This software is the confidential and proprietary information of
 * duolabao.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with duolabao.com.
 */
package com.andaily.springoauth.util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.security.MessageDigest;
import java.util.Map;


/**
 * 类SignUtil.java的实现描述：TODO 类实现描述
 *
 * @author chengdong.wei 2016年4月16日 下午3:51:44
 */
public class TokenUtil {

    private static final Log logger = LogFactory.getLog(TokenUtil.class);

    /**
     * 验证签名与当前参数是否匹配
     *
     * @param params 传入参数
     * @param token 签名
     * @return 匹配结果
     */
    public static String generateToken(Map<String, String> params) {

        // 获取不参与排序的参数time&secretKey
        String time = params.get("timestamp");
        String secretKey = params.get("secretKey");
        String path = params.get("path");
        String body = params.get("body").trim();

        StringBuffer summary = new StringBuffer();

        // 拼接secretKey & time
        if (StringUtils.isEmpty(body)) {
            summary.append("secretKey=").append(secretKey).append("&timestamp=").append(time).append("&path=").append(path);
        } else {
            summary.append("secretKey=").append(secretKey).append("&timestamp=").append(time).append("&path=").append(path).append("&body=").append(body);
        }

        if (logger.isInfoEnabled()) {
            logger.info("========客户端签名摘要:====" + summary.toString());
        }
        // 生成token
        String token2 = shaDigest(summary.toString()).toUpperCase();
        if (logger.isInfoEnabled()) {
            logger.info("========生成签名:====" + token2);
        }

        return token2;
    }

    /**
     * 生成出参token
     *
     * @param params 传入参数
     * @return
     */
    public static String generalToken(Map<String, String> params) {

        // 获取不参与排序的参数time&secretKey
        String time = params.get("timestamp");
        String secretKey = params.get("secretKey");

        StringBuffer summary = new StringBuffer();
        // 拼接secretKey & time & path
        summary.append("secretKey=").append(secretKey).append("&timestamp=").append(time);

        // 生成token
        String token = shaDigest(summary.toString()).toUpperCase();
        if (logger.isInfoEnabled()) {
            logger.info("========服务端回签客户端摘要:====" + summary.toString());
            logger.info("========服务端回签客户端签名:====" + token);
        }
        return token;
    }

    public static String shaDigest(String str) {
        try {
            byte[] data = str.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("SHA");
            return com.andaily.springoauth.util.Hex.toHex(md.digest(data));
        } catch (Exception e) {
            throw new RuntimeException("digest fail!", e);
        }
    }
}
