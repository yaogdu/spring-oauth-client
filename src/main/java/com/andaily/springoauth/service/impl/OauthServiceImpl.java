package com.andaily.springoauth.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.andaily.springoauth.infrastructure.httpclient.HttpClientExecutor;
import com.andaily.springoauth.infrastructure.httpclient.HttpClientPostExecutor;
import com.andaily.springoauth.service.OauthService;
import com.andaily.springoauth.service.dto.*;
import com.andaily.springoauth.util.HttpUtil;
import com.andaily.springoauth.util.TokenUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 15-5-18
 *
 * @author Shengzhao Li
 */
@Service("oauthService")
public class OauthServiceImpl implements OauthService {



    private static final Logger logger = LoggerFactory.getLogger(OauthServiceImpl.class);

    @Resource
    HttpUtil httpUtil;

    @Value("#{properties['access-token-uri']}")
    private String              accessTokenUri;

    @Value("#{properties['unityUserInfoUri']}")
    private String              unityUserInfoUri;

    @Value("#{properties['payUrl']}")
    private String              payUrl;

    @Value("#{properties['customerInfoUrl']}")
    private String              customerInfoUrl;

    @Override
    public CustomerDto readCustomerInfo(String accessToken, HttpServletRequest request) {
        HttpClientExecutor executor = new HttpClientExecutor(customerInfoUrl+"/0d4b2ada0261d0281ec97e4eb498a9c7");
        CustomerDtoResponseHandler responseHandler = new CustomerDtoResponseHandler();
        executor.contentType("application/json");

        executor.execute(responseHandler);
        return responseHandler.getDto();
    }

    @Override
    public String pay(String accessToken,HttpServletRequest request) {
        if (StringUtils.isEmpty(accessToken)) {
            return null;
        } else {


            HttpClientExecutor executor = new HttpClientPostExecutor(payUrl);
            JSONObject data = new JSONObject();

            data.put("accessToken", accessToken);
            data.put("customerOpenId","6c20341b522bbd690764dc05d0d73789");
            data.put("machineNum","10011014799900375250386");
            data.put("shopNum","de71d2e9153a4b11871c6a8b482b2fd62afaa564");
            data.put("requestNum",System.currentTimeMillis()+""+System.currentTimeMillis());
            data.put("amount","0.01");
            data.put("source","API");
            data.put("tableNum","15");
            data.put("callbackUrl","http://www.baidu.com");


            JSONObject extraInfo = new JSONObject();

            extraInfo.put("pickNum","071");
            extraInfo.put("menuList","青椒鸡蛋40.00*2,农家小炒肉50.00*3");

            data.put("extraInfo",extraInfo.toString());


            Map<String,String> headers = new HashMap<>();

            Map<String,String> tokenParams = new HashMap<>();

            String accessKey = "35649fdf38be45858ba7ea1de9404e0bd58c9846";
            String secretKey = "12f3d4f076944921857ff10def2ef3c974d6c04f";//根据accessKey从passport_center库的key_pair表中查询

            String timestamp = System.currentTimeMillis()+"";

            String path = "/v1/payurl/create";

            headers.put("accessToken",accessToken);
            headers.put("accessKey",accessKey);
            headers.put("timestamp",timestamp);


            tokenParams.put("timestamp",timestamp);
            tokenParams.put("secretKey",secretKey);
            tokenParams.put("path",path);
            tokenParams.put("body",data.toString());

            String token = TokenUtil.generateToken(tokenParams);

            headers.put("token",token);
            executor.addHeaders(headers);
            executor.contentType("application/json");
            logger.info("data is {}",data.toString());
            PayResponseHandler payResponseHandler = new PayResponseHandler();
            executor.setPostBody(data.toString());
            executor.execute(payResponseHandler);

            return payResponseHandler.getDto().getOriginalText();
        }

    }

    @Override
    public AccessTokenDto retrieveAccessTokenDto(AuthAccessTokenDto tokenDto) {
        final String fullUri = tokenDto.getAccessTokenUri();
        logger.debug("Get access_token URL: {}", fullUri);

        return loadAccessTokenDto(fullUri, tokenDto.getAuthCodeParams());
    }

    @Override
    public AuthAccessTokenDto createAuthAccessTokenDto(AuthCallbackDto callbackDto) {
        return new AuthAccessTokenDto().setAccessTokenUri(accessTokenUri).setCode(callbackDto.getCode());
    }

    @Override
    public UserDto loadUnityUserDto(String accessToken) {
        logger.debug("Load Unity-User_Info by access_token = {}", accessToken);

        if (StringUtils.isEmpty(accessToken)) {
            return new UserDto("Illegal 'access_token'", "'access_token' is empty");
        } else {
            HttpClientExecutor executor = new HttpClientExecutor(unityUserInfoUri);
            executor.addRequestParam("access_token", accessToken);

            UserDtoResponseHandler responseHandler = new UserDtoResponseHandler();
            executor.execute(responseHandler);

            return responseHandler.getUserDto();
        }

    }



    @Override
    public AccessTokenDto retrievePasswordAccessTokenDto(AuthAccessTokenDto authAccessTokenDto) {
        final String fullUri = authAccessTokenDto.getAccessTokenUri();
        logger.debug("Get [password] access_token URL: {}", fullUri);

        return loadAccessTokenDto(fullUri, authAccessTokenDto.getAccessTokenParams());
    }

    @Override
    public AccessTokenDto refreshAccessTokenDto(RefreshAccessTokenDto refreshAccessTokenDto) {
        final String fullUri = refreshAccessTokenDto.getRefreshAccessTokenUrl();
        logger.debug("Get refresh_access_token URL: {}", fullUri);

        return loadAccessTokenDto(fullUri, refreshAccessTokenDto.getRefreshTokenParams());
    }

    @Override
    public AccessTokenDto retrieveCredentialsAccessTokenDto(AuthAccessTokenDto authAccessTokenDto) {
        final String uri = authAccessTokenDto.getAccessTokenUri();
        logger.debug("Get [{}] access_token URL: {}", authAccessTokenDto.getGrantType(), uri);

        return loadAccessTokenDto(uri, authAccessTokenDto.getCredentialsParams());
    }


    private AccessTokenDto loadAccessTokenDto(String fullUri, Map<String, String> params) {

        HttpClientExecutor executor = new HttpClientPostExecutor(fullUri);
        for (String key : params.keySet()) {
            //
            String value = params.get(key);

            if ("client_id".equals(key)) {
                key = "appNum";
            }

            if ("client_secret".equals(key)) {
                key = "appSecretKey";
            }

            if ("grant_type".equals(key)) {
                key = "grantType";
            }

            if ("redirect_uri".equals(key)) {
                key = "redirectUrl";
            }

            logger.info("key is {} and value is {}", key, value);
            executor.addRequestParam(key, value);
        }

        AccessTokenResponseHandler responseHandler = new AccessTokenResponseHandler();
        executor.execute(responseHandler);

        return responseHandler.getAccessTokenDto();
    }
}
