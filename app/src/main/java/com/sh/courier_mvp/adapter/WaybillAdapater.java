package com.sh.courier_mvp.adapter;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.sh.courier_mvp.R;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WaybillAdapater extends RecyclerView.Adapter {
    private final Context context;
    private List<Map<String,String>> data;
    private Integer type;
    private onRemove onRemove;

    public WaybillAdapater(Context context, List<Map<String,String>> data, Integer type){
        this.context = context;
        this.data = data;
        this.type = type;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DeliveryHolder(LayoutInflater.from(context).inflate(R.layout.delivery_waybill,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof WaybillAdapater.DeliveryHolder) {
            ((DeliveryHolder) holder).txtDeliveryWB.setText(data.get(position).get("code"));
            ((DeliveryHolder) holder).txtDeliveryNo.setText(String.valueOf(position+1)+".");

            if(type == 1){
                ((DeliveryHolder) holder).remove.setVisibility(View.GONE);
                if(data.get(position).get("status").equals("1")){

                    ((DeliveryHolder)holder).txtDeliveryWB.setTextColor(Color.parseColor("#DD2C00"));
                }
            }
            else{
                ((DeliveryHolder) holder).remove.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class DeliveryHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.txtDeliveryWB)
        TextView txtDeliveryWB;
        @BindView(R.id.txtDeliveryNo)
        TextView txtDeliveryNo;
        @BindView(R.id.remove)
        ImageButton remove;

        DeliveryHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onRemove != null){
                        int postion = getAdapterPosition();
                        if(postion != RecyclerView.NO_POSITION){
                            onRemove.onremove(postion);
                        }
                    }
                }
            });
        }

    }
    public interface onRemove{
        public void onremove(int position);
    }
    public void onRemoveListener(onRemove onRemove){
        this.onRemove = onRemove;

    }
}
