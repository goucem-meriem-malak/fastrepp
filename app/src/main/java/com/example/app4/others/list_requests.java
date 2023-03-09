package com.example.app4.others;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app4.R;
import com.example.app4.homee;

public class list_requests extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_requests);

        final Button btn1 = (Button) findViewById(R.id.goback);
        final Button btn3 = (Button) findViewById(R.id.home);
        final Button btn4 = (Button) findViewById(R.id.menu);
        final Button btn5 = (Button) findViewById(R.id.profil);


        btn3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent activityChangeIntent = new Intent(list_requests.this, homee.class);

                list_requests.this.startActivity(activityChangeIntent);
            }
        });



    }
}