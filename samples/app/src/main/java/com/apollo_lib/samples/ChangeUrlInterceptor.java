package com.apollo_lib.samples;

import com.apollo_lib.network.HttpInterceptor;
import com.apollo_lib.network.HttpRequestConfig;
import com.apollo_lib.network.HttpResult;

import java.net.HttpURLConnection;

public class ChangeUrlInterceptor implements HttpInterceptor {

    @Override
    public void onOpening(HttpRequestConfig httpRequestConfig) {
        httpRequestConfig.setUrl("http://server.apollo-lib.com/status/401");
    }

    @Override
    public void onConnecting(HttpURLConnection httpURLConnection, HttpRequestConfig httpRequestConfig) {

    }

    @Override
    public void onConnected(HttpURLConnection httpURLConnection, HttpRequestConfig httpRequestConfig) {

    }

    @Override
    public HttpResult onResult(HttpURLConnection httpURLConnection, HttpRequestConfig httpRequestConfig, HttpResult httpResult) {
        return httpResult;
    }
}
