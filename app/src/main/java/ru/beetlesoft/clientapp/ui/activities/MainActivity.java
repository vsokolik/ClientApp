package ru.beetlesoft.clientapp.ui.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import butterknife.ButterKnife;
import ru.beetlesoft.clientapp.R;
import ru.beetlesoft.clientapp.constant.ActionPosition;
import ru.beetlesoft.clientapp.ui.fragments.GeofenceFragment;
import ru.beetlesoft.clientapp.ui.fragments.MainFragment;
import ru.beetlesoft.clientapp.ui.fragments.SoundFragment;

public class MainActivity extends BaseActivity implements MainFragment.OnChangeFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        MainFragment mainFragment = new MainFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fr_container, mainFragment);
        transaction.commit();
    }

    @Override
    public void changeFragment(int actionPosition) {
        FragmentTransaction tr = getSupportFragmentManager().beginTransaction();
        switch (actionPosition) {
            case ActionPosition.SOUND:
                SoundFragment soundFragment = new SoundFragment();
                tr.addToBackStack("SoundRecord");
                tr.replace(R.id.fr_container, soundFragment);
                tr.commit();
                break;

            case ActionPosition.GEOFENCE:
                GeofenceFragment geofenceFragment = new GeofenceFragment();
                tr.addToBackStack("Geofence");
                tr.replace(R.id.fr_container, geofenceFragment);
                tr.commit();
                break;
            default:
                break;
        }
    }
}
