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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mydarasa.app.login.UserLoginModel;
import com.mydarasa.app.register.GuardianModel;

import java.security.Guard;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

     TextInputEditText etemail;
     TextInputEditText etFirstName;
     TextInputEditText etLastName;
     TextInputEditText etPhoneNumber;
     TextInputEditText etPostalAddress;
     TextInputEditText etPhysicalAddress;
     TextInputEditText etPin;

     TextInputLayout inputLayoutFName, inputLayoutLName, inputLayoutPhoneNo,
             inputLayoutPostalAdd, inputLayoutPhysicalAdd, inputLayoutEmail, inputLayoutPin;

    private Button btnRegister;
    private TextView tvLogin;

    String email = " ";
    String firstName, lastName, phoneNumber, postalAddress, physicalAddress, pin;

    private ProgressDialog mProgressDialog;

    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    private Matcher matcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

       getUiViews();
       etFirstName.addTextChangedListener(inputWatcher);
       etLastName.addTextChangedListener(inputWatcher);
       etPhoneNumber.addTextChangedListener(inputWatcher);
       etPostalAddress.addTextChangedListener(inputWatcher);
       etPhysicalAddress.addTextChangedListener(inputWatcher);
       etemail.addTextChangedListener(inputWatcher);
       etPin.addTextChangedListener(inputWatcher);



       btnRegister.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {




               email = etemail.getText().toString();
               firstName = etFirstName.getText().toString();
               lastName = etLastName.getText().toString();
               phoneNumber = etPhoneNumber.getText().toString();
               postalAddress = etPostalAddress.getText().toString();
               physicalAddress = etPhysicalAddress.getText().toString();
               pin = etPin.getText().toString().trim();



              /** Log.d("Guardian", "email is"+email+"" + firstName+ " "+ lastName+ " "+ phoneNumber + " " + physicalAddress+
                       " " + pin +" "+ postalAddress); **/

              if(checkViews()== false && validateEmail(email) ) {
                  showProgressDialog();
                  GuardianModel guardian = new GuardianModel(email, firstName, lastName, "string", phoneNumber, physicalAddress, pin, postalAddress);

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
                                      finish();
                                  }
                              });


                          } else {
                              hideProgressDialog();
                              Toast.makeText(RegisterActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                          }

                      }

                      @Override
                      public void onFailure(Call<GuardianModel> call, Throwable t) {
                          hideProgressDialog();
                      }
                  });
              } else{
                  inputLayoutEmail.setErrorEnabled(true);
                  inputLayoutEmail.setError("Incorrect email format");
              }


           }
       });

       tvLogin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
               finish();
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
        inputLayoutFName =findViewById(R.id.textInputLayoutFirstName);
        inputLayoutLName = findViewById(R.id.textInputLayoutLastName);
        inputLayoutPhoneNo = findViewById(R.id.textInputLayoutphoneno);
        inputLayoutPostalAdd = findViewById(R.id.textInputLayoutpostaladdress);
        inputLayoutPhysicalAdd = findViewById(R.id.textInputLayoutphysicaladdress);
        inputLayoutEmail = findViewById(R.id.textInputLayoutemail);
        inputLayoutPin = findViewById(R.id.textInputLayoutpin);
    }

    private boolean checkViews(){
        Boolean isEmpty = false;
        email = etemail.getText().toString();
        firstName = etFirstName.getText().toString();
        lastName = etLastName.getText().toString();
        phoneNumber = etPhoneNumber.getText().toString();
        postalAddress = etPostalAddress.getText().toString();
        physicalAddress = etPhysicalAddress.getText().toString();
        pin = etPin.getText().toString().trim();

        if(firstName.equals("")){
            isEmpty =true;
           // inputLayoutFName.setErrorEnabled(true);

            inputLayoutFName.setError("First name is missing");
        }
        if(lastName.equals("")){
            isEmpty =true;
            inputLayoutLName.setErrorEnabled(true);
            inputLayoutLName.setError("Last name is missing");
        }

        if(phoneNumber.equals("")){
            isEmpty =true;
            inputLayoutPhoneNo.setErrorEnabled(true);
            inputLayoutPhoneNo.setError("Phone No is missing");
        }

        if(postalAddress.equals("")){
            isEmpty =true;
            inputLayoutPostalAdd.setErrorEnabled(true);
            inputLayoutPostalAdd.setError("Postal address is missing");
        }

        if(physicalAddress.equals("")){
            isEmpty =true;
            inputLayoutPhysicalAdd.setErrorEnabled(true);
            inputLayoutPhysicalAdd.setError("Physical address is missing");
        }


        if(email.equals("")){
            isEmpty =true;
            inputLayoutEmail.setErrorEnabled(true);
            inputLayoutEmail.setError("Email is missing");
        }

        if(pin.equals("")){
            isEmpty =true;
            inputLayoutPin.setErrorEnabled(true);
            inputLayoutPin.setError("Pin is missing");
        }
        return isEmpty;
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

    private final TextWatcher inputWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() == 0) {
                //inputLayoutFName.setErrorEnabled(true);
                //textInputPassword.setError("Password Required");
            } else{
                inputLayoutFName.setErrorEnabled(false);
                inputLayoutLName.setErrorEnabled(false);
                inputLayoutPhoneNo.setErrorEnabled(false);
                inputLayoutPostalAdd.setErrorEnabled(false);
                inputLayoutPhysicalAdd.setErrorEnabled(false);
                inputLayoutEmail.setErrorEnabled(false);
                inputLayoutPin.setErrorEnabled(false);
                //textView.setText("You have entered : " + passwordEditText.getText());
            }
        }
    };

    public boolean validateEmail(String email) {
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
