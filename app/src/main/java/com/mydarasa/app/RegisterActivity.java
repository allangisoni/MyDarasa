package com.mydarasa.app;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.mydarasa.app.login.UserLoginModel;
import com.mydarasa.app.register.GuardianModel;

import java.security.Guard;

public class RegisterActivity extends AppCompatActivity {

     TextInputEditText etemail;
     TextInputEditText etFirstName;
     TextInputEditText etLastName;
     TextInputEditText etPhoneNumber;
     TextInputEditText etPostalAddress;
     TextInputEditText etPhysicalAddress;
     TextInputEditText etPin;

    private Button btnRegister;
    private TextView tvLogin;

    String email = " ";
    String firstName, lastName, phoneNumber, postalAddress, physicalAddress, pin;

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

       getUiViews();


       btnRegister.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               showProgressDialog();


               email = etemail.getText().toString();
               firstName = etFirstName.getText().toString();
               lastName = etLastName.getText().toString();
               phoneNumber = etPhoneNumber.getText().toString();
               postalAddress = etPostalAddress.getText().toString();
               physicalAddress = etPhysicalAddress.getText().toString();
               pin = etPin.getText().toString().trim();


              /** Log.d("Guardian", "email is"+email+"" + firstName+ " "+ lastName+ " "+ phoneNumber + " " + physicalAddress+
                       " " + pin +" "+ postalAddress); **/
               GuardianModel guardian =  new GuardianModel(email, firstName, lastName, "string", phoneNumber, physicalAddress, pin, postalAddress);

               GuardianModel guardianModel = new GuardianModel("allangisoni@gmail.com", "allan", "hesus",
                                                               "string", "0724821898", "Nakuru", "0000",
                                                                "2349");
               GetDataService retrofitService =
                       RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
               Call<GuardianModel> call = retrofitService.registerGuardian(guardian);

               call.enqueue(new Callback<GuardianModel>() {
                   @Override
                   public void onResponse(Call<GuardianModel> call, Response<GuardianModel> response) {

                       Log.d("response", "" + response.code());
                       Log.d("response", "" + response.isSuccessful());

                       if (response.isSuccessful()) {
                           hideProgressDialog();
                           //Toast.makeText(RegisterActivity.this, "Successfully registered", Toast.LENGTH_LONG).show();
                           // final Dialog dialog = new Dialog(ProfileActivity.this);

                           AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(RegisterActivity.this);
                           ViewGroup viewGroup = findViewById(android.R.id.content);
                           View dialogView = LayoutInflater.from(RegisterActivity.this).inflate(R.layout.custom_dialog, viewGroup, false);
                           alertDialogBuilder.setView(dialogView);

                           AlertDialog dialog = alertDialogBuilder.create();
                           //TextView tvDialog = (TextView) dialog.findViewById(R.id.tvDialog);
                           //tvDialog.setText("Account created successfully!");
                           //dialog.show();
                           //dialog.setContentView(R.layout.custom_dialog);
                           //dialog.setTitle("");
                           dialog.show();
                           dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                               @Override
                               public void onDismiss(DialogInterface dialog) {
                                   startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                               }
                           });


                       }
                       else {
                           hideProgressDialog();
                           Toast.makeText(RegisterActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                       }

                   }

                   @Override
                   public void onFailure(Call<GuardianModel> call, Throwable t) {
                      hideProgressDialog();
                   }
               });


           }
       });

       tvLogin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
           }
       });

    }

    private void getUiViews(){
        etemail = findViewById(R.id.etEmailAddress);
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etPostalAddress = findViewById(R.id.etPostalAddress);
        etPhysicalAddress = findViewById(R.id.etPhysicalAddress);
        etPin = findViewById(R.id.etPin);
        btnRegister = findViewById(R.id.btn_register);
        tvLogin = findViewById(R.id.tv_login);
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

}
