package com.example.app4;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class find extends AppCompatActivity{
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
                Intent activityChangeIntent = new Intent(find.this, profile.class);
                find.this.startActivity(activityChangeIntent);
            }
        });
        btnrequestmechanic.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(find.this, mechanics_list.class);
                startActivity(activityChangeIntent);
            }
        });
        btnrequesttowtruck.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(find.this, find_towtruck.class);
                find.this.startActivity(activityChangeIntent);
            }
        });
        btnrequestoil.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(find.this, find_oil.class);
                find.this.startActivity(activityChangeIntent);
            }
        });
        btnrequestfuel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(find.this, find_fuel.class);
                find.this.startActivity(activityChangeIntent);
            }
        });
        btnrequestteam.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(find.this, find_team.class);
                find.this.startActivity(activityChangeIntent);
            }
        });
    }

}