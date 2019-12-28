package com.mydarasa.app.timetable;

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

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.mydarasa.app.GetDataService;
import com.mydarasa.app.PrefManager;
import com.mydarasa.app.R;
import com.mydarasa.app.RetrofitClientInstance;
import com.mydarasa.app.progressreports.ProgressModel;
import com.mydarasa.app.progressreports.ProgressRListModel;
import com.mydarasa.app.progressreports.ProgressReportsAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class TimetableActivity extends AppCompatActivity {

    private RecyclerView rvtimetable;
    private TimetableAdapter timetableAdapter;
    CalendarView calendarView;

    private ProgressBar progressBar;
    List<TimetableItemModel> timetableItemModelList = new ArrayList<>();

    Toolbar myToolbar;
    PrefManager prefManager;
    String accessToken = " ";
    String refreshToken = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);

        rvtimetable = findViewById(R.id.rvTimetable);
        myToolbar =  findViewById(R.id.toolbar);
        progressBar = findViewById(R.id.progressbar);
        calendarView = findViewById(R.id.calendarView);
        setSupportActionBar(myToolbar);

        ActionBar actionBar = getSupportActionBar();
        //actionBar.setHomeAsUpIndicator(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Timetable");
        prefManager = new PrefManager(this);

        accessToken = prefManager.getAccessToken();
        refreshToken = prefManager.getRefreshToken();

        progressBar.setVisibility(View.VISIBLE);
        getTimetable();

        List<EventDay> events = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        events.add(new EventDay(calendar, R.drawable.ic_timetable));
        calendarView.setEvents(events);

        rvtimetable.setHasFixedSize(true);
        timetableAdapter = new TimetableAdapter(this, timetableItemModelList);

        rvtimetable.setAdapter(timetableAdapter);
        rvtimetable.setLayoutManager(new LinearLayoutManager(this));
        rvtimetable.setItemAnimator(new DefaultItemAnimator());

    }

    public void getTimetable(){


        if(timetableItemModelList.size()>0){
            timetableItemModelList.clear();
        }

        GetDataService retrofitService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<TimetableListModel> call = retrofitService.getTimeTable("Bearer" +" "+ accessToken);
        call.enqueue(new Callback<TimetableListModel>() {
            @Override
            public void onResponse(Call<TimetableListModel> call, Response<TimetableListModel> response) {
                Log.d("timetableresponse", "" + response.code());
                Log.d("reportsresponse", "" + response.isSuccessful());

                //Toast.makeText(getApplicationContext(), "Response" + response.code(), Toast.LENGTH_LONG).show();

                if (response.isSuccessful()) {

                    if (response.body() != null) {

                        TimetableListModel timetableListModel = response.body();





                       // List newlist = new ArrayList<>(Arrays.asList(timetableListModel.getTimetableModel()));

                       TimetableModel[] timetableModel = timetableListModel.getTimetableModel();
                        //Toast.makeText(getApplicationContext(), "" + timetableModel.length, Toast.LENGTH_LONG).show();

                       List<TimetableModel> itemllist = Arrays.asList(timetableModel);
                        //Toast.makeText(getApplicationContext(), "" + itemllist.size(), Toast.LENGTH_LONG).show();

                        TimetableModel timetableItemModel = itemllist.get(0);
                        TimetableItemModel[] i = timetableItemModel.getTimetableItemModel();
                        List newlist = new ArrayList<>(Arrays.asList(i));
                       // TimetableItemModel[] timetableItemModel = new TimetableItemModel[itemllist.size()];
                        //timetableItemModel  = itemllist.toArray(timetableItemModel);

                        //List<TimetableItemModel> timetableItemModel =new ArrayList<>();
                        //timetableItemModel.add(itemllist.get(0).getTimetableItemModel());

                        //TimetableItemModel timetableItemModel = timetableModel;
                        //List newlist = new ArrayList<>(Arrays.asList(timetableModel));
                       //Toast.makeText(getApplicationContext(), "" + timetableItemModel.length, Toast.LENGTH_LONG).show();


                        progressBar.setVisibility(View.INVISIBLE);

                        //List<TimetableItemModel> list = (newlist);

                        timetableItemModelList.addAll(newlist);
                    }

                    timetableAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<TimetableListModel> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Log.d("timetableresponse", "" + t.getMessage());
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
