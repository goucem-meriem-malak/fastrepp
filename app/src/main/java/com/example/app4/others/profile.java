package com.example.app4.others;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app4.R;
import com.example.app4.data.client;
import com.example.app4.homee;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import java.util.HashMap;
import java.util.Map;


public class profile extends AppCompatActivity {
    private FirebaseFirestore db;
    private FirebaseUser user;
    private FirebaseAuth auth;
    private EditText firstname, lastname, email, country, city, state, phone;
    private Button btnhome, btnlistrequests, btnprofile, btnedit, btnhelpcenter;
    private String clientid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        btnhelpcenter = findViewById(R.id.helpcenter);
        btnhome = findViewById(R.id.home);
        btnprofile = findViewById(R.id.profil);
        btnlistrequests = findViewById(R.id.list_requests);
        btnedit = findViewById(R.id.edit);

        firstname=findViewById(R.id.firstname);
        lastname=findViewById(R.id.lastname);
        phone=findViewById(R.id.phone);
        email = findViewById(R.id.email);
        country = findViewById(R.id.country);
        city = findViewById(R.id.city);
        state = findViewById(R.id.state);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        clientid = user.getUid();

        db.collection("client").document(clientid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshott) {
                client client = documentSnapshott.toObject(client.class);
                firstname.setText(client.getFirstname());
                lastname.setText(client.getLastname());
                phone.setText(client.getPhone());
                email.setText(client.getEmail());}});

        btnhome.setOnClickListener(new View.OnClickListener() {
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
        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnedit.getText().equals("Edit")){
                    firstname.setEnabled(true);
                    lastname.setEnabled(true);
                    country.setEnabled(true);
                    city.setEnabled(true);
                    state.setEnabled(true);
                    email.setEnabled(true);
                    btnedit.setText("Save");
                }
                else{
                    btnedit.setText("Edit");
                    firstname.setEnabled(false);
                    lastname.setEnabled(false);
                    country.setEnabled(false);
                    city.setEnabled(false);
                    state.setEnabled(false);
                    email.setEnabled(false);
                    Map<String, Object> update = new HashMap<>();
                    update.put("firstname", firstname.getText().toString().trim());
                    update.put("lastnsme", lastname.getText().toString().trim());
                    update.put("email", email.getText().toString().trim());
                    Map<String, Object> addresss = new HashMap<>();
                    addresss.put("country", country.getText().toString().trim());
                    addresss.put("city", city.getText().toString().trim());
                    addresss.put("state", state.getText().toString().trim());
                    update.put("address", addresss);


                    db.collection("client").document(clientid).update(update);
                }
            }
        });
       btnlistrequests.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent activityChangeIntent = new Intent(profile.this, list_requests.class);

               profile.this.startActivity(activityChangeIntent);
           }
       });
    }
}