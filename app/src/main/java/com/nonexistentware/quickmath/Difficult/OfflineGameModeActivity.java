package com.nonexistentware.quickmath.Difficult;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nonexistentware.quickmath.Activity.EndGameActivity;
import com.nonexistentware.quickmath.Activity.EndGameActivityOffline;
import com.nonexistentware.quickmath.Activity.MainActivity;
import com.nonexistentware.quickmath.R;

import java.util.ArrayList;
import java.util.Random;

public class OfflineGameModeActivity extends AppCompatActivity {

    TextView timerTxt, correctTxt, wrongTxt, classicGameMode, difficultyLevelTxt;
    TextView attemptsTxt; //custom alert dialog
    TextView totalQuestionTxt;
    TextView QuestionTextView;
    Button button0;
    Button button1;
    Button button2;
    Button button3;
    CountDownTimer countDownTimer;

    public static final String EXTRA_NUMBER_NGTV_OFFLINE = "com.nonexistentware.quickmath.Difficult.EXTRA_NUMBER_NGTV";
    public static final String EXTRA_NUMBER_PSTV_OFFLINE = "com.nonexistentware.quickmath.Difficult.EXTRA_NUMBER_PSTV";
    public static final String EXTRA_TIME_LEFT_OFFLINE = "com.nonexistentware.quickmath.Difficult.EXTRA_TIME_LEFT";
    public static final String EXTRA_DIFFICULT_LEVEL_OFFLINE = "com.nonexistentware.quickmath.Difficult.EXTRA_DIFFICULT_LEVEL";
    public static final String EXTRA_TIME_REMAIN_OFFLINE = "com.nonexistentware.quickmath.Difficult.EXTRA_TIME_REMAIN";
    public static final String EXTRA_LEVEL_INCREASE_COUNTER_OFFLINE = "com.nonexistentware.quickmath.Difficult.EXTRA_INCREASE_COUNTER";
    public static final String EXTRA_GAME_MODE_OFFLINE = "com.nonexistentware.quickmath.Difficult.EXTRA_GAME_TYPE";
    public static final String EXTRA_QUESTIONS_LEFT_OFFLINE = "com.nonexistentware.quickmath.Difficult.EXTRA_QUESTIONS_LEFT";

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_game_mode);

        timerTxt = findViewById(R.id.TimeTextView_offline);
        totalQuestionTxt = findViewById(R.id.total_question_offline);
        QuestionTextView = findViewById(R.id.QuestionTextView_offline);
        correctTxt = findViewById(R.id.correct_answer_offline);
        wrongTxt = findViewById(R.id.wrong_answer_offline);
        classicGameMode = findViewById(R.id.offline_game_mode);

        button0 = findViewById(R.id.button0_offline);
        button1 = findViewById(R.id.button1_offline);
        button2 = findViewById(R.id.button2_offline);
        button3 = findViewById(R.id.button3_offline);

        totalQuestionTxt.setText(Integer.toString(totalQuestionToLow));
        correctTxt.setText(Integer.toString(0));
        wrongTxt.setText(Integer.toString(0));

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
        totalQuestions++; //correct answers validator
        if (Integer.toString(indexOfCorrectAnswer).equals(view.getTag().toString())) {
            correctPoints++;
            totalQuestionToLow--;
            totalQuestionTxt.setText(Integer.toString(totalQuestionToLow));
            correctTxt.setText(Integer.toString(correctPoints));
            allQuestionDone(); // if player answer all questions redirect to activity end

        } else {
            wrongPoints++; //wrong answers validator
            totalQuestionToLow--;
            totalQuestionTxt.setText(Integer.toString(totalQuestionToLow));
            wrongTxt.setText(Integer.toString(wrongPoints));
            allQuestionDone(); //  if player answer all questions redirect to activity end
        }

        NextQuestion();
    }

    private void countDownTimer() {
        NextQuestion();
        countDownTimer = new CountDownTimer(61000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerTxt.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                timerTxt.setText("Time up!");
                button0.setEnabled(false);
                button1.setEnabled(false);
                button2.setEnabled(false);
                button3.setEnabled(false);
                Intent transferIntent = new Intent(getBaseContext(), EndGameActivityOffline.class);
                int number = Integer.parseInt(wrongTxt.getText().toString());
                transferIntent.putExtra(EXTRA_NUMBER_NGTV_OFFLINE, number);
                int numberPSTV = Integer.parseInt(correctTxt.getText().toString());
                transferIntent.putExtra(EXTRA_NUMBER_PSTV_OFFLINE, numberPSTV);
                transferIntent.putExtra(EXTRA_TIME_REMAIN_OFFLINE, timerTxt.getText().toString());
//                transferIntent.putExtra(EXTRA_DIFFICULT_LEVEL_OFFLINE, difficultyLevelTxt.getText().toString());
                transferIntent.putExtra(EXTRA_GAME_MODE_OFFLINE, classicGameMode.getText().toString());
                transferIntent.putExtra(EXTRA_QUESTIONS_LEFT_OFFLINE, totalQuestionTxt.getText().toString());
                startActivity(transferIntent);
                cancel();
                finish();
            }
        }.start();
    }

    private void allQuestionDone() {
        if (totalQuestionToLow == 0) {
            totalQuestionTxt.setText("All done!");
            countDownTimer.cancel();
            button0.setEnabled(false);
            button1.setEnabled(false);
            button2.setEnabled(false);
            button3.setEnabled(false);
//            playerLevelCounterOnline();
//            uploadTypeOfGameMode();
            Intent transferIntent = new Intent(getBaseContext(), EndGameActivityOffline.class);
            int numberNGTV = Integer.parseInt(wrongTxt.getText().toString());
            transferIntent.putExtra(EXTRA_NUMBER_NGTV_OFFLINE, numberNGTV);
            int numberPSTV = Integer.parseInt(correctTxt.getText().toString());
            transferIntent.putExtra(EXTRA_NUMBER_PSTV_OFFLINE, numberPSTV);
            transferIntent.putExtra(EXTRA_GAME_MODE_OFFLINE, classicGameMode.getText().toString());
            transferIntent.putExtra(EXTRA_TIME_REMAIN_OFFLINE, timerTxt.getText().toString());
//            transferIntent.putExtra(EXTRA_DIFFICULT_LEVEL_OFFLINE, difficultyLevelTxt.getText().toString());
            transferIntent.putExtra(EXTRA_QUESTIONS_LEFT_OFFLINE, totalQuestionTxt.getText().toString());
//            uploadRemainingTime();                                                                                                //upload time to data base
            startActivity(transferIntent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        countDownTimer.cancel();
        finish();
    }
}