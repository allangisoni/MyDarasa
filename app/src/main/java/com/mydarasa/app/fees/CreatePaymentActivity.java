package com.mydarasa.app.fees;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.mydarasa.app.GetDataService;
import com.mydarasa.app.PrefManager;
import com.mydarasa.app.R;
import com.mydarasa.app.RegisterActivity;
import com.mydarasa.app.RetrofitClientInstance;
import com.mydarasa.app.student.StudentListModel;
import com.mydarasa.app.student.StudentModel;
import com.mydarasa.app.student.StudentPickerAdapter;
import com.squareup.picasso.Picasso;
import com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.engine.impl.PicassoEngine;


import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class CreatePaymentActivity extends AppCompatActivity  implements StudentPickerAdapter.OnItemClickListener,
DatePickerDialog.OnDateSetListener{

    Toolbar myToolbar;
    PrefManager prefManager;
    String accessToken = " ";
    String refreshToken = " ";

    Button btnSave;

    EditText etStudentName, etAmount, etPaymentType, etPaymentAtt, etPaymentDate,etReferenceNo;
    ImageView ivFeeImage, ivAttachImage;
    TextView tvAttachImage;

    int READ_IMAGE_PERMISSION = 100;
    int REQUEST_CODE_CHOOSE =110;
    int checkedItem ;
    int activeStudent;

    private MultiSnapRecyclerView rvStudentPicker;
    private StudentPickerAdapter studentPickerAdapter;
    List<StudentModel> studentList = new ArrayList<>();

    TextView tvStudOption;
    String studId;

    int calDay, calMonth, calYear;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_payment);

        myToolbar =  findViewById(R.id.toolbar);
        btnSave = findViewById(R.id.btnCreatePayment);
        etStudentName = findViewById(R.id.etStudentName);
        etAmount = findViewById(R.id.etPaymentAmount);
        etPaymentType = findViewById(R.id.etPaymentType);
        rvStudentPicker = findViewById(R.id.rvStudentPicker);
        tvStudOption = findViewById(R.id.tvStudName);
        ivAttachImage= findViewById(R.id.ivAttach);
        ivFeeImage = findViewById(R.id.ivfeeImage);
      //  etPaymentAtt = findViewById(R.id.etPaymentAtt);
        etPaymentDate =findViewById(R.id.etPaymentDate);
        etReferenceNo =findViewById(R.id.etReferenceNo);
        tvAttachImage = findViewById(R.id.tvAttachImage);
        rvStudentPicker.setVisibility(View.GONE);

        checkedItem =1;
        activeStudent = 0;
        studId = "";
        setSupportActionBar(myToolbar);

        ActionBar actionBar = getSupportActionBar();
        //actionBar.setHomeAsUpIndicator(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Create Payment");
        prefManager = new PrefManager(this);

        accessToken = prefManager.getAccessToken();
        refreshToken = prefManager.getRefreshToken();

        rvStudentPicker.setVisibility(View.VISIBLE);
        tvStudOption.setText("Select child:");
        tvStudOption.setVisibility(View.VISIBLE);

        etStudentName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // rvStudentPicker.setVisibility(View.VISIBLE);
                //tvStudOption.setText("Select child:");
                //tvStudOption.setVisibility(View.VISIBLE);
            }
        });

        getStudents();
        rvStudentPicker.setHasFixedSize(true);

        studentPickerAdapter = new StudentPickerAdapter(studentList, getApplicationContext(), this);



        rvStudentPicker.setAdapter(studentPickerAdapter);
        MultiSnapRecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvStudentPicker.setLayoutManager(layoutManager);
        rvStudentPicker.setItemAnimator(new DefaultItemAnimator());

        etPaymentType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder =new AlertDialog.Builder(CreatePaymentActivity.this, R.style.AlertDialogCustom);
                builder.setTitle("Select payment type");



                final String[] RSVPOptions = {"Cheque Deposit", "Mpesa", "Direct Deposit"};


                builder.setSingleChoiceItems(RSVPOptions, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(which ==1){
                           // etPaymentType.setText("Mpesa Deposit");
                            checkedItem =1;
                        } else if(which==0){
                            //etPaymentType.setText("Cheque Deposit");
                            checkedItem =0;
                        } else{
                            //etPaymentType.setText("Direct Deposit");
                            checkedItem =2;
                        }

                    }
                } );

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (checkedItem == 0) {
                            etPaymentType.setText("Cheque Deposit");
                        } else if (checkedItem==1){
                            etPaymentType.setText("Mpesa Deposit");
                        }else {
                            etPaymentType.setText("Direct Deposit");
                        }
                    }
                });

                builder.setNegativeButton("CANCEL", null);

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createPayment();

            }
        });

        ivAttachImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             getImage();
            }
        });
        tvAttachImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImage();
            }
        });

        ivFeeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(CreatePaymentActivity.this, R.style.AlertDialogCustom)
                .setTitle("Delete Image")
                .setMessage("Are you sure you want to delete this image?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    //Picasso.with(CreatePaymentActivity.this).load("").into(ivFeeImage);
                    ivFeeImage.setImageDrawable(null);
                    ivFeeImage.setVisibility(View.GONE);
                    //etPaymentAtt.setText(" ");
                    }
                }).setNegativeButton("NO", null).show();
            }
        });
        Calendar today = Calendar.getInstance();
        calDay =0;
        calMonth = 0;
        calYear = 0;
        calMonth = today.get(Calendar.MONTH) + 1;
        calDay = today.get(Calendar.DATE);
        calYear = today.get(Calendar.YEAR);
        etPaymentDate.setText(" "+calYear+"-"+Integer.toString(calMonth) +"-"+ calDay);
        etPaymentDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                       CreatePaymentActivity.this,
                        calYear, // Initial year selection
                        calMonth -1, // Initial month selection
                        calDay // Inital day selection

                );


