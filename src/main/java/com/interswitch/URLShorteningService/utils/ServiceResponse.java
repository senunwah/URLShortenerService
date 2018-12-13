package com.interswitch.URLShorteningService.utils;

import com.interswitch.URLShorteningService.api.model.Error;

import java.util.List;

public class ServiceResponse {
    private String responseCode;
    private String responseMessage;
    private List<Error> errors;

    public ServiceResponse(String responseCode, String responseMessage, List<Error> errors) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
        this.errors = errors;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public List<Error> getErrors() {
        return errors;
    }
}
