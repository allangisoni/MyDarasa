package com.mydarasa.app.progressreports;

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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mydarasa.app.GetDataService;
import com.mydarasa.app.PrefManager;
import com.mydarasa.app.R;
import com.mydarasa.app.RequestNewToken;
import com.mydarasa.app.RetrofitClientInstance;
import com.mydarasa.app.cocurricular.CocurricularAdapter;
import com.mydarasa.app.cocurricular.CocurricularModel;
import com.mydarasa.app.student.StudentListModel;
import com.mydarasa.app.student.StudentModel;
import com.mydarasa.app.student.StudentPickerAdapter;
import com.mydarasa.app.student.StudentPickerModel;
import com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProgressReportsActivity extends AppCompatActivity implements StudentPickerAdapter.OnItemClickListener {

    private RecyclerView rvProgressReports;
    private ProgressReportsAdapter reportsAdapter;
    private MultiSnapRecyclerView rvStudentPicker;
    private StudentPickerAdapter studentPickerAdapter;

    private ProgressBar progressBar;
    List<ProgressModel> progressModelList = new ArrayList<>();

    List<StudentModel> studentList = new ArrayList<>();
    TextView tvStudName;

    Toolbar myToolbar;
    PrefManager prefManager;
    String accessToken = " ";
    String refreshToken = " ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progressreports);


        rvProgressReports = findViewById(R.id.rvProgressReports);
        myToolbar =  findViewById(R.id.toolbar);
        progressBar = findViewById(R.id.progressbar);
        rvStudentPicker = findViewById(R.id.rvStudentPicker);
        tvStudName = findViewById(R.id.tvStudName);
        setSupportActionBar(myToolbar);

        ActionBar actionBar = getSupportActionBar();
        //actionBar.setHomeAsUpIndicator(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Progress Reports");
        prefManager = new PrefManager(this);

        accessToken = prefManager.getAccessToken();
        refreshToken = prefManager.getRefreshToken();

        progressBar.setVisibility(View.VISIBLE);


        rvProgressReports.setHasFixedSize(true);
        rvStudentPicker.setHasFixedSize(true);

        reportsAdapter = new ProgressReportsAdapter(this, progressModelList);
        studentPickerAdapter = new StudentPickerAdapter(studentList, getApplicationContext(), this);

        rvProgressReports.setAdapter(reportsAdapter);
        rvStudentPicker.setAdapter(studentPickerAdapter);
        rvProgressReports.setLayoutManager(new LinearLayoutManager(this));
        MultiSnapRecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvStudentPicker.setLayoutManager(layoutManager);
        rvProgressReports.setItemAnimator(new DefaultItemAnimator());
        rvStudentPicker.setItemAnimator(new DefaultItemAnimator());

      /**  long currentTime = System.currentTimeMillis();
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


    }

    private void getProgressReports(final String studentName){
        if(progressModelList.size()>0){
            progressModelList.clear();
        }


        GetDataService retrofitService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<ProgressRListModel> call = retrofitService.getProgressReports("Bearer" +" "+ accessToken);
        call.enqueue(new Callback<ProgressRListModel>() {
            @Override
            public void onResponse(Call<ProgressRListModel> call, Response<ProgressRListModel> response) {
                Log.d("reportsresponse", "" + response.code());
                Log.d("reportsresponse", "" + response.isSuccessful());

                if (response.isSuccessful()) {

                    if (response.body() != null) {

                        ProgressRListModel progressRListModel = response.body();
                        List newlist = new ArrayList<>(Arrays.asList(progressRListModel.getProgressModels()));
                        progressBar.setVisibility(View.INVISIBLE);

                        List<ProgressModel> allprogressModelList = new ArrayList<>();
                        allprogressModelList.addAll(newlist);

                        if(studentName.equals("all")){
                            progressModelList.addAll(newlist);
                           // reportsAdapter.notifyDataSetChanged();

                        }
                        else{
                            for (ProgressModel progressModel: allprogressModelList){
                                if(progressModel.getStudentName().toLowerCase().equals(studentName.toLowerCase())){
                                    progressModelList.add(progressModel);
                                }
                            }

                        }

                    }

                    reportsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ProgressRListModel> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
               // Toast.makeText(getApplicationContext(), "Failed" + t.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });

    }


    public  void getStudents(){

        if(studentList.size()>0){
            studentList.clear();
        }

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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


     public void getStudentList(){

        StudentModel model = new StudentModel();
         model.setName("Allan");
         model.setId("0");
         StudentModel model1 = new StudentModel();
         model1.setName("Benito Muuo");
         model1.setId("9");
         StudentModel model2 = new StudentModel();
         model2.setName("Haron");
         model2.setId("5");

         studentList.add(model);
         studentList.add(model1);
         studentList.add(model2);
        // studentPickerAdapter.notifyDataSetChanged();
     }


    @Override
    public void onItemClick(StudentModel studentPickerModel, int pos) {

        tvStudName.setVisibility(View.VISIBLE);
        tvStudName.setText(null);
        tvStudName.setText(studentPickerModel.getName());
        progressBar.setVisibility(View.VISIBLE);
        getProgressReports(studentPickerModel.getName());
        Log.d("studentpick", "Name"+" " + studentPickerModel.getName() +" "+"Id" + studentPickerModel.getId()+" "+ "Pos " +pos);
        //Toast.makeText(ProgressReportsActivity.this, "Position" + pos , Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getStudents();
        //getStudentList();
        getProgressReports("all");
    }
}
