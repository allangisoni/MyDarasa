package com.mydarasa.app.fees;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.mydarasa.app.GetDataService;
import com.mydarasa.app.PrefManager;
import com.mydarasa.app.R;
import com.mydarasa.app.RetrofitClientInstance;
import com.mydarasa.app.events.EventsAdapter;
import com.mydarasa.app.events.EventsModel;
import com.mydarasa.app.student.StudentListModel;
import com.mydarasa.app.student.StudentModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FeesActivity extends AppCompatActivity {

    private RecyclerView rvFees;
    private FeeStatementAdapter feesAdapter;
    private ProgressBar progressBar;
    private Spinner spinnerStudents;
    List<FeeStatementModel> feeStatementModelList = new ArrayList<>();
    List<StudentModel> studentList = new ArrayList<>();

    ArrayAdapter<String> arrayAdapter;
    List<String> studentsList = new ArrayList<>();

    Toolbar myToolbar;
    PrefManager prefManager;
    String accessToken = " ";
    String refreshToken = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fees);

        rvFees = findViewById(R.id.rv_fees);
        myToolbar =  findViewById(R.id.toolbar);
        progressBar = findViewById(R.id.progressbar);
        spinnerStudents = findViewById(R.id.spinnerStudent);
        setSupportActionBar(myToolbar);

        ActionBar actionBar = getSupportActionBar();
        //actionBar.setHomeAsUpIndicator(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Fees Statement");
        prefManager = new PrefManager(this);

        accessToken = prefManager.getAccessToken();
        refreshToken = prefManager.getRefreshToken();

        progressBar.setVisibility(View.VISIBLE);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, studentsList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerStudents.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        getStudents();
        spinnerStudents.setSelection(0);

        rvFees.setHasFixedSize(true);
        feesAdapter= new FeeStatementAdapter(this, feeStatementModelList);

        rvFees.setAdapter(feesAdapter);
        rvFees.setLayoutManager(new LinearLayoutManager(this));
        rvFees.setItemAnimator(new DefaultItemAnimator());

        getFeesStatement();

    }

    public  void getStudents(){

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


                        for (StudentModel studentModel : studentList){
                            studentsList.add(studentModel.getName());
                        }
                        Toast.makeText(getApplicationContext(), "" + studentsList.size(), Toast.LENGTH_LONG).show();
                        //Toast.makeText(getApplicationContext(), "" + studentModel.getFirstName(), Toast.LENGTH_LONG).show();
                        //studentList.add(studentModel);
                    }
                }
                else if(response.code()==401) {
                    //getRefreshToken(refreshToken);
                    GetDataService retrofitService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

                    Call<StudentListModel> call1 = retrofitService.getStudentDetails("Bearer" + " " + accessToken);
                    call1.enqueue(new Callback<StudentListModel>() {
                        @Override
                        public void onResponse(Call<StudentListModel> call, Response<StudentListModel> response) {

                            if (response.isSuccessful()) {

                                if (response.body() != null) {
                                    StudentListModel currstudent = response.body();

                                    List newlist = new ArrayList<>(Arrays.asList(currstudent.getStudentModel()));

                                    studentList.addAll(newlist);
                                    progressBar.setVisibility(View.INVISIBLE);

                                    for (StudentModel studentModel : studentList) {
                                        studentsList.add(studentModel.getName());
                                    }
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
                    });


                }

                Log.d("listSize", "" + studentList.size());
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<StudentListModel> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

    }

    public void getFeesStatement(){

        GetDataService retrofitService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        // Call<StudentListModel> call = retrofitService.getStudentDetails("Bearer b4f9765d-c0cc-4133-9fa0-777bf3b1b3dc");
        Call<FeeStatementModel> call = retrofitService.getFeeStatement("Bearer" +" "+ accessToken, 1);

        call.enqueue(new Callback<FeeStatementModel>() {
            @Override
            public void onResponse(Call<FeeStatementModel> call, Response<FeeStatementModel> response) {
                Log.d("childresponse", "" + response.code());
                Log.d("childresponse", "" + response.isSuccessful());

                if(response.isSuccessful()){

                    if(response.body() != null){
                        FeeStatementModel feeStatementList = response.body();

                        //List newlist = new ArrayList<>();
                        //newlist.add(feeStatementList);

                        feeStatementModelList.add(feeStatementList);
                        progressBar.setVisibility(View.INVISIBLE);



                        //Toast.makeText(getApplicationContext(), "" + studentModel.getFirstName(), Toast.LENGTH_LONG).show();
                        //studentList.add(studentModel);
                    }
                }


                Log.d("listSize", "" + studentList.size());
                feesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<FeeStatementModel> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
