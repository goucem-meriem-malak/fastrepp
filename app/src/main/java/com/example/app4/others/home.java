package com.example.app4.others;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app4.R;
import com.example.app4.mechanic_services.find_mechanic;
import com.example.app4.oil_fuel_services.find_fuel;
import com.example.app4.oil_fuel_services.find_oil;
import com.example.app4.team_services.find_team;
import com.example.app4.tow_services.find_towtruck;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class home extends AppCompatActivity{
    private FirebaseUser user;
    private FirebaseAuth auth;
    private String uid;
    private TextView tv;
    private Button btnmenu, btnlistrequest, btnprofile, btnrequestmechanic, btnrequesttowtruck, btnrequestoil,
    btnrequestfuel, btnrequestteam, btnrequestgarage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        btnmenu = findViewById(R.id.home);
        btnlistrequest = findViewById(R.id.menu);
        btnprofile = findViewById(R.id.profil);
        btnrequestmechanic = findViewById(R.id.request_mechanic);
        btnrequesttowtruck = findViewById(R.id.request_towtruck);
        btnrequestoil = findViewById(R.id.request_oil);
        btnrequestfuel = findViewById(R.id.request_fuel);
        btnrequestteam = findViewById(R.id.request_team);
        btnrequestgarage = findViewById(R.id.request_garage);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        uid = user.getUid();

        btnprofile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(home.this, profile.class);
                home.this.startActivity(activityChangeIntent);
            }
        });
        btnrequestmechanic.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(home.this, find_mechanic.class);
                startActivity(activityChangeIntent);
            }
        });
        btnrequesttowtruck.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(home.this, find_towtruck.class);
                home.this.startActivity(activityChangeIntent);
            }
        });
        btnrequestoil.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(home.this, find_oil.class);
                home.this.startActivity(activityChangeIntent);
            }
        });
        btnrequestfuel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(home.this, find_fuel.class);
                home.this.startActivity(activityChangeIntent);
            }
        });
        btnrequestteam.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(home.this, find_team.class);
                home.this.startActivity(activityChangeIntent);
            }
        });
    }

}