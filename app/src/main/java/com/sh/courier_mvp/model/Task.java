
package com.sh.courier_mvp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Task {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("tran_id")
    @Expose
    private String tranId;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("subject")
    @Expose
    private String subject;
    @SerializedName("created_by")
    @Expose
    private String createdBy;

    @SerializedName("ready_time")
    @Expose
    private String ready_time;
    @SerializedName("ready_date")
    @Expose
    private String ready_date;
    @SerializedName("route_assign_time")
    @Expose
    private String route_assign_time;

    private String remoteTo;
    @SerializedName("doc_type")
    @Expose
    private String docType;
    @SerializedName("shipper_ac_code")
    @Expose
    private String shipperAcCode;
    @SerializedName("shipper_ref")
    @Expose
    private String shipperRef;
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
    @SerializedName("pickup_by")
    @Expose
    private String pickupBy;
    @SerializedName("pickup_time")
    @Expose
    private String pickupTime;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("couriermen")
    @Expose
    private String couriermen;
    @SerializedName("location_from")
    @Expose
    private String locationFrom;
    @SerializedName("location_to")
    @Expose
    private String locationTo;
    @SerializedName("operation_accepted_by")
    @Expose
    private String operation_accepted_by;
    @SerializedName("shipper")
    @Expose
    private String shipper;
    @SerializedName("gross_weight")
    @Expose
    private String gross_weight;
    @SerializedName("consignee")
    @Expose
    private String consignee;
    @SerializedName("consignee_addr")
    @Expose
    private String consignee_addr;
    @SerializedName("consignee_phone")
    @Expose
    private String consignee_phone;
    @SerializedName("pieces")
    @Expose
    private String pieces;
    @SerializedName("total_amt")
    @Expose
    private String total_amt;
    @SerializedName("payment_type")
    @Expose
    private String payment_type;

    private boolean checked;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTranId() {
        return tranId;
    }

    public void setTranId(String tranId) {
        this.tranId = tranId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getReady_date() {
        return ready_date;
    }

    public String getReady_time() {
        return ready_time;
    }

    public String getRoute_assign_time() {
        return route_assign_time;
    }

    public void setReady_date(String ready_date) {
        this.ready_date = ready_date;
    }

    public void setReady_time(String ready_time) {
        this.ready_time = ready_time;
    }

    public void setRoute_assign_time(String route_assign_time) {
        this.route_assign_time = route_assign_time;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getRemoteTo() {
        return remoteTo;
    }

    public void setRemoteTo(String remoteTo) {
        this.remoteTo = remoteTo;
    }

    public String getShipperAcCode() {
        return shipperAcCode;
    }

    public void setShipperAcCode(String shipperAcCode) {
        this.shipperAcCode = shipperAcCode;
    }

    public String getPickupBy() {
        return pickupBy;
    }

    public void setPickupBy(String pickupBy) {
        this.pickupBy = pickupBy;
    }

    public String getShipperAddr() {
        return shipperAddr;
    }

    public void setShipperAddr(String shipperAddr) {
        this.shipperAddr = shipperAddr;
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

    public String getShipperPhone() {
        return shipperPhone;
    }

    public void setShipperPhone(String shipperPhone) {
        this.shipperPhone = shipperPhone;
    }

    public String getShipperRef() {
        return shipperRef;
    }

    public void setShipperRef(String shipperRef) {
        this.shipperRef = shipperRef;
    }

    public String getCouriermen() {
        return couriermen;
    }

    public void setCouriermen(String couriermen) {
        this.couriermen = couriermen;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(String pickupTime) {
        this.pickupTime = pickupTime;
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

    public String getOperation_accepted_by() {
        return operation_accepted_by;
    }

    public void setOperation_accepted_by(String operation_accepted_by) {
        this.operation_accepted_by = operation_accepted_by;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getConsignee_addr() {
        return consignee_addr;
    }

    public void setConsignee_addr(String consignee_addr) {
        this.consignee_addr = consignee_addr;
    }

    public String getConsignee_phone() {
        return consignee_phone;
    }

    public void setConsignee_phone(String consignee_phone) {
        this.consignee_phone = consignee_phone;
    }

    public String getGross_weight() {
        return gross_weight;
    }

    public void setGross_weight(String gross_weight) {
        this.gross_weight = gross_weight;
    }

    public String getPieces() {
        return pieces;
    }

    public void setPieces(String pieces) {
        this.pieces = pieces;
    }

    public String getShipper() {
        return shipper;
    }

    public void setShipper(String shipper) {
        this.shipper = shipper;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getTotal_amt() {
        return total_amt;
    }

    public void setTotal_amt(String total_amt) {
        this.total_amt = total_amt;
    }

    public boolean getChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
