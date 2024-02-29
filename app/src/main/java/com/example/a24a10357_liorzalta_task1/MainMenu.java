package com.example.a24a10357_liorzalta_task1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;

public class MainMenu extends AppCompatActivity {

    private ShapeableImageView menu_IMG_background;
    private ShapeableImageView menu_IMG_Title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        findViews();
        Glide
             .with(this)
             .load(R.drawable.mario_background)
             .centerCrop()
             .into(menu_IMG_background);

        Glide.with(this).asGif().load(R.drawable.mario_gif_trans).into(menu_IMG_Title);
    }

    private void findViews() {
        menu_IMG_background = findViewById(R.id.menu_IMG_background);
        menu_IMG_Title = findViewById(R.id.menu_IMG_Title);
    }
}