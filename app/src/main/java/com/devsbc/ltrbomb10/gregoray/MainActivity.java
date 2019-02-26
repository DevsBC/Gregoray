package com.devsbc.ltrbomb10.gregoray;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    WebView gregoWeb;
    ProgressBar progressBar;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).hide();
        }

        String url = "https://gregoray.com/";
        gregoWeb = findViewById(R.id.webView);
        gregoWeb.getSettings().setJavaScriptEnabled(true);

        progressBar = findViewById(R.id.progressBar);

        gregoWeb.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                boolean overrideUrlLoading = false;

                if (url != null && url.startsWith("whatsapp://")) {

                    view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));

                    overrideUrlLoading = true;

                } else {

                    view.loadUrl(url);
                }

                return overrideUrlLoading;
            }
        });
        gregoWeb.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int progress) {
                progressBar.setProgress(0);
                progressBar.setVisibility(View.VISIBLE);
                MainActivity.this.setProgress(progress * 1000);

                progressBar.incrementProgressBy(progress);

                if (progress == 100) {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        gregoWeb.loadUrl(url);

    }

    @Override
    public void onBackPressed()
    {
        if (gregoWeb.canGoBack())
        {
            gregoWeb.goBack();
        }
        else
        {
            super.onBackPressed();
        }
    }

}
