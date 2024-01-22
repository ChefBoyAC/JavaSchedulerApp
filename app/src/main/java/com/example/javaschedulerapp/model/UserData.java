package com.example.javaschedulerapp.model;



public class UserData {

    private final String username;
    private final String userSchedule;
    private final String userInstructor;

    private final String userLocation;

    public UserData(String username, String userSchedule, String userInstructor, String userLocation) {
        this.username = username;
        this.userSchedule = userSchedule;
        this.userInstructor = userInstructor;
        this.userLocation = userLocation;
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

    public String getUserLocation() {
        return userLocation;
    }
}
