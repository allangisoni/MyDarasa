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
    List<EventsModel> eventsModelsList = new ArrayList<>();

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
        actionBar.setTitle("Cocurriculars");
        prefManager = new PrefManager(this);

        accessToken = prefManager.getAccessToken();
        refreshToken = prefManager.getRefreshToken();

        progressBar.setVisibility(View.VISIBLE);
        getCocurriculars();

        rvAlert.setHasFixedSize(true);
        alertsAdapter = new AlertsAdapter(this, eventsModelsList);

        rvAlert.setAdapter(alertsAdapter);
        rvAlert.setLayoutManager(new LinearLayoutManager(this));
        rvAlert.setItemAnimator(new DefaultItemAnimator());

    }


    private void getCocurriculars() {

        if(eventsModelsList.size()>0){
            eventsModelsList.clear();
        }

        GetDataService retrofitService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<EventListModel> call = retrofitService.getAlerts("Bearer" +" "+ accessToken);


        call.enqueue(new Callback<EventListModel>() {
            @Override
            public void onResponse(Call<EventListModel> call, Response<EventListModel> response) {
                Log.d("cocurricularresponse", "" + response.code());
                Log.d("cocurricularresponse", "" + response.isSuccessful());

                if(response.isSuccessful()){

                    if(response.body() != null){
                        // for (EventListModel eventListModel : response.body()) {
                        EventListModel eventListModel = response.body();
                        //EventsModel[] events =eventListModel.getEventsModel();


                        List newlist = new ArrayList<>(Arrays.asList(eventListModel.getEventsModel()));
                        progressBar.setVisibility(View.INVISIBLE);
                        eventsModelsList.addAll(newlist);


                        //}
                    }
                } else if(response.code()==401){


                }

                Log.d("eventlistSize", "" + eventsModelsList.size());
                alertsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<EventListModel> call, Throwable t) {
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

}
