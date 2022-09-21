package com.nonexistentware.quickmath.Difficult;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.nonexistentware.quickmath.Activity.EndGameActivity;
import com.nonexistentware.quickmath.R;

import java.util.ArrayList;
import java.util.Random;

public class ClassicGameMode extends AppCompatActivity {
    TextView timerTxt, correctTxt, wrongTxt, classicGameMode, difficultyLevelTxt;
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
    int totalQuestionToLow = 30; //total questions counter
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
        setContentView(R.layout.activity_classic_game_mode);

        timerTxt = findViewById(R.id.TimeTextView);
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
        databaseReference = FirebaseDatabase.getInstance().getReference("Players");
        levelCounterRef = FirebaseDatabase.getInstance().getReference("Players").child("playerLevel");

        countDownTimer();
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
        countDownTimer = new CountDownTimer(61000, 1000) {
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
//            playerLevelCounterOnline();
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


    private void uploadRemainingTime() { //upload temp data
        databaseReference.child(auth.getCurrentUser().getUid()).child("remainCounterTimeTemp").setValue(timerTxt.getText().toString().trim());
//        finish();
    }

    private void playerLevelCounterOnline() { // increment of player level
        if (wrongPoints == 0) {
//            databaseReference.child("playerLevel").setValue(ServerValue.increment(1).toString().trim());
            databaseReference.child(auth.getCurrentUser().getUid()).child("playerLevel").setValue(ServerValue.increment(1));
        }
    }

    private void uploadTypeOfGameMode() {
        databaseReference.child(auth.getCurrentUser().getUid()).child("classicGameMode").setValue(classicGameMode.getText().toString().trim());
//        databaseReference.child(auth.getCurrentUser().getUid()).child("difficultLevel").setValue(difficultyLevelTxt.getText().toString().trim());
    }

}
