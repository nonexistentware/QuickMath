package com.nonexistentware.quickmath.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.nonexistentware.quickmath.R;

public class EndGameActivity extends AppCompatActivity {

    TextView backBtn, wrongCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        wrongCounter = findViewById(R.id.end_game_wrong_counter);
        backBtn = findViewById(R.id.end_back_to_dashboard);

        Intent intent = getIntent();
        int number = intent.getIntExtra(ClassicGameMode.EXTRA_NUMBER, 0);

        wrongCounter.setText("" + number);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SelectGameActivity.class));
                finish();
            }
        });
    }
}