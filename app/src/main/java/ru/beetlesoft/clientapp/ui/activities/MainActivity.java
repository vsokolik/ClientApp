package ru.beetlesoft.clientapp.ui.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.beetlesoft.clientapp.R;
import ru.beetlesoft.clientapp.ui.fragments.MainFragment;
import ru.beetlesoft.clientapp.ui.fragments.SoundFragment;

public class MainActivity extends BaseActivity implements MainFragment.OnChangeFragmentListener{

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
    public void changeFragment(int fragmentId) {
        if(fragmentId == 0){
            FragmentTransaction tr = getSupportFragmentManager().beginTransaction();
            SoundFragment soundFragment = new SoundFragment();
            tr.addToBackStack("SoundRecord");
            tr.replace(R.id.fr_container, soundFragment);
            tr.commit();
        }
    }
}
