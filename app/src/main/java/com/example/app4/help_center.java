package com.example.app4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class help_center extends AppCompatActivity {
    private Button btngoback;
    private LinearLayout btnchat, btnhelpline, btnfaq, btnmessenger, btninstagram, btnwhatsapp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_center);

        btngoback = findViewById(R.id.goback);
        btnchat = findViewById(R.id.chat);
        btnhelpline = findViewById(R.id.faq);
        btnmessenger = findViewById(R.id.messenger);
        btninstagram = findViewById(R.id.instagram);
        btnwhatsapp = findViewById(R.id.whatsapp);

        btngoback.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        btnchat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(help_center.this, profile.class);
                help_center.this.startActivity(activityChangeIntent);
            }
        });
        btnhelpline.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(help_center.this, home.class);
                help_center.this.startActivity(activityChangeIntent);
            }
        });
        btnmessenger.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            }
        });
        btninstagram.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(help_center.this, list_requests.class);
                help_center.this.startActivity(activityChangeIntent);
            }
        });
        btnwhatsapp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(help_center.this, list_requests.class);
                help_center.this.startActivity(activityChangeIntent);
            }
        });
    }
}