package com.example.sukrut.detectionphase;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by root on 26/3/19.
 */

public class GlideDemo extends AppCompatActivity {

    ImageView imageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imagelayout);
        imageView=findViewById(R.id.imgview);
        Glide.with(this)
                .load("http://192.168.43.145/DemoSite/logo.png")
                .into(imageView);
    }
}
