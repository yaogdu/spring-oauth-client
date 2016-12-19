package com.andaily.springoauth.service.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * http://localhost:8080/spring-oauth-server/oauth/token?client_id=mobile-client&client_secret=mobile&grant_type=refresh_token&refresh_token=b36f4978-a172-4aa8-af89-60f58abe3ba1
 *
 * @author Shengzhao Li
 */
public class RefreshAccessTokenDto implements Serializable {

    private String refreshAccessTokenUrl;
    private String appNum;
    private String appSecretKey;

    private String grantType = "refresh_token";

    private String refreshToken;


    public String getRefreshAccessTokenUrl() {
        return refreshAccessTokenUrl;
    }

    public void setRefreshAccessTokenUrl(String refreshAccessTokenUrl) {
        this.refreshAccessTokenUrl = refreshAccessTokenUrl;
    }

    public String getAppNum() {
        return appNum;
    }

    public void setAppNum(String appNum) {
        this.appNum = appNum;
    }

    public String getAppSecretKey() {
        return appSecretKey;
    }

    public void setAppSecretKey(String appSecretKey) {
        this.appSecretKey = appSecretKey;
    }

    public RefreshAccessTokenDto() {

    }



    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    /*
    * http://localhost:8080/spring-oauth-server/oauth/token?client_id=mobile-client&client_secret=mobile&grant_type=refresh_token&refresh_token=b36f4978-a172-4aa8-af89-60f58abe3ba1
    * */
    public Map<String, String> getRefreshTokenParams() {
        Map<String, String> map = new HashMap<>();
        map.put("appNum", appNum);

        map.put("appSecretKey", appSecretKey);
        map.put("grantType", grantType);
        map.put("refreshToken", refreshToken);

        return map;
    }

}