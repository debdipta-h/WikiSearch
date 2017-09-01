package com.xyz.debdipta.wikisearch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class ItemViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_view);
        Intent viewIntent=getIntent();
        String url=viewIntent.getStringExtra("LINK");
        WebView newView=(WebView) findViewById(R.id.webview);
        newView.loadUrl(url);

    }
}
