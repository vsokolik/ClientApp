package ru.beetlesoft.clientapp.utils;

import android.app.Activity;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import ru.beetlesoft.clientapp.BuildConfig;

public class FileUtils {

    private static final int BUFFER_SIZE = 2048;

    public static Uri generateFileUri(Activity context, File file) {
        return FileProvider.getUriForFile(context,
                BuildConfig.APPLICATION_ID + ".provider",
                file);
    }

    private static File getParentDir() {
        File parentDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "ClientApp");
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }
        return parentDir;
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

    public static File createZipFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String audioFileName = "ARCHIVE_" + timeStamp + "_";
        File storageDir = getParentDir();
        return File.createTempFile(
                audioFileName,  /* prefix */
                ".zip",         /* suffix */
                storageDir      /* directory */
        );
    }

    public static void zipAudio(File audioFile) {
        try {
            BufferedInputStream origin;
            FileOutputStream dest = new FileOutputStream(createZipFile().getAbsolutePath());
            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));

            byte data[] = new byte[BUFFER_SIZE];


            FileInputStream fi = new FileInputStream(audioFile.getAbsolutePath());
            origin = new BufferedInputStream(fi, BUFFER_SIZE);
            ZipEntry entry = new ZipEntry(audioFile.getAbsolutePath().substring(audioFile.getAbsolutePath().lastIndexOf("/") + 1));
            out.putNextEntry(entry);
            int count;
            while ((count = origin.read(data, 0, BUFFER_SIZE)) != -1) {
                out.write(data, 0, count);
            }
            origin.close();


            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
