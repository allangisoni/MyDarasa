package com.mydarasa.app;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.mydarasa.app.login.TokenDetails;
import com.mydarasa.app.login.UserLoginModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public  class RequestNewToken {

    private static PrefManager prefManager;
    private static Context mcontext;

    public RequestNewToken (Context context){
        mcontext = context;

    }

    public  void getNewToken(){
        prefManager = new PrefManager(mcontext);
        UserLoginModel userLoginModel = new UserLoginModel("1234", "allan@gmail.com");

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

                    Log.d("token", "" + details.getAccessToken());
                    Log.d("token", "" + details.getTokenType());
                    Log.d("token", "" + details.getRefreshToken());
                    Log.d("token", "" + details.getExpiryTime());
                    Log.d("token", "" + details.getScope());

                    prefManager.setAccessToken(details.getAccessToken());
                    prefManager.setRefreshToken(details.getRefreshToken());
                    prefManager.setGuardianId(response.body().getGuardianNo());
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

}
