package com.interswitch.URLShorteningService.api.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.interswitch.URLShorteningService.utils.ServiceResponse;

import java.util.List;

@JsonSerialize
public class SubmitResponse extends ServiceResponse {

    private static String Domain = "https://bit.ly/";
    private String OldUrl;
    private String LinkId;
    private String DomainUrl;

    public SubmitResponse(String Id, String OldUrl, String code, String Message, List<Error> errors) {
        super(code,Message,errors);
        this.OldUrl = OldUrl;
        this.LinkId = Id;
        DomainUrl = Domain.concat(LinkId);
    }

    public SubmitResponse(String responseCode, String responseMessage, List<Error> errors, String oldUrl, String linkId, String domainUrl) {
        super(responseCode, responseMessage, errors);
        OldUrl = oldUrl;
        LinkId = linkId;
        DomainUrl = domainUrl;
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
