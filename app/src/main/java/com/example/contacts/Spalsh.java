package com.example.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Spalsh extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_spalsh);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i =new Intent(Spalsh.this, MainActivity.class);
                startActivity(i);
            }
        },1000);
    }
}