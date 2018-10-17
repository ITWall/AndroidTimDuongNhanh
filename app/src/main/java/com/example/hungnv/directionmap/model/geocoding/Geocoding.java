package com.example.hungnv.directionmap.model.geocoding;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by hungnv on 3/30/2018.
 */

public class Geocoding {
    @SerializedName("results")
    @Expose
    private List<Result> listResults;
    @SerializedName("status")
    @Expose
    private String status;

    public List<Result> getListResults() {
        return listResults;
    }

    public void setListResults(List<Result> listResults) {
        this.listResults = listResults;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
