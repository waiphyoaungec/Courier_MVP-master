package com.sh.courier_mvp.view.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sh.courier_mvp.R;
import com.sh.courier_mvp.util.Utility;
import com.sh.courier_mvp.data.AppData;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment {
    @BindView(R.id.cardPickup)
    CardView cardPickup;
    @BindView(R.id.cardWayBill)
    CardView cardWayBill;
    @BindView(R.id.cardCheckpoint)
    CardView cardCheckpoint;
    @BindView(R.id.cardCreateWaybill)
    CardView cardCreateWaybill;
    @BindView(R.id.card_add_pickup)
    CardView cardCreatePickup;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);

        ButterKnife.bind(this,view);
        Utility.page = 0;

        cardPickup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AppData.getNotiCount(getContext()).equals("0")){
                    changeFragment(new PickUpListFragment());
                }else{
                    showDialog();
                }

            }
        });

        cardWayBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AppData.getNotiCount(getContext()).equals("0")){
                    changeFragment(new WayBillListFragment());
                }else{
                    showDialog();
                }

            }
        });
        cardCreatePickup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(AppData.getNotiCount(getContext()).equals("0")){
                    changeFragment(new CreatePickupFragment());
                }else{
                    showDialog();
                }
            }
        });

        cardCheckpoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AppData.getNotiCount(getContext()).equals("0")){

                    changeFragment(new TrackingPointFragment());
                }else{
                    showDialog();
                }

            }
        });
        cardCreateWaybill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AppData.getNotiCount(getContext()).equals("0")){

                    changeFragment(new CreateWaybillFragmenet());
                }else{
                    showDialog();
                }

            }
        });

        return view;
    }

    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Show status");
        builder.setMessage("Please click accept button of Pickup");

        String positiveText = getString(R.string.ok);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // dismiss alert dialog, update preferences with game score and restart play fragment
                        changeFragment(new PickUpListFragment());
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void changeFragment(Fragment fm) {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content, fm);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }
}
