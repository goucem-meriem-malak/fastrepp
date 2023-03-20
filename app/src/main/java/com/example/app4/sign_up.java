package com.example.app4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.app4.data.client;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class sign_up extends AppCompatActivity{
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private EditText firstname,lastname,phone,password,address;
    private Button btnsignup, btnsignin, btnguest;
    private client client;
    private LatLng clocation= new LatLng(0,0);
    private String cid, cfirstname, clastname, cphone, cpassword, caddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        btnsignup = findViewById(R.id.signup);
        btnsignin = findViewById(R.id.signin);

        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        address = findViewById(R.id.address);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();


        btnsignup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                HashMap<String, Object> m =new HashMap<String, Object>();

                    Intent intentt = getIntent();
                    cid = intentt.getStringExtra("clientid");
                    DocumentReference ref = db.collection("client").document(cid);
                    cfirstname = firstname.getText().toString().trim();
                    clastname = lastname.getText().toString().trim();
                    cphone = phone.getText().toString().trim();
                    cpassword = password.getText().toString().trim();
                    caddress = address.getText().toString().trim();

                    m.put("firstname",cfirstname);
                    m.put("lastname",clastname);
                    m.put("phone",cphone);
                    m.put("password",cpassword);
                    m.put("address",caddress);
                    m.put("signed", true);
                    ref.update(m)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getApplicationContext(),"Welcome", Toast.LENGTH_LONG).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(),"Failed", Toast.LENGTH_LONG).show();
                                }
                            });

                    Intent intent = new Intent(sign_up.this, find.class);

                    intent.putExtra("clientid",cid);
                    startActivity(intent);
            }
        });
        btnsignin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent activityChangeIntent = new Intent(sign_up.this, sign_in.class);

                sign_up.this.startActivity(activityChangeIntent);
            }
        });
    }
}