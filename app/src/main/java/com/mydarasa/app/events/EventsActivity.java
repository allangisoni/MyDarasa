package com.mydarasa.app.events;

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
import com.mydarasa.app.ServiceGenerator;
import com.mydarasa.app.login.TokenDetails;
import com.mydarasa.app.refreshtoken.RefreshTokenModel;
import com.mydarasa.app.student.StudentAdapter;
import com.mydarasa.app.student.StudentListModel;
import com.mydarasa.app.student.StudentModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EventsActivity extends AppCompatActivity {

    private RecyclerView rvEvents;
    private EventsAdapter eventsAdapter;
    private ProgressBar progressBar;
    List<EventsModel> eventsList = new ArrayList<>();

    Toolbar myToolbar;
    PrefManager prefManager;
    String accessToken = " ";
    String refreshToken = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        rvEvents = findViewById(R.id.rv_events);
        myToolbar =  findViewById(R.id.toolbar);
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
         getEvents();

        rvEvents.setHasFixedSize(true);
        eventsAdapter = new EventsAdapter(this, eventsList);

        rvEvents.setAdapter(eventsAdapter);
        rvEvents.setLayoutManager(new LinearLayoutManager(this));
        rvEvents.setItemAnimator(new DefaultItemAnimator());

    }

    public void getEvents(){


        if(eventsList.size()>0){
            eventsList.clear();
        }

        GetDataService retrofitService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<EventListModel> call = retrofitService.getEvents("Bearer" +" "+ accessToken);


        call.enqueue(new Callback<EventListModel>() {
            @Override
            public void onResponse(Call<EventListModel> call, Response<EventListModel> response) {
                Log.d("eventresponse", "" + response.code());
                Log.d("eventresponse", "" + response.isSuccessful());

                if(response.isSuccessful()){

                    if(response.body() != null){
                       // for (EventListModel eventListModel : response.body()) {
                            EventListModel eventListModel = response.body();
                            //EventsModel[] events =eventListModel.getEventsModel();


                           List newlist = new ArrayList<>(Arrays.asList(eventListModel.getEventsModel()));
                           progressBar.setVisibility(View.INVISIBLE);
                           eventsList.addAll(newlist);


                            //Toast.makeText(getApplicationContext(), "" + e, Toast.LENGTH_LONG).show();
                            //eventsList.add(events);
                        //}
                    }
                } else if(response.code()==200){

                    GetDataService retrofitService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
                    Call<EventListModel> call1 = retrofitService.getEvents("Bearer" +" "+ accessToken);

                    call1.enqueue(new Callback<EventListModel>() {
                        @Override
                        public void onResponse(Call<EventListModel> call, Response<EventListModel> response) {

                            if(response.isSuccessful()){

                                if(response.body() != null){

                                    EventListModel eventListModel = response.body();

                                    List newlist = new ArrayList<>(Arrays.asList(eventListModel.getEventsModel()));

                                    progressBar.setVisibility(View.INVISIBLE);
                                    eventsList.addAll(newlist);

                                    //Toast.makeText(getApplicationContext(), "" + e, Toast.LENGTH_LONG).show();

                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<EventListModel> call, Throwable t) {
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    });
                }

                Log.d("eventlistSize", "" + eventsList.size());
                eventsAdapter.notifyDataSetChanged();
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

    private void getRefreshToken(String token) {

        RefreshTokenModel refreshTokenModel = new RefreshTokenModel(token);
        GetDataService loginService =
                ServiceGenerator.createService(GetDataService.class, "mydarasa", "Magic@2019!");

        Call<RefreshTokenModel> call = loginService.getRefreshToken(refreshTokenModel);
        call.enqueue(new Callback<RefreshTokenModel>() {
            @Override
            public void onResponse(Call<RefreshTokenModel> call, Response<RefreshTokenModel> response) {

                if(response.isSuccessful()){

                    TokenDetails details = new TokenDetails();
                    details = response.body().getTokenDetails();

                    String newAccessToken = details.getAccessToken();
                    prefManager.setAccessToken(newAccessToken);
                    String newRefreshToken = details.getRefreshToken();
                    prefManager.setRefreshToken(newRefreshToken);


                    Toast.makeText(getApplicationContext(), "" + "success", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<RefreshTokenModel> call, Throwable t) {

            }
        });


    }
}
