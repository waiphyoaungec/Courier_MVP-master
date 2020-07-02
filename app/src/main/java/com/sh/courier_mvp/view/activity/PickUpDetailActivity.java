package com.sh.courier_mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.textfield.TextInputEditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.sh.courier_mvp.R;
import com.sh.courier_mvp.data.AppData;
import com.sh.courier_mvp.model.Pickupdetailresponse;
import com.sh.courier_mvp.presenter.MainContract;
import com.sh.courier_mvp.presenter.Presenter;

import app.dinus.com.loadingdrawable.LoadingView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PickUpDetailActivity extends AppCompatActivity implements MainContract.PickUpDetailView{
    @BindView(R.id.fabAddWayBill)
    FloatingActionButton fab;
    @BindView(R.id.fabWayBillList)
    FloatingActionButton fabWayBillList;
    @BindView(R.id.txtCode)
    TextView txtCode;
    @BindView(R.id.txtDocType)
    TextView txtDocType;
    @BindView(R.id.txtPaymentType)
    TextView txtPaymentType;
    @BindView(R.id.txtCustomerTye)
    TextView txtCustomerTye;
    @BindView(R.id.txtRealDate)
    TextView txtRealDate;
    @BindView(R.id.txtRealtime)
    TextView txtRealtime;
    @BindView(R.id.txtRemark)
    TextView txtRemark;
    @BindView(R.id.edtShipperName)
    TextInputEditText edtShipperName;
    @BindView(R.id.edtCompanyName)
    TextInputEditText edtCompanyName;
    @BindView(R.id.edtContactName)
    TextInputEditText edtContactName;
    @BindView(R.id.edtAddress)
    TextInputEditText edtAddress;
    @BindView(R.id.edtPhone)
    TextInputEditText edtPhone;
    @BindView(R.id.edtShipperCode)
    TextInputEditText edtShipperCode;
    @BindView(R.id.txtDetailSource)
    TextView txtSource;
    @BindView(R.id.txtDetailDestionation)
    TextView txtDestination;
    @BindView(R.id.loading)
    LoadingView loadingView;
    @BindView(R.id.linearPickupDetial)
    LinearLayout linearPickupDetial;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    String id; //TAG = "PickUpDetailActivity";
    String shipperCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pickup_detail);
        ButterKnife.bind(this);

        MainContract.MainPresenter mainPresenter = new Presenter(this, this);
        if(getIntent().hasExtra("id")){
            id = getIntent().getExtras().getString("id");
            //Log.i("Pickup", "token" +AppData.GetToken(getApplicationContext()));
            //Log.i("Pickup", "id "+ id);
            showProgress();
            mainPresenter.getPickUpById(AppData.GetToken(getApplicationContext()), id);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PickUpDetailActivity.this, AddWayBillActivity.class);
                intent.putExtra("PICKUP_ID", id);
                /*intent.putExtra("SHIPPER_CODE", shipperCode);
                intent.putExtra("SHIPPER", edtShipperName.getText().toString());
                intent.putExtra("SHIPPER_COMPANY_NAME", edtCompanyName.getText().toString());
                intent.putExtra("SHIPPER_CONTACT_NAME", edtContactName.getText().toString());
                intent.putExtra("SHIPPER_ADDR", edtAddress.getText().toString());
                intent.putExtra("SHIPPER_PHONE", edtPhone.getText().toString());*/
                intent.putExtra("MODE", "NEW");
                startActivity(intent);
            }
        });

        fabWayBillList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PickUpDetailActivity.this, WayBillListByPickUpActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
        toolbar.setTitle("PickUpDetail");
        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_white_18dp);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void showProgress() {
        loadingView.setVisibility(View.VISIBLE);
        linearPickupDetial.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        loadingView.setVisibility(View.GONE);
        linearPickupDetial.setVisibility(View.VISIBLE);
    }

    @Override
    public void showPickUp(Pickupdetailresponse pickupdetailresponse) {
        shipperCode = pickupdetailresponse.getShipperCode();

        txtCode.setText(pickupdetailresponse.getCode());
        txtDocType.setText(pickupdetailresponse.getDocType());
        txtPaymentType.setText(pickupdetailresponse.getPaymentType());
        txtCustomerTye.setText(pickupdetailresponse.getCustomerType());
        txtSource.setText(pickupdetailresponse.getLocationFrom());
        txtDestination.setText(pickupdetailresponse.getLocationTo());
        txtRealDate.setText(pickupdetailresponse.getReadyDate());
        txtRealtime.setText(pickupdetailresponse.getReadyTime());
        txtRemark.setText(pickupdetailresponse.getRemark());
        edtShipperCode.setText(pickupdetailresponse.getShipperCode());
        edtCompanyName.setText(pickupdetailresponse.getShipperCompanyName());
        edtShipperName.setText(pickupdetailresponse.getShipper());
        edtAddress.setText(pickupdetailresponse.getShipperAddr());
        edtPhone.setText(pickupdetailresponse.getShipperPhone());
        edtContactName.setText(pickupdetailresponse.getShipperContactName());
    }
}
