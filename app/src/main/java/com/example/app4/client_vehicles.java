package com.example.app4;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app4.data.client;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class client_vehicles extends AppCompatActivity implements listener_vehicle {

    private FirebaseFirestore db;
    private FirebaseUser user;
    private FirebaseAuth auth;
    private String clientid, vehicleid;
    private RecyclerView recyclerView;
    private com.example.app4.data.vehicle vehicle;
    private ArrayList<com.example.app4.data.vehicle> vehicles;
    private adapter_vehicle adapter_vehicle;
    private Button btnhome, btnlist, btnprofile, btngoback, btnhelpcenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vehicule_info);

        btnhome = findViewById(R.id.home);
        btnlist = findViewById(R.id.list_requests);
        btnprofile = findViewById(R.id.profile);
        btngoback = findViewById(R.id.goback);
        btnhelpcenter = findViewById(R.id.settings);
        recyclerView = findViewById(R.id.recycler_vehicle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        clientid = user.getUid();
        vehicleid = getIntent().getStringExtra("requestid");

        vehicles = new ArrayList<com.example.app4.data.vehicle>();
        adapter_vehicle = new adapter_vehicle(this, vehicles, new listener_vehicle() {
            @Override
            public void onItemClicked(String doc_id, com.example.app4.data.vehicle items, int position) {
                sendrequest(doc_id);
                Intent activityChangeIntent = new Intent(client_vehicles.this, profile.class);
                client_vehicles.this.startActivity(activityChangeIntent);
            }
        });
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter_vehicle);

        get_list_vehicles();

        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(client_vehicles.this, home.class);
                client_vehicles.this.startActivity(activityChangeIntent);
            }
        });
        btnlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(client_vehicles.this, list_requests.class);
                client_vehicles.this.startActivity(activityChangeIntent);
            }
        });
        btnprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(client_vehicles.this, profile.class);
                client_vehicles.this.startActivity(activityChangeIntent);
            }
        });
        btngoback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    finish();
            }
        });
        btnhelpcenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(client_vehicles.this, help_center.class);
                client_vehicles.this.startActivity(activityChangeIntent);
            }
        });
    }

    private void get_list_vehicles() {
        Query query = db.collection("client").document(clientid).collection("vehicles").orderBy("matriculation", Query.Direction.ASCENDING);
        query.get().addOnSuccessListener(queryDocumentSnapshots -> {
            ArrayList<com.example.app4.data.vehicle> vehicles = new ArrayList<>();
            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                com.example.app4.data.vehicle vehicle = document.toObject(com.example.app4.data.vehicle.class);
                vehicles.add(vehicle);
            }
            adapter_vehicle.setVehicles(vehicles);
        });

    }

    private void sendrequest(String doc_id) {
        db.collection("client").document(clientid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshott) {
                client client = documentSnapshott.toObject(client.class);
                db.collection("vehicle").whereEqualTo("id", vehicleid)
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                if (error != null) {
                                    Toast.makeText(client_vehicles.this, "There's No Mechanics", Toast.LENGTH_LONG).show();
                                    return;
                                }
                                for (DocumentChange dc : value.getDocumentChanges() ) {
                                    if (dc.getType() == DocumentChange.Type.ADDED) {

                                    }

                                    adapter_vehicle.notifyDataSetChanged();
                                }
                            }
                        });
            }
        });
    }
    @Override
    public void onItemClicked(String doc_id, com.example.app4.data.vehicle items, int position) {
        Intent activityChangeIntent = new Intent(client_vehicles.this, menu.class);
        client_vehicles.this.startActivity(activityChangeIntent);
    }
}