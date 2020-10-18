package com.tbonas.mineseeker;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.tbonas.mineseeker.model.Mine;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Provides two radio groups for changing the settings (qualities of the singleton) of the game.
 */
public class OptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        TextView dimensionsTitle = findViewById(R.id.mine_size);
        dimensionsTitle.setText(R.string.mine_size);

        TextView goldTitle = findViewById(R.id.num_ores_title);
        goldTitle.setText(R.string.number_of_gold_bars);

        setupRadioButtons();
    }

    private void setupRadioButtons() {
        final Mine field = Mine.getInstance();

        // Creating the radioGroups
        final RadioGroup dimensionGroup = (RadioGroup)findViewById(R.id.b_size_group);
        final String[] dimensions = getResources().getStringArray(R.array.board_sizes);

        final RadioGroup numGoldGroup = (RadioGroup)findViewById(R.id.ores_group);
        final int[] numGold = getResources().getIntArray(R.array.mine_num_options);

        // Dynamically creates radioButtons on the options screen
        for (int i = 0; i < dimensions.length; i++) {
            final String dimension = dimensions[i];

            final RadioButton button = new RadioButton(this);
            button.setTextColor(getResources().getColor(R.color.solid_white, getTheme()));
            button.setText(dimension);
            dimensionGroup.addView(button);

            // The item in the array in activity_options.xml for the dimensions
            // is stored as a string, rather than make a 2d array or two arrays for
            // columns and rows
            // This requires parsing the string for the columns and rows
            String[] strings = dimension.split(" X ");
            final int columns = Integer.parseInt(strings[0]);
            final int rows = Integer.parseInt(strings[1]);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // In the case that the selected settings means not all
                    // gold could be distributed (which would actually make an infinite loop)
                    // this prevents the settings from being changed, prevents the button
                    // from being selected, and maintains the selection of the button associated
                    // with the already saved or default values
                    if (field.getNumGold() > (columns * rows) ) {
                        Toast.makeText(OptionsActivity.this,
                                "Number of gold bars is greater than dimensions of mine",
                                Toast.LENGTH_SHORT).show();
                                button.setChecked(false);

                        // Re-selecting the previously selected button
                        // So that radiogroup still shows the value in the singleton
                        for (int j = 0; j < dimensions.length; j++) {
                            String tempDimension = dimensions[j];
                            String[] tempStrings = tempDimension.split(" X ");
                            int tempColumns = Integer.parseInt(tempStrings[0]);
                            int tempRows = Integer.parseInt(tempStrings[1]);

                            if (tempColumns == getSavedCol(OptionsActivity.this) &&
                                tempRows == getSavedRows(OptionsActivity.this)) {
                                RadioButton tempButton = (RadioButton)dimensionGroup.getChildAt(j);
                                dimensionGroup.clearCheck();
                                tempButton.setChecked(true);
                            }
                        }
                    }
                    // Check the button and resize the mine
                    else {
                        // Due to some internal problems with setChecked
                        // clearCheck must be called
                        dimensionGroup.clearCheck();
                        button.setChecked(true);
                        field.resize(columns, rows);
                        saveColumns(columns);
                        saveRows(rows);
                        dimensionGroup.requestLayout();
                    }
                }
            });

            // Checks the already saved settings
            if (columns == getSavedCol(this) && rows == getSavedRows(this)) {
                button.setChecked(true);
                dimensionGroup.requestLayout();
            }

        }

        // Making a button with the values of gold
        for (int i = 0; i < numGold.length; i++) {
            final int number = numGold[i];

            final RadioButton button = new RadioButton(this);
            button.setTextColor(getResources().getColor(R.color.solid_white, getTheme()));
            button.setText("" + number);

            numGoldGroup.addView(button);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // In the case that the selected settings means not all
                    // gold could be distributed (which would actually make an infinite loop)
                    // this prevents the settings from being changed, prevents the button
                    // from being selected, and maintains the selection of the button associated
                    // with the already saved or default values
                    if (number > (field.getColumns() * field.getRows()) ) {
                        Toast.makeText(OptionsActivity.this,
                                "Number of gold bars is greater than dimensions of mine",
                                Toast.LENGTH_SHORT).show();
                        button.setChecked(false);

                        // Re-selecting the previously selected button
                        // So that radiogroup still shows the value in the singleton
                        for (int j = 0; j < numGold.length; j++) {
                            if (numGold[j] == getSavedGold(OptionsActivity.this)) {
                                RadioButton tempButton = (RadioButton)numGoldGroup.getChildAt(j);
                                tempButton.setChecked(true);
                            }
                        }
                    }
                    // Change the number of gold in the field singleton
                    else {
                        // Due to an internal problem with setChecked
                        // clearCheck must be called here
                        numGoldGroup.clearCheck();
                        button.setChecked(true);
                        field.setNumGold(number);
                        saveNumGold(number);
                        numGoldGroup.requestLayout();
                        
                    }
                }
            });
            // Checks the already saved settings
            if (number == getSavedGold(this)) {
                button.setChecked(true);
                numGoldGroup.refreshDrawableState();
            }

        }




    }

    // Setters and getters for the persistent saved settings
    // Default values are a 6 X 4 mine with 6 gold bars present
    private void saveNumGold(int num) {
        SharedPreferences savedGold = this.getSharedPreferences("AppPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = savedGold.edit();
        editor.putInt("Num gold", num);
        editor.apply();
    }

    private void saveColumns(int x) {
        SharedPreferences savedCol = this.getSharedPreferences("AppPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = savedCol.edit();
        editor.putInt("Columns", x);
        editor.apply();
    }
    private void saveRows(int y) {
        SharedPreferences savedRows = this.getSharedPreferences("AppPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = savedRows.edit();
        editor.putInt("Rows", y);
        editor.apply();
    }
    static public int getSavedGold(Context context) {
        SharedPreferences savedGold = context.getSharedPreferences("AppPrefs",
                MODE_PRIVATE);
        return savedGold.getInt("Num gold", 6);

    }
    static public int getSavedCol(Context context){
        SharedPreferences savedCol= context.getSharedPreferences("AppPrefs",
                MODE_PRIVATE);
        return savedCol.getInt("Columns", 6);

    }
    static public int getSavedRows(Context context){
        SharedPreferences savedRows = context.getSharedPreferences("AppPrefs",
                MODE_PRIVATE);
        return savedRows.getInt("Rows", 4);

    }
}