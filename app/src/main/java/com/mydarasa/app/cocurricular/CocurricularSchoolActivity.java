package com.mydarasa.app.cocurricular;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mydarasa.app.R;

public class CocurricularSchoolActivity extends AppCompatActivity {

    Toolbar myToolbar;

    CardView cvNormal, cvCocurricular;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cocurricular_school);

        myToolbar =  findViewById(R.id.toolbar);
        cvNormal = findViewById(R.id.cvNormal);
        cvCocurricular = findViewById(R.id.cvCocurricular);

        setSupportActionBar(myToolbar);
        ActionBar actionBar = getSupportActionBar();
        //actionBar.setHomeAsUpIndicator(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Cocurricular School");

        cvNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CocurricularSchoolActivity.this, CocuricularActivity.class);
                intent.putExtra("schoolType", "1");
                startActivity(intent);
            }
        });

        cvCocurricular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CocurricularSchoolActivity.this, CocuricularActivity.class);
                intent.putExtra("schoolType", "2");
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {

        onBackPressed();
        return true;
    }
}
