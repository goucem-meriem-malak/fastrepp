package com.example.app4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;



public class profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        final Button btn1 = (Button) findViewById(R.id.goback);
        final Button btn2 = (Button) findViewById(R.id.helpcenter);
        final Button btn3 = (Button) findViewById(R.id.home);
        final Button btn4 = (Button) findViewById(R.id.menu);
        final Button btn5 = (Button) findViewById(R.id.profil);

        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent activityChangeIntent = new Intent(profile.this, homee.class);

                profile.this.startActivity(activityChangeIntent);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent activityChangeIntent = new Intent(profile.this, help_center.class);

                profile.this.startActivity(activityChangeIntent);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent activityChangeIntent = new Intent(profile.this, homee.class);

                profile.this.startActivity(activityChangeIntent);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent activityChangeIntent = new Intent(profile.this, menu.class);

                profile.this.startActivity(activityChangeIntent);
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent activityChangeIntent = new Intent(profile.this, profile.class);

                profile.this.startActivity(activityChangeIntent);
            }
        });

    }
}