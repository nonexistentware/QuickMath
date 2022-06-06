package com.nonexistentware.quickmath.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.nonexistentware.quickmath.R;

public class SelectGameActivity extends AppCompatActivity {



    TextView aboutAppBtn, classicModeBtn, timeAttackModeBtn, scoreCounterTxt, winCounterTxt, levelCounterTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_game);


        //to activity
        classicModeBtn = findViewById(R.id.dashboard_to_classic_game_mode);
        timeAttackModeBtn = findViewById(R.id.dashboard_to_time_attack_mode);
        aboutAppBtn = findViewById(R.id.dashboard_about_app);

    }
}