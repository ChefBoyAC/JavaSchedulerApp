package com.example.javaschedulerapp.model;

public class ClassData {

    private final String className;
    private final String classSchedule;
    private final String classInstructor;

    public ClassData(String className, String classSchedule, String classInstructor) {
        this.className = className;
        this.classSchedule = classSchedule;
        this.classInstructor = classInstructor;
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


}
