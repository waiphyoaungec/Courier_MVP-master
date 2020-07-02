package com.sh.courier_mvp.view.fragment;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.sh.courier_mvp.R;
import com.sh.courier_mvp.util.Utility;
import com.sh.courier_mvp.adapter.FragmentCommunication;
import com.sh.courier_mvp.adapter.WaybillAdapater;
import com.sh.courier_mvp.data.AppData;
import com.sh.courier_mvp.model.Delivery;
import com.sh.courier_mvp.model.Exist;
import com.sh.courier_mvp.model.Notexist;
import com.sh.courier_mvp.model.Task;
import com.sh.courier_mvp.presenter.MainContract;
import com.sh.courier_mvp.presenter.Presenter;
import com.shashank.sony.fancydialoglib.Animation;
import com.shashank.sony.fancydialoglib.FancyAlertDialog;
import com.shashank.sony.fancydialoglib.FancyAlertDialogListener;
import com.shashank.sony.fancydialoglib.Icon;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import app.dinus.com.loadingdrawable.LoadingView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class WayBillListFragment extends Fragment implements MainContract.DeliveryWBView{
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.recycler_exist)
    RecyclerView recycler_exist;
    /*@BindView(R.id.recycler_notexist)
    RecyclerView recycler_notexist;*/
    @BindView(R.id.btnDownload)
    Button btnDownload;
    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.txtExist)
    TextView txtExist;
    @BindView(R.id.btnSend)
    Button btnSend;
    @BindView(R.id.edtWayBillCode2)
    EditText edtWayBillCode2;
    @BindView(R.id.imgbtnScan2)
    ImageButton imgbtnScan2;
    private WaybillAdapater adapter;
    MainContract.MainPresenter mainPresenter;
    String TAG = "WayBillListFragment";
    //Pickuplistresponse data;
    List<Task> printedData;
    @BindView(R.id.loading)
    LoadingView loadingView;
    public static final String FONT = "res/raw/zg.TTF";
    private ArrayList<String> waybilllist;
    private ArrayList<Map<String,String>> waybillcode;
    private String downloadPdflink;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_waybill_list,container,false);
        ButterKnife.bind(this,view);

        mainPresenter = new Presenter(this, getActivity());
        Utility.page = 2;


        txtTitle.setVisibility(View.GONE);
        txtExist.setVisibility(View.GONE);
        btnSend.setVisibility(View.GONE);
        btnDownload.setVisibility(View.GONE);

        /*if(MyNetwork.isNetworkConnected(getContext())){
            mainPresenter.getWayBillList(AppData.GetToken(getActivity().getApplicationContext()));
        }else {
            Toast.makeText(getActivity(), "No Internet Connection!", Toast.LENGTH_LONG).show();
        }*/
        //mainPresenter.getPickupNotiCount(AppData.GetToken(getActivity().getApplicationContext()));
        loadingView.setVisibility(View.GONE);
        waybillcode = new ArrayList<>();
        waybilllist =  new ArrayList<>();
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    DownloadPdf(downloadPdflink);
                }
                catch (Exception e){
                    Toast.makeText(getContext(),"invalid",Toast.LENGTH_LONG).show();
                }

