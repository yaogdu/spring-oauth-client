package com.andaily.springoauth.service;

import com.andaily.springoauth.service.dto.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Shengzhao Li
 */

public interface OauthService {


    CustomerDto readCustomerInfo(String accessToken, HttpServletRequest request);

    String pay(String accessToken,HttpServletRequest request);

    String realPay(PayVO payVO,HttpServletRequest request);

    AccessTokenDto retrieveAccessTokenDto(AuthAccessTokenDto tokenDto);

    AuthAccessTokenDto createAuthAccessTokenDto(AuthCallbackDto callbackDto);

    UserDto loadUnityUserDto(String accessToken);

    AccessTokenDto retrievePasswordAccessTokenDto(AuthAccessTokenDto authAccessTokenDto);

    AccessTokenDto refreshAccessTokenDto(RefreshAccessTokenDto refreshAccessTokenDto);

    AccessTokenDto retrieveCredentialsAccessTokenDto(AuthAccessTokenDto authAccessTokenDto);
}