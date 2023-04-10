package com.example.app4;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app4.R;
import com.example.app4.adapter_garage;
import com.example.app4.data.client;
import com.example.app4.data.garage;
import com.example.app4.data.get_mechanics;
import com.example.app4.data.mechanic;
import com.example.app4.data.veh;
import com.example.app4.data.vehicule;
import com.example.app4.find;
import com.example.app4.home;
import com.example.app4.list_requests;
import com.example.app4.listener_garage;
import com.example.app4.profile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class form_vehicule extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

        private FirebaseFirestore db;
        private FirebaseUser user;
        private FirebaseAuth auth;
        private String clientid, type, mark, model;
        private ArrayAdapter typee, markk, modell;
        private com.example.app4.data.veh vehicle;
        private Button btnhome, btnlist, btnprofile, btnnext, btngoback;
        private Spinner types, marks, models;
        private NumberPicker nbrpeople;
        private LinearLayout taxii;
        private veh veh = new veh();
        private String requestid;
        private int nbr_people;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.form_vehicule);

            btnhome = findViewById(R.id.home);
            btnlist = findViewById(R.id.list);
            btnprofile = findViewById(R.id.profile);
            btnnext = findViewById(R.id.next);
            btngoback = findViewById(R.id.goback);
            nbrpeople = findViewById(R.id.nbrpeople);
            taxii = findViewById(R.id.taxi);

            types = findViewById(R.id.type);
            types.setOnItemSelectedListener(this);
            marks = findViewById(R.id.mark);
            marks.setOnItemSelectedListener(this);
            models = findViewById(R.id.model);
            models.setOnItemSelectedListener(this);

            if(getIntent().getStringExtra("service").equals("both")){
                taxii.setVisibility(View.VISIBLE);
            } else if (getIntent().getStringExtra("service").equals("taxi")) {
                taxii.setVisibility(View.VISIBLE);
            }

            db = FirebaseFirestore.getInstance();
            auth = FirebaseAuth.getInstance();
            user = auth.getCurrentUser();
            clientid = user.getUid();

            typee = new ArrayAdapter<String>(form_vehicule.this, android.R.layout.simple_spinner_item, veh.getTypes());
            typee.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            types.setAdapter(typee);

            markk = new ArrayAdapter<String>(form_vehicule.this, android.R.layout.simple_spinner_item, veh.getPassenger_vehicle());
            markk.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            marks.setAdapter(markk);

            nbrpeople.setMinValue(1);
            nbrpeople.setMaxValue(5);
            nbrpeople.setValue(1);

            nbrpeople.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker nbrpeople, int oldVal, int newVal) {
                    nbr_people = nbrpeople.getValue();
                }
            });

            btnhome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent activityChangeIntent = new Intent(form_vehicule.this, home.class);
                    form_vehicule.this.startActivity(activityChangeIntent);
                }
            });
            btnlist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent activityChangeIntent = new Intent(form_vehicule.this, list_requests.class);
                    form_vehicule.this.startActivity(activityChangeIntent);
                }
            });
            btnprofile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent activityChangeIntent = new Intent(form_vehicule.this, profile.class);
                    form_vehicule.this.startActivity(activityChangeIntent);
                }
            });
            btnnext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(getIntent().getStringExtra("service").equals("both")){
                        request_both();
                        Intent activityChangeIntent = new Intent(form_vehicule.this, list_taxis.class);
                        activityChangeIntent.putExtra("requestid",requestid);
                        Toast.makeText(form_vehicule.this, requestid, Toast.LENGTH_SHORT).show();
                        form_vehicule.this.startActivity(activityChangeIntent);
                    } else if (getIntent().getStringExtra("service").equals("taxi")) {
                        request_taxi();
                        Intent activityChangeIntent = new Intent(form_vehicule.this, list_taxis.class);
                        activityChangeIntent.putExtra("requestid",requestid);
                        Toast.makeText(form_vehicule.this, requestid, Toast.LENGTH_SHORT).show();
                        form_vehicule.this.startActivity(activityChangeIntent);
                    } else if (getIntent().getStringExtra("service").equals("ambulance")){
                        request_ambulance();
                        Intent activityChangeIntent = new Intent(form_vehicule.this, list_tow_truck.class);
                        activityChangeIntent.putExtra("requestid",requestid);
                        Toast.makeText(form_vehicule.this, requestid, Toast.LENGTH_SHORT).show();
                        form_vehicule.this.startActivity(activityChangeIntent);
                    } else if (getIntent().getStringExtra("service").equals("mechanic")){
                        request_mechanic();
                        Intent activityChangeIntent = new Intent(form_vehicule.this, list_mechanics.class);
                        activityChangeIntent.putExtra("requestid",requestid);
                        form_vehicule.this.startActivity(activityChangeIntent);
                    }
                }
            });
            btngoback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (requestid!=null){
                        db.collection("request").document(requestid).delete();
                        finish();
                    } else {
                        finish();
                    }
                }
            });
        }

    private void request_mechanic() {
        HashMap<String, Object> m = new HashMap<String, Object>();
        Map<String, Object> veh = new HashMap<String, Object>();
        veh.put("type", type);
        veh.put("mark", mark);
        veh.put("model", model);

        DocumentReference ref = db.collection("request").document();
        String request_id = ref.getId();
        requestid =request_id;
        m.put("id", request_id);
        m.put("client_id", clientid);
        m.put("type","mechanic");
        m.put("vehicle", veh);
        ref.set(m);
    }

    private void request_ambulance() {
    }

    private void request_taxi() {
        HashMap<String, Object> m = new HashMap<String, Object>();
        Map<String, Object> veh = new HashMap<String, Object>();
        veh.put("type", type);
        veh.put("mark", mark);
        veh.put("model", model);

        DocumentReference ref = db.collection("request").document();
        String request_id = ref.getId();
        requestid =request_id;
        m.put("id", request_id);
        m.put("client_id", clientid);
        m.put("date", Calendar.getInstance().getTime());
        m.put("type","tow_truck");
        m.put("vehicle", veh);
        m.put("people_number", nbr_people);
        m.put("ambulance", false);
        m.put("taxi", true);
        ref.set(m);
    }

    private void request_both() {
        HashMap<String, Object> m = new HashMap<String, Object>();
        Map<String, Object> veh = new HashMap<String, Object>();
        veh.put("type", type);
        veh.put("mark", mark);
        veh.put("model", model);

        DocumentReference ref = db.collection("request").document();
        String request_id = ref.getId();
        requestid =request_id;
        m.put("id", request_id);
        m.put("client_id", clientid);
        m.put("date", Calendar.getInstance().getTime());
        m.put("type","tow_truck");
        m.put("vehicle", veh);
        m.put("people_number", nbr_people);
        m.put("ambulance", false);
        m.put("taxi", true);
        m.put("state", "not finished");
        ref.set(m);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            mark = marks.getSelectedItem().toString();
        if (marks.getSelectedItem().toString().equals("Nissan")){
            modell = new ArrayAdapter<String>(form_vehicule.this, android.R.layout.simple_spinner_item, veh.getNissan());
            modell.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            models.setAdapter(modell);
        } else if (marks.getSelectedItem().toString().equals("Hyundai")) {
            modell = new ArrayAdapter<String>(form_vehicule.this, android.R.layout.simple_spinner_item, veh.getHyundai());
            modell.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            models.setAdapter(modell);
        }
        type = types.getSelectedItem().toString();
        mark = marks.getSelectedItem().toString();
        if (models.getSelectedItem()!=null){
            model = models.getSelectedItem().toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
