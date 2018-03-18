package com.kamalova.translator.model.telegram;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Awesome Pojo Generator
 */
public class Result {
    @SerializedName("update_id")
    @Expose
    private Integer update_id;
    @SerializedName("message")
    @Expose
    private Message message;

    public void setUpdate_id(Integer update_id) {
        this.update_id = update_id;
    }

    public Integer getUpdate_id() {
        return update_id;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }
}