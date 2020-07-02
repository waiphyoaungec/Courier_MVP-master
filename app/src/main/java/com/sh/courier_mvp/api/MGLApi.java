package com.sh.courier_mvp.api;

import com.sh.courier_mvp.model.Delivery;
import com.sh.courier_mvp.model.Loginresponse;
import com.sh.courier_mvp.model.Pickupdetailresponse;
import com.sh.courier_mvp.model.Pickuplistresponse;
import com.sh.courier_mvp.model.ShipperModel;
import com.sh.courier_mvp.model.WaybillResponse;
import com.sh.courier_mvp.model.Waybilldetailresponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MGLApi {

    @POST("auth/login")
    @FormUrlEncoded
    Call<Loginresponse> login(@Field("name") String name,
                              @Field("password") String password);

    @GET("waybill_noti_list")
    Call<Pickuplistresponse> getWayBillList(@Query("token") String token);

    @GET("pickup_noti_list")
    Call<Pickuplistresponse> getPickUpList(@Query("token") String token);

    @GET("pickup")
    Call<Pickupdetailresponse[]> getPickUpById(@Query("token") String token,
                                             @Query("id") String id);

    @GET("getpaymentype")
    Call<Map<String,String>[]> getPaymentType(@Query("token") String token);

    @GET("getcustomertype")
    Call<Map<String,String>[]> getCustomerType(@Query("token") String token);

    @GET("waybillcheckpointlist")
    Call<Map<String,String>[]> getCheckPoints(@Query("token") String token);

    @FormUrlEncoded
    @POST("waybilltracking")
    Call<Map<String,String>> waybilltracking(@Field("token") String token,
                           @Field("id") String id,
                           @Field("checkpoint_id") String checkpoint_id,
                           @Field("remark") String remark);

    /*@POST("deliveryroutefix")
    @FormUrlEncoded
    Call<Delivery> deliveryroutefix(@Field("token") String token,
                                    @Field("id[]") ArrayList<String> id);*/
    @FormUrlEncoded
    @POST("deliveryroutefix")
    Call<Delivery> deliveryroutefix(
            @Field("token") String token, @Field("id[]") ArrayList<String> id);

    @GET("accept_pickup")
    Call<Map<String,String>> accept_pickup(@Query("token") String token,
                                           @Query("pickup_id") String pickup_id);

    @GET("accept_waybill")
    Call<Map<String,String>> accept_waybill(@Query("token") String token,
                                           @Query("waybill_id") String waybill_id);

    @GET("noti_counts")
    Call<Map<String,String>> getPickupNotiCount(@Query("token") String token);

    @GET("get_remote_charges")
    Call<Map<String,String>> getRemoteCharges(@Query("token") String token,
                                              @Query("remote_to") int remote_to,
                                              @Query("remote_from")int remote_from);


    @GET("waybill_show")
    Call<WaybillResponse> getWayBillById(@Query("token") String token,
                                         @Query("id") String id);

    @GET("waybill_showbycode")
    Call<Waybilldetailresponse> getWayBillByCode(@Query("token") String token,
                                               @Query("code") String code);


    @GET("waybill_list")
    Call<Pickuplistresponse> getWayBillListByPickupId(@Query("token") String token,
                                               @Query("id") String id);

    @GET("locations")
    Call<Map<String,String>[]> getLocation(@Query("token") String token);

    @GET("remote_areas")
    Call<Map<String,String>[]> getRemoteArea(@Query("token") String token,
                                             @Query("id") String id);

    @POST("deliverywaybill")
    @FormUrlEncoded
    Call<Map<String,String>> delivery(@Field("token") String token,
                                             @Field("receiver_name") String receiver_name,
                                             @Field("waybill_id") String waybill_id);

    @GET("ratecard_amt")
    Call<Map<String,String>> getRateCard(@Query("token") String token,
                                           @Query("loc_from") String loc_from,
                                           @Query("loc_to") String loc_to,
                                           @Query("gw") String gw,
                                           @Query("actual_weight") String actual_weight,
                                           @Query("cus_type") String cus_type,
                                           @Query("payment_type") String payment_type,
                                           @Query("cur_id") String cur_id,
                                           @Query("shipper_code") String shipper_code,
                                           @Query("ratecard") String ratecard);

    @GET("waybillsearch")
    Call<Waybilldetailresponse> getWaybillSearch(@Query("token") String token,
                                         @Query("created_by") int created_by);

    @POST("waybill_store")
    @FormUrlEncoded
    Call<Map<String,String>> addWayBill(@Field("token") String token,
                                        @Field("location_from") String location_from,
                                        @Field("location_to") String location_to,
                                        @Field("remote_from") int remote_from,
                                        @Field("remote_to") int remote_to,
                                        @Field("waybill_code") String waybill_code,
                                        @Field("customer_type") int customer_type,
                                        @Field("doc_type") int doc_type,
                                        @Field("description") String description,
                                        @Field("declare_value") double declare_value,
                                        @Field("shipper_code") String shipper_code,
                                        @Field("shipper") String shipper,
                                        @Field("shipper_company_name") String shipper_company_name,
                                        @Field("shipper_contact_name") String shipper_contact_name,
                                        @Field("shipper_addr") String shipper_addr,
                                        @Field("shipper_phone") String shipper_phone,
                                        @Field("shipper_email") String  shipper_email,
                                        @Field("consignee") String  consignee_code,
                                        @Field("consignee_company_name") String  consignee_company_name,
                                        @Field("consignee_contact_name") String  consignee_contact_name,
                                        @Field("consignee_addr") String  consignee_addr,
                                        @Field("consignee_phone") String  consignee_phone,
                                        @Field("consignee_email") String  consignee_email,
                                        @Field("remark") String  remark,
                                        @Field("payment_type") int  payment_type,
                                        @Field("duty_tax") int  duty_tax,
                                        @Field("service_type") int service_type,
                                        @Field("gross_weight") double  gross_weight,
                                        @Field("length") double length,
                                        @Field("width") double width,
                                        @Field("height") double height,
                                        @Field("actual_weight") double actual_weight,
                                        @Field("pieces") int pieces,
                                        @Field("cur_id") int cur_id,
                                        @Field("cur_rate") double cur_rate,
                                        @Field("item_amt") double item_amt,
                                        @Field("service_amt") double service_amt,
                                        @Field("charges") double charges,
                                        @Field("commercial_tax") double commercial_tax,
                                        @Field("dis_percent") double dis_percent,
                                        @Field("dis_amt") double dis_amt,
                                        @Field("area_charges") double area_charges,
                                        @Field("online_charges") double online_charges,
                                        @Field("additional_amt") double additional_amt,
                                        @Field("received_amt") double received_amt,
                                        @Field("received_amt_cur_id") int received_amt_cur_id,
                                        @Field("total_amt") double total_amt,
                                        @Field("pickup_id") String pickup_id,
                                        @Field("service_alert") String service_alert,
                                        @Field("img") String img,
                                        @Field("mode") String mode,
                                        @Field("id") int id,
                                        @Field("ratecard_id") String ratecard_id,
                                        @Field("pickup_time") String pickupTime,
                                        @Field("two_percent_discount") double two_percent_discount);
    @POST("pickup_store")
    Call<Map<String,String>> addPickup(@Query("token") String token,
                                        @Query("location_from") String location_from,
                                        @Query("location_to") String location_to,
                                        @Query("remote_from") int remote_from,
                                        @Query("remote_to") int remote_to,
                                        @Query("dom_int") String dom_int,
                                        @Query("doc_type") int doc_type,
                                        @Query("shipper_code") String shipper_code,
                                        @Query("shipper") String shipper,
                                        @Query("shipper_company_name") String shipper_company_name,
                                        @Query("shipper_contact_name") String shipper_contact_name,
                                        @Query("shipper_addr") String shipper_addr,
                                        @Query("shipper_phone") String shipper_phone,
                                        @Query("ready_time") String ready_time,
                                        @Query("ready_date")String ready_date,
                                        @Query("pickup_by") String pickup_by,
                                        @Query("remark") String remark,
                                        @Query("payment_type") int paymenttype);


    @GET("api.customerspickup")
    Call<List<ShipperModel>> getShipperInfo(
            @Query("q") String keyword
    );






}
