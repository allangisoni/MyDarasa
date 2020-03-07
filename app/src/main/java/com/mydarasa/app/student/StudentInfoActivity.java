package com.mydarasa.app.student;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mydarasa.app.GetDataService;
import com.mydarasa.app.PrefManager;
import com.mydarasa.app.R;
import com.mydarasa.app.RetrofitClientInstance;
import com.mydarasa.app.cocurricular.CocuricularActivity;
import com.mydarasa.app.cocurricular.CocurricularAdapter;
import com.mydarasa.app.cocurricular.CocurricularListModel;
import com.mydarasa.app.cocurricular.CocurricularModel;
import com.mydarasa.app.events.EventListModel;
import com.mydarasa.app.events.EventsActivity;
import com.mydarasa.app.events.EventsAdapter;
import com.mydarasa.app.events.EventsModel;
import com.mydarasa.app.progressreports.ProgressModel;
import com.mydarasa.app.progressreports.ProgressRListModel;
import com.mydarasa.app.progressreports.ProgressReportsActivity;
import com.mydarasa.app.progressreports.ProgressReportsAdapter;
import com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StudentInfoActivity extends AppCompatActivity {

    String studentId = " ", studentName="";
    Context context;

    private MultiSnapRecyclerView rvEvents;
    private MultiSnapRecyclerView rvCocuricular , rvProgress;
    private StudentInfoAdapter eventsAdapter;
    private CocurricularAdapter cocurricularAdapter;
    private ProgressReportsAdapter progressReportsAdapter;
    private ProgressBar progressBar;
    List<EventsModel> eventsList = new ArrayList<>();
    List<CocurricularModel> cocurricularList = new ArrayList<>();
    List<ProgressModel> progressList = new ArrayList<>();

    Toolbar myToolbar;
    PrefManager prefManager;
    String accessToken = " ";
    String refreshToken = " ";

    @BindView(R.id.tv_all_events)
    TextView tv_all_events;

    @BindView(R.id.tv_all_cocurricular)
    TextView tv_all_cocurricular;

    @BindView(R.id.tv_all_reports)
    TextView tv_all_reports;

    @BindView(R.id.tv_no_events_data)
    TextView tv_no_events_data;

    @BindView(R.id.tv_no_cocurricular_data)
    TextView tv_no_cocurricular_data;

    @BindView(R.id.tv_no_reports_data)
    TextView tv_no_reports_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_info);
        ButterKnife.bind(this);

        rvEvents = findViewById(R.id.rvStudentEvents);
        rvCocuricular = findViewById(R.id.rvStudentCocurricular);
        rvProgress = findViewById(R.id.rvStudentProgress);
        myToolbar =  findViewById(R.id.toolbar);
        progressBar = findViewById(R.id.progressbar);
        setSupportActionBar(myToolbar);

        ActionBar actionBar = getSupportActionBar();
        //actionBar.setHomeAsUpIndicator(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Student Details");

        prefManager = new PrefManager(this);
        accessToken = prefManager.getAccessToken();
        refreshToken = prefManager.getRefreshToken();


        context = StudentInfoActivity.this;
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
         studentId = bundle.getString("studentId");
         studentName = bundle.getString("studentName");
           // Toast.makeText(context, studentId, Toast.LENGTH_LONG).show();
            Log.d("studentId", "" + studentId);
        }

        getStudentEvents();
        //getProgressReports(studentName);
       // getCocurriculars();

        rvEvents.setHasFixedSize(true);
        rvCocuricular.setHasFixedSize(true);
        rvProgress.setHasFixedSize(true);
        eventsAdapter = new StudentInfoAdapter(this, eventsList);
        cocurricularAdapter = new CocurricularAdapter(this, cocurricularList);
        progressReportsAdapter = new ProgressReportsAdapter(this, progressList);

        rvEvents.setAdapter(eventsAdapter);
        rvCocuricular.setAdapter(cocurricularAdapter);
        rvProgress.setAdapter(progressReportsAdapter);
        MultiSnapRecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        MultiSnapRecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        MultiSnapRecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvEvents.setLayoutManager(layoutManager);
        rvCocuricular.setLayoutManager(layoutManager1);
        rvProgress.setLayoutManager(layoutManager2);
        rvEvents.setItemAnimator(new DefaultItemAnimator());
        rvCocuricular.setItemAnimator(new DefaultItemAnimator());
        rvProgress.setItemAnimator(new DefaultItemAnimator());

        tv_all_events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentInfoActivity.this, EventsActivity.class));
            }
        });
        tv_all_cocurricular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentInfoActivity.this, CocuricularActivity.class));
            }
        });

        tv_all_reports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentInfoActivity.this, ProgressReportsActivity.class));
            }
        });

    }


    public void getStudentEvents(){

        if(eventsList.size()>0){
            eventsList.clear();
        }

        final Long id = Long.parseLong(studentId);
        GetDataService retrofitService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<StudentInfoModel> call = retrofitService.getStudentEvents("Bearer" +" "+ accessToken, id);


        call.enqueue(new Callback<StudentInfoModel>() {
            @Override
            public void onResponse(Call<StudentInfoModel> call, Response<StudentInfoModel> response) {
                Log.d("eventresponse", "" + response.code());
                Log.d("eventresponse", "" + response.isSuccessful());

                if(response.isSuccessful()){

                    if(response.body() != null){
                        // for (EventListModel eventListModel : response.body()) {
                        EventsModel[] eventListModel = response.body().getEventsModels();
                        //EventsModel[] events =eventListModel.getEventsModel();


                        List newlist = new ArrayList<>(Arrays.asList(eventListModel));
                        progressBar.setVisibility(View.INVISIBLE);
                        eventsList.addAll(newlist);

                        CocurricularModel[] cocurricularModels = response.body().getCocurricularModels();

                        List newlist1 = new ArrayList<>(Arrays.asList(cocurricularModels));
                        cocurricularList.addAll(newlist1);


                        ProgressModel[] progressModels = response.body().getProgressModels();
                        List newList2 = new ArrayList<>(Arrays.asList(progressModels));

                        List<ProgressModel> allprogressModelList = new ArrayList<>();
                        allprogressModelList.addAll(newList2);

                        for (ProgressModel progressModel: allprogressModelList){
                            if(progressModel.getStudentName().toLowerCase().equals(studentName.toLowerCase())){
                                progressList.add(progressModel);
                            }
                        }


                        //Toast.makeText(getApplicationContext(), "" + e, Toast.LENGTH_LONG).show();
                        //eventsList.add(events);
                        //}
                    }
                } else if(response.code()==401){

                    GetDataService retrofitService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
                    Call<StudentInfoModel> call1 = retrofitService.getStudentEvents("Bearer" +" "+ accessToken, id);

                    call1.enqueue(new Callback<StudentInfoModel>() {
                        @Override
                        public void onResponse(Call<StudentInfoModel> call, Response<StudentInfoModel> response) {

                            if(response.isSuccessful()){

                                if(response.body() != null){

                                    EventsModel[] eventListModel = response.body().getEventsModels();

                                    List newlist = new ArrayList<>(Arrays.asList(eventListModel));

                                    CocurricularModel[] cocurricularModels = response.body().getCocurricularModels();

                                    List newlist1 = new ArrayList<>(Arrays.asList(cocurricularModels));
                                    cocurricularList.addAll(newlist1);

                                    progressBar.setVisibility(View.INVISIBLE);
                                    eventsList.addAll(newlist);

                                    ProgressModel[] progressModels = response.body().getProgressModels();
                                    List newList2 = new ArrayList<>(Arrays.asList(progressModels));

                                    List<ProgressModel> allprogressModelList = new ArrayList<>();
                                    allprogressModelList.addAll(newList2);

                                    for (ProgressModel progressModel: allprogressModelList){
                                        if(progressModel.getStudentName().toLowerCase().equals(studentName.toLowerCase())){
                                            progressList.add(progressModel);
                                        }
                                    }

                                    // progressList.addAll(newList2);

                                    //Toast.makeText(getApplicationContext(), "" + e, Toast.LENGTH_LONG).show();

                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<StudentInfoModel> call, Throwable t) {
                           progressBar.setVisibility(View.INVISIBLE);
                        }
                    });
                }
                if(eventsList.size() == 0){
                    tv_no_events_data.setVisibility(View.VISIBLE);
                    tv_all_events.setVisibility(View.INVISIBLE);
                }

                if(cocurricularList.size() == 0){
                    tv_no_cocurricular_data.setVisibility(View.VISIBLE);
                    tv_all_cocurricular.setVisibility(View.INVISIBLE);
                }

                if (progressList.size() == 0){
                    tv_no_reports_data.setVisibility(View.VISIBLE);
                    tv_all_reports.setVisibility(View.INVISIBLE);
                }

                Log.d("eventlistSize", "" + eventsList.size());
                eventsAdapter.notifyDataSetChanged();
                cocurricularAdapter.notifyDataSetChanged();
                progressReportsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<StudentInfoModel> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), "Failed" + t.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });


    }

    private void getCocurriculars() {

        if(cocurricularList.size()>0){
            cocurricularList.clear();
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
                        // for (EventListModel eventListModel : response.body()) {
                        CocurricularListModel cocurricularListModel = response.body();
                        //EventsModel[] events =eventListModel.getEventsModel();


                        List newlist = new ArrayList<>(Arrays.asList(cocurricularListModel.getCocurricularItemList()));
                        progressBar.setVisibility(View.INVISIBLE);
                        cocurricularList.addAll(newlist);


                        //}
                    }
                } else if(response.code()==401){


                }

                Log.d("eventlistSize", "" + cocurricularList.size());
                cocurricularAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<CocurricularListModel> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), "Failed" + t.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });


    }


    private void getProgressReports(final String studentName){

        if(progressList.size()>0){
            progressList.clear();
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
                            progressList.addAll(newlist);
                            // reportsAdapter.notifyDataSetChanged();

                        }
                        else{
                            for (ProgressModel progressModel: allprogressModelList){
                                if(progressModel.getStudentName().toLowerCase().equals(studentName.toLowerCase())){
                                    progressList.add(progressModel);
                                }
                            }

                        }

                    }

                    progressReportsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ProgressRListModel> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                // Toast.makeText(getApplicationContext(), "Failed" + t.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
