package com.mydarasa.app.alerts;

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
import com.mydarasa.app.RequestNewToken;
import com.mydarasa.app.RetrofitClientInstance;
import com.mydarasa.app.cocurricular.CocurricularAdapter;
import com.mydarasa.app.cocurricular.CocurricularListModel;
import com.mydarasa.app.cocurricular.CocurricularModel;
import com.mydarasa.app.events.EventListModel;
import com.mydarasa.app.events.EventsAdapter;
import com.mydarasa.app.events.EventsModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AlertsActivity extends AppCompatActivity {

    private RecyclerView rvAlert;
    private AlertsAdapter alertsAdapter;

    private ProgressBar progressBar;
    List<AlertsModel> alertsModelList = new ArrayList<>();

    Toolbar myToolbar;
    PrefManager prefManager;
    String accessToken = " ";
    String refreshToken = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerts);


        rvAlert = findViewById(R.id.rvAlerts);
        myToolbar =  findViewById(R.id.toolbar);
        progressBar = findViewById(R.id.progressbar);
        setSupportActionBar(myToolbar);

        ActionBar actionBar = getSupportActionBar();
        //actionBar.setHomeAsUpIndicator(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Alerts");
        prefManager = new PrefManager(this);

        accessToken = prefManager.getAccessToken();
        refreshToken = prefManager.getRefreshToken();

        progressBar.setVisibility(View.VISIBLE);


        rvAlert.setHasFixedSize(true);
        alertsAdapter = new AlertsAdapter(this, alertsModelList);

        rvAlert.setAdapter(alertsAdapter);
        rvAlert.setLayoutManager(new LinearLayoutManager(this));
        rvAlert.setItemAnimator(new DefaultItemAnimator());

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


    private void getAlerts() {

        if(alertsModelList.size()>0){
            alertsModelList.clear();
        }

        GetDataService retrofitService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<AlertsListModel> call = retrofitService.getAlerts("Bearer" +" "+ accessToken);


        call.enqueue(new Callback<AlertsListModel>() {
            @Override
            public void onResponse(Call<AlertsListModel> call, Response<AlertsListModel> response) {
                Log.d("cocurricularresponse", "" + response.code());
                Log.d("cocurricularresponse", "" + response.isSuccessful());

                if(response.isSuccessful()){

                    if(response.body() != null){
                        // for (EventListModel eventListModel : response.body()) {
                        AlertsListModel alertsListModel = response.body();
                        //EventsModel[] events =eventListModel.getEventsModel();


                        List newlist = new ArrayList<>(Arrays.asList(alertsListModel.getAlertsModels()));
                        progressBar.setVisibility(View.INVISIBLE);
                        alertsModelList.addAll(newlist);


                        //}
                    }
                } else if(response.code()==401){


                }

                Log.d("eventlistSize", "" + alertsModelList.size());
                alertsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<AlertsListModel> call, Throwable t) {
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
        getAlerts();
    }
}
