package com.supinfo.suppictures.Model.rest.ResponseInfo;
public class RestStatus {
    private int httpStatus;
    private String message;

    public RestStatus(int httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
