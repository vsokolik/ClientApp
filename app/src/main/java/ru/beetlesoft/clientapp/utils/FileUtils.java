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
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "Camera");
        return File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
    }

    public static File createAudioFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String audioFileName = "AUDIO_" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "Camera");
//        File storageDir = createParentDir();
        return File.createTempFile(
                audioFileName,  /* prefix */
                ".m4a",         /* suffix */
                storageDir      /* directory */
        );
    }

//    private static File createParentDir() throws IOException {
//        File file = new File(Environment.getExternalStorageDirectory(), "ClientApp");
//        if (!file.getParentFile().exists()) {
//            file.getParentFile().mkdirs();
//        }
//        if (!file.exists()) {
//            file.createNewFile();
//        }
//        return file;
//    }
}
