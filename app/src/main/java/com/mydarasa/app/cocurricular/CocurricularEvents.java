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
import com.mydarasa.app.events.EventsAdapter;
import com.mydarasa.app.events.EventsModel;
import com.mydarasa.app.progressreports.ProgressModel;
import com.mydarasa.app.progressreports.ProgressReportsAdapter;

import java.util.ArrayList;
import java.util.List;

public class CocurricularEvents extends AppCompatActivity {
    private RecyclerView rvEvents;
    private CocurricularEventsAdapter eventsAdapter;


    private ProgressBar progressBar;
    List<EventsModel> eventsModelList = new ArrayList<>();
    List<EventsModel> mRetrievedItems = new ArrayList<>();

    Toolbar myToolbar;
    PrefManager prefManager;
    String accessToken = " ";
    String refreshToken = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cocurricular_events);
        myToolbar =  findViewById(R.id.toolbar);
        rvEvents = findViewById(R.id.rvEvents);
        progressBar = findViewById(R.id.progressbar);
        setSupportActionBar(myToolbar);

        ActionBar actionBar = getSupportActionBar();
        //actionBar.setHomeAsUpIndicator(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Events");
        prefManager = new PrefManager(this);
        accessToken = prefManager.getAccessToken();
        refreshToken = prefManager.getRefreshToken();


        progressBar.setVisibility(View.VISIBLE);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {

            String gsonString = bundle.getString("eventItems");
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            if(mRetrievedItems.size()>0){
                mRetrievedItems.clear();
            }
            mRetrievedItems = gson.fromJson(gsonString,new TypeToken<List<EventsModel>>() {}.getType());
            //Toast.makeText(CocurricularTrainingActivity.this, "" + model.size(), Toast.LENGTH_LONG).show();
        }

        rvEvents.setHasFixedSize(true);
        eventsAdapter = new CocurricularEventsAdapter(this, eventsModelList);

        rvEvents.setAdapter(eventsAdapter);
        rvEvents.setLayoutManager(new LinearLayoutManager(this));
        rvEvents.setItemAnimator(new DefaultItemAnimator());

        getEvents();
    }

    private void getEvents(){

        if(eventsModelList.size()>0){
            eventsModelList.clear();
        }

        for (EventsModel eventsModel: mRetrievedItems){
            eventsModelList.add(eventsModel);

        }

        progressBar.setVisibility(View.INVISIBLE);
        eventsAdapter.notifyDataSetChanged();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();

    }
}
