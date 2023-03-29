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
    private Button btnlistrequests, btngoback, btnprofile, btnrequestmechanic, btnrequesttowtruck, btnrequestoil,
    btnrequestfuel, btnrequestteam, btnrequestgarage, btnhome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find);

        btnprofile = findViewById(R.id.profil);
        btnhome = findViewById(R.id.home);
        btnlistrequests = findViewById(R.id.list_requests);
        btnrequestmechanic = findViewById(R.id.request_mechanic);
        btnrequesttowtruck = findViewById(R.id.request_towtruck);
        btnrequestoil = findViewById(R.id.request_oil);
        btnrequestfuel = findViewById(R.id.request_fuel);
        btnrequestteam = findViewById(R.id.request_team);
        btnrequestgarage = findViewById(R.id.request_garage);
        btngoback = findViewById(R.id.goback);


        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        uid = user.getUid();
        btnhome.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(find.this, home.class);
                find.this.startActivity(activityChangeIntent);
            }
        });
        btnlistrequests.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(find.this, list_requests.class);
                find.this.startActivity(activityChangeIntent);
            }
        });
        btnprofile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(find.this, profile.class);
                find.this.startActivity(activityChangeIntent);
            }
        });
        btngoback.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        btnrequestmechanic.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(find.this, form_vehicule.class);
                startActivity(activityChangeIntent);
            }
        });
        btnrequesttowtruck.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Intent activityChangeIntent = new Intent(find.this, li.class);
                //find.this.startActivity(activityChangeIntent);
            }
        });
        btnrequestoil.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(find.this, list_stations.class);
                find.this.startActivity(activityChangeIntent);
            }
        });
        btnrequestfuel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(find.this, list_stations.class);
                find.this.startActivity(activityChangeIntent);
            }
        });
        btnrequestteam.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(find.this, list_garage.class);
                find.this.startActivity(activityChangeIntent);
            }
        });
        btnrequestgarage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(find.this, list_garage.class);
                find.this.startActivity(activityChangeIntent);
            }
        });
    }

}