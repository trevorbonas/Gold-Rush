package com.tbonas.mineseeker;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import com.tbonas.mineseeker.model.Mine;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {
    int foundOre;
    int scansUsed;
    Mine mine;
    Button buttons[][];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        foundOre = 0;
        scansUsed = 0;

        mine = Mine.getInstance();
        mine.clear();
        mine.putGold();

        buttons = new Button[mine.getColumns()][mine.getRows()];

        // Puts squares in table (the mine the player interacts with)
        updateScores();
        populateTable();
    }

    private void updateScores() {
        TextView goldScore = (TextView)findViewById(R.id.showFound);
        goldScore.setText("" + foundOre + " gold found of " + mine.getNumGold());

        TextView scanScore = (TextView)findViewById(R.id.showScans);
        scanScore.setText("" + scansUsed + " scans used");
    }

    private void populateTable() {
        TableLayout table = (TableLayout)findViewById(R.id.mineTable);
        for (int row = 0; row < mine.getRows(); row++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f));
            table.addView(tableRow);
            for (int column = 0; column < mine.getColumns(); column++) {
                final int finalColumn = column;
                final int finalRow = row;
                final Button button = new Button(this);
                button.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f));
                button.setPadding(0, 0, 0, 0);
                buttons[column][row] = button;

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gridButtonClicked(finalColumn, finalRow);
                    }
                });

                tableRow.addView(button);
                /*
                // From Dr. Brian Fraser's video
                int newWidth = button.getWidth();
                int newHeight = button.getHeight();
                Bitmap orignalBitmap = BitmapFactory.decodeResource(getResources(),
                        R.drawable.rock);
                Bitmap scaledBitmap = Bitmap.createScaledBitmap(orignalBitmap, newWidth,
                        newHeight, true);
                Resources resource = getResources();
                button.setBackground(new BitmapDrawable(resource, scaledBitmap)); */
            }
        }
    }

    private void gridButtonClicked(int x, int y) {
        Button button = buttons[x][y];
        if (!mine.goldFound(x, y)) {
            button.setText("" + mine.getNearby(x, y));
            mine.setSquareClicked(true, x, y);
            scansUsed++;
        }
        else if (mine.goldFound(x, y) && mine.squareClicked(x, y)) {
            button.setText("" + mine.getNearby(x, y));
            mine.setSquareGoldClicked(true, x, y);
            scansUsed++;
        }
        else if (mine.goldFound(x, y) && !(mine.squareClicked(x, y))){
            mine.setSquareClicked(true, x, y);
            foundOre++;
            if (foundOre >= mine.getNumGold()) {
                // End game
            }

            // From Dr. Brian Fraser's video
            int newWidth = button.getWidth();
            int newHeight = button.getHeight();
            Bitmap orignalBitmap = BitmapFactory.decodeResource(getResources(),
                    R.drawable.gold_bar);
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(orignalBitmap, newWidth,
                    newHeight, true);
            Resources resource = getResources();
            button.setBackground(new BitmapDrawable(resource, scaledBitmap));

            mine.updateMinesNearby(x, y);
            showNewNearby(x, y);
        }
        updateScores();
    }

    private void showNewNearby(int x, int y) {
        Button button;
        for (int row = 0; row < mine.getRows(); row++) {
            if (row != y) {
                if ( (mine.squareClicked(x, row) && !mine.goldFound(x, row) )
                    || mine.squareGoldClicked(x, row)) {
                    button = buttons[x][row];
                    button.setText("" + mine.getNearby(x, row));
                }
            }
        }
        for (int column = 0; column < mine.getColumns(); column++) {
            if (column != x) {
                if ( (mine.squareClicked(column, y) && !mine.goldFound(column, y) )
                        || mine.squareGoldClicked(column, y)) {
                    button = buttons[column][y];
                    button.setText("" + mine.getNearby(column, y));
                }
            }
        }
    }
}