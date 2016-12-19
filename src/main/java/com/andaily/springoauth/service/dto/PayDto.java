/*
 * Copyright 2013-2015 duolabao.com All right reserved. This software is the
 * confidential and proprietary information of duolabao.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with duolabao.com.
 */
package com.andaily.springoauth.service.dto;

/**
 * 类PayDto的实现描述：TODO 类实现描述
 *
 * @author Administrator 2016/11/17 18:58
 */
public class PayDto extends AbstractOauthDto {
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    private String result;
}
