package com.example.app4.others;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app4.R;
import com.example.app4.mechanic_services.find_mechanic;
import com.example.app4.oil_fuel_services.find_fuel;
import com.example.app4.oil_fuel_services.find_oil;
import com.example.app4.team_services.find_team;
import com.example.app4.tow_services.find_towtruck;


public class home extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        final Button btn1 = (Button) findViewById(R.id.home);
        final Button btn2 = (Button) findViewById(R.id.menu);
        final Button btn3 = (Button) findViewById(R.id.profil);
        final Button btn4 = (Button) findViewById(R.id.request_mechanic);
        final Button btn5 = (Button) findViewById(R.id.request_towtruck);
        final Button btn6 = (Button) findViewById(R.id.request_oil);
        final Button btn7 = (Button) findViewById(R.id.request_fuel);
        final Button btn8 = (Button) findViewById(R.id.request_team);
        final Button btn9 = (Button) findViewById(R.id.request_garage);

        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent activityChangeIntent = new Intent(home.this, home.class);

                home.this.startActivity(activityChangeIntent);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent activityChangeIntent = new Intent(home.this, profile.class);

                home.this.startActivity(activityChangeIntent);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent activityChangeIntent = new Intent(home.this, find_mechanic.class);

                home.this.startActivity(activityChangeIntent);
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent activityChangeIntent = new Intent(home.this, find_towtruck.class);

                home.this.startActivity(activityChangeIntent);
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent activityChangeIntent = new Intent(home.this, find_oil.class);

                home.this.startActivity(activityChangeIntent);
            }
        });
        btn7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent activityChangeIntent = new Intent(home.this, find_fuel.class);

                home.this.startActivity(activityChangeIntent);
            }
        });
        btn8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent activityChangeIntent = new Intent(home.this, find_team.class);

                home.this.startActivity(activityChangeIntent);
            }
        });
   /*     btn9.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent activityChangeIntent = new Intent(home.this, find_garage.class);

                home.this.startActivity(activityChangeIntent);
            }
        });
*/
    }

}