package ru.beetlesoft.clientapp.di;


import javax.inject.Singleton;

import dagger.Component;
import ru.beetlesoft.clientapp.app.ClientApp;
import ru.beetlesoft.clientapp.di.modules.ClientModule;
import ru.beetlesoft.clientapp.di.modules.ContextModule;
import ru.beetlesoft.clientapp.services.GeofenceTransitionsIntentService;
import ru.beetlesoft.clientapp.ui.activities.AuthActivity;
import ru.beetlesoft.clientapp.ui.fragments.AudioFragment;
import ru.beetlesoft.clientapp.ui.fragments.MainFragment;

@Singleton
@Component(modules = {ClientModule.class, ContextModule.class})
public interface AppComponent {

    void inject(ClientApp clientApp);

    void inject(AuthActivity authActivity);

    void inject(AudioFragment audioFragment);

    void inject(MainFragment mainFragment);

    void inject(GeofenceTransitionsIntentService geofenceTransitionsIntentService);
}
