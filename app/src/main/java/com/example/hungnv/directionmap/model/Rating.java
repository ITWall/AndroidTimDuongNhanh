package com.example.hungnv.directionmap.model;

public class Rating {
    private int id;


    private String time;

    private int status;


    private int trafficStatus;

    private Place place;

    private Device device;

    public Rating() {
    }

    public Rating(String time, int status, int trafficStatus, Place place, Device device) {
        this.time = time;
        this.status = status;
        this.trafficStatus = trafficStatus;
        this.place = place;
        this.device = device;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTrafficStatus() {
        return trafficStatus;
    }

    public void setTrafficStatus(int trafficStatus) {
        this.trafficStatus = trafficStatus;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }
}
