package com.sh.courier_mvp.model.reporterror;

public class ErrorModel {
    private String api;
    private String phoneModel;
    private String date;
    private String time;

    public ErrorModel(String api, String phoneModel, String date, String time) {
        this.api = api;
        this.phoneModel = phoneModel;
        this.date = date;
        this.time = time;
    }

    public String getApi() {
        return api;
    }

    public String getPhoneModel() {
        return phoneModel;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
