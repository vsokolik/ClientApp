package ru.beetlesoft.clientapp.ui.fragments;

import android.content.Context;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.beetlesoft.clientapp.R;
import ru.beetlesoft.clientapp.app.ClientApp;
import ru.beetlesoft.clientapp.app.ClientService;
import ru.beetlesoft.clientapp.constant.F;
import ru.beetlesoft.clientapp.utils.FileUtils;

public class AudioFragment extends Fragment {


    @Inject
    ClientService clientService;

    @BindView(R.id.btn_record)
    Button btnRecord;

    private Context context;
    private MediaRecorder mediaRecorder;
    private File audioFile;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ClientApp.getAppComponent().inject(this);
        View rootView = inflater.inflate(R.layout.fragment_audio, container, false);
        ButterKnife.bind(this, rootView);

        this.context = this.getActivity();

        btnRecord.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        startRecording();
                        return true;
                    case MotionEvent.ACTION_UP:
                        // RELEASED
                        stopRecording();
                        zipFileAndLoadToServer();
                        return true;
                }
                return false;
            }
        });

        return rootView;
    }

    private void startRecording() {
        //we use the MediaRecorder class to record
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);

        try {
            audioFile = FileUtils.createAudioFile();
            mediaRecorder.setOutputFile(audioFile.getAbsolutePath());
            mediaRecorder.setAudioEncoder(
                    Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                            ? MediaRecorder.AudioEncoder.HE_AAC
                            : MediaRecorder.AudioEncoder.AAC
            );
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void stopRecording() {
        try {
            mediaRecorder.stop();
            mediaRecorder.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mediaRecorder = null;
    }

    private void zipFileAndLoadToServer() {
        try {
            final File zipFile = FileUtils.zipAudio(audioFile);
            clientService.getDocsWallUploadServer().enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            JSONObject responseObj = jsonObject.getJSONObject(F.responce);
                            String uploadUrl = responseObj.getString(F.uploadUrl);

                            clientService.sendDocument(uploadUrl, zipFile).enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    Log.d("", "");
                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {

                                }
                            });
                            Log.d("", uploadUrl);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, getString(R.string.unknow_error), Toast.LENGTH_SHORT).show();
        }
    }
}
