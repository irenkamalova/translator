package com.kamalova.translator.model.telegram;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Awesome Pojo Generator
 */
public class From {
    @SerializedName("language_code")
    @Expose
    private String language_code;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("is_bot")
    @Expose
    private Boolean is_bot;
    @SerializedName("first_name")
    @Expose
    private String first_name;
    @SerializedName("username")
    @Expose
    private String username;

    public void setLanguage_code(String language_code) {
        this.language_code = language_code;
    }

    public String getLanguage_code() {
        return language_code;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setIs_bot(Boolean is_bot) {
        this.is_bot = is_bot;
    }

    public Boolean getIs_bot() {
        return is_bot;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}