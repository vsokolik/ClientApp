package ru.beetlesoft.clientapp.app;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface VkApi {

    //получть ссылку для загрузки документа
    @GET("docs.getWallUploadServer")
    Call<String> getDocsWallUploadServer();

    //сохранить документ
    @GET("docs.save")
    Call<String> saveDoc(@Query("file") String file,
                         @Query("title") String title
    );

    //получть ссылку для загрузки фото
    @GET("photos.getWallUploadServer")
    Call<String> getPhotosWallUploadServer();


    //загрузить фото или документ
    @Multipart
    @POST()
    Call<String> upload(@Url String uploadUrl,
                        @Part MultipartBody.Part photo
    );

    //сохранить фото
    @GET("photos.saveWallPhoto")
    Call<String> savePhoto(@Query("server") int server,
                           @Query("hash") String hash,
                           @Query("photo") String photo
    );

    //опубликовать фото
    @GET("wall.post")
    Call<String> wallPost(@Query("attachments") String attachments,
                          @Query("lat") double latitude,
                          @Query("long") double longitude
    );
}
