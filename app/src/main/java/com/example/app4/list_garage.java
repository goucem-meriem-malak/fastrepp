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
import com.example.app4.data.garage;
import com.example.app4.data.get_mechanics;
import com.example.app4.data.mechanic;
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

public class list_garage extends AppCompatActivity implements listener_garage {

    private FirebaseFirestore db;
    private FirebaseUser user;
    private FirebaseAuth auth;
    private String clientid;
    private GeoPoint client_location, garage_id;
    private Map<String, Object> client_address;
    private com.example.app4.data.garage grg;
    private RecyclerView recyclerView;
    private ArrayList<com.example.app4.data.garage> garage;
    private adapter_garage adapter_garage;
    private Button btnhome, btnlist, btnprofile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_garages);

        btnhome = findViewById(R.id.home);
        btnlist = findViewById(R.id.list);
        btnprofile = findViewById(R.id.profile);
        recyclerView = findViewById(R.id.recycler_garage);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        clientid = user.getUid();

        garage = new ArrayList<garage>();
        adapter_garage = new adapter_garage(this, garage, new listener_garage() {
            @Override
            public void onItemClicked(String doc_id, garage garage, int position) {
                sendrequest(client_location, doc_id);
                Intent activityChangeIntent = new Intent(list_garage.this, list_requests.class);
                list_garage.this.startActivity(activityChangeIntent);
            }

        });
        recyclerView.setAdapter(adapter_garage);

        get_list_available_garages();

        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(list_garage.this, home.class);
                list_garage.this.startActivity(activityChangeIntent);
            }
        });
        btnlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(list_garage.this, list_requests.class);
                list_garage.this.startActivity(activityChangeIntent);
            }
        });
        btnprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(list_garage.this, profile.class);
                list_garage.this.startActivity(activityChangeIntent);
            }
        });
    }

    private void get_list_available_garages() {
        db.collection("client").document(clientid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshott) {
                client client = documentSnapshott.toObject(client.class);
                client_location = client.getLocation();
                client_address = (Map<String, Object>) documentSnapshott.get("address");
                db.collection("garage").whereEqualTo("available", true).whereEqualTo("address", client_address)
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                if (error != null) {
                                    return;
                                }
                                for (DocumentChange dc : value.getDocumentChanges()) {
                                    if (dc.getType() == DocumentChange.Type.ADDED) {
                                        grg = dc.getDocument().toObject(garage.class);
                                        garage it = dc.getDocument().toObject(garage.class);
                                        it.setDistance(get_distance(client_location, grg.getLocation()));
                                        it.setDunit("M");
                                        garage.add(it);
                                    }

                                    adapter_garage.notifyDataSetChanged();
                                }
                            }
                        });
            }
        });
    }

    private void sendrequest(GeoPoint client_location, String garage_id) {
        db.collection("client").document(clientid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshott) {
                client client = documentSnapshott.toObject(client.class);
                db.collection("garage").whereEqualTo("id", garage_id)
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                if (error != null) {
                                    return;
                                }
                                for (DocumentChange dc : value.getDocumentChanges() ) {
                                    if (dc.getType() == DocumentChange.Type.ADDED) {
                                        grg = dc.getDocument().toObject(garage.class);
                                        garage it = dc.getDocument().toObject(garage.class);

                                        float distance = get_distance(client.getLocation(), grg.getLocation());

                                        HashMap<String, Object> m = new HashMap<String, Object>();

                                        DocumentReference ref = db.collection("request").document();
                                        String requestid = ref.getId();
                                        m.put("id", requestid);
                                        m.put("client_id", clientid);
                                        m.put("garage_id", grg.getId());
                                        m.put("client_location", client_location);
                                        m.put("garage_location", grg.getLocation());
                                        m.put("type", "team");
                                        m.put("date", Calendar.getInstance().getTime());
                                        m.put("distance", distance);
                                        m.put("price", distance * 200);
                                        m.put("state", "wait");
                                        ref.set(m);

                                        HashMap n = new HashMap<>();
                                        n.put("available", false);
                                        db.collection("garage").document(grg.getId()).update(n);
                                    }

                                    adapter_garage.notifyDataSetChanged();
                                }
                            }
                        });
            }
        });
    }

    private float get_distance(GeoPoint client_location, GeoPoint garage_location) {

        Location client_loc = new Location("");
        client_loc.setLatitude(client_location.getLatitude() / 1E6);
        client_loc.setLongitude(client_location.getLongitude() / 1E6);
        Location garage_loc = new Location("");
        garage_loc.setLatitude(garage_location.getLatitude() / 1E6);
        garage_loc.setLongitude(garage_location.getLongitude() / 1E6);
        DecimalFormat df = new DecimalFormat("#.##");
        float distanceInMeters = Float.parseFloat(df.format(client_loc.distanceTo(garage_loc)));
        float distanceInKm = distanceInMeters / 1000;

        if (distanceInMeters>1000){
            return distanceInKm;
        }
        else return distanceInMeters;
    }

    @Override
    public void onItemClicked(String doc_id, garage garage, int position) {
        Intent activityChangeIntent = new Intent(list_garage.this, find.class);
        list_garage.this.startActivity(activityChangeIntent);
    }
}
