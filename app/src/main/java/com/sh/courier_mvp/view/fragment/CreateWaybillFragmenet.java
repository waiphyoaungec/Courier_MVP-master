package com.sh.courier_mvp.view.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.sh.courier_mvp.R;
import com.sh.courier_mvp.util.Utility;
import com.sh.courier_mvp.adapter.LocationAdapter;
import com.sh.courier_mvp.adapter.ShipperInfoAdapter;
import com.sh.courier_mvp.data.AppData;
import com.sh.courier_mvp.model.Pickupdetailresponse;
import com.sh.courier_mvp.model.ShipperModel;
import com.sh.courier_mvp.presenter.MainContract;
import com.sh.courier_mvp.presenter.Presenter;
import com.shashank.sony.fancydialoglib.Animation;
import com.shashank.sony.fancydialoglib.FancyAlertDialog;
import com.shashank.sony.fancydialoglib.FancyAlertDialogListener;
import com.shashank.sony.fancydialoglib.Icon;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import app.dinus.com.loadingdrawable.LoadingView;
import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class CreateWaybillFragmenet extends Fragment implements MainContract.AddWayBillView,AdapterView.OnItemSelectedListener {
    @BindView(R.id.spnCustomerType)
    Spinner spnCustomerType;
    @BindView(R.id.spnCurrencyType)
    Spinner spnCurrencyType;
    @BindView(R.id.spnDocType)
    Spinner spnDocType;
    @BindView(R.id.spnPaymentType)
    Spinner spnPaymentType;
    @BindView(R.id.spnRateCard)
    Spinner spnRateCard;
    @BindView(R.id.comtxtDestination)
    AutoCompleteTextView comtxtDestination;
    @BindView(R.id.comtxtRemoteDestination)
    AutoCompleteTextView comtxtRemoteDestination;
    @BindView(R.id.comtxtSource)
    AutoCompleteTextView comtxtSource;
    @BindView(R.id.comtxtRemoteSource)
    AutoCompleteTextView comtxtRemoteSource;
    @BindView(R.id.edtWayBillCode)
    EditText edtWayBillCode;
    @BindView(R.id.edtConsignee)
    TextInputEditText edtConsignee;
    @BindView(R.id.edtConsigneeCompanyName)
    TextInputEditText edtConsigneeCompanyName;
    @BindView(R.id.edtConsigneeContactName)
    TextInputEditText edtConsigneeContactName;
    @BindView(R.id.edtConsigneeAddr)
    TextInputEditText edtConsigneeAddr;
    @BindView(R.id.edtConsigneePhone)
    TextInputEditText edtConsigneePhone;
    @BindView(R.id.edtConsigneeCode)
    TextInputEditText edtConsigneeCode;
    @BindView(R.id.edtConsigneeEmail)
    TextInputEditText edtConsigneeEmail;
    @BindView(R.id.consigneeProgress)
    ProgressBar consigneeProgress;
    @BindView(R.id.edtConsigneeRateCard)
    TextInputEditText edtConsigneeRateCard;

    @BindView(R.id.edtLength)
    EditText edtLength;
    @BindView(R.id.edtWidth)
    EditText edtWidth;
    @BindView(R.id.edtHeight)
    EditText edtHeight;
    @BindView(R.id.edtGrossWeight)
    EditText edtGrossWeight;
    @BindView(R.id.edtDiscountAmount)
    EditText edtDiscountAmount;
    @BindView(R.id.edtPieces)
    EditText edtPieces;
    @BindView(R.id.edtCommericalTax)
    EditText edtCommericalTax;
    @BindView(R.id.edtAdditionalAmt)
    EditText edtAdditionalAmt;
    @BindView(R.id.edtItemAmount)
    EditText edtItemAmount;
    @BindView(R.id.edtOtherCharges)
    EditText edtOtherCharges;
    @BindView(R.id.edtReceivedAmt)
    EditText edtReceivedAmt;
    @BindView(R.id.edttwopercentdisc)
    EditText edttwoPercentDisc;
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
    @BindView(R.id.edtEmail)
    TextInputEditText edtEmail;
    @BindView(R.id.edtRateCard)
    TextInputEditText edtRateCard;

    @BindView(R.id.txtDiscountPercentage)
    EditText txtDiscountPercentage;
    @BindView(R.id.txtCharges)
    TextView txtCharges;
    @BindView(R.id.txtDesCharges)
    TextView txtDesCharges;
    @BindView(R.id.txtTotal)
    TextView txtTotal;
    @BindView(R.id.txtExchangeRate)
    TextView txtExchangeRate;
    @BindView(R.id.txtDesExchangeRate)
    TextView txtDesExchangeRate;
    @BindView(R.id.txtRemoteCharges)
    TextView txtRemoteCharges;
    @BindView(R.id.txtDesServiceAmount)
    TextView txtDesServiceAmount;
    @BindView(R.id.txtServiceAmount)
    TextView txtServiceAmount;
    @BindView(R.id.txtDimensionWeight)
    TextView txtDimensionWeight;
    @BindView(R.id.waybill_remark)
    TextInputEditText remark;

    @BindView(R.id.btnAddWayBill)
    Button btnAddWayBill;
    @BindView(R.id.imgbtnScan)
    ImageButton imageButtonScan;
    @BindView(R.id.btnCalculate)
    Button btnCalculate;
    @BindView(R.id.imgWayBill)
    ImageView imgWayBill;

    @BindView(R.id.linearCommercialTax)
    LinearLayout linearCommercialTax;
    @BindView(R.id.linearDiscount)
    LinearLayout linearDiscount;
    @BindView(R.id.linearItemAmount)
    LinearLayout linearItemAmount;
    @BindView(R.id.linearOtherCharges)
    LinearLayout linearOtherCharges;
    @BindView(R.id.linearAllData)
    LinearLayout linearAllData;
    @BindView(R.id.linearAdditionalAmt)
    LinearLayout linearAdditionalAmt;
    @BindView(R.id.twopercentdisclayout)
    LinearLayout linearTwoPercentUser;
    @BindView(R.id.waybillloading)
    LoadingView loadingView;
    @BindView(R.id.pbcalculate)
    ProgressBar progressBar;

    @BindView(R.id.txtkeyword)
    AutoCompleteTextView edtShipperKeyword;
    @BindView(R.id.shipperProgress)
    ProgressBar shipperProgress;
    @BindView(R.id.shipperInfo)
    RelativeLayout shipperInfoLayout;
    @BindView(R.id.edtConsigneeKeyword)
    AutoCompleteTextView edtConsigneeKeyword;


    @BindView(R.id.errorView)
            RelativeLayout errorView;
    @BindView(R.id.try_again_text)
            TextView try_again_text;
    @BindView(R.id.try_again)
            Button try_again;

    MainContract.MainPresenter mainPresenter;
    LocationAdapter adapter;
    LocationAdapter remoteadapter;
    ArrayAdapter<String> sourceRemoteAdapter;
    ArrayAdapter<String> destinationRemoteAdapter;
    ShipperInfoAdapter shipperInfo;
    private String imageFilePath = "";
    private Map<String, String>[]  remoteFromList, remoteToList, customerList, paymentList;

    int cur_id;
    int doc_type = 0;
    int payment_type = 0;
    int remote_from;
    int remote_to;
    private int customer_type = 0;
    private double dis_amt;
    private double online_charges;
    private double additional_amt;
    private double received_amt;
    //private int received_amt_cur_id;
    private double commercial_tax;
    private double total_amt;
    private double actual_weight;
    private double cur_rate;
    private double item_amt;
    private double gross_weight;
    private double length;
    private double width;
    private double height;
    private String TAG = "CreateWaybillFragmenet";
    private String location_from = "";
    private String location_to = "";
    private String remote = "";
    private String ratecard_id = "0";
    private static final int REQUEST_IMAGE = 101;
    private String img = "";
    private String shipper_code = "";
    private int pieces;
    private boolean poramount=false;
    private boolean first_bind = false;
    private Double global_charges = 0.0;
    private List<ShipperModel> shipperList;
    private String [] item;
    private Typeface zawgyi;
    private String keywordType = "shipper";
    private String str_ratecard = "";
    private  ArrayList<String> ratecard;
    String pickupTime = "";



    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_addwaybill,container,false);
        ButterKnife.bind(this, view);
        Utility.page = 5;
        mainPresenter = new Presenter(this, getActivity());
        String token = AppData.GetToken(getContext());
        bindSpinner();

        mainPresenter.getLocation(token);
        mainPresenter.getPaymentType(AppData.GetToken(getContext()));
        mainPresenter.getCustomerType(AppData.GetToken(getContext()));

         zawgyi = Typeface.createFromAsset(getContext().getAssets(),"zawgyi.TTF");

        first_bind = true;

        errorView.setVisibility(View.GONE);

        spnCustomerType.setOnItemSelectedListener(this);
        spnDocType.setOnItemSelectedListener(this);
        spnPaymentType.setOnItemSelectedListener(this);
        spnCurrencyType.setOnItemSelectedListener(this);
        spnRateCard.setOnItemSelectedListener(this);
        mainPresenter.getShipperInfo(
                "ygn"
        );

        comtxtSource.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                remote = "FROM";
                //String[] lf = adapter.getItem(position).split(" - ", 0);//substring
                location_from = adapter.getItem(position).substring(0, 3);
                Log.d("getft", location_from);
                mainPresenter.getRemoteArea(AppData.GetToken(getContext()), location_from);
            }
        });

        edtShipperKeyword.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                shipperInfoLayout.setVisibility(View.VISIBLE);
                shipperProgress.setVisibility(View.GONE);
                if(shipperList!=null && position<= shipperList.size()){
                    shipperInfoLayout.setVisibility(View.VISIBLE);
                    edtShipperKeyword.setText(shipperList.get(position).getCode());
                    edtShipperCode.setText(shipperList.get(position).getCode());
                    edtShipperName.setText(shipperList.get(position).getName());
                    edtCompanyName.setText(shipperList.get(position).getCompanyName());
                    edtContactName.setText(shipperList.get(position).getContactName());
                    edtAddress.setText(shipperList.get(position).getMailingAddr());
                    edtPhone.setText(shipperList.get(position).getMailingPhone());
                    edtEmail.setText(shipperList.get(position).getMailingEmail());
                    edtRateCard.setText(shipperList.get(position).getRateCard());
                    str_ratecard = shipperList.get(position).getRateCard();

                    ratecard = new ArrayList<>();
                    ratecard.add("Choose Rate Card");
                    ratecard_id = shipperList.get(position).getRateCardHdrIds();
                    if(str_ratecard.length()<2) //if rate card name is null or empty
                        ratecard.add("Express");
                    else
                        ratecard.add(str_ratecard);
                    ArrayAdapter<String> ratecardAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, ratecard);
                    ratecardAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnRateCard.setAdapter(ratecardAdapter);
                }

            }
        });
        edtConsigneeKeyword.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {


                consigneeProgress.setVisibility(View.GONE);
                if(shipperList!=null  && position<= shipperList.size() ){

                    edtConsigneeKeyword.setText(shipperList.get(position).getCode());
                    edtConsigneeCode.setText(shipperList.get(position).getCode());
                    edtConsignee.setText(shipperList.get(position).getName());
                    edtConsigneeCompanyName.setText(shipperList.get(position).getCompanyName());
                    edtConsigneeContactName.setText(shipperList.get(position).getContactName());
                    edtConsigneeAddr.setText(shipperList.get(position).getMailingAddr());
                    edtConsigneePhone.setText(shipperList.get(position).getMailingPhone());
                    edtConsigneeEmail.setText(shipperList.get(position).getMailingEmail());
                    edtConsigneeRateCard.setText(shipperList.get(position).getRateCard());
                }

            }
        });

        comtxtDestination.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getContext(),"clickeddd",Toast.LENGTH_SHORT).show();
                remote = "TO";
                //String[] lt = remoteadapter.getItem(position).split(" - ", 0);
                location_to = remoteadapter.getItem(position).substring(0, 3);
                Log.d("geft",location_to);
                mainPresenter.getRemoteArea(AppData.GetToken(getContext()), location_to);
            }
        });
        comtxtRemoteSource.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = sourceRemoteAdapter.getItem(position);
                for (Map<String, String> aRemoteFromList : remoteFromList) {
                    if (aRemoteFromList.get("name").equals(name)) {
                        remote_from = Integer.parseInt(aRemoteFromList.get("id"));
                    }
                }
                mainPresenter.getRemoteCharges(AppData.GetToken(getContext()), remote_to, remote_from);
            }
        });
        comtxtRemoteDestination.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = destinationRemoteAdapter.getItem(position);
                for (Map<String, String> aRemoteToList : remoteToList) {
                    if (aRemoteToList.get("name").equals(name)) {
                        remote_to = Integer.parseInt(aRemoteToList.get("id"));
                    }
                }
                //Log.i(TAG,"remote_from "+ remote_from +" remote_to"+ remote_to);
                mainPresenter.getRemoteCharges(AppData.GetToken(getContext()), remote_to, remote_from);
            }
        });

        edtCommericalTax.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //calculateTotal();
            }

            @Override
            public void afterTextChanged(Editable s) {
                calculateTotal();
            }
        });
        edtItemAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //calculateTotal();
            }

            @Override
            public void afterTextChanged(Editable s) {
                calculateTotal();
            }
        });
        edtOtherCharges.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                calculateTotal();
            }
        });
        edtAdditionalAmt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                calculateTotal();
            }
        });
        edtDiscountAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(edtDiscountAmount.isFocused()){
                    if(s.length()==0){
                        calculate_Amount(0.0);
                    }else {
                        calculate_Amount(Double.parseDouble(s.toString()));
                    }
                }
            }
        });
        txtDiscountPercentage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
               if(txtDiscountPercentage.isFocused()){
                   if(s.length()==0){
                       calculate_Percent(0.0);
                   }else{
                       calculate_Percent(Double.parseDouble(s.toString()));
                   }
               }
            }
        });

        edttwoPercentDisc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                calculateTotal();
            }
        });


        edtShipperKeyword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                keywordType = "shipper";
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if( charSequence.toString().trim().length()>0 && charSequence.toString().trim().length()<15)

               mainPresenter.getShipperInfo(charSequence.toString().toUpperCase());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtConsigneeKeyword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                keywordType = "consignee";
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if( charSequence.toString().trim().length()>0 && charSequence.toString().trim().length()<15)

                    mainPresenter.getShipperInfo(charSequence.toString().toUpperCase());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        imageButtonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scan();
            }
        });
        imgWayBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                dispatchTakePictureIntent();
                }
                catch(Exception e){
                    Toast.makeText(getContext(),"error" + e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showProgress();
                progressBar.setVisibility(View.VISIBLE);
                calculate();
            }
        });
        btnAddWayBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addWaybill();
            }
        });
        setHasOptionsMenu(true);
        return view;
    }

    private void addWaybill(){
        Double two_percent=0.0;
        if(edtWayBillCode.getText().length() == 0) {
            edtWayBillCode.setError("Please Scan Barcode!");
            showError("Please Enter Barcode!");
        }else if(location_from.equals("") || location_to.equals("")){
            comtxtSource.setError("Please Select Location");
            showError("Please Select Location!");
        }else if(customer_type == 0 || doc_type == 0 || payment_type == 0){
            showError("Please choose customer or document or payment type.");
        }else if(img.equals("")){
            showError("Please take photo.");
        }else if(edtShipperName.getText().length()==0){
            edtShipperName.setError("");
            showError("Please Enter ShipperName");
        }else if(edtAddress.getText().length()==0){
            edtAddress.setError("");
            showError("Please Enter Shipper Address");
        }else if(edtPhone.getText().length()==0){
            edtPhone.setError("");
            showError("Please Enter Shipper Phone");
        }
        else if(edtEmail.getText().length() == 0){
            edtEmail.setError("");
            showError("Please Enter Email");

        }
        else if(edtConsignee.getText().length()==0){
            edtConsignee.setError("");
            showError("Please Enter ConsigneeName");
        }else if(edtConsigneeAddr.getText().length()==0){
            edtConsigneeAddr.setError("");
            showError("Please Enter ConsigneeAddr");
        }else if(edtConsigneePhone.getText().length()==0){
            edtConsigneePhone.setError("");
            showError("Please Enter ConsigneePhno");
        }
        else {
            showProgress();
            shipper_code = edtShipperCode.getText().toString();

            double area_charges = txtRemoteCharges.getText().toString().equals("") ? 0.0 : Double.valueOf(txtRemoteCharges.getText().toString());
            double charges = txtCharges.getText().toString().equals("") ? 0.0 : Double.valueOf(txtCharges.getText().toString());
            double service_amt = txtServiceAmount.getText().toString().equals("") ? 0.0 : Double.valueOf(txtServiceAmount.getText().toString());
            double dis_percent = txtDiscountPercentage.getText().toString().equals("") ? 0.0 : Double.valueOf(txtDiscountPercentage.getText().toString());
            //total_amt = txtTotal.getText().toString().equals("") ? 0.0 : Double.valueOf(txtTotal.getText().toString());
            if(edtCommericalTax.getText().length() > 0)
                commercial_tax = edtCommericalTax.getText().toString().equals("") ? 0.0 : Double.valueOf(edtCommericalTax.getText().toString());
            if(edtDiscountAmount.getText().length() > 0)
                dis_amt = edtDiscountAmount.getText().toString().equals("") ? 0.0 : Double.valueOf(edtDiscountAmount.getText().toString());
            if(edtItemAmount.getText().length() > 0)
                item_amt = edtItemAmount.getText().toString().equals("") ? 0.0 : Double.valueOf(edtItemAmount.getText().toString());
            if(edtReceivedAmt.getText().length() > 0)
                received_amt = edtReceivedAmt.getText().toString().equals("")? 0.0 : Double.valueOf(edtReceivedAmt.getText().toString());
            if(edtOtherCharges.getText().length() > 0)
                online_charges = edtOtherCharges.getText().toString().equals("")? 0.0 : Double.valueOf(edtOtherCharges.getText().toString());
            if(edtAdditionalAmt.getText().length() > 0)
                additional_amt = edtAdditionalAmt.getText().toString().equals("")? 0.0 : Double.valueOf(edtAdditionalAmt.getText().toString());
            if(edtPieces.getText().length() > 0)
                pieces = edtPieces.getText().toString().equals("") ? 0 : Integer.valueOf(edtPieces.getText().toString());
            if(spnCustomerType.getSelectedItem().equals("E-Com")){
                if(edttwoPercentDisc.getText().length()>0){
                    two_percent = Double.parseDouble(edttwoPercentDisc.getText().toString());
                }
            }
            mainPresenter.addWayBill(AppData.GetToken(getContext()), location_from, location_to, remote_from, remote_to, edtWayBillCode.getText().toString(), customer_type, doc_type, "", 0.0, shipper_code,
                    edtShipperName.getText().toString(), edtCompanyName.getText().toString(), edtContactName.getText().toString(), edtAddress.getText().toString(), edtPhone.getText().toString(), edtEmail.getText().toString(),
                    edtConsignee.getText().toString(), edtConsigneeCompanyName.getText().toString(), edtConsigneeContactName.getText().toString(), edtConsigneeAddr.getText().toString(), edtConsigneePhone.getText().toString(),
                    edtEmail.getText().toString(), remark.getText().toString() + "", payment_type, 0, 13, gross_weight, length, width, height, actual_weight, pieces, cur_id, cur_rate, item_amt,
                    service_amt, charges, commercial_tax, dis_percent, dis_amt, area_charges,online_charges , additional_amt,
                    received_amt, cur_id, total_amt, "", "", img, "NEW", 0, ratecard_id, pickupTime,two_percent);
        }
    }

    private void calculate(){
       if(spnCustomerType.getSelectedItemPosition()==0 || spnRateCard.getSelectedItemPosition()==0
       || spnPaymentType.getSelectedItemPosition()==0 || spnDocType.getSelectedItemPosition()==0){
           String msg = "";
           if(spnCustomerType.getSelectedItemPosition()==0){
               TextView tvcustomer = (TextView) spnCustomerType.getSelectedView();
               tvcustomer.setError("CustomerType");
               msg += "\nChoose Customer Type";
           }
           if(spnPaymentType.getSelectedItemPosition()==0){
               TextView tvpayment = (TextView) spnPaymentType.getSelectedView();
               tvpayment.setError("PaymentType");
               msg += "\nChoose Payment Type";
           }
           if(spnDocType.getSelectedItemPosition()==0){
               TextView tvdocument = (TextView) spnDocType.getSelectedView();
               tvdocument.setError("DocumentType");
               msg += "\nChoose Document Type";
           }
           if(spnRateCard.getSelectedItemPosition()==0){
               TextView tvratecard = (TextView) spnRateCard.getSelectedView();
               tvratecard.setError("RateCard First");
               msg += "\nChoose RateCard";
           }
           showError(msg);
           Log.d("testtest","firest block");
       }
       else{
           //loadingView.setVisibility(View.VISIBLE);
           if(edtLength.getText().length() > 0)
               length = edtLength.getText().toString().equals("") ? 0.0 :  Double.parseDouble(edtLength.getText().toString());
           if(edtWidth.getText().length() > 0)
               width = edtWidth.getText().toString().equals("") ? 0.0 :  Double.parseDouble(edtWidth.getText().toString());
           if(edtHeight.getText().length() > 0)
               height = edtHeight.getText().toString().equals("") ? 0.0 :  Double.parseDouble(edtHeight.getText().toString());
           actual_weight = Double.parseDouble(String.format("%.2f", (length * width * height/6000)));
           Log.i(TAG, "dimension "+ actual_weight);
           txtDimensionWeight.setText(actual_weight + " kg");
           if(edtGrossWeight.getText().length() > 0)
               gross_weight = edtGrossWeight.getText().toString().equals("") ? 0.0 :  Double.parseDouble(edtGrossWeight.getText().toString());

           if(edtShipperCode.getText().length() == 0){
               shipper_code = "";
           }else{
               shipper_code = edtShipperCode.getText().toString();
           }
           Log.d("testtest","second block" + payment_type);
           mainPresenter.getRateCard(AppData.GetToken(getContext()),location_from, location_to, String.valueOf(gross_weight), String.valueOf(actual_weight), String.valueOf(customer_type), String.valueOf(payment_type), String.valueOf(cur_id), shipper_code, ratecard_id);
       }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent in) {
        super.onActivityResult(requestCode, resultCode, in);
        if (resultCode == RESULT_OK) {

            IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, in);
            if (scanResult != null) {
                String contents = in.getStringExtra("SCAN_RESULT");
                edtWayBillCode.setText(contents);
            }else{
                Log.e("SEARCH_EAN", "IntentResult je NULL!");
            }
        } else if (resultCode == RESULT_CANCELED) {
            Log.e("SEARCH_EAN", "CANCEL");
            Toast.makeText(getActivity(), "You cancelled the operation", Toast.LENGTH_SHORT).show();
        }
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                RequestOptions requestOptions = new RequestOptions();
                requestOptions=requestOptions.placeholder(R.drawable.ic_add);
                Glide.with(imgWayBill.getContext())
                        .load(imageFilePath).apply(requestOptions)
                        .into(imgWayBill);
                Bitmap bm = BitmapFactory.decodeFile(imageFilePath);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 70, stream);
                byte[] byteFormat = stream.toByteArray();
                // get the base 64 string
                img = Base64.encodeToString(byteFormat, Base64.NO_WRAP);
            }
            else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getActivity(), "You cancelled the operation", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void dispatchTakePictureIntent() {
        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (pictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {

            File photoFile;
            try {
                photoFile = createImageFile();
            }
            catch (IOException e) {
                Toast.makeText(getContext(),"error" + e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                e.printStackTrace();
                return;
            }
            Uri photoUri = FileProvider.getUriForFile(getActivity(), getActivity().getPackageName() +".provider", photoFile);
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            startActivityForResult(pictureIntent, REQUEST_IMAGE);
        }
    }

    private File createImageFile() throws IOException{

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        imageFilePath = image.getAbsolutePath();

        return image;
    }

    private void scan(){
        IntentIntegrator scanIntegrator = new IntentIntegrator(getActivity());
        scanIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        scanIntegrator.setPrompt("Scan a barcode");
        scanIntegrator.setCameraId(0);
        scanIntegrator.setOrientationLocked(false);
        scanIntegrator.setBeepEnabled(true);
        scanIntegrator.setBarcodeImageEnabled(true);
        scanIntegrator.forSupportFragment(this).initiateScan();
    }

    @Override
    public void showProgress() {
        loadingView.setVisibility(View.VISIBLE);
        linearAllData.setVisibility(View.GONE);
    }

    @Override
    public void LoadError(String error) {
        Toast.makeText(getContext(),"Loading error..",Toast.LENGTH_SHORT).show();
        loadingView.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
        try_again_text.setText("\n"+error + "\n");
        try_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                errorView.setVisibility(View.GONE);
                loadingView.setVisibility(View.VISIBLE);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content, new CreateWaybillFragmenet());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();

            }
        });

    }

    @Override
    public void hideProgress() {

        loadingView.setVisibility(View.GONE);
        linearAllData.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        errorView.setVisibility(View.GONE);

    }



    @Override
    public void bindSpinner() {
        ArrayAdapter<CharSequence> docAdapter = ArrayAdapter
                .createFromResource(getActivity(), R.array.doc_type,
                        android.R.layout.simple_spinner_item);
        docAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnDocType.setAdapter(docAdapter);

        ArrayAdapter<CharSequence> currencyAdapter = ArrayAdapter
                .createFromResource(getActivity(), R.array.currency_type,
                        android.R.layout.simple_spinner_item);
        currencyAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCurrencyType.setAdapter(currencyAdapter);

        ratecard = new ArrayList<>();
        ratecard.add("Choose Rate Card");
        if(str_ratecard.length()<2)
            ratecard.add("Express");
        else
            ratecard.add(str_ratecard);
        ArrayAdapter<String> ratecardAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, ratecard);
        ratecardAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnRateCard.setAdapter(ratecardAdapter);


    }

    @Override
    public void showLocation(String[] list) {
        Log.i(TAG, "location "+ list);
        List<String> remoteFrom = Arrays.asList(list);
        adapter = new LocationAdapter(getContext(),R.layout.shipperitem,remoteFrom,zawgyi);

        comtxtSource.setThreshold(1);
        comtxtSource.setAdapter(adapter);
        comtxtSource.setTypeface(zawgyi);

        remoteadapter = new LocationAdapter(getContext(),R.layout.shipperitem,remoteFrom,zawgyi);
        comtxtDestination.setThreshold(1);
        comtxtDestination.setAdapter(remoteadapter);
        comtxtDestination.setTypeface(zawgyi);
        hideProgress();
    }

    @Override
    public void showCustomerType(Map<String, String>[] list) {
        customerList = list;
        int size = list.length+1;
        String[] item = new String[size];
        item[0] = "Customer Type";
        for (int i = 1; i < size; i++) {
            item[i] = list[i-1].get("name");
        }
        ArrayAdapter<String> customerAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, item);
        spnCustomerType.setAdapter(customerAdapter);
    }

    @Override
    public void showPaymentType(Map<String, String>[] list) {
        paymentList = list;
        int size = list.length+1;
        String[] item = new String[size];
        item[0] = "Payment Type";
        for (int i = 1; i < size; i++) {
            item[i] = list[i-1].get("name");
        }
        ArrayAdapter<String> paymentAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, item);
        spnPaymentType.setAdapter(paymentAdapter);
    }

    @Override
    public void showRemoteLocation(Map<String, String>[] list) {
        Log.d("remotelocation", new Gson().toJson(list));
        if(list == null){
            Toast.makeText(getActivity(), "no remote location", Toast.LENGTH_LONG).show();
        }else{
            String [] item = new String[list.length];
            for(int i = 0; i < list.length; i++){
                item[i] = list[i].get("name");
            }
            if(remote.equals("FROM")){
                remoteFromList = list;
                sourceRemoteAdapter = new LocationAdapter(getContext(),R.layout.shipperitem,Arrays.asList(item),zawgyi);
                comtxtRemoteSource.setThreshold(1);
                comtxtRemoteSource.setAdapter(sourceRemoteAdapter);
                comtxtRemoteSource.setTypeface(zawgyi);
            }else {
                remoteToList = list;
                destinationRemoteAdapter = new LocationAdapter(getContext(),R.layout.shipperitem,Arrays.asList(item),zawgyi);
                comtxtRemoteDestination.setThreshold(1);
                comtxtRemoteDestination.setAdapter(destinationRemoteAdapter);
                comtxtRemoteDestination.setTypeface(zawgyi);
            }
        }
    }

    @Override
    public void showRate(Map<String, String> response) {
        if(response == null){
            Toast.makeText(getActivity(), "No Rate Card!", Toast.LENGTH_SHORT).show();
        }else {

            String amount = response.get("amt");
            String exchange_rate = response.get("cur_rate");
            pickupTime = response.get("pickup_time");

            Log.d(TAG, cur_id+" CurrencyID");
            if(cur_id == 2){
                txtExchangeRate.setText(exchange_rate);
                if (customer_type == 2) {
                    txtServiceAmount.setText(amount);
                }else{
                    txtCharges.setText(amount);
                }
                Double charges_mmk_usd = global_charges/Double.parseDouble(exchange_rate);
                txtRemoteCharges.setText(String.valueOf(charges_mmk_usd));
            }else{
                if (customer_type == 2) {
                    txtServiceAmount.setText(amount);
                }else{
                    txtCharges.setText(amount);
                }
                txtRemoteCharges.setText(global_charges+"");
            }
           Log.d("testtest","Calculating..." + exchange_rate + " --- " + amount +"testtest id "+ cur_id + " customer_type" + customer_type + " "+
                   pickupTime);
            Toast.makeText(getContext(),"Calculating..." + exchange_rate + " --- " + amount +" id "+ cur_id + " customer_type" + customer_type,Toast.LENGTH_SHORT).show();
            calculateTotal();
        }
    }

    @Override
    public void showRemoteCharges(String charges) {
        global_charges = Double.parseDouble(charges);
        txtRemoteCharges.setText(charges);
        calculateTotal();
    }

    @Override
    public void showPickupData(Pickupdetailresponse pickupdetailresponse) {

        /*this.pickupdetailresponse = pickupdetailresponse;
        this.shipper = pickupdetailresponse.getShipper();
        this.shipper_code = pickupdetailresponse.getShipperCode();
        this.shipper_addr = pickupdetailresponse.getShipperAddr();
        this.shipper_company_name = pickupdetailresponse.getShipperCompanyName();
        this.shipper_contact_name = pickupdetailresponse.getShipperContactName();
        this.shipper_phone = pickupdetailresponse.getShipperPhone();
        ArrayList<String> years = new ArrayList<>();
        years.add("Choose Rate Card");
        for (int i = 0; i < pickupdetailresponse.getRatecard().getItem().size(); i++) {
            years.add(pickupdetailresponse.getRatecard().getItem().get(i).getRatecardName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, years);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnRateCard.setAdapter(adapter);
        if (customer_type != 2) {

            if (pickupdetailresponse.getIsPercent().equals("1")) {
                Log.i(TAG, "diper " + pickupdetailresponse.getDisPercent());
                txtDiscountPercentage.setText(pickupdetailresponse.getDisPercent());
            } else {
                Log.i(TAG, "disval " + pickupdetailresponse.getDisPercent());
                if (weight > 5) {
                    edtDiscountAmount.setText(pickupdetailresponse.getDisValue2());
                } else {
                    edtDiscountAmount.setText(pickupdetailresponse.getDisValue1());
                }
            }
        }*/
    }

    @Override
    public void showMessage(String message) {
        new FancyAlertDialog.Builder(getActivity())
                .setTitle("S" +
                        "how status")
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
                        Toast.makeText(getContext(),"success",Toast.LENGTH_LONG).show();
                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.content, new CheckPickupListFragment());
                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                        ft.commit();
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
    public void showError(String message) {
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

    private void calculateTotal(){
        double serviceAmt = 0, charges = 0, tax = 0, remote_charges = 0, othercharges = 0, twopercentdisc = 0;
        try{
            dis_amt = edtDiscountAmount.getText().toString().equals("") ? 0.0 :  Double.parseDouble(edtDiscountAmount.getText().toString());
            if(edtOtherCharges.getText().length() > 0)
                othercharges = edtOtherCharges.getText().toString().equals("") ? 0.0 :  Double.valueOf(edtOtherCharges.getText().toString());
            if(txtCharges.getText().length() > 0)
                charges = txtCharges.getText().toString().equals("") ? 0.0 :  Double.valueOf(txtCharges.getText().toString());
            if(edtCommericalTax.getText().length() > 0)
                tax = edtCommericalTax.getText().toString().equals("") ? 0.0 :  Double.valueOf(edtCommericalTax.getText().toString());


            if(txtRemoteCharges.getText().length() > 0)
                remote_charges = txtRemoteCharges.getText().toString().equals("") ? 0.0 :  Double.parseDouble(txtRemoteCharges.getText().toString());
            if(txtServiceAmount.getText().length() > 0)
                serviceAmt = txtServiceAmount.getText().toString().equals("") ? 0.0 :  Double.parseDouble(txtServiceAmount.getText().toString());
            if(txtExchangeRate.getText().length() > 0)
                cur_rate = txtExchangeRate.getText().toString().equals("") ? 0.0 :  Double.parseDouble(txtExchangeRate.getText().toString());
            if(edtAdditionalAmt.getText().length() > 0)
                additional_amt = edtAdditionalAmt.getText().toString().equals("")? 0.0 : Double.valueOf(edtAdditionalAmt.getText().toString());
            if(edttwoPercentDisc.getText().length()>0){
                twopercentdisc = edttwoPercentDisc.getText().toString().equals("")? 0.0 :
                        Double.valueOf(edttwoPercentDisc.getText().toString());
            }
            if(cur_id == 2){//for USD
                if(customer_type == 2){// for online customer with other charges
                    total_amt = (serviceAmt+ othercharges + remote_charges + twopercentdisc) ;
                    String amt = getString(R.string.dollor) +total_amt;
                    txtTotal.setText(amt);
                }else{
                    total_amt = (additional_amt + charges + tax + remote_charges) - dis_amt ;
                    String amt = getString(R.string.dollor) +total_amt;
                    txtTotal.setText(amt);
                }
            }else{//for MMK
                if(customer_type == 2){// for online customer with other charges
                    total_amt = (serviceAmt+ othercharges + remote_charges + twopercentdisc) ;
                    String amt = total_amt+ getString(R.string.mmk);
                    txtTotal.setText(amt);
                }else{
                    total_amt = (additional_amt + charges + tax + remote_charges)- dis_amt ;
                    String amt = total_amt+ getString(R.string.mmk);
                    txtTotal.setText(amt);
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spnCustomerType:
                String customer = parent.getSelectedItem().toString();
                for (Map<String, String> customervalue : customerList) {
                    if (customervalue.get("name").equals(customer)) {
                        customer_type = Integer.parseInt(customervalue.get("id"));
                    }


                }
                Log.i(TAG, "customer_type " + customer_type);
                if(customer_type == 2){
                    txtCharges.setText("");
                    edtDiscountAmount.setText("");
                    txtDiscountPercentage.setText("");
                    linearCommercialTax.setVisibility(View.GONE);
                    linearDiscount.setVisibility(View.GONE);
                    txtDesCharges.setVisibility(View.GONE);
                    txtCharges.setVisibility(View.GONE);
                    linearAdditionalAmt.setVisibility(View.GONE);

                    linearItemAmount.setVisibility(View.VISIBLE);
                    linearOtherCharges.setVisibility(View.VISIBLE);
                    txtDesServiceAmount.setVisibility(View.VISIBLE);
                    txtServiceAmount.setVisibility(View.VISIBLE);
                    linearTwoPercentUser.setVisibility(View.VISIBLE);

                    edtWayBillCode.requestFocus();
                }else{
                    edtItemAmount.setText("");
                    edtOtherCharges.setText("");
                    txtServiceAmount.setText("");
                    linearItemAmount.setVisibility(View.GONE);
                    linearOtherCharges.setVisibility(View.GONE);
                    txtDesServiceAmount.setVisibility(View.GONE);
                    txtServiceAmount.setVisibility(View.GONE);

                    linearAdditionalAmt.setVisibility(View.VISIBLE);
                    linearCommercialTax.setVisibility(View.VISIBLE);
                    linearDiscount.setVisibility(View.VISIBLE);
                    txtDesCharges.setVisibility(View.VISIBLE);
                    txtCharges.setVisibility(View.VISIBLE);
                    linearTwoPercentUser.setVisibility(View.GONE);

                    edtWayBillCode.requestFocus();
                }
                break;
            case R.id.spnDocType:
                edtWayBillCode.clearFocus();
                edtLength.clearFocus();
                if(parent.getSelectedItemPosition() == 1){
                    doc_type = 20;
                }else{
                    doc_type = 21;
                }
                Log.i(TAG, "doc "+doc_type);
                break;
            case R.id.spnPaymentType:
                String payment = parent.getSelectedItem().toString();
                for (Map<String, String> value : paymentList) {
                    if (value.get("name").equals(payment)) {

                        payment_type = Integer.parseInt(value.get("id"));
                        Log.d("testtest", "hello" + value.get("name") + " " + payment_type);
                    }
                }
                Log.i(TAG, "payment_type "+ payment_type);
                break;
            case R.id.spnCurrencyType:
                if(parent.getSelectedItem().equals("MMK")){
                    cur_id = 1;
                    txtExchangeRate.setVisibility(View.GONE);
                    txtDesExchangeRate.setVisibility(View.GONE);
                }else {
                    txtExchangeRate.setVisibility(View.VISIBLE);
                    txtDesExchangeRate.setVisibility(View.VISIBLE);
                    cur_id = 2;
                }
                if(position!=0){
                    edtLength.requestFocus();
                }
                break;
            case R.id.spnRateCard:
                if(position != 0) {
                    for(int i=0;i< getResources().getStringArray(R.array.rate_card).length;i++){
                        if(ratecard.get(1).equals(getResources().getStringArray(R.array.rate_card)[i])){
                            Log.d("ratecard","  " + i);
                            break;


                        }
                    }

                    edtLength.requestFocus();

                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void calculate_Amount(Double discountAmount){
        Double charges=0.0, disAmt = 0.0, cal_exchangerate=0.0;

        disAmt = discountAmount;
            if(txtCharges.getText().length() > 0 && (Double.valueOf(txtCharges.getText().toString()) != 0.00)) {
                Log.i(TAG, "charges " +txtCharges.getText().toString());
                charges = Double.valueOf(txtCharges.getText().toString());
                if(spnCurrencyType.getSelectedItemPosition()==0){//0 for MMK
                    disAmt = (disAmt/charges)*100;
                }else if(spnCurrencyType.getSelectedItemPosition()==1){//1 for USD
                    cal_exchangerate= disAmt/Double.parseDouble(txtExchangeRate.getText().toString());
                    disAmt = (cal_exchangerate/charges)*100;
                }
            txtDiscountPercentage.setText(String.format("%.2f", disAmt));
            }
        calculateTotal();
    }

    private void calculate_Percent(Double discountpercent){
        Double charges=0.0, disPercent = 0.0, cal_exchangerate=0.0;

        disPercent = discountpercent;
        if(txtCharges.getText().length() > 0 && (Double.valueOf(txtCharges.getText().toString()) != 0.00)) {
            Log.i(TAG, "charges " +txtCharges.getText().toString());
            charges = Double.valueOf(txtCharges.getText().toString());
            if(spnCurrencyType.getSelectedItemPosition()==0){//0 for MMK
                disPercent = (disPercent*charges)/100;
            }else if(spnCurrencyType.getSelectedItemPosition()==1){//1 for USD
                cal_exchangerate= disPercent*Double.parseDouble(txtExchangeRate.getText().toString());
                disPercent = (cal_exchangerate*charges)/100;
            }
            edtDiscountAmount.setText(String.format("%.2f", disPercent));
        }
        calculateTotal();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.waybill_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.edit){
            addWaybill();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showPro() {
        if(keywordType.equals("shipper"))
      shipperProgress.setVisibility(View.VISIBLE);
        else
            consigneeProgress.setVisibility(View.VISIBLE);

        //Toast.makeText(getContext(),"Wait",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideProg() {
        shipperProgress.setVisibility(View.GONE);
        consigneeProgress.setVisibility(View.GONE);
        shipperInfoLayout.setVisibility(View.GONE);
    }

    @Override
    public void showShipperData(List<ShipperModel> shipperData) {
        consigneeProgress.setVisibility(View.GONE);
        shipperProgress.setVisibility(View.GONE);

        if(keywordType.equals("shipper")) {

            if (0 < shipperData.size()) {
                showShipperList(shipperData);
                shipperList = shipperData;

            }
        }

       else if(keywordType.equals("consignee")){

           if(0 < shipperData.size()){
               showConsigneeList(shipperData);
               shipperList = shipperData;

           }
       }
       //

    }

    @Override
    public void KeywordError(String error) {
      shipperProgress.setVisibility(View.GONE);
      consigneeProgress.setVisibility(View.GONE);

        Toast.makeText(getContext(),error,Toast.LENGTH_LONG).show();
    }

    private void showShipperList(List<ShipperModel> list){
        item = new String[list.size()];
        for(int i=0;i<list.size();i++){
            item[i] = list.get(i).getCode() + "\n" + list.get(i).getName() + "\n" + list.get(i).getContactName() + list.get(i).getCompanyName() + "\n......................................\n" ;

        }
        shipperInfo = new ShipperInfoAdapter(getContext(),R.layout.shipperitem,list);
        edtShipperKeyword.setThreshold(2);
        edtShipperKeyword.setAdapter(shipperInfo);
    }
    private void showConsigneeList(List<ShipperModel> list){

        item = new String[list.size()];
        for(int i=0;i<list.size();i++){
            item[i] = list.get(i).getCode() + "\n" + list.get(i).getName() + "\n" + list.get(i).getContactName() + list.get(i).getCompanyName() + "\n......................................\n" ;

        }
        shipperInfo = new ShipperInfoAdapter(getContext(),R.layout.shipperitem,list);
        edtConsigneeKeyword.setThreshold(2);
        edtConsigneeKeyword.setAdapter(shipperInfo);
    }

}
