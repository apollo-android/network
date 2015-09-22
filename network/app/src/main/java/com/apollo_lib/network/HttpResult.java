package com.apollo_lib.network;

public class HttpResult {
    private int status;
    private String response;
    private String responseError;

    public HttpResult(int status, String response, String responseError) {
        this.status = status;
        this.response = response;
        this.responseError = responseError;
    }

    public int getStatus() {
        return status;
    }

    public String getResponse() {
        return response;
    }

    public String getResponseError() {
        return responseError;
    }
}