//                if(printedData == null){
//                    showError("No way bill. Please scan or enter waybill code.");
//                }else {
//                    DownloadPdf(downloadPdflink);
//                    //createandDisplayPdf("Delivery Sheet", printedData);
//                }

            }
        });

        adapter = new WaybillAdapater(getActivity(), waybillcode, 0);
        recycler_view.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));
        recycler_view.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayout.VERTICAL));
        recycler_view.setAdapter(adapter);
        adapter.onRemoveListener(new WaybillAdapater.onRemove() {
            @Override
            public void onremove(int position) {
                waybillcode.remove(position);
                waybilllist.remove(position);
                adapter.notifyDataSetChanged();
            }
        });
        imgbtnScan2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scan();
            }
        });
        edtWayBillCode2.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    Map<String,String> map = new HashMap<>();
                    map.put("code",edtWayBillCode2.getText().toString());
                    map.put("status", "0");
                    waybillcode.add(map);
                    waybilllist.add(edtWayBillCode2.getText().toString());
                    adapter.notifyDataSetChanged();
                    txtTitle.setVisibility(View.VISIBLE);
                    btnSend.setVisibility(View.VISIBLE);
                    edtWayBillCode2.setText("");
                    return true;
                }
                return false;
            }
        });
        edtWayBillCode2.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if (keyCode == EditorInfo.IME_ACTION_DONE) {
                    // Perform action on key press
                    Map<String,String> map = new HashMap<>();
                    map.put("code",edtWayBillCode2.getText().toString());
                    map.put("status", "0");
                    waybillcode.add(map);
                    waybilllist.add(edtWayBillCode2.getText().toString());
                    adapter.notifyDataSetChanged();
                    txtTitle.setVisibility(View.VISIBLE);
                    btnSend.setVisibility(View.VISIBLE);
                    edtWayBillCode2.requestFocus();
                    return true;
                }
                return false;
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "size "+ waybillcode.size());
                if(waybillcode.size() == 0)
                    showError("No way bill. Please scan or enter waybill code.");
                else{
                    Log.i(TAG, new Gson().toJson(waybilllist));
                    hideKeyboard(getActivity());
                    mainPresenter.deliveryroutefix(AppData.GetToken(Objects.requireNonNull(getContext())),waybilllist);
                }

            }
        });
        return view;
    }

    void showError(String message){
        new FancyAlertDialog.Builder(getActivity())
                .setTitle("Show status")
                .setBackgroundColor(Color.parseColor("#63341a"))  //Don't pass R.color.colorvalue
                .setMessage(message)
                .setPositiveBtnBackground(Color.parseColor("#e12425"))  //Don't pass R.color.colorvalue
                .setPositiveBtnText("OK")
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
                edtWayBillCode2.setText(contents);
                waybilllist.add(edtWayBillCode2.getText().toString());
                Map<String,String> map = new HashMap<>();
                map.put("code",edtWayBillCode2.getText().toString());
                map.put("status", "0");
                waybillcode.add(map);
                adapter.notifyDataSetChanged();
                txtTitle.setVisibility(View.VISIBLE);
                btnSend.setVisibility(View.VISIBLE);
                edtWayBillCode2.setText("");
            }else{
                Log.e("SEARCH_EAN", "IntentResult je NULL!");
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Log.e("SEARCH_EAN", "CANCEL");
            Toast.makeText(getActivity(), "You cancelled the operation", Toast.LENGTH_SHORT).show();
        }
    }

    public void createandDisplayPdf(String text, List<Task> printedData) {

        Document doc = new Document();

        try {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Dir";

            File dir = new File(path);
            if(!dir.exists())
                dir.mkdirs();

            File file = new File(dir, "waybill_list.pdf");
            FileOutputStream fOut = new FileOutputStream(file);

            PdfWriter.getInstance(doc, fOut);
            //open the document
            doc.open();

            Paragraph p1 = new Paragraph(text);
            Font paraFont= new Font(Font.getFamily(""));
            p1.setAlignment(Paragraph.ALIGN_CENTER);
            p1.setFont(paraFont);
            doc.add(p1);
            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            String formattedDate = df.format(c);
            Paragraph p4 = new Paragraph(formattedDate);
            p4.setAlignment(Paragraph.ALIGN_RIGHT);
            p4.setFont(paraFont);

            doc.add(p4);
            Paragraph p2 = new Paragraph("Route No: ....................");
            p2.setFont(paraFont);
            p2.setSpacingBefore(20);
            doc.add(p2);
            Paragraph p3 = new Paragraph("Courier:   ....................");
            p3.setFont(paraFont);
            p3.setSpacingBefore(20);
            doc.add(p3);

            Font f = FontFactory.getFont(FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            PdfPTable table = new PdfPTable(12);
            table.addCell(new PdfPCell(new Paragraph("No")));
            table.addCell(new PdfPCell(new Paragraph("Way Bill No.")));
            table.addCell(new PdfPCell(new Paragraph("Sender")));


            table.addCell(new PdfPCell(new Paragraph("Weight")));
            table.addCell(new PdfPCell(new Paragraph("Pieces")));
            table.addCell(new PdfPCell(new Paragraph("Consignee")));
            table.addCell(new PdfPCell(new Paragraph("Address")));

            table.addCell(new PdfPCell(new Paragraph("Phone")));
            table.addCell(new PdfPCell(new Paragraph("Pay. Type")));
            table.addCell(new PdfPCell(new Paragraph("Amount")));

            table.addCell(new PdfPCell(new Paragraph("Signature")));
            table.addCell(new PdfPCell(new Paragraph("Time")));

            for(int i=0; i<printedData.size(); i++){
                String no = ""+(i+1);
                table.addCell(new PdfPCell(new Paragraph(no)));
                table.addCell(new PdfPCell(new Paragraph(printedData.get(i).getCode(), f)));
                table.addCell(new PdfPCell(new Paragraph(printedData.get(i).getShipper(), f)));

                table.addCell(new PdfPCell(new Paragraph(printedData.get(i).getGross_weight(), f)));
                table.addCell(new PdfPCell(new Paragraph(printedData.get(i).getPieces(), f)));
                table.addCell(new PdfPCell(new Paragraph(printedData.get(i).getConsignee(), f)));
                table.addCell(new PdfPCell(new Paragraph(printedData.get(i).getConsignee_addr(), f)));

                table.addCell(new PdfPCell(new Paragraph(printedData.get(i).getConsignee_phone(), f)));
                table.addCell(new PdfPCell(new Paragraph(printedData.get(i).getPayment_type(), f)));
                table.addCell(new PdfPCell(new Paragraph(printedData.get(i).getTotal_amt(), f)));

                table.addCell(new PdfPCell(new Paragraph("")));
                table.addCell(new PdfPCell(new Paragraph("")));
            }
            table.setSpacingBefore(20);
            doc.add(table);

        } catch (DocumentException de) {
            Log.e("PDFCreator", "DocumentException:" + de);
        } catch (IOException e) {
            Log.e("PDFCreator", "ioException:" + e);
        }
        finally {
            doc.close();
        }

        viewPdf("Dir");
    }

    public void DownloadPdf(String pdfurl){
        Intent pdfdownload = new Intent(Intent.ACTION_VIEW);
        pdfdownload.setDataAndType(Uri.parse(pdfurl), "application/pdf");
        startActivity(pdfdownload);
    }

    // Method for opening a pdf file
    private void viewPdf(String file) {

        File pdfFile = new File(Environment.getExternalStorageDirectory() + "/" + file + "/waybill_list.pdf");
        Uri path = Uri.fromFile(pdfFile);

        // Setting the intent for pdf reader
        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(path, "application/pdf");
        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        try {
            startActivity(pdfIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getActivity(), "Can't read pdf file", Toast.LENGTH_SHORT).show();
        }
    }

    final FragmentCommunication communication=new FragmentCommunication() {
        @Override
        public void respond(List<Task> tasks) {
            //printedData = new ArrayList<>();
            //printedData = tasks;
        }

    };

    @Override
    public void showProgress() {
        loadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        loadingView.setVisibility(View.GONE);
    }

    @Override
    public void showDelivery(Delivery data) {
        //Log.i(TAG, "data " + data);
        /*hide keyboard and
        clear text field
         */
        try {
            if (data != null) {
                downloadPdflink = data.getFile_path();
                printedData = new ArrayList<>();
                List<Exist> exists = data.getExist();
                List<Notexist> notexists = data.getNotexist();
                int non_exist_size = notexists.size();
                int exist_size = exists.size();
                txtExist.setVisibility(View.VISIBLE);
                btnDownload.setVisibility(View.VISIBLE);
                recycler_exist.setVisibility(View.VISIBLE);
                ArrayList<Map<String, String>> all = new ArrayList<>();
                if (exist_size > 0) {
                    for (int i = 0; i < exist_size; i++) {
                        Map<String, String> map = new HashMap<>();
                        map.put("code", exists.get(i).getCode());
                        map.put("status", "0");
                        all.add(map);
                        Task task = new Task();
                        task.setCode(exists.get(i).getCode());
                        task.setShipper(exists.get(i).getShipper());
                        task.setGross_weight(exists.get(i).getGrossWeight().toString());
                        task.setPieces(exists.get(i).getPieces().toString());
                        task.setConsignee(exists.get(i).getConsignee());
                        task.setConsignee_addr(exists.get(i).getConsigneeAddr());
                        task.setConsignee_phone(exists.get(i).getConsigneePhone());
                        task.setPayment_type(exists.get(i).getPaymentType().toString());
                        task.setTotal_amt(exists.get(i).getTotalAmt().toString());
                        printedData.add(task);
                    }
                }
                if (non_exist_size > 0) {
                    for (int i = 0; i < non_exist_size; i++) {
                        Task task = new Task();
                        Map<String, String> map = new HashMap<>();
                        map.put("code", notexists.get(i).getCode());
                        map.put("status", "1");
                        all.add(map);
                        task.setCode(notexists.get(i).getCode());
                        printedData.add(task);
                    }
                }
                WaybillAdapater e_adapter = new WaybillAdapater(getActivity(), all, 1);
                recycler_exist.setLayoutManager(new LinearLayoutManager(getActivity(),
                        LinearLayoutManager.VERTICAL, false));
                recycler_exist.setAdapter(e_adapter);

            }
        }
        catch (Exception e){

        }

    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getActivity(), "error " + message+"", Toast.LENGTH_LONG).show();
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
