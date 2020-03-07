package com.mydarasa.app.login;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import network.ConnectivityReceiver;
import network.CustomNoInternetDialog;
import network.MyApplication;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.mydarasa.app.R;
import com.mydarasa.app.fees.CreatePaymentActivity;

public class ChangePasswordActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    Toolbar myToolbar;

    TextInputEditText etPin;
    CustomNoInternetDialog customNoInternetDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);


        myToolbar =  findViewById(R.id.toolbar);
        etPin = findViewById(R.id.etPin);
        customNoInternetDialog = findViewById(R.id.custom_dialog);

        setSupportActionBar(myToolbar);
        ActionBar actionBar = getSupportActionBar();
        //actionBar.setHomeAsUpIndicator(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Change Pin");

        etPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showcustomDialog();
            }
        });

        checkConnection();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    private void showcustomDialog(boolean isConnected){

        if(!isConnected) {
         /**   AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ChangePasswordActivity.this, R.style.fullScreenDialog);
            ViewGroup viewGroup = findViewById(android.R.id.content);
            View dialogView = LayoutInflater.from(ChangePasswordActivity.this).inflate(R.layout.custom_no_internet_dialog, viewGroup, false);
            //TextView tvMessage = dialogView.findViewById(R.id.tvDialog);
            //tvMessage.setText("Payment Uploaded Successfully");
            alertDialogBuilder.setView(dialogView);

            AlertDialog dialog = alertDialogBuilder.create();
            dialog.show(); **/

         customNoInternetDialog.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // register connection status listener
        MyApplication.getInstance().setConnectivityListener(this);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showcustomDialog(isConnected);
    }

    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showcustomDialog(isConnected);
    }
}
