package com.example.javaschedulerapp.model;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;


public class TaskData {

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

    public static class Comparators {
        public static Comparator<TaskData> TYPE = new Comparator<TaskData>() {
            @Override
            public int compare(TaskData thisTask, TaskData otherTask) {
                Integer taskType1;
                Integer taskType2;
                System.out.println(thisTask.getTaskType() + " & " + otherTask.getTaskType());
                if ("exam".equals(thisTask.getTaskType().substring(6))) {
                    taskType1 = Integer.valueOf(1);
                } else if ("HW".equals(thisTask.getTaskType().substring(6))) {
                    taskType1 = Integer.valueOf(2);
                } else {
                    taskType1 = Integer.valueOf(3);
                }
                if ("exam".equals(otherTask.getTaskType().substring(6)            )) {
                    taskType2 = Integer.valueOf(1);
                } else if ("HW".equals(otherTask.getTaskType().substring(6))) {
                    taskType2 = Integer.valueOf(2);
                } else {
                    taskType2 = Integer.valueOf(3);
                }
                return taskType1.compareTo(taskType2);
            }
        };

        public static Comparator<TaskData> TIME = new Comparator<TaskData>() {
            @Override
            public int compare(TaskData thisTask, TaskData otherTask) {
                String subTask = thisTask.getTaskSchedule().substring(6);
                String subOtherTask = otherTask.getTaskSchedule().substring(6);
                LocalDate date = LocalDate.parse(subTask);
                LocalDate otherDate = LocalDate.parse(subOtherTask);
                return date.compareTo(otherDate);
            }
        };
    }
}
