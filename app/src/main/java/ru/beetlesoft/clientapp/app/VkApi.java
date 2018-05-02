package ru.beetlesoft.clientapp.app;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface VkApi {

    //получть ссылку для загрузки документа
    @GET("docs.getWallUploadServer")
    Call<String> getWallUploadServer();

    //загрузить документ
    @Multipart
    @POST()
    Call<String> uploadDocument(@Url String uploadUrl,
                                @Part MultipartBody.Part file);

    //сохранить документ
    @GET("docs.save")
    Call<String> saveDoc(@Query("file") String file, @Query("title") String title);
}
