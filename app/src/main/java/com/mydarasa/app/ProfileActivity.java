package com.mydarasa.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.mydarasa.app.alerts.AlertsActivity;
import com.mydarasa.app.guardian.GuardianData;
import com.mydarasa.app.guardian.GuardianModel;
import com.mydarasa.app.guardian.GuardianUser;
import com.mydarasa.app.login.ChangePasswordActivity;
import com.mydarasa.app.login.TokenDetails;
import com.mydarasa.app.login.UserLoginModel;
import com.mydarasa.app.refreshtoken.RefreshTokenModel;
import com.mydarasa.app.settings.SettingsActivity;

import java.io.IOException;

public class ProfileActivity extends AppCompatActivity {

    PrefManager prefManager;
    String guardianId = "";
    String accessToken = " ";
    String refreshToken = " ";
    TextInputEditText etemail;
    TextInputEditText etFirstName;
    TextInputEditText etLastName;
    TextInputEditText etPhoneNumber;
    TextInputEditText etPostalAddress;
    TextInputEditText etPhysicalAddress;

    TextView tvPassChange;
    //TextInputEditText etPin;

    private Button btnSave;

    RelativeLayout btnSignOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        prefManager = new PrefManager(this);
        guardianId = prefManager.getGuardianId();
        accessToken = prefManager.getAccessToken();
        refreshToken = prefManager.getRefreshToken();
        //Toast.makeText(getApplicationContext(), "Access token " + accessToken, Toast.LENGTH_LONG).show();
        Log.d("Access", "" + accessToken);
        Log.d("Accessr", "" + refreshToken);
        Log.d("AccessTime", " "+  prefManager.getTokenTime());

        getUiViews();

