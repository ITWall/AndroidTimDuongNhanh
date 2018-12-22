package com.example.hungnv.directionmap.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.hungnv.directionmap.OnSelectLevelListener;
import com.example.hungnv.directionmap.R;

public class RatingDialog extends Dialog implements View.OnClickListener{
    private RadioGroup mRgResult;
    private Button mBtnOk;
    private Button mBtnCancel;
    private OnSelectLevelListener onSelectLevelListener;

    public RatingDialog(@NonNull Context context, OnSelectLevelListener onSelectLevelListener) {
        super(context);
        this.onSelectLevelListener = onSelectLevelListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_rating, null);
        setContentView(v);
        setup(v);
    }

    private void setup(View v) {
        mapping(v);
        mBtnOk.setOnClickListener(this);
        mBtnCancel.setOnClickListener(this);
        mRgResult.check(mRgResult.getChildAt(0).getId());
    }

    private void mapping(View v) {
        mRgResult = v.findViewById(R.id.rg_result);
        mBtnCancel = v.findViewById(R.id.btn_cancel);
        mBtnOk = v.findViewById(R.id.btn_ok);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_ok:
                int radioButtonID = mRgResult.getCheckedRadioButtonId();
                RadioButton mRadioButton = mRgResult.findViewById(radioButtonID);
                if (mRadioButton != null) {
                    onSelectLevelListener.onSelected(mRadioButton.getText().toString().split(" ")[0]);
                }
                dismiss();
                break;
            case R.id.btn_cancel:
                dismiss();
                break;
        }
    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//    }
}
