package com.sh.courier_mvp.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.sh.courier_mvp.R;
import com.sh.courier_mvp.util.Utility;
import com.sh.courier_mvp.data.AppData;
import com.sh.courier_mvp.model.Waybilldetailresponse;
import com.sh.courier_mvp.presenter.MainContract;
import com.sh.courier_mvp.presenter.Presenter;
import com.shashank.sony.fancydialoglib.Animation;
import com.shashank.sony.fancydialoglib.FancyAlertDialog;
import com.shashank.sony.fancydialoglib.FancyAlertDialogListener;
import com.shashank.sony.fancydialoglib.Icon;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrackingPointFragment extends Fragment implements MainContract.TrackingPointView,AdapterView.OnItemSelectedListener {
    @BindView(R.id.edtWayBillCode3)
    EditText edtWayBillCode3;
    @BindView(R.id.imgbtnScan3)
    ImageButton imgbtnScan3;
    @BindView(R.id.btnDelivery)
    Button btnDelivery;
    @BindView(R.id.btnAddCheckPoint)
    Button btnAddCheckPoint;
    @BindView(R.id.btnAdd)
    Button btnAdd;
    @BindView(R.id.btnSave)
    Button btnSave;
    @BindView(R.id.relativeTracking)
    RelativeLayout relativeTracking;
    @BindView(R.id.relativeDelivery)
    RelativeLayout relativeDelivery;
    @BindView(R.id.relativeWaybill)
    RelativeLayout relativeWaybill;
    @BindView(R.id.spnCheckPoints)
    Spinner spnCheckPoints;
    @BindView(R.id.edtCheckRemark)
    EditText edtCheckRemark;
    @BindView(R.id.edtReceiver)
    EditText edtReceiver;
    @BindView(R.id.loading_add)
    ProgressBar loading_add;
    @BindView(R.id.txtWayBillSource)
    TextView txtWayBillSource;
    @BindView(R.id.txtWayBillDestination)
    TextView txtWayBillDestination;
    @BindView(R.id.txtShipper)
    TextView txtShipper;

    @BindView(R.id.txtCosignee)
    TextView txtCosignee;
    @BindView(R.id.txtCode)
    TextView txtCode;
    @BindView(R.id.txtDocType)
    TextView txtDocType;

    @BindView(R.id.txtCustomerTye)
    TextView txtCustomerTye;
    @BindView(R.id.txtPaymentType)
    TextView txtPaymentType;

    MainContract.MainPresenter mainPresenter;
    private Map<String,String>[] checkpointlist;
    private String check_id, waybill_id;
    Waybilldetailresponse wayBillDetail;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trackingpoint,container,false);
        ButterKnife.bind(this,view);
        Utility.page = 3;
        relativeTracking.setVisibility(View.GONE);
        relativeDelivery.setVisibility(View.GONE);
        relativeWaybill.setVisibility(View.GONE);
        btnDelivery.setVisibility(View.GONE);
        btnAddCheckPoint.setVisibility(View.GONE);
        hideProgress();
        mainPresenter = new Presenter(this, getActivity());
        //mainPresenter.getPickupNotiCount(AppData.GetToken(getActivity().getApplicationContext()));
        imgbtnScan3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scan();
            }
        });
        edtWayBillCode3.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    mainPresenter.getWayBillByCode(AppData.GetToken(getActivity().getApplicationContext()), edtWayBillCode3.getText().toString());
                    return true;
                }
                return false;
            }
        });
        btnAddCheckPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgress();
                mainPresenter.getCheckPoints(AppData.GetToken(getActivity().getApplicationContext()));
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainPresenter.changeCheckPoint(AppData.GetToken(getActivity().getApplicationContext()),waybill_id, check_id, edtCheckRemark.getText().toString());
            }
        });
        btnDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relativeTracking.setVisibility(View.GONE);
                relativeDelivery.setVisibility(View.VISIBLE);
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainPresenter.devilery(AppData.GetToken(getActivity().getApplicationContext()), edtReceiver.getText().toString(), waybill_id);
            }
        });
        spnCheckPoints.setOnItemSelectedListener(this);
        return view;
    }

    private void scan(){
        IntentIntegrator scanIntegrator = new IntentIntegrator(getActivity());
        scanIntegrator.setDesiredBarcodeFormats(IntentIntegrator.CODE_128);
        scanIntegrator.setPrompt("Scan a barcode");
        scanIntegrator.setCameraId(0);
        scanIntegrator.setOrientationLocked(true);
        scanIntegrator.setBeepEnabled(true);
        scanIntegrator.setBarcodeImageEnabled(true);
        scanIntegrator.forSupportFragment(this).initiateScan();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent in) {
        super.onActivityResult(requestCode, resultCode, in);
        if (resultCode == Activity.RESULT_OK) {

            IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, in);
            if (scanResult != null) {
                String contents = in.getStringExtra("SCAN_RESULT");
                edtWayBillCode3.setText(contents);
                mainPresenter.getWayBillByCode(AppData.GetToken(getActivity().getApplicationContext()), edtWayBillCode3.getText().toString());
            }else{
                Log.e("SEARCH_EAN", "IntentResult je NULL!");
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Log.e("SEARCH_EAN", "CANCEL");
            Toast.makeText(getActivity(), "You cancelled the operation", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showTrackingPoint(Map<String, String>[] list) {
        Log.d("list", new Gson().toJson(list[0].keySet()+"\n"+list[0].values()));
        checkpointlist = list;
        if (list != null) {
            relativeDelivery.setVisibility(View.GONE);
            relativeTracking.setVisibility(View.VISIBLE);
            int size = list.length;
            String[] item = new String[size];
            item[0] = "Select Check Point";
            for (int i = 1; i < size; i++) {
                Log.d("list", list[i-1].get("id")+list[i-1].get("code_type_id")+list[i-1].get("name"));
                item[i] = list[i-1].get("name");
            }
            ArrayAdapter<String> customerAdapter = new ArrayAdapter<>(getActivity(),
                    android.R.layout.simple_list_item_1, item);
            spnCheckPoints.setAdapter(customerAdapter);
        }
    }

    @Override
    public void showProgress() {
        loading_add.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        loading_add.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(String message) {

        edtCheckRemark.setText("");
        edtReceiver.setText("");
        new FancyAlertDialog.Builder(getActivity())
                .setTitle("Show status")
                .setBackgroundColor(Color.parseColor("#63341a"))  //Don't pass R.color.colorvalue
                .setMessage(message)
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
    public void showWaybill(Waybilldetailresponse waybilldetailresponse) {
        if(waybilldetailresponse == null){
            Toast.makeText(getActivity(), "No Data", Toast.LENGTH_LONG).show();
        }else {
            this.wayBillDetail = waybilldetailresponse;
            waybill_id = waybilldetailresponse.getWaybill().get(0).getId().toString();
            relativeWaybill.setVisibility(View.VISIBLE);
            btnDelivery.setVisibility(View.VISIBLE);
            btnAddCheckPoint.setVisibility(View.VISIBLE);
            txtCode.setText(wayBillDetail.getWaybill().get(0).getCode());
            txtWayBillDestination.setText(wayBillDetail.getWaybill().get(0).getLocationTo());
            txtWayBillSource.setText(wayBillDetail.getWaybill().get(0).getLocationFrom());
            txtCosignee.setText(wayBillDetail.getWaybill().get(0).getConsignee());
            txtCustomerTye.setText(wayBillDetail.getWaybill().get(0).getCustomerTypeId());
            txtShipper.setText(wayBillDetail.getWaybill().get(0).getShipper());
            txtPaymentType.setText(wayBillDetail.getWaybill().get(0).getPaymentType());
            txtDocType.setText(wayBillDetail.getWaybill().get(0).getDocType());
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String TAG = "TrackingPointFragment";
        switch (parent.getId()) {
            case R.id.spnCheckPoints:
                String customer = parent.getSelectedItem().toString();
                for (Map<String, String> value : checkpointlist) {
                    if (value.get("name").equals(customer)) {
                        check_id = value.get("id");
                    }
                }
                Log.i(TAG, "check " + check_id);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
