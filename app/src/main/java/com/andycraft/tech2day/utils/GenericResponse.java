package com.andycraft.tech2day.utils;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;

public class GenericResponse {

    @SerializedName("status")
    public String  status;
    @SerializedName("totalResults")
    public int totalResults;
    @SerializedName("articles")
    public JSONArray articles;

    @SerializedName("code")
    public String code;

    @SerializedName("message")
    public String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public JSONArray getArticles() {
        return articles;
    }

    public void setArticles(JSONArray articles) {
        this.articles = articles;
    }
}
