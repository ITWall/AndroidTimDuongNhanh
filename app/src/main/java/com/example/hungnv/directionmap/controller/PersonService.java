package com.example.hungnv.directionmap.controller;

import com.example.hungnv.directionmap.model.ResponseMessage;
import com.example.hungnv.directionmap.model.person.Person;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PersonService {

    @POST("register")
    Call<ResponseMessage> register(@Body Person person);
}
