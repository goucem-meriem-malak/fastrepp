package com.example.app4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app4.data.address;
import com.example.app4.data.client;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;


public class profile extends AppCompatActivity {
    private FirebaseFirestore db;
    private FirebaseUser user;
    private FirebaseAuth auth;
    private EditText firstname, lastname, email, country, city, state, phone, fphone;
    private Button btnhome, btnlistrequests, btnprofile, btnedit, btnhelpcenter, btngoback;
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
        btngoback = findViewById(R.id.goback);

        firstname=findViewById(R.id.firstname);
        lastname=findViewById(R.id.lastname);
        fphone=findViewById(R.id.fphone);
        phone=findViewById(R.id.phone);
        email = findViewById(R.id.email);
        country = findViewById(R.id.country);
        city = findViewById(R.id.city);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        clientid = user.getUid();

        db.collection("client").document(clientid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshott) {
                client client = documentSnapshott.toObject(client.class);
                if (client.getFirstname()==null){
                    firstname.setText("");
                }else {
                    firstname.setText(client.getFirstname());
                }
                if (client.getLastname()==null){
                    lastname.setText("");
                } else {
                    lastname.setText(client.getLastname());
                }
                if (client.getPhone()==null){
                    fphone.setText("");
                    phone.setText("");
                } else {
                    fphone.setText(client.getPhone().substring(0,client.getPhone().length()-9));
                    phone.setText(client.getPhone().substring(client.getPhone().length()-9,client.getPhone().length()));
                }
                if (client.getEmail()==null){
                    email.setText("");
                } else {
                    email.setText(client.getEmail());
                }
                if (((Map<String, Object>)documentSnapshott.getData().get("address")).get("country")==null) {
                    country.setText("");
                } else {
                    country.setText(((Map<String, Object>)documentSnapshott.getData().get("address")).get("country").toString());
                }
                //state.setText(((Map<String, Object>)documentSnapshott.getData().get("address")).get("state").toString());
                if (((Map<String, Object>)documentSnapshott.getData().get("address")).get("city")==null){
                    city.setText("");
                } else {
                    city.setText(((Map<String, Object>)documentSnapshott.getData().get("address")).get("city").toString());
                }
                //state.setText(((Map<String, Object>)documentSnapshott.getData().get("address")).get("state").toString());
            }
        });

        btnhome.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(profile.this, home.class);

                profile.this.startActivity(activityChangeIntent);
            }
        });
        btnhelpcenter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent activityChangeIntent = new Intent(profile.this, launch_screen.class);
                profile.this.startActivity(activityChangeIntent);
                finish();
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
                //    state.setEnabled(true);
                    email.setEnabled(true);
                    btnedit.setText("Save");
                }
                else{
                    Map<String, Object> update = new HashMap<>();
                    update.put("firstname", firstname.getText().toString().trim());
                    update.put("lastname", lastname.getText().toString().trim());
                    Pattern rfc2822 = Pattern.compile(
                            "^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$");
                    if (!rfc2822.matcher(email.getText().toString()).matches()&&email.getText().toString().isEmpty()!=true) {
                        Toast.makeText(profile.this, "Email invalide", Toast.LENGTH_LONG).show();
                        email.setFocusable(true);
                    }
                    else {
                        update.put("email", email.getText().toString().trim());
                        btnedit.setText("Edit");
                        firstname.setEnabled(false);
                        lastname.setEnabled(false);
                        country.setEnabled(false);
                        city.setEnabled(false);
                      //  state.setEnabled(false);
                        email.setEnabled(false);
                    }
                    Map<String, Object> addresss = new HashMap<>();
                    addresss.put("country", country.getText().toString().trim());
                    addresss.put("city", city.getText().toString().trim());
                 //   addresss.put("state", state.getText().toString().trim());
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
        btngoback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}