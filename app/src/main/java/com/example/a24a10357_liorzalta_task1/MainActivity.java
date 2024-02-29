package com.example.a24a10357_liorzalta_task1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.a24a10357_liorzalta_task1.Logic.GameManager;
import com.example.a24a10357_liorzalta_task1.Model.EntityType;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private static final long DELAY = 1000;
    private final int ROWS = 7;
    private final int COLS = 5;
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
                        findViewById(R.id.main_IMG_cell03),
                        findViewById(R.id.main_IMG_cell04),
                },
                {
                        findViewById(R.id.main_IMG_cell10),
                        findViewById(R.id.main_IMG_cell11),
                        findViewById(R.id.main_IMG_cell12),
                        findViewById(R.id.main_IMG_cell13),
                        findViewById(R.id.main_IMG_cell14),
                },
                {
                        findViewById(R.id.main_IMG_cell20),
                        findViewById(R.id.main_IMG_cell21),
                        findViewById(R.id.main_IMG_cell22),
                        findViewById(R.id.main_IMG_cell23),
                        findViewById(R.id.main_IMG_cell24),
                },
                {
                        findViewById(R.id.main_IMG_cell30),
                        findViewById(R.id.main_IMG_cell31),
                        findViewById(R.id.main_IMG_cell32),
                        findViewById(R.id.main_IMG_cell33),
                        findViewById(R.id.main_IMG_cell34),
                },
                {
                        findViewById(R.id.main_IMG_cell40),
                        findViewById(R.id.main_IMG_cell41),
                        findViewById(R.id.main_IMG_cell42),
                        findViewById(R.id.main_IMG_cell43),
                        findViewById(R.id.main_IMG_cell44),
                },
                {
                        findViewById(R.id.main_IMG_cell50),
                        findViewById(R.id.main_IMG_cell51),
                        findViewById(R.id.main_IMG_cell52),
                        findViewById(R.id.main_IMG_cell53),
                        findViewById(R.id.main_IMG_cell54),
                },
                {
                        findViewById(R.id.main_IMG_cell60),
                        findViewById(R.id.main_IMG_cell61),
                        findViewById(R.id.main_IMG_cell62),
                        findViewById(R.id.main_IMG_cell63),
                        findViewById(R.id.main_IMG_cell64),
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

        for (int i = 0; i < COLS; i++) {
            Glide.with(this)
                    .load(R.drawable.mario)
                    .into(cells[ROWS-1][i]);
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
        int isHit = gameManager.moveEntities();

        if (isHit == 1) {
            vibrate();
            // make hits obstacle sound
        }

        if (isHit == 2) {
            vibrate();
            // make hits reward sound
        }

        if (isHit == 3) {
            vibrate();
            // make hits life sound
        }

        if (gameManager.isLost()) {
            toast("GAME OVER");
            resetGame();
            return;
        }

        //separate refresh actions - motivation is the times that only player moves
        refreshPlayerArea();
        refreshObstaclesArea();
        refreshHearts();
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
        EntityType[] entitiesType = gameManager.getEntitiesType();

        for (int i = 1; i < layoutState.length; i++) {
            int row = layoutState[i][0];
            int col = layoutState[i][1];
            EntityType entityType = entitiesType[i];

            //make obstacle previous location invisible
            if (row > 0){
                cells[row - 1][col].setVisibility(View.INVISIBLE);
            }

            //won't display obstacles in player area (first cell)
            if (row < ROWS - 1){
                cells[row][col].setVisibility(View.VISIBLE);

                if (entityType == EntityType.OBSTACLE){
                    Glide.with(this)
                            .load(R.drawable.bowser)
                            .into(cells[row][col]);
                }
                else if (entityType == EntityType.REWARD){
                    Glide.with(this)
                            .load(R.drawable.coin)
                            .into(cells[row][col]);
                }
                else{
                    Glide.with(this)
                            .load(R.drawable.mushroom)
                            .into(cells[row][col]);
                }
            }
        }
    }

    // refresh hearts based on last hitting status
    // 0-not hit, 1-hit obstacle,  2-hit reward, 3-hit life
    public void refreshHearts(){
        if (gameManager.getHits() == 0){
            lives[0].setVisibility(View.VISIBLE);
            lives[1].setVisibility(View.VISIBLE);
        }

        else if (gameManager.getHits() == 1){
            lives[0].setVisibility(View.INVISIBLE);
            lives[1].setVisibility(View.VISIBLE);
        }

        else if (gameManager.getHits() == 2){
            lives[0].setVisibility(View.INVISIBLE);
            lives[1].setVisibility(View.INVISIBLE);
        }
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