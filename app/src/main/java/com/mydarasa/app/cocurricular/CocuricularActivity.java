package com.mydarasa.app.cocurricular;

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
import com.mydarasa.app.events.EventListModel;
import com.mydarasa.app.events.EventsAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CocuricularActivity extends AppCompatActivity {

    private RecyclerView rvCocurricular;
    private CocurricularAdapter cocurricularAdapter;

    private ProgressBar progressBar;
    List<CocurricularModel> cocurricularModelList = new ArrayList<>();

    Toolbar myToolbar;
    PrefManager prefManager;
    String accessToken = " ";
    String refreshToken = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cocuricular);

        rvCocurricular = findViewById(R.id.rvCocurricular);
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

        rvCocurricular.setHasFixedSize(true);
        cocurricularAdapter = new CocurricularAdapter(this, cocurricularModelList);

        rvCocurricular.setAdapter(cocurricularAdapter);
        rvCocurricular.setLayoutManager(new LinearLayoutManager(this));
        rvCocurricular.setItemAnimator(new DefaultItemAnimator());

    }

    private void getCocurriculars() {

        if(cocurricularModelList.size()>0){
            cocurricularModelList.clear();
        }

        GetDataService retrofitService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<CocurricularListModel> call = retrofitService.getCocuricularActivities("Bearer" +" "+ accessToken);

        call.enqueue(new Callback<CocurricularListModel>() {
            @Override
            public void onResponse(Call<CocurricularListModel> call, Response<CocurricularListModel> response) {
                Log.d("cocurricularresponse", "" + response.code());
                Log.d("cocurricularresponse", "" + response.isSuccessful());

                if(response.isSuccessful()){

                    if(response.body() != null){

                        CocurricularListModel cocurricularList = response.body();
                        //EventsModel[] events =eventListModel.getEventsModel();

                        List newlist = new ArrayList<>(Arrays.asList(cocurricularList.getCocurricularModel()));
                        progressBar.setVisibility(View.INVISIBLE);
                        cocurricularModelList.addAll(newlist);


                    }


                } else if(response.code()==401){

                    Log.d("accessTokenold", "" + accessToken);
                    RequestNewToken requestNewToken = new RequestNewToken(getApplicationContext());
                    requestNewToken.getNewToken();
                    recreate();
                    //RequestNewToken.getNewToken();

                    PrefManager prefManager1 = new PrefManager(getApplicationContext());
                    String newAccessToken = prefManager1.getAccessToken();
                    Log.d("accessTokennew", "" + newAccessToken);

                    GetDataService retrofitService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
                    Call<CocurricularListModel> call1 = retrofitService.getCocuricularActivities("Bearer" +" "+ newAccessToken);
                    call1.enqueue(new Callback<CocurricularListModel>() {
                        @Override
                        public void onResponse(Call<CocurricularListModel> call, Response<CocurricularListModel> response) {
                            if(response.isSuccessful()){

                                if(response.body() != null){

                                    CocurricularListModel cocurricularList = response.body();
                                    //EventsModel[] events =eventListModel.getEventsModel();

                                    List newlist = new ArrayList<>(Arrays.asList(cocurricularList.getCocurricularModel()));
                                    progressBar.setVisibility(View.INVISIBLE);
                                    cocurricularModelList.addAll(newlist);
                                }


                            }
                        }

                        @Override
                        public void onFailure(Call<CocurricularListModel> call, Throwable t) {

                        }
                    });
                }

                Log.d("eventlistSize", "" + cocurricularModelList.size());
                cocurricularAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<CocurricularListModel> call, Throwable t) {
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
