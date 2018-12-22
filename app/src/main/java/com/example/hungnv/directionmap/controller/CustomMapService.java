package com.example.hungnv.directionmap.controller;

import com.example.hungnv.directionmap.model.Path;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface CustomMapService {
    @POST("search")
    Call<List<Path>> getDirection(@Body Path path);
}
