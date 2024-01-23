package com.example.javaschedulerapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.javaschedulerapp.model.UserData;
import com.example.javaschedulerapp.view.UserAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton addsBtn;
    private RecyclerView recy;
    private ArrayList<UserData> userList;
    private UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set List
        userList = new ArrayList<>();

        // Set find Id
        addsBtn = findViewById(R.id.addingBtn);
        recy = findViewById(R.id.mRecycler);

        // Set Adapter
        userAdapter = new UserAdapter(this, userList);

        // Set RecyclerView Adapter
        recy.setLayoutManager(new LinearLayoutManager(this));
        recy.setAdapter(userAdapter);

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
        View v = inflater.inflate(R.layout.add_item, null);

        // Set view
        EditText courseName = v.findViewById(R.id.courseName);
        EditText dateAndTimeOfClass = v.findViewById(R.id.dateAndTimeOfClass);
        EditText instructor = v.findViewById(R.id.instructor);
        EditText location = v.findViewById(R.id.location);

        AlertDialog.Builder addDialog = new AlertDialog.Builder(this);

        // Figure out what all of this means after this code works
        addDialog.setView(v);
        addDialog.setPositiveButton("Ok", (dialog, which) -> {
            String courses = courseName.getText().toString();
            String dateAndTime = dateAndTimeOfClass.getText().toString();
            String instructors = instructor.getText().toString();
            String locations = location.getText().toString();

            userList.add(new UserData(
                    "Course: " + courses,
                    "Date and Time of Class: " + dateAndTime,
                    "Instructor: " + instructors,
                    "Location: " + locations));

            userAdapter.notifyDataSetChanged();
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



    /***
     * Now we code the whole CardView portal
     * 1. Create the data class -> userData
     * 2. Add user and button dialog information into the activity_main.xml
     */
}