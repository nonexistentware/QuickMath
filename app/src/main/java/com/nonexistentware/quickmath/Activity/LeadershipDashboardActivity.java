package com.nonexistentware.quickmath.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nonexistentware.quickmath.Adapter.LeadershipDashboardAdapter;
import com.nonexistentware.quickmath.Model.PlayerModel;
import com.nonexistentware.quickmath.R;

import java.util.ArrayList;

public class LeadershipDashboardActivity extends AppCompatActivity {

    TextView backBtn;

    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    LeadershipDashboardAdapter adapter;
    ArrayList<PlayerModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leadership_dashboard);

        recyclerView = findViewById(R.id.recyclerview_top_players_activity);
        databaseReference = FirebaseDatabase.getInstance().getReference("Players");
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        adapter = new LeadershipDashboardAdapter(list, this);
        recyclerView.setAdapter(adapter);

        backBtn = findViewById(R.id.activity_top_players_back_btn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SelectGameActivity.class));
                finish();
            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()) {
                    PlayerModel playerModel = ds.getValue(PlayerModel.class);
                    list.add(playerModel);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}