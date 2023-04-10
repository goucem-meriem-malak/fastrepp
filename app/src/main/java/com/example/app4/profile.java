package com.example.app4;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app4.data.address;
import com.example.app4.data.client;
import com.example.app4.data.garage;
import com.example.app4.data.mechanic;
import com.example.app4.data.station;
import com.example.app4.data.tow_truck;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Locale;
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

        db.collection("worker").document(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
            if (task.isSuccessful()) {
            DocumentSnapshot document = task.getResult();
            if (document.exists()) {
                btnhome.setVisibility(View.GONE);
                btnlistrequests.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent activityChangeIntent = new Intent(profile.this, list_requests_worker.class);
                        profile.this.startActivity(activityChangeIntent);
                        finish();
                    }
                });
            String type = document.get("type").toString();
            if (type.equals("mechanic")) {
            db.collection("mechanic").document(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            mechanic mechanic = document.toObject(mechanic.class);
                            if (mechanic.getFirstname() == null) {
                                firstname.setText("");
                            } else {
                                firstname.setText(mechanic.getFirstname());
                            }
                            if (mechanic.getLastname() == null) {
                                lastname.setText("");
                            } else {
                                lastname.setText(mechanic.getLastname());
                            }
                            if (mechanic.getPhone() == null) {
                                fphone.setText("");
                                phone.setText("");
                            } else {
                                fphone.setText(mechanic.getPhone().substring(0, mechanic.getPhone().length() - 9));
                                phone.setText(mechanic.getPhone().substring(mechanic.getPhone().length() - 9, mechanic.getPhone().length()));
                            }
                            if (mechanic.getEmail() == null) {
                                email.setText("");
                            } else {
                                email.setText(mechanic.getEmail());
                            }
                            if(((Map<String, Object>) document.getData().get("address"))==null){
                                country.setText("");
                                city.setText("");
                            } else {
                                if (((Map<String, Object>) document.getData().get("address")).get("country") == null) {
                                    country.setText("");
                                } else {
                                    country.setText(((Map<String, Object>) document.getData().get("address")).get("country").toString());
                                }
                                //state.setText(((Map<String, Object>)documentSnapshott.getData().get("address")).get("state").toString());
                                if (((Map<String, Object>) document.getData().get("address")).get("city") == null) {
                                    city.setText("");
                                } else {
                                    city.setText(((Map<String, Object>) document.getData().get("address")).get("city").toString());
                                }
                                //state.setText(((Map<String, Object>)documentSnapshott.getData().get("address")).get("state").toString());
                            }
                        }
                    }
                }
            });
            } else if (type.equals("station")) {
            db.collection("station").document(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {

                            station station = document.toObject(station.class);
                            if (station.getName() == null) {
                                firstname.setText("");
                            } else {
                                firstname.setText(station.getName());
                            }
                            if (station.getPhone() == null) {
                                fphone.setText("");
                                phone.setText("");
                            } else {
                                fphone.setText(station.getPhone().substring(0, station.getPhone().length() - 9));
                                phone.setText(station.getPhone().substring(station.getPhone().length() - 9, station.getPhone().length()));
                            }
                            if (((Map<String, Object>) document.getData().get("address")).get("country") == null) {
                                country.setText("");
                            } else {
                                country.setText(((Map<String, Object>) document.getData().get("address")).get("country").toString());
                            }
                            //state.setText(((Map<String, Object>)documentSnapshott.getData().get("address")).get("state").toString());
                            if (((Map<String, Object>) document.getData().get("address")).get("city") == null) {
                                city.setText("");
                            } else {
                                city.setText(((Map<String, Object>) document.getData().get("address")).get("city").toString());
                            }
                            //state.setText(((Map<String, Object>)documentSnapshott.getData().get("address")).get("state").toString());
                        }
                    }
                }

            });
            } else if (type.equals("garage")) {
            db.collection("garage").document(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            garage garage = document.toObject(garage.class);
                            if (garage.getName() == null) {
                                firstname.setText("");
                            } else {
                                firstname.setText(garage.getName());
                            }
                            if (garage.getPhone() == null) {
                                fphone.setText("");
                                phone.setText("");
                            } else {
                                fphone.setText(garage.getPhone().substring(0, garage.getPhone().length() - 9));
                                phone.setText(garage.getPhone().substring(garage.getPhone().length() - 9, garage.getPhone().length()));
                            }
                            if (((Map<String, Object>) document.getData().get("address")).get("country") == null) {
                                country.setText("");
                            } else {
                                country.setText(((Map<String, Object>) document.getData().get("address")).get("country").toString());
                            }
                            //state.setText(((Map<String, Object>)documentSnapshott.getData().get("address")).get("state").toString());
                            if (((Map<String, Object>) document.getData().get("address")).get("city") == null) {
                                city.setText("");
                            } else {
                                city.setText(((Map<String, Object>) document.getData().get("address")).get("city").toString());
                            }
                            //state.setText(((Map<String, Object>)documentSnapshott.getData().get("address")).get("state").toString());
                        }
                    }
                }

            });
            } else if (type.equals("towtruck")) {
            db.collection("towtruck").document(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            tow_truck tow_truck = document.toObject(tow_truck.class);
                            if (tow_truck.getFirstname() == null) {
                                firstname.setText("");
                            } else {
                                firstname.setText(tow_truck.getFirstname());
                            }
                            if (tow_truck.getLastname() == null) {
                                lastname.setText("");
                            } else {
                                lastname.setText(tow_truck.getFirstname());
                            }
                            if (tow_truck.getPhone() == null) {
                                fphone.setText("");
                                phone.setText("");
                            } else {
                                fphone.setText(tow_truck.getPhone().substring(0, tow_truck.getPhone().length() - 9));
                                phone.setText(tow_truck.getPhone().substring(tow_truck.getPhone().length() - 9, tow_truck.getPhone().length()));
                            }
                            if (((Map<String, Object>) document.getData().get("address")).get("country") == null) {
                                country.setText("");
                            } else {
                                country.setText(((Map<String, Object>) document.getData().get("address")).get("country").toString());
                            }
                            //state.setText(((Map<String, Object>)documentSnapshott.getData().get("address")).get("state").toString());
                            if (((Map<String, Object>) document.getData().get("address")).get("city") == null) {
                                city.setText("");
                            } else {
                                city.setText(((Map<String, Object>) document.getData().get("address")).get("city").toString());
                            }
                            //state.setText(((Map<String, Object>)documentSnapshott.getData().get("address")).get("state").toString());
                        }
                    }
                }

            });
            }
            } else {
            db.collection("client").document(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        client client = document.toObject(client.class);
                        if (client.getFirstname() == null) {
                            firstname.setText("");
                        } else {
                            firstname.setText(client.getFirstname());
                        }
                        if (client.getLastname() == null) {
                            lastname.setText("");
                        } else {
                            lastname.setText(client.getLastname());
                        }
                        if (client.getPhone() == null) {
                            fphone.setText("");
                            phone.setText("");
                        } else {
                            fphone.setText(client.getPhone().substring(0, client.getPhone().length() - 9));
                            phone.setText(client.getPhone().substring(client.getPhone().length() - 9, client.getPhone().length()));
                        }
                        if (client.getEmail() == null) {
                            email.setText("");
                        } else {
                            email.setText(client.getEmail());
                        }
                        if (((Map<String, Object>) document.getData().get("address")).get("country") == null) {
                            country.setText("");
                        } else {
                            country.setText(((Map<String, Object>) document.getData().get("address")).get("country").toString());
                        }
                        //state.setText(((Map<String, Object>)documentSnapshott.getData().get("address")).get("state").toString());
                        if (((Map<String, Object>) document.getData().get("address")).get("city") == null) {
                            city.setText("");
                        } else {
                            city.setText(((Map<String, Object>) document.getData().get("address")).get("city").toString());
                        }
                        //state.setText(((Map<String, Object>)documentSnapshott.getData().get("address")).get("state").toString());

                    }
                } else {
                    Log.d(TAG, "Failed to get document.", task.getException());
                }
            }
            });
            }
            } else {
            Log.d(TAG, "Failed to get document.", task.getException());
            }
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
             /*   FirebaseAuth.getInstance().signOut();
                Intent activityChangeIntent = new Intent(profile.this, launch_screen.class);
                profile.this.startActivity(activityChangeIntent);
                finish();*/
                // Define a string for the language code to switch to
                String languageCode = "ar"; // Arabic

// Set the desired locale
                Locale locale = new Locale(languageCode);

// Set the configuration of the Resources object to use the new locale
                Configuration config = new Configuration(getResources().getConfiguration());
                config.setLocale(locale);
                getResources().updateConfiguration(config, getResources().getDisplayMetrics());

// Restart the activity to apply the new language
                Intent intent = getIntent();
                finish();
                startActivity(intent);

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