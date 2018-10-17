package com.example.hungnv.directionmap.controller;

import android.location.Location;

import com.example.hungnv.directionmap.model.direction.Direction;
import com.example.hungnv.directionmap.model.graphhopper.ResultPath;
import org.mapsforge.core.model.LatLong;

public interface MapListener {
    void onGetLocation(LatLong latLng, int function);
    void onDirectListener(ResultPath resultPath);
}
