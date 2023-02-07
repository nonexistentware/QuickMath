package com.nonexistentware.quickmath.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nonexistentware.quickmath.Difficult.OfflineGameModeActivity;
import com.nonexistentware.quickmath.R;

public class MainActivity extends AppCompatActivity {

    SignInButton mGoogleLogin;
    TextView noLoginPlay, tooltipBtn;


    private FirebaseAuth auth;
    private DatabaseReference reference;
    private static final int RC_SIGN_IN = 100;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGoogleLogin = findViewById(R.id.googleSignBtn);
        tooltipBtn = findViewById(R.id.main_tooltip);
        noLoginPlay = findViewById(R.id.main_activity_play_no_login);

        reference = FirebaseDatabase.getInstance().getReference("Players");

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(com.firebase.ui.auth.R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        auth = FirebaseAuth.getInstance();
        checkConnection();

        mGoogleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Auth.GoogleSignInApi.signOut(mGoogleSignInClient.asGoogleApiClient());
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

        noLoginPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), OfflineGameModeActivity.class));
                finish();
            }
        });

        tooltipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.alert_dialog_window_main_activity);
                dialog.setCancelable(true);

                TextView pstvBtn = dialog.findViewById(R.id.alert_dialog_positive_btn);
                TextView ngtvBtn = dialog.findViewById(R.id.alert_dialog_negative_btn);

                pstvBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), SelectGameActivity.class));
                        finish();
                    }
                });

                ngtvBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

    }

    private void firebaseAuthWithGoogle(final String idToken) {
        final AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())  {
                            FirebaseUser fUser = auth.getCurrentUser();
                            reference.child(auth.getCurrentUser().getUid()).child("google").setValue(idToken);
                            reference.child(auth.getCurrentUser().getUid()).child("playerEmail").setValue(fUser.getEmail());
                            reference.child(auth.getCurrentUser().getUid()).child("playerName").setValue(fUser.getDisplayName());
                            reference.child(auth.getCurrentUser().getUid()).child("playerImg").setValue(fUser.getPhotoUrl().toString().trim());
//                            reference.child(auth.getCurrentUser().getUid()).child("playerLevel").setValue("0");
//                            reference.child(auth.getCurrentUser().getUid()).child("playerScore").setValue("0");
//                            reference.child(auth.getCurrentUser().getUid()).child("duelWin").setValue("0");
//                            reference.child(auth.getCurrentUser().getUid()).child("playerDefeated").setValue("0");
//                            reference.child(auth.getCurrentUser().getUid()).child("playerEmojiAchieve").setValue("0");
//                            reference.child(auth.getCurrentUser().getUid()).child("totalTimePlay").setValue("0");
//                            reference.child(auth.getCurrentUser().getUid()).child("classicTimePlay").setValue("0");
//                            reference.child(auth.getCurrentUser().getUid()).child("timeAttackTimePlay").setValue("0");
//                            reference.child(auth.getCurrentUser().getUid()).child("hardLevelSelect").setValue("0");
//                            reference.child(auth.getCurrentUser().getUid()).child("remainCounterTime").setValue("0");
                            Toast.makeText(MainActivity.this, "Successfully sigin", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this, SelectGameActivity.class));
                            finish();
                        } else {
                            Toast.makeText(MainActivity.this, "Authentication fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, " " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void updateUI() {
        startActivity(new Intent(getApplicationContext(), SelectGameActivity.class));
        finish();
    }

    private void checkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnected() || !networkInfo.isAvailable()) {
            connectionAlertDialog();
        }
    }

    private void connectionAlertDialog() {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.alert_network_error);
        dialog.setCancelable(true);

        TextView reloadBtn = dialog.findViewById(R.id.alert_dialog_network_error_reload_btn);
        TextView toSettingsBtn = dialog.findViewById(R.id.alert_dialog_network_error_to_settings_btn);
        TextView closeAlert = dialog.findViewById(R.id.alert_dialog_network_error_dismiss_btn);

        reloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reload = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(reload);
                finish();
            }
        });

        toSettingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                finish();
            }
        });

        closeAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            updateUI();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.alert_dialog_exit_application);
        dialog.setCancelable(true);

        TextView yesBtn = dialog.findViewById(R.id.alert_dialog_exit_application_yes);
        TextView noBtn = dialog.findViewById(R.id.alert_dialog_exit_application_no);

        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.finish();
                System.exit(0);
            }
        });

        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}