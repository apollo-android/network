package com.apollo_lib.samples.ui.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.apollo_lib.network.Http;
import com.apollo_lib.network.HttpInterceptor;
import com.apollo_lib.network.HttpRequestConfig;
import com.apollo_lib.network.HttpResult;
import com.apollo_lib.network.HttpStatusCode;
import com.apollo_lib.samples.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "SAMPLES";

    private String error = "";

    private boolean ignore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView getText = (TextView)findViewById(R.id.get);
        final TextView postText = (TextView)findViewById(R.id.post);
        final TextView putText = (TextView)findViewById(R.id.put);
        final TextView deleteText = (TextView)findViewById(R.id.delete);
        final TextView errorText = (TextView)findViewById(R.id.error);
        final TextView headerText = (TextView)findViewById(R.id.header);
        final TextView callInterceptor = (TextView)findViewById(R.id.callInterceptor);

        Http.toogleInterceptors();

        final Switch interceptors = (Switch)findViewById(R.id.interceptors);
        final Switch ignoreSwitch = (Switch)findViewById(R.id.ignore);

        interceptors.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Http.toogleInterceptors();
            }
        });

        ignoreSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ignore = isChecked;
            }
        });

        Button send = (Button)findViewById(R.id.send);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getText.setText("GET...................");
                postText.setText("POST................");
                putText.setText("PUT...................");
                deleteText.setText("DELETE............");
                headerText.setText("HEADER............");
                callInterceptor.setText("CALL INTERCEPTOR......");

                final AsyncTask<Void, Void, HttpResult> taskGet = new AsyncTask<Void, Void, HttpResult>() {
                    @Override
                    protected HttpResult doInBackground(Void... params) {
                        Http.Builder builder = new Http.Builder("http://server.apollo-lib.com/data");

                        try {
                            HttpRequestConfig config = builder.get();

                            return Http.send(config);
                        } catch (Exception e) {
                            error = e.getMessage();
                        }

                        return null;
                    }

                    @Override
                    protected void onPostExecute(HttpResult s) {
                        if (!error.isEmpty()) {
                            errorText.setText(error);
                        }
                        else {

                            getText.setText(getText.getText().toString() + s.getStatus());
                        }
                    }
                };

                final AsyncTask<Void, Void, HttpResult> taskPost = new AsyncTask<Void, Void, HttpResult>() {
                    @Override
                    protected HttpResult doInBackground(Void... params) {
                        Http.Builder builder = new Http.Builder("http://server.apollo-lib.com/status/301");

                        try {
                            JSONObject json = new JSONObject();

                            json.accumulate("title", "foo");
                            json.accumulate("body", "bar");
                            json.accumulate("userId", 1);

                            HttpRequestConfig config = builder.post(json.toString());

                            return Http.send(config);
                        } catch (Exception e) {
                            error = e.getMessage();
                        }

                        return null;
                    }

                    @Override
                    protected void onPostExecute(HttpResult s) {
                        if (!error.isEmpty()) {
                            errorText.setText(error);
                        }
                        else {

                            postText.setText(postText.getText().toString() + s.getStatus());
                        }
                    }
                };

                final AsyncTask<Void, Void, HttpResult> taskPut = new AsyncTask<Void, Void, HttpResult>() {
                    @Override
                    protected HttpResult doInBackground(Void... params) {
                        Http.Builder builder = new Http.Builder("http://server.apollo-lib.com/data");

                        try {
                            JSONObject json = new JSONObject();

                            json.accumulate("title", "foo");
                            json.accumulate("body", "bar");
                            json.accumulate("userId", 1);

                            HttpRequestConfig config = builder.put(json.toString());

                            return Http.send(config);
                        } catch (Exception e) {
                            error = e.getMessage();
                        }

                        return null;
                    }

                    @Override
                    protected void onPostExecute(HttpResult s) {
                        if (!error.isEmpty()) {
                            errorText.setText(error);
                        }
                        else {

                            putText.setText(putText.getText().toString() + s.getStatus());
                        }
                    }
                };

                final AsyncTask<Void, Void, HttpResult> taskDelete = new AsyncTask<Void, Void, HttpResult>() {
                    @Override
                    protected HttpResult doInBackground(Void... params) {
                        Http.Builder builder = new Http.Builder("http://server.apollo-lib.com/data");

                        try {
                            HttpRequestConfig config = builder.delete();

                            return Http.send(config);
                        } catch (Exception e) {
                            error = e.getMessage();
                        }

                        return null;
                    }

                    @Override
                    protected void onPostExecute(HttpResult s) {
                        if (!error.isEmpty()) {
                            errorText.setText(error);
                        }
                        else {
                            deleteText.setText(deleteText.getText().toString() + s.getStatus());
                        }
                    }
                };

                final AsyncTask<Void, Void, HttpResult> taskHeader = new AsyncTask<Void, Void, HttpResult>() {
                    @Override
                    protected HttpResult doInBackground(Void... params) {
                        Http.Builder builder = new Http.Builder("http://server.apollo-lib.com/data");

                        try {
                            HttpRequestConfig config = builder.get();

                            config.getInterceptors().add(new HeaderInterceptor());

                            if (!Http.interceptorsEnabled()) {
                                config.toogleInterceptors();
                            }

                            config.addHeader("test", "test");

                            return Http.send(config);
                        } catch (Exception e) {
                            error = e.getMessage();
                        }

                        return null;
                    }

                    @Override
                    protected void onPostExecute(HttpResult s) {
                        if (!error.isEmpty()) {
                            errorText.setText(error);
                        }
                        else {

                            try {
                                JSONObject jsonObject =  new JSONObject(s.getResponse());

                                headerText.setText(headerText.getText().toString() + jsonObject.getJSONObject("headers").getString("test"));
                            } catch (JSONException e) {
                                headerText.setText(headerText.getText().toString() + "NOT FOUND!");
                            }
                        }
                    }
                };

                final AsyncTask<Void, Void, HttpResult> taskCallInterceptor = new AsyncTask<Void, Void, HttpResult>() {
                    @Override
                    protected HttpResult doInBackground(Void... params) {
                        Http.Builder builder = new Http.Builder("http://server.apollo-lib.com/data");

                        try {
                            HttpRequestConfig config = builder.get();

                            return Http.send(config, new HttpInterceptor() {
                                @Override
                                public void onOpening(HttpRequestConfig httpRequestConfig) {}

                                @Override
                                public void onConnecting(HttpURLConnection httpURLConnection, HttpRequestConfig httpRequestConfig) { }

                                @Override
                                public void onConnected(HttpURLConnection httpURLConnection, HttpRequestConfig httpRequestConfig) { }

                                @Override
                                public HttpResult onResult(HttpURLConnection httpURLConnection, HttpRequestConfig httpRequestConfig, HttpResult httpResult) {
                                    if (httpResult.getStatus() == HttpStatusCode.UNAUTHORIZED)
                                    {
                                        return httpResult;
                                    } else {
                                        return new HttpResult(HttpStatusCode.CREATED, httpResult.getResponse(), httpResult.getResponseError());
                                    }
                                }
                            }, ignore);

                        } catch (Exception e) {
                            error = e.getMessage();
                        }

                        return null;
                    }

                    @Override
                    protected void onPostExecute(HttpResult s) {
                        if (!error.isEmpty()) {
                            errorText.setText(error);
                        }
                        else {
                            if (s.getStatus() == HttpStatusCode.OK) {
                                callInterceptor.setText(callInterceptor.getText().toString() + "NOT INTERCEPTED");
                            }
                            else {
                                if (s.getStatus() == HttpStatusCode.UNAUTHORIZED) {
                                    callInterceptor.setText(callInterceptor.getText().toString() + "INTERCEPTED BY CALL INTERCEPTOR AND OTHERS");
                                }
                                else {
                                    callInterceptor.setText(callInterceptor.getText().toString() + "INTERCEPTED BY CALL INTERCEPTOR");
                                }
                            }
                        }
                    }
                };


                taskGet.execute();
                taskPost.execute();
                taskPut.execute();
                taskDelete.execute();
                taskHeader.execute();
                taskCallInterceptor.execute();

            }
        });

    }

    public static class HeaderInterceptor implements HttpInterceptor {

        @Override
        public void onOpening(HttpRequestConfig httpRequestConfig) {
        }

        @Override
        public void onConnecting(HttpURLConnection httpURLConnection, HttpRequestConfig httpRequestConfig) {

        }

        @Override
        public void onConnected(HttpURLConnection httpURLConnection, HttpRequestConfig httpRequestConfig) {

        }

        @Override
        public HttpResult onResult(HttpURLConnection httpURLConnection, HttpRequestConfig httpRequestConfig, HttpResult httpResult) {
            return new HttpResult(httpResult.getStatus(), "{ 'headers': { 'test': 'INTERCEPTED'} }", null);
        }
    }

}
