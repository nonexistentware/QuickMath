package com.nonexistentware.quickmath.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nonexistentware.quickmath.R;

public class SelectGameActivity extends AppCompatActivity {

    TextView aboutAppBtn, classicModeBtn, timeAttackModeBtn, scoreCounterTxt, winCounterTxt, levelCounterTxt, signOut;

    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_game);


        //to activity
        classicModeBtn = findViewById(R.id.dashboard_to_classic_game_mode);
        timeAttackModeBtn = findViewById(R.id.dashboard_to_time_attack_mode);
        aboutAppBtn = findViewById(R.id.dashboard_about_app);

        //btn
        signOut = findViewById(R.id.dashboard_sign_ot_btn);

        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

    }
}