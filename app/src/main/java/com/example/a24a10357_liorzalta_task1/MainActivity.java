package com.example.a24a10357_liorzalta_task1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.a24a10357_liorzalta_task1.Logic.GameManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private static final long DELAY = 1000;
    private final int ROWS = 6;
    private final int COLS = 3;
    private final int MAX_LIVES = 3;
    private final int INTERVAL = 3;     // interval between obstacles creation
    private ShapeableImageView[] lives;
    private ShapeableImageView[][] cells;   // game cells consist of player area (first line) and obstacles area
    private MaterialButton main_BTN_right;
    private MaterialButton main_BTN_left;
    private GameManager gameManager;    // handles all the games logic
    private Timer timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameManager = new GameManager(MAX_LIVES, ROWS, COLS, INTERVAL);

        findViews();
        initViews();
        startGame();
    }

    private void findViews() {
        main_BTN_left = findViewById(R.id.main_BTN_left);
        main_BTN_right = findViewById(R.id.main_BTN_right);
        lives = new ShapeableImageView[]{
                findViewById(R.id.main_IMG_heart1),
                findViewById(R.id.main_IMG_heart2),
                findViewById(R.id.main_IMG_heart3)
        };

        cells = new ShapeableImageView[][]{
                {
                        findViewById(R.id.main_IMG_cell00),
                        findViewById(R.id.main_IMG_cell01),
                        findViewById(R.id.main_IMG_cell02),
                },
                {
                        findViewById(R.id.main_IMG_cell10),
                        findViewById(R.id.main_IMG_cell11),
                        findViewById(R.id.main_IMG_cell12),
                },
                {
                        findViewById(R.id.main_IMG_cell20),
                        findViewById(R.id.main_IMG_cell21),
                        findViewById(R.id.main_IMG_cell22),
                },
                {
                        findViewById(R.id.main_IMG_cell30),
                        findViewById(R.id.main_IMG_cell31),
                        findViewById(R.id.main_IMG_cell32),
                },
                {
                        findViewById(R.id.main_IMG_cell40),
                        findViewById(R.id.main_IMG_cell41),
                        findViewById(R.id.main_IMG_cell42),
                },
                {
                        findViewById(R.id.main_IMG_cell50),
                        findViewById(R.id.main_IMG_cell51),
                        findViewById(R.id.main_IMG_cell52),
                },
        };
    }

    private void initViews() {
        main_BTN_left.setOnClickListener(v -> {
            gameManager.movePlayerLeft();
            refreshPlayerArea();
        });
        main_BTN_right.setOnClickListener(v -> {
            gameManager.movePlayerRight();
            refreshPlayerArea();
        });

        for (int i = 0; i < MAX_LIVES; i++) {
            Glide.with(this)
                    .load(R.drawable.mario_life)
                    .into(lives[i]);
        }

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (i < ROWS - 1)
                    Glide.with(this)
                            .load(R.drawable.bowser)
                            .into(cells[i][j]);
                else
                    Glide.with(this)
                            .load(R.drawable.mario)
                            .into(cells[i][j]);
            }
        }
    }

    private void startGame() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> nextCycle());
            }
        }, 0, DELAY);
    }

    // update the game logic after each clock cycle
    private void nextCycle() {
        boolean isHit = gameManager.moveObstacles();

        if (isHit) {
            vibrate();
        }

        if (gameManager.isLost()) {
            toast("GAME OVER");
            resetGame();
        }

        //separate refresh actions - motivation is the times that only player moves
        refreshPlayerArea();
        refreshObstaclesArea();
    }

    // refresh player area (first line)
    private void refreshPlayerArea() {
        int[] playerCords = gameManager.getEntitiesCords()[0];  // player coordinates always the first in array

        for (int i = 0; i < COLS; i++) {
            cells[ROWS - 1][i].setVisibility(View.INVISIBLE);   // make player area invisible
        }
        cells[playerCords[0]][playerCords[1]].setVisibility(View.VISIBLE); // make player current cell visible
    }

    // refresh obstacles area
    private void refreshObstaclesArea() {
        int[][] layoutState = gameManager.getEntitiesCords();   // coordinates of all existing obstacles

        for (int i = 1; i < layoutState.length; i++) {
            int row = layoutState[i][0];
            int col = layoutState[i][1];
            Log.d("cords in refresh", row + ", " + col);

            //make obstacle previous location invisible
            if (row > 0){
                cells[row - 1][col].setVisibility(View.INVISIBLE);
            }

            //won't display obstacles in player area (first cell)
            if (row < ROWS - 1){
                cells[row][col].setVisibility(View.VISIBLE);
            }
        }
        if (gameManager.getHits() > 0)
            lives[MAX_LIVES - gameManager.getHits()].setVisibility(View.INVISIBLE);
    }


    private void resetGame() {
        gameManager.resetGame();    // reset game logic

        // reset game view
        for (int i = 0; i < MAX_LIVES; i++) {
            lives[i].setVisibility(View.VISIBLE);
        }
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                cells[i][j].setVisibility(View.INVISIBLE);
            }
        }
    }


    private void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    private void vibrate() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
    }


}