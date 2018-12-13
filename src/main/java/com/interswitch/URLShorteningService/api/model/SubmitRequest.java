package com.interswitch.URLShorteningService.api.model;

import javax.validation.constraints.NotBlank;

public class SubmitRequest {

    @NotBlank(message = "Url cannot be empty")
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
