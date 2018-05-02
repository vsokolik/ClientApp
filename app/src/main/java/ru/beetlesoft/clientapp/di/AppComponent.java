package ru.beetlesoft.clientapp.di;


import javax.inject.Singleton;

import dagger.Component;
import ru.beetlesoft.clientapp.app.ClientApp;
import ru.beetlesoft.clientapp.di.modules.ClientModule;
import ru.beetlesoft.clientapp.di.modules.ContextModule;
import ru.beetlesoft.clientapp.ui.activities.AuthActivity;
import ru.beetlesoft.clientapp.ui.fragments.AudioFragment;

@Singleton
@Component(modules = {ClientModule.class, ContextModule.class})
public interface AppComponent {

    void inject(ClientApp clientApp);

    void inject(AuthActivity authActivity);

    void inject(AudioFragment audioFragment);
}
