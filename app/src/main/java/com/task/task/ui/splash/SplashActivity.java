package com.task.task.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.task.task.R;
import com.task.task.ui.home.HomeActivity;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Handler().postDelayed(() -> startActivity(new Intent(SplashActivity.this, HomeActivity.class)),
                getResources().getInteger(R.integer.splash_display_duration));
    }
}