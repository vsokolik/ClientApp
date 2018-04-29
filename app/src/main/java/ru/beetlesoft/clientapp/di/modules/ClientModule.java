package ru.beetlesoft.clientapp.di.modules;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.beetlesoft.clientapp.app.ClientService;
import ru.beetlesoft.clientapp.app.VkApi;

/**
 * Date: 8/26/2016
 * Time: 11:58
 *
 * @author Artur Artikov
 */

@Module(includes = {ApiModule.class})
public class ClientModule {
	@Provides
	@Singleton
	public ClientService provideClientService(VkApi api) {
		return new ClientService(api);
	}
}