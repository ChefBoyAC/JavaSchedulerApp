package com.example.javaschedulerapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javaschedulerapp.model.TaskData;
import com.example.javaschedulerapp.view.TaskAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;

public class SecondActivity extends AppCompatActivity {

    private FloatingActionButton addBtn;
    private FloatingActionButton sortBtn;

    private FloatingActionButton sortBtn2;
    private RecyclerView recy;
    private ArrayList<TaskData> taskList;
    private TaskAdapter taskAdapter;

    private static final String TAG = "SecondScreen";

    private TasksSharedPreferences preferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Log.d(TAG, "onCreate: Starting");

        Button btnNavToFirst = (Button) findViewById(R.id.toFirstScreen);

        btnNavToFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked!");

                Intent intent = new Intent(SecondActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        taskList = new ArrayList<>();

        // Set find Id
        addBtn = findViewById(R.id.addingTaskBtn);

        sortBtn = findViewById(R.id.sortBtn);

        sortBtn2 = findViewById(R.id.sortBtn2);

        recy = findViewById(R.id.tRecycler);

        // Set Adapter
        taskAdapter = new TaskAdapter(this, taskList);

        // Set RecyclerView Adapter
        recy.setLayoutManager(new LinearLayoutManager(this));
        recy.setAdapter(taskAdapter);

        preferencesManager = new TasksSharedPreferences(this);

        String taskListJson = preferencesManager.getTaskList();
        if (taskListJson != null) {
            taskList = deserializeTaskList(taskListJson);
            taskAdapter.setTaskList(taskList);
        }

        // Set Dialog
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addInfo();
            }
        });

        sortBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortTaskList();
            }
        });

        sortBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortTaskList2();
            }
        });
    }

    // Inside addInfo method in SecondActivity
    private void addInfo() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.add_task_item, null);

        // Set view
        EditText taskType = v.findViewById(R.id.taskType);
        EditText taskName = v.findViewById(R.id.taskName);
        EditText taskTime = v.findViewById(R.id.taskTime);
        EditText taskClass = v.findViewById(R.id.taskClass);
        EditText taskLocation = v.findViewById(R.id.taskLocation);


        AlertDialog.Builder addDialog = new AlertDialog.Builder(this);


        addDialog.setView(v);
        addDialog.setPositiveButton("Ok", (dialog, which) -> {
            String task = taskType.getText().toString();
            String Name = taskName.getText().toString();
            String time = taskTime.getText().toString();
            String Class = taskClass.getText().toString();
            String Location = taskLocation.getText().toString();



            Log.d(TAG, "addInfo: Adding Task - Task: " + Name + ", Time: " + time);

            taskList.add(new TaskData(
                    "Type: " + task,
                    "Item: " + Name,
                    "Date: " + time,
                    "Class: " + Class,
                    "Location: " + Location
            ));



            Log.d(TAG, "addInfo: taskList size after adding: " + taskList.size());

            preferencesManager.saveTaskList(serializeTaskList(taskList));
            taskAdapter.setTaskList(taskList);
            taskAdapter.notifyDataSetChanged();

            preferencesManager.saveTaskList(serializeTaskList(taskList));

            dialog.dismiss();
        });

        addDialog.setNegativeButton("Cancel", (dialog, which) -> {
            dialog.dismiss();
        });

        addDialog.create();
        addDialog.show();
    }

    private void sortTaskList() {
        Collections.sort(taskList, TaskData.Comparators.TIME);
        preferencesManager.saveTaskList(serializeTaskList(taskList));
        taskAdapter.setTaskList(taskList);
        taskAdapter.notifyDataSetChanged();
    }

    private void sortTaskList2() {
        Collections.sort(taskList, TaskData.Comparators.TYPE);
        preferencesManager.saveTaskList(serializeTaskList(taskList));
        taskAdapter.setTaskList(taskList);
        taskAdapter.notifyDataSetChanged();
    }


    private String serializeTaskList(ArrayList<TaskData> taskList) {
        // Convert taskList to JSON or any other suitable format
        // Example: Using Gson library
        return new Gson().toJson(taskList);
    }

    private ArrayList<TaskData> deserializeTaskList(String taskListJson) {
        // Convert JSON or any other format to ArrayList<TaskData>
        // Example: Using Gson library
        Type listType = new TypeToken<ArrayList<TaskData>>() {}.getType();
        return new Gson().fromJson(taskListJson, listType);
    }


}
