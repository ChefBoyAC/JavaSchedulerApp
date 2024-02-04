package com.example.javaschedulerapp.view;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.javaschedulerapp.R;
import com.example.javaschedulerapp.ItemsSharedPreferences;
import com.example.javaschedulerapp.model.AssignmentData;
import com.example.javaschedulerapp.model.ExamData;
import com.example.javaschedulerapp.model.ItemData;
import com.example.javaschedulerapp.model.TaskData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int DEFAULT_TYPE = 0;
    private static final int ASSIGNMENT_TYPE = 1;
    private static final int EXAM_TYPE = 2;
    private static final int TASK_TYPE = 3;

    private final Context context;
    private ArrayList<ItemData> itemList;
    private ItemsSharedPreferences preferencesManager;

    public ItemAdapter(Context context, ArrayList<ItemData> itemList) {
        this.context = context;
        this.itemList = itemList;
        preferencesManager = new ItemsSharedPreferences(context);
    }

    public static class DefaultViewHolder extends RecyclerView.ViewHolder {
        public DefaultViewHolder(View itemView) {
            super(itemView);
        }
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

    public static class AssignmentViewHolder extends RecyclerView.ViewHolder {
        TextView assignmentTitle;
        TextView assignmentClassname;
        TextView assignmentTime;
        CheckBox checkBox;

        public AssignmentViewHolder(View v) {
            super(v);
            assignmentTitle = v.findViewById(R.id.assignmentTitle);
            assignmentClassname = v.findViewById(R.id.assignmentClassname);
            assignmentTime = v.findViewById(R.id.assignmentTime);
            checkBox = v.findViewById(R.id.checkBox);
        }
    }

    public static class ExamViewHolder extends RecyclerView.ViewHolder {
        TextView examTitle;
        TextView examTime;
        TextView examClassname;
        TextView examPlace;
        CheckBox checkBox;

        public ExamViewHolder(View v) {
            super(v);
            examTitle = v.findViewById(R.id.examTitle);
            examTime = v.findViewById(R.id.examTime);
            examClassname = v.findViewById(R.id.examClassname);
            examPlace = v.findViewById(R.id.examPlace);
            checkBox = v.findViewById(R.id.checkBox);
        }
    }

    @Override
    public int getItemViewType(int position) {
        ItemData item = itemList.get(position);
        if (item instanceof AssignmentData) {
            return ASSIGNMENT_TYPE;
        } else if (item instanceof ExamData) {
            return EXAM_TYPE;
        } else if (item instanceof TaskData){
            return TASK_TYPE;
        }
        return DEFAULT_TYPE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        if (viewType == ASSIGNMENT_TYPE) {
            View assignmentView = inflater.inflate(R.layout.assignment_item, parent, false);
            return new AssignmentViewHolder(assignmentView);
        } else if (viewType == EXAM_TYPE) {
            View examView = inflater.inflate(R.layout.exam_item, parent, false);
            return new ExamViewHolder(examView);
        } else if (viewType == TASK_TYPE) {
            View taskView = inflater.inflate(R.layout.task_item, parent, false);
            return new TaskViewHolder(taskView);
        }
        return new DefaultViewHolder(new View(context));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder == null) {
            return;
        }

        ItemData item = itemList.get(position);

        if (holder instanceof AssignmentViewHolder) {
            AssignmentViewHolder assignmentHolder = (AssignmentViewHolder) holder;
            AssignmentData assignmentData = (AssignmentData) item;

            assignmentHolder.assignmentTitle.setText(assignmentData.getItemName());
            assignmentHolder.assignmentClassname.setText(assignmentData.getAssignmentClass());
            assignmentHolder.assignmentTime.setText(assignmentData.getAssignmentTime());

            assignmentHolder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteAssignment(assignmentData);
                }
            });

        } else if (holder instanceof ExamViewHolder) {
            ExamViewHolder examHolder = (ExamViewHolder) holder;
            ExamData examData = (ExamData) item;

            examHolder.examTitle.setText(examData.getItemName());
            examHolder.examTime.setText(examData.getExamDate());
            examHolder.examClassname.setText(examData.getExamClass());
            examHolder.examPlace.setText(examData.getExamLocation());

            examHolder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteExam(examData);
                }
            });

        } else if (holder instanceof TaskViewHolder) {
            TaskViewHolder taskViewHolder = (TaskViewHolder) holder;
            TaskData taskData = (TaskData) item;

            taskViewHolder.TaskTitle.setText(taskData.getItemName());
            taskViewHolder.TaskSchedule.setText(taskData.getTaskSchedule());

            taskViewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                    public void onClick(View v) {
                        deleteTask(taskData);
                    }
                });
        }
    }

    private void deleteAssignment(AssignmentData assignmentData) {
        ArrayList<ItemData> updatedList = new ArrayList<>(itemList);
        updatedList.remove(assignmentData);
        String updatedJson = serializeItemList(updatedList);
        preferencesManager.saveItemList(updatedJson);
        setItemList(updatedList);
    }

    private void deleteExam(ExamData examData) {
        ArrayList<ItemData> updatedList = new ArrayList<>(itemList);
        updatedList.remove(examData);
        String updatedJson = serializeItemList(updatedList);
        preferencesManager.saveItemList(updatedJson);
        setItemList(updatedList);
    }

    private void deleteTask(TaskData taskData) {
        ArrayList<ItemData> updatedList = new ArrayList<>(itemList);
        updatedList.remove(taskData);
        String updatedJson = serializeItemList(updatedList);
        preferencesManager.saveItemList(updatedJson);
        setItemList(updatedList);
    }

    public void setItemList(ArrayList<ItemData> itemList) {
        this.itemList = itemList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public String serializeItemList(ArrayList<ItemData> itemList) {
        String serialized = new Gson().toJson(itemList);
        Log.d(TAG, "Serialized item list: " + serialized);
        return serialized;
    }

    public ArrayList<ItemData> deserializeItemList(String itemListJson) {
        Type listType = new TypeToken<ArrayList<ItemData>>() {}.getType();
        ArrayList<ItemData> deserialized = new Gson().fromJson(itemListJson, listType);
        for (ItemData item : deserialized) {
            if (item instanceof AssignmentData) {
                Log.d(TAG, "Deserialized assignment name: " + ((AssignmentData) item).getItemName());
            } else if (item instanceof ExamData) {
                Log.d(TAG, "Deserialized exam name: " + ((ExamData) item).getItemName());
            } else if (item instanceof TaskData) {
                Log.d(TAG, "Deserialized task name: " + ((TaskData) item).getItemName());
            } else {
                // Handle the case for the base ItemData class if needed
                Log.d(TAG, "Deserialized item name: " + item.getItemName());
            }
        }
        Log.d(TAG, "Deserialized itemList size: " + deserialized.size());
        return deserialized;
    }
}
