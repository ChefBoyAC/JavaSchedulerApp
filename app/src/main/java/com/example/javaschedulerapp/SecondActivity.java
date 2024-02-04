package com.example.javaschedulerapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javaschedulerapp.model.AssignmentData;
import com.example.javaschedulerapp.model.ExamData;
import com.example.javaschedulerapp.model.ItemData;
import com.example.javaschedulerapp.model.TaskData;
import com.example.javaschedulerapp.view.ItemAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;

public class SecondActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private FloatingActionButton addBtn;
    private FloatingActionButton sortBtn;
    private RecyclerView recy;
    private ArrayList<ItemData> itemList;
    private ItemAdapter itemAdapter;

    private static final String TAG = "SecondScreen";

    private ItemsSharedPreferences preferencesManager;

    private Spinner spinnerItems;
    private LinearLayout additionalFieldsLayouts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Log.d(TAG, "onCreate: Starting");

        Button btnNavToFirst = (Button) findViewById(R.id.toFirstScreen);

        btnNavToFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "OnClick: Clicked btnNavToSecond");
                Intent intent = new Intent(SecondActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        addBtn = findViewById(R.id.addingTaskBtn);
        sortBtn = findViewById(R.id.sortBtn);
        recy = findViewById(R.id.tRecycler);

        preferencesManager = new ItemsSharedPreferences(this);
        String itemListJson = preferencesManager.getItemList();
        itemList = new ArrayList<>();
        itemAdapter = new ItemAdapter(this, itemList);
        if (itemListJson != null) {
            itemList = itemAdapter.deserializeItemList(itemListJson);
        }
        // Set RecyclerView Adapter
        recy.setLayoutManager(new LinearLayoutManager(this));
        recy.setAdapter(itemAdapter);
        itemAdapter.notifyDataSetChanged();


        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddItemDialog();
            }
        });

//        sortBtn.setOnClickListener(new View.OnClickListener()
////            @Override
////            public void onClick(View view) {
////                sortItemList();
////            }
//        );
    }

    private void showAddItemDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.add_item, null);

        spinnerItems = dialogView.findViewById(R.id.spinner);
        additionalFieldsLayouts = dialogView.findViewById(R.id.additionalFieldsLayout);

        String[] items = getResources().getStringArray(R.array.items);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerItems.setAdapter(adapter);

        spinnerItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                updateAdditionalFields(selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle the case where nothing is selected.
            }
        });

        AlertDialog.Builder addDialog = new AlertDialog.Builder(this);
        addDialog.setView(dialogView);

        addDialog.setPositiveButton("Ok", (dialog, which) -> {
            String selectedItem = spinnerItems.getSelectedItem().toString();
            if ("Assignment".equals(selectedItem)) {
                createAssignment(dialogView);
            } else if ("Exam".equals(selectedItem)) {
                createExam(dialogView);
            } else if ("Task".equals(selectedItem)) {
                createTask(dialogView);
            } else {
                Log.e(TAG, "showAddItemDialog: Unknown item type selected");
            }
        });

        addDialog.setNegativeButton("Cancel", (dialog, which) -> {
            dialog.dismiss();
        });

        addDialog.create();
        addDialog.show();
    }

    private AssignmentData createAssignmentItem(View v) {
        EditText assignmentName = v.findViewById(R.id.assignmentName);
        EditText assignmentClass = v.findViewById(R.id.assignmentClass);
        EditText assignmentTime = v.findViewById(R.id.assignmentDue);

        String name = "Assignment: " + assignmentName.getText().toString();
        String className = "Class: " + assignmentClass.getText().toString();
        String time = "Due Date:" + assignmentTime.getText().toString();

        return new AssignmentData(name, className, time);
    }

    private ExamData createExamItem(View v) {
        EditText examName = v.findViewById(R.id.examName);
        EditText examDate = v.findViewById(R.id.examDate);
        EditText examClass = v.findViewById(R.id.examClass);
        EditText examLocation = v.findViewById(R.id.examLocation);

        String name = "Exam: " + examName.getText().toString();
        String date = "Date: " + examDate.getText().toString();
        String className = "Class: " + examClass.getText().toString();
        String location = "Location: " + examLocation.getText().toString();

        return new ExamData(name, date, className, location);
    }

    private TaskData createTaskItem(View v) {
        EditText taskName = v.findViewById(R.id.taskName);
        EditText taskSchedule = v.findViewById(R.id.taskTime);

        String name = "Task: " + taskName.getText().toString();
        String schedule = "Time: " + taskSchedule.getText().toString();

        return new TaskData(name, schedule);
    }

    private void createAssignment(View dialogView) {
        AssignmentData assignmentData = createAssignmentItem(dialogView);
        Log.d(TAG, "Before adding assignment: " + itemList.size());
        itemList.add(assignmentData);
        preferencesManager.saveItemList(itemAdapter.serializeItemList(itemList));
        itemAdapter.setItemList(itemList);
        itemAdapter.notifyDataSetChanged();
    }

    private void createExam(View dialogView) {
        ExamData examData = createExamItem(dialogView);
        Log.d(TAG, "Before adding exam: " + itemList.size());
        itemList.add(examData);
        Log.d(TAG, "After adding exam: " + itemList.size());
        preferencesManager.saveItemList(itemAdapter.serializeItemList(itemList));
        itemAdapter.setItemList(itemList);
        itemAdapter.notifyDataSetChanged();
    }

    private void createTask(View dialogView) {
        TaskData taskData = createTaskItem(dialogView);
        Log.d(TAG, "Before adding task: " + itemList.size());
        itemList.add(taskData);
        Log.d(TAG, "After adding task: " + itemList.size());
        preferencesManager.saveItemList(itemAdapter.serializeItemList(itemList));
        itemAdapter.setItemList(itemList);
        itemAdapter.notifyDataSetChanged();
    }

    private void updateAdditionalFields(String selectedItem) {
        additionalFieldsLayouts.removeAllViews();

        if ("Assignment".equals(selectedItem)) {
            View additionalFieldsView = getLayoutInflater().inflate(R.layout.add_assignment, additionalFieldsLayouts, false);
            additionalFieldsLayouts.addView(additionalFieldsView);
        } else if ("Exam".equals(selectedItem)) {
            View additionalFieldsView = getLayoutInflater().inflate(R.layout.add_exam, additionalFieldsLayouts, false);
            additionalFieldsLayouts.addView(additionalFieldsView);
        } else if ("Task".equals(selectedItem)) {
            View additionalFieldsView = getLayoutInflater().inflate(R.layout.add_task, additionalFieldsLayouts, false);
            additionalFieldsLayouts.addView(additionalFieldsView);
        }
        additionalFieldsLayouts.setVisibility(View.VISIBLE);
    }

//    private void sortItemList() {
//        Collections.sort(itemList);
//        preferencesManager.saveItemList(itemAdapter.serializeItemList(itemList));
//        itemAdapter.setItemList(itemList);
//        itemAdapter.notifyDataSetChanged();
//    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // Handle spinner item selection if needed
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Handle the case where nothing is selected.
    }
}

