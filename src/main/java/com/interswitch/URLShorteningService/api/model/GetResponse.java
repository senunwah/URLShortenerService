package com.interswitch.URLShorteningService.api.model;

public class GetResponse {

    private String Url;

    public GetResponse(String url) {
        Url = url;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
