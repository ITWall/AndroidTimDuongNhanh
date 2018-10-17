
package com.example.hungnv.directionmap.model.graphhopper;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InstructionList {

    @SerializedName("distance")
    @Expose
    private Double distance;
    @SerializedName("time")
    @Expose
    private Double time;
    @SerializedName("polyline")
    @Expose
    private String polyline;

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Double getTime() {
        return time;
    }

    public void setTime(Double time) {
        this.time = time;
    }

    public String getPolyline() {
        return polyline;
    }

    public void setPolyline(String polyline) {
        this.polyline = polyline;
    }

}
