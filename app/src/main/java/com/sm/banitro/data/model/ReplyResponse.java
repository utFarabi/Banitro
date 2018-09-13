package com.sm.banitro.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReplyResponse {
    @SerializedName("is_replied")
    @Expose
    private boolean isReplied;
    @SerializedName("error_message")
    @Expose
    private String errorMessage;

    public boolean isReplied() {
        return isReplied;
    }

    public void setReplied(boolean replied) {
        isReplied = replied;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}