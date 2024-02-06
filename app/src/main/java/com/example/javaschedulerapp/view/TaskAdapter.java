    package com.example.javaschedulerapp.view;

    import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

    import android.content.Context;
    import android.content.DialogInterface;
    import android.util.Log;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.CheckBox;
    import android.widget.EditText;
    import android.widget.LinearLayout;
    import android.widget.TextView;
    import android.widget.Toast;

    import androidx.appcompat.app.AlertDialog;
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
            TextView TaskType;
            TextView TaskTitle;
            TextView TaskSchedule;
            TextView TaskClass;
            TextView TaskLocation;
            CheckBox checkBox;

            LinearLayout tabLayout;

            public TaskViewHolder(View v) {
                super(v);
                TaskType = v.findViewById(R.id.taskTyper);
                TaskTitle = v.findViewById(R.id.taskTitle);
                TaskSchedule = v.findViewById(R.id.taskSchedule);
                TaskClass = v.findViewById(R.id.taskClasses);
                TaskLocation = v.findViewById(R.id.taskPlace);
                checkBox = v.findViewById(R.id.checkBox);
                tabLayout = v.findViewById(R.id.tabLayout);

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
            holder.TaskType.setText(newList.getTaskType());
            holder.TaskTitle.setText(newList.getTaskName());
            holder.TaskSchedule.setText(newList.getTaskSchedule());
            holder.TaskClass.setText(newList.getTaskClass());
            holder.TaskLocation.setText(newList.getTaskLocation());

            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TaskData taskData = taskList.get(holder.getAdapterPosition());
                    taskList.remove(holder.getAdapterPosition());
                    deleteTaskFromJson(taskData);
                    preferencesManager.saveTaskList(serializeTaskList(taskList));
                    notifyItemRemoved(holder.getAdapterPosition());
                    notifyDataSetChanged();
                }
            });
            holder.tabLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            LayoutInflater inflater = LayoutInflater.from(context);
                            View view = inflater.inflate(R.layout.add_task_item, null);

                            TextView updateTitle = view.findViewById(R.id.addTaskTitle);
                            EditText taskType = view.findViewById(R.id.taskType);
                            EditText taskName = view.findViewById(R.id.taskName);
                            EditText taskTime = view.findViewById(R.id.taskTime);
                            EditText taskClass = view.findViewById(R.id.taskClass);
                            EditText taskLocation = view.findViewById(R.id.taskLocation);

                            updateTitle.setText("Update the Item");

                            String task = taskList.get(position).getTaskSchedule();



                            AlertDialog.Builder addDialog = new AlertDialog.Builder(context);


                            addDialog.setView(view);
                            addDialog.setPositiveButton("Update", ((dialog, which) -> {

                                String types ="", names ="", times = "", classes="", locations="";
                                if(ifEmpty(taskType)){
                                    types = setString(taskType);
                                }
                                if(ifEmpty(taskName)){
                                    names = setString(taskName);
                                }
                                if(ifEmpty(taskTime)){
                                   times = setString(taskTime);
                                }
                                if(ifEmpty(taskClass)){
                                    classes = setString(taskClass);
                                }
                                if(ifEmpty(taskLocation)){
                                    locations = setString(taskLocation);
                                }



                                taskList.set(position,
                                        new TaskData(
                                                "Type: " + types,
                                                "Item: " + names,
                                                "Date: " + times,
                                                "Classe: " + classes,
                                                "Location: " + locations
                                        ));
                                notifyItemChanged(position);
                                preferencesManager.saveTaskList(serializeTaskList(taskList));
                                dialog.dismiss();


                            }));


                            addDialog.setNegativeButton("Cancel",((dialog, which) -> {

                                dialog.dismiss();
                            }));


                            addDialog.show();
                        }
            });

            holder.tabLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Delete Task");
                    builder.setMessage("Are you sure you want to delete?");
                    builder.setIcon(R.drawable.baseline_block_24);
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            TaskData deletedTask = taskList.get(position);
                            taskList.remove(position);
                            preferencesManager.saveTaskList(serializeTaskList(taskList));
                            notifyItemRemoved(position);
                            dialog.dismiss();
                            Toast.makeText(context, "Task deleted", Toast.LENGTH_SHORT).show();
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
        private String extractValue(String className, String delimiter) {
            int index = className.indexOf(delimiter);
            if (index != -1) {
                return className.substring(index + delimiter.length()).trim();
            }
            return " "; // Handle case when delimiter is not found
        }
        private boolean ifEmpty(EditText text){
            if(!text.getText().toString().equals("")){
                return true;
            }
            Toast.makeText(context, "Please Enter Course Name!", Toast.LENGTH_SHORT);
            return false;
        }
        private String setString(EditText text){
            return text.getText().toString();
        }
    }
