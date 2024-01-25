package com.example.javaschedulerapp;

import android.content.Context;
import android.content.SharedPreferences;

public class ClassSharedPreferences {
    private static final String PREF_NAME = "ClassPreferences";
    private static final String KEY_CLASS_LIST = "classList";

    private final SharedPreferences preferences;

    public ClassSharedPreferences(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveClassList(String classListJson) {
        preferences.edit().putString(KEY_CLASS_LIST, classListJson).apply();
    }

    public String getClassList() {
        return preferences.getString(KEY_CLASS_LIST, null);
    }
}
