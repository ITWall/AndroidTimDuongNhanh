package com.example.hungnv.directionmap.model;

public class Edge {

    private int id;

    private int baseNode;

    private int adjustNode;
    private double distance;

    private double speed;
    private String polyline;
    private int trafficStatus;

    public Edge() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBaseNode() {
        return baseNode;
    }

    public void setBaseNode(int baseNode) {
        this.baseNode = baseNode;
    }

    public int getAdjustNode() {
        return adjustNode;
    }

    public void setAdjustNode(int adjustNode) {
        this.adjustNode = adjustNode;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public String getPolyline() {
        return polyline;
    }

    public void setPolyline(String polyline) {
        this.polyline = polyline;
    }

    public int getTrafficStatus() {
        return trafficStatus;
    }

    public void setTrafficStatus(int trafficStatus) {
        this.trafficStatus = trafficStatus;
    }

    public Edge(int id, int baseNode, int adjustNode, double distance, double speed, String polyline, int trafficStatus) {

        this.id = id;
        this.baseNode = baseNode;
        this.adjustNode = adjustNode;
        this.distance = distance;
        this.speed = speed;
        this.polyline = polyline;
        this.trafficStatus = trafficStatus;
    }
}
