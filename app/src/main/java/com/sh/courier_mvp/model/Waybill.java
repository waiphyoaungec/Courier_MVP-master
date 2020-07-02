
package com.sh.courier_mvp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Waybill implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("location_from")
    @Expose
    private String locationFrom;
    @SerializedName("location_to")
    @Expose
    private String locationTo;
    @SerializedName("remote_from")
    @Expose
    private String remoteFrom;
    @SerializedName("remote_to")
    @Expose
    private String remoteTo;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("ref_code")
    @Expose
    private Object refCode;
    @SerializedName("customer_type_id")
    @Expose
    private String customerTypeId;
    @SerializedName("doc_type")
    @Expose
    private String docType;
    @SerializedName("description")
    @Expose
    private Object description;
    @SerializedName("declare_value")
    @Expose
    private String declareValue;
    @SerializedName("shipper_code")
    @Expose
    private String shipperCode;
    @SerializedName("shipper_ac_code")
    @Expose
    private String shipperAcCode;
    @SerializedName("shipper_ref")
    @Expose
    private String shipperRef;
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
    @SerializedName("shipper_email")
    @Expose
    private String shipperEmail;
    @SerializedName("shipper_signature")
    @Expose
    private Object shipperSignature;
    @SerializedName("consignee_code")
    @Expose
    private String consigneeCode;
    @SerializedName("consignee_ac_code")
    @Expose
    private String consigneeAcCode;
    @SerializedName("consignee_ref")
    @Expose
    private String consigneeRef;
    @SerializedName("consignee")
    @Expose
    private String consignee;
    @SerializedName("consignee_company_name")
    @Expose
    private String consigneeCompanyName;
    @SerializedName("consignee_contact_name")
    @Expose
    private String consigneeContactName;
    @SerializedName("consignee_addr")
    @Expose
    private String consigneeAddr;
    @SerializedName("consignee_phone")
    @Expose
    private String consigneePhone;
    @SerializedName("consignee_email")
    @Expose
    private String consigneeEmail;
    @SerializedName("consignee_signature")
    @Expose
    private Object consigneeSignature;
    @SerializedName("remark")
    @Expose
    private String remark;
    @SerializedName("payment_type")
    @Expose
    private String paymentType;
    @SerializedName("duty_tax")
    @Expose
    private String dutyTax;
    @SerializedName("service_type")
    @Expose
    private String serviceType;
    @SerializedName("gross_weight")
    @Expose
    private String grossWeight;
    @SerializedName("length")
    @Expose
    private String length;
    @SerializedName("width")
    @Expose
    private String width;
    @SerializedName("height")
    @Expose
    private String height;
    @SerializedName("actual_weight")
    @Expose
    private String actualWeight;
    @SerializedName("pieces")
    @Expose
    private String pieces;
    @SerializedName("cur_id")
    @Expose
    private String curId;
    @SerializedName("cur_rate")
    @Expose
    private String curRate;
    @SerializedName("item_amt")
    @Expose
    private String itemAmt;
    @SerializedName("service_amt")
    @Expose
    private String serviceAmt;
    @SerializedName("charges")
    @Expose
    private String charges;
    @SerializedName("commercial_tax")
    @Expose
    private String commercialTax;
    @SerializedName("dis_percent")
    @Expose
    private String disPercent;
    @SerializedName("dis_amt")
    @Expose
    private String disAmt;
    @SerializedName("area_charges")
    @Expose
    private String areaCharges;
    @SerializedName("additional_amt")
    @Expose
    private String additionalAmt;
    @SerializedName("total_amt")
    @Expose
    private String totalAmt;
    @SerializedName("received_amt")
    @Expose
    private String receivedAmt;
    @SerializedName("received_amt_cur_id")
    @Expose
    private String receivedAmtCurId;
    @SerializedName("pickup_id")
    @Expose
    private String pickupId;
    @SerializedName("parent_id")
    @Expose
    private Object parentId;
    @SerializedName("service_alert")
    @Expose
    private String serviceAlert;
    @SerializedName("img")
    @Expose
    private String img;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("current_location_id")
    @Expose
    private String currentLocationId;
    @SerializedName("path_id")
    @Expose
    private Object pathId;
    @SerializedName("tracking_point")
    @Expose
    private Object trackingPoint;
    @SerializedName("couriermen")
    @Expose
    private Object couriermen;
    @SerializedName("deliverymen")
    @Expose
    private Object deliverymen;
    @SerializedName("created_by")
    @Expose
    private String createdBy;
    @SerializedName("updated_by")
    @Expose
    private String updatedBy;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("deleted_at")
    @Expose
    private Object deletedAt;
    @SerializedName("receiver_name")
    @Expose
    private String receiver_name;
    @SerializedName("receive_date")
    @Expose
    private String receive_date;
    @SerializedName("route_assign_time")
    @Expose
    private String route_assign_time;
    @SerializedName("received_by")
    @Expose
    private String received_by;
    @SerializedName("pkcode")
    @Expose
    private String pkcode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getRemoteFrom() {
        return remoteFrom;
    }

    public void setRemoteFrom(String remoteFrom) {
        this.remoteFrom = remoteFrom;
    }

    public String getRemoteTo() {
        return remoteTo;
    }

    public void setRemoteTo(String remoteTo) {
        this.remoteTo = remoteTo;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getRefCode() {
        return refCode;
    }

    public void setRefCode(Object refCode) {
        this.refCode = refCode;
    }

    public String getCustomerTypeId() {
        return customerTypeId;
    }

    public void setCustomerTypeId(String customerTypeId) {
        this.customerTypeId = customerTypeId;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public String getDeclareValue() {
        return declareValue;
    }

    public void setDeclareValue(String declareValue) {
        this.declareValue = declareValue;
    }

    public String getShipperCode() {
        return shipperCode;
    }

    public void setShipperCode(String shipperCode) {
        this.shipperCode = shipperCode;
    }

    public String getShipperAcCode() {
        return shipperAcCode;
    }

    public void setShipperAcCode(String shipperAcCode) {
        this.shipperAcCode = shipperAcCode;
    }

    public String getShipperRef() {
        return shipperRef;
    }

    public void setShipperRef(String shipperRef) {
        this.shipperRef = shipperRef;
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

    public String getShipperEmail() {
        return shipperEmail;
    }

    public void setShipperEmail(String shipperEmail) {
        this.shipperEmail = shipperEmail;
    }

    public Object getShipperSignature() {
        return shipperSignature;
    }

    public void setShipperSignature(Object shipperSignature) {
        this.shipperSignature = shipperSignature;
    }

    public String getConsigneeCode() {
        return consigneeCode;
    }

    public void setConsigneeCode(String consigneeCode) {
        this.consigneeCode = consigneeCode;
    }

    public String getConsigneeAcCode() {
        return consigneeAcCode;
    }

    public void setConsigneeAcCode(String consigneeAcCode) {
        this.consigneeAcCode = consigneeAcCode;
    }

    public String getConsigneeRef() {
        return consigneeRef;
    }

    public void setConsigneeRef(String consigneeRef) {
        this.consigneeRef = consigneeRef;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getConsigneeCompanyName() {
        return consigneeCompanyName;
    }

    public void setConsigneeCompanyName(String consigneeCompanyName) {
        this.consigneeCompanyName = consigneeCompanyName;
    }

    public String getConsigneeContactName() {
        return consigneeContactName;
    }

    public void setConsigneeContactName(String consigneeContactName) {
        this.consigneeContactName = consigneeContactName;
    }

    public String getConsigneeAddr() {
        return consigneeAddr;
    }

    public void setConsigneeAddr(String consigneeAddr) {
        this.consigneeAddr = consigneeAddr;
    }

    public String getConsigneePhone() {
        return consigneePhone;
    }

    public void setConsigneePhone(String consigneePhone) {
        this.consigneePhone = consigneePhone;
    }

    public String getConsigneeEmail() {
        return consigneeEmail;
    }

    public void setConsigneeEmail(String consigneeEmail) {
        this.consigneeEmail = consigneeEmail;
    }

    public Object getConsigneeSignature() {
        return consigneeSignature;
    }

    public void setConsigneeSignature(Object consigneeSignature) {
        this.consigneeSignature = consigneeSignature;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getDutyTax() {
        return dutyTax;
    }

    public void setDutyTax(String dutyTax) {
        this.dutyTax = dutyTax;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(String grossWeight) {
        this.grossWeight = grossWeight;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getActualWeight() {
        return actualWeight;
    }

    public void setActualWeight(String actualWeight) {
        this.actualWeight = actualWeight;
    }

    public String getPieces() {
        return pieces;
    }

    public void setPieces(String pieces) {
        this.pieces = pieces;
    }

    public String getCurId() {
        return curId;
    }

    public void setCurId(String curId) {
        this.curId = curId;
    }

    public String getCurRate() {
        return curRate;
    }

    public void setCurRate(String curRate) {
        this.curRate = curRate;
    }

    public String getItemAmt() {
        return itemAmt;
    }

    public void setItemAmt(String itemAmt) {
        this.itemAmt = itemAmt;
    }

    public String getServiceAmt() {
        return serviceAmt;
    }

    public void setServiceAmt(String serviceAmt) {
        this.serviceAmt = serviceAmt;
    }

    public String getCharges() {
        return charges;
    }

    public void setCharges(String charges) {
        this.charges = charges;
    }

    public String getCommercialTax() {
        return commercialTax;
    }

    public void setCommercialTax(String commercialTax) {
        this.commercialTax = commercialTax;
    }

    public String getDisPercent() {
        return disPercent;
    }

    public void setDisPercent(String disPercent) {
        this.disPercent = disPercent;
    }

    public String getDisAmt() {
        return disAmt;
    }

    public void setDisAmt(String disAmt) {
        this.disAmt = disAmt;
    }

    public String getAreaCharges() {
        return areaCharges;
    }

    public void setAreaCharges(String areaCharges) {
        this.areaCharges = areaCharges;
    }

    public String getAdditionalAmt() {
        return additionalAmt;
    }

    public void setAdditionalAmt(String additionalAmt) {
        this.additionalAmt = additionalAmt;
    }

    public String getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(String totalAmt) {
        this.totalAmt = totalAmt;
    }

    public String getReceivedAmt() {
        return receivedAmt;
    }

    public void setReceivedAmt(String receivedAmt) {
        this.receivedAmt = receivedAmt;
    }

    public String getReceivedAmtCurId() {
        return receivedAmtCurId;
    }

    public void setReceivedAmtCurId(String receivedAmtCurId) {
        this.receivedAmtCurId = receivedAmtCurId;
    }

    public String getPickupId() {
        return pickupId;
    }

    public void setPickupId(String pickupId) {
        this.pickupId = pickupId;
    }

    public Object getParentId() {
        return parentId;
    }

    public void setParentId(Object parentId) {
        this.parentId = parentId;
    }

    public String getServiceAlert() {
        return serviceAlert;
    }

    public void setServiceAlert(String serviceAlert) {
        this.serviceAlert = serviceAlert;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCurrentLocationId() {
        return currentLocationId;
    }

    public void setCurrentLocationId(String currentLocationId) {
        this.currentLocationId = currentLocationId;
    }

    public Object getPathId() {
        return pathId;
    }

    public void setPathId(Object pathId) {
        this.pathId = pathId;
    }

    public Object getTrackingPoint() {
        return trackingPoint;
    }

    public void setTrackingPoint(Object trackingPoint) {
        this.trackingPoint = trackingPoint;
    }

    public Object getCouriermen() {
        return couriermen;
    }

    public void setCouriermen(Object couriermen) {
        this.couriermen = couriermen;
    }

    public Object getDeliverymen() {
        return deliverymen;
    }

    public void setDeliverymen(Object deliverymen) {
        this.deliverymen = deliverymen;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Object getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Object deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getPkcode() {
        return pkcode;
    }

    public void setPkcode(String pkcode) {
        this.pkcode = pkcode;
    }
}
