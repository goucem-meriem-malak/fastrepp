package com.example.app4.others;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app4.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;

public class launch_screen extends AppCompatActivity{
    private FirebaseFirestore db;
    private Button btnsignup, btnsignin, btnguest;
    private String clientid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launch_screen);

        btnsignup = findViewById(R.id.signup);
        btnsignin = findViewById(R.id.signin);
        btnguest = findViewById(R.id.continuee);

        db = FirebaseFirestore.getInstance();


        btnsignup.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(launch_screen.this, sign_up.class);
                activityChangeIntent.putExtra("clientid", createid());
                startActivity(activityChangeIntent);
            }
        });
        btnsignin.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(launch_screen.this, sign_in.class);
                activityChangeIntent.putExtra("clientid", createid());
                startActivity(activityChangeIntent);
            }
        });
        btnguest.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(launch_screen.this, home.class);
                activityChangeIntent.putExtra("clientid", createid());
                startActivity(activityChangeIntent);
            }
        });
    }

    private String createid() {
        DocumentReference ref = db.collection("client").document();
        HashMap<String, Object> m =new HashMap<String, Object>();
        clientid = ref.getId();
        m.put("id", clientid);
        m.put("signed", false);
        ref.set(m);

        return clientid;
    }

}