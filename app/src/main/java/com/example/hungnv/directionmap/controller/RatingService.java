package com.example.hungnv.directionmap.controller;
import com.example.hungnv.directionmap.model.Rating;
import com.example.hungnv.directionmap.model.ResponseMessage;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RatingService {
    @POST("insert")
    Call<ResponseMessage> insertRating(@Body Rating rating);
}
