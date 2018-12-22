package com.example.hungnv.directionmap.controller;

import android.Manifest;
import android.content.Context;
import android.widget.Toast;

import com.example.hungnv.directionmap.R;
import com.example.hungnv.directionmap.model.Rating;
import com.example.hungnv.directionmap.model.ResponseMessage;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RatingController {
    private Context context;
    private OnRatingListener onRatingListener;

    public RatingController(Context context, OnRatingListener onRatingListener) {
        this.context = context;
        this.onRatingListener = onRatingListener;
    }

    public void insertRating(Rating rating) {
        onRatingListener.onSubmiiting();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(context.getString(R.string.api_rating))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RatingService ratingService = retrofit.create(RatingService.class);
        ratingService.insertRating(rating).enqueue(new Callback<ResponseMessage>() {
            @Override
            public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                if (response.body().getCode() == 200) {
                    onRatingListener.onSuccess(response.body());
                } else {
                    onRatingListener.onFailed(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseMessage> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
