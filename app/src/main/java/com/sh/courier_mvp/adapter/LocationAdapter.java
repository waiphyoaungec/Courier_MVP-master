package com.sh.courier_mvp.adapter;

import android.content.Context;
import android.graphics.Typeface;
import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.sh.courier_mvp.R;

import java.util.ArrayList;
import java.util.List;

public class LocationAdapter extends ArrayAdapter<String> {

    private List<String> dataList;
    private Context mContext;
    private int itemLayout;

    private ListFilter listFilter = new ListFilter();
    private List<String> dataListAllItems;
    private Typeface zawgyi;



    public LocationAdapter(Context context, int resource, List<String> locationsList,Typeface zawgyi) {
        super(context, resource, locationsList);
        dataList = locationsList;
        mContext = context;
        itemLayout = resource;
        this.zawgyi = zawgyi;
    }

    @Override
    public int getCount() {
        if(dataList!=null)
            return dataList.size();
        else
            return  0;
    }

    @Override
    public String getItem(int position) {
        Log.d("CustomListAdapter",
                dataList.get(position));
        return dataList.get(position);
    }

    @Override
    public View getView(int position, View view, @NonNull ViewGroup parent) {

        if (view == null) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(itemLayout, parent, false);
        }

        TextView code = view.findViewById(R.id.shippercode);
        TextView name = view.findViewById(R.id.shippername);

        code.setText(getItem(position) + "");
        code.setTypeface(zawgyi);
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
                ArrayList<String> matchValues = new ArrayList<>();

                for (String dataItem : dataListAllItems) {
                    if (dataItem.toLowerCase().startsWith(searchStrLowerCase)) {
                        matchValues.add(dataItem);
                        Log.d("testing","exactly");
                    }
                    else if(dataItem.toLowerCase().contains(searchStrLowerCase)){
                        matchValues.add((dataItem));
                        Log.d("testing","contain");

                    }

                }

                results.values = matchValues;
                results.count = matchValues.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.values != null) dataList = (ArrayList<String>) results.values;
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
}
