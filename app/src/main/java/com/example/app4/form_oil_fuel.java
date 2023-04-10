package com.example.app4;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app4.data.oil_fuel;
import com.example.app4.data.veh;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;


public class form_oil_fuel extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private FirebaseFirestore db;
    private FirebaseUser user;
    private FirebaseAuth auth;
    private String clientid, fueltype, oiltype, fuelunit, oilunit, requestid;
    private int fuelquantity, oilquantity;
    private float fuelprice, oilprice;
    private Button btnnext, btnhome, btnlistrequests, btnprofile, btngoback;
    private Spinner oil, fuel, funit, ounit;
    private NumberPicker qoil, qfuel;
    private TextView fprice, oprice;
    private LinearLayout requestoil, requestfuel;
    private ArrayAdapter oils, fuels, ounits, funits;
    private String[] units = new String[]{"mL", "L"};
    private com.example.app4.data.oil_fuel oil_fuel = new oil_fuel();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_oil_fuel);

        btnnext = findViewById(R.id.next);
        btnhome = findViewById(R.id.home);
        btnlistrequests = findViewById(R.id.list);
        btnprofile = findViewById(R.id.profile);
        btngoback = findViewById(R.id.goback);
        qoil = findViewById(R.id.qoil);
        qfuel = findViewById(R.id.qfuel);
        fprice = findViewById(R.id.fprice);
        oprice = findViewById(R.id.oprice);
        requestoil = findViewById(R.id.request_oil);
        requestfuel = findViewById(R.id.request_fuel);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        clientid = user.getUid();

        if(getIntent().getStringExtra("service").trim().equals("both")) {
            requestoil.setVisibility(View.VISIBLE);
            requestfuel.setVisibility(View.VISIBLE);
        } else if(getIntent().getStringExtra("service").trim().equals("oil")) {
            requestoil.setVisibility(View.VISIBLE);
        } else if (getIntent().getStringExtra("service").equals("fuel")) {
            requestfuel.setVisibility(View.VISIBLE);
        }

        ounit = findViewById(R.id.ounit);
        ounit.setOnItemSelectedListener(this);

        funit = findViewById(R.id.funit);
        funit.setOnItemSelectedListener(this);

        oil = findViewById(R.id.oil);
        oil.setOnItemSelectedListener(this);

        fuel = findViewById(R.id.fuel);
        fuel.setOnItemSelectedListener(this);

        qfuel.setMinValue(0);
        qfuel.setMaxValue(10);
        qfuel.setValue(5);

        qoil.setMinValue(0);
        qoil.setMaxValue(10);
        qoil.setValue(5);

        qfuel.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker qfuel, int oldVal, int newVal) {
                fuelquantity = qfuel.getValue();
            }
        });
        qoil.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker qoil, int oldVal, int newVal) {
                oilquantity = qoil.getValue();
            }
        });


        ounits = new ArrayAdapter<String>(form_oil_fuel.this, android.R.layout.simple_spinner_item, units);
        ounits.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ounit.setAdapter(ounits);

        funits = new ArrayAdapter<String>(form_oil_fuel.this, android.R.layout.simple_spinner_item, units);
        funits.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        funit.setAdapter(funits);

        funits = new ArrayAdapter<String>(form_oil_fuel.this, android.R.layout.simple_spinner_item, units);
        oils = new ArrayAdapter<String>(form_oil_fuel.this, android.R.layout.simple_spinner_item, oil_fuel.getOil());
        oils.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        oil.setAdapter(oils);

        fuels = new ArrayAdapter<String>(form_oil_fuel.this, android.R.layout.simple_spinner_item, oil_fuel.getFuel());
        fuels.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fuel.setAdapter(fuels);

        btnnext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(getIntent().getStringExtra("service").trim().equals("both")) {
                    request_both();
                } else if(getIntent().getStringExtra("service").trim().equals("oil")) {
                    request_oil();
                } else if (getIntent().getStringExtra("service").equals("fuel")) {
                    request_fuel();
                }
                Intent activityChangeIntent = new Intent(form_oil_fuel.this, list_stations.class);
                activityChangeIntent.putExtra("requestid",requestid);
                form_oil_fuel.this.startActivity(activityChangeIntent);
            }
        });
        btnhome.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(form_oil_fuel.this, home.class);
                form_oil_fuel.this.startActivity(activityChangeIntent);
            }
        });
        btnlistrequests.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(form_oil_fuel.this, list_requests.class);
                form_oil_fuel.this.startActivity(activityChangeIntent);
            }
        });
        btnprofile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(form_oil_fuel.this, profile.class);
                form_oil_fuel.this.startActivity(activityChangeIntent);
            }
        });
        btngoback.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void request_both() {
        HashMap<String, Object> m = new HashMap<String, Object>();

        DocumentReference ref = db.collection("request").document();
        String request_id = ref.getId();
        requestid =request_id;
        m.put("id", request_id);
        m.put("client_id", clientid);
        m.put("date", Calendar.getInstance().getTime());
        m.put("type","station");
        m.put("fuel", true);
        m.put("oil", true);
        m.put("tfuel", fueltype);
        m.put("toil",oiltype);
        m.put("qfuel",fuelquantity);
        m.put("qoil",oilquantity);
        m.put("state", "not finished");
        ref.set(m);

       // db.collection("station").document(station_id).update(n);
    }
    private void request_oil() {
        HashMap<String, Object> m = new HashMap<String, Object>();

        DocumentReference ref = db.collection("request").document();
        String request_id = ref.getId();
        requestid =request_id;
        m.put("id", request_id);
        m.put("client_id", clientid);
        m.put("date", Calendar.getInstance().getTime());
        m.put("type","station");
        m.put("fuel", false);
        m.put("oil", true);
        m.put("toil",oiltype);
        m.put("qoil",oilquantity);
        m.put("state", "not finished");
        ref.set(m);

    }
    private void request_fuel() {
        HashMap<String, Object> m = new HashMap<String, Object>();

        DocumentReference ref = db.collection("request").document();
        String request_id = ref.getId();
        requestid =request_id;
        m.put("id", request_id);
        m.put("client_id", clientid);
        m.put("date", Calendar.getInstance().getTime());
        m.put("type","station");
        m.put("fuel", true);
        m.put("oil", false);
        m.put("tfuel", fueltype);
        m.put("qfuel",fuelquantity);
        m.put("state", "not finished");
        ref.set(m);

        // db.collection("station").document(station_id).update(n);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        fueltype = fuel.getSelectedItem().toString();
        fuelunit = funit.getSelectedItem().toString();
        oiltype = oil.getSelectedItem().toString();
        oilunit = ounit.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}