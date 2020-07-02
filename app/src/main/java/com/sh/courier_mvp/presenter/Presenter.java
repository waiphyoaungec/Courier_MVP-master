package com.sh.courier_mvp.presenter;

import android.content.Context;
import androidx.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.api.Http;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.sh.courier_mvp.api.Apis;
import com.sh.courier_mvp.api.MGLApi;
import com.sh.courier_mvp.data.AppData;
import com.sh.courier_mvp.model.Delivery;
import com.sh.courier_mvp.model.Loginresponse;
import com.sh.courier_mvp.model.Pickupdetailresponse;
import com.sh.courier_mvp.model.Pickuplistresponse;
import com.sh.courier_mvp.model.ShipperModel;
import com.sh.courier_mvp.model.WaybillResponse;
import com.sh.courier_mvp.model.Waybilldetailresponse;
import com.sh.courier_mvp.model.reporterror.ErrorModel;
import com.sh.courier_mvp.notification.NotificationScheduler;
import com.sh.courier_mvp.view.activity.MainActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class Presenter implements MainContract.MainPresenter{
    private MainContract.MainView mainView;
    private MainContract.LoginView loginView;
    private MainContract.PickUpListView pickUpListView;
    private MainContract.PickUpDetailView pickUpDetailView;
    private MainContract.AddWayBillView addWayBillView;
    private MainContract.WayBillListByPickUp wayBillListByPickUp;
    private MainContract.WayBillDetailView wayBillDetailView;
    private MainContract.DeliveryWBView deliveryWBView;
    private MainContract.TrackingPointView trackingPointView;

    private MainContract.CheckpickupView checkpickupView;
    private final MGLApi mglApi;
    private String q;
    private final String TAG =  "Presenter";
    private Context context;

    public Presenter(MainContract.MainView view, Context context) {
        this.mainView = view;
        this.mglApi = Apis.getMGLApi();
        this.context = context;
    }

    public Presenter(MainContract.LoginView view, Context context) {
        this.loginView = view;
        this.mglApi = Apis.getMGLApi();
        this.context = context;
    }

    public Presenter(MainContract.DeliveryWBView deliveryWBView, Context context) {
        this.deliveryWBView = deliveryWBView;
        this.mglApi = Apis.getMGLApi();
        this.context = context;
    }

    public Presenter(MainContract.PickUpListView pickUpListView, Context context) {
        this.pickUpListView = pickUpListView;
        this.mglApi = Apis.getMGLApi();
        this.context = context;
    }

    public Presenter(MainContract.PickUpDetailView pickUpDetailView, Context context) {
        this.pickUpDetailView = pickUpDetailView;
        this.mglApi = Apis.getMGLApi();
        this.context = context;
    }

    public Presenter(MainContract.AddWayBillView addWayBillView, Context context) {
        this.addWayBillView = addWayBillView;
        this.mglApi = Apis.getMGLApi();
        this.context = context;
    }


    public Presenter(MainContract.WayBillListByPickUp wayBillListByPickUp, Context context) {
        this.wayBillListByPickUp = wayBillListByPickUp;
        this.mglApi = Apis.getMGLApi();
        this.context = context;
    }

    public Presenter(MainContract.WayBillDetailView wayBillDetailView, Context context) {
        this.wayBillDetailView = wayBillDetailView;
        this.mglApi = Apis.getMGLApi();
        this.context = context;
    }

    public Presenter(MainContract.TrackingPointView trackingPointView, Context context) {
        this.trackingPointView = trackingPointView;
        this.mglApi = Apis.getMGLApi();
        this.context = context;
    }

    public Presenter(MainContract.CheckpickupView checkpickupView, Context context) {
        this.checkpickupView = checkpickupView;
        this.mglApi = Apis.getMGLApi();
        this.context = context;
    }



    @Override
    public void login(String name, String password) {
        Log.w(TAG, ""+name+ password);
        loginView.showProgress();
        mglApi.login(name,password).enqueue(new Callback<Loginresponse>() {
            @Override
            public void onResponse(@NonNull Call<Loginresponse> call, @NonNull Response<Loginresponse> response) {
                if(response.isSuccessful()) {
                    Log.w(TAG, "" + new Gson().toJson(response.body()));
                    if (response.body() != null) {
                        //AppData.CurrentUserLocationID = response.body().getMessage().getLocationId();
                        //AppData.CurrentUserLocationCode = response.body().getMessage().getLocationCode();
                        //AppData.CurrentUserID = response.body().getMessage().getUserId();
                        //AppData.CurrentUserName = response.body().getMessage().getUserName();
                        AppData.storeUserID(context, response.body().getMessage().getUserId());
                        AppData.StoreToken(context, response.body().getMessage().getToken());
                        loginView.gotoHomePage();
                        loginView.hideProgress();
                    }
                }else{
                    setReportError("api error:"+ new HttpException(response).response().raw().request().url().url().toString()+"\n" + new HttpException(response).getLocalizedMessage());

                }
            }

            @Override
            public void onFailure(@NonNull Call<Loginresponse> call, @NonNull Throwable t) {
                Log.w(TAG, ""+new Gson().toJson(t));
                loginView.hideProgress();
                loginView.showMessage("Login Fail");
                setReportError("app error" + t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void checkData(String name, String password) {
        if (TextUtils.isEmpty(name))
            loginView.phonenoEmpty();
        else if (TextUtils.isEmpty(password))
            loginView.passwordEmpty();
        else
            login(name, password);
    }

    @Override
    public void getPickUpList(String token) {

        mglApi.getPickUpList(token).enqueue(new Callback<Pickuplistresponse>() {
            @Override
            public void onResponse(@NonNull Call<Pickuplistresponse> call, @NonNull Response<Pickuplistresponse> response) {

                pickUpListView.hideProgress();
                if(response.isSuccessful())
                if(response.body() != null){
                        if(response.body().getResponse().equals("success")){
                            pickUpListView.showPickUpList(response.body());
                            pickUpListView.hideProgress();
                        }
                }
                else {
                    pickUpListView.showMessage("Not Successfully");
                    setReportError("api error:"+ new HttpException(response).response().raw().request().url().url().toString()+"\n" + new HttpException(response).getLocalizedMessage());

                }
            }

            @Override
            public void onFailure(@NonNull Call<Pickuplistresponse> call, @NonNull Throwable t) {
                Log.d("test", "onFailure: " + t.getLocalizedMessage());
                //Log.w(TAG, "getPickUpList"+new Gson().toJson(t));
                pickUpListView.hideProgress();
                pickUpListView.showMessage(t.getMessage());
                setReportError("app error" + t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void getPickupWayBillList(String token, int userid) {
        //checkpickupView.showProgress();
        mglApi.getWaybillSearch(token, userid).enqueue(new Callback<Waybilldetailresponse>() {
            @Override
            public void onResponse(@NonNull Call<Waybilldetailresponse> call, @NonNull Response<Waybilldetailresponse> response) {
                Log.w(TAG, "getPickupWayBillList "+new Gson().toJson(response));
                checkpickupView.hideProgress();
                if(response.isSuccessful()) {
                    checkpickupView.showPickupwaybilllist(response.body());
                }
                else{
                    setReportError("api error:"+ new HttpException(response).response().raw().request().url().url().toString()+"\n" + new HttpException(response).getLocalizedMessage());

                }

            }

            @Override
            public void onFailure(@NonNull Call<Waybilldetailresponse> call, @NonNull Throwable t) {
                Log.w(TAG, "getPickupWayBillList "+new Gson().toJson(t));
                checkpickupView.hideProgress();
                checkpickupView.error(t.getLocalizedMessage());
                setReportError("app error" + t.getLocalizedMessage());

            }
        });
    }

    @Override
    public void getPickUpById(String token, String id) {
        mglApi.getPickUpById(token, id).enqueue(new Callback<Pickupdetailresponse[]>() {
            @Override
            public void onResponse(@NonNull Call<Pickupdetailresponse[]> call, @NonNull Response<Pickupdetailresponse[]> response) {
                if(response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.w(TAG, "getPickUpById " + new Gson().toJson(response));
                        if (response.body() != null) {
                            Pickupdetailresponse[] pickupdetailresponses = response.body();
                            pickUpDetailView.showPickUp(pickupdetailresponses[0]);

                        }
                    }
                }
                else {

                    setReportError("api error:"+ new HttpException(response).response().raw().request().url().url().toString()+"\n" + new HttpException(response).getLocalizedMessage());

                }
                pickUpDetailView.hideProgress();
            }

            @Override
            public void onFailure(@NonNull Call<Pickupdetailresponse[]> call, @NonNull Throwable t) {
                //Log.w(TAG, "getPickUpById "+new Gson().toJson(t));
                if(t.getMessage() != null){
                    pickUpDetailView.hideProgress();
                    setReportError("app error" + t.getLocalizedMessage());

                }
            }
        });
    }

    @Override
    public void getPickUpforWaybill(String token, String id) {
        mglApi.getPickUpById(token, id).enqueue(new Callback<Pickupdetailresponse[]>() {
            @Override
            public void onResponse(@NonNull Call<Pickupdetailresponse[]> call, @NonNull Response<Pickupdetailresponse[]> response) {
                if(response.isSuccessful())
                if(response.body() != null){
                    Log.w(TAG, "getPickUpById "+new Gson().toJson(response));
                    if(response.body() != null){
                        Pickupdetailresponse[] pickupdetailresponses = response.body();
                        addWayBillView.showPickupData(pickupdetailresponses[0]);

                    }
                }
                else {
                    addWayBillView.showError("Not Successfully connected..");
                    setReportError("api error:"+ new HttpException(response).response().raw().request().url().url().toString()+"\n" + new HttpException(response).getLocalizedMessage());

                }
                addWayBillView.hideProgress();
            }

            @Override
            public void onFailure(@NonNull Call<Pickupdetailresponse[]> call, @NonNull Throwable t) {
                //Log.w(TAG, "getPickUpById "+new Gson().toJson(t));
                if(t.getMessage() != null){
                    addWayBillView.hideProgress();
                    addWayBillView.showError(t.getLocalizedMessage());
                    setReportError("app error" + t.getLocalizedMessage());

                }
            }
        });
    }

    @Override
    public void getWayBillById(String token, String id) {
        Log.w(TAG, "getWayBillById ");
        mglApi.getWayBillById(token,id).enqueue(new Callback<WaybillResponse>() {
            @Override
            public void onResponse(@NonNull Call<WaybillResponse> call, @NonNull Response<WaybillResponse> response) {
                if(response.isSuccessful()) {
                    Log.w(TAG, "getWayBillById " + new Gson().toJson(response));
                    wayBillDetailView.showWayBill(response.body());

                }else{

                    setReportError("api error:"+ new HttpException(response).response().raw().request().url().url().toString()+"\n" + new HttpException(response).getLocalizedMessage());

                }
                wayBillDetailView.hideProgress();
            }

            @Override
            public void onFailure(@NonNull Call<WaybillResponse> call, @NonNull Throwable t) {
                Log.w(TAG, "getWayBillById "+new Gson().toJson(t));
                wayBillDetailView.hideProgress();
                setReportError("app error" + t.getLocalizedMessage());

            }
        });
    }

    @Override
    public void getWayBillByCode(String token, String code) {
        trackingPointView.showProgress();
        Log.d(TAG, "getWayBillByCode "+token+"\n"+code);
        mglApi.getWayBillByCode(token,code).enqueue(new Callback<Waybilldetailresponse>() {
            @Override
            public void onResponse(@NonNull Call<Waybilldetailresponse> call, @NonNull Response<Waybilldetailresponse> response) {
                if(response.isSuccessful()) {
                    Log.w(TAG, "getWayBillByCode " + new Gson().toJson(response));
                    trackingPointView.showWaybill(response.body());

                }
                else{

                    setReportError("api error:"+ new HttpException(response).response().raw().request().url().url().toString()+"\n" + new HttpException(response).getLocalizedMessage());

                }
                trackingPointView.hideProgress();
            }

            @Override
            public void onFailure(@NonNull Call<Waybilldetailresponse> call, @NonNull Throwable t) {
                Log.w(TAG, "getWayBillByCode "+new Gson().toJson(t));
                trackingPointView.hideProgress();
                setReportError("app error" + t.getLocalizedMessage());

            }
        });
    }

    @Override
    public void getWayBillListByPickUp(String token, String id) {
        mglApi.getWayBillListByPickupId(token,id).enqueue(new Callback<Pickuplistresponse>() {
            @Override
            public void onResponse(@NonNull Call<Pickuplistresponse> call, @NonNull Response<Pickuplistresponse> response) {
                //Log.w(TAG, "getWayBillListByPickUp"+new Gson().toJson(response));
                if(response.isSuccessful()) {
                    wayBillListByPickUp.showWayBillList(response.body());

                }
                else{

                    setReportError("api error:"+ new HttpException(response).response().raw().request().url().url().toString()+"\n" + new HttpException(response).getLocalizedMessage());

                }

                wayBillListByPickUp.hideProgress();
            }

            @Override
            public void onFailure(@NonNull Call<Pickuplistresponse> call, @NonNull Throwable t) {
                //Log.w(TAG, "getWayBillListByPickUp"+new Gson().toJson(t));
                wayBillListByPickUp.hideProgress();
                setReportError("app error" + t.getLocalizedMessage());

            }
        });
    }

    @Override
    public void getLocation(String token) {
        addWayBillView.showProgress();
        mglApi.getLocation(token).enqueue(new Callback<Map<String, String>[]>() {
            @Override
            public void onResponse(@NonNull Call<Map<String, String>[]> call, @NonNull Response<Map<String, String>[]> response) {
                Log.w(TAG, "onResponse : getLocation"+new Gson().toJson(response));

                if(response.isSuccessful())
                if (response.body() != null) {
                    String [] item = new String[response.body().length];
                    for(int i = 0; i < response.body().length; i++){
                        item[i] = response.body()[i].get("code")+" - "+response.body()[i].get("name");
                    }
                    AppData.LOCATIONS = item;
                    addWayBillView.showLocation(item);
                }
                else {

                    setReportError("api error:"+ new HttpException(response).response().raw().request().url().url().toString()+"\n" + new HttpException(response).getLocalizedMessage());

                    addWayBillView.LoadError(new HttpException(response).getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Map<String, String>[]> call, @NonNull Throwable t) {
                Log.w(TAG, "onFailure : getLocation"+new Gson().toJson(t));
                addWayBillView.hideProgress();
                addWayBillView.LoadError(t.getLocalizedMessage() + "server error");
                setReportError("app error" + t.getLocalizedMessage());

            }
        });
    }

    @Override
    public void getPickupNotiCount(String token) {
        Log.d("pickuptoken", token);
        mglApi.getPickupNotiCount(token).enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(@NonNull Call<Map<String, String>> call, @NonNull Response<Map<String, String>> response) {
                if(response.isSuccessful()) {
                    Log.w(TAG, "getPickupNotiCount" + new Gson().toJson(response));
                    if (response.body() != null) {
                        AppData.storeNotiCount(context, response.body().get("pickup_count"));

                        //AppData.noticount = response.body().get("pickup_count") + response.body().get("waybill_count");
                        if (!response.body().get("pickup_count").equals("0")) {
                            Log.d("test", "onResponse: Notification");
                            NotificationScheduler.showNotification(context, MainActivity.class,
                                    response.body().get("pickup_count") + " new pickup", "Watch them now?");
                        } else {
                            Log.d("test", "onResponse: Notification");

                        }
                        if (mainView != null) {
                            mainView.setNavItemCount(response.body().get("pickup_count"));
                        }
                    }
                }
                else{

                    setReportError("api error:"+ new HttpException(response).response().raw().request().url().url().toString()+"\n" + new HttpException(response).getLocalizedMessage());

                }
            }

            @Override
            public void onFailure(@NonNull Call<Map<String, String>> call, @NonNull Throwable t) {
                Log.w(TAG, "getPickupNotiCount"+new Gson().toJson(t));
                Log.d("test","Noti Count Error" );
                setReportError("app error" + t.getLocalizedMessage());

            }
        });
    }

    @Override
    public void getRemoteArea(String token, String id) {
        mglApi.getRemoteArea(token, id).enqueue(new Callback<Map<String, String>[]>() {
            @Override
            public void onResponse(@NonNull Call<Map<String, String>[]> call, @NonNull Response<Map<String, String>[]> response) {
                if(response.isSuccessful()) {
                    Log.d(TAG, "getRemoteArea" + new Gson().toJson(response));
                    addWayBillView.showRemoteLocation(response.body());
                }
                else{

                    setReportError("api error:"+ new HttpException(response).response().raw().request().url().url().toString()+"\n" + new HttpException(response).getLocalizedMessage());

                }
                addWayBillView.hideProgress();

            }

            @Override
            public void onFailure(@NonNull Call<Map<String, String>[]> call, @NonNull Throwable t) {
                //Log.w(TAG, ""+new Gson().toJson(t));
                addWayBillView.hideProgress();
                setReportError("app error" + t.getLocalizedMessage());

            }
        });
    }

    @Override
    public void acceptPickUp(final String token, String id) {
        pickUpListView.showProgress();
        mglApi.accept_pickup(
                token,id).enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(@NonNull Call<Map<String, String>> call, @NonNull Response<Map<String, String>> response) {
                //Log.w(TAG, "acceptPickUp"+new Gson().toJson(response));
                pickUpListView.hideProgress();
                if(response.isSuccessful()) {
                    getPickupNotiCount(token);
                    pickUpListView.showMessage(response.body() != null ? response.body().get("message") + "here" : "null");
                }
                else{

                    setReportError("api error:"+ new HttpException(response).response().raw().request().url().url().toString()+"\n" + new HttpException(response).getLocalizedMessage());

                }
            }

            @Override
            public void onFailure(@NonNull Call<Map<String, String>> call, @NonNull Throwable t) {
                //Log.w(TAG, "acceptPickUp"+new Gson().toJson(t));
                pickUpListView.hideProgress();
                setReportError("app error" + t.getLocalizedMessage());

            }
        });
    }

    @Override
    public void devilery(String token, String receiver, String id) {
        trackingPointView.showProgress();
        mglApi.delivery(token, receiver, id).enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(@NonNull Call<Map<String, String>> call, @NonNull Response<Map<String, String>> response) {
                trackingPointView.hideProgress();
                if(response.isSuccessful()){
                Log.w(TAG, "devilery "+new Gson().toJson(response));
                if(response.body() != null) {
                    if (response.body().get("status").equals("true")) {
                        trackingPointView.showMessage("Successfully delivery!");
                    } else {
                        trackingPointView.showMessage("Something Worng!");
                    }
                }
                else{
                    setReportError("api error:"+ new HttpException(response).response().raw().request().url().url().toString()+"\n" + new HttpException(response).getLocalizedMessage());

                }

                }
            }

            @Override
            public void onFailure(@NonNull Call<Map<String, String>> call,@NonNull Throwable t) {
                Log.w(TAG, "devilery "+new Gson().toJson(t));
                trackingPointView.showMessage("Something wrong!");
                trackingPointView.hideProgress();
                setReportError("app error" + t.getLocalizedMessage());

            }
        });
    }

    @Override
    public void acceptWayBill(String token, String id) {
        pickUpListView.showProgress();
        mglApi.accept_waybill(token, id).enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(@NonNull Call<Map<String, String>> call, @NonNull Response<Map<String, String>> response) {
                //Log.w(TAG, "acceptWaybill"+new Gson().toJson(response));

                pickUpListView.hideProgress();
                if(response.isSuccessful()){
                pickUpListView.showMessage(response.body() != null ? response.body().get("message") : null);
                }
                else{

                    setReportError("api error:"+ new HttpException(response).response().raw().request().url().url().toString()+"\n" + new HttpException(response).getLocalizedMessage());

                }
            }

            @Override
            public void onFailure(@NonNull Call<Map<String, String>> call, @NonNull Throwable t) {
                pickUpListView.showMessage("Something wrong!");
                pickUpListView.hideProgress();
                setReportError("app error" + t.getLocalizedMessage());

            }
        });
    }

    @Override
    public void getRemoteCharges(String token, int remote_to, int remote_from) {
        Log.i(TAG, remote_from + " & " + remote_to);
        mglApi.getRemoteCharges(token, remote_to, remote_from).enqueue(new Callback<Map<String,String>>() {
            @Override

            public void onResponse(@NonNull Call<Map<String,String>> call, @NonNull Response<Map<String,String>> response) {
                if(response.isSuccessful()) {
                    Log.w(TAG, "getRemoteCharges " + new Gson().toJson(response));
                    addWayBillView.showRemoteCharges(response.body() != null ? response.body().get("charges") : null);
                }
                else{

                    setReportError("api error:"+ new HttpException(response).response().raw().request().url().url().toString()+"\n" + new HttpException(response).getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Map<String,String>> call, @NonNull Throwable t) {
                Log.w(TAG, "getRemoteCharges "+new Gson().toJson(t));
                setReportError("app error" + t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void getCustomerType(String token) {
        addWayBillView.showProgress();
        mglApi.getCustomerType(token).enqueue(new Callback<Map<String, String>[]>() {
            @Override
            public void onResponse(Call<Map<String, String>[]> call, Response<Map<String, String>[]> response) {
                Log.w(TAG, "getCustomerType "+new Gson().toJson(response));
                if (response.body() != null && response.isSuccessful()) {
                    addWayBillView.showCustomerType(response.body());
                    addWayBillView.hideProgress();
                }

               else {
                    addWayBillView.LoadError(new HttpException(response).message());
                    setReportError("api error:"+ new HttpException(response).response().raw().request().url().url().toString()+"\n" + new HttpException(response).getLocalizedMessage());

                }
            }

            @Override
            public void onFailure(Call<Map<String, String>[]> call, Throwable t) {
                Log.w(TAG, "v "+new Gson().toJson(t));
                addWayBillView.hideProgress();
                addWayBillView.LoadError(t.getLocalizedMessage());
               setReportError("app error" + t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void getPaymentType(String token) {
        addWayBillView.showProgress();
        mglApi.getPaymentType(token).enqueue(new Callback<Map<String, String>[]>() {
            @Override
            public void onResponse(Call<Map<String, String>[]> call, Response<Map<String, String>[]> response) {
                Log.w(TAG, "getPaymentType "+new Gson().toJson(response));
                if (response.body() != null && response.isSuccessful()) {
                    addWayBillView.showPaymentType(response.body());
                    addWayBillView.hideProgress();
                }

                else {
                    addWayBillView.LoadError(new HttpException(response).message());
                    setReportError("api error:"+ new HttpException(response).response().raw().request().url().url().toString()+"\n" + new HttpException(response).getLocalizedMessage());

                }

            }

            @Override
            public void onFailure(Call<Map<String, String>[]> call, Throwable t) {
                Log.w(TAG, "getPaymentType "+new Gson().toJson(t));
                addWayBillView.hideProgress();
                addWayBillView.LoadError(t.getLocalizedMessage());
                setReportError("app error" + t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void getCheckPoints(String token) {
        mglApi.getCheckPoints(token).enqueue(new Callback<Map<String, String>[]>() {
            @Override
            public void onResponse(Call<Map<String, String>[]> call, Response<Map<String, String>[]> response) {
                Log.w(TAG, "getCheckPoints "+new Gson().toJson(response));
                trackingPointView.hideProgress();
                if (response.body() != null) {
                   trackingPointView.showTrackingPoint(response.body());
                }
            }

            @Override
            public void onFailure(Call<Map<String, String>[]> call, Throwable t) {
                Log.d("test", "getCheckPoints "+new Gson().toJson(t));
                trackingPointView.hideProgress();
                trackingPointView.showMessage(t.getMessage());

                setReportError("app error" + t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void changeCheckPoint(String token, String id, String checkpoint_id, String remark) {
        trackingPointView.showProgress();
        mglApi.waybilltracking(token, id, checkpoint_id, remark).enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                Log.w(TAG, "getCheckPoints "+new Gson().toJson(response));
                if(response.body().get("status").equals("success")){
                    trackingPointView.showMessage("Successfully add check point.");
                }else{
                    trackingPointView.showMessage("Something Worng!");
                    setReportError("api error:"+ new HttpException(response).response().raw().request().url().url().toString()+"\n" + new HttpException(response).getLocalizedMessage());
                }

                trackingPointView.hideProgress();
            }

            @Override
            public void onFailure(Call<Map<String, String>> call, Throwable throwable) {
                Log.w(TAG, "getCheckPoints "+new Gson().toJson(throwable));
                trackingPointView.hideProgress();

                setReportError("app error" + throwable.getLocalizedMessage());

            }
        });
    }


    @Override
    public void deliveryroutefix(String token, ArrayList<String> waybillcode) {
        deliveryWBView.showProgress();
        mglApi.deliveryroutefix(token, waybillcode).enqueue(new Callback<Delivery>() {
            @Override
            public void onResponse(Call<Delivery> call, Response<Delivery> response) {
                if(response.isSuccessful()) {
                    Log.w("test", "deliveryroutefix:onResponse " + new Gson().toJson(response));
                    deliveryWBView.showDelivery(response.body());
                    deliveryWBView.hideProgress();
                }
                else{
                    deliveryWBView.hideProgress();
                    deliveryWBView.showMessage(new HttpException(response).message());

                    setReportError("api error:"+ new HttpException(response).response().raw().request().url().url().toString()+"\n" + new HttpException(response).getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(Call<Delivery> call, Throwable t) {
                Log.w("test", "deliveryroutefix:onFailure "+new Gson().toJson(t));
                deliveryWBView.showMessage(t.getLocalizedMessage());
                deliveryWBView.hideProgress();
                setReportError("app error" + t.getLocalizedMessage());

            }
        });
    }

    @Override
    public void getRateCard(String token, String loc_from, String loc_to, String gw, String actual_weight, String cus_type, String payment_type, String cur_id,String shipper_code, String ratecard_id) {
        Log.i(TAG, "rate " + ratecard_id + "gw " + gw+"from "+loc_from+"to "+loc_to+"cus_type "+cus_type+"cur_id "+cur_id+"shipper "+shipper_code);
        mglApi.getRateCard(token, loc_from, loc_to, gw, actual_weight, cus_type, payment_type, cur_id, shipper_code,ratecard_id).enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(@NonNull Call<Map<String, String>> call, @NonNull Response<Map<String, String>> response) {
                if(response.isSuccessful()) {
                    Log.w(TAG, "getRateCard " + new Gson().toJson(response));
                    Log.d("testtest", response.message());
                    addWayBillView.showRate(response.body());
                }

                else
                setReportError("api error:"+ new HttpException(response).response().raw().request().url().url().toString()+"\n" + new HttpException(response).getLocalizedMessage());
                addWayBillView.hideProgress();
            }

            @Override
            public void onFailure(@NonNull Call<Map<String, String>> call, @NonNull Throwable t) {
                Log.w(TAG, "addWayBill"+new Gson().toJson(t));
                addWayBillView.hideProgress();
                Log.d("testtest","failure");
                setReportError("app error" + t.getLocalizedMessage());

            }
        });
    }

    @Override
    public void addWayBill(String token, String location_from, String location_to, int remote_from, int remote_to, String waybill_code,
                           int customer_type, int doc_type, String description, double declare_value, String shipper_code,
                           String shipper, String shipper_company_name, String shipper_contact_name, String shipper_addr,
                           String shipper_phone, String shipper_email, String consignee_code, String consignee_company_name,
                           String consignee_contact_name, String consignee_addr, String consignee_phone, String consignee_email,
                           String remark, int payment_type, int duty_tax, int service_type, double gross_weight, double length,
                           double width, double height, double actual_weight, int pieces, int cur_id, double cur_rate, double item_amt,
                           double service_amt, double charges, double commercial_tax, double dis_percent, double dis_amt, double area_charges,
                           double online_charges, double additional_amt, double received_amt, int received_amt_cur_id, double total_amt, String pickup_id, String service_alert, String img, String mode, int id, String ratecard_id,
                           String pickupTime,double two_percent_discount) {


        mglApi.addWayBill(token,location_from, location_to, remote_from, remote_to, waybill_code, customer_type, doc_type, description,
                        declare_value, shipper_code, shipper, shipper_company_name, shipper_contact_name, shipper_addr,
                        shipper_phone, shipper_email, consignee_code, consignee_company_name, consignee_contact_name, consignee_addr,
                        consignee_phone, consignee_email, remark, payment_type, duty_tax, service_type, gross_weight, length, width,
                        height, actual_weight, pieces, cur_id, cur_rate, item_amt, service_amt, charges, commercial_tax,
                        dis_percent, dis_amt, area_charges, online_charges, additional_amt, received_amt, received_amt_cur_id, total_amt, pickup_id, service_alert, img, mode, id, ratecard_id,
               pickupTime, two_percent_discount).enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(@NonNull Call<Map<String, String>> call, @NonNull Response<Map<String, String>> response) {
                Log.w(TAG, "addWayBill"+new Gson().toJson(response));
                if(response.isSuccessful()){
                if(response.body() != null) {
                    if (response.body().get("response").equals("success")) {
                        addWayBillView.showMessage(response.body().get("message"));
                    } else {
                        addWayBillView.showError(response.body().get("message"));
                    }
                }
                }
                else{
                    addWayBillView.showError(new HttpException(response).message());

                    setReportError("api error:"+ new HttpException(response).response().raw().request().url().url().toString()+"\n" + new HttpException(response).getLocalizedMessage());
                }

                addWayBillView.hideProgress();
            }

            @Override
            public void onFailure(@NonNull Call<Map<String, String>> call, @NonNull Throwable t) {
                Log.w(TAG, "addWayBill"+new Gson().toJson(t));
                addWayBillView.showError(t.getMessage());
                addWayBillView.hideProgress();
                setReportError("app error" + t.getLocalizedMessage());

            }
        });
    }

    @Override
    public void addPickup(String token, String location_from, String location_to, int remote_from, int remote_to, String dom_int, int doc_type, String shipper_code, String shipper, String shipper_company_name, String shipper_contact_name, String shipper_addr, String shipper_phone, String ready_time, String ready_date, String pickup_by, String remark, int payment_type) {
         mglApi.addPickup(token,location_from,location_to,remote_from,remote_to,dom_int,doc_type,shipper_code,shipper,shipper_company_name,
                 shipper_contact_name,shipper_addr,shipper_phone,ready_time,ready_date,pickup_by,remark,payment_type).enqueue(new Callback<Map<String, String>>() {
             @Override
             public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                 if(response.isSuccessful()){
                     if(response.body() != null){
                         if(response.body().get("response").equals("success")){
                             addWayBillView.showMessage(response.body().get("message"));
                         }
                         else{
                             addWayBillView.showError(response.body().get("message"));
                         }
                     }
                     else{
                         addWayBillView.showError(new HttpException(response).message());

                         setReportError("api error:"+ new HttpException(response).response().raw().request().url().url().toString()+"\n" + new HttpException(response).getLocalizedMessage());
                     }
                 }
             }

             @Override
             public void onFailure(Call<Map<String, String>> call, Throwable t) {
                 addWayBillView.showError(t.getMessage());

                 setReportError("app error" + t.getLocalizedMessage());
                 addWayBillView.hideProgress();
             }
         });
    }

    @Override
    public void getShipperInfo(String q) {
        addWayBillView.showPro();
        if(mglApi.getShipperInfo(q).isExecuted()){
            Log.d("test",q);
        }
        mglApi.getShipperInfo(q).enqueue(new Callback<List<ShipperModel>>() {
            @Override
            public void onResponse(Call<List<ShipperModel>> call, Response<List<ShipperModel>> response) {
                if(response.isSuccessful()){

                  addWayBillView.showShipperData(response.body());
                }
                else{

                    addWayBillView.KeywordError("correctly search");
                    setReportError("api error:"+ new HttpException(response).response().raw().request().url().url().toString()+"\n" + new HttpException(response).getLocalizedMessage());

                }
            }

            @Override
            public void onFailure(Call<List<ShipperModel>> call, Throwable t) {

                    addWayBillView.KeywordError(t.getLocalizedMessage());
                    setReportError("ap error" + t.getLocalizedMessage());

            }
        });
    }
    private void setReportError(String error) {
        Log.d("apierror",error);
        String api =   android.os.Build.VERSION.SDK   ;   // API Level
        String device =  android.os.Build.DEVICE   ;        // Device
        String model =  android.os.Build.MODEL    ;        // Model
        String porduct =  android.os.Build.PRODUCT;
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        ErrorModel errorModel = new ErrorModel(error, api+"," +device+"," + model + "," +porduct,mDay +"/" + mMonth + "/" + mYear, hour + " : " + minute);
        db.collection("error").document(System.currentTimeMillis()+"")
                .set(errorModel)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });


    }

}
