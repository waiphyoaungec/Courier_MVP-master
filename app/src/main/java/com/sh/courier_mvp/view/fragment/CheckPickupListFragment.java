package com.sh.courier_mvp.view.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.sh.courier_mvp.R;
import com.sh.courier_mvp.util.Utility;
import com.sh.courier_mvp.data.AppData;
import com.sh.courier_mvp.model.Waybilldetailresponse;
import com.sh.courier_mvp.presenter.MainContract;
import com.sh.courier_mvp.presenter.Presenter;

import app.dinus.com.loadingdrawable.LoadingView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CheckPickupListFragment extends Fragment implements MainContract.CheckpickupView {
    @BindView(R.id.tableLayout)
    TableLayout tableLayout;
    @BindView(R.id.loading)
    LoadingView loading;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_checkpickuplist,container,false);
        ButterKnife.bind(this,view);
        Utility.page = 4;

        MainContract.MainPresenter mainPresenter = new Presenter(this, getActivity());
        String TAG = "CheckPickupListFragment";
        Log.i(TAG, "user id " + AppData.getUserId(getActivity()));
        mainPresenter.getPickupWayBillList(AppData.GetToken(getActivity()), AppData.getUserId(getActivity()));
        //init();

        return view;
    }

    @Override
    public void showProgress() {
        loading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {

        loading.setVisibility(View.GONE);
    }

    @Override
    public void error(String error) {
        Toast.makeText(getContext(),"Error Try Again \n" + error,Toast.LENGTH_SHORT).show();
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content, new HomeFragment());
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();

    }

    @Override
    public void showPickupwaybilllist(Waybilldetailresponse waybilldetailresponse) {
        if (waybilldetailresponse != null) {
            int size = waybilldetailresponse.getWaybillList().size();
            for (int i = 0; i < size; i++) {
                TableRow row = new TableRow(getActivity());
                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                row.setLayoutParams(lp);
                row.setPadding(10, 10, 10, 10);
                row.setBackgroundResource(R.drawable.row_border);
                TextView txtSr = new TextView(getActivity());
                TextView txtPickupcode = new TextView(getActivity());
                TextView txtWBCode = new TextView(getActivity());

                txtSr.setText((i + 1) + "");
                txtPickupcode.setText(waybilldetailresponse.getWaybillList().get(i).getPkcode());
                txtWBCode.setText(waybilldetailresponse.getWaybillList().get(i).getCode());

                row.addView(txtSr);
                row.addView(txtPickupcode);
                row.addView(txtWBCode);

                tableLayout.addView(row);
            }
        }
    }
}
