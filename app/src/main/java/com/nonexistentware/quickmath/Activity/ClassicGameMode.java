package com.nonexistentware.quickmath.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nonexistentware.quickmath.R;

import java.util.ArrayList;
import java.util.Random;

public class ClassicGameMode extends AppCompatActivity {
    TextView timerTxt, correctTxt, wrongTxt;
    TextView attemptsTxt; //custom alert dialog
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
    int totalQuestionToLow = 5; //total questions counter
    int attempts = 3; //attempts per day. Drop after specific hour.
    int clickAttemptsCounter = 0;

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
            allQuestionDone(); // if player answer all questions redirect to activity end

        } else {
            wrongPoints++;
            totalQuestionToLow--;
            totalQuestionTxt.setText(Integer.toString(totalQuestionToLow));
            wrongTxt.setText(Integer.toString(wrongPoints) + "Wrong");
            allQuestionDone(); //  if player answer all questions redirect to activity end
        }

        NextQuestion();
    }

    //after count down timer stop, buttons and score should locked
    private void countDownTimer() {
        NextQuestion();
        countDownTimer = new CountDownTimer(11000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerTxt.setText(String.valueOf(millisUntilFinished/1000)+"s");
            }

            @Override
            public void onFinish() {
                timerTxt.setText("Time up!");
                button0.setEnabled(false);
                button1.setEnabled(false);
                button2.setEnabled(false);
                button3.setEnabled(false);
                    startActivity(new Intent(ClassicGameMode.this, EndGameActivity.class));
                    finish();
                }
        }.start();
    }

    private void allQuestionDone() {
        if (totalQuestionToLow == 0) {
            countDownTimer.cancel();
            startActivity(new Intent(ClassicGameMode.this, EndGameActivity.class));
            finish();
        }
    }

    private void askToReplayDialog() {
       final AlertDialog.Builder builder = new AlertDialog.Builder(ClassicGameMode.this);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.alert_dialog_classicgame, null);
        attemptsTxt = view.findViewById(R.id.attempts_counter);
        builder.setTitle("Your score is too low. Do you wont to reply?");
        builder.setMessage(attempts-- + " attempts left" ) //set attempts counter
                .setPositiveButton("Replay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // reload activity
                        attempts--;
                        attemptsTxt.setText(Integer.toString(attempts));
                        finish();
                        startActivity(getIntent());
                    }
                })
                .setNegativeButton("Finish the game", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(ClassicGameMode.this, EndGameActivity.class));
                        finish();
                    }
                });
            AlertDialog alertDialog = builder.create();
            alertDialog.setCancelable(false);
            alertDialog.show();
            alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);


    }

    private void askToEndDialog() { //in case player wish to press back or end of the game. Alert window.

    }

    private void endOfTheGame() {
        startActivity(new Intent(ClassicGameMode.this, EndGameActivity.class));
        finish();
    }
}