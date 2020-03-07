package com.mydarasa.app.fees;

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
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mydarasa.app.GetDataService;
import com.mydarasa.app.PrefManager;
import com.mydarasa.app.R;
import com.mydarasa.app.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FeesStructureDetailsActivity extends AppCompatActivity {
    Toolbar myToolbar;
    TextView tvStudent;
    TextView tvClass;
    TextView tvPeriod;
    TextView tvSchoolYear;
    TextView tvSchoolName;

    RecyclerView rvFeeItem;
    private FeeItemAdapter feeItemAdapter;
    private List<FeeItemModel> feeItemModelList = new ArrayList<>();

    PrefManager prefManager;
    String accessToken = " ";
    String refreshToken = " ";

    String schoolName = "", studentName="", className="", period="", year=" ", gsonString;

    FeeItemModel[] feeItemModels = new FeeItemModel[]{};

    List<FeeItemModel> bundleFeeList = new ArrayList<>();

    FeeStructureModel feeStructureModel ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fees_structure_details);

        myToolbar =  findViewById(R.id.toolbar);
        tvStudent = findViewById(R.id.tvStudent);
        tvClass = findViewById(R.id.tvClass);
        tvSchoolYear = findViewById(R.id.tvYear);
        tvPeriod = findViewById(R.id.tvPeriod);
        rvFeeItem = findViewById(R.id.rvFeeItems);
        tvSchoolName = findViewById(R.id.tvSchoolName);


        setSupportActionBar(myToolbar);

        ActionBar actionBar = getSupportActionBar();
        //actionBar.setHomeAsUpIndicator(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Fees Structure");

        prefManager = new PrefManager(this);

        accessToken = prefManager.getAccessToken();
        refreshToken = prefManager.getRefreshToken();

        //feeStructureModel = new FeeStructureModel();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            schoolName = bundle.getString("schoolName");
            className = bundle.getString("className");
            studentName = bundle.getString("studentName");
            period = bundle.getString("period");
            year = bundle.getString("year");
            gsonString = bundle.getString("gsonString");

            FeeItemModel feeItemModel= new FeeItemModel();
            feeItemModel.setCost("Fee");
            feeItemModel.setName("Item");
            feeItemModelList.add(feeItemModel);

            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();

            feeStructureModel = gson.fromJson(gsonString, FeeStructureModel.class);
            feeItemModels = feeStructureModel.getFeeItem();

            List list = new ArrayList<>(Arrays.asList(feeItemModels));
            feeItemModelList.addAll(list);

            FeeItemModel feeItemModel2= new FeeItemModel();
            feeItemModel2.setCost(feeStructureModel.getTotalCoast());
            feeItemModel2.setName("Total");
            feeItemModelList.add(feeItemModel2);




           // Toast.makeText(getApplicationContext(), "" + feeItemModels.length, Toast.LENGTH_LONG).show();



            //bundleFeeList =(ArrayList<FeeItemModel>) bundle.getSerializable("feeItem");
            //schoolName = bundle.getString("schoolName");
        }


        tvStudent.setText(studentName);
        tvClass.setText(className);
        tvPeriod.setText(period);
        tvSchoolYear.setText(year);
        tvSchoolName.setText(schoolName);

        rvFeeItem.setHasFixedSize(true);
        feeItemAdapter = new FeeItemAdapter(this, feeItemModelList);
        rvFeeItem.setAdapter(feeItemAdapter);
        rvFeeItem.setLayoutManager(new LinearLayoutManager(this));
        rvFeeItem.setItemAnimator(new DefaultItemAnimator());

        //getFeesStructure("all");


    }





    private void getFeesStructure(final String studentName){
        GetDataService retrofitService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<FeeStructureModelList> call = retrofitService.getFeeStructure("Bearer" +" "+ accessToken);

        call.enqueue(new Callback<FeeStructureModelList>() {
            @Override
            public void onResponse(Call<FeeStructureModelList> call, Response<FeeStructureModelList> response) {

                if(response.isSuccessful()){

                    if(response.body() != null){
                        FeeStructureModelList feeStructureList = response.body();


                        List newlist = new ArrayList<>(Arrays.asList(feeStructureList.getFeeStructureModels()));
                        List<FeeStructureModel> feeStructureModels = new ArrayList<>();
                        feeStructureModels = newlist;



                        Log.d("listSize", "" + feeStructureModels.size());



                        //FeeItemModel[] feeItemModels = new FeeItemModel[]{};
                        //feeItemModels = feeStructureModels.get(0).getFeeItem();

                        FeeItemModel feeItemModel= new FeeItemModel();
                        feeItemModel.setCost("Fee");
                        feeItemModel.setName("Item");
                        feeItemModelList.add(feeItemModel);

                        FeeItemModel feeItemModel1= new FeeItemModel();
                        feeItemModel1.setCost("4000");
                        feeItemModel1.setName("Swimming");
                        //feeItemModelList.add(feeItemModel1);

                        FeeItemModel[] models = new FeeItemModel[]{};

                        for (FeeStructureModel feeStructureModel : feeStructureModels){

                            for (FeeItemModel model : feeStructureModel.getFeeItem()){
                                feeItemModelList.add(model);
                            }
                        }
                        FeeItemModel feeItemModel2= new FeeItemModel();
                        feeItemModel2.setCost(feeStructureModels.get(1).getTotalCoast());
                        feeItemModel2.setName("Total");
                        feeItemModelList.add(feeItemModel2);

                        //feeItemModelList.addAll(newlist1);
                        //feeItemModelList.addAll(Arrays.asList(feeItemModels));
                        //progressBar.setVisibility(View.INVISIBLE);



                        //Toast.makeText(getApplicationContext(), "" + studentModel.getFirstName(), Toast.LENGTH_LONG).show();
                        //studentList.add(studentModel);
                    }
                }


               // Log.d("listSize", "" + feeItemModelList.size());
                feeItemAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<FeeStructureModelList> call, Throwable t) {

            }
        });
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
