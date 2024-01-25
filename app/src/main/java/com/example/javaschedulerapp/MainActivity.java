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

import com.example.javaschedulerapp.model.ClassData;
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
            public void onClick(View view) {
                addInfo();
            }
        });
    }

    private void addInfo() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.add_class_item, null);

        // Set view
        EditText courseName = v.findViewById(R.id.courseName);
        EditText dateAndTimeOfClass = v.findViewById(R.id.dateAndTimeOfClass);
        EditText instructor = v.findViewById(R.id.instructor);

        AlertDialog.Builder addDialog = new AlertDialog.Builder(this);

        // Figure out what all of this means after this code works
        addDialog.setView(v);
        addDialog.setPositiveButton("Ok", (dialog, which) -> {
            String courses = courseName.getText().toString();
            String dateAndTime = dateAndTimeOfClass.getText().toString();
            String instructors = instructor.getText().toString();

            classList.add(new ClassData(
                    "Course: " + courses,
                    "Date and Time of Class: " + dateAndTime,
                    "Instructor: " + instructors
            ));

            classAdapter.notifyDataSetChanged();

            preferencesManager.saveClassList(serializeClassList(classList));

            //Toast.makeText(MainActivity.this, "Adding User Information Success", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

        addDialog.setNegativeButton("Cancel", (dialog, which) -> {
            dialog.dismiss();
            //Toast.makeText(MainActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
        });

        addDialog.create();
        addDialog.show();
    }

    private String serializeClassList(ArrayList<ClassData> classList) {
        return new Gson().toJson(classList);
    }

    private ArrayList<ClassData> deserializeClassList(String classListJson) {
        Type listType = new TypeToken<ArrayList<ClassData>>() {}.getType();
        return new Gson().fromJson(classListJson, listType);
    }
}