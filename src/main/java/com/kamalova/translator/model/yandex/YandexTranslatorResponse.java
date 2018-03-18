package com.kamalova.translator.model.yandex;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Awesome Pojo Generator
 */
public class YandexTranslatorResponse {
    @SerializedName("code")
    @Expose
    private Integer code;

    @SerializedName("text")
    @Expose
    private List<String> text;

    @SerializedName("lang")
    @Expose
    private String lang;

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setText(List<String> text) {
        this.text = text;
    }

    public List<String> getText() {
        return text;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getLang() {
        return lang;
    }
}