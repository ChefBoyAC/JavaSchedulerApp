package com.example.javaschedulerapp.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.javaschedulerapp.R;
import com.example.javaschedulerapp.TasksSharedPreferences;
import com.example.javaschedulerapp.model.TaskData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private final Context context;
    private ArrayList<TaskData> taskList;


    private TasksSharedPreferences preferencesManager;


    public TaskAdapter(Context context, ArrayList<TaskData> taskList) {
        this.context = context;
        this.taskList = taskList;
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView TaskTitle;

        TextView TaskSchedule;

        CheckBox checkBox;

        public TaskViewHolder(View v) {
            super(v);
            TaskTitle = v.findViewById(R.id.taskTitle);
            TaskSchedule = v.findViewById(R.id.taskSchedule);
            checkBox = v.findViewById(R.id.checkBox);


        }
    }


    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.task_item, parent, false);
        preferencesManager = new TasksSharedPreferences(context);

        String taskListJson = preferencesManager.getTaskList();
        if (taskListJson != null) {
            taskList = deserializeTaskList(taskListJson);

        }
        return new TaskViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        TaskData newList = taskList.get(holder.getAdapterPosition());
        holder.TaskTitle.setText(newList.getTaskName());
        holder.TaskSchedule.setText(newList.getTaskSchedule());

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskData taskData = taskList.get(holder.getAdapterPosition());
                taskList.remove(holder.getAdapterPosition());
                holder.TaskTitle.setText(taskData.getTaskName());
                holder.TaskSchedule.setText(taskData.getTaskSchedule());
                deleteTaskFromJson(taskData);
                preferencesManager.saveTaskList(serializeTaskList(taskList));
                notifyItemRemoved(holder.getAdapterPosition());
                notifyDataSetChanged();
            }
        });
    }

    private void deleteTaskFromJson(TaskData taskData) {
        ArrayList<TaskData> updatedList = new ArrayList<>(taskList);
        updatedList.remove(taskData);
        String updatedJson = serializeTaskList(updatedList);
        preferencesManager.saveTaskList(updatedJson);
        setTaskList(updatedList);
    }


    public void setTaskList(ArrayList<TaskData> taskList) {
        this.taskList = taskList;
        notifyDataSetChanged(); // or other appropriate notification method
    }

    @Override
    public int getItemCount() {
        return taskList.size();
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
