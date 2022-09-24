package com.nonexistentware.quickmath.Difficult;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.LayoutInflater;
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
import com.nonexistentware.quickmath.Activity.SelectGameActivity;
import com.nonexistentware.quickmath.R;

import java.util.ArrayList;
import java.util.Random;

public class HardClassicMode extends AppCompatActivity {

    TextView timerTxt, correctTxt, wrongTxt, classicGameMode, difficultyLevelTxt, scoreTxt, levelTxt;
    TextView attemptsTxt; //custom alert dialog
    TextView totalQuestionTxt;
    TextView QuestionTextView;
    Button button0;
    Button button1;
    Button button2;
    Button button3;
    CountDownTimer countDownTimer;

    //data to transfer
    public static final String EXTRA_NUMBER_NGTV = "com.nonexistentware.quickmath.Difficult.EXTRA_NUMBER_NGTV";
    public static final String EXTRA_NUMBER_PSTV = "com.nonexistentware.quickmath.Difficult.EXTRA_NUMBER_PSTV";
    public static final String EXTRA_TIME_LEFT = "com.nonexistentware.quickmath.Difficult.EXTRA_TIME_LEFT";
    public static final String EXTRA_DIFFICULT_LEVEL = "com.nonexistentware.quickmath.Difficult.EXTRA_DIFFICULT_LEVEL";
    public static final String EXTRA_TIME_REMAIN = "com.nonexistentware.quickmath.Difficult.EXTRA_TIME_REMAIN";
    public static final String EXTRA_LEVEL_INCREASE_COUNTER = "com.nonexistentware.quickmath.Difficult.EXTRA_INCREASE_COUNTER";

