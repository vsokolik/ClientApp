package ru.beetlesoft.clientapp.app;

import android.app.Application;

import ru.beetlesoft.clientapp.di.AppComponent;
import ru.beetlesoft.clientapp.di.DaggerAppComponent;
import ru.beetlesoft.clientapp.di.modules.ContextModule;
import ru.beetlesoft.clientapp.di.modules.RetrofitModule;

public class ClientApp extends Application {

    private static AppComponent appComponent;


    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder()
                .contextModule(new ContextModule(this))
                .retrofitModule(new RetrofitModule(this))
                .build();

        appComponent.inject(this);
    }


    public static AppComponent getAppComponent() {
        return appComponent;
    }
}
