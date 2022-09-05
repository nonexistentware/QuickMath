package com.nonexistentware.quickmath.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.nonexistentware.quickmath.R;

public class DifficultySelectActivity extends AppCompatActivity {

    TextView lEasy, lMedium, lHard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty_select);

        lEasy = findViewById(R.id.dif_level_easy);
        lMedium = findViewById(R.id.dif_level_medium);
        lHard = findViewById(R.id.dif_level_hard);

        lEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                easyLevelAlert();
            }
        });

        lMedium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        lHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void easyLevelAlert() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(DifficultySelectActivity.this);
        builder.setTitle("Easy level selected");
        builder.setMessage("Proceed with this level")
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       startActivity(new Intent(DifficultySelectActivity.this, ClassicGameMode.class));
                       finish();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
    }
}