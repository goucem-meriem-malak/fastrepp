package com.example.app4;


import android.app.ProgressDialog;
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
import com.example.app4.data.get_requests;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class list_requests extends AppCompatActivity{
    private FirebaseFirestore db;
    private FirebaseUser user;
    private FirebaseAuth auth;
    private String clientid;
    private Button btnhome, btnlist, btnprofile;
    private RecyclerView recyclerView;
    private ArrayList<com.example.app4.data.get_requests> get_requests;
    private get_requests_adapter get_requests_adapter;
    private ProgressDialog pd;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_requests);
        
        pd = new ProgressDialog(this);
     //   pd.setCancelable(false);
       // pd.setMessage("Fetchingdata");
      //  pd.show();
        
        btnhome = findViewById(R.id.home);
        btnlist = findViewById(R.id.list);
        btnprofile = findViewById(R.id.profile);
        recyclerView = findViewById(R.id.recyclerr);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        clientid = user.getUid();

        get_requests = new ArrayList<get_requests>();
        get_requests_adapter = new get_requests_adapter(this, get_requests);
        recyclerView.setAdapter(get_requests_adapter);

        get_list_requests();
        
        btnhome.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(list_requests.this, home.class);

                list_requests.this.startActivity(activityChangeIntent);
            }
        });
        btnprofile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(list_requests.this, profile.class);

                list_requests.this.startActivity(activityChangeIntent);
            }
        });
    }

    private void get_list_requests() {
        Toast.makeText(list_requests.this, clientid, Toast.LENGTH_LONG).show();
        db.collection("request").whereEqualTo("client_id", clientid)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            return;
                        }
                        for (DocumentChange dc : value.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                get_requests request = dc.getDocument().toObject(get_requests.class);
                                /*
                                request.setId(dc.getDocument().getData().get("id").toString());
                                request.setClient_id(dc.getDocument().getData().get("client_id").toString());
                                request.setMechanic_id(dc.getDocument().getData().get("mechanic_id").toString());
                                request.setState(dc.getDocument().getData().get("state").toString());
                                request.setType(dc.getDocument().getData().get("type").toString());
                                request.setClient_location((GeoPoint) dc.getDocument().getData().get("client_location"));
                                request.setMechanic_location((GeoPoint) dc.getDocument().getData().get("mechanic_location"));
                                request.setPrice(Float.parseFloat(dc.getDocument().getData().get("price").toString()));
                                request.setDistance(Float.parseFloat(dc.getDocument().getData().get("distance").toString()));
                                */
                                get_requests.add(request);
                            }
                            get_requests_adapter.notifyDataSetChanged();
                        }
                    }
                });
    }

}