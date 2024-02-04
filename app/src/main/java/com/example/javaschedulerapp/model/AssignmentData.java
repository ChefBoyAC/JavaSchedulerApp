package com.example.javaschedulerapp.model;

public class AssignmentData extends ItemData {
    private String assignmentClass;
    private String assignmentTime;

    public AssignmentData(String assignmentName, String assignmentClass, String assignmentTime) {
        super(assignmentName);
        this.assignmentTime = assignmentTime;
        this.assignmentClass = assignmentClass;
    }

    public String getAssignmentTime() {
        return assignmentTime;
    }

    public String getAssignmentClass() {
        return assignmentClass;
    }

}
