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
import com.nonexistentware.quickmath.Difficult.FirstMistakeGameMode;
import com.nonexistentware.quickmath.R;
import com.squareup.picasso.Picasso;

public class EndGameActivity2 extends AppCompatActivity {

    private FirebaseAuth auth;
    private DatabaseReference reference;
    private FirebaseUser firebaseUser;

    TextView backBtn, correctCounter, wrongCounter, levelCounter, playerName, scoreCounter,
            difficultyLevel, shareResultBtn, gameMode, questionsLeft;
    ImageView playerImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game_activity2);

        wrongCounter = findViewById(R.id.end2_game_wrong_counter); //wrong points
        correctCounter = findViewById(R.id.end2_game_correct_counter); //correct points
        levelCounter = findViewById(R.id.end2_game_level_counter);
        playerName = findViewById(R.id.end2_player_google_name);
        scoreCounter = findViewById(R.id.end2_game_score_counter);
        difficultyLevel = findViewById(R.id.end2_game_difficult_level);
        shareResultBtn = findViewById(R.id.end2_share_result);
        playerImg = findViewById(R.id.end2_user_profile_img);
        gameMode = findViewById(R.id.end2_game_game_mode);
        questionsLeft = findViewById(R.id.end2_game_questionsq_left);

        backBtn = findViewById(R.id.end2_back_to_dashboard);

        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference().child("Players").child(auth.getCurrentUser().getUid());

        Intent intent = getIntent();
        int numberNGTV = intent.getIntExtra(FirstMistakeGameMode.FIRST_MISTAKE_EXTRA_NUMBER_NGTV, 0);
        int numberPSTV = intent.getIntExtra(FirstMistakeGameMode.FIRST_MISTAKE_EXTRA_NUMBER_PSTV, 0);
        int mandatoryLeft = intent.getIntExtra(FirstMistakeGameMode.FIRST_MISTAKE_EXTRA_MANDATORY_LEFT, 0);
        String difLevel = intent.getStringExtra(FirstMistakeGameMode.FIRST_MISTAKE_EXTRA_DIFFICULT_LEVEL);
        String gameTypeMode = intent.getStringExtra(FirstMistakeGameMode.FIRST_MISTAKE_EXTRA_GAME_MODE);

        wrongCounter.setText("" + numberNGTV);
        correctCounter.setText("" + numberPSTV);
        questionsLeft.setText("" + mandatoryLeft);
        difficultyLevel.setText(difLevel);
        gameMode.setText(gameTypeMode);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SelectGameActivity.class));
                finish();
            }
        });

        loadPlayerData();

    }

    private void loadPlayerData() {
        reference = FirebaseDatabase.getInstance().getReference("Players").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                playerName.setText(firebaseUser.getDisplayName());
                Picasso.get()
                        .load(firebaseUser.getPhotoUrl())
                        .into(playerImg);
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

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), SelectGameActivity.class));
        finish();

    }
}