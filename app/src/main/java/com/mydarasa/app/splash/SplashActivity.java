package com.mydarasa.app.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.mydarasa.app.LoginActivity;
import com.mydarasa.app.MainActivity;
import com.mydarasa.app.OnboardingActivity;
import com.mydarasa.app.PrefManager;
import com.mydarasa.app.R;
import com.mydarasa.app.RequestNewToken;

public class SplashActivity extends AppCompatActivity {


    private int splashTime =3000;
    private Handler handler;
    private Runnable runnable;
    private PrefManager prefManager;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        prefManager = new PrefManager(this);


        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                goToMainActivity();

            }
        };
        handler.postDelayed(runnable,splashTime);



    }

    public void goToMainActivity(){

        long currentTime = System.currentTimeMillis();
        long tokenTime = Long.parseLong(prefManager.getTokenTime());

        Log.d("accessTime", " "+ prefManager.getTokenTime());
        Log.d("accessTimec", " "+ currentTime);

        if(currentTime>tokenTime){
            RequestNewToken requestNewToken = new RequestNewToken(getApplicationContext());
            requestNewToken.getNewToken();
            //Toast.makeText(CocuricularActivity.this, "" + currentTime, Toast.LENGTH_SHORT).show();
        }
        if(!prefManager.isFirstTimeLaunch()){
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            finish();
        } else {
            startActivity(new Intent(SplashActivity.this, OnboardingActivity.class));
            finish();
        }


    }

    private void launchLoginScreen() {
        prefManager.setFirstTimeLaunch(false);
        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        finish();
    }
}
