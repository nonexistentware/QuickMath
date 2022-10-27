package com.nonexistentware.quickmath.Difficult;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nonexistentware.quickmath.Activity.SelectGameActivity;
import com.nonexistentware.quickmath.R;

import java.util.ArrayList;
import java.util.Random;

public class FirstMistakeGameMode extends AppCompatActivity {

    TextView scoreCounter, questionCounter, questionView;
    Button button0;
    Button button1;
    Button button2;
    Button button3;

    Random random = new Random();
    int a;
    int b;
    int indexOfCorrectAnswer;
    ArrayList<Integer> answers = new ArrayList<Integer>();
    int correctPoints = 0;
    int wrongPoints = 0;

    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference databaseReference;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_mistake_game_mode);

        scoreCounter = findViewById(R.id.first_mistake_score_counter);
        questionView = findViewById(R.id.first_mistake_question_vew);
        questionCounter = findViewById(R.id.first_mistake_question_done_counter);

        button0 = findViewById(R.id.fm_button0);
        button1 = findViewById(R.id.fm_button1);
        button2 = findViewById(R.id.fm_button2);
        button3 = findViewById(R.id.fm_button3);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Players");
        questionCounter.setText(Integer.toString(correctPoints));

        NextQuestion();
        loadPlayerData();
    }

    @SuppressLint("SetTextI18n")
    private void NextQuestion() {
        a = random.nextInt(10);
        b = random.nextInt(10);
        questionView.setText(a + "*" + b);

        indexOfCorrectAnswer = random.nextInt(4);
        answers.clear();
        for (int i = 0; i < 4; i++) {

            if (indexOfCorrectAnswer == i) {
                answers.add(a * b);
            } else {
                int wrongAnswer = random.nextInt(20);
                while (wrongAnswer == a * b) {

                    wrongAnswer = random.nextInt(20);
                }
                answers.add(wrongAnswer);
            }
        }

        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));
    }

    public void optionSelect(View view) {
        if (Integer.toString(indexOfCorrectAnswer).equals(view.getTag().toString())) {
            correctPoints++;
            questionCounter.setText(Integer.toString(correctPoints));
             // if player answer all questions redirect to activity end
        } else {
            wrongPoints++;
            Toast.makeText(this, "Fuck", Toast.LENGTH_SHORT).show();
        }
        NextQuestion();
    }

    private void loadPlayerData() {
        databaseReference.child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("playerScore").exists()) {
                    String scoreStr = snapshot.child("playerScore").getValue().toString().trim();
                    scoreCounter.setText(scoreStr);
                } else {
                    String scoreStrCheck = "0";
                    scoreCounter.setText(scoreStrCheck);
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