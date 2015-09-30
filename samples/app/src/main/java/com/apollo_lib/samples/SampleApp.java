package com.apollo_lib.samples;

import android.app.Application;

import com.apollo_lib.network.Http;

public class SampleApp extends Application{
    @Override
    public void onCreate() {
        super.onCreate();

        Http.getInterceptors().add(new ChangeUrlInterceptor());

    }
}
