package com.example.hungnv.directionmap.controller;

import com.example.hungnv.directionmap.model.graphhopper.ResultPath;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CustomMapService {
    @GET("location")
    Call<ResultPath> getDirection(@Query("fromLocation") String fromLocation, @Query("toLocation") String toLocation);

    @GET("point")
    Call<ResultPath> getDirectionFromPoint(@Query("startPoint") String startPoint, @Query("endPoint") String endPoint);
}
