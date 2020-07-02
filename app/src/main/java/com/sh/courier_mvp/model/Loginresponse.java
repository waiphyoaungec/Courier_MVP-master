
package com.sh.courier_mvp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Loginresponse {

    @SerializedName("response")
    @Expose
    private String response;
    @SerializedName("message")
    @Expose
    private Message message;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

}
