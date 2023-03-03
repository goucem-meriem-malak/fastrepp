package com.example.app4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class outside_services extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.outside_services);

        final Button btn1 = (Button) findViewById(R.id.goback);
        final Button btn2 = (Button) findViewById(R.id.listrequest);
        final Button btn3 = (Button) findViewById(R.id.home);
        final Button btn4 = (Button) findViewById(R.id.menu);
        final Button btn5 = (Button) findViewById(R.id.profil);

        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent activityChangeIntent = new Intent(outside_services.this, menu.class);

                outside_services.this.startActivity(activityChangeIntent);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent activityChangeIntent = new Intent(outside_services.this, list_requests.class);

                outside_services.this.startActivity(activityChangeIntent);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent activityChangeIntent = new Intent(outside_services.this, homee.class);

                outside_services.this.startActivity(activityChangeIntent);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent activityChangeIntent = new Intent(outside_services.this, menu.class);

                outside_services.this.startActivity(activityChangeIntent);
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent activityChangeIntent = new Intent(outside_services.this, profile.class);

                outside_services.this.startActivity(activityChangeIntent);
            }
        });
    }

}