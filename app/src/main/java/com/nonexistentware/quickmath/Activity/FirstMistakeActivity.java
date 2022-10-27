package com.nonexistentware.quickmath.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nonexistentware.quickmath.Difficult.FirstMistakeGameMode;
import com.nonexistentware.quickmath.R;
// игра до первой ошибки. количество правильных ответов превращается в очки. доступен в определенное время. например раз в неделю

public class FirstMistakeActivity extends AppCompatActivity {

    TextView startBtn, aboutMode, hideBtn, backBtn;
    ImageView questionMark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_mistake);

        startBtn = findViewById(R.id.first_mistake_start_game);
        aboutMode = findViewById(R.id.first_mistake_about_changeable);
        hideBtn = findViewById(R.id.first_mistake_hide_text);
        backBtn = findViewById(R.id.first_mistake_back_btn);
        questionMark = findViewById(R.id.first_mistake_question_mark);

        aboutMode.setVisibility(View.INVISIBLE);
        hideBtn.setVisibility(View.INVISIBLE);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGamePopup();
            }
        });

        questionMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aboutMode.setVisibility(View.VISIBLE);
                hideBtn.setVisibility(View.VISIBLE);
                aboutMode.setText("Rules: \n Try to play with out any mistakes. If you do it - game stops. " +
                        "\n Each question = 1 score for you. Game mode does nor available daily.");


            }
        });

        hideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aboutMode.setVisibility(View.INVISIBLE);
                hideBtn.setVisibility(View.INVISIBLE);
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SelectGameActivity.class));
                finish();
            }
        });

    }



    private void startGamePopup() {
        final Dialog readyDialog = new Dialog(FirstMistakeActivity.this);
        readyDialog.setContentView(R.layout.alert_dialog_ready_for_game);
        readyDialog.setCancelable(false);

        TextView proceedBtn = readyDialog.findViewById(R.id.alert_dialog_ready_for_game_positive_btn);
        TextView noBtn = readyDialog.findViewById(R.id.alert_dialog_ready_for_game_negative_btn);

        proceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), FirstMistakeGameMode.class));
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

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), SelectGameActivity.class));
        finish();
    }
}