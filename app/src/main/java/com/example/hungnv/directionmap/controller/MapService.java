package com.example.hungnv.directionmap.controller;

import com.example.hungnv.directionmap.model.direction.Direction;
import com.example.hungnv.directionmap.model.geocoding.Geocoding;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MapService {
    @GET("geocode/json")
    Call<Geocoding> getAddressInfo(@Query("address") String address, @Query("key") String key);

    @GET("directions/json")
    Call<Direction> getDirection(@Query("origin") String source, @Query("destination") String des, @Query("waypoints") String waypoint, @Query("key") String key ,@Query("alternatives") boolean isAlternative);
}
