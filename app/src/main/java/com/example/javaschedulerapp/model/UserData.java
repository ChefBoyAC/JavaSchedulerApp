package com.example.javaschedulerapp.model;



public class UserData {

    private final String username;
    private final String userSchedule;
    private final String userInstructor;

    public UserData(String username, String userSchedule, String userInstructor) {
        this.username = username;
        this.userSchedule = userSchedule;
        this.userInstructor = userInstructor;
    }

    public String getUsername() {
        return username;
    }

    public String getUserSchedule() {
        return userSchedule;
    }

    public String getUserInstructor() {
        return userInstructor;
    }
}
