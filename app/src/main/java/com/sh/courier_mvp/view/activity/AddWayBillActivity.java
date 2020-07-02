package com.sh.courier_mvp.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.sh.courier_mvp.R;
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
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import app.dinus.com.loadingdrawable.LoadingView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AddWayBillActivity extends AppCompatActivity implements MainContract.AddWayBillView, AdapterView.OnItemSelectedListener {
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
    /*@BindView(R.id.edtDiscount)
    EditText edtDiscountPresent;*/
    @BindView(R.id.txtDiscountPercentage)
    TextView txtDiscountPercentage;
    /*@BindView(R.id.edtDeclareValue)
    EditText edtDeclareValue;*/
    @BindView(R.id.edtAdditionalAmt)
    EditText edtAdditionalAmt;
    @BindView(R.id.edtPieces)
    EditText edtPieces;
    @BindView(R.id.edtCommericalTax)
    EditText edtCommericalTax;
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
    @BindView(R.id.edtAddress)
    TextInputEditText edtAddress;
    @BindView(R.id.edtPhone)
    TextInputEditText edtPhone;

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
    @BindView(R.id.txtShipper)
    TextView txtShipper;
    @BindView(R.id.txtDimensionWeight)
    TextView txtDimensionWeight;

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
    @BindView(R.id.linearShipper)
    RelativeLayout linearShipper;
    @BindView(R.id.twopercentdisclayout)
    LinearLayout linearTwoPercentUser;
    @BindView(R.id.waybillloading)
    LoadingView loadingView;
    @BindView(R.id.pbcalculate)
    ProgressBar progressBar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private final String TAG = "AddWayBillActivity";
    private int customer_type = 0;
    int doc_type = 0;
    int payment_type = 0;
    int remote_from;
    int remote_to;
    int id;
    int duty_tax = 0;
    int service_type;
    int cur_id;
    int pieces;
    MainContract.MainPresenter mainPresenter;
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> remoteadapter;
    ArrayAdapter<String> sourceRemoteAdapter;
    ArrayAdapter<String> destinationRemoteAdapter;
    private String remote = "";
    private String token;
    private String location_from = "";
    private String location_to = "";
    private String shipper_code = "";
    private String shipper = "";
    private String shipper_company_name = "";
    private String shipper_contact_name = "";
    private String shipper_addr = "";
    private String shipper_phone = "";
    private String img = "";
    private String mode;
    private String pickup_id = "";
    private double gross_weight;
    private double length;
    private double width;
    private double height;
    private double item_amt;
    private double dis_amt;
    private double online_charges;
    private double additional_amt;
    private double received_amt;
    private double total_amt;
    private double actual_weight;
    private double cur_rate;
    private double weight;
    private static final int REQUEST_IMAGE = 100;
    private String ratecard_id = "0";

    private String imageFilePath = "";
    private Map<String, String>[]  remoteFromList, remoteToList, customerList, paymentList;
    private Pickupdetailresponse pickupdetailresponse;
    private Double global_charges = 0.0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addwaybill);
        ButterKnife.bind(this);

        linearShipper.setVisibility(View.GONE);
        txtShipper.setVisibility(View.GONE);
        mainPresenter = new Presenter(this, this);
        mainPresenter.getLocation(AppData.GetToken(getApplicationContext()));
        mainPresenter.getPaymentType(AppData.GetToken(getApplicationContext()));
        mainPresenter.getCustomerType(AppData.GetToken(getApplicationContext()));
        bindSpinner();

        token = AppData.GetToken(getApplicationContext());
        Intent intent = getIntent();

        this.mode = intent.getStringExtra("MODE");
        if(this.mode.equalsIgnoreCase("NEW")) {
            this.pickup_id = intent.getStringExtra("PICKUP_ID");
            mainPresenter.getPickUpforWaybill(token,pickup_id);
            mainPresenter.getPickupNotiCount(AppData.GetToken(getApplicationContext()));
        }

        spnCustomerType.setOnItemSelectedListener(this);
        spnDocType.setOnItemSelectedListener(this);
        spnPaymentType.setOnItemSelectedListener(this);
        spnCurrencyType.setOnItemSelectedListener(this);
        spnRateCard.setOnItemSelectedListener(this);
        comtxtSource.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                remote = "FROM";
                //String[] lf = adapter.getItem(position).split(" - ", 0);//substring

                location_from = adapter.getItem(position).substring(0, 3);
                Log.d("getft", location_from);
                mainPresenter.getRemoteArea(AppData.GetToken(getApplicationContext()), location_from);
            }
        });
        comtxtDestination.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                remote = "TO";
                //String[] lt = remoteadapter.getItem(position).split(" - ", 0);
                location_to = remoteadapter.getItem(position).substring(0,3);
                Log.d("geft",location_to);
                mainPresenter.getRemoteArea(AppData.GetToken(getApplicationContext()), location_to);
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
                mainPresenter.getRemoteCharges(token, remote_to, remote_from);
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
                mainPresenter.getRemoteCharges(token, remote_to, remote_from);
            }
        });
        edtDiscountAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(edtDiscountAmount.isFocused()){
                    if(s.length()==0){
                        calculate_Amount(0.0);
                    }else {
                        calculate_Amount(Double.parseDouble(s.toString()));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtCommericalTax.addTextChangedListener(new TextWatcher() {
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
        edtItemAmount.addTextChangedListener(new TextWatcher() {
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
                    }else{
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
        imageButtonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scan();
            }
        });
        imgWayBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
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
        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_white_18dp);
        toolbar.setTitle("AddWayBill");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
        }else if(edtConsignee.getText().length()==0){
            edtConsignee.setError("");
            showError("Please Enter ConsigneeName");
        }else if(edtConsigneeAddr.getText().length()==0){
            edtConsigneeAddr.setError("");
            showError("Please Enter ConsigneeAddr");
        }else if(edtConsigneePhone.getText().length()==0){
            edtConsigneePhone.setError("");
            showError("Please Enter ConsigneePhno");
        }
        else{
            showProgress();
            String waybill_code;
            String consignee_company_name = edtConsigneeCompanyName.getText().toString();
            String consignee_addr = edtConsigneeAddr.getText().toString();
            String consignee_contact_name = edtConsigneeContactName.getText().toString();
            String consignee = edtConsignee.getText().toString();
            String consignee_phone = edtConsigneePhone.getText().toString();
            waybill_code = edtWayBillCode.getText().toString();
            double area_charges = txtRemoteCharges.getText().toString().equals("") ? 0.0 : Double.valueOf(txtRemoteCharges.getText().toString());
            double charges = txtCharges.getText().toString().equals("") ? 0.0 : Double.valueOf(txtCharges.getText().toString());
            //private int received_amt_cur_id;
            double commercial_tax = edtCommericalTax.getText().toString().equals("") ? 0.0 : Double.valueOf(edtCommericalTax.getText().toString());
            double service_amt = txtServiceAmount.getText().toString().equals("") ? 0.0 : Double.valueOf(txtServiceAmount.getText().toString());
            //dis_amt = edtDiscountAmount.getText().toString().equals("") ? 0.0 : Double.valueOf(edtDiscountAmount.getText().toString());
            double dis_percent = txtDiscountPercentage.getText().toString().equals("") ? 0.0 : Double.valueOf(txtDiscountPercentage.getText().toString());
            /*if(edtItemAmount.getText().length() > 0)
                item_amt = edtItemAmount.getText().toString().equals("") ? 0.0 : Double.valueOf(edtItemAmount.getText().toString());*/
            showProgress();
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
            /*Log.i(TAG, "token "+token+ " /nlocation_from "+location_from+
                    " /nlocation_to "+location_to+" /nremote_from "+remote_from+
                    " /nremote_to "+remote_to+ " /nwaybill_code "+ waybill_code + " /ncustomer_type "+customer_type+
                    " /ndoc_type "+doc_type+ "/description "+
                     " /declare_value "+ declare_value + " /shipper_code "+ shipper_code+  " /shipper "+ shipper+
                    " /shipper_company_name "+shipper_company_name+ " /shipper_contact_name "+shipper_contact_name+ " /shipper_addr "+shipper_addr+
                    " /shipper_phone "+ shipper_phone+ " /shipper_email "+ " /consignee_code "+ consignee +
                    " /consignee_company_name "+consignee_company_name+ " /consignee_contact_name "+consignee_contact_name+ " /consignee_addr "+consignee_addr+
                    " /consignee_phone "+ consignee_phone+ " /consignee_email "+ consignee_email+ " /remark "+ remark+ payment_type+ duty_tax+ service_type+ gross_weight+ length+ width+
                    " /height "+ height+ " /actual_weight "+ actual_weight+ " /pieces "+  pieces+ " /cur_id "+ cur_id+ " /cur_rate "+cur_rate+
                    " /item_amt "+ item_amt+ " /service_amt "+ service_amt+ " /charges "+charges+ "/commercial_tax "+commercial_tax+
                    "/dis_percent "+dis_percent+ "/dis_amt "+dis_amt+ "/area_charges "+area_charges+ "/total_amt "+total_amt+
                    "/pickup_id "+pickup_id+ "/service_alert "+ "/img "+img+ "/mode "+ mode+ "/id "+ pickup_id+"");*/
            String consignee_email = "";
            String remark = "";
            mainPresenter.addWayBill(token, location_from,location_to,remote_from, remote_to, waybill_code, customer_type, doc_type,
                    "", 0.0, shipper_code, shipper, shipper_company_name, shipper_contact_name, shipper_addr,
                    shipper_phone, "", consignee, consignee_company_name, consignee_contact_name, consignee_addr,
                    consignee_phone, consignee_email, remark, payment_type, duty_tax, service_type, gross_weight, length, width,
                    height, actual_weight, pieces, cur_id, cur_rate, item_amt, service_amt, charges, commercial_tax,
                    dis_percent, dis_amt, area_charges, online_charges, additional_amt, received_amt, cur_id, total_amt, pickup_id, "", img, mode, id, ratecard_id,
                    "",two_percent);
        }
    }

    private void scan(){
        IntentIntegrator scanIntegrator = new IntentIntegrator(this);
        scanIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        scanIntegrator.setPrompt("Scan a barcode");
        scanIntegrator.setCameraId(0);
        scanIntegrator.setOrientationLocked(false);
        scanIntegrator.setBeepEnabled(true);
        scanIntegrator.setBarcodeImageEnabled(true);
        scanIntegrator.initiateScan();
    }

    private void dispatchTakePictureIntent() {
        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (pictureIntent.resolveActivity(getPackageManager()) != null) {

            File photoFile;
            try {
                photoFile = createImageFile();
            }
            catch (IOException e) {
                e.printStackTrace();
                return;
            }
            Uri photoUri = FileProvider.getUriForFile(this, getPackageName() +".provider", photoFile);
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            startActivityForResult(pictureIntent, REQUEST_IMAGE);
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
        }
        else{
            if(edtLength.getText().length() > 0)
                length = edtLength.getText().toString().equals("") ? 0.0 :  Double.parseDouble(edtLength.getText().toString());
            if(edtWidth.getText().length() > 0)
                width = edtWidth.getText().toString().equals("") ? 0.0 :  Double.parseDouble(edtWidth.getText().toString());
            if(edtHeight.getText().length() > 0)
                height = edtHeight.getText().toString().equals("") ? 0.0 :  Double.parseDouble(edtHeight.getText().toString());
            actual_weight = length * width * height/6000;
            Log.i(TAG, "dimension "+ actual_weight);
            txtDimensionWeight.setText(actual_weight + " kg");
            if(edtGrossWeight.getText().length() > 0)
                gross_weight = edtGrossWeight.getText().toString().equals("") ? 0.0 :  Double.parseDouble(edtGrossWeight.getText().toString());
            if(shipper_code == null){
                shipper_code = "";
            }
            mainPresenter.getRateCard(token,location_from, location_to, String.valueOf(gross_weight), String.valueOf(actual_weight), String.valueOf(customer_type), String.valueOf(payment_type), String.valueOf(cur_id), shipper_code, ratecard_id);
        }
        //showProgress();
    }

    private void calculateTotal(){
        double serviceAmt = 0, charges = 0, tax = 0, remote_charges = 0, othercharges = 0, twopercentdisc=0.0;
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
                    total_amt = (charges + tax + remote_charges) - dis_amt ;
                    String amt = getString(R.string.dollor) +total_amt;
                    txtTotal.setText(amt);
                }
            }else{//for MMK
                if(customer_type == 2){// for online customer with other charges
                    total_amt = (serviceAmt+ othercharges + remote_charges + twopercentdisc) ;
                    String amt = total_amt+ getString(R.string.mmk);
                    txtTotal.setText(amt);
                }else{
                    total_amt = (charges + tax + remote_charges)- dis_amt ;
                    String amt = total_amt+ getString(R.string.mmk);
                    txtTotal.setText(amt);
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent in) {
        super.onActivityResult(requestCode, resultCode, in);
        if (resultCode == Activity.RESULT_OK) {

            IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, in);
            if (scanResult != null) {
                String contents = in.getStringExtra("SCAN_RESULT");
                edtWayBillCode.setText(contents);
            }else{
                Log.e("SEARCH_EAN", "IntentResult je NULL!");
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Log.e("SEARCH_EAN", "CANCEL");
            Toast.makeText(this, "You cancelled the operation", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(this, "You cancelled the operation", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private File createImageFile() throws IOException{

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        imageFilePath = image.getAbsolutePath();

        return image;
    }

    @Override
    public void showProgress() {
        loadingView.setVisibility(View.VISIBLE);
        linearAllData.setVisibility(View.GONE);
    }

    @Override
    public void LoadError(String error) {

    }

    @Override
    public void hideProgress() {
        loadingView.setVisibility(View.GONE);
        linearAllData.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void bindSpinner() {
        ArrayAdapter<CharSequence> docAdapter = ArrayAdapter
                .createFromResource(this, R.array.doc_type,
                        android.R.layout.simple_spinner_item);
        docAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnDocType.setAdapter(docAdapter);

        ArrayAdapter<CharSequence> currencyAdapter = ArrayAdapter
                .createFromResource(this, R.array.currency_type,
                        android.R.layout.simple_spinner_item);
        currencyAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCurrencyType.setAdapter(currencyAdapter);
    }

    @Override
    public void showLocation(String[] list) {
        Log.d("locationList", new Gson().toJson(list));
        adapter = new ArrayAdapter<>
                (this, android.R.layout.select_dialog_item, list);

        comtxtSource.setThreshold(1);
        comtxtSource.setAdapter(adapter);

        remoteadapter = new ArrayAdapter<>
                (this, android.R.layout.select_dialog_item, list);
        comtxtDestination.setThreshold(1);
        comtxtDestination.setAdapter(remoteadapter);
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
        ArrayAdapter<String> customerAdapter = new ArrayAdapter<>(this,
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
        ArrayAdapter<String> paymentAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, item);
        spnPaymentType.setAdapter(paymentAdapter);
    }

    @Override
    public void showRemoteLocation(Map<String, String>[] list) {
        Log.d("remotelocation", new Gson().toJson(list));
        if(list == null){
            Toast.makeText(this, "no remote location", Toast.LENGTH_LONG).show();
        }else{
            String [] item = new String[list.length];
            for(int i = 0; i < list.length; i++){
                item[i] = list[i].get("name");
            }
            if(remote.equals("FROM")){
                remoteFromList = list;
                sourceRemoteAdapter = new ArrayAdapter<>
                        (this,android.R.layout.select_dialog_item, item);
                comtxtRemoteSource.setThreshold(1);
                comtxtRemoteSource.setAdapter(sourceRemoteAdapter);
            }else {
                remoteToList = list;
                destinationRemoteAdapter = new ArrayAdapter<>
                        (this,android.R.layout.select_dialog_item, item);
                comtxtRemoteDestination.setThreshold(1);
                comtxtRemoteDestination.setAdapter(destinationRemoteAdapter);
            }
        }

    }

    @Override
    public void showRate(Map<String,String> response) {
        if(response == null){
            Toast.makeText(this, "No Rate Card!", Toast.LENGTH_LONG).show();
        }else {
            String amount = response.get("amt");
            String exchange_rate = response.get("cur_rate");
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
            if (pickupdetailresponse.getIsPercent().equals("1")) {
                //txtDiscountPercentage.setText(pickupdetailresponse.getDisPercent());
                double disamount = (Double.parseDouble(amount) / 100) * Double.parseDouble(pickupdetailresponse.getDisPercent());
                //edtDiscountAmount.setText(String.valueOf(disamount));
            } else {
                if(actual_weight > gross_weight){
                    weight = actual_weight;
                }else{
                    weight = gross_weight;
                }
//                if (weight > 5) {
//                    edtDiscountAmount.setText(pickupdetailresponse.getDisValue2());
//                } else {
//                    edtDiscountAmount.setText(pickupdetailresponse.getDisValue1());
//                }
                //txtDiscountPercentage.setText(String.format("%.2f", (Double.parseDouble(pickupdetailresponse.getDisValue1()) / Double.parseDouble(amount)) * 100));
            }
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
        this.pickupdetailresponse = pickupdetailresponse;
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
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, years);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnRateCard.setAdapter(adapter);
        if (customer_type != 2) {

            if (pickupdetailresponse.getIsPercent().equals("1")) {
                Log.i(TAG, "diper " + pickupdetailresponse.getDisPercent());
                //txtDiscountPercentage.setText(pickupdetailresponse.getDisPercent());
            } else {
                Log.i(TAG, "disval " + pickupdetailresponse.getDisPercent());
//                if (weight > 5) {
//                    edtDiscountAmount.setText(pickupdetailresponse.getDisValue2());
//                } else {
//                    edtDiscountAmount.setText(pickupdetailresponse.getDisValue1());
//                }
            }
        }
    }

    @Override
    public void showError(String message){
        new FancyAlertDialog.Builder(this)
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
    public void showPro() {

    }

    @Override
    public void hideProg() {

    }

    @Override
    public void showShipperData(List<ShipperModel> shipperData) {

    }

    @Override
    public void KeywordError(String error) {

    }

    @Override
    public void showMessage(String message) {
        new FancyAlertDialog.Builder(this)
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
                        Intent intent = new Intent(AddWayBillActivity.this, WayBillListByPickUpActivity.class);
                        intent.putExtra("id", pickup_id);
                        startActivity(intent);
                        finish();
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
                if(parent.getSelectedItemPosition() == 1){
                    doc_type =20;
                }else{
                    doc_type =21;
                }
                Log.i(TAG, "doc "+doc_type);
                break;
            case R.id.spnPaymentType:
                String payment = parent.getSelectedItem().toString();
                for (Map<String, String> value : paymentList) {
                    if (value.get("name").equals(payment)) {
                        payment_type = Integer.parseInt(value.get("id"));
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
                    ratecard_id = pickupdetailresponse.getRatecard().getItem().get(position - 1).getRatecardId();
                    Log.i(TAG, "ratecard "+ ratecard_id);
                    edtLength.requestFocus();
                }else{
                    ratecard_id = "1";
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.waybill_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.edit){
            addWaybill();
        }
        return super.onOptionsItemSelected(item);
    }
}