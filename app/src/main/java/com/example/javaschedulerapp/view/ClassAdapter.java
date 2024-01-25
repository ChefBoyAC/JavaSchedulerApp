package com.example.javaschedulerapp.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.javaschedulerapp.R;
import com.example.javaschedulerapp.model.ClassData;

import java.util.ArrayList;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ClassViewHolder> {

    private final Context context;
    private ArrayList<ClassData> classList;

    public ClassAdapter(Context context, ArrayList<ClassData> classList) {
        this.context = context;
        this.classList = classList;
    }

    public static class ClassViewHolder extends RecyclerView.ViewHolder {
        TextView className;
        TextView classSchedule;
        TextView classInstructor;

        public ClassViewHolder(View v) {
            super(v);
            className = v.findViewById(R.id.ClassTitle);
            classSchedule = v.findViewById(R.id.TimeTitle);
            classInstructor = v.findViewById(R.id.InstructorTitle);

        }
    }

    @Override
    public ClassViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.class_item, parent, false);
        return new ClassViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ClassViewHolder holder, int position) {
        ClassData newList = classList.get(position);
        holder.className.setText(newList.getClassName());
        holder.classSchedule.setText(newList.getClassSchedule());
        holder.classInstructor.setText(newList.getClassInstructor());
    }

    public void setClassList(ArrayList<ClassData> classList) {
        this.classList = classList;
        notifyDataSetChanged(); // or other appropriate notification method
    }

    @Override
    public int getItemCount() {
        return classList.size();
    }
}
