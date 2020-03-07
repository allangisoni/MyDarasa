package com.mydarasa.app.cocurricular;

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
import com.mydarasa.app.events.EventListModel;
import com.mydarasa.app.events.EventsAdapter;
import com.mydarasa.app.student.StudentListModel;
import com.mydarasa.app.student.StudentModel;
import com.mydarasa.app.student.StudentPickerAdapter;
import com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CocuricularActivity extends AppCompatActivity implements StudentPickerAdapter.OnItemClickListener {

    private RecyclerView rvCocurricular;
    private MultiSnapRecyclerView rvStudentPicker;
    private CocurricularAdapter cocurricularAdapter;
    private StudentPickerAdapter studentPickerAdapter;


    private ProgressBar progressBar;
    List<CocurricularModel> cocurricularModelList = new ArrayList<>();
    List<StudentModel> studentList = new ArrayList<>();

    Toolbar myToolbar;
    PrefManager prefManager;
    String accessToken = " ";
    String refreshToken = " ";

    String schoolType = "";
    TextView tvStudName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cocuricular);

        rvCocurricular = findViewById(R.id.rvCocurricular);
        myToolbar =  findViewById(R.id.toolbar);
        progressBar = findViewById(R.id.progressbar);
        rvStudentPicker = findViewById(R.id.rvStudentPicker);
        tvStudName = findViewById(R.id.tvStudName);
        setSupportActionBar(myToolbar);

        ActionBar actionBar = getSupportActionBar();
        //actionBar.setHomeAsUpIndicator(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Cocurriculars");
        prefManager = new PrefManager(this);

        accessToken = prefManager.getAccessToken();
        refreshToken = prefManager.getRefreshToken();

        progressBar.setVisibility(View.VISIBLE);

        if(getIntent().getExtras() != null ) {
            schoolType = getIntent().getStringExtra("schoolType");
        }
       // getCocurriculars();

        rvCocurricular.setHasFixedSize(true);
        cocurricularAdapter = new CocurricularAdapter(this, cocurricularModelList);

        rvCocurricular.setAdapter(cocurricularAdapter);
        rvCocurricular.setLayoutManager(new LinearLayoutManager(this));
        rvCocurricular.setItemAnimator(new DefaultItemAnimator());

        rvStudentPicker.setHasFixedSize(true);
        studentPickerAdapter = new StudentPickerAdapter(studentList, getApplicationContext(), this);

        rvStudentPicker.setAdapter(studentPickerAdapter);
        MultiSnapRecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvStudentPicker.setLayoutManager(layoutManager);
        rvStudentPicker.setItemAnimator(new DefaultItemAnimator());




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

    private void getCocurriculars(final String studentName) {

        if(cocurricularModelList.size()>0){
            cocurricularModelList.clear();
        }

        GetDataService retrofitService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<CocurricularListModel> call = retrofitService.getCocuricularActivities("Bearer" +" "+ accessToken);

        call.enqueue(new Callback<CocurricularListModel>() {
            @Override
            public void onResponse(Call<CocurricularListModel> call, Response<CocurricularListModel> response) {
                Log.d("cocurricularresponse", "" + response.code());
                Log.d("cocurricularresponse", "" + response.isSuccessful());

                if(response.isSuccessful()){

                    if(response.body() != null){

                        CocurricularListModel cocurricularList = response.body();
                        //EventsModel[] events =eventListModel.getEventsModel();

                        List newlist = new ArrayList<>(Arrays.asList(cocurricularList.getCocurricularItemList()));
                        List<CocurricularItemList> mCocurricularListModel = new ArrayList<>();
                        mCocurricularListModel.addAll(newlist);



                           for (CocurricularItemList model : mCocurricularListModel){

                               if(model.getSchoolTypeNo().equals(schoolType) && schoolType.equals("1")) {
                                   for (CocurricularModel mCocurricular : model.getCocurricularModel()) {

                                       if(studentName.equals("all")){
                                           cocurricularModelList.add(mCocurricular);

                                       }
                                       else{
                                           if(studentName != null) {
                                               if (mCocurricular.getStudentName().toLowerCase().equals(studentName.toLowerCase())) {
                                                   cocurricularModelList.add(mCocurricular);
                                               }
                                           }

                                       }

                                   }
                               }
                               else if(model.getSchoolTypeNo().equals(schoolType) && schoolType.equals("2")){
                                   for (SchoolData schoolData : model.getSchoolData()) {
                                       CocurricularModel model1 = new CocurricularModel();
                                       model1.setName(schoolData.getName());
                                       model1.setStudentName(schoolData.getStudentName());
                                       model1.setStudentNo(schoolData.getStudentNo());
                                       model1.setSchoolTypeNo("2");

                                       if(model.getSchoolTypeNo().equals(schoolType) ) {

                                           if(studentName.equals("all")){
                                               cocurricularModelList.add(model1);
                                           }
                                           else{
                                               if(studentName != null) {
                                                   if (model1.getStudentName().toLowerCase().equals(studentName.toLowerCase())) {
                                                       cocurricularModelList.add(model1);
                                                   }
                                               }

                                           }
                                           //cocurricularModelList.add(model1);
                                       }
                                   }
                               } else{

                               }

                           }



                        //cocurricularModelsList.addAll(cocurricularList.getCocurricularListModel())

                        progressBar.setVisibility(View.INVISIBLE);
                        //cocurricularModelList.addAll(newlist);

                    }


                }


                Log.d("eventlistSize", "" + cocurricularModelList.size());
                cocurricularAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<CocurricularListModel> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), "Failed" + t.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getStudents();
        getCocurriculars("all");
    }



    @Override
    public void onItemClick(StudentModel studentPickerModel, int pos) {
        tvStudName.setVisibility(View.VISIBLE);
        tvStudName.setText(null);
        tvStudName.setText(studentPickerModel.getName());
        progressBar.setVisibility(View.VISIBLE);
        getCocurriculars(studentPickerModel.getName());
        Log.d("studentpick", "Name"+" " + studentPickerModel.getName() +" "+"Id" + studentPickerModel.getId()+" "+ "Pos " +pos);
        //Toast.makeText(ProgressReportsActivity.this, "Position" + pos , Toast.LENGTH_LONG).show();
    }
}
