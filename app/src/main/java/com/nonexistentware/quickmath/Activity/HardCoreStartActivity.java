package com.nonexistentware.quickmath.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nonexistentware.quickmath.R;

public class HardCoreStartActivity extends AppCompatActivity {

    ImageView satanStar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hard_core_start);

        satanStar = findViewById(R.id.satan_start_gif);

        Glide.with(this)
                .load(R.drawable.pentagram_anim)
                .into(satanStar);
    }
}