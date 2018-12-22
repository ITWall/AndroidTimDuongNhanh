package com.example.hungnv.directionmap.model;

import java.util.List;

public class Place {

    private int id;


    private String name;


    private double latitude;

    private double longitude;

    private List<Edge> edges;

    public Place() {
    }

    public Place(String name, double latitude, double longitude, List<Edge> edges) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.edges = edges;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }
}
