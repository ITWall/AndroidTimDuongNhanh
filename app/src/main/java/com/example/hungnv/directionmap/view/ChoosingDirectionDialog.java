package com.example.hungnv.directionmap.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.hungnv.directionmap.OnChoosingDirectionListener;
import com.example.hungnv.directionmap.R;

import java.util.List;

public class ChoosingDirectionDialog extends Dialog implements View.OnClickListener {
    private List<Integer> listDirection;
    private RadioGroup mRgChoosingDirection;
    private View v;
    private OnChoosingDirectionListener mOnChoosingDirectionListener;
    private Button mBtnOkChoosingDirection;
    private Button mBtnCancelChoosingDirection;

    public ChoosingDirectionDialog(@NonNull Context context, List<Integer> listDirection, OnChoosingDirectionListener onChoosingDirectionListener) {
        super(context);
        this.listDirection = listDirection;
        this.mOnChoosingDirectionListener = onChoosingDirectionListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = getLayoutInflater();
        v = inflater.inflate(R.layout.dialog_choosing, null);
        setContentView(v);
        setup(v);
    }

    private void setup(View v) {
        mapping(v);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 18, 0, 18);
        mRgChoosingDirection.clearCheck();
        int id = 0;
        for (int i = 0; i < listDirection.size(); i++) {
            RadioButton mRadioButtonResult = new RadioButton(this.getContext());
            mRadioButtonResult.setText("Path " + listDirection.get(i));
            mRadioButtonResult.setTextSize(16);
            mRadioButtonResult.setLayoutParams(layoutParams);
            mRgChoosingDirection.addView(mRadioButtonResult);
            if (i == 0) {
                id = mRadioButtonResult.getId();
            }
        }
        mRgChoosingDirection.check(id);
        mBtnOkChoosingDirection.setOnClickListener(this);
        mBtnCancelChoosingDirection.setOnClickListener(this);
    }

    private void mapping(View v) {
        mRgChoosingDirection = v.findViewById(R.id.rgChoosingDirection);
        mBtnOkChoosingDirection = v.findViewById(R.id.btn_ok_choosing_direction);
        mBtnCancelChoosingDirection = v.findViewById(R.id.btn_cancel_choosing_direction);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_ok_choosing_direction:
                int selection = mRgChoosingDirection.indexOfChild(v.findViewById(mRgChoosingDirection.getCheckedRadioButtonId()));
                if (selection < 0) {
                    Toast.makeText(this.getContext(), "Hãy chọn một kết quả", Toast.LENGTH_SHORT).show();
                } else {
                    mOnChoosingDirectionListener.onChoosing(listDirection.get(selection));
                    this.dismiss();
                }
            case R.id.btn_cancel_choosing_direction:
                this.dismiss();
        }
    }
}
