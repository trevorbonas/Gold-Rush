package com.tbonas.mineseeker;

import android.content.Intent;
import android.os.Bundle;

import com.tbonas.mineseeker.model.Mine;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

/**
 * The main menu.
 *
 * Handles the initialization of the singleton and provides the UI elements
 * for navigating through and playing the game.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Only purpose of this is to initialize the singleton
        Mine mine = Mine.getInstance();
        // Just in case we're returning from playing a game
        // Makes sure there's no gold left in already found places
        mine.clear();
        // In case options have already been set before and application is
        // starting again
        mine.resize(OptionsActivity.getSavedCol(MainActivity.this),
                OptionsActivity.getSavedRows(MainActivity.this));
        mine.setNumGold(OptionsActivity.getSavedGold(MainActivity.this));

        Button help = (Button)findViewById(R.id.helpButton);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent helpIntent = new Intent(MainActivity.this,
                        HelpActivity.class);
                startActivity(helpIntent);
            }
        });

        Button options = (Button)findViewById(R.id.optionsButton);
        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent optionsIntent = new Intent(MainActivity.this,
                        OptionsActivity.class);
                startActivity(optionsIntent);
            }
        });

        Button play = (Button)findViewById(R.id.playButton);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent playIntent = new Intent(MainActivity.this,
                        GameActivity.class);
                startActivity(playIntent);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
}