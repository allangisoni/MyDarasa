package com.mydarasa.app.cocurricular;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mydarasa.app.PrefManager;
import com.mydarasa.app.R;
import com.mydarasa.app.progressreports.ProgressModel;
import com.mydarasa.app.progressreports.ProgressReportsAdapter;

import java.util.ArrayList;
import java.util.List;

public class CocurricularReport extends AppCompatActivity {
    private RecyclerView rvReports;
    private ProgressReportsAdapter reportsAdapter;


    private ProgressBar progressBar;
    List<ProgressModel> progressModelList = new ArrayList<>();
    List<ProgressModel> mRetrievedItems = new ArrayList<>();

    Toolbar myToolbar;
    PrefManager prefManager;
    String accessToken = " ";
    String refreshToken = " ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cocurricular_report);

        myToolbar =  findViewById(R.id.toolbar);
        rvReports = findViewById(R.id.rvReport);
        progressBar = findViewById(R.id.progressbar);
        setSupportActionBar(myToolbar);

        ActionBar actionBar = getSupportActionBar();
        //actionBar.setHomeAsUpIndicator(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Progress Report");
        prefManager = new PrefManager(this);
        accessToken = prefManager.getAccessToken();
        refreshToken = prefManager.getRefreshToken();


        progressBar.setVisibility(View.VISIBLE);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {

            String gsonString = bundle.getString("reportItems");
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            if(mRetrievedItems.size()>0){
                mRetrievedItems.clear();
            }
            mRetrievedItems = gson.fromJson(gsonString,new TypeToken<List<ProgressModel>>() {}.getType());
            //Toast.makeText(CocurricularTrainingActivity.this, "" + model.size(), Toast.LENGTH_LONG).show();
        }

        rvReports.setHasFixedSize(true);
        reportsAdapter = new ProgressReportsAdapter(this, progressModelList);

        rvReports.setAdapter(reportsAdapter);
        rvReports.setLayoutManager(new LinearLayoutManager(this));
        rvReports.setItemAnimator(new DefaultItemAnimator());

        getProgressReports();
    }

    private void getProgressReports(){

        if(progressModelList.size()>0){
            progressModelList.clear();
        }

        for (ProgressModel progressModel: mRetrievedItems){
                progressModelList.add(progressModel);

        }

        progressBar.setVisibility(View.INVISIBLE);
        reportsAdapter.notifyDataSetChanged();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();

    }
}
