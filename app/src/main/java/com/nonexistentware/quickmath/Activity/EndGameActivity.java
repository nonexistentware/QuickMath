package com.nonexistentware.quickmath.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nonexistentware.quickmath.Difficult.ClassicGameMode;
import com.nonexistentware.quickmath.R;
import com.squareup.picasso.Picasso;

public class EndGameActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private DatabaseReference reference;
    private FirebaseUser firebaseUser;

    TextView backBtn, correctCounter, wrongCounter, timeRemain, levelCounter, playerName, scoreCounter, difficultyLevel, shareResultBtn, playAgain, gameMode, questionsLeft;
    ImageView playerImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        wrongCounter = findViewById(R.id.end_game_wrong_counter); //wrong points
        correctCounter = findViewById(R.id.end_game_correct_counter); //correct points
        timeRemain = findViewById(R.id.end_remain_time_counter); //time left
        levelCounter = findViewById(R.id.dashboard_level_counter);
        playerName = findViewById(R.id.end_player_google_name);
        scoreCounter = findViewById(R.id.end_game_score_counter);
        difficultyLevel = findViewById(R.id.end_game_difficult_level);
        shareResultBtn = findViewById(R.id.end_share_result);
        playAgain = findViewById(R.id.end_game_play_again);
        playerImg = findViewById(R.id.end_user_profile_img);
        gameMode = findViewById(R.id.end_game_game_mode);
        questionsLeft = findViewById(R.id.end_game_questionsq_left);
//        shareResultBtn.setVisibility(View.INVISIBLE);



        backBtn = findViewById(R.id.end_back_to_dashboard);

        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference().child("Players").child(auth.getCurrentUser().getUid());

        Intent intent = getIntent();
        int numberNGTV = intent.getIntExtra(ClassicGameMode.EXTRA_NUMBER_NGTV, 0);
        int numberPSTV = intent.getIntExtra(ClassicGameMode.EXTRA_NUMBER_PSTV, 0);
        int counterTimeRemain = intent.getIntExtra(ClassicGameMode.EXTRA_TIME_LEFT, 0);
        String difLevel = getIntent().getExtras().getString(ClassicGameMode.EXTRA_DIFFICULT_LEVEL);
        String timer = intent.getStringExtra(ClassicGameMode.EXTRA_TIME_REMAIN);
        String gameModeType = intent.getStringExtra(ClassicGameMode.EXTRA_GAME_MODE);
        String questionsLeftDisplayed = intent.getStringExtra(ClassicGameMode.EXTRA_QUESTIONS_LEFT);


        wrongCounter.setText("" + numberNGTV);
        correctCounter.setText("" + numberPSTV);
        timeRemain.setText("" + counterTimeRemain + "s");
        difficultyLevel.setText(difLevel);
        timeRemain.setText(timer + "s");
        gameMode.setText(gameModeType);
        questionsLeft.setText(questionsLeftDisplayed);



        loadPlayerData();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SelectGameActivity.class));
                finish();
            }
        });

        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), DifficultySelectActivity.class));
                finish();
            }
        });


        shareResultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog uploadDialog = new Dialog(EndGameActivity.this);
                uploadDialog.setContentView(R.layout.alert_dialog_end_game_upload_data);
                uploadDialog.setCancelable(false);

                TextView pstvBtn = uploadDialog.findViewById(R.id.alert_dialog_end_game_positive_btn);
                TextView ngtvBtn = uploadDialog.findViewById(R.id.alert_dialog_end_game_negative_btn);

                pstvBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    shareDataToCloud();
                    uploadDialog.dismiss();
                    }
                });

                ngtvBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        uploadDialog.dismiss();
                    }
                });

                uploadDialog.show();
            }
        });

    }

    private void loadPlayerData() {
        reference = FirebaseDatabase.getInstance().getReference("Players").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                timeRemain.setText(playerModel.getRemainCounterTimeTemp());
                playerName.setText(firebaseUser.getDisplayName());
                Picasso.get()
                        .load(firebaseUser.getPhotoUrl())
                        .into(playerImg);
                //exception if fields exist
                if (snapshot.child("playerScore").exists()) {
                    String scoreStr = snapshot.child("playerScore").getValue().toString().trim();
                    scoreCounter.setText(scoreStr);
                } else {
                    String scoreStrCheck = "0";
                    scoreCounter.setText(scoreStrCheck);
                } if (snapshot.child("playerLevel").exists()) {
                    String levelStr = snapshot.child("playerLevel").getValue().toString().trim();
                    levelCounter.setText(levelStr);
                } else {
                    String scoreStrCheck = "0";
                    levelCounter.setText(scoreStrCheck);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void shareDataToCloud() {
        reference.child("correctAnswersCounter").setValue(correctCounter.getText().toString().trim());
        reference.child("wrongAnswersCounter").setValue(wrongCounter.getText().toString().trim());
        reference.child("difficultLevel").setValue(difficultyLevel.getText().toString().trim());
        reference.child("remainCounterTime").setValue(timeRemain.getText().toString().trim());
        reference.child("gameMode").setValue(gameMode.getText().toString().trim());
        shareResultBtn.setText("Uploaded! Check leadership dashboard.");
        reference.child("playerFlag").setValue("1").toString().trim();
        shareResultBtn.setEnabled(false);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), SelectGameActivity.class));
        finish();
    }
}