        //getGuardianDetails();



        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        //Toast.makeText(ProfileActivity.this, "Home", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                        break;
                    case R.id.action_alerts:
                        startActivity(new Intent(ProfileActivity.this, AlertsActivity.class));
                       // Toast.makeText(ProfileActivity.this, "Alerts", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_settings:
                       // Toast.makeText(ProfileActivity.this, "Settings", Toast.LENGTH_SHORT).show();
                        //startActivity(new Intent(ProfileActivity.this, SettingsActivity.class));
                        break;
                }
                return true;
            }
        });

        bottomNavigationView.setSelectedItemId(R.id.action_settings);

        /**btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefManager.setIsLoggedIn(false);
                startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
            }
        }); **/

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        tvPassChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, ChangePasswordActivity.class));
            }
        });

        long currentTime = System.currentTimeMillis();
        long tokenTime = Long.parseLong(prefManager.getTokenTime());

        Log.d("accessTime", " "+ prefManager.getTokenTime());
        Log.d("accessTimec", " "+ currentTime);

      /**  if(currentTime>tokenTime){
            RequestNewToken requestNewToken = new RequestNewToken(getApplicationContext());
            requestNewToken.getNewToken();
            PrefManager prefManager1 = new PrefManager(this);
            accessToken = prefManager1.getAccessToken();
            refreshToken = prefManager1.getRefreshToken();
            recreate();
            //Toast.makeText(CocuricularActivity.this, "" + currentTime, Toast.LENGTH_SHORT).show();
        } **/


    }


    private void getUiViews(){
        etemail = findViewById(R.id.etEmailAddress);
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etPostalAddress = findViewById(R.id.etPostalAddress);
        etPhysicalAddress = findViewById(R.id.etPhysicalAddress);
        //btnSignOut = findViewById(R.id.rl_SignOut);
       // etPin = findViewById(R.id.etPin);
        btnSave = findViewById(R.id.btn_save);
        tvPassChange = findViewById(R.id.tvPassChange);

    }


    public void getGuardianDetails(){

        final Long id = Long.parseLong(guardianId);
        //getRefreshToken(refreshToken);
        GetDataService retrofitService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<GuardianData> call = retrofitService.getGuardian("Bearer" +" "+ accessToken, id);

        call.enqueue(new Callback<GuardianData>() {
            @Override
            public void onResponse(Call<GuardianData> call, Response<GuardianData> response) {
              // Toast.makeText(getApplicationContext(), "" + refreshToken, Toast.LENGTH_LONG).show();
                Log.d("profileresponse", "" + response.code());
                if(response.isSuccessful() && response.code()== 200){

                    GuardianUser guardianUser = response.body().getGuardianUser();
                    GuardianModel guardian = guardianUser.getUser();

                    etPostalAddress.setText(guardianUser.getPostalAddress());
                    etPhysicalAddress.setText(guardianUser.getPhysicalAddress());
                    etFirstName.setText(guardian.getFirstName());
                    etLastName.setText(guardian.getLastName());
                    etPhoneNumber.setText(guardian.getPhoneNumber());
                    etemail.setText(guardian.getEmail());
                    //etPin.setText("1234");
                    //getRefreshToken(refreshToken);



                }

               /** else if(response.code() == 401){

                    RequestNewToken requestNewToken = new RequestNewToken(getApplicationContext());
                    //String newToken = requestNewToken.getToken();
                    Log.d("newrequested", "" + newToken);
                    newToken = " ";
                    PrefManager prefManager1 = new PrefManager(getApplicationContext());
                    GetDataService retrofitService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
                    Call<GuardianData> call1 = retrofitService.getGuardian("Bearer" +" "+ newToken, id);
                    call1.enqueue(new Callback<GuardianData>() {
                        @Override
                        public void onResponse(Call<GuardianData> call, Response<GuardianData> response) {
                            if(response.isSuccessful()){

                                GuardianUser guardianUser = response.body().getGuardianUser();
                                GuardianModel guardian = guardianUser.getUser();

                                etPostalAddress.setText(guardianUser.getPostalAddress());
                                etPhysicalAddress.setText(guardianUser.getPhysicalAddress());
                                etFirstName.setText(guardian.getFirstName());
                                etLastName.setText(guardian.getLastName());
                                etPhoneNumber.setText(guardian.getPhoneNumber());
                                etemail.setText(guardian.getEmail());
                              //  etPin.setText("1234");
                                //getRefreshToken(refreshToken);


                                //Toast.makeText(getApplicationContext(), "" + "success", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<GuardianData> call, Throwable t) {

                        }
                    });

                }else{

                } **/

            }

            @Override
            public void onFailure(Call<GuardianData> call, Throwable t) {

            }
        });


    }

    private String getRefreshToken(String token) {

        RefreshTokenModel refreshTokenModel = new RefreshTokenModel(token);
        GetDataService loginService =
                ServiceGenerator.createService(GetDataService.class, "mydarasa", "Magic@2019!");

        Call<RefreshTokenModel> call = loginService.getRefreshToken(refreshTokenModel);
        call.enqueue(new Callback<RefreshTokenModel>() {
            @Override
            public void onResponse(Call<RefreshTokenModel> call, Response<RefreshTokenModel> response) {

                if(response.isSuccessful()){

                    TokenDetails details;
                    details = response.body().getTokenDetails();
                    PrefManager manager = new PrefManager(ProfileActivity.this);

                    String newAccessToken = details.getAccessToken();
                    manager.setAccessToken(newAccessToken);
                   // Toast.makeText(getApplicationContext(), "Access token " + newAccessToken, Toast.LENGTH_LONG).show();
                    //accessToken = newAccessToken;
                    String newRefreshToken = details.getRefreshToken();
                    manager.setRefreshToken(newRefreshToken);
                    Log.d("Access new", "" + accessToken);
                    //recreate();
                    refreshToken = newRefreshToken;


                   // Toast.makeText(getApplicationContext(), "" + "success", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<RefreshTokenModel> call, Throwable t) {

            }
        });

        return refreshToken;

    }

    @Override
    protected void onResume() {
        super.onResume();
        getGuardianDetails();
    }
}
