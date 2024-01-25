package com.example.javaschedulerapp.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.example.javaschedulerapp.R;
import com.example.javaschedulerapp.model.TaskData;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private final Context context;
    private ArrayList<TaskData> taskList;

    public TaskAdapter(Context context, ArrayList<TaskData> taskList) {
        this.context = context;
        this.taskList = taskList;
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView TaskTitle;

        TextView TaskSchedule;

        public TaskViewHolder(View v) {
            super(v);
            TaskTitle = v.findViewById(R.id.taskTitle);
            TaskSchedule = v.findViewById(R.id.taskSchedule);
        }
    }


    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        TaskData newList = taskList.get(position);
        holder.TaskTitle.setText(newList.getTaskName());
        holder.TaskSchedule.setText(newList.getTaskSchedule());
    }

    public void setTaskList(ArrayList<TaskData> taskList) {
        this.taskList = taskList;
        notifyDataSetChanged(); // or other appropriate notification method
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }
}
