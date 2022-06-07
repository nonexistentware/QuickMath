package com.nonexistentware.quickmath.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.nonexistentware.quickmath.R;
import com.squareup.picasso.Picasso;

public class SelectGameActivity extends AppCompatActivity {

    TextView aboutAppBtn, classicModeBtn, timeAttackModeBtn, scoreCounterTxt, duelWinCounterTxt, levelCounterTxt, playerGoogleName, signOut;
    ImageView playerIconImg;

    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;
    private DatabaseReference reference;

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
        aboutAppBtn = findViewById(R.id.dashboard_about_app);

        //btn
        signOut = findViewById(R.id.dashboard_sign_ot_btn);

        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();

        classicModeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        timeAttackModeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        aboutAppBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AboutActivity.class));
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
        if (auth.getCurrentUser() != null) {
            playerGoogleName.setText(firebaseUser.getDisplayName());
            Picasso.get()
                    .load(firebaseUser.getPhotoUrl())
                    .into(playerIconImg);

        }
    }
}