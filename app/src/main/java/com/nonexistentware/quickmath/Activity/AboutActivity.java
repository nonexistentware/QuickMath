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
import com.nonexistentware.quickmath.Adapter.AppNewsAdapter;
import com.nonexistentware.quickmath.Model.AppNewsModel;
import com.nonexistentware.quickmath.R;

import java.util.ArrayList;

public class AboutActivity extends AppCompatActivity {

    TextView backBtn;

    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    AppNewsAdapter adapter;
    ArrayList<AppNewsModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        recyclerView = findViewById(R.id.recyclerview_about_activity);
        databaseReference = FirebaseDatabase.getInstance().getReference("AppNews");
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        adapter = new AppNewsAdapter(list, this);
        recyclerView.setAdapter(adapter);

        backBtn = findViewById(R.id.activity_about_back_btn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SelectGameActivity.class));
                finish();
            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()) {
                    AppNewsModel appNewsModel = ds.getValue(AppNewsModel.class);
                    list.add(appNewsModel);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}