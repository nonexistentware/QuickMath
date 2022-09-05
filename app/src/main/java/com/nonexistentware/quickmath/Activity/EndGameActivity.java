package com.nonexistentware.quickmath.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.nonexistentware.quickmath.Model.PlayerModel;
import com.nonexistentware.quickmath.R;
import com.squareup.picasso.Picasso;

import java.util.Map;

public class EndGameActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private DatabaseReference reference;
    private FirebaseUser firebaseUser;

    TextView backBtn, correctCounter, wrongCounter, timeRemain, levelCounter, playerName, scoreCounter;
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

        playerImg = findViewById(R.id.end_user_profile_img);



        backBtn = findViewById(R.id.end_back_to_dashboard);

        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference().child("Players").child(auth.getCurrentUser().getUid());

        Intent intent = getIntent();
        int numberNGTV = intent.getIntExtra(ClassicGameMode.EXTRA_NUMBER_NGTV, 0);
        int numberPSTV = intent.getIntExtra(ClassicGameMode.EXTRA_NUMBER_PSTV, 0);
        int counterTimeRemain = intent.getIntExtra(ClassicGameMode.EXTRA_TIME_LEFT, 0);


        wrongCounter.setText("" + numberNGTV);
        correctCounter.setText("" + numberPSTV);
        timeRemain.setText("" + counterTimeRemain + "s");

        loadPlayerData();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SelectGameActivity.class));
                finish();
            }
        });
    }

    private void loadPlayerData() {
        reference = FirebaseDatabase.getInstance().getReference("Players").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                PlayerModel playerModel = snapshot.getValue(PlayerModel.class);
                timeRemain.setText(playerModel.getRemainCounterTimeTemp());
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

}