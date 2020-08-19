package com.sh.courier_mvp.view.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.downloadapk.util.DownloadController;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.sh.courier_mvp.R;
import com.sh.courier_mvp.data.AppData;
import com.sh.courier_mvp.presenter.MainContract;
import com.sh.courier_mvp.presenter.Presenter;
import com.sh.courier_mvp.util.ConnectivityReceiver;
import com.sh.courier_mvp.util.Utility;
import com.sh.courier_mvp.view.fragment.CheckPickupListFragment;
import com.sh.courier_mvp.view.fragment.CreateWaybillFragmenet;
import com.sh.courier_mvp.view.fragment.HomeFragment;
import com.sh.courier_mvp.view.fragment.PickUpListFragment;
import com.sh.courier_mvp.view.fragment.TrackingPointFragment;
import com.sh.courier_mvp.view.fragment.WayBillListFragment;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainContract.MainView{
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    private static final int REQUEST_PERMISSION = 200;
    MainContract.MainPresenter mainPresenter;
    String TAG = "MainActivity";
    private int count = 0;
    private String notiCount = "0";

    Timer timer;
    private final String versions = "17";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        LocalData localData = new LocalData(getApplicationContext());
//        localData.set_hour(19);
//        localData.set_min(0);
//        NotificationScheduler.setReminder(MainActivity.this, AlarmReceiver.class, localData.get_hour(), localData.get_min());
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                    REQUEST_PERMISSION);
        }


        // Access a Cloud Firestore instance from your Activity


        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);

        changeFragment(new HomeFragment());
        checkUpdate();
        if(ConnectivityReceiver.isConnected()){

            mainPresenter = new Presenter(this,getApplicationContext());
            mainPresenter.getPickupNotiCount(AppData.GetToken(getApplicationContext()));
            Calendar cal = Calendar.getInstance();
            int hour = cal.get(Calendar.HOUR_OF_DAY);
            if(hour >= 9 && hour <= 18){
                Log.i(TAG, "timer " + timer);
                if(timer == null){
                    startTime();
                }
            }else if(hour >= 18){
                Log.i(TAG, "pauseTime " + hour);
                pauseTime();
            }
            //NotificationScheduler.setReminder(this, AlarmReceiver.class, 10, 60);
        }else{
            Toast.makeText(this, "No Internet Connection!", Toast.LENGTH_SHORT).show();
        }
    }

    private void startTime(){
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Log.i(TAG, "timer : getPickupNotiCount" + timer);
                mainPresenter.getPickupNotiCount(AppData.GetToken(getApplicationContext()));
            }
        }, 0, 3600000);
    }

    private void pauseTime() {
        if (timer != null)
        {
            timer.cancel();
        }

    }

    @Override
    public void onBackPressed() {
        if(Utility.page == 0) {
            count++;
            if (count == 1) {
                Toast.makeText(this, getResources().getString(R.string.pressagain), Toast.LENGTH_SHORT).show();
            } else {
                count = 0;
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
                finish();
            }
        }
        else{
            changeFragment(new HomeFragment());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Thanks for granting Permission", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(ConnectivityReceiver.isConnected()) {
            mainPresenter = new Presenter(this,getApplicationContext());
            mainPresenter.getPickupNotiCount(AppData.GetToken(getApplicationContext()));
            if (AppData.getNotiCount(this).equals("0")) {
                if (id == R.id.nav_pickuplist) {
                    changeFragment(new PickUpListFragment());
                } else if (id == R.id.nav_waybill) {
                    changeFragment(new WayBillListFragment());
                } else if (id == R.id.nav_home) {
                    changeFragment(new HomeFragment());
                } else if (id == R.id.nav_tracking) {
                    changeFragment(new TrackingPointFragment());
                } else if (id == R.id.nav_createwaybill) {
                    changeFragment(new CreateWaybillFragmenet());
                }

                else if (id == R.id.nav_checkpickuplist) {
                    changeFragment(new CheckPickupListFragment());
                } else if (id == R.id.nav_logout) {
                    AppData.CurrentUserLocationID = "";
                    AppData.CurrentUserLocationCode = "";
                    AppData.CurrentUserID = 0;
                    AppData.CurrentUserName = "";
                    AppData.StoreToken(this, "");

                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                else if(id == R.id.check_update){
                    checkUpdate();
                }
            } else {
                showDialog();
                //Toast.makeText(this, "Please click accept button of Pickup", Toast.LENGTH_LONG).show();
                if (id == R.id.nav_home) {
                    mainPresenter.getPickupNotiCount(AppData.GetToken(getApplicationContext()));
                    changeFragment(new HomeFragment());
                } else {
                    //mainPresenter.getPickupNotiCount(AppData.GetToken(getApplicationContext()));
                    changeFragment(new PickUpListFragment());
                }
            }
        }else{
            Toast.makeText(this, "No Internet Connection!", Toast.LENGTH_SHORT).show();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showDialog(){
        /*new FancyAlertDialog.Builder(this)
                .setTitle("Show status")
                .setBackgroundColor(Color.parseColor("#63341a"))  //Don't pass R.color.colorvalue
                .setMessage("Please click accept button of Pickup")
                .setPositiveBtnBackground(Color.parseColor("#e12425"))  //Don't pass R.color.colorvalue
                .setPositiveBtnText("OK")
                .setAnimation(Animation.POP)
                .isCancellable(true)
                .setIcon(R.drawable.ic_check_black_24dp, Icon.Visible)
                .OnPositiveClicked(new FancyAlertDialogListener() {
                    @Override
                    public void OnClick() {

                    }
                })
                .build();*/
        // create a dialog with AlertDialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Show status");
        builder.setMessage("Please click accept button of Pickup");

        String positiveText = getString(android.R.string.ok);
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

    @Override
    public void changeFragment(Fragment fm) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content, fm);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }

    @Override
    public void setNavItemCount(String count) {
        TextView view = (TextView) navigationView.getMenu().findItem(R.id.nav_pickuplist).getActionView();
        view.setText(count);
        notiCount = count;
    }
    private void checkUpdate(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("mgl")
                .document("E2aWEd5OyX1qZPxSrLad")
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        if(documentSnapshot!=null && documentSnapshot.exists()) {
                            String version = documentSnapshot.get("version") + "";
                            final String link = documentSnapshot.get("link") + "";
                            final String whatupdate = documentSnapshot.get("update") + "";
                            if (!version.equals(versions)) {
                                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                                alertDialog.setTitle("New Updated Version Available");
                                alertDialog.setCancelable(false);
                                alertDialog.setMessage(whatupdate + "\n please update");
                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Cancel",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                Toast.makeText(MainActivity.this,"You need to update new Version",Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Download",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                DownloadController downloadController = new DownloadController(MainActivity.this, link);
                                                downloadController.enqueueDownload();
                                            }
                                        });
                                alertDialog.show();
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Updated",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }



    @Override
    protected void onResume() {
        super.onResume();
    }
}
