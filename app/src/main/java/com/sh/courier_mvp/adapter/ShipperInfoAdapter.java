package com.sh.courier_mvp.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;
import android.widget.Toast;

import com.sh.courier_mvp.R;
import com.sh.courier_mvp.model.ShipperModel;

import java.util.ArrayList;
import java.util.List;

public class ShipperInfoAdapter extends ArrayAdapter<ShipperModel> {

    private List<ShipperModel> dataList;
    private Context mContext;
    private int itemLayout;
    private OnPostClick mlistener;
    private ListFilter listFilter = new ListFilter();
    private List<ShipperModel> dataListAllItems;



    public ShipperInfoAdapter(Context context, int resource, List<ShipperModel> shipperModels) {
        super(context, resource, shipperModels);
        dataList = shipperModels;
        mContext = context;
        itemLayout = resource;
    }

    @Override
    public int getCount() {
        if(dataList!=null)
        return dataList.size();
        else
            return  0;
    }

    @Override
    public ShipperModel getItem(int position) {
        Log.d("CustomListAdapter",
                dataList.get(position).getCode());
        return dataList.get(position);
    }

    @Override
    public View getView(final int position, View view, @NonNull ViewGroup parent) {

        if (view == null) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(itemLayout, parent, false);
        }
        ConstraintLayout cardView = view.findViewById(R.id.spinnercard);


        TextView code = view.findViewById(R.id.shippercode);
        TextView name = view.findViewById(R.id.shippername);
        code.setText(getItem(position).getCode() + "\n......................\n" + getItem(position).getName());
        name.setVisibility(View.GONE);
        return view;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return listFilter;
    }

    public class ListFilter extends Filter {
        private Object lock = new Object();

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            //Log.d("testing",prefix.toString());
            FilterResults results = new FilterResults();
            if (dataListAllItems == null) {
                synchronized (lock) {
                    dataListAllItems = new ArrayList<>(dataList);
                }
            }

            if (prefix == null || prefix.length() == 0) {
                synchronized (lock) {
                    results.values = dataListAllItems;
                    results.count = dataListAllItems.size();
                }
            } else {

                final String searchStrLowerCase = prefix.toString().toLowerCase();
                Log.d("testing",searchStrLowerCase);
                ArrayList<ShipperModel> matchValues = new ArrayList<>();

                for (ShipperModel dataItem : dataListAllItems) {
                    if (dataItem.getCode().toLowerCase().startsWith(searchStrLowerCase)) {
                        matchValues.add(dataItem);
                        Log.d("testing","exactly");
                    }
                   else if (dataItem.getCode().toLowerCase().endsWith(searchStrLowerCase)) {
                        matchValues.add(dataItem);
                        Log.d("testing","exactly");
                    }
                    else if(dataItem.getName().toLowerCase().contains(searchStrLowerCase)){
                        matchValues.add((dataItem));
                        Log.d("testing","contain");

                    }
                    else{
                        matchValues.add(dataItem);

                        Log.d("testing","name is " + dataItem.getName().toLowerCase() + "......" + searchStrLowerCase + " ..." +  searchStrLowerCase.toLowerCase().contains(dataItem.getName().toLowerCase()));
                    }
                }

                results.values = matchValues;
                results.count = matchValues.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.values != null) dataList = (ArrayList<ShipperModel>) results.values;
            else {
                dataList = null;
            }
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }

    }
    public interface OnPostClick{
        void onclick(int position);
    }
    public void onClick(OnPostClick mlistener){
        this.mlistener = mlistener;

    }
}
