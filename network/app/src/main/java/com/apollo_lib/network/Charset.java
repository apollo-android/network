package com.apollo_lib.network;

public enum Charset {
    UTF8("UTF-8");

    private String value;

    Charset(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
