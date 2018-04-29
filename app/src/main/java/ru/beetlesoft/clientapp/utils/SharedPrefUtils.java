package ru.beetlesoft.clientapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

/**
 * Created by alond on 09.01.18.
 */

public class SharedPrefUtils {
    public static final String NAME_SHARED_PREFERENCES = "ru.beetlesoft.clientapp.utils";

    public SharedPrefUtils() {
    }

    public static void saveString(Context context, String key, String value) {
        SharedPreferences.Editor editor = getSharedEditor(context, key);
        editor.putString(key, value);
        editor.apply();
    }

    private static SharedPreferences.Editor getSharedEditor(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(NAME_SHARED_PREFERENCES, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(sharedPreferences.contains(key)) {
            editor.remove(key);
        }

        return editor;
    }

    public static String getString(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(NAME_SHARED_PREFERENCES, 0);
        return sharedPreferences.getString(key, "");
    }
}