package com.example.hungnv.directionmap.view;


import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hungnv.directionmap.OnSelectLevelListener;
import com.example.hungnv.directionmap.R;
import com.example.hungnv.directionmap.controller.OnRatingListener;
import com.example.hungnv.directionmap.controller.PermissionController;
import com.example.hungnv.directionmap.controller.RatingController;
import com.example.hungnv.directionmap.model.Device;
import com.example.hungnv.directionmap.model.Edge;
import com.example.hungnv.directionmap.model.Rating;
import com.example.hungnv.directionmap.model.ResponseMessage;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import org.mapsforge.core.model.LatLong;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class RatingFragment extends Fragment implements View.OnClickListener, OnSelectLevelListener, OnRatingListener {

    private Button mBtnSubmitRating;
    private View view;
    private PlaceAutocompleteFragment autocompleteFragmentRating;
    private TextView mTvPlaceRating;
    private TextView mTvLevelRating;
    private RelativeLayout mRlAutoRating;
    private LatLong ratingPlaceLatlong;
    private PermissionController permissionController;
    private ProgressDialog mProgressDialog;
    public RatingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
            view = inflater.inflate(R.layout.fragment_rating, container, false);
        } catch (InflateException e) {
            /* map is already there, just return view as it is */
        }
        setup(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        autocompleteFragmentRating.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                mTvPlaceRating.setText(place.getName());
                ratingPlaceLatlong = new LatLong(place.getLatLng().latitude, place.getLatLng().longitude);
            }

            @Override
            public void onError(Status status) {
                Toast.makeText(getContext(), "An error occurred: " + status, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void mapping(View v) {
        mBtnSubmitRating = v.findViewById(R.id.btn_submit_rating);
        autocompleteFragmentRating = (PlaceAutocompleteFragment) getActivity().getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment_rating);
        mTvPlaceRating = v.findViewById(R.id.tv_place_rating);
        mTvLevelRating = v.findViewById(R.id.tv_level_rating);
        mRlAutoRating = v.findViewById(R.id.rlAutoRating);
    }

    private void setup(View v) {
        mapping(v);
        mTvLevelRating.setOnClickListener(this);
        mRlAutoRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "abcd", Toast.LENGTH_SHORT).show();
            }
        });
        permissionController = new PermissionController(getActivity());
        if (!this.permissionController.checkSelfPermission(Manifest.permission.READ_PHONE_STATE)) {
//            permissionController.askPermission(new String[]{Manifest.permission.READ_PHONE_STATE});
            requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, 0);
        } else {
            mBtnSubmitRating.setOnClickListener(this);
        }
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage("Submitting");
        mProgressDialog.setCancelable(false);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mBtnSubmitRating.setOnClickListener(this);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit_rating:
                RatingController ratingController = new RatingController(getContext(), this);
                Rating rating = new Rating();
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getActivity(), "Bạn cần cấp quyền để sử dụng tình năng này", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mTvLevelRating.getText().toString().length() == 0 || mTvPlaceRating.getText().toString().length() == 0) {
                    Toast.makeText(getActivity(), "Bạn cần nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    TelephonyManager telephonyManager = (TelephonyManager)getActivity().getSystemService(Context.TELEPHONY_SERVICE);
                    Device device = new Device(telephonyManager.getDeviceId(), 0);
                    rating.setDevice(device);
                    rating.setId(0);
                    com.example.hungnv.directionmap.model.Place place = new com.example.hungnv.directionmap.model.Place();
                    place.setEdges(new ArrayList<Edge>());
                    place.setId(0);
                    place.setLatitude(ratingPlaceLatlong.latitude);
                    place.setLongitude(ratingPlaceLatlong.longitude);
                    place.setName(mTvPlaceRating.getText().toString());
                    rating.setPlace(place);
                    rating.setStatus(1);
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    rating.setTime(format.format(calendar.getTime()));
                    rating.setTrafficStatus(Integer.parseInt(mTvLevelRating.getText().toString()));
                    ratingController.insertRating(rating);
                }
                break;
            case R.id.tv_level_rating:
                RatingDialog ratingDialog = new RatingDialog(getActivity(), this);
                DisplayMetrics displayMetrics = new DisplayMetrics();
                getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int height = displayMetrics.heightPixels;
                int width = displayMetrics.widthPixels;
                ratingDialog.setCancelable(false);
                ratingDialog.show();
                ratingDialog.getWindow().setLayout(width * 2 / 3, height / 2);
                break;
        }
    }

    @Override
    public void onSelected(String level) {
        mTvLevelRating.setText(level);
    }

    @Override
    public void onSubmiiting() {
        mProgressDialog.show();
    }

    @Override
    public void onSuccess(ResponseMessage responseMessage) {
        mProgressDialog.dismiss();
        Toast.makeText(getContext(), "Submit " + responseMessage.getMessage(), Toast.LENGTH_SHORT).show();
        getActivity().onBackPressed();
    }

    @Override
    public void onFailed(ResponseMessage responseMessage) {
        mProgressDialog.dismiss();
        Toast.makeText(getContext(), "Submit " + responseMessage.getMessage(), Toast.LENGTH_SHORT).show();
        getActivity().onBackPressed();
    }
}
