package ru.beetlesoft.clientapp.utils;

import android.app.Activity;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ru.beetlesoft.clientapp.BuildConfig;

public class FileUtils {

    public static Uri generateFileUri(Activity context, File file){
        return FileProvider.getUriForFile(context,
                BuildConfig.APPLICATION_ID + ".provider",
                file);
    }

    public static File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "PHOTO_" + timeStamp + "_";
        File storageDir = getParentDir();
        return File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
    }

    public static File createAudioFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String audioFileName = "AUDIO_" + timeStamp + "_";
        File storageDir = getParentDir();
        return File.createTempFile(
                audioFileName,  /* prefix */
                ".m4a",         /* suffix */
                storageDir      /* directory */
        );
    }

    private static File getParentDir(){
        File parentDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "ClientApp");
        if(!parentDir.exists()) {
            parentDir.mkdirs();
        }
        return parentDir;
    }
}
