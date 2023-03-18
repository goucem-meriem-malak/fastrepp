package com.example.app4.mechanic_services;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app4.R;
import com.example.app4.all;
import com.example.app4.data.client;
import com.example.app4.data.mechanic;
import com.example.app4.item;
import com.example.app4.myadapter;
import com.example.app4.others.home;
import com.example.app4.others.profile;
import com.example.app4.selectlistener;
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
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class mechanics_list extends AppCompatActivity implements selectlistener {

    private FirebaseFirestore db;
    private FirebaseUser user;
    private FirebaseAuth auth;
    private String clientid, mechanicid="wxIV8sS5pD0rw9MrXxmA", client_addy;
    private GeoPoint client_location, mechanic_location;
    private Map<String, Object> client_address;
    private ListView lv;
    private EditText dt,dtt;
    private mechanic mech;
    private TextView tv;
    private RecyclerView recyclerView;
    private  ArrayList<item> items;
    private com.example.app4.myadapter myadapter;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all);

        pd = new ProgressDialog(this);
        pd.setCancelable(false);
        pd.setMessage("Fetchingdata");
        pd.show();

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        clientid = user.getUid();

        items = new ArrayList<item>();
        myadapter= new myadapter(this, items, new selectlistener() {
            @Override
            public void onItemClicked(item items) {
                Intent activityChangeIntent = new Intent(mechanics_list.this, profile.class);
                mechanics_list.this.startActivity(activityChangeIntent);
            }
        });
        recyclerView.setAdapter(myadapter);

        get_list_free_mechanics();
    }

    private void get_list_free_mechanics() {
        db.collection("client").document(clientid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshott) {
                client client = documentSnapshott.toObject(client.class);
                client_location = client.getLocation();
                client_address = (Map<String, Object>) documentSnapshott.get("address");
                final String[] dataa = {""};
                db.collection("mechanic").whereEqualTo("free", true).whereEqualTo("address", client_address)
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                if (error != null) {
                                    if (pd.isShowing()) {
                                        pd.dismiss();
                                    }
                                    return;
                                }
                                for (DocumentChange dc : value.getDocumentChanges()) {
                                    if (dc.getType() == DocumentChange.Type.ADDED) {
                                        mech = dc.getDocument().toObject(mechanic.class);
                                        mechanic_location = mech.getLocation();
                                        Location client_loc = new Location("");
                                        client_loc.setLatitude(client_loc.getLatitude() / 1E6);
                                        client_loc.setLongitude(client_loc.getLongitude() / 1E6);
                                        Location mech_loc = new Location("");
                                        mech_loc.setLatitude(mechanic_location.getLatitude() / 1E6);
                                        mech_loc.setLongitude(mechanic_location.getLongitude() / 1E6);

                                        float distance = get_distance(client_loc, mech_loc);

                                        items.add(dc.getDocument().toObject(item.class));
                                    }

                                    myadapter.notifyDataSetChanged();
                                    if (pd.isShowing()) {
                                        pd.dismiss();
                                    }
                                }
                            }
                        });
            }
        });
    }

    private void sendrequest(GeoPoint client_location, GeoPoint mechanic_location) {
        HashMap<String, Object> m = new HashMap<String, Object>();

        DocumentReference ref = db.collection("request").document();
        String requestid = ref.getId();
        m.put("requestid", requestid);
        m.put("client_id", clientid);
        m.put("mechanic_id", mechanicid);
        m.put("client_location", client_location);
        m.put("mechanic_location", mechanic_location);
        m.put("date", Calendar.getInstance().getTime());
        m.put("confirm", false);
        ref.set(m);
    }

    private float get_distance(Location client_location, Location mechanic_location) {
        float distanceInMeters = client_location.distanceTo(mechanic_location);
        float distanceInKm = distanceInMeters / 1000;

        if (distanceInMeters>1000){
            return distanceInKm;
        }
        else return distanceInMeters;
    }

    @Override
    public void onItemClicked(item items) {Intent activityChangeIntent = new Intent(mechanics_list.this, home.class);
            mechanics_list.this.startActivity(activityChangeIntent);
    }
}
