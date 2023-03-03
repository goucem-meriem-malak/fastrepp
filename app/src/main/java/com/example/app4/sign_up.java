package com.example.app4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class sign_up extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        final Button btn1 = (Button) findViewById(R.id.signup);
        final Button btn2 = (Button) findViewById(R.id.signin);

        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent activityChangeIntent = new Intent(sign_up.this, homee.class);

                sign_up.this.startActivity(activityChangeIntent);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent activityChangeIntent = new Intent(sign_up.this, sign_in.class);

                sign_up.this.startActivity(activityChangeIntent);
            }
        });
    }
}