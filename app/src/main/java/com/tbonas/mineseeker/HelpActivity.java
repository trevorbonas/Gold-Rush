package com.tbonas.mineseeker;


import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Html;
import android.view.View;
import android.widget.TextView;

/**
 * Simply displays text about the game and provides a back button in the action bar.
 */
public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        TextView helpText = findViewById(R.id.helpContent);
        helpText.setText(R.string.helpText);

        TextView courseTitle = findViewById(R.id.courseTitle);
        courseTitle.setText(R.string.courseTitle);
        courseTitle.setTextColor(getResources().getColor(R.color.solid_white,
                this.getTheme()));

        TextView web = findViewById(R.id.courseLink);
        web.setText(Html.fromHtml(getString(R.string.website), Html.FROM_HTML_MODE_COMPACT));
        web.setTextColor(getResources().getColor(R.color.solid_white,
                this.getTheme()));
    }
}