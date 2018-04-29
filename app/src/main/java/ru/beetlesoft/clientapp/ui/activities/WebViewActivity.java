package ru.beetlesoft.clientapp.ui.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebView;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import ru.beetlesoft.clientapp.constant.SharedPrefKeys;
import ru.beetlesoft.clientapp.http.VkAuthResponse;
import ru.beetlesoft.clientapp.http.VkWebViewClient;
import ru.beetlesoft.clientapp.utils.SharedPrefUtils;

public class WebViewActivity extends Activity {
    public static final String KEY_URL = "URL";
    private static final String TAG = "WebViewActivity";
    private WebView webView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        webView = new WebView(this);
        webView.getSettings().setJavaScriptEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (0 != (getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE)) {
                WebView.setWebContentsDebuggingEnabled(true);
            }
        }
        webView.setWebViewClient(new VkWebViewClient(
                new VkAuthResponse() {
                    @Override
                    public void onComplete(String url) {
                        saveData(url);
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        finish();
                    }

                    @Override
                    public void onError() {
                        Intent intent = new Intent();
                        setResult(RESULT_CANCELED, intent);
                        finish();
                    }
                })
        );

        this.setContentView(webView);

        String url = this.getIntent().getStringExtra(KEY_URL);

        Log.d(TAG, url);
        webView.loadUrl(url);
    }

    @Override
    public boolean onKeyDown(final int keyCode, final KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void saveData(String urlString){
        try {
            URL url = new URL(urlString);
            Map<String, String> mapParams = new HashMap<>();
            String[] pairs = url.getRef().split("&");
            for (String pair : pairs) {
                int idx = pair.indexOf("=");
                String key;

                key = idx > 0 ? URLDecoder.decode(pair.substring(0, idx)) : pair;
                String value = idx > 0 && pair.length() > idx + 1 ? URLDecoder.decode(pair.substring(idx + 1)) : null;

                if (!mapParams.containsKey(key)) {
                    mapParams.put(key, value);
                }
            }
            SharedPrefUtils.saveString(this, SharedPrefKeys.TOKEN, mapParams.get("access_token"));
            SharedPrefUtils.saveString(this, SharedPrefKeys.USER_ID, mapParams.get("user_id"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
