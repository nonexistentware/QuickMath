package com.nonexistentware.quickmath.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class SelectGameActivity extends AppCompatActivity {

    TextView aboutAppBtn, classicModeBtn, timeAttackModeBtn, topPlayersBtn, scoreCounterTxt, duelWinCounterTxt, levelCounterTxt, playerGoogleName, signOut;
    ImageView playerIconImg;

    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;
    private DatabaseReference reference;
    private FirebaseDatabase database;

    private long backPressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_game);

        //img
        playerIconImg = findViewById(R.id.dashboard_user_profile_img);

        //data to fetch
        playerGoogleName = findViewById(R.id.dashboard_player_google_name);
        scoreCounterTxt = findViewById(R.id.dashboard_score_counter);
        levelCounterTxt = findViewById(R.id.dashboard_level_counter);
        duelWinCounterTxt = findViewById(R.id.dashboard_duel_win_counter);

        //to activity
        classicModeBtn = findViewById(R.id.dashboard_to_classic_game_mode);
        timeAttackModeBtn = findViewById(R.id.dashboard_to_time_attack_mode);
        topPlayersBtn = findViewById(R.id.dashboard_leadership_table);
        aboutAppBtn = findViewById(R.id.dashboard_about_app);

        //btn
        signOut = findViewById(R.id.dashboard_sign_ot_btn);

        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference().child("Players").child(auth.getCurrentUser().getUid());

        classicModeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ClassicGameMode.class));
                finish();
            }
        });

        timeAttackModeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        topPlayersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LeadershipDashboardActivity.class));
                finish();
            }
        });

        aboutAppBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AboutActivity.class));
                finish();
            }
        });

//        if (auth.getCurrentUser() != null) {
//            reference = FirebaseDatabase.getInstance().getReference()
//                    .child("Players").child(auth.getCurrentUser().getUid());
//        }

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

        loadPlayerData();

    }

    private void loadPlayerData() {
        if (auth.getCurrentUser() != null)
            reference = FirebaseDatabase.getInstance().getReference("Players").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                PlayerModel playerModel = snapshot.getValue(PlayerModel.class);
                playerGoogleName.setText(firebaseUser.getDisplayName());
                if (snapshot.child("Players").exists()) { //simple check if fields are missed.
                    scoreCounterTxt.setText(playerModel.getPlayerScore());
                    duelWinCounterTxt.setText(playerModel.getDuelWin());
                    levelCounterTxt.setText(playerModel.getPlayerLevel());
                    Picasso.get()
                            .load(firebaseUser.getPhotoUrl())
                            .into(playerIconImg);
                } else {
                    scoreCounterTxt.setText("0");
                    duelWinCounterTxt.setText("0");
                    levelCounterTxt.setText("0");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            return;
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
            backPressedTime = System.currentTimeMillis();
        }
    }
}