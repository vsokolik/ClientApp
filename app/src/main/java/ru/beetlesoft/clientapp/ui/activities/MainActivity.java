package ru.beetlesoft.clientapp.ui.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import butterknife.ButterKnife;
import ru.beetlesoft.clientapp.R;
import ru.beetlesoft.clientapp.constant.ActionPosition;
import ru.beetlesoft.clientapp.ui.fragments.GeofenceFragment;
import ru.beetlesoft.clientapp.ui.fragments.MainFragment;
import ru.beetlesoft.clientapp.ui.fragments.SoundFragment;

public class MainActivity extends BaseActivity implements MainFragment.OnChangeFragmentListener {

    private static final int PERMISSION_REQUEST_CODE = 90;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.RECORD_AUDIO,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        PERMISSION_REQUEST_CODE);
            }

        } else {
            changeFragment(ActionPosition.MAIN);
        }


    }

    @Override
    public void changeFragment(int actionPosition) {
        FragmentTransaction tr = getSupportFragmentManager().beginTransaction();
        switch (actionPosition) {
            case ActionPosition.MAIN:
                MainFragment mainFragment = new MainFragment();
                tr.add(R.id.fr_container, mainFragment);
                tr.commit();
                break;

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


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length == 3 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED
                    && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                changeFragment(ActionPosition.MAIN);
            } else {
                Toast.makeText(this, getString(R.string.error_permission), Toast.LENGTH_SHORT).show();
                finishAffinity();
            }
        }
    }
}
