package com.mydarasa.app.cocurricular;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarView;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mydarasa.app.PrefManager;
import com.mydarasa.app.R;
import com.mydarasa.app.progressreports.ProgressModel;
import com.mydarasa.app.timetable.TimetableAdapter;
import com.mydarasa.app.timetable.TimetableItemModel;

import org.threeten.bp.LocalDate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CocurricularTrainingActivity extends AppCompatActivity {

    private RecyclerView rvTraining;
    private TrainingAdapter trainingAdapter;


    private ProgressBar progressBar;
    List<TrainingModel> trainingModelList = new ArrayList<>();
    List<TrainingModel> mRetrievedItems = new ArrayList<>();

    Toolbar myToolbar;
    PrefManager prefManager;
    String accessToken = " ";
    String refreshToken = " ";

    int dayoftheWeek = 0;


    SimpleDateFormat iFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat oFormatter = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat tFormatter = new SimpleDateFormat("HH:mm");

    Date localDate = null;
    String todaysDate = " ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cocurricular_training);

        myToolbar =  findViewById(R.id.toolbar);
        rvTraining = findViewById(R.id.rvTraining);
        progressBar = findViewById(R.id.progressbar);
        setSupportActionBar(myToolbar);

        ActionBar actionBar = getSupportActionBar();
        //actionBar.setHomeAsUpIndicator(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Training Schedule");
        prefManager = new PrefManager(this);

        progressBar.setVisibility(View.VISIBLE);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {

            String gsonString = bundle.getString("timetableItems");
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            mRetrievedItems = gson.fromJson(gsonString,new TypeToken<List<TrainingModel>>() {}.getType());

        }


        localDate =new Date();
        todaysDate = oFormatter.format(localDate);

        Calendar startDate = Calendar.getInstance();
        dayoftheWeek = startDate.get(Calendar.DAY_OF_WEEK);
        startDate.add(Calendar.MONTH, -1);

        /* ends after 1 month from now */
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 11);

        HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.calendarView)
                .range(startDate, endDate)
                .datesNumberOnScreen(7)
                .build();
        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                int month, year, day;
                Calendar calendar = date;
                dayoftheWeek = calendar.get(Calendar.DAY_OF_WEEK);
                month = calendar.get(Calendar.MONTH) + 1;
                year = calendar.get(Calendar.YEAR);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                String todaysmonth = "";
                if(month <10){
                    todaysmonth = "0" +month;
                }else{
                    todaysmonth = Integer.toString(month);
                }
                todaysDate = Integer.toString(year)+"-"+ todaysmonth + "-"+ Integer.toString(day);
                Log.d("formattedDate", todaysDate);
                getTrainingSchedule(dayoftheWeek);

                //  Toast.makeText(TimetableActivity.this, "" + dayoftheWeek, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCalendarScroll(HorizontalCalendarView calendarView,
                                         int dx, int dy) {

            }

            @Override
            public boolean onDateLongClicked(Calendar date, int position) {
                return true;
            }
        });






        rvTraining.setHasFixedSize(true);
        trainingAdapter = new TrainingAdapter(this, trainingModelList);

        rvTraining.setAdapter(trainingAdapter);
        rvTraining.setLayoutManager(new LinearLayoutManager(this));
        rvTraining.setItemAnimator(new DefaultItemAnimator());

        getTrainingSchedule(dayoftheWeek);

    }

    private void getTrainingSchedule(int dayofWeek){
         if(trainingModelList.size()>0){
             trainingModelList.clear();
         }

        final String weekDay = Integer.toString(dayofWeek);

      /**   TimetableItemModel itemModel = new TimetableItemModel();
         itemModel.setStartTime("2020-01-11 12:10:33");
         itemModel.setEndTime("2020-01-11 12:40:33");
         itemModel.setItemName("Classic Yoga");

        TimetableItemModel itemModel1 = new TimetableItemModel();
        itemModel1.setStartTime("2020-01-11 13:10:33");
        itemModel1.setEndTime("2020-01-11 13:40:33");
        itemModel1.setItemName("Stretching");
       **/


        Log.d("localDate", todaysDate);




        //String formattedEndTime = tFormatter.format(endDate);

      for (TrainingModel trainingModel : mRetrievedItems ){
          Log.d("trainingModel" , "" + trainingModel.getTeacherName());
          Log.d("trainingModel" , "" + trainingModel.getStartTime());
          Log.d("trainingModel" , "" + trainingModel.getEndTime());
          Log.d("trainingModel" , "" + trainingModel.getTeacherNo());
          Log.d("trainingModel" , "" + trainingModel.getWeekDay());

          Date startDate = null;
          Date today =null;
          try {
              startDate = iFormatter.parse(trainingModel.getStartTime());

          }catch (Exception e){
          }
          String formattedStartTime = oFormatter.format(startDate);
          //todaysDate = oFormatter.format(todaysDate);
          Log.d("localDate", formattedStartTime);

          if (formattedStartTime.equals(todaysDate)) {
              trainingModelList.add(trainingModel);
          }
      }

        //timetableItemModelList.add(itemModel);
        //timetableItemModelList.add(itemModel1);
        progressBar.setVisibility(View.INVISIBLE);
        trainingAdapter.notifyDataSetChanged();


    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //getTrainingSchedule();
    }
}
