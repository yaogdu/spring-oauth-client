/*
 * Copyright 2013-2015 duolabao.com All right reserved. This software is the confidential and proprietary information of
 * duolabao.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with duolabao.com.
 */
package com.andaily.springoauth.util;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 类HttpHelperUtil.java的实现描述：TODO 类实现描述
 *
 * @author chengdong.wei 2016年4月22日 下午9:52:30
 */
public class HttpHelperUtil {

    /**
     * 获取body体内容
     *
     * @param
     * @return
     * @throws IOException
     */
    public static String getBodyString(InputStream inputStream) throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos = new ByteArrayOutputStream();
        byte[] bytes = new byte[1024];

        if (inputStream.markSupported()) {
            inputStream.reset();
        }
        int length = inputStream.read(bytes);
        while (length != -1) {
            baos.write(bytes, 0, length);
            length = inputStream.read(bytes);
        }
        baos.flush();
        String bodyStr = new String(baos.toByteArray(),"UTF-8");

        if (baos != null) {
            baos.close();
        }
        if (inputStream != null) {
            inputStream.close();
        }
        return bodyStr;
    }

    /**
     * copy inpustream
     *
     * @param request
     * @return
     * @throws IOException
     */
    public static ByteArrayInputStream inpustreamWrapper(HttpServletRequest request) throws IOException {
        InputStream sourceInputstream = request.getInputStream();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] bytes = new byte[1024];

        int length = sourceInputstream.read(bytes);
        while (length != -1) {
            baos.write(bytes, 0, length);
            length = sourceInputstream.read(bytes);
        }
        baos.flush();

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        bais.reset();
        return bais;
    }
}
