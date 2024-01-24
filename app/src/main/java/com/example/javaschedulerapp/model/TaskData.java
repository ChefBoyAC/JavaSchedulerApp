package com.example.javaschedulerapp.model;

public class TaskData {
    private final String taskName;

    private final String taskSchedule;

    public TaskData(String taskName, String taskSchedule) {
        this.taskName = taskName;
        this.taskSchedule = taskSchedule;
    }

    public String getTaskName() { return taskName; }

    public String getTaskSchedule() { return taskSchedule; }
}
