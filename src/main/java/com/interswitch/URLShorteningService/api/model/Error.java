package com.interswitch.URLShorteningService.api.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class Error {
    private String errorcode;
    private String Description;

    public Error(String errorCode, String description) {
        this.errorcode = errorCode;
        Description = description;
    }
}
