package com.example.app4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class launch_screen extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launch_screen);

        final Button btn1 = (Button) findViewById(R.id.signup);
        final Button btn2 = (Button) findViewById(R.id.signin);
        final Button btn3 = (Button) findViewById(R.id.continuee);

        btn1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent activityChangeIntent = new Intent(launch_screen.this, sign_up.class);

                launch_screen.this.startActivity(activityChangeIntent);
            }
        });
        btn2.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent activityChangeIntent = new Intent(launch_screen.this, sign_in.class);

                launch_screen.this.startActivity(activityChangeIntent);
            }
        });
        btn3.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent activityChangeIntent = new Intent(launch_screen.this, home.class);

                launch_screen.this.startActivity(activityChangeIntent);
            }
        });
    }

}