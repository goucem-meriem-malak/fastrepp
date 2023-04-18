package com.example.app4;


import static android.content.ContentValues.TAG;

import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app4.data.request;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class list_requests_worker extends AppCompatActivity{
    private FirebaseFirestore db;
    private FirebaseUser user;
    private FirebaseAuth auth;
    private String clientid;
    private Button btnhome, btngoback, btnhelpcenter, btnlist, btnprofile;
    private RecyclerView recyclerView;
    private ArrayList<request> get_requests;
    private com.example.app4.adapter_requests_worker adapter_requests_worker;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_requests_worker);

        pd = new ProgressDialog(this);
        //   pd.setCancelable(false);
        // pd.setMessage("Fetchingdata");
        //  pd.show();

        btnlist = findViewById(R.id.list_requests);
        btnprofile = findViewById(R.id.profile);
        recyclerView = findViewById(R.id.recycler_request_worker);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        clientid = user.getUid();

        get_requests = new ArrayList<request>();
        adapter_requests_worker = new adapter_requests_worker(this, get_requests, new listener_request_worker() {
            @Override
            public void onItemClicked(String document_id, request request, int position) {
                Intent intent = new Intent(list_requests_worker.this, request_worker.class);
                list_requests_worker.this.startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter_requests_worker);

        get_list_requests();
        btnprofile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
              //  FirebaseAuth.getInstance().signOut();
                Intent activityChangeIntent = new Intent(list_requests_worker.this, profile_worker.class);
                list_requests_worker.this.startActivity(activityChangeIntent);
                finish();
            }
        });
        notification();
    }

    private void notification() {
        db.collection("request").whereEqualTo("mechanicid",user.getUid()).orderBy("date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }

                for (DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()) {
                    if (dc.getType() == DocumentChange.Type.ADDED) {
                        DocumentSnapshot documentSnapshot = dc.getDocument();
                        String documentId = documentSnapshot.getId();
                        String documentName = documentSnapshot.getString("name");

                        // Send notification to user
                        sendNotification(documentName + " document created with ID: " + documentId);
                    }
                }
            }
        });

    }

    private void sendNotification(String message) {
     /*   Intent intent = new Intent(this, list_requests_worker.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("New Document Created")
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(notificationId, builder.build());*/
    }

    private void get_list_requests() {
        db.collection("request").whereEqualTo("client_id", clientid).addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            return;
                        }
                        for (DocumentChange dc : value.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                request request = dc.getDocument().toObject(request.class);
                                get_requests.add(request);
                            }
                            adapter_requests_worker.notifyDataSetChanged();
                        }
                    }
                });
    }

}