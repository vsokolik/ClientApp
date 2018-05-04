package ru.beetlesoft.clientapp.ui.fragments;

import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.widget.Toast;

import ru.beetlesoft.clientapp.R;

public class BaseFragment extends Fragment {

    protected void showToast(String message) {
        if(TextUtils.isEmpty(message)){
            message = getString(R.string.unknow_error);
        }
        Toast.makeText(this.getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
