package com.example.javaschedulerapp.model;

public class TaskData extends ItemData {
    private String taskSchedule;
    public TaskData(String taskName, String taskSchedule) {
        super(taskName);
        this.taskSchedule = taskSchedule;
    }

    public String getTaskSchedule() {
        return taskSchedule;
    }
}

