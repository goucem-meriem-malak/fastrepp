package com.example.app4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class sign_in extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        final Button btn1 = (Button) findViewById(R.id.signin);
        final Button btn2 = (Button) findViewById(R.id.signup);

        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent activityChangeIntent = new Intent(sign_in.this, home.class);

                sign_in.this.startActivity(activityChangeIntent);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent activityChangeIntent = new Intent(sign_in.this, sign_up.class);

                sign_in.this.startActivity(activityChangeIntent);
            }
        });
    }
}