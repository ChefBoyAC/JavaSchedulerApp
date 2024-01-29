package com.example.javaschedulerapp.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.javaschedulerapp.ClassSharedPreferences;
import com.example.javaschedulerapp.R;
import com.example.javaschedulerapp.model.ClassData;
import com.google.gson.Gson;

import java.util.ArrayList;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ClassViewHolder> {

    private final Context context;
    private ArrayList<ClassData> classList;

    private ClassSharedPreferences preferencesManager;


    public ClassAdapter(Context context, ArrayList<ClassData> classList){
        this.context = context;
        this.classList = classList;
    }

    public static class ClassViewHolder extends RecyclerView.ViewHolder {
        TextView className;
        TextView classSchedule;
        TextView classInstructor;

        TextView classLocationAndRoomNumber;

        LinearLayout llRow;


        public ClassViewHolder(View v) {
            super(v);
            className = v.findViewById(R.id.ClassTitle);
            classSchedule = v.findViewById(R.id.TimeTitle);
            classInstructor = v.findViewById(R.id.InstructorTitle);
            classLocationAndRoomNumber= v.findViewById(R.id.LocationAndRoomNumberTitle);
            llRow = v.findViewById(R.id.llRow);
        }
    }

    @Override
    public ClassViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.class_item, parent, false);
        preferencesManager = new ClassSharedPreferences(context);
        return new ClassViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ClassViewHolder holder, int position) {
        ClassData newList = classList.get(position);
        holder.className.setText(newList.getClassName());
        holder.classSchedule.setText(newList.getClassSchedule());
        holder.classInstructor.setText(newList.getClassInstructor());
        holder.classLocationAndRoomNumber.setText(newList.getClassLocationAndRoomNumber());

        holder.llRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.add_class_item);

                TextView textTitle = dialog.findViewById(R.id.textTitle);
                EditText courseName = dialog.findViewById(R.id.courseName);
                EditText dateAndTimeOfClass = dialog.findViewById(R.id.dateAndTimeOfClass);
                EditText instructor = dialog.findViewById(R.id.instructor);
                EditText locationAndRoomNumber = dialog.findViewById(R.id.locationAndRoomNumber);
                Button addBtn = dialog.findViewById(R.id.addBtn);
                Button cancelBtn = dialog.findViewById(R.id.cancelBtn);

                addBtn.setText("Update");
                textTitle.setText("Update Content of your Course");


                String className = classList.get(position).getClassName();


                String course = extractValue(className, "Course:");
                courseName.setText(course);


                String dateAndTime = extractValue(className, "Date and Time of Class:");
                dateAndTimeOfClass.setText(dateAndTime);


                String instructors = extractValue(className, "Instructor:");
                instructor.setText(instructors);


                String lAR = extractValue(className, "Location/Room Number:");
                locationAndRoomNumber.setText(lAR);



                addBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String courses ="", datesAndTimes = "", instructors = "", locationsAndRoomNumbers = "";
                        if(ifEmpty(courseName)){
                            courses = setString(courseName);
                        }
                        if(ifEmpty(dateAndTimeOfClass)){
                            datesAndTimes = setString(dateAndTimeOfClass);
                        }
                        if(ifEmpty(instructor)){
                            instructors = setString(instructor);
                        }
                        if(ifEmpty(locationAndRoomNumber)){
                            locationsAndRoomNumbers = setString(locationAndRoomNumber);
                        }


                        classList.set(position,
                                new ClassData(
                                    "Course: "+ courses,
                                    "Date and Time of Class: " + datesAndTimes,
                                    "Instructor: " + instructors,
                                    "Location/Room Number: " + locationsAndRoomNumbers
                                ));
                        notifyItemChanged(position);
                        preferencesManager.saveClassList(serializeClassList(classList));
                        dialog.dismiss();
                    }
                });

                cancelBtn.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        holder.llRow.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete Course");
                builder.setMessage("Are you sure you want to delete?");
                builder.setIcon(R.drawable.baseline_block_24);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        classList.remove(position);
                        preferencesManager.saveClassList(serializeClassList(classList));
                        notifyItemRemoved(position);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.show();


                return true;
            }
        });

    }
    public void setClassList (ArrayList < ClassData > classList) {
        this.classList = classList;
        notifyDataSetChanged(); // or other appropriate notification method
    }
    @Override
    public int getItemCount () {
        return classList.size();
    }
    private String setString(EditText text){
        return text.getText().toString();
    }
    private boolean ifEmpty(EditText text){
        if(!text.getText().toString().equals("")){
            return true;
        }
        Toast.makeText(context, "Please Enter Course Name!", Toast.LENGTH_SHORT);
        return false;
    }

    private String extractValue(String className, String delimiter) {
        int index = className.indexOf(delimiter);
        if (index != -1) {
            return className.substring(index + delimiter.length()).trim();
        }
        return " "; // Handle case when delimiter is not found
    }

    private String serializeClassList(ArrayList<ClassData> classList) {
        return new Gson().toJson(classList);
    }

}
