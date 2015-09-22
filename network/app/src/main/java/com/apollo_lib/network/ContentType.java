package com.apollo_lib.network;

public enum ContentType {
    JSON("application/json"),
    MULTPART("multipart/form-data;boundary=*****"),
    OCTECTSTREAM("application/octet-stream"),
    PDF("application/pdf"),
    PNG("image/png"),
    JPEG("image/jpeg");

    private String value;

    ContentType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}