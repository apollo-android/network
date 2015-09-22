package com.apollo_lib.samples.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;

import com.apollo_lib.network.Http;
import com.apollo_lib.samples.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppCompatTextView textView = (AppCompatTextView)findViewById(R.id.text);

        textView.setText(Http.helloWorld("Samples"));
    }
}
