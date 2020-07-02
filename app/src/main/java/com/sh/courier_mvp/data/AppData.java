package com.sh.courier_mvp.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.sh.courier_mvp.R;

public class AppData {
    public static final int PICKUP_VIEW = 1;
    public static int WAYBILL_VIEW = 3;
    public static int WAYBILL_FRAGMENT_VIEW = 2;

    public static int CurrentUserID;
    public static String CurrentUserName;
    public static String CurrentUserLocationID;
    public static String CurrentUserLocationCode;
    public static String noticount;

    public static String[] LOCATIONS;

    public static int getUserId(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences( context.getString(R.string.PREFS_FILE), Context.MODE_PRIVATE);
        return sharedPref.getInt("userid", 0);
    }

    public static void storeUserID(Context context, int userid){
        SharedPreferences.Editor editor = context.getSharedPreferences( context.getString(R.string.PREFS_FILE), Context.MODE_PRIVATE).edit();
        editor.putInt("userid", userid);
        editor.apply();
    }

    public static String getNotiCount(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences( context.getString(R.string.PREFS_FILE), Context.MODE_PRIVATE);
        return sharedPref.getString("noticount", "0");
    }

    public static void storeNotiCount(Context context, String count){
        SharedPreferences.Editor editor = context.getSharedPreferences( context.getString(R.string.PREFS_FILE), Context.MODE_PRIVATE).edit();
        editor.putString("noticount", count);
        editor.apply();
    }

    public static String GetToken(Context mContext){
        SharedPreferences sharedPref = mContext.getSharedPreferences( mContext.getString(R.string.PREFS_FILE), Context.MODE_PRIVATE);
        return sharedPref.getString("Token", "NotFound");
    }

    public static void StoreToken(Context mContext, String token){
        SharedPreferences.Editor editor = mContext.getSharedPreferences( mContext.getString(R.string.PREFS_FILE), Context.MODE_PRIVATE).edit();
        editor.putString("Token", token);
        editor.apply();
    }

}
