/*
 * Copyright 2013-2015 duolabao.com All right reserved. This software is the confidential and proprietary information of
 * duolabao.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with duolabao.com.
 */
package com.andaily.springoauth.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 类MainController的实现描述：TODO 类实现描述
 *
 * @author Administrator 2016/11/17 15:18
 */
@Controller
public class MainController {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @Value("#{properties['user-authorization-uri']}")
    private String              userAuthorizationUri;

    @Value("#{properties['application-host']}")
    private String              host;

    /**
     * used for receive server first request,as index page of this project(third party configured main page)
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "main",method = RequestMethod.GET)
    public String mainPage(HttpServletRequest request, @RequestParam(value="appNum",required = false) String appNum, HttpServletResponse response) {
        StringBuffer url = new StringBuffer(userAuthorizationUri);
        url.append("?");
        if (StringUtils.isEmpty(appNum)) {
            appNum = "10131314817002351431054";
            logger.info("set appNum to default {}", appNum);
        }

        Cookie [] cookies = request.getCookies();

        boolean cookieExists = false;

        if(cookies != null){

            for(Cookie cookie : cookies){

                if("customerNum".equals(cookie.getName())){
                    cookieExists = true;
                }

                logger.info("cookie name is {} and cookie value  is {}",cookie.getName(),cookie.getValue());
            }
        }


        if(!cookieExists){
            Cookie cookie = new Cookie("customerNum","cookiestestttt");
            cookie.setPath("/");
            cookie.setMaxAge(30*60*60);
            response.addCookie(cookie);
            logger.info("set new cookie");
        }

        String redirectUrl = host + "callback";
        String responseType = "code";
        String scope = "read";
        String state = "123456";

        WebUtils.saveState(request, state);

        url.append("appNum=").append(appNum).append("&").append("redirectUrl=").append(redirectUrl).append("&").append("responseType=code&scope=read&state=123456");
        logger.info("url is {}", url.toString());

        return "redirect:"+url.toString();
    }

}
