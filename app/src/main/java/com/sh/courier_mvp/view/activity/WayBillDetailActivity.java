package com.sh.courier_mvp.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sh.courier_mvp.R;
import com.sh.courier_mvp.data.AppData;
import com.sh.courier_mvp.model.WaybillResponse;
import com.sh.courier_mvp.presenter.MainContract;
import com.sh.courier_mvp.presenter.Presenter;
import com.shashank.sony.fancydialoglib.Animation;
import com.shashank.sony.fancydialoglib.FancyAlertDialog;
import com.shashank.sony.fancydialoglib.FancyAlertDialogListener;
import com.shashank.sony.fancydialoglib.Icon;

import app.dinus.com.loadingdrawable.LoadingView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class WayBillDetailActivity extends AppCompatActivity implements MainContract.WayBillDetailView{

    @BindView(R.id.txtWayBillSource)
    TextView txtWayBillSource;
    @BindView(R.id.txtWayBillDestination)
    TextView txtWayBillDestination;
    @BindView(R.id.txtRemoteSource)
    TextView txtRemoteSource;
    @BindView(R.id.txtRemoteDestination)
    TextView txtRemoteDestination;

    @BindView(R.id.txtCode)
    TextView txtCode;
    @BindView(R.id.txtDocType)
    TextView txtDocType;
    @BindView(R.id.txtPaymentType)
    TextView txtPaymentType;
    @BindView(R.id.txtCustomerTye)
    TextView txtCustomerTye;
    @BindView(R.id.txtRemark)
    TextView txtRemark;
    @BindView(R.id.txtShipperCode)
    TextView txtShipperCode;
    @BindView(R.id.txtShipperName)
    TextView txtShipperName;
    @BindView(R.id.txtCompanyName)
    TextView txtCompanyName;
    @BindView(R.id.txtContactName)
    TextView txtContactName;
    @BindView(R.id.txtAddress)
    TextView txtAddress;
    @BindView(R.id.txtPhone)
    TextView txtPhone;

    @BindView(R.id.txtConsigneeCode)
    TextView txtConsigneeCode;
    @BindView(R.id.txtConsigneeName)
    TextView txtConsigneeName;
    @BindView(R.id.txtConsigneeCompanyName)
    TextView txtConsigneeCompanyName;
    @BindView(R.id.txtConsigneeContactName)
    TextView txtConsigneeContactName;
    @BindView(R.id.txtConsigneeAddress)
    TextView txtConsigneeAddress;
    @BindView(R.id.txtConsigneePhone)
    TextView txtConsigneePhone;

    @BindView(R.id.txtLength)
    TextView txtLength;
    @BindView(R.id.txtHeight)
    TextView txtHeight;
    @BindView(R.id.txtWidth)
    TextView txtWidth;
    @BindView(R.id.txtGrossWeight)
    TextView txtGrossWeight;
    @BindView(R.id.txtPieces)
    TextView txtPieces;
    /*@BindView(R.id.txtDeclareValue)
    TextView txtDeclareValue;*/

    @BindView(R.id.txtCharges)
    TextView txtCharges;
    @BindView(R.id.txtRemoteCharges)
    TextView txtRemoteCharges;
    @BindView(R.id.txtDiscountAmount)
    TextView txtDiscountAmount;
    @BindView(R.id.txtCommericalTax)
    TextView txtCommericalTax;
    @BindView(R.id.txtTotal)
    TextView txtTotal;
    @BindView(R.id.loading)
    LoadingView loadingView;
    @BindView(R.id.linearWaybillDetail)
    LinearLayout linearWaybillDetail;

    String id; //TAG = "WayBillDetailActivity";
    WaybillResponse wayBillDetail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waybill_detail);
        ButterKnife.bind(this);

        String TAG = "Detail";
        MainContract.MainPresenter mainPresenter = new Presenter(this, this);
        Log.i(TAG, getIntent().getExtras().getString("id"));
        if(getIntent().hasExtra("id")){
            id = getIntent().getExtras().getString("id");
            String type = getIntent().getStringExtra("type");
            showProgress();
            mainPresenter.getWayBillById(AppData.GetToken(getApplicationContext()), id);
        }
    }

    @Override
    public void showProgress() {
        loadingView.setVisibility(View.VISIBLE);
        linearWaybillDetail.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        loadingView.setVisibility(View.GONE);
        linearWaybillDetail.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMessage(String message) {
        String des;
        if(message.equals("true")){
            des = "Delivery Sucessfully!";
        }else {
            des = "Unsucessfully!";
        }
        new FancyAlertDialog.Builder(this)
                .setTitle("Show status")
                .setBackgroundColor(Color.parseColor("#63341a"))  //Don't pass R.color.colorvalue
                .setMessage(des)
                .setPositiveBtnBackground(Color.parseColor("#e12425"))  //Don't pass R.color.colorvalue
                .setPositiveBtnText("OK")
                .setNegativeBtnText("Cancel")
                .setAnimation(Animation.POP)
                .isCancellable(true)
                .setIcon(R.drawable.ic_check_black_24dp, Icon.Visible)
                .OnPositiveClicked(new FancyAlertDialogListener() {
                    @Override
                    public void OnClick() {

                    }
                })
                .OnNegativeClicked(new FancyAlertDialogListener() {
                    @Override
                    public void OnClick() {
                    }
                })
                .build();
    }

    @Override
    public void showWayBill(WaybillResponse wayBillDetail) {
        if(wayBillDetail == null){
            Toast.makeText(this, "No Data", Toast.LENGTH_LONG).show();
        }else{
            this.wayBillDetail = wayBillDetail;
            //Log.i("WaybillDetail", wayBillDetail.getDelivered()+"");
            txtWayBillSource.setText(wayBillDetail.getWaybill().getLocationFrom());
            txtWayBillDestination.setText(wayBillDetail.getWaybill().getLocationTo());
            txtRemoteSource.setText(wayBillDetail.getWaybill().getRemoteFrom());
            txtRemoteDestination.setText(wayBillDetail.getWaybill().getRemoteTo());
            txtCode.setText(wayBillDetail.getWaybill().getCode());
            txtDocType.setText(wayBillDetail.getWaybill().getDocType());
            txtPaymentType.setText(wayBillDetail.getWaybill().getPaymentType());
            txtCustomerTye.setText(wayBillDetail.getWaybill().getCustomerTypeId());
            txtShipperCode.setText(wayBillDetail.getWaybill().getShipperCode());
            txtRemark.setText(wayBillDetail.getWaybill().getRemark());
            txtCompanyName.setText(wayBillDetail.getWaybill().getShipperCompanyName());
            txtShipperName.setText(wayBillDetail.getWaybill().getShipper());
            txtAddress.setText(wayBillDetail.getWaybill().getShipperAddr());
            txtPhone.setText(wayBillDetail.getWaybill().getShipperPhone());
            txtContactName.setText(wayBillDetail.getWaybill().getShipperContactName());

            txtConsigneeCode.setText(wayBillDetail.getWaybill().getConsigneeCode());
            txtConsigneeName.setText(wayBillDetail.getWaybill().getConsignee());
            txtConsigneeCompanyName.setText(wayBillDetail.getWaybill().getConsigneeCompanyName());
            txtConsigneeContactName.setText(wayBillDetail.getWaybill().getConsigneeContactName());
            txtConsigneeAddress.setText(wayBillDetail.getWaybill().getConsigneeAddr());
            txtConsigneePhone.setText(wayBillDetail.getWaybill().getConsigneePhone());

            txtLength.setText(wayBillDetail.getWaybill().getLength());
            txtHeight.setText(wayBillDetail.getWaybill().getHeight());
            txtWidth.setText(wayBillDetail.getWaybill().getWidth());
            txtGrossWeight.setText(wayBillDetail.getWaybill().getGrossWeight());
            txtPieces.setText(wayBillDetail.getWaybill().getPieces());
            //txtDeclareValue.setText(wayBillDetail.getWaybill().getDeclareValue());

            txtCharges.setText(wayBillDetail.getWaybill().getCharges());
            txtRemoteCharges.setText(wayBillDetail.getWaybill().getAreaCharges());
            txtDiscountAmount.setText(wayBillDetail.getWaybill().getDisAmt());
            txtCommericalTax.setText(wayBillDetail.getWaybill().getCommercialTax());
            txtTotal.setText(wayBillDetail.getWaybill().getTotalAmt());
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.waybill_menu, menu);
        return true;
    }

}
