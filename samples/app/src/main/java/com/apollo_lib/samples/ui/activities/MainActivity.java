package com.apollo_lib.samples.ui.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.apollo_lib.network.Http;
import com.apollo_lib.network.HttpRequestConfig;
import com.apollo_lib.network.HttpResult;
import com.apollo_lib.samples.R;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "SAMPLES";

    private String error = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView getText = (TextView)findViewById(R.id.get);
        final TextView postText = (TextView)findViewById(R.id.post);
        final TextView putText = (TextView)findViewById(R.id.put);
        final TextView deleteText = (TextView)findViewById(R.id.delete);
        final TextView errorText = (TextView)findViewById(R.id.error);

        AsyncTask<Void, Void, HttpResult> taskGet = new AsyncTask<Void, Void, HttpResult>() {
            @Override
            protected HttpResult doInBackground(Void... params) {
                Http.Builder builder = new Http.Builder("http://jsonplaceholder.typicode.com/posts/1");

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

        AsyncTask<Void, Void, HttpResult> taskPost = new AsyncTask<Void, Void, HttpResult>() {
            @Override
            protected HttpResult doInBackground(Void... params) {
                Http.Builder builder = new Http.Builder("http://jsonplaceholder.typicode.com/posts");

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

        AsyncTask<Void, Void, HttpResult> taskPut = new AsyncTask<Void, Void, HttpResult>() {
            @Override
            protected HttpResult doInBackground(Void... params) {
                Http.Builder builder = new Http.Builder("http://jsonplaceholder.typicode.com/posts/1");

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

        AsyncTask<Void, Void, HttpResult> taskDelete = new AsyncTask<Void, Void, HttpResult>() {
            @Override
            protected HttpResult doInBackground(Void... params) {
                Http.Builder builder = new Http.Builder("http://jsonplaceholder.typicode.com/posts/1");

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

        taskGet.execute();
        taskPost.execute();
        taskPut.execute();
        taskDelete.execute();
    }

}
