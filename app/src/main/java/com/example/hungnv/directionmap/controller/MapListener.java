package com.example.hungnv.directionmap.controller;

import com.example.hungnv.directionmap.model.Path;
import org.mapsforge.core.model.LatLong;

import java.util.List;

public interface MapListener {
    void onGetLocation(LatLong latLng, int function);
    void onDirectListener(List<Path> resultPaths);
}
