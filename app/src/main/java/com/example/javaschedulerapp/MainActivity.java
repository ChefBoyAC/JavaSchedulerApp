package com.example.javaschedulerapp;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javaschedulerapp.model.ClassData;
import com.example.javaschedulerapp.model.TaskData;
import com.example.javaschedulerapp.view.ClassAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton addsBtn;
    private RecyclerView recy;
    private ArrayList<ClassData> classList;
    private ClassAdapter classAdapter;

    private static final String TAG = "MainActivity";

    private ClassSharedPreferences preferencesManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Log.d(TAG, "onCreate: Starting.");

        Button btnNavToSecond = (Button) findViewById(R.id.toSecondScreen);

        btnNavToSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "OnClick: Clicked btnNavToSecond");
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });

        // Set List
        classList = new ArrayList<>();

        // Set find Id
        addsBtn = findViewById(R.id.addingClassBtn);

        recy = findViewById(R.id.mRecycler);

        // Set Adapter
        classAdapter = new ClassAdapter(this, classList);

        // Set RecyclerView Adapter
        recy.setLayoutManager(new LinearLayoutManager(this));
        recy.setAdapter(classAdapter);

        preferencesManager = new ClassSharedPreferences(this);

        String classListJson = preferencesManager.getClassList();
        if (classListJson != null) {
            classList = deserializeClassList(classListJson);
            classAdapter.setClassList(classList);
        }

        // Set Dialog
        addsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addInformation(v);
            }
        });
    }

    private void addInformation(View v){
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.add_class_item, null);

        EditText courseName = view.findViewById(R.id.courseName);
        EditText dateAndTimeOfClass = view.findViewById(R.id.dateAndTimeOfClass);
        EditText instructor = view.findViewById(R.id.instructor);
        EditText locationAndRoomNumber = view.findViewById(R.id.locationAndRoomNumber);

        AlertDialog.Builder addDialog = new AlertDialog.Builder(this);

        addDialog.setView(view);
        addDialog.setPositiveButton("Ok", (dialog, which) -> {
            String courses = "";
            String datesAndTimes = "";
            String instructors = "";
            String locationsAndRoomNumbers = "";

            if (ifEmpty(courseName)) {
                courses = setString(courseName);
            }
            if (ifEmpty(dateAndTimeOfClass)) {
                datesAndTimes = setString(dateAndTimeOfClass);
            }
            if (ifEmpty(instructor)) {
                instructors = setString(instructor);
            }
            if (ifEmpty(locationAndRoomNumber)) {
                locationsAndRoomNumbers = setString(locationAndRoomNumber);
            }

            classList.add(new ClassData(
                    "Courses: " + courses,
                    "Date and Time of Class: " + datesAndTimes,
                    "Instructor: " + instructors,
                    "Location/ Room Number: " + locationsAndRoomNumbers));

            preferencesManager.saveClassList(serializeClassList(classList));
            classAdapter.notifyItemInserted(classList.size() - 1);
            recy.scrollToPosition(classList.size() - 1);

            dialog.dismiss();
        });

        addDialog.setNegativeButton("Cancel", (dialog, which) -> {
            // Handle cancel button action if needed
            dialog.dismiss();
        });

        addDialog.create();
        addDialog.show();

    }
    private String setString(EditText text){
        return text.getText().toString();
    }
    private boolean ifEmpty(EditText text){
        if(!text.getText().toString().equals("")){
            return true;
        }
        Toast.makeText(MainActivity.this, "Please Enter Contact Name!", Toast.LENGTH_SHORT);
        return false;
    }

    private String serializeClassList(ArrayList<ClassData> classList) {
        return new Gson().toJson(classList);
    }

    private ArrayList<ClassData> deserializeClassList(String classListJson) {
        Type listType = new TypeToken<ArrayList<ClassData>>() {}.getType();
        return new Gson().fromJson(classListJson, listType);
    }
}