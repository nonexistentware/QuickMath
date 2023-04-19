package com.nonexistentware.quickmath.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.nonexistentware.quickmath.Activity.SelectGameActivity;
import com.nonexistentware.quickmath.Difficult.FirstMistakeGameMode;
import com.nonexistentware.quickmath.R;
// игра до первой ошибки. количество правильных ответов превращается в очки. доступен в определенное время. например раз в неделю

public class FirstMistakeActivity extends AppCompatActivity {

    TextView startBtn, aboutMode, hideBtn, backBtn, playerScore, scoreCheckTxt;
    ImageView questionMark;

    DatabaseReference reference;
    FirebaseDatabase database;
    FirebaseAuth auth;
    FirebaseUser fUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_mistake);

        auth = FirebaseAuth.getInstance();
        fUser = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("Players").child(fUser.getUid());

        startBtn = findViewById(R.id.first_mistake_start_game);
        aboutMode = findViewById(R.id.first_mistake_about_changeable);
        hideBtn = findViewById(R.id.first_mistake_hide_text);
        backBtn = findViewById(R.id.first_mistake_back_btn);
        questionMark = findViewById(R.id.first_mistake_question_mark);

        playerScore = findViewById(R.id.first_mistake_start_game_score);
        scoreCheckTxt = findViewById(R.id.first_mistake_start_game_score_text_check);


        aboutMode.setVisibility(View.INVISIBLE);
        hideBtn.setVisibility(View.INVISIBLE);

        loadUserScore();
        scoreCheck();

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
                aboutMode.setText("Правила: \n Намагайтеся грати без помилок. Якщо ви отримаєте один - гра зупиниться без збереження результатів." +
                        "                         \n Кожне запитання = 1 бал для вас. Ігровий режим недоступний щодня." +
                        "                         \n Щоб іграшка зберегла результати, потрібно відповісти на 100 запитань. Якщо ви виконаєте це, усі результати надійдуть до вашого облікового запису" +
                        "                         \n Якщо ви залишите гру, результати не будуть додані до вашого облікового запису.");
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

    private void loadUserScore() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("playerScore").exists()) {
                    String scoreStr = snapshot.child("playerScore").getValue().toString().trim();
                    playerScore.setText(scoreStr);
                } else {
                    String scoreStrCheck = "0";
                    playerScore.setText(scoreStrCheck);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void scoreCheck() {
        reference.child("playerScore").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue(Integer.class) <= 999) {
                    scoreCheckTxt.setText("Не вистачає балів для початку гри");
                    startBtn.setVisibility(View.INVISIBLE);
                } else if (snapshot.getValue(Integer.class) >= 1000) {
                    scoreCheckTxt.setText("Балів для поатку гри достатньо");
                    startBtn.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
                scoreCalculator();
                readyDialog.dismiss();
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

    private void scoreCalculator() { //score to minus from total player score counter
        reference.child("playerScore").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue(Integer.class) >= 1000) {
                    reference.child("playerScore").setValue(ServerValue.increment(-1000));
                    startActivity(new Intent(getApplicationContext(), FirstMistakeGameMode.class));
                    finish();
                } else if (snapshot.getValue(Integer.class) <= 999) {
                    Toast.makeText(FirstMistakeActivity.this, "Not enough score to play", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), SelectGameActivity.class));
        finish();
    }
}