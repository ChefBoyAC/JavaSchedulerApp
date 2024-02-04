package com.example.javaschedulerapp;

import android.content.Context;
import android.content.SharedPreferences;

public class ItemsSharedPreferences {
    private static final String PREF_NAME = "ItemsPreferences";
    private static final String KEY_ITEM_LIST = "itemList";

    private final SharedPreferences preferences;

    public ItemsSharedPreferences(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveItemList(String itemListJson) {
        preferences.edit().putString(KEY_ITEM_LIST, itemListJson).apply();
    }

    public String getItemList() {
        return preferences.getString(KEY_ITEM_LIST, null);
    }

    public void clearItemList() {
        preferences.edit().remove(KEY_ITEM_LIST).apply();
    }
}
