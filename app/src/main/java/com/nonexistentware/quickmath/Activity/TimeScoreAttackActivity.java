package com.nonexistentware.quickmath.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.nonexistentware.quickmath.R;

// игровой режим на время и очки. По прохождению вам дается время для ответа на 10. Далее время добавляется или отнимается или движется с одной скростью
// если игрок отвтил на 10 правильно время добавляется + очки
// после каждіх 10 вопросов время ускоряется (становится меньше)

public class TimeScoreAttackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_score_attck);
    }
}