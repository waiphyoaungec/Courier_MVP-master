package com.sh.courier_mvp.view.fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.textfield.TextInputEditText;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
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

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import app.dinus.com.loadingdrawable.LoadingView;
import butterknife.BindView;
import butterknife.ButterKnife;


public class CreatePickupFragment extends Fragment implements MainContract.AddWayBillView,AdapterView.OnItemSelectedListener,View.OnClickListener {



    @BindView(R.id.pickup_spnPaymentType)
    Spinner spnPaymentType;

    @BindView(R.id.spn_dom_int)
    Spinner spnDomType;

    @BindView(R.id.pickup_documentType)
            Spinner spnDocType;

    @BindView(R.id.pickup_comtxtDestination)
    AutoCompleteTextView comtxtDestination;

    @BindView(R.id.pickup_comtxtRemoteDestination)
    AutoCompleteTextView comtxtRemoteDestination;

    @BindView(R.id.pickup_comtxtSource)
    AutoCompleteTextView comtxtSource;

    @BindView(R.id.linear_pickup_data)
    LinearLayout linearAllData;

    @BindView(R.id.pickup_comtxtRemoteSource)
    AutoCompleteTextView comtxtRemoteSource;

    @BindView(R.id.addPickup)
    Button addPickup;


    @BindView(R.id.pickup_edtShipperName)
    TextInputEditText edtShipperName;

    @BindView(R.id.pickup_edtCompanyName)
    TextInputEditText edtCompanyName;

    @BindView(R.id.pickup_edtContactName)
    TextInputEditText edtContactName;

    @BindView(R.id.pickup_edtAddress)
    TextInputEditText edtAddress;

    @BindView(R.id.pickup_edtPhone)
    TextInputEditText edtPhone;

    @BindView(R.id.pickup_edtEmail)
    TextInputEditText edtEmail;

    @BindView(R.id.pickup_edtShipperCode)
    TextInputEditText edtShipperCode;

    @BindView(R.id.pickup_edtRateCard)
    TextInputEditText pickup_edtRateCard;

    @BindView(R.id.ready_date)
    TextView ready_date;

    @BindView(R.id.ready_time)
    EditText ready_time;


    @BindView(R.id.pickup_by)
    EditText pickup_by;

    @BindView(R.id.remark_by)
    EditText remark_by;

    @BindView(R.id.pickup_errorView)
    RelativeLayout pickup_errorView;

    @BindView(R.id.pickup_try_again_text)
    TextView pickup_try_again_text;

    @BindView(R.id.pickup_try_again)
    Button pickup_try_again;


    @BindView(R.id.pickup_waybillloading)
    LoadingView loadingView;


    @BindView(R.id.pickup_txtkeyword)
    AutoCompleteTextView edtShipperKeyword;
    @BindView(R.id.pickup_shipperProgress)
    ProgressBar shipperProgress;
    @BindView(R.id.pickup_shipperInfo)
    RelativeLayout shipperInfoLayout;

    MainContract.MainPresenter mainPresenter;
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> remoteadapter;
    ArrayAdapter<String> sourceRemoteAdapter;
    ArrayAdapter<String> destinationRemoteAdapter;
    ShipperInfoAdapter shipperInfo;
    private String imageFilePath = "";
    private Map<String, String>[]  remoteFromList, remoteToList, customerList, paymentList;


    int doc_type = 0;
    int payment_type = 0;
    int remote_from;
    int remote_to;
    String dom_type = "";

    private String TAG = "CreateWaybillFragmenet";
    private String location_from = "";
    private String location_to = "";
    private String remote = "";

    private String shipper_code = "";

    private boolean poramount=false;
    private boolean first_bind = false;

    private List<ShipperModel> shipperList;
    private String [] item;

