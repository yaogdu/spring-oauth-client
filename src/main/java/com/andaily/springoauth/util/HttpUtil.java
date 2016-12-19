package com.andaily.springoauth.util;

import com.alibaba.fastjson.JSONObject;
import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClient.BoundRequestBuilder;
import com.ning.http.client.ListenableFuture;
import com.ning.http.client.Response;

import java.util.concurrent.ExecutionException;

public class HttpUtil {

    private AsyncHttpClient asyncHttpClient;


    

    public AsyncHttpClient getAsyncHttpClient() {
        return asyncHttpClient;
    }



    public void setAsyncHttpClient(AsyncHttpClient asyncHttpClient) {
        this.asyncHttpClient = asyncHttpClient;
    }



    public JSONObject request(String url) {
        JSONObject result = new JSONObject();


        ListenableFuture<JSONObject> f = getAsyncHttpClient().prepareGet(url).addHeader("Content-Type",
                "application/json;charset=utf-8")
                .execute(new AsyncCompletionHandler<JSONObject>() {
                    JSONObject result = new JSONObject();

                    @Override
                    public JSONObject onCompleted(Response response) throws Exception {
                        result.put("key", response.getResponseBody());
                        result.put("success", true);
                        return result;
                    }

                    @Override
                    public void onThrowable(Throwable t) {
                        result.put("success", false);

                    }
                });
        try {
            result = f.get();
            result.put("success", true);
        }
        catch (InterruptedException | ExecutionException e) {

            result.put("success", false);
        }

        return result;
    }
    
    public JSONObject requestPost(String url,String data) {
        JSONObject result = new JSONObject();

        BoundRequestBuilder builder = getAsyncHttpClient().preparePost(url).addHeader("Content-Type","application/json;charset=utf-8");
        builder.setBody(data);
        ListenableFuture<JSONObject> f = builder
                .execute(new AsyncCompletionHandler<JSONObject>() {
                    JSONObject result = new JSONObject();

                    @Override
                    public JSONObject onCompleted(Response response) throws Exception {
                        result.put("key", response.getResponseBody());
                        return result;
                    }

                    @Override
                    public void onThrowable(Throwable t) {
                        result.put("key", "error");

                    }
                });
        try {
            result = f.get();
            result.put("success", true);
        }
        catch (InterruptedException | ExecutionException e) {

            result.put("success", false);
        }

        return result;
    }

}
