package com.example.app4;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.icu.text.DecimalFormat;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.app4.data.client;
import com.example.app4.data.get_mechanics;
import com.example.app4.data.mechanic;
import com.example.app4.data.station;
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

public class list_stations extends AppCompatActivity implements listener_station {

    private FirebaseFirestore db;
    private FirebaseUser user;
    private FirebaseAuth auth;
    private String clientid;
    private GeoPoint client_location, station_location;
    private Map<String, Object> client_address;
    private station stat;
    private RecyclerView recyclerView;
    private ArrayList<com.example.app4.data.station> station;
    private adapter_station adapter_stations;
    private Button btnhome, btnlist, btnprofile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_stations);

        btnhome = findViewById(R.id.home);
        btnlist = findViewById(R.id.list);
        btnprofile = findViewById(R.id.profile);
        recyclerView = findViewById(R.id.recycler_station);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        clientid = user.getUid();

        station = new ArrayList<station>();
        adapter_stations = new adapter_station(this, station, new listener_station() {
            @Override
            public void onItemClicked(String doc_id, station station, int position) {
                sendrequest(client_location, doc_id);
                Intent activityChangeIntent = new Intent(list_stations.this, list_requests.class);
                list_stations.this.startActivity(activityChangeIntent);
            }

        });
        recyclerView.setAdapter(adapter_stations);

        get_list_stations();

        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(list_stations.this, home.class);
                list_stations.this.startActivity(activityChangeIntent);
            }
        });
        btnlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(list_stations.this, list_requests.class);
                list_stations.this.startActivity(activityChangeIntent);
            }
        });
        btnprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(list_stations.this, profile.class);
                list_stations.this.startActivity(activityChangeIntent);
            }
        });
    }

    private void get_list_stations() {
        db.collection("client").document(clientid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                client client = documentSnapshot.toObject(client.class);
                client_location = client.getLocation();
                client_address = (Map<String, Object>) documentSnapshot.get("address");
                db.collection("station").whereEqualTo("available", true).whereEqualTo("address", client_address)
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                if (error != null) {
                                    return;
                                }
                                for (DocumentChange dc : value.getDocumentChanges()) {
                                    if (dc.getType() == DocumentChange.Type.ADDED) {
                                        station.add(dc.getDocument().toObject(station.class));
                                    }
                                    adapter_stations.notifyDataSetChanged();
                                }
                            }
                        });
            }
        });
    }

    private void sendrequest(GeoPoint client_location, String station_id) {
        db.collection("client").document(clientid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                client client = documentSnapshot.toObject(client.class);
                client_address = (Map<String, Object>) documentSnapshot.get("address");
                db.collection("station").whereEqualTo("id", station_id)
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                if (error != null) {
                                    return;
                                }
                                for (DocumentChange dc : value.getDocumentChanges() ) {
                                    if (dc.getType() == DocumentChange.Type.ADDED) {
                                        stat = dc.getDocument().toObject(station.class);
                                        station it = dc.getDocument().toObject(station.class);

                                        float distance = get_distance(client.getLocation(), stat.getLocation());
                                        it.setDistance(distance);
                                        it.setDunit("M");

                                        HashMap<String, Object> m = new HashMap<String, Object>();

                                        DocumentReference ref = db.collection("request").document();
                                        String requestid = ref.getId();
                                        m.put("id", requestid);
                                        m.put("client_id", clientid);
                                        m.put("station_id", station_id);
                                        m.put("client_location", client.getLocation());
                                        m.put("station_location", stat.getLocation());
                                        m.put("date", Calendar.getInstance().getTime());
                                        m.put("type", "fuel");
                                        m.put("distance", get_distance(client_location, stat.getLocation()));
                                        m.put("price", distance * 200);
                                        m.put("state", "ongoing");
                                        ref.set(m);

                                        HashMap n = new HashMap<>();
                                        n.put("available", false);
                                        db.collection("station").document(station_id).update(n);
                                    }

                                    adapter_stations.notifyDataSetChanged();
                                }
                            }
                        });
            }
        });
    }

    private float get_distance(GeoPoint client_location, GeoPoint station_location) {

        Location client_loc = new Location("");
        client_loc.setLatitude(client_location.getLatitude() / 1E6);
        client_loc.setLongitude(client_location.getLongitude() / 1E6);
        Location station_loc = new Location("");
        station_loc.setLatitude(station_location.getLatitude() / 1E6);
        station_loc.setLongitude(station_location.getLongitude() / 1E6);
        DecimalFormat df = new DecimalFormat("#.##");
        float distanceInMeters = Float.parseFloat(df.format(client_loc.distanceTo(station_loc)));
        float distanceInKm = distanceInMeters / 1000;

        if (distanceInMeters>1000){
            return distanceInKm;
        }
        else return distanceInMeters;
    }

    @Override
    public void onItemClicked(String doc_id, station station, int position) {
        Intent activityChangeIntent = new Intent(list_stations.this, find.class);
        list_stations.this.startActivity(activityChangeIntent);
    }
}
