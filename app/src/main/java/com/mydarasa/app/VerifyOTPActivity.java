package com.mydarasa.app;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class VerifyOTPActivity extends AppCompatActivity {
   // @BindView(R.id.button_verifyOTP)
    Button buttonverifyOTP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);
        //ButterKnife.bind(this);
          buttonverifyOTP = (Button) findViewById(R.id.button_verifyOTP);

        buttonverifyOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VerifyOTPActivity.this, RegisterActivity.class));
            }
        });
    }
}
