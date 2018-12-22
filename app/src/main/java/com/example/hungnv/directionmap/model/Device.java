package com.example.hungnv.directionmap.model;

public class Device {

    private String id;

    private int warning;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getWarning() {
        return warning;
    }

    public void setWarning(int warning) {
        this.warning = warning;
    }

    public Device() {
    }

    public Device(String id, int warning) {
        this.id = id;
        this.warning = warning;
    }
}
