package com.example.hungnv.directionmap.controller;

import com.example.hungnv.directionmap.model.ResponseMessage;

public interface OnRatingListener {
    void onSuccess (ResponseMessage responseMessage);
    void onFailed (ResponseMessage responseMessage);
    void onSubmiiting ();
}
