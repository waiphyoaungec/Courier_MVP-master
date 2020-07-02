
package com.sh.courier_mvp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Exist {

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
    private Object remoteFrom;
    @SerializedName("remote_to")
    @Expose
    private Object remoteTo;
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
    private Integer docType;
    @SerializedName("description")
    @Expose
    private Object description;
    @SerializedName("declare_value")
    @Expose
    private Integer declareValue;
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
    private Integer paymentType;
    @SerializedName("duty_tax")
    @Expose
    private Double dutyTax;
    @SerializedName("service_type")
    @Expose
    private Double serviceType;
    @SerializedName("gross_weight")
    @Expose
    private Double grossWeight;
    @SerializedName("length")
    @Expose
    private Double length;
    @SerializedName("width")
    @Expose
    private Double width;
    @SerializedName("height")
    @Expose
    private Double height;
    @SerializedName("actual_weight")
    @Expose
    private Double actualWeight;
    @SerializedName("pieces")
    @Expose
    private Integer pieces;
    @SerializedName("cur_id")
    @Expose
    private Integer curId;
    @SerializedName("cur_rate")
    @Expose
    private Integer curRate;
    @SerializedName("item_amt")
    @Expose
    private Double itemAmt;
    @SerializedName("service_amt")
    @Expose
    private Double serviceAmt;
    @SerializedName("charges")
    @Expose
    private Double charges;
    @SerializedName("commercial_tax")
    @Expose
    private Double commercialTax;
    @SerializedName("dis_percent")
    @Expose
    private Double disPercent;
    @SerializedName("dis_amt")
    @Expose
    private Double disAmt;
    @SerializedName("area_charges")
    @Expose
    private Double areaCharges;
    @SerializedName("additional_amt")
    @Expose
    private Double additionalAmt;
    @SerializedName("total_amt")
    @Expose
    private Double totalAmt;
    @SerializedName("received_amt")
    @Expose
    private Double receivedAmt;
    @SerializedName("received_amt_cur_id")
    @Expose
    private Integer receivedAmtCurId;
    @SerializedName("pickup_id")
    @Expose
    private Object pickupId;
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
    private Integer currentLocationId;
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
    @SerializedName("operation_accepted_by")
    @Expose
    private Integer operationAcceptedBy;
    @SerializedName("created_by")
    @Expose
    private Integer createdBy;
    @SerializedName("updated_by")
    @Expose
    private Object updatedBy;
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
    private String receiverName;
    @SerializedName("receive_date")
    @Expose
    private String receiveDate;
    @SerializedName("route_assign_time")
    @Expose
    private Object routeAssignTime;
    @SerializedName("received_by")
    @Expose
    private Object receivedBy;

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

    public Object getRemoteFrom() {
        return remoteFrom;
    }

    public void setRemoteFrom(Object remoteFrom) {
        this.remoteFrom = remoteFrom;
    }

    public Object getRemoteTo() {
        return remoteTo;
    }

    public void setRemoteTo(Object remoteTo) {
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

    public Integer getDocType() {
        return docType;
    }

    public void setDocType(Integer docType) {
        this.docType = docType;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public Integer getDeclareValue() {
        return declareValue;
    }

    public void setDeclareValue(Integer declareValue) {
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

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    public Double getDutyTax() {
        return dutyTax;
    }

    public void setDutyTax(Double dutyTax) {
        this.dutyTax = dutyTax;
    }

    public Double getServiceType() {
        return serviceType;
    }

    public void setServiceType(Double serviceType) {
        this.serviceType = serviceType;
    }

    public Double getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(Double grossWeight) {
        this.grossWeight = grossWeight;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getActualWeight() {
        return actualWeight;
    }

    public void setActualWeight(Double actualWeight) {
        this.actualWeight = actualWeight;
    }

    public Integer getPieces() {
        return pieces;
    }

    public void setPieces(Integer pieces) {
        this.pieces = pieces;
    }

    public Integer getCurId() {
        return curId;
    }

    public void setCurId(Integer curId) {
        this.curId = curId;
    }

    public Integer getCurRate() {
        return curRate;
    }

    public void setCurRate(Integer curRate) {
        this.curRate = curRate;
    }

    public Double getItemAmt() {
        return itemAmt;
    }

    public void setItemAmt(Double itemAmt) {
        this.itemAmt = itemAmt;
    }

    public Double getServiceAmt() {
        return serviceAmt;
    }

    public void setServiceAmt(Double serviceAmt) {
        this.serviceAmt = serviceAmt;
    }

    public Double getCharges() {
        return charges;
    }

    public void setCharges(Double charges) {
        this.charges = charges;
    }

    public Double getCommercialTax() {
        return commercialTax;
    }

    public void setCommercialTax(Double commercialTax) {
        this.commercialTax = commercialTax;
    }

    public Double getDisPercent() {
        return disPercent;
    }

    public void setDisPercent(Double disPercent) {
        this.disPercent = disPercent;
    }

    public Double getDisAmt() {
        return disAmt;
    }

    public void setDisAmt(Double disAmt) {
        this.disAmt = disAmt;
    }

    public Double getAreaCharges() {
        return areaCharges;
    }

    public void setAreaCharges(Double areaCharges) {
        this.areaCharges = areaCharges;
    }

    public Double getAdditionalAmt() {
        return additionalAmt;
    }

    public void setAdditionalAmt(Double additionalAmt) {
        this.additionalAmt = additionalAmt;
    }

    public Double getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(Double totalAmt) {
        this.totalAmt = totalAmt;
    }

    public Double getReceivedAmt() {
        return receivedAmt;
    }

    public void setReceivedAmt(Double receivedAmt) {
        this.receivedAmt = receivedAmt;
    }

    public Integer getReceivedAmtCurId() {
        return receivedAmtCurId;
    }

    public void setReceivedAmtCurId(Integer receivedAmtCurId) {
        this.receivedAmtCurId = receivedAmtCurId;
    }

    public Object getPickupId() {
        return pickupId;
    }

    public void setPickupId(Object pickupId) {
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

    public Integer getCurrentLocationId() {
        return currentLocationId;
    }

    public void setCurrentLocationId(Integer currentLocationId) {
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

    public Integer getOperationAcceptedBy() {
        return operationAcceptedBy;
    }

    public void setOperationAcceptedBy(Integer operationAcceptedBy) {
        this.operationAcceptedBy = operationAcceptedBy;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Object getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Object updatedBy) {
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

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(String receiveDate) {
        this.receiveDate = receiveDate;
    }

    public Object getRouteAssignTime() {
        return routeAssignTime;
    }

    public void setRouteAssignTime(Object routeAssignTime) {
        this.routeAssignTime = routeAssignTime;
    }

    public Object getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(Object receivedBy) {
        this.receivedBy = receivedBy;
    }

}
