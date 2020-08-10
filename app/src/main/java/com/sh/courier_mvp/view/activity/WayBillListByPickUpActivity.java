package com.sh.courier_mvp.view.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.sh.courier_mvp.R;
import com.sh.courier_mvp.adapter.FragmentCommunication;
import com.sh.courier_mvp.adapter.RecyclerAdapter;
import com.sh.courier_mvp.data.AppData;
import com.sh.courier_mvp.model.Pickuplistresponse;
import com.sh.courier_mvp.model.Task;
import com.sh.courier_mvp.presenter.MainContract;
import com.sh.courier_mvp.presenter.Presenter;

import java.util.List;

import app.dinus.com.loadingdrawable.LoadingView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class WayBillListByPickUpActivity extends AppCompatActivity implements MainContract.WayBillListByPickUp {
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    MainContract.MainPresenter mainPresenter;
    //String TAG = "WayBillListByPickUpActivity";
    @BindView(R.id.loading)
    LoadingView loadingView;
    @BindView(R.id.btnDownload)
    Button btnDownload;
    /*@BindView(R.id.edtWayBillCode2)
    EditText edtWayBillCode2;
    @BindView(R.id.imgbtnScan2)
    ImageButton imgbtnScan2;*/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_pickuplist);
        ButterKnife.bind(this);
        /*edtWayBillCode2.setVisibility(View.GONE);
        imgbtnScan2.setVisibility(View.GONE);*/
        mainPresenter = new Presenter(this, this);
        btnDownload.setVisibility(View.GONE);

        String id = getIntent().getStringExtra("id");
        mainPresenter.getWayBillListByPickUp(AppData.GetToken(getApplicationContext()), id);
    }

    final FragmentCommunication communication=new FragmentCommunication() {
        @Override
        public void respond(List<Task> tasks) {
           // Log.i(TAG, "FragmentCommunication"+tasks);
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
    public void showWayBillList(Pickuplistresponse data) {
        //Log.i(TAG, data.getData().size()+" showWayBillList");
        if(data == null){
            Toast.makeText(this, "No Data", Toast.LENGTH_LONG).show();
        }
        else if(data.getData().size() == 0){
            Toast.makeText(this,"No Waybill created for this Pickup",Toast.LENGTH_SHORT).show();
        }
        else {
            RecyclerAdapter adapter = new RecyclerAdapter(data, this, AppData.WAYBILL_VIEW, communication, mainPresenter);
            recycler_view.setLayoutManager(new LinearLayoutManager(this,
                    LinearLayoutManager.VERTICAL,false));
            recycler_view.setAdapter(adapter);
        }

    }
}
