package com.example.hungnv.directionmap.model.direction;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by hungnv on 3/30/2018.
 */

public class Direction {
    @SerializedName("geocoded_waypoints")
    @Expose
    private List<GeocodedWaypoint> listGeocodedWaypoint;
    @SerializedName("routes")
    @Expose
    private List<Route> listRoutes;
    @SerializedName("status")
    @Expose
    private String status;

    public List<GeocodedWaypoint> getListGeocodedWaypoint() {
        return listGeocodedWaypoint;
    }

    public void setListGeocodedWaypoint(List<GeocodedWaypoint> listGeocodedWaypoint) {
        this.listGeocodedWaypoint = listGeocodedWaypoint;
    }

    public List<Route> getListRoutes() {
        return listRoutes;
    }

    public void setListRoutes(List<Route> listRoutes) {
        this.listRoutes = listRoutes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
