package com.example.hungnv.directionmap.controller;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

public class PermissionController {
    private Activity activity;

    public PermissionController(Activity activity) {
        this.activity = activity;
    }

    public boolean checkSelfPermission(String permission){
        return ActivityCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public void askPermission(String[] permissions){
        ActivityCompat.requestPermissions(activity, permissions, 0);
    }
}
