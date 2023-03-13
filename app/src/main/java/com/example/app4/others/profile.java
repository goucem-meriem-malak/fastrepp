package com.example.app4.others;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app4.R;
import com.example.app4.data.client;
import com.example.app4.homee;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class profile extends AppCompatActivity {
    private EditText firstname, lastname, address, phone;
    private Button btnmenu, btnprofile, btnhelpcenter, btnedit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        btnhelpcenter = findViewById(R.id.helpcenter);
        btnmenu = findViewById(R.id.home);
        btnprofile = findViewById(R.id.profil);
        btnedit = findViewById(R.id.edit);

        firstname=findViewById(R.id.firstname);
        lastname=findViewById(R.id.lastname);
        phone=findViewById(R.id.phone);
        address=findViewById(R.id.address);

        Intent intent = getIntent();
        firstname.setText(intent.getStringExtra("cfirstname"));
        lastname.setText(intent.getStringExtra("clastname"));
        phone.setText(intent.getStringExtra("cphone"));
        address.setText(intent.getStringExtra("caddress"));

        btnmenu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(profile.this, home.class);

                profile.this.startActivity(activityChangeIntent);
            }
        });
        btnhelpcenter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(profile.this, help_center.class);

                profile.this.startActivity(activityChangeIntent);
            }
        });
        btnprofile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(profile.this, homee.class);

                profile.this.startActivity(activityChangeIntent);
            }
        });
    }
}