package com.apollo_lib.network;

public class HttpHeaderField {

    private String fieldName;
    private String value;

    public String getFieldName() {
        return fieldName;
    }

    public String getValue() {
        return value;
    }

    public HttpHeaderField(String name, String value) {
        this.fieldName = name;
        this.value = value;
    }
}
