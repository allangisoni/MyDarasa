package com.mydarasa.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mydarasa.app.alerts.AlertsActivity;
import com.mydarasa.app.cocurricular.CocuricularActivity;
import com.mydarasa.app.events.EventsActivity;
import com.mydarasa.app.fees.FeesActivity;
import com.mydarasa.app.progressreports.ProgressReportsActivity;
import com.mydarasa.app.settings.SettingsActivity;
import com.mydarasa.app.student.StudentListModel;
import com.mydarasa.app.student.StudentModel;
import com.mydarasa.app.timetable.TimetableActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView tvProfile;
    CardView cvChildren, cvEvents, cvProfile, cvCocurricular, cvAlerts, cvProgressReports, cvTimetable, cvGuardian, cvFees;

    String accessToken = "";
    PrefManager prefManager;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvProfile = findViewById(R.id.tv_Profile);

        cvChildren = findViewById(R.id.cv_children);
        cvEvents = findViewById(R.id.cv_events);
        cvProfile = findViewById(R.id.cv_Profile);
        cvCocurricular = findViewById(R.id.cvCocurricular);
        cvAlerts = findViewById(R.id.cvAlerts);
        cvProgressReports = findViewById(R.id.cv_progressReports);
        cvTimetable = findViewById(R.id.cv_timetable);
        cvGuardian = findViewById(R.id.cv_guardian);
        cvFees = findViewById(R.id.cv_fees);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        prefManager = new PrefManager(this);
        accessToken = prefManager.getAccessToken();



        GetDataService retrofitService =
             //   ServiceGenerator.createService(GetDataService.class);
                RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<StudentListModel> call = retrofitService.getStudentDetails("Bearer" +" " +accessToken);
        //Call<List<StudentListModel>> call = retrofitService.getStudentDetails();
        call.enqueue(new Callback<StudentListModel>() {
            @Override
            public void onResponse(Call<StudentListModel> call, Response<StudentListModel> response) {
                Log.d("studentresponse", "" + response.code());
                Log.d("studentresponse", "" + response.isSuccessful());
                Log.d("studentresponse", "" + response.code());

                if(response.isSuccessful()){

                    StudentModel student = new StudentModel();
                    StudentListModel studentList;
                    studentList = response.body();
                    //student =studentList.getStudentModel();

                    //String name = response.body().getFirstName();
                   // Log.d("studentName", "" + student.getFirstName());
                   // Log.d("studentName", "" + student.getMiddleName());
                   // Log.d("studentName", "" + studentList.toString());
                }
            }

            @Override
            public void onFailure(Call<StudentListModel> call, Throwable t) {

            }
        });

        cvChildren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ChildrenActivity.class));
            }
        });

        cvEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, EventsActivity.class));
            }
        });


        cvProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            }
        });

        cvCocurricular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CocuricularActivity.class));
            }
        });

        cvAlerts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AlertsActivity.class));
            }
        });

        cvProgressReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ProgressReportsActivity.class));
            }
        });

        cvTimetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TimetableActivity.class));
            }
        });

        cvGuardian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PhoneOTPActivity.class));
            }
        });

        cvFees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FeesActivity.class));
            }
        });
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        //Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_alerts:
                        startActivity(new Intent(MainActivity.this, AlertsActivity.class));
                        //Toast.makeText(MainActivity.this, "Alerts", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_settings:
                        startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                        //Toast.makeText(MainActivity.this, "Settings", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });

        tvProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            }
        });

    }

}
