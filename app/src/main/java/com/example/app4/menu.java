package com.example.app4;


import static android.content.ContentValues.TAG;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.app4.data.mechanic;
import com.example.app4.data.oil_fuel;
import com.example.app4.data.other;
import com.example.app4.data.team_services;
import com.example.app4.data.veh;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class menu extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private FirebaseFirestore db;
    private FirebaseUser user;
    private FirebaseAuth auth;
    private String mech_veh_typee, mech_veh_markk, mech_veh_modell, tow_veh_typee, tow_veh_markk,
            tow_veh_modell, oil_typee, fuel_typee, team_service_typee, oil_unitt, fuel_unitt;
    private ArrayAdapter<String> mech_veh_types, mech_veh_marks, mech_veh_models, tow_veh_types, tow_veh_marks,
            tow_veh_models, oil_types, fuel_types, team_service_types, oil_units, fuel_units;
    private oil_fuel oil_fuel = new oil_fuel();
    private veh mech_veh = new veh(), tow_veh = new veh();
    private other other;
    private List<String> team_service = new ArrayList<>(), fueltype = new ArrayList<>(), oiltype = new ArrayList<>();
    private team_services team_services = new team_services();
    private Button btnhome, btnlistrequests, btngoback, btnprofile, btnhelpcenter,
            btnrequestmechanic, btnrequesttowtruck, btnrequeststation, btnrequestteam, btnrequestgarage;
    private LinearLayout mechanic, mech_veh_form, towtruck, tow_veh_form, tow_taxi_form, station,
            station_oil_form, station_fuel_form, team, team_service_form, garage, garage_form, towcheckbox, stationcheckbox;
    private CheckBox fuel, oil, taxi, ambulance;
    private Spinner mech_veh_type, mech_veh_mark, mech_veh_model, tow_veh_type, tow_veh_mark, tow_veh_model,
            oil_type, oil_unit, fuel_type, fuel_unit, team_service_type;
    private NumberPicker taxi_nbr_passengers, qoil, qfuel;
    private TextView fprice, oprice;
    private String clientid, requestid, fuelunit, oilunit;
    private String[] units = new String[]{"mL", "L"};
    private int nbr_people, fuelquantity, oilquantity;
    private float fuelprice, oilprice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        clientid = user.getUid();

        btnprofile = findViewById(R.id.profile);
        btnhome = findViewById(R.id.home);
        btnlistrequests = findViewById(R.id.list_requests);
        btngoback = findViewById(R.id.goback);
        btnhelpcenter = findViewById(R.id.help_center);

        btnrequestmechanic = findViewById(R.id.request_mechanic);
        btnrequesttowtruck = findViewById(R.id.request_towtruck);
        btnrequeststation = findViewById(R.id.request_station);
        btnrequestteam = findViewById(R.id.request_team);
        btnrequestgarage = findViewById(R.id.request_garage);

        mechanic = findViewById(R.id.mechanich);
        towtruck = findViewById(R.id.towtruck);
        station = findViewById(R.id.station);
        team = findViewById(R.id.team);
        garage = findViewById(R.id.garage);

        mech_veh_form = findViewById(R.id.mechanic_veh_form);
        tow_veh_form = findViewById(R.id.tow_veh_form);
        tow_taxi_form = findViewById(R.id.tow_taxi_form);
        station_oil_form = findViewById(R.id.station_oil_form);
        station_fuel_form = findViewById(R.id.station_fuel_form);
        team_service_form = findViewById(R.id.team_service_form);
        garage_form = findViewById(R.id.garage_form);
        towcheckbox = findViewById(R.id.tow_checkbox);
        stationcheckbox = findViewById(R.id.station_checkbox);

        fuel = findViewById(R.id.fuel);
        oil = findViewById(R.id.oil);
        taxi = findViewById(R.id.taxi);
        ambulance = findViewById(R.id.ambulance);

        taxi_nbr_passengers = findViewById(R.id.taxi_nbr_passengers);

        oil_type = findViewById(R.id.oil_type);
        oil_type.setOnItemSelectedListener(this);
        fuel_type = findViewById(R.id.fuel_type);
        fuel_type.setOnItemSelectedListener(this);
        qoil = findViewById(R.id.qoil);
        qfuel = findViewById(R.id.qfuel);
        fprice = findViewById(R.id.fuel_price);
        oprice = findViewById(R.id.oil_price);
        oil_unit = findViewById(R.id.oil_unit);
        oil_unit.setOnItemSelectedListener(this);
        fuel_unit = findViewById(R.id.fuel_unit);
        fuel_unit.setOnItemSelectedListener(this);

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

        oil_units = new ArrayAdapter<String>(menu.this, android.R.layout.simple_spinner_item, units);
        oil_units.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        oil_unit.setAdapter(oil_units);

        fuel_units = new ArrayAdapter<String>(menu.this, android.R.layout.simple_spinner_item, units);
        fuel_units.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fuel_unit.setAdapter(fuel_units);

        oil_types = new ArrayAdapter<String>(menu.this, android.R.layout.simple_spinner_item, oiltype);
        oil_types.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        oil_type.setAdapter(oil_types);
        get_oil();

        fuel_types = new ArrayAdapter<String>(menu.this, android.R.layout.simple_spinner_item, fueltype);
        fuel_types.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fuel_type.setAdapter(fuel_types);
        get_fuel();

        mech_veh_type = findViewById(R.id.mech_veh_type);
        mech_veh_type.setOnItemSelectedListener(this);
        mech_veh_mark = findViewById(R.id.mech_veh_mark);
        mech_veh_mark.setOnItemSelectedListener(this);
        mech_veh_model = findViewById(R.id.mech_veh_model);
        mech_veh_model.setOnItemSelectedListener(this);

        tow_veh_type = findViewById(R.id.tow_veh_type);
        tow_veh_type.setOnItemSelectedListener(this);
        tow_veh_mark = findViewById(R.id.tow_veh_mark);
        tow_veh_mark.setOnItemSelectedListener(this);
        tow_veh_model = findViewById(R.id.tow_veh_model);
        tow_veh_model.setOnItemSelectedListener(this);
        oil_type = findViewById(R.id.oil_type);
        oil_type.setOnItemSelectedListener(this);
        oil_unit = findViewById(R.id.oil_unit);
        oil_unit.setOnItemSelectedListener(this);
        fuel_type = findViewById(R.id.fuel_type);
        fuel_type.setOnItemSelectedListener(this);
        fuel_unit = findViewById(R.id.fuel_unit);
        fuel_unit.setOnItemSelectedListener(this);
        team_service_type = findViewById(R.id.service_type);
        team_service_type.setOnItemSelectedListener(this);

        mech_veh_types = new ArrayAdapter<String>(menu.this, android.R.layout.simple_spinner_item, mech_veh.getTypes());
        mech_veh_types.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mech_veh_type.setAdapter(mech_veh_types);

        mech_veh_marks = new ArrayAdapter<String>(menu.this, android.R.layout.simple_spinner_item, mech_veh.getPassenger_vehicle());
        mech_veh_marks.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mech_veh_mark.setAdapter(mech_veh_marks);

        mech_veh_models = new ArrayAdapter<String>(menu.this, android.R.layout.simple_spinner_item, mech_veh.getNissan());
        mech_veh_models.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mech_veh_model.setAdapter(mech_veh_models);

        tow_veh_types = new ArrayAdapter<String>(menu.this, android.R.layout.simple_spinner_item, mech_veh.getTypes());
        tow_veh_types.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tow_veh_type.setAdapter(tow_veh_types);

        tow_veh_marks = new ArrayAdapter<String>(menu.this, android.R.layout.simple_spinner_item, mech_veh.getPassenger_vehicle());
        tow_veh_marks.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tow_veh_mark.setAdapter(tow_veh_marks);

        tow_veh_models = new ArrayAdapter<String>(menu.this, android.R.layout.simple_spinner_item, mech_veh.getNissan());
        tow_veh_models.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tow_veh_model.setAdapter(tow_veh_models);

    //    oil_types = new ArrayAdapter<String>(menu.this, android.R.layout.simple_spinner_item, oiltype);
      //  oil_types.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      //  oil_type.setAdapter(oil_types);
      //  get_oil();

        fuel_types = new ArrayAdapter<String>(menu.this, android.R.layout.simple_spinner_item, fueltype);
        fuel_types.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fuel_type.setAdapter(fuel_types);
        get_fuel();

        team_service_types = new ArrayAdapter<String>(menu.this, android.R.layout.simple_spinner_dropdown_item, team_service);
        team_service_types.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        team_service_type.setAdapter(team_service_types);
        get_team_services();


        taxi_nbr_passengers.setMinValue(1);
        taxi_nbr_passengers.setMaxValue(5);
        taxi_nbr_passengers.setValue(1);

        DocumentReference docRef = db.collection("myCollection").document("myDocument");

