package com.kamalova.translator.model.telegram;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Awesome Pojo Generator
 */
public class GetResponse {
    @SerializedName("result")
    @Expose
    private List<Result> result;
    @SerializedName("ok")
    @Expose
    private Boolean ok;

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setOk(Boolean ok) {
        this.ok = ok;
    }

    public Boolean getOk() {
        return ok;
    }
}