package com.fricsoft.gocorona;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebActivity extends AppCompatActivity {

    WebView webview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        getSupportActionBar().hide();

        webview =(WebView)findViewById(R.id.webView);
        String url=getIntent().getStringExtra("url");


        webview.setWebViewClient(new WebViewClient());
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setDomStorageEnabled(true);

        webview.getSettings().setSupportMultipleWindows(true);


        webview.setOverScrollMode(WebView.OVER_SCROLL_NEVER);
        //webview.loadUrl("https://www.youtube.com/watch?v=ZzMauoEU65s");
        //webview.loadUrl("https://drive.google.com/file/d/18ovLF9iCiSpKVBD33O0Vk5ft0uflJuUR/view?usp=drivesdk");
        webview.loadUrl(url);

    }
}
