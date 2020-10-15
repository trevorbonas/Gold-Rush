package com.tbonas.mineseeker;

import android.content.Intent;
import android.os.Bundle;
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

        Button skip_button = (Button)findViewById(R.id.skip_button);
        findViewById(R.id.skip_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home_intent = new Intent(IntroActivity.this,
                        MainActivity.class);
                startActivity(home_intent);
            }
        });

        TextView title = findViewById(R.id.intro_title);
        Animation title_animation = AnimationUtils.loadAnimation(this,
                R.anim.intro_animation);
        title.startAnimation(title_animation);
    }
}