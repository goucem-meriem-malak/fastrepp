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

public class list_mechanics extends AppCompatActivity implements listener_mechanic {

    private FirebaseFirestore db;
    private FirebaseUser user;
    private FirebaseAuth auth;
    private String clientid;
    private GeoPoint client_location, mechanic_location;
    private Map<String, Object> client_address;
    private mechanic mech;
    private RecyclerView recyclerView;
    private ArrayList<com.example.app4.data.get_mechanics> get_mechanics;
    private adapter_mechanics adapter_mechanics;
    private Button btnhome, btnlist, btnprofile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_mechanics);

        btnhome = findViewById(R.id.home);
        btnlist = findViewById(R.id.list);
        btnprofile = findViewById(R.id.profile);
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        clientid = user.getUid();

        get_mechanics = new ArrayList<get_mechanics>();
        adapter_mechanics = new adapter_mechanics(this, get_mechanics, new listener_mechanic() {
            @Override
            public void onItemClicked(String doc_id, get_mechanics items, int position) {
                sendrequest(client_location, doc_id);
                Intent activityChangeIntent = new Intent(list_mechanics.this, list_requests.class);
                list_mechanics.this.startActivity(activityChangeIntent);
            }

        });
        recyclerView.setAdapter(adapter_mechanics);

        get_list_free_mechanics();

        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(list_mechanics.this, home.class);
                list_mechanics.this.startActivity(activityChangeIntent);
            }
        });
        btnlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(list_mechanics.this, list_requests.class);
                list_mechanics.this.startActivity(activityChangeIntent);
            }
        });
        btnprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(list_mechanics.this, profile.class);
                list_mechanics.this.startActivity(activityChangeIntent);
            }
        });
    }

    private void get_list_free_mechanics() {
        db.collection("client").document(clientid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshott) {
                client client = documentSnapshott.toObject(client.class);
                client_location = client.getLocation();
                client_address = (Map<String, Object>) documentSnapshott.get("address");
                db.collection("mechanic").whereEqualTo("free", true).whereEqualTo("address", client_address)
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                if (error != null) {

                                }
                                for (DocumentChange dc : value.getDocumentChanges()) {
                                    if (dc.getType() == DocumentChange.Type.ADDED) {
                                        mech = dc.getDocument().toObject(mechanic.class);
                                        mechanic_location = mech.getLocation();
                                        client_location = client.getLocation();
                                        get_mechanics it = dc.getDocument().toObject(get_mechanics.class);
                                        it.setDistance(get_distance(client_location, mechanic_location));
                                        it.setDunit("M");
                                        get_mechanics.add(it);
                                    }

                                    adapter_mechanics.notifyDataSetChanged();
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
                client_address = (Map<String, Object>) documentSnapshott.get("address");
                db.collection("mechanic").whereEqualTo("id", mechanic_id)
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                if (error != null) {
                                    Toast.makeText(list_mechanics.this, "There's No Mechanics", Toast.LENGTH_LONG).show();
                                    return;
                                }
                                for (DocumentChange dc : value.getDocumentChanges() ) {
                                    if (dc.getType() == DocumentChange.Type.ADDED) {
                                            mech = dc.getDocument().toObject(mechanic.class);
                                            get_mechanics it = dc.getDocument().toObject(get_mechanics.class);

                                            float distance = get_distance(client.getLocation(), mech.getLocation());
                                            it.setDistance(distance);
                                            it.setDunit("M");

                                            HashMap<String, Object> m = new HashMap<String, Object>();

                                            DocumentReference ref = db.collection("request").document();
                                            String requestid = ref.getId();
                                            m.put("id", requestid);
                                            m.put("client_id", clientid);
                                            m.put("mechanic_id", mech.getId());
                                            m.put("client_location", client.getLocation());
                                            m.put("mechanic_location", mech.getLocation());
                                            m.put("address",client.getAddress());
                                            m.put("type", "mechanic");
                                            m.put("date", Calendar.getInstance().getTime());
                                            m.put("distance", get_distance(client_location, mech.getLocation()));
                                            m.put("price", distance * 200);
                                            m.put("state", "ongoing");
                                            ref.set(m);

                                            HashMap<String, Object> n = new HashMap<>();

                                            n.put("free",false);
                                            db.collection("mechanic").document(mech.getId()).update(n);
                                    }

                                    adapter_mechanics.notifyDataSetChanged();
                                }
                            }
                        });
            }
        });
    }

    private float get_distance(GeoPoint client_location, GeoPoint mechanic_location) {

        Location client_loc = new Location("");
        client_loc.setLatitude(client_location.getLatitude() / 1E6);
        client_loc.setLongitude(client_location.getLongitude() / 1E6);
        Location mechanic_loc = new Location("");
        mechanic_loc.setLatitude(mechanic_location.getLatitude() / 1E6);
        mechanic_loc.setLongitude(mechanic_location.getLongitude() / 1E6);
        DecimalFormat df = new DecimalFormat("#.##");
        float distanceInMeters = Float.parseFloat(df.format(client_loc.distanceTo(mechanic_loc)));
        float distanceInKm = distanceInMeters / 1000;

        if (distanceInMeters>1000){
            return distanceInKm;
        }
        else return distanceInMeters;
    }

    @Override
    public void onItemClicked(String doc_id, get_mechanics items, int position) {
        Intent activityChangeIntent = new Intent(list_mechanics.this, find.class);
        list_mechanics.this.startActivity(activityChangeIntent);
    }
}
