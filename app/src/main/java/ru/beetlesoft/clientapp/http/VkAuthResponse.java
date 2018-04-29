package ru.beetlesoft.clientapp.http;

public interface VkAuthResponse {
    void onComplete(String url);
    void onError();
}
