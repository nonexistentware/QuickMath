package com.nonexistentware.quickmath.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.nonexistentware.quickmath.R;

public class EndGameActivity extends AppCompatActivity {

    TextView backBtn, correctCounter, wrongCounter, timeRemain, levelCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        wrongCounter = findViewById(R.id.end_game_wrong_counter); //wrong points
        correctCounter = findViewById(R.id.end_game_correct_counter); //correct points
        timeRemain = findViewById(R.id.end_remain_time_counter); //time left
        levelCounter = findViewById(R.id.dashboard_level_counter);

        backBtn = findViewById(R.id.end_back_to_dashboard);

        Intent intent = getIntent();
        int numberNGTV = intent.getIntExtra(ClassicGameMode.EXTRA_NUMBER_NGTV, 0);
        int numberPSTV = intent.getIntExtra(ClassicGameMode.EXTRA_NUMBER_PSTV, 0);
        int counterTimeRemain = intent.getIntExtra(ClassicGameMode.EXTRA_TIME_LEFT, 0);


        wrongCounter.setText("" + numberNGTV);
        correctCounter.setText("" + numberPSTV);
        timeRemain.setText("" + counterTimeRemain + "s");


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SelectGameActivity.class));
                finish();
            }
        });
    }
}