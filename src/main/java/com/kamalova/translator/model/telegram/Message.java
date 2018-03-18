package com.kamalova.translator.model.telegram;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Awesome Pojo Generator
 */
public class Message {
    @SerializedName("date")
    @Expose
    private Integer date;
    @SerializedName("chat")
    @Expose
    private Chat chat;
    @SerializedName("message_id")
    @Expose
    private Integer message_id;
    @SerializedName("from")
    @Expose
    private From from;
    @SerializedName("text")
    @Expose
    private String text;

    public void setDate(Integer date) {
        this.date = date;
    }

    public Integer getDate() {
        return date;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public Chat getChat() {
        return chat;
    }

    public void setMessage_id(Integer message_id) {
        this.message_id = message_id;
    }

    public Integer getMessage_id() {
        return message_id;
    }

    public void setFrom(From from) {
        this.from = from;
    }

    public From getFrom() {
        return from;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}