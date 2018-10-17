package com.example.hungnv.directionmap.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hungnv.directionmap.OnDirectionAttached;
import com.example.hungnv.directionmap.R;
import com.example.hungnv.directionmap.controller.MapController;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class DirectionFragment extends Fragment implements View.OnClickListener{

    private PlaceAutocompleteFragment autocompleteFragmentFrom;
    private PlaceAutocompleteFragment autocompleteFragmentTo;
    private TextView mTvFrom;
    private TextView mTvTo;
    private TextView mTvTapToClose;
    private View view;
    private OnDirectionAttached onDirectionAttached;
    private MapController mapController;
    private String startPoint, endPoint;
    public void setOnDirectionAttached(OnDirectionAttached onDirectionAttached) {
        this.onDirectionAttached = onDirectionAttached;
    }

    public DirectionFragment() {
        // Required empty public constructor
    }

    public void setMapController(MapController mapController) {
        this.mapController = mapController;
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
            view = inflater.inflate(R.layout.fragment_direction, container, false);
        } catch (InflateException e) {
            /* map is already there, just return view as it is */
        }
        setup(view);
        return view;
    }

    private void mapping(View v){
        mTvFrom = v.findViewById(R.id.tvFrom);
        mTvTo = v.findViewById(R.id.tvTo);
        mTvTapToClose = v.findViewById(R.id.tvTapToClose);
    }

    private void setup(View v){
        mapping(v);
        mTvTapToClose.setOnClickListener(this);
        autocompleteFragmentFrom = (PlaceAutocompleteFragment) getActivity().getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        autocompleteFragmentTo = (PlaceAutocompleteFragment) getActivity().getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment2);
        v.findViewById(R.id.rlAuto).bringToFront();
        v.findViewById(R.id.rlAuto2).bringToFront();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        autocompleteFragmentFrom.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                mTvFrom.setText(place.getAddress());
                startPoint = place.getLatLng().latitude + "," + place.getLatLng().longitude;
                if(mTvTo.getText().length() > 0){
                    mapController.getDirectionFromPoint(startPoint, endPoint);
                }
            }

            @Override
            public void onError(Status status) {
                Toast.makeText(getContext(), "An error occurred: " + status, Toast.LENGTH_SHORT).show();
            }
        });

        autocompleteFragmentTo.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                mTvTo.setText(place.getAddress());
                endPoint = place.getLatLng().latitude + "," + place.getLatLng().longitude;
                if(mTvFrom.getText().length() > 0){
                    mapController.getDirectionFromPoint(startPoint, endPoint);
                }
            }

            @Override
            public void onError(Status status) {
                Toast.makeText(getContext(), "An error occurred: " + status, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tvTapToClose:
                getActivity().onBackPressed();
                onDirectionAttached.onDetached();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        onDirectionAttached.onDetached();
    }
}