    Random random = new Random();
    int a;
    int b;
    int indexOfCorrectAnswer;
    ArrayList<Integer> answers = new ArrayList<Integer>();
    int correctPoints = 0;
    int wrongPoints = 0;
    int totalQuestions = 0;
    int totalQuestionToLow = 10; //total questions counter
    int attempts = 3; //attempts per day. Drop after specific hour
    long levelIncrease = 0; // in case correct points == totalQuestionsToLow level will be increased


    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference, levelCounterRef;
    private FirebaseDatabase firebaseDatabase;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hard_classic_mode);

        timerTxt = findViewById(R.id.TimeTextView);
        scoreTxt = findViewById(R.id.hard_mode_score);
        levelTxt = findViewById(R.id.hard_mode_level);
        totalQuestionTxt = findViewById(R.id.total_question);
        QuestionTextView = findViewById(R.id.QuestionTextView);
        correctTxt = findViewById(R.id.correct_answer);
        wrongTxt = findViewById(R.id.wrong_answer);
        classicGameMode = findViewById(R.id.classic_game_mode);
        difficultyLevelTxt = findViewById(R.id.difficulty_level_classic_game_mode);

        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);

        totalQuestionTxt.setText(Integer.toString(totalQuestionToLow));
        correctTxt.setText(Integer.toString(0));
        wrongTxt.setText(Integer.toString(0));

        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseUser = auth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Players").child(firebaseUser.getUid());
        levelCounterRef = FirebaseDatabase.getInstance().getReference("Players").child("playerLevel");

        countDownTimer();
        loadPlayerData();
    }

    @SuppressLint("SetTextI18n")
    public void NextQuestion() {
        a = random.nextInt(10);
        b = random.nextInt(10);
        QuestionTextView.setText(a + "*" + b);

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
        totalQuestions++;
        if (Integer.toString(indexOfCorrectAnswer).equals(view.getTag().toString())) {
            correctPoints++;
            totalQuestionToLow--;
            totalQuestionTxt.setText(Integer.toString(totalQuestionToLow));
            correctTxt.setText(Integer.toString(correctPoints));
            allQuestionDone(); // if player answer all questions redirect to activity end

        } else {
            wrongPoints++;
            totalQuestionToLow--;
            totalQuestionTxt.setText(Integer.toString(totalQuestionToLow));
            wrongTxt.setText(Integer.toString(wrongPoints));
            allQuestionDone(); //  if player answer all questions redirect to activity end
        }

        NextQuestion();
    }

    //after count down timer stop, buttons and score should locked
    private void countDownTimer() {
        NextQuestion();
        countDownTimer = new CountDownTimer(26000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerTxt.setText(String.valueOf(millisUntilFinished / 1000) + "s");
            }

            @Override
            public void onFinish() {
                timerTxt.setText("Time up!");
                button0.setEnabled(false);
                button1.setEnabled(false);
                button2.setEnabled(false);
                button3.setEnabled(false);
                Intent transferIntent = new Intent(getBaseContext(), EndGameActivity.class);
                int number = Integer.parseInt(wrongTxt.getText().toString());
                transferIntent.putExtra(EXTRA_NUMBER_NGTV, number);
                int numberPSTV = Integer.parseInt(correctTxt.getText().toString());
                transferIntent.putExtra(EXTRA_NUMBER_PSTV, numberPSTV);
                transferIntent.putExtra(EXTRA_TIME_REMAIN, timerTxt.getText().toString());
                transferIntent.putExtra(EXTRA_DIFFICULT_LEVEL, difficultyLevelTxt.getText().toString());
//                uploadRemainingTime();
//                uploadTypeOfGameMode();
                playedGamesCounter();
                startActivity(transferIntent);
                cancel();
                finish();
            }
        }.start();
    }

    private void allQuestionDone() {
        if (totalQuestionToLow == 0) {
            countDownTimer.cancel();
            button0.setEnabled(false);
            button1.setEnabled(false);
            button2.setEnabled(false);
            button3.setEnabled(false);
            playerLevelCounterOnline();
            playerScoreCounterOnline();
            playedGamesCounter();
//            uploadTypeOfGameMode();
            Intent transferIntent = new Intent(getBaseContext(), EndGameActivity.class);
            int numberNGTV = Integer.parseInt(wrongTxt.getText().toString());
            transferIntent.putExtra(EXTRA_NUMBER_NGTV, numberNGTV);
            int numberPSTV = Integer.parseInt(correctTxt.getText().toString());
            transferIntent.putExtra(EXTRA_NUMBER_PSTV, numberPSTV);
            transferIntent.putExtra(EXTRA_TIME_REMAIN, timerTxt.getText().toString());
            transferIntent.putExtra(EXTRA_DIFFICULT_LEVEL, difficultyLevelTxt.getText().toString());
//            uploadRemainingTime();                                                                                                //upload time to data base
            startActivity(transferIntent);
            finish();
        }
    }

    private void loadPlayerData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("playerScore").exists()) {
                    String scoreStr = snapshot.child("playerScore").getValue().toString().trim();
                    scoreTxt.setText(scoreStr);
                } else {
                    String scoreStrCheck = "0";
                    scoreTxt.setText(scoreStrCheck);
                } if (snapshot.child("playerLevel").exists()) {
                    String levelStr = snapshot.child("playerLevel").getValue().toString().trim();
                    levelTxt.setText(levelStr);
                } else {
                    String scoreStrCheck = "0";
                    levelTxt.setText(scoreStrCheck);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void uploadRemainingTime() { //upload temp data
        databaseReference.child(auth.getCurrentUser().getUid()).child("remainCounterTimeTemp").setValue(timerTxt.getText().toString().trim());
//        finish();
    }

    private void playerLevelCounterOnline() { // increment of player level
        if (wrongPoints == 0) {
//            databaseReference.child("playerLevel").setValue(ServerValue.increment(1).toString().trim());
            databaseReference.child("playerLevel").setValue(ServerValue.increment(1));
        }
    }

    private void playerScoreCounterOnline() {
        if (wrongPoints == 0) {
            databaseReference.child("playerScore").setValue(ServerValue.increment(10));
        }
    }

    private void playedGamesCounter() {
        databaseReference.child("totalPlayedGamesCounter").setValue(ServerValue.increment(1));
    }

    private void attemptsToStartTheGame() {
        databaseReference.child("attemptsToStartTheGame").setValue(ServerValue.increment(1));
    }

    private void uploadTypeOfGameMode() {
        databaseReference.child(auth.getCurrentUser().getUid()).child("classicGameMode").setValue(classicGameMode.getText().toString().trim());
//        databaseReference.child(auth.getCurrentUser().getUid()).child("difficultLevel").setValue(difficultyLevelTxt.getText().toString().trim());
    }

    private void blink() {
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                int timeBlink = 1000;
                try {
                    Thread.sleep(timeBlink);
                } catch (Exception e) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
//                            if ()
                        }
                    });
                }
            }
        }).start();
    }

    @Override
    public void onBackPressed() {
       final Dialog dialog = new Dialog(HardClassicMode.this);
       dialog.setContentView(R.layout.alert_dialog_exit);
       dialog.setCancelable(true);

        TextView yesBtn = dialog.findViewById(R.id.alert_dialog_exit_positive_btn);
        TextView noBtn = dialog.findViewById(R.id.alert_dialog_exit_negative_btn);

        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(),"Yes", Toast.LENGTH_LONG).show();
                attemptsToStartTheGame();
                countDownTimer.cancel();
                startActivity(new Intent(getApplicationContext(), SelectGameActivity.class));
                finish();
            }
        });

        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(),"No", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}