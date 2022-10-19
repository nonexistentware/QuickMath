package com.nonexistentware.quickmath.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nonexistentware.quickmath.Adapter.LeadershipDashboardAdapter;
import com.nonexistentware.quickmath.Model.PlayerModel;
import com.nonexistentware.quickmath.R;

import java.util.ArrayList;

public class LeadershipDashboardActivity extends AppCompatActivity {

    TextView backBtn, removeFromList, deletePlayer;

    DatabaseReference databaseReference;
    FirebaseDatabase database;
    FirebaseAuth auth;
    FirebaseUser fUser;
    RecyclerView recyclerView;
    LeadershipDashboardAdapter adapter;
    ArrayList<PlayerModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leadership_dashboard);

        recyclerView = findViewById(R.id.recyclerview_top_players_activity);
        databaseReference = FirebaseDatabase.getInstance().getReference("Players");
        auth = FirebaseAuth.getInstance();
        fUser = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        adapter = new LeadershipDashboardAdapter(list, this);
        recyclerView.setAdapter(adapter);

        dataQuery();
        backBtn = findViewById(R.id.activity_top_players_back_btn);

        removeFromList = findViewById(R.id.activity_top_players_remove_data_from_recyclerview);
        deletePlayer = findViewById(R.id.activity_top_players_delete_player_data);

        removeFromList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeDataFromList();
            }
        });

        deletePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePlayerData();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SelectGameActivity.class));
                finish();
            }
        });

        databaseReference.child(auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("playerFlag").equals("0")) {
                    removeFromList.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot ds: snapshot.getChildren()) {
//                    PlayerModel playerModel = ds.getValue(PlayerModel.class);
//                    list.add(playerModel);
//                }
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
    }

    private void removeDataFromList() {
        databaseReference.child(auth.getCurrentUser().getUid()).child("playerFlag").setValue("0").toString().trim();
        Intent refresh = new Intent(this, LeadershipDashboardActivity.class);
        startActivity(refresh);
        finish();
        overridePendingTransition(0, 1);
    }

    private void deletePlayerData() {

    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            for (DataSnapshot ds: snapshot.getChildren()) {
                PlayerModel playerModel = ds.getValue(PlayerModel.class);
                list.add(playerModel);
                if (ds.child("Players").child("playerFlag").getValue() == "x") {
                    removeFromList.setVisibility(View.VISIBLE);
                } else {
                    removeFromList.setVisibility(View.INVISIBLE);
                }
            }
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

    private void dataQuery() {
        Query query = FirebaseDatabase.getInstance().getReference("Players").orderByChild("playerFlag")
                .equalTo("x");
        query.addListenerForSingleValueEvent(valueEventListener);
    }
}