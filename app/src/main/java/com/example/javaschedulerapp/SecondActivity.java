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

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

    private FloatingActionButton addBtn;
    private RecyclerView recy;
    private ArrayList<TaskData> taskList;
    private TaskAdapter taskAdapter;

    private static final String TAG = "SecondScreen";

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
        recy = findViewById(R.id.tRecycler);

        // Set Adapter
        taskAdapter = new TaskAdapter(this, taskList);

        // Set RecyclerView Adapter
        recy.setLayoutManager(new LinearLayoutManager(this));
        recy.setAdapter(taskAdapter);

        // Set Dialog
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addInfo();
            }
        });
    }

    // Inside addInfo method in SecondActivity
    private void addInfo() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.add_task_item, null);

        // Set view
        EditText taskName = v.findViewById(R.id.taskName);
        EditText taskTime = v.findViewById(R.id.taskTime);

        AlertDialog.Builder addDialog = new AlertDialog.Builder(this);

        // Figure out what all of this means after this code works
        addDialog.setView(v);
        addDialog.setPositiveButton("Ok", (dialog, which) -> {
            String task = taskName.getText().toString();
            String time = taskTime.getText().toString();

            Log.d(TAG, "addInfo: Adding Task - Task: " + task + ", Time: " + time);

            taskList.add(new TaskData(
                    "Task: " + task,
                    "Time of Task: " + time
            ));

            Log.d(TAG, "addInfo: taskList size after adding: " + taskList.size());

            taskAdapter.notifyDataSetChanged();
            dialog.dismiss();
        });

        addDialog.setNegativeButton("Cancel", (dialog, which) -> {
            dialog.dismiss();
        });

        addDialog.create();
        addDialog.show();
    }


}
