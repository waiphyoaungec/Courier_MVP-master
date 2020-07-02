
package com.sh.courier_mvp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Waybilldetailresponse implements Serializable {

    @SerializedName("response")
    @Expose
    private String response;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("waybill")
    @Expose
    private List<Waybill> waybill;
    @SerializedName("waybill_data")
    @Expose
    private List<Waybill> waybillList;
    @SerializedName("currencies")
    @Expose
    private List<Currency> currencies = null;
    @SerializedName("detail_count")
    @Expose
    private Integer detailCount;
    @SerializedName("is_delivered")
    @Expose
    private Boolean isDelivered;

    public Boolean getDelivered() {
        return isDelivered;
    }

    public void setDelivered(Boolean delivered) {
        isDelivered = delivered;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Waybill> getWaybill() {
        return waybill;
    }

    public void setWaybill(List<Waybill> waybill) {
        this.waybill = waybill;
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }

    public Integer getDetailCount() {
        return detailCount;
    }

    public void setDetailCount(Integer detailCount) {
        this.detailCount = detailCount;
    }

    public List<Waybill> getWaybillList() {
        return waybillList;
    }

    public void setWaybillList(List<Waybill> waybillList) {
        this.waybillList = waybillList;
    }
}
