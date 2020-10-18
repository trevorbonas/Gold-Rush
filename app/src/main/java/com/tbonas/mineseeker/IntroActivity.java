package com.tbonas.mineseeker;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * The welcoming intro animation.
 *
 * Displays the game title, creator, calls animations on ImageViews, provides a button to
 * skip to the main menu, and prevents the user from using the android "back" button to
 * return to the intro screen.
 */
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
       Animation titleAnimation = AnimationUtils.loadAnimation(this,
                R.anim.intro_animation);
        title.startAnimation(titleAnimation);

        ImageView gold = findViewById(R.id.introGold);
        Animation goldAnimation = AnimationUtils.loadAnimation(this,
                R.anim.gold_animation);
        gold.startAnimation(goldAnimation);
    }
}