//        team_service_type<DocumentSnapshot> future = docRef.get();
        // DocumentSnapshot document = db.collection("other").document("team_services").get().getResult();

       /* if (document.exists()) {
            List<String> stringList = (List<String>) document.get("myArrayField");
            String[] stringArray = stringList.toArray(new String[stringList.size()]);
            // Now you can use the stringArray variable as an array of strings
        }*/

        taxi_nbr_passengers.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker taxi_nbr_passengers, int oldVal, int newVal) {
                nbr_people = taxi_nbr_passengers.getValue();
            }
        });

        btnhome.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(menu.this, home.class);
                menu.this.startActivity(activityChangeIntent);
            }
        });
        btnlistrequests.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(menu.this, list_requests.class);
                menu.this.startActivity(activityChangeIntent);
            }
        });
        btnprofile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(menu.this, profile.class);
                menu.this.startActivity(activityChangeIntent);
            }
        });
        btngoback.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (requestid != null) {
                    db.collection("request").document(requestid).delete();
                    finish();
                } else {
                    finish();
                }
            }
        });
        btnhelpcenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(menu.this, help_center.class);
                menu.this.startActivity(activityChangeIntent);
            }
        });
        btnrequestmechanic.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mech_veh_form.isShown()) {
                    request_mechanic();
                    Intent activityChangeIntent = new Intent(menu.this, list_mechanics.class);
                    activityChangeIntent.putExtra("requestid", requestid);
                    startActivity(activityChangeIntent);
                } else {
                    mech_veh_form.setVisibility(View.VISIBLE);
                }
            }
        });
        btnrequesttowtruck.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (tow_veh_form.isShown()) {
                    if (!taxi.isChecked() && !ambulance.isChecked()) {
                        request_tow();
                        Intent activityChangeIntent = new Intent(menu.this, listener_tow.class);
                        activityChangeIntent.putExtra("requestid", requestid);
                        startActivity(activityChangeIntent);
                    } else if (taxi.isChecked() && !ambulance.isChecked()) {
                        request_tow();
                        request_taxi();
                        Intent activityChangeIntent = new Intent(menu.this, list_taxis.class);
                        activityChangeIntent.putExtra("requestid", requestid);
                        activityChangeIntent.putExtra("service", "taxi");
                        startActivity(activityChangeIntent);
                    } else if (!taxi.isChecked() && ambulance.isChecked()) {
                        request_tow();
                        request_ambulance();
                        Intent activityChangeIntent = new Intent(menu.this, list_tows.class);
                        activityChangeIntent.putExtra("requestid", requestid);
                        activityChangeIntent.putExtra("service", "ambulance");
                        startActivity(activityChangeIntent);
                    } else {
                        request_tow();
                        request_taxi();
                        request_ambulance();
                        Intent activityChangeIntent = new Intent(menu.this, list_taxis.class);
                        activityChangeIntent.putExtra("requestid", requestid);
                        activityChangeIntent.putExtra("service", "both");
                        startActivity(activityChangeIntent);
                    }
                } else {
                    tow_veh_form.setVisibility(View.VISIBLE);
                }
            }
        });
        btnrequeststation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (stationcheckbox.isShown()) {
                    if (oil.isChecked() && fuel.isChecked()) {
                        request_oil();
                        request_fuel();
                        Intent activityChangeIntent = new Intent(menu.this, list_stations.class);
                        activityChangeIntent.putExtra("requestid", requestid);
                        menu.this.startActivity(activityChangeIntent);
                    } else if (oil.isChecked()) {
                        request_oil();
                        Intent activityChangeIntent = new Intent(menu.this, list_stations.class);
                        activityChangeIntent.putExtra("requestid", requestid);
                        menu.this.startActivity(activityChangeIntent);
                    } else if (fuel.isChecked()) {
                        request_fuel();
                        Intent activityChangeIntent = new Intent(menu.this, list_stations.class);
                        activityChangeIntent.putExtra("requestid", requestid);
                        menu.this.startActivity(activityChangeIntent);
                    } else {
                        Toast.makeText(menu.this, "Choose A Service! ", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    stationcheckbox.setVisibility(View.VISIBLE);
                }
            }
        });
        btnrequestteam.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (team_service_form.isShown()) {
                    request_team();
                    Intent activityChangeIntent = new Intent(menu.this, list_garage.class);
                    activityChangeIntent.putExtra("requestid", requestid);
                    menu.this.startActivity(activityChangeIntent);
                } else {
                    team_service_form.setVisibility(View.VISIBLE);
                }
            }
        });
        btnrequestgarage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (garage_form.isShown()) {
                    Intent activityChangeIntent = new Intent(menu.this, list_garage.class);
                    menu.this.startActivity(activityChangeIntent);
                } else {
                    garage_form.setVisibility(View.VISIBLE);
                }
            }
        });
        mechanic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mech_veh_form.isShown()) {
                    mech_veh_form.setVisibility(View.GONE);
                } else {
                    mech_veh_form.setVisibility(View.VISIBLE);
                }
            }
        });
        towtruck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tow_veh_form.isShown() || tow_taxi_form.isShown()) {
                    tow_veh_form.setVisibility(View.GONE);
                    tow_taxi_form.setVisibility(View.GONE);
                } else {
                    if (taxi.isChecked()) {
                        tow_veh_form.setVisibility(View.VISIBLE);
                        tow_taxi_form.setVisibility(View.VISIBLE);
                    } else {
                        tow_veh_form.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        station.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (station_oil_form.isShown() || station_fuel_form.isShown() || stationcheckbox.isShown()) {
                    stationcheckbox.setVisibility(View.GONE);
                    station_oil_form.setVisibility(View.GONE);
                    station_fuel_form.setVisibility(View.GONE);
                } else {
                    stationcheckbox.setVisibility(View.VISIBLE);
                    if (oil.isChecked()) {
                        station_oil_form.setVisibility(View.VISIBLE);
                    } else if (fuel.isChecked()) {
                        station_fuel_form.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (team_service_form.isShown()) {
                    team_service_form.setVisibility(View.GONE);
                } else {
                    team_service_form.setVisibility(View.VISIBLE);
                }
            }
        });
        garage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (garage_form.isShown()) {
                    garage_form.setVisibility(View.GONE);
                } else {
                    garage_form.setVisibility(View.VISIBLE);
                }
            }
        });
        fuel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (fuel.isChecked()) {
                    station_fuel_form.setVisibility(View.VISIBLE);
                } else {
                    station_fuel_form.setVisibility(View.GONE);
                }
            }
        });
        oil.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (oil.isChecked()) {
                    station_oil_form.setVisibility(View.VISIBLE);
                } else {
                    station_oil_form.setVisibility(View.GONE);
                }
            }
        });
        taxi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (taxi.isChecked()) {
                    tow_veh_form.setVisibility(View.VISIBLE);
                    tow_taxi_form.setVisibility(View.VISIBLE);
                } else {
                    tow_taxi_form.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (mech_veh_mark.getSelectedItem().toString().equals("Hyundai")) {
            mech_veh_models = new ArrayAdapter<String>(menu.this, android.R.layout.simple_spinner_item, mech_veh.getHyundai());
            mech_veh_models.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mech_veh_model.setAdapter(mech_veh_models);
        } else if (mech_veh_mark.getSelectedItem().toString().equals("Nissan")) {
            mech_veh_models = new ArrayAdapter<String>(menu.this, android.R.layout.simple_spinner_item, mech_veh.getNissan());
            mech_veh_models.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mech_veh_model.setAdapter(mech_veh_models);
        }
        mech_veh_typee = mech_veh_type.getSelectedItem().toString();
        mech_veh_markk = mech_veh_mark.getSelectedItem().toString();
        if (mech_veh_model.getSelectedItem() != null) {
            mech_veh_modell = mech_veh_model.getSelectedItem().toString();
        }
        if (tow_veh_mark.getSelectedItem().toString().equals("Hyundai")) {
            tow_veh_models = new ArrayAdapter<String>(menu.this, android.R.layout.simple_spinner_item, mech_veh.getHyundai());
            tow_veh_models.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            tow_veh_model.setAdapter(tow_veh_models);
        } else if (tow_veh_mark.getSelectedItem().toString().equals("Nissan")) {
            tow_veh_models = new ArrayAdapter<String>(menu.this, android.R.layout.simple_spinner_item, mech_veh.getNissan());
            tow_veh_models.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            tow_veh_model.setAdapter(tow_veh_models);
        }
        tow_veh_typee = tow_veh_type.getSelectedItem().toString();
        tow_veh_markk = tow_veh_mark.getSelectedItem().toString();
        if (tow_veh_model.getSelectedItem() != null) {
            tow_veh_modell = tow_veh_model.getSelectedItem().toString();
        }
//        oil_typee = oil_type.getSelectedItem().toString().trim();
        fuel_typee = fuel_type.getSelectedItem().toString();
        fuelunit = fuel_unit.getSelectedItem().toString();
//        oil_typee = oil_type.getSelectedItem().toString();
        oilunit = oil_unit.getSelectedItem().toString();
//        team_service_typee = team_service_type.getSelectedItem().toString().trim();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void request_mechanic() {
        HashMap<String, Object> m = new HashMap<String, Object>();
        Map<String, Object> veh = new HashMap<String, Object>();
        veh.put("type", mech_veh_typee);
        veh.put("mark", mech_veh_markk);
        veh.put("model", mech_veh_modell);

        DocumentReference ref = db.collection("request").document();
        requestid = ref.getId();
        m.put("id", requestid);
        m.put("client_id", clientid);
        m.put("type", "mechanic");
        m.put("vehicle", veh);
        ref.set(m);
        Toast.makeText(this, requestid, Toast.LENGTH_SHORT).show();
    }

    private void request_tow() {
        HashMap<String, Object> m = new HashMap<String, Object>();
        Map<String, Object> veh = new HashMap<String, Object>();
        veh.put("type", tow_veh_typee);
        veh.put("mark", tow_veh_markk);
        veh.put("model", tow_veh_modell);

        DocumentReference ref = db.collection("request").document();
        requestid = ref.getId();
        m.put("id", requestid);
        m.put("client_id", clientid);
        m.put("date", Calendar.getInstance().getTime());
        m.put("type", "tow_truck");
        m.put("vehicle", veh);
        ref.set(m);
    }

    private void request_taxi() {
        HashMap<String, Object> m = new HashMap<String, Object>();
        DocumentReference ref;
        m.put("id", requestid);
        m.put("client_id", clientid);
        m.put("date", Calendar.getInstance().getTime());
        m.put("type", "tow_truck");
        m.put("passenger_number", nbr_people);
        m.put("taxi", true);
        if (requestid == null) {
            ref = db.collection("request").document();
            requestid = ref.getId();
            ref.set(m);
        } else {
            ref = db.collection("request").document(requestid);
            ref.update(m);
        }
    }

    private void request_ambulance() {
        HashMap<String, Object> m = new HashMap<String, Object>();
        DocumentReference ref;
        m.put("id", requestid);
        m.put("client_id", clientid);
        m.put("date", Calendar.getInstance().getTime());
        m.put("type", "tow_truck");
        m.put("ambulance", true);
        if (requestid == null) {
            ref = db.collection("request").document();
            requestid = ref.getId();
            ref.set(m);
        } else {
            ref = db.collection("request").document(requestid);
            ref.update(m);
        }
    }

    private void request_fuel() {
        HashMap<String, Object> m = new HashMap<String, Object>();

        m.put("id", requestid);
        m.put("client_id", clientid);
        m.put("date", Calendar.getInstance().getTime());
        m.put("type", "station");
        m.put("fuel", true);
        m.put("fuel_type", fueltype);
        m.put("fuel_quantity", fuelquantity);
        m.put("fuel_unit", fuel_unitt);
        m.put("state", "not finished");
        DocumentReference ref;
        if (requestid == null) {
            ref = db.collection("request").document();
            requestid = ref.getId();
            ref.set(m);
        } else {
            ref = db.collection("request").document(requestid);
            requestid = ref.getId();
            ref.update(m);
        }
    }

    private void request_oil() {
        HashMap<String, Object> m = new HashMap<String, Object>();

        m.put("id", requestid);
        m.put("client_id", clientid);
        m.put("date", Calendar.getInstance().getTime());
        m.put("type", "station");
        m.put("oil", true);
        m.put("oil_type", oil_typee);
        m.put("oil_quantity", oilquantity);
        m.put("oil_unit", oil_unitt);
        m.put("state", "not finished");
        DocumentReference ref;
        if (requestid == null) {
            ref = db.collection("request").document();
            requestid = ref.getId();
            ref.set(m);
        } else {
            ref = db.collection("request").document(requestid);
            requestid = ref.getId();
            ref.update(m);
        }
    }

    private void request_team() {
        HashMap<String, Object> m = new HashMap<String, Object>();
        DocumentReference ref;
        ref = db.collection("request").document();
        requestid = ref.getId();
        m.put("id", requestid);
        m.put("client_id", clientid);
        m.put("date", Calendar.getInstance().getTime());
        m.put("type", "team");
        m.put("team_nbr", nbr_people);
        m.put("state", "not finished");
        ref.set(m);
    }
    public void get_vehicle () {
        db.collection("other").document("vehicle").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        List<String> markList = (List<String>) document.get("mark");
                        if (markList!= null) {
                            for (String mark : markList) {
                                //mech_veh_marks.add(mark);
                            }
                            team_service_types.notifyDataSetChanged();
                        }
                    }
                }
            }
        });
    }
    public void get_oil () {
        db.collection("other").document("fuel_oil").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        List<String> oilList = (List<String>) document.get("oil");
                        if (oilList!= null) {
                            for (String oil : oilList) {
                                oiltype.add(oil);
                            }
                            oil_types.notifyDataSetChanged();
                        }
                    }
                }
            }
        });
    }
    public void get_fuel () {
        db.collection("other").document("fuel_oil").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        List<String> fuelList = (List<String>) document.get("fuel");
                        if (fuelList!= null) {
                            for (String fuel : fuelList) {
                                fueltype.add(fuel);
                            }
                            fuel_types.notifyDataSetChanged();
                        }
                    }
                }
            }
        });
    }
    public void get_team_services () {
        db.collection("other").document("team_services").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        List<String> servicesList = (List<String>) document.get("services");
                        if (servicesList != null) {
                            for (String service : servicesList) {
                                team_service.add(service);
                            }
                            team_service_types.notifyDataSetChanged();
                        }
                    }
                }
            }
        });
    }
}
