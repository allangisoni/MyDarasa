package com.mydarasa.app;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class PhoneOTPActivity extends AppCompatActivity {

   // @BindView(R.id.button_sendOTP)
    Button buttonsendOTP;
    //@BindView(R.id.spinner_counry_code)
    Spinner spinnerCountryCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_otp);
       // ButterKnife.bind(this);
        buttonsendOTP = (Button) findViewById(R.id.button_sendOTP);
        spinnerCountryCode = (Spinner) findViewById(R.id.spinner_counry_code);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.country_code_array,
                                             android.R.layout.simple_spinner_item );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCountryCode.setAdapter(adapter);

        buttonsendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PhoneOTPActivity.this, VerifyOTPActivity.class));
        }
        });



    }
}
