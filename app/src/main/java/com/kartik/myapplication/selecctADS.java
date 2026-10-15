package com.kartik.myapplication;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.widget.ImageView;

public class selecctADS extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_selecct_ads);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ImageView imageView = findViewById(R.id.imageView4);
        ImageView img1 = findViewById(R.id.clickoneimg);
        ImageView img2 = findViewById(R.id.imageView5);
        ImageView img3 = findViewById(R.id.imageView6);
        imageView.setOnClickListener(v -> {
            Intent intent = new Intent(selecctADS.this, Home.class);
            startActivity(intent);
        });

        img2.setOnClickListener(v -> {
            Intent intent = new Intent(selecctADS.this, Nativeads.class);
            startActivity(intent);
        });
        img3.setOnClickListener(v -> {
            Intent intent = new Intent(selecctADS.this, Rewardviewads.class);
            startActivity(intent);
        });
    }
}