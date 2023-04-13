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
import com.example.app4.data.tow;
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
import java.util.HashMap;
import java.util.Map;

public class list_tows extends AppCompatActivity implements listener_tow {

    private FirebaseFirestore db;
    private FirebaseUser user;
    private FirebaseAuth auth;
    private String clientid, requestid;
    private RecyclerView recyclerView;
    private GeoPoint client_location;
    private tow tow;
    private ArrayList<tow> tows;
    private adapter_tow adapter_tow;
    private Button btnhome, btnlist, btnprofile, btngoback, btnhelpcenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_tows);

        btnhome = findViewById(R.id.home);
        btnlist = findViewById(R.id.list_requests);
        btnprofile = findViewById(R.id.profile);
        btngoback = findViewById(R.id.goback);
        btnhelpcenter = findViewById(R.id.help_center);
        recyclerView = findViewById(R.id.recycler_tow);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        clientid = user.getUid();
        requestid = getIntent().getStringExtra("requestid");
        Toast.makeText(this, requestid, Toast.LENGTH_SHORT).show();

        tows = new ArrayList<tow>();
        adapter_tow = new adapter_tow(this, tows, new list_tows() {
            @Override
            public void onItemClicked(String doc_id, tow items, int position) {
                sendrequest(doc_id);
                Intent activityChangeIntent = new Intent(list_tows.this, list_requests.class);
                activityChangeIntent.putExtra("requestid", requestid);
                list_tows.this.startActivity(activityChangeIntent);
            }

        });
        recyclerView.setAdapter(adapter_tow);

        get_list_free_tows();

        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(list_tows.this, home.class);
                list_tows.this.startActivity(activityChangeIntent);
            }
        });
        btnlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(list_tows.this, list_requests.class);
                list_tows.this.startActivity(activityChangeIntent);
            }
        });
        btnprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(list_tows.this, profile.class);
                list_tows.this.startActivity(activityChangeIntent);
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
                Intent activityChangeIntent = new Intent(list_tows.this, help_center.class);
                list_tows.this.startActivity(activityChangeIntent);
            }
        });
    }

    private void get_list_free_tows() {
        db.collection("client").document(clientid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshott) {
                client client = documentSnapshott.toObject(client.class);
                client_location = client.getLocation();
                db.collection("tow").whereEqualTo("free", true).whereEqualTo("address", client.getLocationaddress())
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                if (error != null) {

                                }
                                for (DocumentChange dc : value.getDocumentChanges()) {
                                    if (dc.getType() == DocumentChange.Type.ADDED) {
                                        tow = dc.getDocument().toObject(tow.class);
                                        tow.setDistance(get_distance(client.getLocation(), tow.getLocation()));
                                        tow.setDunit(get_unit(client.getLocation(), tow.getLocation()));
                                        tows.add(tow);
                                    }

                                    adapter_tow.notifyDataSetChanged();
                                }
                            }
                        });
                }
            });
    }

    private void sendrequest(String towid) {
        db.collection("client").document(clientid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshott) {
                client client = documentSnapshott.toObject(client.class);
                db.collection("tow").whereEqualTo("id", tow.getId())
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                if (error != null) {
                                    Toast.makeText(list_tows.this, "There's No Mechanics", Toast.LENGTH_LONG).show();
                                    return;
                                }
                                for (DocumentChange dc : value.getDocumentChanges() ) {
                                    if (dc.getType() == DocumentChange.Type.ADDED) {
                                        tow = dc.getDocument().toObject(tow.class);
                                        tow.setDistance(get_distance(client.getLocation(), tow.getLocation()));
                                        tow.setDunit(get_unit(client.getLocation(), tow.getLocation()));

                                        HashMap<String, Object> m = new HashMap<String, Object>();

                                        DocumentReference ref = db.collection("request").document(requestid);
                                        m.put("id", requestid);
                                        m.put("client_id", clientid);
                                        m.put("taxi_id", tow.getId());
                                        m.put("client_location", client.getLocation());
                                        m.put("taxi_location", tow.getLocation());
                                        m.put("address",client.getLocationaddress());
                                        m.put("distance", get_distance(client.getLocation(), tow.getLocation()));
                                        m.put("price", tow.getDistance() * 200);
                                        m.put("state", "waiting");
                                        ref.update(m);

                                        HashMap<String, Object> n = new HashMap<>();

                                        n.put("free",false);
                                        db.collection("tow").document(tow.getId()).update(n);
                                    }

                                    adapter_tow.notifyDataSetChanged();
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
    public void onItemClicked(String doc_id, tow items, int position) {
        Intent activityChangeIntent = new Intent(list_tows.this, menu.class);
        list_tows.this.startActivity(activityChangeIntent);
    }
}