package com.example.hungnv.directionmap.view;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.hungnv.directionmap.controller.PermissionController;

public class BaseActivity extends FragmentActivity {

    protected PermissionController permissionController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        permissionController = new PermissionController(this);
    }
}
