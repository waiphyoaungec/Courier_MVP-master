package com.sh.courier_mvp.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sh.courier_mvp.R;
import com.sh.courier_mvp.data.AppData;
import com.sh.courier_mvp.presenter.MainContract;
import com.sh.courier_mvp.presenter.Presenter;
import com.sh.courier_mvp.util.ConnectivityReceiver;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements MainContract.LoginView {
    @BindView(R.id.autoTxtPhone)
    EditText autoTxtPhone;
    @BindView(R.id.edtPassword)
    EditText edtPassword;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    ProgressDialog progressDialog;
//    @BindView(R.id.loading)
//    LoadingView loadingView;
    private MainContract.MainPresenter mainPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //calling_Alarm();
        String token = AppData.GetToken(getApplicationContext());
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Loading..."); // Setting Message
        progressDialog.setTitle("Courier Sign In"); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner

        progressDialog.setCancelable(false);
        //Log.i("TAG", "token"+token);
        if (token == null || token.equals("") || token.equals("NotFound")) {
            setContentView(R.layout.activity_login);
            ButterKnife.bind(this);
            hideProgress();
            mainPresenter = new Presenter(this, getApplicationContext());
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(ConnectivityReceiver.isConnected()){

                        //Toast.makeText(getApplicationContext(), "isconnected", Toast.LENGTH_SHORT).show();
                        mainPresenter.checkData(autoTxtPhone.getText().toString(), edtPassword.getText().toString());
                    }else{
                        Toast.makeText(getApplicationContext(), "isNotconnected", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        } else {
            gotoHomePage();
        }
    }

    @Override
    public void gotoHomePage() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void phonenoEmpty() {
        autoTxtPhone.setError("Empty name!");
    }

    @Override
    public void passwordEmpty() {
        edtPassword.setError("Empty phone!");
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {
        progressDialog.show(); // Display Progress Dialog
        //loadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
        //loadingView.setVisibility(View.GONE);
    }
}
