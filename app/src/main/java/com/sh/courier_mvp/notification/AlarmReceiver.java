package com.sh.courier_mvp.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.sh.courier_mvp.data.AppData;
import com.sh.courier_mvp.view.activity.LoginActivity;


public class AlarmReceiver extends BroadcastReceiver {
    String TAG = "AlarmReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub

        if (intent.getAction() != null && context != null) {
            if (intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)) {
                // Set the alarm here.
                Log.d(TAG, "onReceive: BOOT_COMPLETED");
                LocalData localData = new LocalData(context);
                NotificationScheduler.setReminder(context, AlarmReceiver.class, localData.get_hour(), localData.get_min());
                return;
            }
        }

        Log.d(TAG, "onReceive: ");
        AppData.CurrentUserLocationID = "";
        AppData.CurrentUserLocationCode = "";
        AppData.CurrentUserID = 0;
        AppData.CurrentUserName = "";
        AppData.StoreToken(context, "");

        Intent intent1 = new Intent(context, LoginActivity.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent1);



        //Trigger the notification

       /*if(AppData.noticount.equals("0")){
           Log.d(TAG, "onReceive: 0");
        }else{
            NotificationScheduler.showNotification(context, MainActivity.class,
                    AppData.noticount+" new notification", "Watch them now?");

        }*/


    }
}
