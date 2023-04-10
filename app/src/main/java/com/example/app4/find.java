package com.example.app4;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class find extends AppCompatActivity{
    private FirebaseUser user;
    private FirebaseAuth auth;
    private String uid;
    private TextView tv;
    private Button btnhome, btnlistrequests, btngoback, btnprofile, btnrequestmechanic, btnrequesttowtruck, btnrequeststation,
    btnrequestteam, btnrequestgarage;
    private LinearLayout tow, veh;
    private CheckBox fuel, oil, taxi, ambulance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find);

        btnprofile = findViewById(R.id.profil);
        btnhome = findViewById(R.id.home);
        btnlistrequests = findViewById(R.id.list_requests);
        btngoback = findViewById(R.id.goback);

        btnrequestmechanic = findViewById(R.id.request_mechanic);
        btnrequesttowtruck = findViewById(R.id.request_towtruck);
        btnrequeststation = findViewById(R.id.request_station);
        btnrequestteam = findViewById(R.id.request_team);
        btnrequestgarage = findViewById(R.id.request_garage);

        tow = findViewById(R.id.tow);
        veh = findViewById(R.id.veh);

        fuel = findViewById(R.id.fuel);
        oil = findViewById(R.id.oil);
        taxi = findViewById(R.id.taxi);
        ambulance = findViewById(R.id.ambulance);

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
                activityChangeIntent.putExtra("service","mechanic");
                startActivity(activityChangeIntent);
            }
        });
        btnrequesttowtruck.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (taxi.isChecked()&& ambulance.isChecked()){
                    Intent activityChangeIntent = new Intent(find.this, form_vehicule.class);
                    activityChangeIntent.putExtra("service","both");
                    find.this.startActivity(activityChangeIntent);
                }
                else if (ambulance.isChecked()){
                    Intent activityChangeIntent = new Intent(find.this, form_vehicule.class);
                    activityChangeIntent.putExtra("service","ambulance");
                    find.this.startActivity(activityChangeIntent);
                } else if (taxi.isChecked()) {
                    Intent activityChangeIntent = new Intent(find.this, form_vehicule.class);
                    activityChangeIntent.putExtra("service","taxi");
                    find.this.startActivity(activityChangeIntent);
                } else {
                    Intent activityChangeIntent = new Intent(find.this, form_vehicule.class);
                    activityChangeIntent.putExtra("service","none");
                    find.this.startActivity(activityChangeIntent);
                }
            }
        });
        btnrequeststation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (oil.isChecked() && fuel.isChecked()){
                    Intent activityChangeIntent = new Intent(find.this, form_oil_fuel.class);
                    activityChangeIntent.putExtra("service","both");
                    find.this.startActivity(activityChangeIntent);
                } else if(oil.isChecked()){
                    Intent activityChangeIntent = new Intent(find.this, form_oil_fuel.class);
                    activityChangeIntent.putExtra("service","oil");
                    find.this.startActivity(activityChangeIntent);
                }else if(fuel.isChecked()){
                    Intent activityChangeIntent = new Intent(find.this, form_oil_fuel.class);
                    activityChangeIntent.putExtra("service","fuel");
                    find.this.startActivity(activityChangeIntent);
                } else {
                    Toast.makeText(find.this, "Choose A Service! ", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnrequestteam.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(find.this, form_team.class);
                find.this.startActivity(activityChangeIntent);
            }
        });
        btnrequestgarage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(find.this, list_garage.class);
                find.this.startActivity(activityChangeIntent);
            }
        });
        tow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                veh.setVisibility(View.VISIBLE);
            }
        });
    }

}