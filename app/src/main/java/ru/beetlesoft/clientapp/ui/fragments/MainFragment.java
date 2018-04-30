package ru.beetlesoft.clientapp.ui.fragments;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.beetlesoft.clientapp.R;
import ru.beetlesoft.clientapp.ui.adapters.ActionAdapter;
import ru.beetlesoft.clientapp.utils.FileUtils;

public class MainFragment extends Fragment {

    private static final int REQUEST_CODE_PHOTO = 1;
    private static final int WRITE_STORAGE_PERMISSION = 90;

    @BindView(R.id.rv_action)
    RecyclerView rvAction;

    public OnChangeFragmentListener changeFragmentListener;
    private Activity context;
    private String currentPhotoPath;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            changeFragmentListener = (OnChangeFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement OnChangeFragmentListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, rootView);
        this.context = this.getActivity();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        rvAction.setLayoutManager(linearLayoutManager);

        ActionAdapter adapter = new ActionAdapter(context, getActions());
        adapter.setRvItemClickListener(new ActionAdapter.OnRvItemClickListener() {
            @Override
            public void onClick(View view) {
                int itemPosition = rvAction.getChildLayoutPosition(view);
                action(itemPosition);
            }
        });
        rvAction.setAdapter(adapter);

        return rootView;
    }

    private List<HashMap<Integer, String>> getActions() {
        List<HashMap<Integer, String>> mapAction = new ArrayList<>();
        mapAction.add(createMap(R.drawable.ic_add_sound, getString(R.string.title_sound)));
        mapAction.add(createMap(R.drawable.ic_add_photo, getString(R.string.title_photo)));
        mapAction.add(createMap(R.drawable.ic_location, getString(R.string.title_geofence)));
        return mapAction;
    }

    private HashMap<Integer, String> createMap(Integer drawableId, String actionText) {
        HashMap<Integer, String> map = new HashMap<>();
        map.put(drawableId, actionText);
        return map;
    }

    private void action(int position) {
        switch (position) {
            case 0:
                //sound
                changeFragmentListener.changeFragment(0);
                break;
            case 1:
                //foto
                int permissionCheck = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                    startCamera();
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, WRITE_STORAGE_PERMISSION);
                    }
                }

                break;
            case 2:
                //geofence
                break;
            default:
                break;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (data == null) {
//            return;
//        }
        switch (requestCode) {
            case REQUEST_CODE_PHOTO:
                Log.d("photopath", currentPhotoPath);
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case WRITE_STORAGE_PERMISSION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    startCamera();

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(context, getString(R.string.error_permission_photo), Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    private void startCamera(){
        try {
            File image = FileUtils.createImageFile();
            currentPhotoPath = "file:" + image.getAbsolutePath();
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, FileUtils.generateFileUri(context, image));
            startActivityForResult(intent, REQUEST_CODE_PHOTO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public interface OnChangeFragmentListener {
        void changeFragment(int fragmentId);
    }
}
