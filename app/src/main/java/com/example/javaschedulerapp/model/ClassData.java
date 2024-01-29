package com.example.javaschedulerapp.model;

public class ClassData {

    private final String className;
    private final String classSchedule;
    private final String classInstructor;

    private final String classLocationAndRoomNumber;

    public ClassData(String className, String classSchedule, String classInstructor, String classLocationAndRoomNumber) {
        this.className = className;
        this.classSchedule = classSchedule;
        this.classInstructor = classInstructor;
        this.classLocationAndRoomNumber = classLocationAndRoomNumber;
    }

    public String getClassName() {
        return className;
    }

    public String getClassSchedule() {
        return classSchedule;
    }

    public String getClassInstructor() {
        return classInstructor;
    }

    public String getClassLocationAndRoomNumber() {
        return classLocationAndRoomNumber;
    }

    public void setCourse(String newCourse) {
    }

    public void setDateTime(String newDateTime) {
    }

    public void setInstructor(String newInstructor) {
    }

    public void setLocation(String newLocation) {
    }
}
