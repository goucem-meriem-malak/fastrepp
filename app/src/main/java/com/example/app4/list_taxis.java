package com.example.app4;

import android.content.Intent;
import android.icu.text.DecimalFormat;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app4.data.client;
import com.example.app4.data.taxi;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class list_taxis extends AppCompatActivity implements listener_taxi {

    private FirebaseFirestore db;
    private FirebaseUser user;
    private FirebaseAuth auth;
    private String clientid, requestid;
    private GeoPoint client_location, taxi_location;
    private Map<String, Object> client_address;
    private RecyclerView recyclerView;
    private taxi taxi;
    private ArrayList<taxi> taxis;
    private adapter_taxi adapter_taxi;
    private Button btnhome, btnlist, btnprofile, btngoback, btnhelpcenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_taxis);

        btnhome = findViewById(R.id.home);
        btnlist = findViewById(R.id.list_requests);
        btnprofile = findViewById(R.id.profile);
        btngoback = findViewById(R.id.goback);
        btnhelpcenter = findViewById(R.id.help_center);
        recyclerView = findViewById(R.id.recycler_taxi);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        clientid = user.getUid();
        requestid = getIntent().getStringExtra("requestid");

        taxis = new ArrayList<taxi>();
        adapter_taxi = new adapter_taxi(this, taxis, new listener_taxi() {
            @Override
            public void onItemClicked(String doc_id, taxi items, int position) {
                sendrequest(client_location, doc_id);
                Intent activityChangeIntent = new Intent(list_taxis.this, list_tows.class);
                activityChangeIntent.putExtra("requestid", requestid);
                list_taxis.this.startActivity(activityChangeIntent);
            }

        });
        recyclerView.setAdapter(adapter_taxi);

        get_list_free_taxis();

        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(list_taxis.this, home.class);
                list_taxis.this.startActivity(activityChangeIntent);
            }
        });
        btnlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(list_taxis.this, list_requests.class);
                list_taxis.this.startActivity(activityChangeIntent);
            }
        });
        btnprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(list_taxis.this, profile.class);
                list_taxis.this.startActivity(activityChangeIntent);
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
        btnhelpcenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(list_taxis.this, help_center.class);
                list_taxis.this.startActivity(activityChangeIntent);
            }
        });
    }

    private void get_list_free_taxis() {
        db.collection("client").document(clientid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshott) {
                client client = documentSnapshott.toObject(client.class);
                db.collection("taxi").whereEqualTo("free", true).whereEqualTo("address", client.getLocationaddress())
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                if (error != null) {

                                }
                                for (DocumentChange dc : value.getDocumentChanges()) {
                                    if (dc.getType() == DocumentChange.Type.ADDED) {
                                        taxi = dc.getDocument().toObject(taxi.class);
                                        taxi.setDistance(get_distance(client.getLocation(), taxi.getLocation()));
                                        taxi.setDunit(get_unit(client.getLocation(), taxi.getLocation()));
                                        taxis.add(taxi);
                                    }

                                    adapter_taxi.notifyDataSetChanged();
                                }
                            }
                        });
            }
        });
    }

    private void sendrequest(GeoPoint client_location, String mechanic_id) {
        db.collection("client").document(clientid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshott) {
                client client = documentSnapshott.toObject(client.class);
                db.collection("taxi").whereEqualTo("id", taxi.getId())
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                if (error != null) {
                                    Toast.makeText(list_taxis.this, "There's No Mechanics", Toast.LENGTH_LONG).show();
                                    return;
                                }
                                for (DocumentChange dc : value.getDocumentChanges() ) {
                                    if (dc.getType() == DocumentChange.Type.ADDED) {
                                        taxi.setDistance(get_distance(client.getLocation(), taxi.getLocation()));
                                        taxi.setDunit(get_unit(client.getLocation(), taxi.getLocation()));

                                        HashMap<String, Object> m = new HashMap<String, Object>();

                                        DocumentReference ref = db.collection("request").document(requestid);
                                        m.put("id", requestid);
                                        m.put("client_id", clientid);
                                        m.put("taxi_id", taxi.getId());
                                        m.put("client_location", client.getLocation());
                                        m.put("taxi_location", taxi.getLocation());
                                        m.put("address",client.getAddress());
                                        m.put("distance", taxi.getDistance());
                                        m.put("price", taxi.getDistance() * 200);
                                        m.put("state", "waiting");
                                        ref.update(m);

                                        HashMap<String, Object> n = new HashMap<>();

                                        n.put("free",false);
                                        db.collection("taxi").document(taxi.getId()).update(n);
                                    }

                                    adapter_taxi.notifyDataSetChanged();
                                }
                            }
                        });
            }
        });
    }

    private float get_distance(GeoPoint client_location, GeoPoint taxi_location) {

        Location client_loc = new Location("");
        client_loc.setLatitude(client_location.getLatitude() / 1E6);
        client_loc.setLongitude(client_location.getLongitude() / 1E6);
        Location taxi_loc = new Location("");
        taxi_loc.setLatitude(taxi_location.getLatitude() / 1E6);
        taxi_loc.setLongitude(taxi_location.getLongitude() / 1E6);
        DecimalFormat df = new DecimalFormat("#.##");
        float distanceInMeters = Float.parseFloat(df.format(client_loc.distanceTo(taxi_loc)));
        float distanceInKm = distanceInMeters / 1000;

        if (distanceInMeters>1000){
            return distanceInKm;
        }
        else return distanceInMeters;
    }
    private String get_unit(GeoPoint client_location, GeoPoint mechanic_location) {

        Location client_loc = new Location("");
        client_loc.setLatitude(client_location.getLatitude() / 1E6);
        client_loc.setLongitude(client_location.getLongitude() / 1E6);
        Location mechanic_loc = new Location("");
        mechanic_loc.setLatitude(mechanic_location.getLatitude() / 1E6);
        mechanic_loc.setLongitude(mechanic_location.getLongitude() / 1E6);
        DecimalFormat df = new DecimalFormat("#.##");
        float distanceInMeters = Float.parseFloat(df.format(client_loc.distanceTo(mechanic_loc)));

        if (distanceInMeters>1000){
            return "Km";
        }
        else return "M";
    }


    @Override
    public void onItemClicked(String doc_id, taxi items, int position) {
        Intent activityChangeIntent = new Intent(list_taxis.this, menu.class);
        list_taxis.this.startActivity(activityChangeIntent);
    }
}
