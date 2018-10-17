package com.example.hungnv.directionmap.controller;

import com.example.hungnv.directionmap.model.ResponseRegister;
import com.example.hungnv.directionmap.model.person.Person;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PersonService {

    @POST("register")
    Call<ResponseRegister> register(@Body Person person);
}
