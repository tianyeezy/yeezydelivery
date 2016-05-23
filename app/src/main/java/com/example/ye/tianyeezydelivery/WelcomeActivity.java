package com.example.ye.tianyeezydelivery;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(WelcomeActivity.this, ChooseActivity.class));
                finish();

            }
        }, 5000);
    }
}
