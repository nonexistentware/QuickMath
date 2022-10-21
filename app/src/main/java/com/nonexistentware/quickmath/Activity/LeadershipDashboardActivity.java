package com.nonexistentware.quickmath.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.nonexistentware.quickmath.Adapter.LeadershipDashboardAdapter;
import com.nonexistentware.quickmath.Model.PlayerModel;
import com.nonexistentware.quickmath.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LeadershipDashboardActivity extends AppCompatActivity {

    TextView backBtn, removeFromList, deletePlayer, playerName, playerScore, playerLevel;
    ImageView playerImg;

    DatabaseReference databaseReference;
    DatabaseReference pathToPlayerFlag;
    FirebaseDatabase database;
    FirebaseAuth auth;
    FirebaseUser fUser;
    RecyclerView recyclerView;
    LeadershipDashboardAdapter adapter;
    ArrayList<PlayerModel> list;
    String removeBtnCheck = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leadership_dashboard);

        recyclerView = findViewById(R.id.recyclerview_top_players_activity);
        auth = FirebaseAuth.getInstance();
        fUser = auth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Players");
        pathToPlayerFlag = FirebaseDatabase.getInstance().getReference("Players").child("playerFlag");
        database = FirebaseDatabase.getInstance();
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        adapter = new LeadershipDashboardAdapter(list, this);
        recyclerView.setAdapter(adapter);

        dataQuery();
        loadCurrentPlayerData();
        backBtn = findViewById(R.id.activity_top_players_back_btn);

        removeFromList = findViewById(R.id.activity_top_players_remove_data_from_recyclerview);
        deletePlayer = findViewById(R.id.activity_top_players_delete_player_data);

        playerImg = findViewById(R.id.activity_leadership_player_img);
        playerName = findViewById(R.id.top_player_item_player_name);
        playerLevel = findViewById(R.id.top_player_item_level_counter);
        playerScore = findViewById(R.id.top_player_item_score_counter);

        removeFromList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogWindow();
            }
        });

        deletePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogPlayerDelete();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SelectGameActivity.class));
                finish();
            }
        });

        pathToPlayerFlag.child(auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()) {
                    PlayerModel playerModel = ds.getValue(PlayerModel.class);
                    list.add(playerModel);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void removeDataFromList() {
        databaseReference.child(auth.getCurrentUser().getUid()).child("playerFlag").removeValue();
        Intent refresh = new Intent(this, LeadershipDashboardActivity.class);
        startActivity(refresh);
        finish();
        overridePendingTransition(0, 1);
    }


    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            for (DataSnapshot ds : snapshot.getChildren()) {
                PlayerModel playerModel = ds.getValue(PlayerModel.class);
                list.add(playerModel);

            }
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

    private void dataQuery() {
        Query query = FirebaseDatabase.getInstance().getReference("Players").orderByChild("playerFlag")
                .equalTo("1");
        query.addListenerForSingleValueEvent(valueEventListener);
    }

    private void loadCurrentPlayerData() {
        if (auth.getCurrentUser() != null) {
            databaseReference.child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    playerName.setText(fUser.getDisplayName());
                    Picasso.get()
                            .load(fUser.getPhotoUrl())
                            .into(playerImg);
                    if (snapshot.child("playerScore").exists()) {
                        String scoreStr = snapshot.child("playerScore").getValue().toString().trim();
                        playerScore.setText(scoreStr);
                    } else {
                        String scoreStrCheck = "0";
                        playerScore.setText(scoreStrCheck);
                    } if (snapshot.child("playerLevel").exists()) {
                        String levelStr = snapshot.child("playerLevel").getValue().toString().trim();
                        playerLevel.setText(levelStr);
                    } else {
                        String scoreStrCheck = "0";
                        playerLevel.setText(scoreStrCheck);
                    } if (snapshot.child("playerDuelWin").exists()) {
//                        String duelCounterCheck = "0";
//                        duelWinCounterTxt.setText(duelCounterCheck);
//                    } else {
//                        String duelWinCheck = "0";
//                        duelWinCounterTxt.setText(duelWinCheck);
                    }
                    if (snapshot.child("playerFlag").exists()) {
                        removeFromList.setVisibility(View.VISIBLE);
                    } else {
                        removeFromList.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }

    private void deletePlayerData() {
        fUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                    auth.signOut();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                    Toast.makeText(LeadershipDashboardActivity.this, "User has been deleted", Toast.LENGTH_SHORT).show();
                }
            }
        });
        databaseReference.child(auth.getCurrentUser().getUid()).removeValue();
    }

    private void alertDialogWindow() {
        final Dialog dialog = new Dialog(LeadershipDashboardActivity.this);
        dialog.setContentView(R.layout.alert_dialog_leadership_dashboard_remove_from_list);
        dialog.setCancelable(false);

        TextView yesBtn = dialog.findViewById(R.id.alert_dialog_leadership_dashboard_positive_btn);
        TextView noBTn = dialog.findViewById(R.id.alert_dialog_leadership_dashboard_negative_btn);

        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeDataFromList();
                databaseReference.child(auth.getCurrentUser().getUid()).child("playerScore").setValue(ServerValue.increment(-1000));
                dialog.dismiss();
            }
        });

        noBTn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void alertDialogPlayerDelete() {
        final Dialog dialog = new Dialog(LeadershipDashboardActivity.this);
        dialog.setContentView(R.layout.alert_dialog_leadership_dashboard_player_delete);
        dialog.setCancelable(false);

        TextView yesBtn = dialog.findViewById(R.id.alert_dialog_leadership_dashboard_player_delete_positive_btn);
        TextView noBTn = dialog.findViewById(R.id.alert_dialog_leadership_dashboard_player_delete_negative_btn);

        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                deletePlayerData();
            }
        });

        noBTn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void googleSignOut() {

    }
}