package com.itgowo.gamestzb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.TextureView;
import android.widget.ImageView;

public class Main2Activity extends AppCompatActivity {
    private ImageView imageView;
    private TextureView textureView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        imageView = findViewById(R.id.img);
        textureView = findViewById(R.id.sv);


    }
}
