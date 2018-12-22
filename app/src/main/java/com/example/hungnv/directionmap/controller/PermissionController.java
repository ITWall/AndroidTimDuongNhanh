package com.example.hungnv.directionmap.controller;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

public class PermissionController {
    private Activity activity;
    private Fragment fragment;

    public PermissionController(Activity activity) {
        this.activity = activity;
    }

    public PermissionController(Fragment fragment) {
        this.fragment = fragment;
    }

    public boolean checkSelfPermission(String permission) {
        return ActivityCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void askPermission(String[] permissions) {
        ActivityCompat.requestPermissions(activity, permissions, 0);
    }
}
