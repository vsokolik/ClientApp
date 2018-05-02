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
        return vkApi.getWallUploadServer();
    }

    public Call<String> sendDocument(String uploadUrl, File file) {
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getAbsolutePath(), requestFile);
        return vkApi.uploadDocument(uploadUrl, body);
    }
}
