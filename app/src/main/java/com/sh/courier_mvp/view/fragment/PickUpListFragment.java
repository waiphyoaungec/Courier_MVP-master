package com.sh.courier_mvp.view.fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

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
import com.sh.courier_mvp.adapter.RecyclerAdapter;
import com.sh.courier_mvp.data.AppData;
import com.sh.courier_mvp.data.MyNetwork;
import com.sh.courier_mvp.model.Pickuplistresponse;
import com.sh.courier_mvp.model.Task;
import com.sh.courier_mvp.presenter.MainContract;
import com.sh.courier_mvp.presenter.Presenter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import app.dinus.com.loadingdrawable.LoadingView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PickUpListFragment extends Fragment implements MainContract.PickUpListView,
SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.btnDownload)
    Button btnDownload;
    MainContract.MainPresenter mainPresenter;
    //String TAG = "PickUpListFragment";
    Pickuplistresponse data;
    List<Task> printedData;
    @BindView(R.id.loading)
    LoadingView loadingView;
    @BindView(R.id.swp_pickup)
    SwipeRefreshLayout swipeRefreshLayout;
    public static final String FONT = "res/raw/zg.TTF";
    private  RecyclerAdapter adapter;
    //String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOi8vb21zLm1nbGV4cHJlc3MuY29tLm1tL2FwaS9hdXRoL2xvZ2luIiwiaWF0IjoxNTgwODc0NTI2LCJuYmYiOjE1ODA4NzQ1MjYsImp0aSI6IlNYcTFNUWJIWjF3MWxvMGoiLCJzdWIiOjM5LCJwcnYiOiIyM2JkNWM4OTQ5ZjYwMGFkYjM5ZTcwMWM0MDA4NzJkYjdhNTk3NmY3In0.4NE-NqaYYY5Bq4_hUu5uBBZmCrZqnkj30PLOci2kPbY";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pickuplist, container, false);
        ButterKnife.bind(this, view);
        Utility.page = 1;
        Log.d("test","checking...");
        mainPresenter = new Presenter(this, getActivity());
        if (MyNetwork.isNetworkConnected(getContext())) {
            mainPresenter.getPickUpList(AppData.GetToken(getActivity().getApplicationContext()));
            mainPresenter.getPickupNotiCount(AppData.GetToken(getActivity().getApplicationContext()));
        } else {
            Toast.makeText(getActivity(), "No Internet Connection!", Toast.LENGTH_LONG).show();
        }

        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (printedData == null) {
                    Toast.makeText(getActivity(), "Please select pickup", Toast.LENGTH_LONG).show();
                } else {
                    createandDisplayPdf("Pickup List", printedData);
                }

            }
        });
        swipeRefreshLayout.setOnRefreshListener(this);

        return view;
    }

    final FragmentCommunication communication = new FragmentCommunication() {
        @Override
        public void respond(List<Task> tasks) {
            printedData = new ArrayList<>();
            printedData = tasks;
        }

    };

    public void createandDisplayPdf(String text, List<Task> printedData) {

        Document doc = new Document();

        try {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Dir";

            File dir = new File(path);
            if (!dir.exists())
                dir.mkdirs();

            File file = new File(dir, "pickup_list.pdf");
            FileOutputStream fOut = new FileOutputStream(file);

            PdfWriter.getInstance(doc, fOut);
            //open the document
            doc.open();

            Paragraph p1 = new Paragraph(text);
            Font paraFont = new Font(Font.getFamily(""));
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
            PdfPTable table = new PdfPTable(14);
            table.addCell(new PdfPCell(new Paragraph("No")));
            table.addCell(new PdfPCell(new Paragraph("Code")));
            table.addCell(new PdfPCell(new Paragraph("Company")));
            table.addCell(new PdfPCell(new Paragraph("Contact")));

            table.addCell(new PdfPCell(new Paragraph("Addr")));
            table.addCell(new PdfPCell(new Paragraph("Phone")));
            table.addCell(new PdfPCell(new Paragraph("ReadyTime")));
            table.addCell(new PdfPCell(new Paragraph("Doc.Type")));
            table.addCell(new PdfPCell(new Paragraph("PayType")));

            table.addCell(new PdfPCell(new Paragraph("Dest:")));
            table.addCell(new PdfPCell(new Paragraph("PaidBy")));
            table.addCell(new PdfPCell(new Paragraph("Courier")));

            table.addCell(new PdfPCell(new Paragraph("Pickup Time")));
            table.addCell(new PdfPCell(new Paragraph("Created")));
            for (int i = 0; i < printedData.size(); i++) {
                String no = "" + (i + 1);
                Font f = FontFactory.getFont(FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                table.addCell(new PdfPCell(new Paragraph(no)));
                table.addCell(new PdfPCell(new Paragraph(printedData.get(i).getCode(), f)));
                table.addCell(new PdfPCell(new Paragraph(printedData.get(i).getShipperCompanyName(), f)));
                table.addCell(new PdfPCell(new Paragraph(printedData.get(i).getShipperContactName(), f)));

                table.addCell(new PdfPCell(new Paragraph(printedData.get(i).getShipperAddr(), f)));
                table.addCell(new PdfPCell(new Paragraph(printedData.get(i).getShipperPhone(), f)));
                table.addCell(new PdfPCell(new Paragraph(printedData.get(i).getReady_time(), f)));
                table.addCell(new PdfPCell(new Paragraph(printedData.get(i).getDocType(), f)));
                table.addCell(new PdfPCell(new Paragraph("")));

                table.addCell(new PdfPCell(new Paragraph(printedData.get(i).getLocationTo(), f)));
                table.addCell(new PdfPCell(new Paragraph("")));
                table.addCell(new PdfPCell(new Paragraph(printedData.get(i).getCouriermen(), f)));

                table.addCell(new PdfPCell(new Paragraph(printedData.get(i).getPickupTime(), f)));
                table.addCell(new PdfPCell(new Paragraph(printedData.get(i).getCreatedBy(), f)));
            }
            table.setSpacingBefore(20);
            doc.add(table);

        } catch (DocumentException de) {
            Log.e("PDFCreator", "DocumentException:" + de);
        } catch (IOException e) {
            Log.e("PDFCreator", "ioException:" + e);
        } finally {
            doc.close();
        }

        viewPdf("Dir");
    }

    // Method for opening a pdf file
    private void viewPdf(String directory) {

        File pdfFile = new File(Environment.getExternalStorageDirectory() + "/" + directory + "/pickup_list.pdf");
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

    @Override
    public void showProgress() {
        loadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        loadingView.setVisibility(View.GONE);
    }

    @Override
    public void showPickUpList(Pickuplistresponse data) {
        Log.d("test", "showPickUpList: " + data);
        this.data = data;

        adapter = new RecyclerAdapter(data, getActivity(), AppData.PICKUP_VIEW, communication, mainPresenter);
        recycler_view.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));
        recycler_view.setAdapter(adapter);
    }

    @Override
    public void showMessage(String message) {
//        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//        ft.replace(R.id.content, new HomeFragment());
//        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//        ft.commit();
       onRefresh();
        Toast.makeText(getActivity(), message + "", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRefresh() {
        showProgress();
        mainPresenter.getPickupNotiCount(AppData.GetToken(getActivity().getApplicationContext()));
        mainPresenter.getPickUpList(AppData.GetToken(getActivity().getApplicationContext()));
        swipeRefreshLayout.setRefreshing(false);
    }
}
