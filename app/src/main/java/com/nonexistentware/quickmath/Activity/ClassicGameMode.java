package com.nonexistentware.quickmath.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nonexistentware.quickmath.R;

import java.util.ArrayList;
import java.util.Random;

public class ClassicGameMode extends AppCompatActivity {
    TextView timerTxt, correctTxt, wrongTxt;
    TextView totalQuestionTxt;
    TextView QuestionTextView;
    Button button0;
    Button button1;
    Button button2;
    Button button3;
    CountDownTimer countDownTimer;

    Random random = new Random();
    int a;
    int b;
    int indexOfCorrectAnswer;
    ArrayList<Integer> answers = new ArrayList<Integer>();
    int correctPoints = 0;
    int wrongPoints = 0;
    int totalQuestions = 0;
    int totalQuestionToLow = 100; //total questions counter

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

        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);

        totalQuestionTxt.setText(Integer.toString(totalQuestionToLow));
        correctTxt.setText(Integer.toString(0) + "Correct");
        wrongTxt.setText(Integer.toString(0) + "Wrong");

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
            correctTxt.setText(Integer.toString(correctPoints)+"Correct");
            allQuestionDone();
        } else {
            wrongPoints++;
            totalQuestionToLow--;
            totalQuestionTxt.setText(Integer.toString(totalQuestionToLow));
            wrongTxt.setText(Integer.toString(wrongPoints)+"Wrong");
            allQuestionDone();
        }

        NextQuestion();
    }

    //after count down timer stop, buttons and score should locked
    private void countDownTimer() {
        NextQuestion();
        countDownTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerTxt.setText(String.valueOf(millisUntilFinished/1000)+"s");
            }

            @Override
            public void onFinish() {
                timerTxt.setText("Time up!");
                startActivity(new Intent(ClassicGameMode.this, EndGameActivity.class));
                finish();
            }
        }.start();
    }

    private void allQuestionDone() {
        if (totalQuestionToLow == 0) {
            startActivity(new Intent(ClassicGameMode.this, EndGameActivity.class));
            finish();
        } else {

        }
    }

}