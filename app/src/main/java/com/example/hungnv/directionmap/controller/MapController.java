package com.example.hungnv.directionmap.controller;

import android.Manifest;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.hungnv.directionmap.R;
import com.example.hungnv.directionmap.model.direction.Direction;
import com.example.hungnv.directionmap.model.geocoding.Geocoding;
import com.example.hungnv.directionmap.model.graphhopper.ResultPath;
import com.google.android.gms.maps.model.LatLng;

import org.mapsforge.core.model.LatLong;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapController {
    private Context context;
    private PermissionController permissionController;

    public static final int CURRENT_LOCATION = 0;
    public static final int SOURCE_LOCATION = 1;
    public static final int DESTINATION_LOCATION = 2;

    public MapListener getmMapListener() {
        return mMapListener;
    }

    public void setmMapListener(MapListener mMapListener) {
        this.mMapListener = mMapListener;
    }

    private MapListener mMapListener;

    public MapController(Context context) {
        this.context = context;
    }

    public MapController(Context context, PermissionController permissionController) {
        this.context = context;
        this.permissionController = permissionController;
    }

    public void setPermissionController(PermissionController permissionController) {
        this.permissionController = permissionController;
    }

    public void  setupMap() {
        LocationManager mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (permissionController.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) && permissionController.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 5, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    LatLong latLng = new LatLong(location.getLatitude(), location.getLongitude());
                    mMapListener.onGetLocation(latLng, CURRENT_LOCATION);
                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            });
        }
    }

    public void getLocation(String location, final int function){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(context.getString(R.string.api_map))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MapService mapService = retrofit.create(MapService.class);
        mapService.getAddressInfo(location, context.getString(R.string.google_geocoding_key)).enqueue(new Callback<Geocoding>() {
            @Override
            public void onResponse(Call<Geocoding> call, Response<Geocoding> response) {
                Geocoding geocoding = response.body();
                switch (geocoding.getStatus()){
                    case "OK":
                        LatLong latLng = new LatLong(geocoding.getListResults().get(0).getGeometry().getLocation().getLat(), geocoding.getListResults().get(0).getGeometry().getLocation().getLng());
                        mMapListener.onGetLocation(latLng, function);
                        break;
                    case "ZERO_RESULTS":
                        Toast.makeText(context, "No result for this location", Toast.LENGTH_SHORT).show();
                        break;
                    case "OVER_DAILY_LIMIT":
                        Toast.makeText(context, "OVER_DAILY_LIMIT", Toast.LENGTH_SHORT).show();
                        break;
                    case "OVER_QUERY_LIMIT":
                        Toast.makeText(context, "OVER_QUERY_LIMIT", Toast.LENGTH_SHORT).show();
                        break;
                    case "REQUEST_DENIED":
                        Toast.makeText(context, "REQUEST_DENIED", Toast.LENGTH_SHORT).show();
                        break;
                    case "INVALID_REQUEST":
                        Toast.makeText(context, "INVALID_REQUEST", Toast.LENGTH_SHORT).show();
                        break;
                    case "UNKNOWN_ERROR":
                        Toast.makeText(context, "UNKNOWN_ERROR", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<Geocoding> call, Throwable t) {
                Toast.makeText(context, "Cannot get location", Toast.LENGTH_SHORT).show();
                Log.d("Get location error", t.getMessage());
            }
        });
    }

    public void getDirection(String sourceLocation, String desLocation){
        fetchDirection(sourceLocation, desLocation);

    }

    public void fetchDirection(String source, String des){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(context.getString(R.string.api_custom))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CustomMapService customMapService = retrofit.create(CustomMapService.class);
        customMapService.getDirection(source, des).enqueue(new Callback<ResultPath>() {
            @Override
            public void onResponse(Call<ResultPath> call, Response<ResultPath> response) {
                mMapListener.onDirectListener(response.body());
            }

            @Override
            public void onFailure(Call<ResultPath> call, Throwable t) {
                Toast.makeText(context, "error when get direction", Toast.LENGTH_SHORT).show();
                Log.d("Get direction error", t.getMessage());
            }
        });
    }

    public void getDirectionFromPoint(String startPoint, String endPoint){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(context.getString(R.string.api_custom))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CustomMapService customMapService = retrofit.create(CustomMapService.class);
        customMapService.getDirectionFromPoint(startPoint, endPoint).enqueue(new Callback<ResultPath>() {
            @Override
            public void onResponse(Call<ResultPath> call, Response<ResultPath> response) {
                mMapListener.onDirectListener(response.body());
            }

            @Override
            public void onFailure(Call<ResultPath> call, Throwable t) {
                Toast.makeText(context, "error when get direction", Toast.LENGTH_SHORT).show();
                Log.d("Get direction error", t.getMessage());
            }
        });
    }

}
