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
import android.widget.Toast;

import com.mydarasa.app.GetDataService;
import com.mydarasa.app.PrefManager;
import com.mydarasa.app.R;
import com.mydarasa.app.RetrofitClientInstance;
import com.mydarasa.app.cocurricular.CocurricularAdapter;
import com.mydarasa.app.cocurricular.CocurricularModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProgressReportsActivity extends AppCompatActivity {

    private RecyclerView rvProgressReports;
    private ProgressReportsAdapter reportsAdapter;

    private ProgressBar progressBar;
    List<ProgressModel> progressModelList = new ArrayList<>();

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
        getProgressReports();

        rvProgressReports.setHasFixedSize(true);
        reportsAdapter = new ProgressReportsAdapter(this, progressModelList);

        rvProgressReports.setAdapter(reportsAdapter);
        rvProgressReports.setLayoutManager(new LinearLayoutManager(this));
        rvProgressReports.setItemAnimator(new DefaultItemAnimator());

    }

    private void getProgressReports(){
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
                        progressModelList.addAll(newlist);
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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
