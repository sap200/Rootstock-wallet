package com.example.rootstockwallet;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import rootstock.RootStock;

public class ActivityView extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view);

        webView = findViewById(R.id.my_new_view);

        // Enable JavaScript
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);  // Enable DOM storage
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(true); // Hide zoom controls
        webSettings.setLoadWithOverviewMode(true); // Zoom out if the content width exceeds the WebView width
        webSettings.setUseWideViewPort(true); // Enable viewport meta tag
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        String userAgent = "Mozilla/5.0 (Linux; Android 10; Pixel 4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.9999.99 Mobile Safari/537.36";
        webSettings.setUserAgentString(userAgent);


        // Load a URL
        webView.setWebViewClient(new WebViewClient());
        String url = getIntent().getStringExtra("url");
        System.out.println("url:" + url);

        RootStock rootStock = new RootStock(ActivityView.this);
        webView.loadUrl("https://explorer.testnet.rootstock.io/address/" + rootStock.getAddress());


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_view_manage), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}