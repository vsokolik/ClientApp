package ru.beetlesoft.clientapp.app;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

public class ClientService {

    private VkApi vkApi;

    public ClientService(VkApi vkApi) {
        this.vkApi = vkApi;
    }

    public Call<String> getDocsWallUploadServer() {
        return vkApi.getDocsWallUploadServer();
    }

    public Call<String> getPhotosWallUploadServer() {
        return vkApi.getPhotosWallUploadServer();
    }

    public Call<String> uploadPhoto(String uploadUrl, String filePath) {
        return upload(uploadUrl, filePath, "photo");
    }

    public Call<String> uploadDocument(String uploadUrl, String filePath) {
        return upload(uploadUrl, filePath, "file");
    }

    private Call<String> upload(String uploadUrl, String filePath, String nameField) {
        File file = new File(filePath);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData(nameField, file.getName(), requestFile);
        return vkApi.upload(uploadUrl, body);
    }

    public Call<String> savePhoto(int server, String hash, String photo) {
        return vkApi.savePhoto(server, hash, photo);
    }

    public Call<String> wallPost(String attachments, double latitude, double longitude) {
        return vkApi.wallPost(attachments, latitude, longitude);
    }


}
