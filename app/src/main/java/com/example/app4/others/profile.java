package com.example.app4.others;

import android.content.Intent;
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
    private EditText id,address,phone,name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        final Button btn1 = (Button) findViewById(R.id.goback);
        final Button btn2 = (Button) findViewById(R.id.helpcenter);
        final Button btn3 = (Button) findViewById(R.id.home);
        final Button btn4 = (Button) findViewById(R.id.menu);
        final Button btn5 = (Button) findViewById(R.id.profil);
        client client = new client();
        id.setText(client.getId());
        name.setText(client.getFirstname());
        phone.setText(client.getPhone());
        address.setText(client.getAddress());

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

        btn5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent activityChangeIntent = new Intent(profile.this, profile.class);

                profile.this.startActivity(activityChangeIntent);
            }
        });

    }
}