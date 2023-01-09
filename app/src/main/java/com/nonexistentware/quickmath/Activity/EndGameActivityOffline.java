package com.nonexistentware.quickmath.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.nonexistentware.quickmath.Difficult.ClassicGameMode;
import com.nonexistentware.quickmath.Difficult.OfflineGameModeActivity;
import com.nonexistentware.quickmath.R;

public class EndGameActivityOffline extends AppCompatActivity {

    TextView backBtn, correctCounter, wrongCounter, timeRemain, levelCounter, playerName, scoreCounter, difficultyLevel, shareResultBtn, playAgain, gameMode, questionsLeft;
    ImageView playerImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game_offline);

        wrongCounter = findViewById(R.id.end_offline_game_wrong_counter); //wrong points
        correctCounter = findViewById(R.id.end_offline_game_correct_counter); //correct points
        timeRemain = findViewById(R.id.end_offline_remain_time_counter); //time left
        levelCounter = findViewById(R.id.dashboard_offline_level_counter);
        playerName = findViewById(R.id.end_offline_player_google_name);
        scoreCounter = findViewById(R.id.end_offline_game_score_counter);
        shareResultBtn = findViewById(R.id.end_offline_share_result);
        playAgain = findViewById(R.id.end_offline_game_play_again);
        playerImg = findViewById(R.id.end_offline_user_profile_img);
        gameMode = findViewById(R.id.end_offline_game_game_mode);
        questionsLeft = findViewById(R.id.end_offline_game_questionsq_left);

        backBtn = findViewById(R.id.end_offline_back_to_login_screen);

        Intent intent = getIntent();
        int numberNGTV = intent.getIntExtra(OfflineGameModeActivity.EXTRA_NUMBER_NGTV_OFFLINE, 0);
        int numberPSTV = intent.getIntExtra(OfflineGameModeActivity.EXTRA_NUMBER_PSTV_OFFLINE, 0);
        int counterTimeRemain = intent.getIntExtra(OfflineGameModeActivity.EXTRA_TIME_LEFT_OFFLINE, 0);
//        String difLevel = getIntent().getExtras().getString(OfflineGameModeActivity.EXTRA_DIFFICULT_LEVEL);
        String timer = intent.getStringExtra(OfflineGameModeActivity.EXTRA_TIME_REMAIN_OFFLINE);
        String gameModeType = intent.getStringExtra(OfflineGameModeActivity.EXTRA_GAME_MODE_OFFLINE);
        String questionsLeftDisplayed = intent.getStringExtra(OfflineGameModeActivity.EXTRA_QUESTIONS_LEFT_OFFLINE);

        wrongCounter.setText("" + numberNGTV);
        correctCounter.setText("" + numberPSTV);
        timeRemain.setText("" + counterTimeRemain + "s");
        timeRemain.setText(timer + "s");
        gameMode.setText(gameModeType);
        questionsLeft.setText(questionsLeftDisplayed);



    }

    private void loginToGoogle() {

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }
}