
package com.sh.courier_mvp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pickupdetailresponse {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("doc_type")
    @Expose
    private String docType;
    @SerializedName("payment_type")
    @Expose
    private String paymentType;
    @SerializedName("location_from")
    @Expose
    private String locationFrom;
    @SerializedName("location_to")
    @Expose
    private String locationTo;
    @SerializedName("shipper_ac_code")
    @Expose
    private String shipper_ac_code;
    @SerializedName("shipper")
    @Expose
    private String shipper;
    @SerializedName("shipper_company_name")
    @Expose
    private String shipperCompanyName;
    @SerializedName("shipper_contact_name")
    @Expose
    private String shipperContactName;
    @SerializedName("shipper_addr")
    @Expose
    private String shipperAddr;
    @SerializedName("shipper_phone")
    @Expose
    private String shipperPhone;
    @SerializedName("created_by")
    @Expose
    private String createdBy;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("remark")
    @Expose
    private String remark;
    @SerializedName("ready_time")
    @Expose
    private String readyTime;
    @SerializedName("ready_date")
    @Expose
    private String readyDate;
    @SerializedName("customer_type")
    @Expose
    private String customerType;
    @SerializedName("ratecard")
    @Expose
    private Ratecard ratecard;
    @SerializedName("is_percent")
    @Expose
    private String isPercent;
    @SerializedName("dis_percent")
    @Expose
    private String disPercent;
    @SerializedName("dis_value1")
    @Expose
    private String disValue1;
    @SerializedName("dis_value2")
    @Expose
    private String disValue2;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getLocationFrom() {
        return locationFrom;
    }

    public void setLocationFrom(String locationFrom) {
        this.locationFrom = locationFrom;
    }

    public String getLocationTo() {
        return locationTo;
    }

    public void setLocationTo(String locationTo) {
        this.locationTo = locationTo;
    }

    public String getShipperCode() {
        return shipper_ac_code;
    }

    public void setShipperCode(String shipperCode) {
        this.shipper_ac_code = shipperCode;
    }

    public String getShipper() {
        return shipper;
    }

    public void setShipper(String shipper) {
        this.shipper = shipper;
    }

    public String getShipperCompanyName() {
        return shipperCompanyName;
    }

    public void setShipperCompanyName(String shipperCompanyName) {
        this.shipperCompanyName = shipperCompanyName;
    }

    public String getShipperContactName() {
        return shipperContactName;
    }

    public void setShipperContactName(String shipperContactName) {
        this.shipperContactName = shipperContactName;
    }

    public String getShipperAddr() {
        return shipperAddr;
    }

    public void setShipperAddr(String shipperAddr) {
        this.shipperAddr = shipperAddr;
    }

    public String getShipperPhone() {
        return shipperPhone;
    }

    public void setShipperPhone(String shipperPhone) {
        this.shipperPhone = shipperPhone;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getReadyTime() {
        return readyTime;
    }

    public String getReadyDate() {
        return readyDate;
    }

    public void setReadyDate(String readyDate) {
        this.readyDate = readyDate;
    }

    public void setReadyTime(String readyTime) {
        this.readyTime = readyTime;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public Ratecard getRatecard() {
        return ratecard;
    }

    public void setRatecard(Ratecard ratecard) {
        this.ratecard = ratecard;
    }

    public String getIsPercent() {
        return isPercent;
    }

    public void setIsPercent(String isPercent) {
        this.isPercent = isPercent;
    }

    public String getDisPercent() {
        return disPercent;
    }

    public void setDisPercent(String disPercent) {
        this.disPercent = disPercent;
    }

    public String getDisValue1() {
        return disValue1;
    }

    public void setDisValue1(String disValue1) {
        this.disValue1 = disValue1;
    }

    public String getDisValue2() {
        return disValue2;
    }

    public void setDisValue2(String disValue2) {
        this.disValue2 = disValue2;
    }

}
