package com.example.app4;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app4.data.get_requests;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class list_requests extends AppCompatActivity{
    private FirebaseFirestore db;
    private FirebaseUser user;
    private FirebaseAuth auth;
    private String clientid;
    private Button btnhome, btngoback, btnlist, btnprofile;
    private RecyclerView recyclerView;
    private ArrayList<com.example.app4.data.get_requests> get_requests;
    private adapter_request adapter_request;
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
        btngoback = findViewById(R.id.goback);
        recyclerView = findViewById(R.id.recyclerr);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        clientid = user.getUid();

        get_requests = new ArrayList<get_requests>();
        adapter_request = new adapter_request(this, get_requests);
        recyclerView.setAdapter(adapter_request);

        get_list_requests();
        
        btnhome.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(list_requests.this, home.class);

                list_requests.this.startActivity(activityChangeIntent);
            }
        });
        btngoback.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
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
        db.collection("request").whereEqualTo("client_id", clientid).orderBy("date", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            return;
                        }
                        for (DocumentChange dc : value.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                get_requests request = dc.getDocument().toObject(get_requests.class);
                                get_requests.add(request);
                            }
                            adapter_request.notifyDataSetChanged();
                        }
                    }
                });
    }

}