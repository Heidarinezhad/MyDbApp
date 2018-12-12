package com.khn.mydbapp.models;

import com.google.gson.annotations.SerializedName;

public class DefaultResponse {
    @SerializedName("status")
    private int Status;

    @SerializedName("msg")
    private String Msg;

    public DefaultResponse(int status, String msg) {
        Status = status;
        Msg = msg;
    }

    public int getStatus() {
        return Status;
    }

    public String getMsg() {
        return Msg;
    }
}
