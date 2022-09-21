package com.nonexistentware.quickmath.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nonexistentware.quickmath.Difficult.ClassicGameMode;
import com.nonexistentware.quickmath.Difficult.HardClassicMode;
import com.nonexistentware.quickmath.Difficult.MediumClassicMode;
import com.nonexistentware.quickmath.R;

public class DifficultySelectActivity extends AppCompatActivity {

    TextView lEasy, lMedium, lHard, aboutChangeable, hideBtn, backBtn;
    ImageView easyMArk, mediumMark, hardMark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty_select);

        lEasy = findViewById(R.id.dif_level_easy);
        lMedium = findViewById(R.id.dif_level_medium);
        lHard = findViewById(R.id.dif_level_hard);

        easyMArk = findViewById(R.id.difficult_select_easy_mark_img);
        mediumMark = findViewById(R.id.difficult_select_medium_mark_img);
        hardMark = findViewById(R.id.difficult_select_hard_mark_img);
        aboutChangeable = findViewById(R.id.dif_select_activity_about_changeable);
        hideBtn = findViewById(R.id.dif_select_hide_text);
        backBtn = findViewById(R.id.dif_select_back_btn);

        aboutChangeable.setVisibility(View.INVISIBLE);
        hideBtn.setVisibility(View.INVISIBLE);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SelectGameActivity.class));
                finish();
            }
        });

        lEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readyPopupEasy();
            }
        });

        lMedium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readyPopupMedium();
            }
        });

        lHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readyPopupHard();
            }
        });

        hideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aboutChangeable.setVisibility(View.INVISIBLE);
                hideBtn.setVisibility(View.INVISIBLE);
            }
        });

        easyMArk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aboutChangeable.setVisibility(View.VISIBLE);
                hideBtn.setVisibility(View.VISIBLE);
                aboutChangeable.setText("Easy. Rules: \n This mode was made to get the game. In this mode score and level does not count. You will get 60 seconds.");
            }
        });

        mediumMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aboutChangeable.setVisibility(View.VISIBLE);
                hideBtn.setVisibility(View.VISIBLE);
                aboutChangeable.setText("Medium. Rules: \n Answer all question correct and rise your level. In this mode score does not count. You will get 30 seconds.");
            }
        });

        hardMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aboutChangeable.setVisibility(View.VISIBLE);
                hideBtn.setVisibility(View.VISIBLE);
                aboutChangeable.setText("Hard. Rules: \n Answer all question correct and rise your level. In case  all correct answers score will count. You will get 15 seconds.");
            }
        });
    }


    private void readyPopupEasy() {
        final Dialog readyDialog = new Dialog(DifficultySelectActivity.this);
        readyDialog.setContentView(R.layout.alert_dialog_ready_for_game);
        readyDialog.setCancelable(false);

        TextView proceedBtn = readyDialog.findViewById(R.id.alert_dialog_ready_for_game_positive_btn);
        TextView noBtn = readyDialog.findViewById(R.id.alert_dialog_ready_for_game_negative_btn);

        proceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ClassicGameMode.class));
                finish();
        }
        });

        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyDialog.dismiss();
            }

        });

        readyDialog.show();
    }

    private void readyPopupMedium() {
        final Dialog dialog = new Dialog(DifficultySelectActivity.this);
        dialog.setContentView(R.layout.alert_dialog_ready_for_game);
        dialog.setCancelable(false);

        TextView proceedBtn = dialog.findViewById(R.id.alert_dialog_ready_for_game_positive_btn);
        TextView noBtn = dialog.findViewById(R.id.alert_dialog_ready_for_game_negative_btn);

        proceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MediumClassicMode.class));
                finish();
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

    private void readyPopupHard() {
        final Dialog dialog = new Dialog(DifficultySelectActivity.this);
        dialog.setContentView(R.layout.alert_dialog_ready_for_game);
        dialog.setCancelable(false);

        TextView proceedBtn = dialog.findViewById(R.id.alert_dialog_ready_for_game_positive_btn);
        TextView noBtn = dialog.findViewById(R.id.alert_dialog_ready_for_game_negative_btn);

        proceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HardClassicMode.class));
                finish();
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