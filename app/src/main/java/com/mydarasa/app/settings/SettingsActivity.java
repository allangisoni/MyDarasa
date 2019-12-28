package com.mydarasa.app.settings;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;

import com.mydarasa.app.LoginActivity;
import com.mydarasa.app.MainActivity;
import com.mydarasa.app.PrefManager;
import com.mydarasa.app.ProfileActivity;
import com.mydarasa.app.R;
import com.suke.widget.SwitchButton;

public class SettingsActivity extends AppCompatActivity {
    Toolbar myToolbar;

    RelativeLayout rlProfile;
    RelativeLayout rlLogout;
    ImageView ivProfile;

    PrefManager prefManager;
    Switch switchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        myToolbar =  findViewById(R.id.toolbar);
        rlProfile = findViewById(R.id.rlProfile);
        ivProfile = findViewById(R.id.ivSettingsProfile);
        rlLogout = findViewById(R.id.rlLogout);
        switchButton = findViewById(R.id.switchNotifications);

        prefManager = new PrefManager(SettingsActivity.this);

        setSupportActionBar(myToolbar);

        ActionBar actionBar = getSupportActionBar();
        //actionBar.setHomeAsUpIndicator(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Settings");

        switchButton.setChecked(true);

        rlProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingsActivity.this, ProfileActivity.class));
            }
        });

        rlLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefManager.setIsLoggedIn(false);
                startActivity(new Intent(SettingsActivity.this, LoginActivity.class));
                finish();
            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
