package com.example.matthijsblankevoort.eventlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dates {

    @SerializedName("start")
    @Expose
    private Start start;

    public Start getStart() {
        return start;
    }

    public void setStart(Start start) {
        this.start = start;
    }
}