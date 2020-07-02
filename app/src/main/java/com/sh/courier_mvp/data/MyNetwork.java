package com.sh.courier_mvp.data;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by Lenovo on 4/5/2018.
 */

public class MyNetwork
{
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}
