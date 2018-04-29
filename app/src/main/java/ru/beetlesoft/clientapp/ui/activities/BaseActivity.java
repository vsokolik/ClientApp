package ru.beetlesoft.clientapp.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void startActivityAndFinish(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivityAndFinish(intent);
    }

    public void startActivityAndFinish(Intent intent) {
        startActivity(intent);
        this.finish();
    }

    public void startActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }
}
