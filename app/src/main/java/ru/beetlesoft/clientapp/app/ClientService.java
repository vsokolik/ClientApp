package ru.beetlesoft.clientapp.app;

import retrofit2.Call;

public class ClientService {

    private VkApi vkApi;

    public ClientService(VkApi vkApi) {
        this.vkApi = vkApi;
    }

    public Call<String> signIn(String clientId) {
        return vkApi.auth(clientId);
    }
}
