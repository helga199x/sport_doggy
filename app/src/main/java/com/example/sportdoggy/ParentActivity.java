package com.example.sportdoggy;

import android.content.Intent;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public abstract class ParentActivity extends AppCompatActivity {

    protected void setFlags() {
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().hide();
        Window window = this.getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        window.setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    protected void switchActivities(Class classname) {
        Intent switchActivityIntent = new Intent(this, classname);
        startActivity(switchActivityIntent);
    }
}