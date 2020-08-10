package com.sh.courier_mvp.presenter;

import androidx.fragment.app.Fragment;

import com.sh.courier_mvp.model.Delivery;
import com.sh.courier_mvp.model.Pickupdetailresponse;
import com.sh.courier_mvp.model.Pickuplistresponse;
import com.sh.courier_mvp.model.ShipperModel;
import com.sh.courier_mvp.model.WaybillResponse;
import com.sh.courier_mvp.model.Waybilldetailresponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface MainContract {
    interface MainView{
        void changeFragment(Fragment fm);

        void setNavItemCount(String count);


    }
    interface PickUpListView{
        void showProgress();

        void hideProgress();

        void showPickUpList(Pickuplistresponse data);

        void showMessage(String message);
    }

    interface DeliveryWBView{
        void showProgress();

        void hideProgress();

        void showDelivery(Delivery data);

        void showMessage(String message);
    }


    interface PickUpDetailView{
        void showProgress();

        void hideProgress();

        void showPickUp(Pickupdetailresponse pickupdetailresponse);
    }

    interface WayBillDetailView{
        void showProgress();

        void hideProgress();
        void showMessage(String message);
        void showWayBill(WaybillResponse waybilldetailresponse);
    }

    interface LoginView{
        void gotoHomePage();

        void phonenoEmpty();

        void passwordEmpty();

        void showMessage(String message);

        void showProgress();

        void hideProgress();
    }

    interface CheckpickupView{
        void showProgress();

        void hideProgress();

        void error(String error);

        void showPickupwaybilllist(Waybilldetailresponse waybilldetailresponse);
    }

    interface AddWayBillView{
        void showProgress();
        void LoadError(String error);
        void hideProgress();
        void bindSpinner();
        void showLocation(String[] list);
        void showCustomerType(Map<String, String>[] list);
        void showPaymentType(Map<String, String>[] list);
        void showRemoteLocation(Map<String, String>[] list);
        void showRate(Map<String,String> response);
        void showRemoteCharges(String charges);
        void showPickupData(Pickupdetailresponse pickupdetailresponse);
        void showMessage(String message);
        void showError(String message);


        void showPro();
        void hideProg();
        void showShipperData(List<ShipperModel> shipperData);
        void KeywordError(String error);


    }

    interface TrackingPointView{
        void showTrackingPoint(Map<String, String>[] list);
        void showProgress();

        void hideProgress();
        void showMessage(String message);
        void showWaybill(Waybilldetailresponse waybilldetailresponse);
    }

    interface WayBillListByPickUp{
        void showProgress();

        void hideProgress();

        void showWayBillList(Pickuplistresponse data);
    }

    interface MainPresenter{

        void getShipperInfo(String q);
        void login(String name, String password);

        void checkData(String name, String password);

        void getPickUpList(String token);

        void getPickupWayBillList(String token, int userid);

        void getPickUpById(String token, String id);

        void getPickUpforWaybill(String token, String id);

        void getWayBillById(String token, String id);

        void getWayBillByCode(String token, String code);

        void getWayBillListByPickUp(String token, String id);

        void getLocation(String token);

        void getPickupNotiCount(String token);

        void getRemoteArea(String token, String id);

        void acceptPickUp(String token, String id);

        void devilery(String token,String user_id, String receiver, String id);

        void acceptWayBill(String token, String id);

        void getRemoteCharges(String token, int remote_to, int remote_from);

        void getCustomerType(String token);

        void getPaymentType(String token);

        void getCheckPoints(String token);

        void changeCheckPoint(String token, String user_id,String id, String checkpoint_id, String remark);

        void deliveryroutefix(String token,String user_id ,ArrayList<String> waybillcode);

        void getRateCard(String token, String loc_from, String loc_to, String gw, String actual_weight, String cus_type, String payment_type, String cur_id, String shipper_code, String ratecard_id);

        void addWayBill(String token,
                        String location_from,
                        String location_to,
                        int remote_from,
                        int remote_to,
                        String waybill_code,
                        int customer_type,
                        int doc_type,
                        String description,
                        double declare_value,
                        String shipper_code,
                        String shipper,
                        String shipper_company_name,
                        String shipper_contact_name,
                        String shipper_addr,
                        String shipper_phone,
                        String  shipper_email,
                        String  consignee_code,
                        String  consignee_company_name,
                        String  consignee_contact_name,
                        String  consignee_addr,
                        String  consignee_phone,
                        String  consignee_email,
                        String  remark,
                        int  payment_type,
                        int  duty_tax,
                        int service_type,
                        double  gross_weight,
                        double length,
                        double width,
                        double height,
                        double actual_weight,
                        int pieces,
                        int cur_id,
                        double cur_rate,
                        double item_amt,
                        double service_amt,
                        double charges,
                        double commercial_tax,
                        double dis_percent,
                        double dis_amt,
                        double area_charges,
                        double online_charges, double additional_amt, double received_amt, int received_amt_cur_id,
                        double total_amt,
                        String pickup_id,
                        String service_alert,
                        String img,
                        String mode,
                        int id,
                        String ratecard_id,
                        String pickup_time,
                        double two_percent_discount);

        void addPickup(
                String token,
                String location_from,
                String location_to,
                int remote_from,
                int remote_to,
                String dom_int,
                int doc_type,
                String shipper_code,
                String shipper,
                String shipper_company_name,
                String shipper_contact_name,
                String shipper_addr,
                String shipper_phone,
                String ready_time,
                String ready_date,
                String pickup_by,
                String  remark,
                int  payment_type


        );
    }




}
