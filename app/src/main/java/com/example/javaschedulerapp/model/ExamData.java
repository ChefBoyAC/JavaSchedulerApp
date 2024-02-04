package com.example.javaschedulerapp.model;

// Exam class
public class ExamData extends ItemData {
    private String examDate;
    private String examClass;
    private String examLocation;

    public ExamData(String examName, String examDate, String examClass, String examLocation) {
        super(examName);
        this.examDate = examDate;
        this.examClass = examClass;
        this.examLocation = examLocation;
    }

    // Getters and setters for exam-specific fields
    public String getExamDate() {
        return examDate;
    }

    public String getExamClass() {
        return examClass;
    }

    public String getExamLocation() {
        return examLocation;
    }
}


