package com.mydarasa.app.settings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mydarasa.app.LoginActivity;
import com.mydarasa.app.MainActivity;
import com.mydarasa.app.PrefManager;
import com.mydarasa.app.ProfileActivity;
import com.mydarasa.app.R;
import com.mydarasa.app.chat.ChatActivity;
import com.suke.widget.SwitchButton;

public class SettingsActivity extends AppCompatActivity {
    Toolbar myToolbar;

    RelativeLayout rlProfile;
    RelativeLayout rlLogout;
    ImageView ivProfile;

    PrefManager prefManager;
    Switch switchButton;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        myToolbar =  findViewById(R.id.toolbar);
        rlProfile = findViewById(R.id.rlProfile);
        ivProfile = findViewById(R.id.ivSettingsProfile);
        rlLogout = findViewById(R.id.rlLogout);
        switchButton = findViewById(R.id.switchNotifications);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setVisibility(View.INVISIBLE);

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

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        startActivity(new Intent(SettingsActivity.this, MainActivity.class));
                        //Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_alerts:
                        // startActivity(new Intent(MainActivity.this, AlertsActivity.class));
                        //startActivity(new Intent(SettingsActivity.this, ChatActivity.class));
                        //Toast.makeText(MainActivity.this, "Alerts", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_settings:
                       // startActivity(new Intent(SettingsActivity.this, SettingsActivity.class));
                        //Toast.makeText(MainActivity.this, "Settings", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });





    }

    @Override
    public boolean onSupportNavigateUp() {
        bottomNavigationView.setSelectedItemId(R.id.action_home);
        onBackPressed();
        return true;
    }

}
