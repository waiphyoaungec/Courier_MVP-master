
package com.sh.courier_mvp.model;
;
import com.google.gson.annotations.SerializedName;


public class PickupCreate {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("response")
    private String mResponse;
    public PickupCreate(String mMessage,String mResponse){
       this.mMessage = mMessage;
       this.mResponse = mResponse;
    }


    public String getMessage() {
        return mMessage;
    }



    public String getResponse() {
        return mResponse;
    }



}
