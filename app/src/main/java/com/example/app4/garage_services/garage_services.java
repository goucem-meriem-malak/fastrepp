package com.example.app4.garage_services;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app4.R;
import com.example.app4.homee;
import com.example.app4.others.list_requests;
import com.example.app4.others.profile;


public class garage_services extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.garage_services);

        final Button btn1 = (Button) findViewById(R.id.goback);
        final Button btn2 = (Button) findViewById(R.id.listrequest);
        final Button btn3 = (Button) findViewById(R.id.home);
        final Button btn4 = (Button) findViewById(R.id.menu);
        final Button btn5 = (Button) findViewById(R.id.profil);

        btn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent activityChangeIntent = new Intent(garage_services.this, list_requests.class);

                garage_services.this.startActivity(activityChangeIntent);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent activityChangeIntent = new Intent(garage_services.this, homee.class);

                garage_services.this.startActivity(activityChangeIntent);
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent activityChangeIntent = new Intent(garage_services.this, profile.class);

                garage_services.this.startActivity(activityChangeIntent);
            }
        });
    }

}