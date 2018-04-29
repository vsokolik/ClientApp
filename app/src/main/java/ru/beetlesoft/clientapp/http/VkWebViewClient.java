package ru.beetlesoft.clientapp.http;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import ru.beetlesoft.clientapp.constant.VkConstants;

public class VkWebViewClient extends WebViewClient {

    private VkAuthResponse authResponse;

    public VkWebViewClient(VkAuthResponse response) {
        this.authResponse = response;
    }


    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        return overrideUrlLoading(view, request.getUrl().toString());

    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return overrideUrlLoading(view, url);
    }

    private boolean overrideUrlLoading(WebView webView, String url){
        if (url.startsWith(VkConstants.CALLBACK_URL) & (!url.contains("error"))) {
            authResponse.onComplete(url);
            return true;
        } else if (url.contains("error")) {
            authResponse.onError();
            return false;
        } else {
            webView.loadUrl(url);
            return true;
        }
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        if (url.contains("error")) {
            authResponse.onError();
        } else if (url.contains("access_token")) {
            authResponse.onComplete(url);
        }
    }


    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
        authResponse.onError();
    }
}
