package ru.beetlesoft.clientapp.app;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface VkApi {

//авторизация
    @GET("authorize?display=page&redirect_uri=https://oauth.vk.com/blank.html&scope=photos&response_type=token&v=5.74")
    Call<String> auth(@Query("client_id") String clientId);
}
