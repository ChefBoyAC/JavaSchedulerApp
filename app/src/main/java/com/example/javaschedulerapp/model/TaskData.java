package com.example.javaschedulerapp.model;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class TaskData implements Comparable<TaskData> {
    private String taskName;

    private String taskSchedule;

    public TaskData(String taskName, String taskSchedule) {
        this.taskName = taskName;
        this.taskSchedule = taskSchedule;
    }

    public String getTaskName() { return taskName; }

    public String getTaskSchedule() { return taskSchedule; }

    public int compareTo(TaskData otherTask) {
        String subTask = taskSchedule.substring(14);
        String subOtherTask = otherTask.getTaskSchedule().substring(14);
        LocalDate date = LocalDate.parse(subTask);
        LocalDate otherDate = LocalDate.parse(subOtherTask);
        return date.compareTo(otherDate);
    }
}
