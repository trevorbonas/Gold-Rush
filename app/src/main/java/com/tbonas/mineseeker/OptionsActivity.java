package com.tbonas.mineseeker;

import android.os.Bundle;

import com.tbonas.mineseeker.model.Mine;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class OptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        setupRadioButtons();
    }

    private void setupRadioButtons() {
        final Mine field = Mine.getInstance();
        RadioGroup dimensionGroup = (RadioGroup)findViewById(R.id.b_size_group);
        String[] dimensions = getResources().getStringArray(R.array.board_sizes);

        final RadioGroup numMinesGroup = (RadioGroup)findViewById(R.id.ores_group);
        int[] numMines = getResources().getIntArray(R.array.mine_num_options);

        for (int i = 0; i < dimensions.length; i++) {
            final String dimension = dimensions[i];

            RadioButton button = new RadioButton(this);
            button.setText(dimension);
            dimensionGroup.addView(button);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String[] strings = dimension.split(" X ");
                    int columns = Integer.parseInt(strings[0]);
                    int rows = Integer.parseInt(strings[1]);

                    field.resize(columns, rows);
                }
            });

        }

        for (int i = 0; i < numMines.length; i++) {
            final int number = numMines[i];

            final RadioButton button = new RadioButton(this);
            button.setText("" + number);

            numMinesGroup.addView(button);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (number > (field.getColumns() * field.getRows()) ) {
                        Toast.makeText(OptionsActivity.this,
                                "Number of ores greater than dimensions of mine",
                                Toast.LENGTH_LONG).show();
                    }
                    else {
                        field.setNumMines(number);
                    }
                }
            });

        }




    }
}