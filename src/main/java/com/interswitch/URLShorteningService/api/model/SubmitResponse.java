package com.interswitch.URLShorteningService.api.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.interswitch.URLShorteningService.utils.ServiceResponse;

import java.util.Date;
import java.util.List;

@JsonSerialize
public class SubmitResponse extends ServiceResponse {

    private static String Domain = "https://bit.ly/";
    private String OldUrl;
    private String LinkId;
    private String DomainUrl = "";
    private Date DateCreated;



    public SubmitResponse(String Id, String OldUrl, Date DateCreated, String code, String Message, List<Error> errors) {
        super(code,Message,errors);
        this.OldUrl = OldUrl;
        this.LinkId = Id;
        this.DateCreated = DateCreated;
        DomainUrl = Domain + LinkId;
    }

    public static void setDomain(String domain) {
        Domain = domain;
    }

    public Date getDateCreated() {
        return DateCreated;
    }

    public String getOldUrl() {
        return OldUrl;
    }

    public static String getDomain() {
        return Domain;
    }

    public String getLinkId() {
        return LinkId;
    }

    public String getDomainUrl() {
        return DomainUrl;
    }

}
