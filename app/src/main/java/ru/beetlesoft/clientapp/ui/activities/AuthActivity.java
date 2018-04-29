package ru.beetlesoft.clientapp.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.beetlesoft.clientapp.R;
import ru.beetlesoft.clientapp.app.ClientApp;
import ru.beetlesoft.clientapp.app.ClientService;
import ru.beetlesoft.clientapp.constant.VkConstants;

public class AuthActivity extends BaseActivity {

    private static String TAG = "AuthActivity";
    private static final int REQUEST_GET_TOKEN = 101;

    @Inject
    ClientService clientService;

    private Context context;

    @BindView(R.id.btn_auth)
    Button btnAuth;
    @BindView(R.id.tv_description)
    TextView tvDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ClientApp.getAppComponent().inject(this);

        setContentView(R.layout.activity_auth);
        ButterKnife.bind(this);
        this.context = this;

        btnAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra(WebViewActivity.KEY_URL, VkConstants.AUTH_URL);
                startActivityForResult(intent, REQUEST_GET_TOKEN);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        switch (requestCode) {
            case REQUEST_GET_TOKEN:
                if (resultCode == RESULT_OK) {
                    startActivityAndFinish(MainActivity.class);
                } else {
                    tvDescription.setText("Что-то пошло не так");
                }
                break;
            default:
                break;
        }
    }
}
