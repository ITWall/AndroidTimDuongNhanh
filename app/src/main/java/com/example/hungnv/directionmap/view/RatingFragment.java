package com.example.hungnv.directionmap.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.hungnv.directionmap.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class RatingFragment extends Fragment implements View.OnClickListener{

    private Button mBtnSubmitRating;
    private View view;

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

    private void mapping(View v){
        mBtnSubmitRating = v.findViewById(R.id.btn_submit_rating);
    }

    private void setup(View v){
        mapping(v);
        mBtnSubmitRating.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_submit_rating:
                getActivity().getSupportFragmentManager().beginTransaction().
                        addToBackStack(RatingFragment.class.getSimpleName()).
                        replace(R.id.containerCreateAccount, new RegisterFragment()).
                        commit();
                break;
        }
    }
}
