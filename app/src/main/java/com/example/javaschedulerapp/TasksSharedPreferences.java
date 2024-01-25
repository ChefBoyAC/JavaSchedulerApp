package com.example.javaschedulerapp;

import android.content.Context;
import android.content.SharedPreferences;

public class TasksSharedPreferences {
    private static final String PREF_NAME = "TaskPreferences";
    private static final String KEY_TASK_LIST = "taskList";

    private final SharedPreferences preferences;

    public TasksSharedPreferences(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveTaskList(String taskListJson) {
        preferences.edit().putString(KEY_TASK_LIST, taskListJson).apply();
    }

    public String getTaskList() {
        return preferences.getString(KEY_TASK_LIST, null);
    }
}
