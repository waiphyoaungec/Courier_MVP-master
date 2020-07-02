
package com.sh.courier_mvp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item {

    @SerializedName("ratecard_id")
    @Expose
    private String ratecardId;
    @SerializedName("ratecard_name")
    @Expose
    private String ratecardName;

    public String getRatecardId() {
        return ratecardId;
    }

    public void setRatecardId(String ratecardId) {
        this.ratecardId = ratecardId;
    }

    public String getRatecardName() {
        return ratecardName;
    }

    public void setRatecardName(String ratecardName) {
        this.ratecardName = ratecardName;
    }

}
