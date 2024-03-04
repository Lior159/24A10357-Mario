package com.example.a24a10357_liorzalta_task1.Utilities;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.a24a10357_liorzalta_task1.R;

public class ImageLoader {
    private Context context;

    public ImageLoader(Context context) {
        this.context = context;
    }

    public void loadImg (int imgId, ImageView imageView){
        Glide.
                with(context)
                .load(imgId)
                .placeholder(R.drawable.ic_launcher_background)
                .into(imageView);
    }

    public void loadGif (int gifId, ImageView imageView){
        Glide.
                with(context)
                .asGif()
                .load(gifId)
                .into(imageView);
    }
}
