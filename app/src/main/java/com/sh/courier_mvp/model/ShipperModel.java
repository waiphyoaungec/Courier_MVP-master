
package com.sh.courier_mvp.model;


import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class ShipperModel {

    @SerializedName("code")
    private String mCode;
    @SerializedName("company_name")
    private String mCompanyName;
    @SerializedName("contact_name")
    private String mContactName;
    @SerializedName("id")
    private Long mId;
    @SerializedName("mailing_addr")
    private String mMailingAddr;
    @SerializedName("mailing_email")
    private String mMailingEmail;
    @SerializedName("mailing_phone")
    private String mMailingPhone;
    @SerializedName("name")
    private String mName;
    @SerializedName("rate_card")
    private String mRateCard;
    @SerializedName("rate_card_hdr_ids")
    private String mRateCardHdrIds;


    public ShipperModel(String mCode, String mCompanyName, String mContactName, Long mId, String mMailingAddr, String mMailingEmail, String mMailingPhone,
                        String mName, String mRateCard, String mRateCardHdrIds){
        this.mCode = mCode;
        this.mCompanyName = mCompanyName;
        this.mContactName = mContactName;
        this.mId = mId;
        this.mMailingAddr = mMailingAddr;
        this.mMailingEmail = mMailingEmail;
        this.mMailingPhone = mMailingPhone;
        this.mName = mName;
        this.mRateCard = mRateCard;
        this.mRateCardHdrIds = mRateCardHdrIds;
    }

    public String getCode() {
        return mCode;
    }



    public String getCompanyName() {
        return mCompanyName;
    }


    public String getContactName() {
        return mContactName;
    }



    public Long getId() {
        return mId;
    }



    public String getMailingAddr() {
        return mMailingAddr;
    }



    public String getMailingEmail() {
        return mMailingEmail;
    }



    public String getMailingPhone() {
        return mMailingPhone;
    }



    public String getName() {
        return mName;
    }



    public String getRateCard() {
        return mRateCard;
    }


    public String getRateCardHdrIds() {
        return mRateCardHdrIds;
    }



}
