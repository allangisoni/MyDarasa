package com.mydarasa.app;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Credentials;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mydarasa.app.login.LoginResponseModel;
import com.mydarasa.app.login.TokenDetails;
import com.mydarasa.app.login.UserLoginModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;

    //@BindView(R.id.etEmailAddress)
    TextInputEditText userName;
    //@BindView(R.id.etPassword)
    TextInputEditText password ;
    TextInputLayout emailInputLayout, passwordInputLayout;

    TextView tvSignUp;

    String username =" ";
    String userpassword = " ";

    private ProgressDialog mProgressDialog;

    ArrayList<UserLoginModel> loginResponseList = new ArrayList<>();

    PrefManager prefManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
       // ButterKnife.bind(this);

        btnLogin = findViewById(R.id.btn_login);
        userName = findViewById(R.id.etEmailAddress);
        password = findViewById(R.id.etPassword);
        emailInputLayout =findViewById(R.id.textInputLayoutemail);
        passwordInputLayout = findViewById(R.id.textInputLayoutpassword);
        tvSignUp = findViewById(R.id.tv_register);

        prefManager = new PrefManager(this);

        if(prefManager.isUserLoggedIn()){
            launchHomeScreen();
        }




        userName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if( userName.getText().length()>0)
                {
                    emailInputLayout.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if( password.getText().length()>0)
                {
                    passwordInputLayout.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showProgressDialog();

                //startActivity(new Intent(LoginActivity.this, MainActivity.class));
                //finish();

                String swaggerString = "mydarasa:Magic@2019!";
                byte[] data = new byte[0];
                try {
                    data = swaggerString.getBytes("UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                String base64String = Base64.encodeToString(data, Base64.NO_WRAP);
                //base64String = base64String.trim();
                base64String = "Basic" + " "+ base64String;
                Log.d("base64", base64String);
              //  UserLoginModel userLoginModel = new UserLoginModel("Pass@123", "admin@live.com");
             //  GetDataService getDataService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
             //  Call<UserLoginModel> call = getDataService.isValidUser(getAuthToken(), userLoginModel);
                /**GetDataService loginService =
                        ServiceGenerator.createService(GetDataService.class);

                Call<UserLoginModel> call = loginService.isValidUser(userLoginModel);

                call.enqueue(new Callback<UserLoginModel>() {
                    @Override
                    public void onResponse(Call<UserLoginModel>call, Response<UserLoginModel> response) {


                        Log.d("response", "" + response.code());
                        Log.d("response", "" + response.isSuccessful());
                        Log.d("response", "" + response.message());
                        if(response.isSuccessful()) {
                            UserLoginModel userLoginModel = response.body();

                           // Log.d("UserName", userLoginModel.getUserName());
                           // Log.d("Password", userLoginModel.getPassword());
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        }



                    }

                    @Override
                    public void onFailure(Call<UserLoginModel>call, Throwable t) {
                      Log.d("Error", t.getMessage().toString());
                    }
                });

                 **/
                username = userName.getText().toString();

                userpassword = password.getText().toString();

              if(username.length() != 0 && userpassword.length() !=0){
                  UserLoginModel userLoginModel = new UserLoginModel(userpassword, username);

                  GetDataService loginService =
                        ServiceGenerator.createService(GetDataService.class, "mydarasa", "Magic@2019!");
                Call<UserLoginModel> call = loginService.isValidUser(userLoginModel);
                call.enqueue(new Callback<UserLoginModel >() {
                    @Override
                    public void onResponse(Call<UserLoginModel> call, Response<UserLoginModel> response) {

                        Log.d("response", "" + response.code());
                        Log.d("response", "" + response.isSuccessful());

                        if (response.isSuccessful()) {

                            hideProgressDialog();
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
                            launchHomeScreen();

                            // user object available
                        } else {
                            hideProgressDialog();
                            //startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            //finish();
                           // Log.d("responseMessage", "" + response.body().getMessage());
                            Log.d("response", "" + response.code());
                            Toast.makeText(LoginActivity.this, "Wrong credentials entered!!", Toast.LENGTH_SHORT).show();
                            // error response, no access to resource?
                        }
                    }

                    @Override
                    public void onFailure(Call<UserLoginModel> call, Throwable t) {
                        // something went completely south (like no internet connection)
                        hideProgressDialog();
                        Log.d("Error", t.getMessage());

                        Toast.makeText(LoginActivity.this, "Something went wrong!!", Toast.LENGTH_SHORT).show();
                    }
                });

            } else if (username.length()==0 && userpassword.length() ==0) {
                  hideProgressDialog();
                  emailInputLayout.setError("Enter valid email");
                  passwordInputLayout.setError("Enter your password");
                  //Toast.makeText(LoginActivity.this, "Enter valid email and password!!", Toast.LENGTH_SHORT).show();
              }
              else if(username == null){
                  hideProgressDialog();
                  emailInputLayout.setError("Enter valid email");
                 // Toast.makeText(LoginActivity.this, "Please enter valid email!!", Toast.LENGTH_SHORT).show();

              } else {
                  hideProgressDialog();
                  passwordInputLayout.setError("Enter your password");
                  //Toast.makeText(LoginActivity.this, "Please enter your password!!", Toast.LENGTH_SHORT).show();
              }


            }

        });


        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoginActivity.this, PhoneOTPActivity.class));

            }
        });

    }

    /**
     * Show progress dialog
     */
    private void showProgressDialog () {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }
    /**
     * Hide progress dialog
     */

    private void hideProgressDialog () {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }


    /**
     * direct user to the home page
     *
     */


    private void  launchHomeScreen(){

        prefManager.setIsLoggedIn(true);
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();

    }


    public static String getAuthToken() {
        byte[] data = new byte[0];
        try {
            data = ("ongakiharon@gmail.com" + ":" + "Password93#@SP").getBytes("UTF-8");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return "Basic " + Base64.encodeToString(data, Base64.NO_WRAP);
    }
}