    private Typeface zawgyi;


    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_addpickup,container,false);
        ButterKnife.bind(this, view);
        Utility.page = 4;
        mainPresenter = new Presenter(this, getActivity());
        String token = AppData.GetToken(getContext());
        bindSpinner();

        mainPresenter.getLocation(token);
        mainPresenter.getPaymentType(AppData.GetToken(getContext()));
        mainPresenter.getCustomerType(AppData.GetToken(getContext()));

        first_bind = true;


        spnDocType.setOnItemSelectedListener(this);
        ready_date.setOnClickListener(this);
        ready_time.setOnClickListener(this);
        spnPaymentType.setOnItemSelectedListener(this);
        spnDomType.setOnItemSelectedListener(this);
        mainPresenter.getShipperInfo(
                "ygn"
        );
        showDomtype();

        zawgyi = Typeface.createFromAsset(getContext().getAssets(),"zawgyi.TTF");

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
                if(shipperList!=null ){
                    shipperInfoLayout.setVisibility(View.VISIBLE);
                    edtShipperKeyword.setText(shipperList.get(position).getCode());
                    edtShipperCode.setText(shipperList.get(position).getCode());
                    edtShipperName.setText(shipperList.get(position).getName());
                    edtCompanyName.setText(shipperList.get(position).getCompanyName());
                    edtContactName.setText(shipperList.get(position).getCompanyName());
                    edtAddress.setText(shipperList.get(position).getMailingAddr());
                    edtPhone.setText(shipperList.get(position).getMailingPhone());
                    edtEmail.setText(shipperList.get(position).getMailingEmail());
                    pickup_edtRateCard.setText(shipperList.get(position).getRateCard());
                }

            }
        });
        comtxtDestination.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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







        edtShipperKeyword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

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




        addPickup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                addWaybill();
                Toast.makeText(getContext(),"hey",Toast.LENGTH_LONG).show();
            }
        });
        setHasOptionsMenu(true);
        return view;
    }

    private void addWaybill(){

        if(location_from.equals("") || location_to.equals("")){
            comtxtSource.setError("Please Select Location");
            showError("Please Select Location!");
        }
        else if(doc_type == 0 || payment_type == 0){
            showError("Please choose document or payment type.");
        }
        else if(dom_type.length() == 0 || dom_type.equals("DOM Type")){
            showError("Please Choose Dom type");
        }
        else if(edtShipperCode.getText().toString().trim().length() == 0){
            edtShipperCode.setError("");
            showError("Please Enter ShipperCode");
        }
        else if(edtShipperName.getText().length()==0){
            edtShipperName.setError("");
            showError("Please Enter ShipperName");
        }else if(edtAddress.getText().length()==0){
            edtAddress.setError("");
            showError("Please Enter Shipper Address");
        }
        else if(edtCompanyName.getText().toString().trim().length() == 0){
            edtCompanyName.setError("");
            showError("Please Enter Shipper Company Name");
        }
        else if(edtContactName.getText().toString().trim().length() == 0){
            edtContactName.setError("");
            showError("Please Enter Shipper Contact Name");
        }
        else if(edtPhone.getText().length()==0){
            edtPhone.setError("");
            showError("Please Enter Shipper Phone");
        }
        else if(edtEmail.getText().length() == 0){
            edtEmail.setError("");
            showError("Please Enter Shipper Email");
        }
        else if(ready_date.getText().toString().equals("Choose Ready Date")){
            ready_date.setError("");
            showError("Please Choose Ready Date");
        }
        else if(ready_time.getText().toString().trim().length()==0){
            ready_time.setError("");
            showError("Please Choose Ready Time");
        }
        else if(pickup_by.getText().toString().trim().length() == 0){
            pickup_by.setError("");
            showError("Please Choose Pickup by");
        }
        else if(remark_by.getText().toString().trim().length() == 0){
            remark_by.setError("");
            showError("Please Choose Remark by");
        }
        else {
            showProgress();
            shipper_code = edtShipperCode.getText().toString();

            mainPresenter.addPickup(AppData.GetToken(getContext()),location_from,location_to,remote_from,remote_to,dom_type,
                    doc_type,shipper_code,edtShipperName.getText().toString(),edtCompanyName.getText().toString(),edtContactName.getText().toString(),
                    edtAddress.getText().toString(),edtPhone.getText().toString(),ready_time.getText().toString(),ready_date.getText().toString(),pickup_by.getText().toString(),
                    remark_by.getText().toString(),payment_type);



        }
    }








    @Override
    public void showProgress() {
        loadingView.setVisibility(View.VISIBLE);
        linearAllData.setVisibility(View.GONE);
    }

    @Override
    public void LoadError(String error) {
        Toast.makeText(getContext(),"Loading error..",Toast.LENGTH_SHORT).show();
        pickup_errorView.setVisibility(View.VISIBLE);
        loadingView.setVisibility(View.GONE);
        pickup_try_again_text.setText("\n" + error + "\n");
        pickup_try_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickup_errorView.setVisibility(View.GONE);
                loadingView.setVisibility(View.VISIBLE);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content, new CreatePickupFragment());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }
        });

    }

    @Override
    public void hideProgress() {
        loadingView.setVisibility(View.GONE);

        linearAllData.setVisibility(View.VISIBLE);
        pickup_errorView.setVisibility(View.GONE);

       // progressBar.setVisibility(View.GONE);
    }



    @Override
    public void bindSpinner() {
        ArrayAdapter<CharSequence> docAdapter = ArrayAdapter
                .createFromResource(getActivity(), R.array.doc_type,
                        android.R.layout.simple_spinner_item);
        docAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnDocType.setAdapter(docAdapter);

    }

    @Override
    public void showLocation(String[] list) {
        Log.i(TAG, "location "+ list);
        List<String> locationList = Arrays.asList(list);
        adapter = new LocationAdapter(getContext(),R.layout.shipperitem,locationList,zawgyi);

        comtxtSource.setThreshold(1);
        comtxtSource.setAdapter(adapter);
        comtxtSource.setTypeface(zawgyi);

        remoteadapter = new LocationAdapter(getContext(),R.layout.shipperitem,locationList,zawgyi);
        comtxtDestination.setThreshold(1);
        comtxtDestination.setAdapter(remoteadapter);
        comtxtDestination.setTypeface(zawgyi);
        hideProgress();
    }

    @Override
    public void showCustomerType(Map<String, String>[] list) {
        customerList = list;

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
    public void showDomtype() {
        ArrayAdapter<String> paymentAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.dom_type));
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

    }

    @Override
    public void showRemoteCharges(String charges) {

    }

    @Override
    public void showPickupData(Pickupdetailresponse pickupdetailresponse) {
        Toast.makeText(getContext(),pickupdetailresponse.getCode(),Toast.LENGTH_LONG).show();
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
        loadingView.setVisibility(View.GONE);
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

                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.content, new HomeFragment());
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


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {

            case R.id.pickup_documentType:

                if(parent.getSelectedItemPosition() == 1){
                    doc_type = 20;
                }else{
                    doc_type = 21;
                }
                Log.i(TAG, "doc "+doc_type);
                break;
            case R.id.pickup_spnPaymentType:
                String payment = parent.getSelectedItem().toString();
                if(paymentList!=null)
                for (Map<String, String> value : paymentList) {
                    if (value.get("name").equals(payment)) {

                        payment_type = Integer.parseInt(value.get("id"));
                        Log.d("testtest", "hello" + value.get("name") + " " + payment_type);
                    }
                }
                Log.i(TAG, "payment_type "+ payment_type);
                break;
            case R.id.spn_dom_int:
                dom_type = parent.getSelectedItem().toString();

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
        shipperProgress.setVisibility(View.VISIBLE);

        //Toast.makeText(getContext(),"Wait",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideProg() {
        shipperProgress.setVisibility(View.GONE);
        shipperInfoLayout.setVisibility(View.GONE);
    }

    @Override
    public void showShipperData(List<ShipperModel> shipperData) {
        shipperProgress.setVisibility(View.GONE);

        if(shipperData != null && 0 < shipperData.size()){
            shipperList = shipperData;
            showShipperList(shipperList);


        }
        //

    }

    @Override
    public void KeywordError(String error) {
       Toast.makeText(getContext(),"Cannot found keyword",Toast.LENGTH_SHORT).show();
    }



    void showShipperList(List<ShipperModel> list) {
        if (list != null) {
            item = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                item[i] = list.get(i).getCode() + "\n" + list.get(i).getName() + "\n" + list.get(i).getContactName() + list.get(i).getCompanyName() + "\n......................................\n";

            }
            shipperInfo = new ShipperInfoAdapter(getContext(), R.layout.shipperitem, list);
            edtShipperKeyword.setThreshold(1);
            edtShipperKeyword.setAdapter(shipperInfo);
            shipperInfo.notifyDataSetChanged();
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case  R.id.ready_date : {
                final Calendar c = Calendar.getInstance();

                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                if(monthOfYear<10){
                                    ready_date.setText(year +"-0" + monthOfYear + "-" + dayOfMonth);
                                }
                                if(dayOfMonth<10){
                                    ready_date.setText(year +"-" + monthOfYear + "-0" + dayOfMonth);
                                }
                                ready_date.setText(year +"-" + monthOfYear + "-" + dayOfMonth);


                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.setTitle("Select Ready Date");
                datePickerDialog.show();
            }
            break;


        }
    }

}
