package com.mydarasa.app.cocurricular;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mydarasa.app.GetDataService;
import com.mydarasa.app.PrefManager;
import com.mydarasa.app.R;
import com.mydarasa.app.RetrofitClientInstance;
import com.mydarasa.app.events.EventsModel;
import com.mydarasa.app.progressreports.ProgressModel;
import com.mydarasa.app.timetable.TimetableItemModel;
import com.mydarasa.app.timetable.TimetableListModel;
import com.mydarasa.app.timetable.TimetableModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CocurricularDetailsActivity extends AppCompatActivity {

    Toolbar myToolbar;
    PrefManager prefManager;
    String accessToken = " ";
    String refreshToken = " ";

    private RecyclerView rvCocurricular;
    private CocurricularAdapter cocurricularAdapter;

   // List<CocurricularModel> cocurricularModelList = new ArrayList<>();
    List<TrainingModel> trainingModelList = new ArrayList<>();
    List<ProgressModel> progressModelList = new ArrayList<>();
    List<EventsModel> eventsModelList = new ArrayList<>();

    String studentNo = "";
    String studentName = "";
    String schoolName = "";

    LinearLayout linearLayoutTraining, llProgress, llEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cocurricular_details);

       /** if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else{
            View decorView = getWindow().getDecorView();
            // Hide the status bar.
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        } **/

        linearLayoutTraining =findViewById(R.id.llTraining);
        llProgress = findViewById(R.id.llProgress);
        llEvents = findViewById(R.id.llEvents);
        myToolbar =  findViewById(R.id.toolbar);
        rvCocurricular = findViewById(R.id.rvCocurricular);
        setSupportActionBar(myToolbar);

        if(getIntent().getExtras() != null){
            studentNo = getIntent().getStringExtra("studentNo");
            schoolName = getIntent().getStringExtra("schoolName");
            studentName = getIntent().getStringExtra("studentName");
        }
        ActionBar actionBar = getSupportActionBar();
        //actionBar.setHomeAsUpIndicator(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle(schoolName);
        prefManager = new PrefManager(this);

        accessToken = prefManager.getAccessToken();
        refreshToken = prefManager.getRefreshToken();



        linearLayoutTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(CocurricularDetailsActivity.this, "" + studentName, Toast.LENGTH_LONG).show();
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();

                String gsonString =  gson.toJson(trainingModelList);
                Intent intent = new Intent(CocurricularDetailsActivity.this, CocurricularTrainingActivity.class);
                intent.putExtra("timetableItems", gsonString);
                startActivity(intent);


            }
        });

        llProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                String reportString = gson.toJson(progressModelList);
                Intent intent = new Intent(CocurricularDetailsActivity.this, CocurricularReport.class);
                intent.putExtra("reportItems", reportString);
                startActivity(intent);
            }
        });

        llEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();

                String reportString = gson.toJson(eventsModelList);
                Intent intent = new Intent(CocurricularDetailsActivity.this, CocurricularEvents.class);
                intent.putExtra("eventItems", reportString);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void getCocurriculars(String name) {

        //if(cocurricularModelList.size()>0){
         //   cocurricularModelList.clear();
       // }

        if(trainingModelList.size() > 0){
            trainingModelList.clear();
        }

        if(progressModelList.size() >0){
           progressModelList.clear();
        }

        if (eventsModelList.size()>0){
            eventsModelList.clear();
        }

        GetDataService retrofitService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<CocurricularListModel> call = retrofitService.getCocuricularActivities("Bearer" +" "+ accessToken);

        /**  try {
            call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        } **/


         call.enqueue(new Callback<CocurricularListModel>() {
            @Override
            public void onResponse(Call<CocurricularListModel> call, Response<CocurricularListModel> response) {
                //Log.d("cocurricularresponse", "" + response.code());
                //Log.d("cocurricularresponse", "" + response.isSuccessful());
                Log.d("CocurricularDetails", "" + response.code());
                if(response.isSuccessful()){

                    if(response.body() != null){
                        CocurricularListModel cocurricularList = response.body();
                        List newlist = new ArrayList<>(Arrays.asList(cocurricularList.getCocurricularItemList()));
                        //List newlist1 = new ArrayList<>(Arrays.asList(cocurricularList.getCocurricularItemList()));


                        List<CocurricularItemList> mCocurricularListModel = new ArrayList<>();
                        List<CocurricularItemList> mCocurricularListModel1 = new ArrayList<>();
                        List<CocurricularItemList> mCocurricularListModel2 = new ArrayList<>();
                        mCocurricularListModel.addAll(newlist);
                        mCocurricularListModel1.addAll(newlist);
                        mCocurricularListModel2.addAll(newlist);
                        //Toast.makeText(CocurricularDetailsActivity.this, "" + newlist.size(), Toast.LENGTH_LONG).show();
                        for (CocurricularItemList model : mCocurricularListModel){
                            if(model.getSchoolTypeNo().equals("2")) {
                                for (TrainingModelList listModel : model.getTimetableModel()) {
                                    for (TrainingModel trainingModel : listModel.getTimetableItemModel()) {
                                        if (listModel.getStudentName().equals(name)) {
                                            trainingModelList.add(trainingModel);
                                            Log.d("timetableItemModel", "" + trainingModelList.size());
                                            // Toast.makeText(CocurricularDetailsActivity.this, "" + timetableItemModel.size(), Toast.LENGTH_LONG).show();
                                        }

                                    }
                                }


                            }


                        }
                       for (CocurricularItemList listItem : mCocurricularListModel) {
                           if(listItem.getSchoolTypeNo().equals("2")) {
                               if (listItem != null) {
                                   for (ProgressModel progressModel : listItem.getProgressModels()) {
                                       progressModelList.add(progressModel);
                                   }
                               }
                           }
                       }

                       for (CocurricularItemList itemList : mCocurricularListModel){
                           if(itemList.getSchoolTypeNo().equals("2")) {

                               for (EventsModel eventsModel : itemList.getEventsModel()){
                                   eventsModelList.add(eventsModel);
                               }
                           }
                       }




                    }


                }


                //Log.d("eventlistSize", "" + cocurricularModelList.size());
                //cocurricularAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<CocurricularListModel> call, Throwable t) {
                //progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), "Failed" + t.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        getCocurriculars(studentName);
    }
}
