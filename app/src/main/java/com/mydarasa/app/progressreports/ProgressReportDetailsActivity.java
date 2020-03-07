package com.mydarasa.app.progressreports;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mydarasa.app.R;
import com.mydarasa.app.chat.ChatActivity;
import com.mydarasa.app.settings.SettingsActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProgressReportDetailsActivity extends AppCompatActivity {

    Toolbar myToolbar;

    //@BindView(R.id.tvStudent)
    TextView tvStudent;

    //@BindView(R.id.tvClass)
    TextView tvClass;

   // @BindView(R.id.tvGradingPeriod)
    TextView tvGradingPeriod;

    //@BindView(R.id.tvYear)
    TextView tvSchoolYear;

    //@BindView(R.id.tvClassTeacher)
    TextView tvClassTeacher;

    TextView tvSchoolName;

    EditText etComments;

    RecyclerView rvReportItem;

    ProgressReportItemAdapter progressReportItemAdapter;
    List<ProgressReportItem> reportItemList = new ArrayList<>();

    ProgressModel progressModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_report_details_activitity);
        ButterKnife.bind(this);

        myToolbar =  findViewById(R.id.toolbar);
        tvStudent = findViewById(R.id.tvStudent);
        tvClass = findViewById(R.id.tvClass);
        tvSchoolYear = findViewById(R.id.tvYear);
        tvGradingPeriod = findViewById(R.id.tvGradingPeriod);
        tvClassTeacher = findViewById(R.id.tvClassTeacher);
        tvSchoolName = findViewById(R.id.tvSchoolName);
        etComments = findViewById(R.id.etComments);
        rvReportItem = findViewById(R.id.rvProgressItems);

        setSupportActionBar(myToolbar);

        ActionBar actionBar = getSupportActionBar();
        //actionBar.setHomeAsUpIndicator(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Progress Report");


        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            String gsonString = bundle.getString("gsonString");
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();

            progressModel  = new ProgressModel();
            progressModel = gson.fromJson(gsonString, ProgressModel.class);

            tvStudent.setText(progressModel.getStudentName());
            tvClass.setText("Six");
            tvGradingPeriod.setText(progressModel.getGradingPeriod());
            tvSchoolYear.setText(progressModel.getSchoolYear());
            tvClassTeacher.setText(progressModel.getClassAdvisor());
            tvSchoolName.setText(progressModel.getSchool());
            etComments.setText("Class teacher comments:"+" "+" "+ progressModel.getReportDescription());


            ProgressReportItem item = new ProgressReportItem();
            item.setSubject("Subject");
            item.setScore("Score");
            item.setGrade("Grade");
            reportItemList.add(item);

            ProgressReportItem[] reportItems = new ProgressReportItem[]{};
            reportItems = progressModel.getProgressReportItems();

            List list = new ArrayList<>(Arrays.asList(reportItems));
            reportItemList.addAll(list);
            //progressReportItemAdapter.notifyDataSetChanged();

        }


        rvReportItem.setHasFixedSize(true);
        progressReportItemAdapter = new ProgressReportItemAdapter(this, reportItemList);
        rvReportItem.setAdapter(progressReportItemAdapter);
        rvReportItem.setLayoutManager(new LinearLayoutManager(this));
        rvReportItem.setItemAnimator(new DefaultItemAnimator());

       // getReportItems();


    }


    private void getReportItems(){

        if(reportItemList.size() > 0 ){
            reportItemList.clear();
        }


        ProgressReportItem item = new ProgressReportItem();
        item.setSubject("Subject");
        item.setScore("Score");
        item.setGrade("Grade");
        reportItemList.add(item);


        ProgressReportItem item1 = new ProgressReportItem();
        item1.setSubject("English");
        item1.setScore("90");
        item1.setGrade("A");
        reportItemList.add(item1);

        progressReportItemAdapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.progress_chat_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.viewComments:
                if(progressModel.getChatModel() != null) {

                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();
                    String gsonString = gson.toJson(progressModel);

                    Intent intent = new Intent(this, ProgressChatActivity.class);
                    intent.putExtra("gsonString", gsonString);
                    startActivity(intent);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
