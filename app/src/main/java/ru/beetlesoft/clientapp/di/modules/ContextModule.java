package ru.beetlesoft.clientapp.di.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.beetlesoft.clientapp.app.ClientApp;

@Module
public class ContextModule {
	private Context context;

	public ContextModule(Context context) {
		this.context = context;
	}

	@Provides
	@Singleton
	public Context provideContext() {
		return context;
	}

	@Provides
	@Singleton
	public ClientApp provideApp() {
		return (ClientApp) context;
	}
}
