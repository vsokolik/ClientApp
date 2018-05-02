package ru.beetlesoft.clientapp.ui.fragments;

import android.content.Context;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.beetlesoft.clientapp.R;
import ru.beetlesoft.clientapp.utils.FileUtils;

public class AudioFragment extends Fragment{

    @BindView(R.id.btn_record)
    Button btnRecord;

    private Context context;
    private MediaRecorder mediaRecorder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_audio, container, false);
        ButterKnife.bind(this, rootView);

        this.context = this.getActivity();

        btnRecord.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch(motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        startRecording();
                        return true;
                    case MotionEvent.ACTION_UP:
                        // RELEASED
                        stopRecording();
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
            File file = FileUtils.createAudioFile();
            mediaRecorder.setOutputFile(file.getAbsolutePath());
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
        try{
            mediaRecorder.stop();
            mediaRecorder.release();
        }catch (Exception e){
            e.printStackTrace();
        }
        mediaRecorder = null;
        Toast.makeText(context, getString(R.string.record_audio_success), Toast.LENGTH_SHORT).show();
    }
}
