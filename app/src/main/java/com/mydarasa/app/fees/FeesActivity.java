package com.mydarasa.app.fees;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mydarasa.app.GetDataService;
import com.mydarasa.app.PrefManager;
import com.mydarasa.app.R;
import com.mydarasa.app.RequestNewToken;
import com.mydarasa.app.RetrofitClientInstance;
import com.mydarasa.app.events.EventsAdapter;
import com.mydarasa.app.events.EventsModel;
import com.mydarasa.app.student.StudentListModel;
import com.mydarasa.app.student.StudentModel;
import com.mydarasa.app.student.StudentPickerAdapter;
import com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FeesActivity extends AppCompatActivity implements StudentPickerAdapter.OnItemClickListener {

    private RecyclerView rvFees;
    private FeeStatementAdapter feesAdapter;
    private RecyclerView rvFeesStructure;
    private FeesStructureAdapter feesStructureAdapter;
    private ProgressBar progressBar;
    private MultiSnapRecyclerView rvStudentPicker;
    private StudentPickerAdapter studentPickerAdapter;
    List<FeeStatementModel> feeStatementModelList = new ArrayList<>();
    List<FeeStructureModel> feeStructureModelList = new ArrayList<>();
    List<StudentModel> studentList = new ArrayList<>();

    ArrayAdapter<String> arrayAdapter;
    List<String> studentsList = new ArrayList<>();

    TextView tvStudName;

    Toolbar myToolbar;
    PrefManager prefManager;
    String accessToken = " ";
    String refreshToken = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fees);

        rvFees = findViewById(R.id.rv_fees);
        rvFeesStructure = findViewById(R.id.rv_feeStructure);
        myToolbar =  findViewById(R.id.toolbar);
        progressBar = findViewById(R.id.progressbar);
        rvStudentPicker = findViewById(R.id.rvStudentPicker);
        tvStudName = findViewById(R.id.tvStudName);
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



        rvFees.setHasFixedSize(true);
        rvStudentPicker.setHasFixedSize(true);
        rvFeesStructure.setHasFixedSize(true);
        feesAdapter= new FeeStatementAdapter(this, feeStatementModelList);
        studentPickerAdapter = new StudentPickerAdapter(studentList, getApplicationContext(), this);
        feesStructureAdapter = new FeesStructureAdapter(this,feeStructureModelList);

        rvFees.setAdapter(feesAdapter);
        rvStudentPicker.setAdapter(studentPickerAdapter);
        rvFeesStructure.setAdapter(feesStructureAdapter);
        rvFees.setLayoutManager(new LinearLayoutManager(this));
        rvFeesStructure.setLayoutManager(new LinearLayoutManager(this));

        MultiSnapRecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvStudentPicker.setLayoutManager(layoutManager);
        rvFees.setItemAnimator(new DefaultItemAnimator());
        rvFeesStructure.setItemAnimator(new DefaultItemAnimator());
        rvStudentPicker.setItemAnimator(new DefaultItemAnimator());


    /**    long currentTime = System.currentTimeMillis();
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

    private   void getStudents(){

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

    private void getFeesStatement(final String studentName){
        if(feeStatementModelList.size() >0 ){
           feeStatementModelList.clear();
        }


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


                        List<FeeStatementModel> allfeeStatementModels = new ArrayList<>();
                        allfeeStatementModels.add(feeStatementList);

                        if(studentName.equals("all")){
                            feeStatementModelList.add(feeStatementList);
                            // reportsAdapter.notifyDataSetChanged();

                        }
                        else{
                            for (FeeStatementModel model: allfeeStatementModels){
                                if(model.getStudentName().toLowerCase().equals(studentName.toLowerCase())){
                                    feeStatementModelList.add(model);
                                }
                            }

                        }



                       // feeStatementModelList.add(feeStatementList);
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

    private void getFeesStructure(final String studentName){
        if(feeStructureModelList.size() >0){
            feeStructureModelList.clear();
        }

        GetDataService retrofitService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<FeeStructureModelList> call = retrofitService.getFeeStructure("Bearer" +" "+ accessToken);

        call.enqueue(new Callback<FeeStructureModelList>() {
            @Override
            public void onResponse(Call<FeeStructureModelList> call, Response<FeeStructureModelList> response) {

                if(response.isSuccessful()){

                    if(response.body() != null){
                        FeeStructureModelList feeStructureList = response.body();


                        List newlist = new ArrayList<>(Arrays.asList(feeStructureList.getFeeStructureModels()));

                        List<FeeStructureModel> allfeeStructureModels = new ArrayList<>();
                        allfeeStructureModels.addAll(newlist);

                        if(studentName.equals("all")){
                            feeStructureModelList.addAll(newlist);
                            // reportsAdapter.notifyDataSetChanged();
                        }
                        else{
                            for (FeeStructureModel model: allfeeStructureModels){
                                if(model.getStudentName().toLowerCase().equals(studentName.toLowerCase())){
                                    feeStructureModelList.add(model);
                                }
                            }

                        }



                       // feeStructureModelList.addAll(newlist);
                        progressBar.setVisibility(View.INVISIBLE);



                        //Toast.makeText(getApplicationContext(), "" + studentModel.getFirstName(), Toast.LENGTH_LONG).show();
                        //studentList.add(studentModel);
                    }
                }


                Log.d("listSize", "" + feeStructureModelList.size());
                feesStructureAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<FeeStructureModelList> call, Throwable t) {

            }
        });
    }




    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onItemClick(StudentModel studentPickerModel, int pos) {
        tvStudName.setVisibility(View.VISIBLE);
        tvStudName.setText(null);
        tvStudName.setText(studentPickerModel.getName());
        progressBar.setVisibility(View.VISIBLE);
        getFeesStatement(studentPickerModel.getName());
        getFeesStructure(studentPickerModel.getName());
        Log.d("studentpick", "Name"+" " + studentPickerModel.getName() +" "+"Id" + studentPickerModel.getId()+" "+ "Pos " +pos);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(studentList.size()>0){
            studentList.clear();
        }
        getStudents();

        getFeesStatement("all");

        getFeesStructure("all");
    }
}
