package com.mydarasa.app;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.mydarasa.app.login.TokenDetails;
import com.mydarasa.app.login.UserLoginModel;
import com.mydarasa.app.refreshtoken.RefreshTokenModel;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public  class RequestNewToken {

    private static PrefManager prefManager;
    private static Context mcontext;
    private String token = "";

    public RequestNewToken (Context context){
        mcontext = context;

    }

    public  void getNewToken(){
        prefManager = new PrefManager(mcontext);
        UserLoginModel userLoginModel = new UserLoginModel(prefManager.getGuardianPass(), prefManager.getGuardianEmail());

        GetDataService loginService =
                ServiceGenerator.createService(GetDataService.class, "mydarasa", "Magic@2019!");
        Call<UserLoginModel> call = loginService.isValidUser(userLoginModel);
        call.enqueue(new Callback<UserLoginModel >() {
            @Override
            public void onResponse(Call<UserLoginModel> call, Response<UserLoginModel> response) {

                Log.d("response", "" + response.code());
                Log.d("response", "" + response.isSuccessful());

                if (response.isSuccessful()) {

                    Log.d("responseMessage", "" + response.body().getMessage());
                    TokenDetails details = new TokenDetails();
                    details = response.body().getTokenDetails();

                    long currentTime = System.currentTimeMillis();
                    String expireTime = " ";
                    expireTime = details.getExpiryTime();
                    long time =Long.parseLong(expireTime);
                    time = time * 1000;

                    currentTime = currentTime + time;
                    String newExpireTime = " ";
                    newExpireTime = Long.toString(currentTime);

                    Log.d("token", "" + details.getAccessToken());
                    Log.d("token", "" + details.getTokenType());
                    Log.d("token", "" + details.getRefreshToken());
                    Log.d("token", "" + currentTime);
                    Log.d("token", "" + details.getScope());

                    prefManager.setAccessToken(details.getAccessToken());
                    prefManager.setRefreshToken(details.getRefreshToken());
                    prefManager.setGuardianId(response.body().getGuardianNo());
                    prefManager.setTokenTime(newExpireTime);
                    Log.d("token_shared", prefManager.getAccessToken());


                    //startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    //finish();

                    // user object available
                } else {

                    //startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    //finish();
                    // Log.d("responseMessage", "" + response.body().getMessage());
                    Log.d("response", "" + response.code());
                    //Toast.makeText(LoginActivity.this, "Wrong credentials entered!!", Toast.LENGTH_SHORT).show();
                    // error response, no access to resource?
                }
            }

            @Override
            public void onFailure(Call<UserLoginModel> call, Throwable t) {
                // something went completely south (like no internet connection)
                //hideProgressDialog();
                Log.d("Error", t.getMessage());

                //Toast.makeText(LoginActivity.this, "Something went wrong!!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public  void getToken() {

        prefManager = new PrefManager(mcontext);
        token = " ";
        //UserLoginModel userLoginModel = new UserLoginModel("1234", "allang@gmail.com");

        RefreshTokenModel refreshTokenModel = new RefreshTokenModel(prefManager.getRefreshToken());

        GetDataService loginService =
                ServiceGenerator.createService(GetDataService.class, "mydarasa", "Magic@2019!");
        Call<RefreshTokenModel> call = loginService.getRefreshToken(refreshTokenModel);



         call.enqueue(new Callback<RefreshTokenModel >() {
            @Override
            public void onResponse(Call<RefreshTokenModel> call, Response<RefreshTokenModel> response) {

                Log.d("response", "" + response.code());
                Log.d("response", "" + response.isSuccessful());

                if (response.isSuccessful()) {

                    Log.d("responseMessage", "" + response.body());
                    TokenDetails details = new TokenDetails();
                    details = response.body().getTokenDetails();

                    long currentTime = System.currentTimeMillis();
                    String expireTime = " ";
                    expireTime = details.getExpiryTime();
                    long time =Long.parseLong(expireTime);
                    time = time * 1000;

                    currentTime = currentTime + time;
                    String newExpireTime = " ";
                    newExpireTime = Long.toString(currentTime);

                    Log.d("token", "" + details.getAccessToken());
                    Log.d("token", "" + details.getTokenType());
                    Log.d("token", "" + details.getRefreshToken());
                    Log.d("token", "" + currentTime);
                    Log.d("token", "" + details.getScope());

                    prefManager.setAccessToken(details.getAccessToken());
                    prefManager.setRefreshToken(details.getRefreshToken());
                  //  prefManager.setGuardianId(response.body().getGuardianNo());
                    prefManager.setTokenTime(newExpireTime);
                    Log.d("token_shared", prefManager.getAccessToken());



                } else {

                    //startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    //finish();
                    // Log.d("responseMessage", "" + response.body().getMessage());
                    Log.d("response", "" + response.code());
                    //Toast.makeText(LoginActivity.this, "Wrong credentials entered!!", Toast.LENGTH_SHORT).show();
                    // error response, no access to resource?
                }
            }

            @Override
            public void onFailure(Call<RefreshTokenModel> call, Throwable t) {
                // something went completely south (like no internet connection)
                //hideProgressDialog();
                Log.d("Error", t.getMessage());

                //Toast.makeText(LoginActivity.this, "Something went wrong!!", Toast.LENGTH_SHORT).show();
            }
        });

       // return token;
    }

}
