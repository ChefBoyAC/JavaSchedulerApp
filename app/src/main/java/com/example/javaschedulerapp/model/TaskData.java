package com.example.javaschedulerapp.model;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class TaskData implements Comparable<TaskData> {

    private String taskType;
    private String taskName;
    private String taskSchedule;
    private String taskClass;
    private String taskLocation;

    public TaskData(String taskType, String taskName, String taskSchedule, String taskClass, String taskLocation) {
        this.taskType = taskType;
        this.taskName = taskName;
        this.taskSchedule = taskSchedule;
        this.taskClass = taskClass;
        this.taskLocation = taskLocation;
    }

    public String getTaskType() { return taskType; }
    public String getTaskName() { return taskName; }

    public String getTaskSchedule() { return taskSchedule; }

    public String getTaskClass() { return taskClass; }
    public String getTaskLocation() { return taskLocation; }

    public int compareTo(TaskData otherTask) {
        String subTask = taskSchedule.substring(14);
        String subOtherTask = otherTask.getTaskSchedule().substring(14);
        LocalDate date = LocalDate.parse(subTask);
        LocalDate otherDate = LocalDate.parse(subOtherTask);
        return date.compareTo(otherDate);
    }
}
