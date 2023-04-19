package com.nonexistentware.quickmath.Difficult;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
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
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.nonexistentware.quickmath.Activity.EndGameActivity;
import com.nonexistentware.quickmath.Activity.EndGameActivity2;
import com.nonexistentware.quickmath.Activity.SelectGameActivity;
import com.nonexistentware.quickmath.R;

import java.util.ArrayList;
import java.util.Random;

public class FirstMistakeGameMode extends AppCompatActivity {

    TextView scoreCounter, questionCounter, questionView, mustScoreTxt, mustScoreBanner, wrongTxt, difficultLevel, firstMistakeMode;
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
    int mastScores = 0; // обязательные очки для продоложенимя игры после их достижения сотальные добавятся на аккаунт игрока + обязательные очки
    int mustScore = 2; // очки для добавления в аккаунт пользователя

    //data to transfer
    public static final String FIRST_MISTAKE_EXTRA_NUMBER_NGTV = "com.nonexistentware.quickmath.Difficult.EXTRA_NUMBER_NGTV";
    public static final String FIRST_MISTAKE_EXTRA_NUMBER_PSTV = "com.nonexistentware.quickmath.Difficult.EXTRA_NUMBER_PSTV";
    public static final String FIRST_MISTAKE_EXTRA_DIFFICULT_LEVEL = "com.nonexistentware.quickmath.Difficult.EXTRA_DIFFICULT_LEVEL";
    public static final String FIRST_MISTAKE_EXTRA_GAME_MODE = "com.nonexistentware.quickmath.Difficult.EXTRA_GAME_TYPE";
    public static final String FIRST_MISTAKE_EXTRA_MANDATORY_LEFT = "com.nonexistentware.quickmath.Difficult.EXTRA_MANDATORY_LEFT";


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
        mustScoreTxt = findViewById(R.id.first_mistake_questions_to_pass_counter_down);
        mustScoreBanner = findViewById(R.id.first_mistake_must_score_banner);
        wrongTxt = findViewById(R.id.first_mistake_questions_wrong_txt);
        difficultLevel = findViewById(R.id.difficulty_level_first_mistake);
        firstMistakeMode = findViewById(R.id.first_mistake_game_mode);

        button0 = findViewById(R.id.fm_button0);
        button1 = findViewById(R.id.fm_button1);
        button2 = findViewById(R.id.fm_button2);
        button3 = findViewById(R.id.fm_button3);

        wrongTxt.setVisibility(View.INVISIBLE);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Players");
        questionCounter.setText(Integer.toString(correctPoints));

        NextQuestion();
        loadPlayerData();
        mustScoreTxt.setText(Integer.toString(mustScore));
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
            mustScore--;
            mustScoreTxt.setText(Integer.toString(mustScore));
            scoreToCheck();
            questionCounter.setText(Integer.toString(correctPoints));
//            timeLocker();
             // if player answer all questions redirect to activity end
        } else {
            correctPoints++;
            wrongTxt.setText(Integer.toString(wrongPoints));
            mustScoreBanner.setText("Завдання провалено!");
            mustScoreTxt.setVisibility(View.INVISIBLE);
            Toast.makeText(this, "Насутпного разу повезе...", Toast.LENGTH_SHORT).show();
            playedGamesCounter();
            scoreToSave();
            timeLocker();
            firstMistakeDone();
            firstMistakeDone();
//            startActivity(new Intent(getApplicationContext(), EndGameActivity2.class));
//            finish();
        }
        NextQuestion();
    }

    private void firstMistakeDone() {
        button0.setEnabled(false);
        button1.setEnabled(false);
        button2.setEnabled(false);
        button3.setEnabled(false);
        Intent transferIntent = new Intent(getBaseContext(), EndGameActivity2.class);
        int numberNGTV = Integer.parseInt((wrongTxt.getText().toString()));
        transferIntent.putExtra(FIRST_MISTAKE_EXTRA_NUMBER_NGTV, numberNGTV);
        int numberPSTV = Integer.parseInt(questionCounter.getText().toString());
        transferIntent.putExtra(FIRST_MISTAKE_EXTRA_NUMBER_PSTV, numberPSTV);
        int mandatoryLeft = Integer.parseInt(mustScoreTxt.getText().toString());
        transferIntent.putExtra(FIRST_MISTAKE_EXTRA_MANDATORY_LEFT, mandatoryLeft);
        transferIntent.putExtra(FIRST_MISTAKE_EXTRA_DIFFICULT_LEVEL, difficultLevel.getText().toString());
        transferIntent.putExtra(FIRST_MISTAKE_EXTRA_GAME_MODE, firstMistakeMode.getText().toString());
        startActivity(transferIntent);
        finish();

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

    private void scoreToCheck() {
        if (mustScore == 0) {
            mustScoreTxt.setVisibility(View.INVISIBLE);
            mustScoreBanner.setText("Task completed!");
        }
    }

    private void scoreToSave() {
        if (mustScore < correctPoints) {
//            String txtTotalQuestionCounter = questionCounter.getText().toString();
            long longQuestionDoneCounter;
            longQuestionDoneCounter = Long.parseLong(questionCounter.getText().toString());
            databaseReference.child(auth.getCurrentUser().getUid()).child("playerScore").setValue(ServerValue.increment(longQuestionDoneCounter));
        }
    }

    private void timeLocker() {
        databaseReference.child(auth.getCurrentUser().getUid()).child("firstMistakeTimeLocker").setValue("1");
    }

    private void attemptsToStartTheGame(){
        databaseReference.child(auth.getCurrentUser().getUid()).child("attemptsToStartTheGame").setValue(ServerValue.increment(1));
    }

    private void playedGamesCounter() {
        databaseReference.child(auth.getCurrentUser().getUid()).child("totalPlayedGamesCounter").setValue(ServerValue.increment(1));
    }

    @Override
    public void onBackPressed() {
        final Dialog dialog = new Dialog(FirstMistakeGameMode.this);
        dialog.setContentView(R.layout.alert_dialog_first_mistake);
        dialog.setCancelable(true);

        TextView yesBtn = dialog.findViewById(R.id.alert_dialog_exit_positive_btn);
        TextView noBtn = dialog.findViewById(R.id.alert_dialog_exit_negative_btn);

        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptsToStartTheGame();
                startActivity(new Intent(getApplicationContext(), SelectGameActivity.class));
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