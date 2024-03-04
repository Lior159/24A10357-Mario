package com.example.a24a10357_liorzalta_task1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;

import com.example.a24a10357_liorzalta_task1.Utilities.ImageLoader;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;

public class MenuActivity extends AppCompatActivity {

    ImageLoader imgLoader = new ImageLoader(this);
    private ShapeableImageView menu_IMG_background;
    private ShapeableImageView menu_IMG_Title;
    private MaterialButton menu_BTN_buttons_mode;
    private MaterialButton menu_BTN_tilt_mode;
    private MaterialButton menu_BTN_scores;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        findViews();
        initViews();
    }

    private void findViews() {
        menu_IMG_background = findViewById(R.id.menu_IMG_background);
        menu_IMG_Title = findViewById(R.id.menu_IMG_Title);
        menu_BTN_buttons_mode = findViewById(R.id.menu_BTN_buttons_mode);
        menu_BTN_tilt_mode = findViewById(R.id.menu_BTN_tilt_mode);
        menu_BTN_scores = findViewById(R.id.menu_BTN_scores);
    }

    private void initViews() {
        imgLoader.loadGif(R.raw.mario_gif_trans2, menu_IMG_Title);
        imgLoader.loadImg(R.drawable.mario_background, menu_IMG_background);

        menu_BTN_buttons_mode.setOnClickListener(v -> redirectToGame("buttons"));
        menu_BTN_tilt_mode.setOnClickListener(v -> redirectToGame("tilt"));
        menu_BTN_scores.setOnClickListener(v -> redirectToTopRecords());
    }

    private void redirectToGame(String mode) {
        Intent gameIntent = new Intent(this, GameActivity.class);
        gameIntent.putExtra(GameActivity.KEY_GAME_MODE, mode);
        startActivity(gameIntent);
        finish();
    }

    private void redirectToTopRecords() {
        Intent topRecordsIntent = new Intent(this, TopRecordsActivity.class);
        startActivity(topRecordsIntent);
        finish();
    }

}