/*
 * Copyright 2013-2015 duolabao.com All right reserved. This software is the confidential and proprietary information of
 * duolabao.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with duolabao.com.
 */
package com.andaily.springoauth.service.impl;

import com.andaily.springoauth.infrastructure.httpclient.MkkHttpResponse;
import com.andaily.springoauth.service.dto.PayDto;

/**
 * 类PayResponseHandler的实现描述：TODO 类实现描述
 *
 * @author Administrator 2016/11/17 18:57
 */
public class PayResponseHandler extends AbstractResponseHandler<PayDto> {

    PayDto dto = null;

    public PayDto getDto() {
        return dto;
    }

    @Override
    public void handleResponse(MkkHttpResponse response) {
        if (response.isResponse200()) {
            this.dto = responseToDto(response, new PayDto());
        } else {
            this.dto = responseToErrorDto(response, new PayDto());
        }
    }
}
