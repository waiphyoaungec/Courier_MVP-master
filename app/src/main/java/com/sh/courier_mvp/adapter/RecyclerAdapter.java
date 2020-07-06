package com.sh.courier_mvp.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.sh.courier_mvp.R;
import com.sh.courier_mvp.data.AppData;
import com.sh.courier_mvp.model.Datum;
import com.sh.courier_mvp.model.Pickuplistresponse;
import com.sh.courier_mvp.model.Task;
import com.sh.courier_mvp.notification.NotificationScheduler;
import com.sh.courier_mvp.presenter.MainContract;
import com.sh.courier_mvp.view.activity.PickUpDetailActivity;
import com.sh.courier_mvp.view.activity.WayBillDetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerAdapter extends RecyclerView.Adapter {

    private final Context context;
    private List<Task> data;
    private final List<Task> printeddata;
    private List<Datum> waybilldata;
    private int SHOWTYPE;
    private String TAG = "RecyclerAdapter";
    private FragmentCommunication communication;
    private MainContract.MainPresenter mainPresenter;

    public RecyclerAdapter(Pickuplistresponse data, Context context, int SHOWTYPE, FragmentCommunication communication, MainContract.MainPresenter mainPresenter) {
        this.context = context;
        this.SHOWTYPE = SHOWTYPE;
        this.communication = communication;
        this.mainPresenter = mainPresenter;
        if (this.SHOWTYPE == AppData.PICKUP_VIEW || this.SHOWTYPE == AppData.WAYBILL_FRAGMENT_VIEW) {
            if (data.getTasks() != null) {
                this.data = data.getTasks();
            }
        } else {
            if (data.getData() != null) {
                this.waybilldata = data.getData();
            }
        }
        printeddata = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (SHOWTYPE == AppData.PICKUP_VIEW || SHOWTYPE == AppData.WAYBILL_FRAGMENT_VIEW)
            return new PickuplistHolder(LayoutInflater.from(context).inflate(R.layout.pickup_list_item, parent, false));
        else {
            return new WayBilllistHolder(LayoutInflater.from(context).inflate(R.layout.waybill_list_item, parent, false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {


        if (holder instanceof PickuplistHolder) {
            ((PickuplistHolder) holder).txtCodeNo.setText(data.get(position).getCode());
            ((PickuplistHolder) holder).txtSr.setText((position+1)+".");
            ((PickuplistHolder) holder).txtRouteAssignName.setText(data.get(position).getCreatedBy());
            ((PickuplistHolder) holder).txtRouteAssignTime.setText(data.get(position).getRoute_assign_time());
            ((PickuplistHolder) holder).txtShipper.setText(data.get(position).getShipper());
            if (SHOWTYPE == AppData.WAYBILL_FRAGMENT_VIEW) {

                if (data.get(position).getOperation_accepted_by() == null) {
                    ((PickuplistHolder) holder).btnAccept.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mainPresenter.acceptWayBill(AppData.GetToken(context.getApplicationContext()), data.get(position).getTranId());
                            ((PickuplistHolder) holder).btnAccept.setVisibility(View.GONE);
                            NotificationScheduler.cancelNotification();
                        }
                    });
                } else {
                    ((PickuplistHolder) holder).btnAccept.setVisibility(View.GONE);
                }

                ((PickuplistHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, WayBillDetailActivity.class);
                        intent.putExtra("type", "D");
                        intent.putExtra("id", data.get(position).getTranId());
                        context.startActivity(intent);
                    }
                });
            } else if (SHOWTYPE == AppData.PICKUP_VIEW) {
                ((PickuplistHolder) holder).btnAccept.setVisibility(View.VISIBLE);
                if (data.get(position).getOperation_accepted_by() == null) {


                    ((PickuplistHolder) holder).btnAccept.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mainPresenter.acceptPickUp(AppData.GetToken(context.getApplicationContext()), data.get(position).getTranId());
                            ((PickuplistHolder) holder).btnAccept.setVisibility(View.VISIBLE);
                        }
                    });
                } else {
                    ((PickuplistHolder) holder).btnAccept.setVisibility(View.GONE);
                }

                ((PickuplistHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Log.i(TAG, "ID " + data.get(position).getTranId());
                        if(AppData.getNotiCount(context).equals("0")){
                            Intent intent = new Intent(context, PickUpDetailActivity.class);
                            intent.putExtra("id", data.get(position).getTranId());
                            context.startActivity(intent);
                        }else{
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("Show status");
                            builder.setMessage("Please click accept button of Pickup");

                            String positiveText = context.getString(R.string.ok);
                            builder.setPositiveButton(positiveText,
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // dismiss alert dialog, update preferences with game score and restart play fragment
                                            dialog.dismiss();
                                        }
                                    });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }

                    }
                });
            }
            ((PickuplistHolder) holder).checkBox.setOnCheckedChangeListener(null);
            ((PickuplistHolder) holder).checkBox.setChecked(data.get(position).getChecked());


            ((PickuplistHolder) holder).checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                  //  Toast.makeText(context,"it is" + position,Toast.LENGTH_SHORT).show();
                    if (isChecked) {
                        data.get(position).setChecked(true);
                        printeddata.add(data.get(position));
                        communication.respond(printeddata);
                    } else {
                        data.get(position).setChecked(false);
                        if (printeddata != null) {
                            printeddata.remove(data.get(position));
                            communication.respond(printeddata);
                        }
                    }
                }
            });

        } else if (holder instanceof WayBilllistHolder) {
            ((WayBilllistHolder) holder).txtWayBillCode.setText(waybilldata.get(position).getCode());
            ((WayBilllistHolder) holder).txtSource.setText(waybilldata.get(position).getLocationFrom());
            ((WayBilllistHolder) holder).txtDestination.setText(waybilldata.get(position).getLocationTo());

            ((WayBilllistHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, WayBillDetailActivity.class);
                    intent.putExtra("type", "P");
                    intent.putExtra("id", waybilldata.get(position).getId());
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (SHOWTYPE == AppData.PICKUP_VIEW || SHOWTYPE == AppData.WAYBILL_FRAGMENT_VIEW) {
            Log.d("test", "getItemCountssss: " + data.size());
            if(data.size() == 0){
                Toast.makeText(context,"no pick up",Toast.LENGTH_SHORT).show();
            }
            return data != null ? data.size() : 0;

        } else {
            Log.d("test", "getItemCountaa: " + waybilldata.size());
            return waybilldata != null ? waybilldata.size() : 0;
        }
    }

    class PickuplistHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtCodeName)
        TextView txtCodeName;
        @BindView(R.id.txtCodeNo)
        TextView txtCodeNo;
        @BindView(R.id.txtSr)
        TextView txtSr;
        @BindView(R.id.txtShipper)
        TextView txtShipper;
        @BindView(R.id.txtRouteAssignTime)
        TextView txtRouteAssignTime;
        @BindView(R.id.txtRouteAssignName)
        TextView txtRouteAssignName;
        @BindView(R.id.btnAccept)
        Button btnAccept;
        @BindView(R.id.chkSelecte)
        CheckBox checkBox;
        /*@BindView(R.id.txtDelivery)
        TextView txtDelivery;*/

        PickuplistHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class WayBilllistHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtWayBillCode)
        TextView txtWayBillCode;
        @BindView(R.id.txtSource)
        TextView txtSource;
        @BindView(R.id.txtDestination)
        TextView txtDestination;

        WayBilllistHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
