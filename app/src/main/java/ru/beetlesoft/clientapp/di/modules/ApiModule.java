package ru.beetlesoft.clientapp.di.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import ru.beetlesoft.clientapp.app.VkApi;

@Module(includes = {RetrofitModule.class})
public class ApiModule {
    @Provides
    @Singleton
    public VkApi provideApi(Retrofit retrofit) {
        return retrofit.create(VkApi.class);
    }
}