// If you're calling this from a support Fragment
                dpd.show(getSupportFragmentManager(), "Datepickerdialog");
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {

        onBackPressed();
        return true;
    }

    private void createPayment(){

        Boolean isempty = true;
        if(etStudentName.getText().toString().equals("") || etStudentName.getText().length() == 0){
            Toast.makeText(CreatePaymentActivity.this, "Please select student", Toast.LENGTH_SHORT).show();
            isempty = false;
        } else if(etPaymentType.getText().toString() .equals("") || etPaymentType.getText().length() == 0){
            Toast.makeText(CreatePaymentActivity.this, "Please select payment type", Toast.LENGTH_SHORT).show();
            isempty =false;
        }else if(etReferenceNo.getText().toString() .equals("") || etReferenceNo.getText().length() == 0){
            Toast.makeText(CreatePaymentActivity.this, "Please enter Reference No", Toast.LENGTH_SHORT).show();
            isempty =false;
        }
        else if(etAmount.getText().toString() .equals("") || etAmount.getText().length() == 0){
            Toast.makeText(CreatePaymentActivity.this, "Please enter amount", Toast.LENGTH_SHORT).show();
            isempty =false;
        } else{
        }

        if(isempty) {
            showProgressDialog();
            FeePaymentModel feePaymentModel = new FeePaymentModel();
            String amount = "", type = "1", studentNo = " ";

            if (!etStudentName.getText().toString().equals("")) {
                studentNo = etStudentName.getText().toString();

            } else if (!etPaymentType.getText().toString().equals("")) {
                // type = etPaymentType.getText().toString();
                if (etPaymentType.getText().toString().equals("Mpesa")) {
                    type = "1";
                } else if (etPaymentType.getText().toString().equals("Bank Cheque")) {
                    type = "2";
                } else {
                    type = "3";
                }

            } else if (!etAmount.getText().toString().equals("")) {
                amount = etAmount.getText().toString();
            } else {

            }

            feePaymentModel.setAmount(amount);
            feePaymentModel.setStudentNo(studId);
            feePaymentModel.setPaymentType(type);

            GetDataService retrofitService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<FeePaymentModel> call = retrofitService.createPayment("Bearer" + " " + accessToken, feePaymentModel);
            call.enqueue(new Callback<FeePaymentModel>() {
                @Override
                public void onResponse(Call<FeePaymentModel> call, Response<FeePaymentModel> response) {
                    if (response.isSuccessful()) {
                     hideProgressDialog();
                     showcustomDialog();
                     etStudentName.setText("");
                     etPaymentType.setText("");
                     etReferenceNo.setText("");
                     etAmount.setText("");

                    }
                }

                @Override
                public void onFailure(Call<FeePaymentModel> call, Throwable t) {
                    hideProgressDialog();
                    Toast.makeText(CreatePaymentActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private   void getStudents(){

        GetDataService retrofitService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<StudentListModel> call = retrofitService.getStudentDetails("Bearer" +" "+ accessToken);

        call.enqueue(new Callback<StudentListModel>() {
            @Override
            public void onResponse(Call<StudentListModel> call, Response<StudentListModel> response) {
                Log.d("childresponse", "" + response.code());
                Log.d("childresponse", "" + response.isSuccessful());

                if(response.isSuccessful()){

                    if(response.body() != null){
                        StudentListModel currstudent = response.body();

                        List newlist = new ArrayList<>(Arrays.asList(currstudent.getStudentModel()));

                        studentList.addAll(newlist);



                        //progressBar.setVisibility(View.INVISIBLE);
                        //StudentModel studentModel = currstudent.getStudentModel();

                        //Toast.makeText(getApplicationContext(), "" + studentModel.getFirstName(), Toast.LENGTH_LONG).show();
                        //studentList.add(studentModel);
                    }
                } else if(response.code()==401){


                }

                //Log.d("listSize", "" + studentList.size());
                studentPickerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<StudentListModel> call, Throwable t) {
                //progressBar.setVisibility(View.INVISIBLE);
            }
        });

    }

    private void  getImage(){

        if(ContextCompat.checkSelfPermission(CreatePaymentActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            //permission is not granted
            //Request Permission

            ActivityCompat.requestPermissions(CreatePaymentActivity.this,
            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);


        } else{

            Matisse.from(CreatePaymentActivity.this)
                    .choose(MimeType.ofImage())
                    .theme( R.style.Matisse_Dracula)
                    .countable(true)
                    .maxSelectable(9)
                    // .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                    .gridExpectedSize(300)
                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                    .thumbnailScale(0.85f)
                    .imageEngine(new PicassoEngine())// Default is `true`
                    .forResult(REQUEST_CODE_CHOOSE);


            //permission is granted
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 100: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Matisse.from(CreatePaymentActivity.this)
                            .choose(MimeType.ofImage())
                            .theme( R.style.Matisse_Dracula)
                            .countable(true)
                            .maxSelectable(9)
                           // .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                             .gridExpectedSize(300)
                            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                            .thumbnailScale(0.85f)
                            .imageEngine(new PicassoEngine())// Default is `true`
                            .forResult(REQUEST_CODE_CHOOSE);
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {

            ivFeeImage.setVisibility(View.VISIBLE);
            List<Uri> uri = Matisse.obtainResult(data);
            //String imagePath = uri.get(0).getPath();

                for (Uri uriImage : uri) {
                    Picasso.with(CreatePaymentActivity.this).load(uriImage).into(ivFeeImage);
                    File f = new File(uriImage.getPath());
                    String imageName = f.getName();
                   // etPaymentAtt.setText(imageName);
                }


        }
    }


    private void showcustomDialog(){

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CreatePaymentActivity.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(CreatePaymentActivity.this).inflate(R.layout.custom_dialog, viewGroup, false);
        TextView tvMessage = dialogView.findViewById(R.id.tvDialog);
        tvMessage.setText("Payment Uploaded Successfully");
        alertDialogBuilder.setView(dialogView);

        AlertDialog dialog = alertDialogBuilder.create();
        dialog.show();
    }




    @Override
    public void onItemClick(StudentModel studentPickerModel, int pos) {

        Log.d("student"  , " "+ studentPickerModel.getId() + " "+ studentPickerModel.getName());
        etStudentName.setText(studentPickerModel.getName());
        studId =studentPickerModel.getId();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
         int calenderMonth = monthOfYear +1;
         calYear = year;
         calMonth = calenderMonth;
         calDay = dayOfMonth;
        etPaymentDate.setText(" "+year+"-"+calenderMonth +"-"+dayOfMonth);
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
