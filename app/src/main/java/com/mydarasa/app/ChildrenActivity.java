package com.mydarasa.app;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import network.ConnectivityReceiver;
import network.CustomNoInternetDialog;
import network.MyApplication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mydarasa.app.login.TokenDetails;
import com.mydarasa.app.refreshtoken.RefreshTokenModel;
import com.mydarasa.app.student.StudentAdapter;
import com.mydarasa.app.student.StudentListModel;
import com.mydarasa.app.student.StudentModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChildrenActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener{

    private RecyclerView rvChildren;
    StudentAdapter studentAdapter;
    List<StudentModel> studentList = new ArrayList<>();

    private ProgressBar progressBar;
    Toolbar myToolbar;
    PrefManager prefManager;

    String accessToken = " ";
    String refreshToken = " ";

    CustomNoInternetDialog customNoInternetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_children);

        rvChildren = (RecyclerView) findViewById(R.id.rv_children);
        myToolbar =  findViewById(R.id.toolbar);
        progressBar = findViewById(R.id.progressbar);
        customNoInternetDialog = findViewById(R.id.custom_dialog);
        setSupportActionBar(myToolbar);

        ActionBar actionBar = getSupportActionBar();
        //actionBar.setHomeAsUpIndicator(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Children");

        prefManager = new PrefManager(this);

        accessToken = prefManager.getAccessToken();
        refreshToken = prefManager.getRefreshToken();




        rvChildren.setHasFixedSize(true);
        studentAdapter = new StudentAdapter(this, studentList);

        rvChildren.setAdapter(studentAdapter);
        rvChildren.setLayoutManager(new LinearLayoutManager(this));
        rvChildren.setItemAnimator(new DefaultItemAnimator());

        checkConnection();




     /**   long currentTime = System.currentTimeMillis();
        long tokenTime = Long.parseLong(prefManager.getTokenTime());

        Log.d("accessTime", " "+ prefManager.getTokenTime());
        Log.d("accessTimec", " "+ currentTime);

        if(currentTime>tokenTime){
            RequestNewToken requestNewToken = new RequestNewToken(getApplicationContext());
            requestNewToken.getNewToken();
            PrefManager prefManager1 = new PrefManager(this);
            accessToken = prefManager1.getAccessToken();
            refreshToken = prefManager1.getRefreshToken();
            recreate();
            //Toast.makeText(CocuricularActivity.this, "" + currentTime, Toast.LENGTH_SHORT).show();
        } **/

    // customNoInternetDialog.setVisibility(View.VISIBLE);
    }

    public  void getStudents(){

        if(studentList.size()>0) {
            studentList.clear();
        }

        GetDataService retrofitService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
       // Call<StudentListModel> call = retrofitService.getStudentDetails("Bearer b4f9765d-c0cc-4133-9fa0-777bf3b1b3dc");
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
                        progressBar.setVisibility(View.INVISIBLE);
                        //StudentModel studentModel = currstudent.getStudentModel();

                        //Toast.makeText(getApplicationContext(), "" + studentModel.getFirstName(), Toast.LENGTH_LONG).show();
                       //studentList.add(studentModel);
                    }
                } else if(response.code()==401){
                  /**  getRefreshToken(refreshToken);
                    GetDataService retrofitService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

                    Call<StudentListModel> call1 = retrofitService.getStudentDetails("Bearer" +" "+ accessToken);
                    call1.enqueue(new Callback<StudentListModel>() {
                        @Override
                        public void onResponse(Call<StudentListModel> call, Response<StudentListModel> response) {

                            if(response.isSuccessful()){

                                if(response.body() != null){
                                    StudentListModel currstudent = response.body();

                                    List newlist = new ArrayList<>(Arrays.asList(currstudent.getStudentModel()));

                                    studentList.addAll(newlist);
                                    progressBar.setVisibility(View.INVISIBLE);
                                    //StudentModel studentModel = currstudent.getStudentModel();

                                    //Toast.makeText(getApplicationContext(), "" + studentModel.getFirstName(), Toast.LENGTH_LONG).show();
                                    //studentList.add(studentModel);
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<StudentListModel> call, Throwable t) {
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }); **/



                }

                Log.d("listSize", "" + studentList.size());
                studentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<StudentListModel> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void getRefreshToken(String token) {

        RefreshTokenModel refreshTokenModel = new RefreshTokenModel(token);
        GetDataService loginService =
                ServiceGenerator.createService(GetDataService.class, "mydarasa", "Magic@2019!");

        Call<RefreshTokenModel> call = loginService.getRefreshToken(refreshTokenModel);
        call.enqueue(new Callback<RefreshTokenModel>() {
            @Override
            public void onResponse(Call<RefreshTokenModel> call, Response<RefreshTokenModel> response) {

                if(response.isSuccessful()){

                    TokenDetails details = new TokenDetails();
                    details = response.body().getTokenDetails();

                    String newAccessToken = details.getAccessToken();
                    prefManager.setAccessToken(newAccessToken);
                    String newRefreshToken = details.getRefreshToken();
                    prefManager.setRefreshToken(newRefreshToken);


                    Toast.makeText(getApplicationContext(), "" + "success", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<RefreshTokenModel> call, Throwable t) {

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        getStudents();
        MyApplication.getInstance().setConnectivityListener(this);

    }

    private void showcustomDialog(boolean isConnected){

        if(!isConnected) {
            customNoInternetDialog.setVisibility(View.VISIBLE);
        }
    }



    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showcustomDialog(isConnected);
    }

    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showcustomDialog(isConnected);
    }


}
