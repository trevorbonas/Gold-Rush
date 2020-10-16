package com.tbonas.mineseeker;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class IntroActivity extends android.app.Activity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        final Intent homeIntent = new Intent(IntroActivity.this,
                MainActivity.class);

        final CountDownTimer timer = new CountDownTimer(7000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Do nothing
            }

            @Override
            public void onFinish() {

                startActivity(homeIntent);
                finish();
            }
        }.start();

        Button skipButton = (Button)findViewById(R.id.skip_button);
        findViewById(R.id.skip_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(homeIntent);
                timer.cancel();
                finish();
            }
        });

        TextView title = findViewById(R.id.intro_title);
        Animation title_animation = AnimationUtils.loadAnimation(this,
                R.anim.intro_animation);
        title.startAnimation(title_animation);
    }
}