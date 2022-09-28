package com.nonexistentware.quickmath.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
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

    private AppUpdateManager appUpdateManager;
    private static final int RC_APP_UPDATE = 100;

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
        database = FirebaseDatabase.getInstance();
        reference = FirebaseDatabase.getInstance().getReference().child("Players").child(auth.getCurrentUser().getUid());

        appUpdateManager = AppUpdateManagerFactory.create(this);
        appUpdateManager.getAppUpdateInfo().addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo result) {
                if (result.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                        && result.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
                    try {
                        appUpdateManager.startUpdateFlowForResult(result, AppUpdateType.FLEXIBLE, SelectGameActivity.this,
                                RC_APP_UPDATE);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        appUpdateManager.registerListener(installStateUpdatedListener);

        classicModeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), DifficultySelectActivity.class));
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
                Picasso.get()
                        .load(firebaseUser.getPhotoUrl())
                        .into(playerIconImg);
                 //exception if fields exist
                if (snapshot.child("playerScore").exists()) {
                    String scoreStr = snapshot.child("playerScore").getValue().toString().trim();
                    scoreCounterTxt.setText(scoreStr);
                } else {
                    String scoreStrCheck = "0";
                    scoreCounterTxt.setText(scoreStrCheck);
                } if (snapshot.child("playerLevel").exists()) {
                    String levelStr = snapshot.child("playerLevel").getValue().toString().trim();
                    levelCounterTxt.setText(levelStr);
                } else {
                    String scoreStrCheck = "0";
                    levelCounterTxt.setText(scoreStrCheck);
                } if (snapshot.child("playerDuelWin").exists()) {
                    String duelCounterCheck = "0";
                    duelWinCounterTxt.setText(duelCounterCheck);
                } else {
                    String duelWinCheck = "0";
                    duelWinCounterTxt.setText(duelWinCheck);
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showCompletedUpdate() {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "New version of the application is ready",
                Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Download and install", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appUpdateManager.completeUpdate();
            }
        });
    }

    private InstallStateUpdatedListener installStateUpdatedListener = new InstallStateUpdatedListener() {
        @Override
        public void onStateUpdate(@NonNull InstallState state) {
            if (state.installStatus() == InstallStatus.DOWNLOADED) {
                showCompletedUpdate();
            }
        }
    };

    @Override
    public void onBackPressed() {
        final Dialog dialog = new Dialog(SelectGameActivity.this);
        dialog.setContentView(R.layout.alert_dialog_exit_signout_application);
        dialog.setCancelable(true);

        TextView exitApp = dialog.findViewById(R.id.alert_dialog_exit_application_positive_btn);
        TextView signoutBtn = dialog.findViewById(R.id.alert_dialog_exit__signout_application_negative_btn);
        TextView cancelBtn = dialog.findViewById(R.id.alert_dialog_exit_cancel_btn_application_negative_btn);

        exitApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectGameActivity.this.finish();
                System.exit(0);
            }
        });

        signoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_APP_UPDATE && requestCode != RESULT_OK) {

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        if (appUpdateManager != null)
            appUpdateManager.unregisterListener(installStateUpdatedListener);
        super.onStop();
    }
}