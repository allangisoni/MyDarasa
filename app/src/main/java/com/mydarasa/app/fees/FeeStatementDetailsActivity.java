package com.mydarasa.app.fees;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mydarasa.app.GetDataService;
import com.mydarasa.app.PrefManager;
import com.mydarasa.app.R;
import com.mydarasa.app.RetrofitClientInstance;
import com.mydarasa.app.alerts.AlertsAdapter;
import com.mydarasa.app.alerts.AlertsListModel;
import com.mydarasa.app.alerts.AlertsModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FeeStatementDetailsActivity extends AppCompatActivity {

    private RecyclerView rvFeeDetails;
    private FeesStatementDetailsAdapter feesStatementAdapter;

    private ProgressBar progressBar;

    FloatingActionButton fab;

    List<FeeStatementDetailsModel> feeStatementDetailsList = new ArrayList<>();

    Toolbar myToolbar;
    PrefManager prefManager;
    String accessToken = " ";
    String refreshToken = " ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fee_statement_details);

        rvFeeDetails = findViewById(R.id.rvFeesDetails);
        myToolbar =  findViewById(R.id.toolbar);
        progressBar = findViewById(R.id.progressbar);
        fab = findViewById(R.id.fabCreate);
        setSupportActionBar(myToolbar);

        ActionBar actionBar = getSupportActionBar();
        //actionBar.setHomeAsUpIndicator(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Fee Statement");
        prefManager = new PrefManager(this);

        accessToken = prefManager.getAccessToken();
        refreshToken = prefManager.getRefreshToken();

        progressBar.setVisibility(View.VISIBLE);
        getFeesStatement();

        rvFeeDetails.setHasFixedSize(true);
        feesStatementAdapter = new FeesStatementDetailsAdapter(this, feeStatementDetailsList);

        rvFeeDetails.setAdapter(feesStatementAdapter);
        rvFeeDetails.setLayoutManager(new LinearLayoutManager(this));
        rvFeeDetails.setItemAnimator(new DefaultItemAnimator());

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FeeStatementDetailsActivity.this, CreatePaymentActivity.class));
            }
        });

    }

    private void getFeesStatement(){

        if(feeStatementDetailsList.size() > 0){
            feeStatementDetailsList.clear();
        }

        GetDataService retrofitService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<FeeStatementDetailsListModel> call = retrofitService.getFeeStatementDetails("Bearer" +" "+ accessToken);

        call.enqueue(new Callback<FeeStatementDetailsListModel>() {
            @Override
            public void onResponse(Call<FeeStatementDetailsListModel> call, Response<FeeStatementDetailsListModel> response) {

                if(response.isSuccessful()){

                    if(response.body() != null){
                        // for (EventListModel eventListModel : response.body()) {
                        FeeStatementDetailsListModel feeStatementDetailsListModel= response.body();
                        //EventsModel[] events =eventListModel.getEventsModel();


                        List newlist = new ArrayList<>(Arrays.asList(feeStatementDetailsListModel.getFeeStatementDetailsModels()));
                        progressBar.setVisibility(View.INVISIBLE);
                        feeStatementDetailsList.addAll(newlist);


                        //}
                    }
                }

                feesStatementAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<FeeStatementDetailsListModel> call, Throwable t) {

            }
        });





    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.feemenu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case  R.id.addFees:
                startActivity(new Intent(FeeStatementDetailsActivity.this, CreatePaymentActivity.class));
                return  true;
            default:
                return super.onOptionsItemSelected(item);
        }

        //return super.onOptionsItemSelected(item);
    }
}
