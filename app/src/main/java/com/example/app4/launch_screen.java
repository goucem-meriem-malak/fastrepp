package com.example.app4;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app4.todelete.sign_in;
import com.example.app4.todelete.sign_up;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class launch_screen extends AppCompatActivity{
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private Button btnsignup, btnsignin, btnguest;
    private String clientid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launch_screen);


        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        if (user!=null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    db.collection("worker").document(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    db.collection(document.get("type").toString()).document(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.isSuccessful()) {
                                                DocumentSnapshot documentt = task.getResult();
                                                if (documentt.exists()) {

                                                    Toast.makeText(launch_screen.this, "Welcome" + documentt.get("name").toString(), Toast.LENGTH_SHORT).show();
                                                    Intent in = new Intent(launch_screen.this, list_requests_worker.class);
                                                    launch_screen.this.startActivity(in);
                                                    finish();
                                                } else {
                                                    Intent intent = new Intent(launch_screen.this, first_screen.class);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            }
                                        }
                                    });
                                } else {
                                    db.collection("client").document(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.isSuccessful()) {
                                                DocumentSnapshot document = task.getResult();
                                                if (document.exists()) {
                                                    Intent intent = new Intent(launch_screen.this, home.class);
                                                    startActivity(intent);
                                                    finish();
                                                } else {
                                                    Intent intent = new Intent(launch_screen.this, first_screen.class);
                                                    startActivity(intent);
                                                    finish();

                                                }
                                            }
                                        }
                                    });
                                }
                            }
                        }
                    });
            }
        }, 2500);
    } else {
            Intent intent = new Intent(launch_screen.this, first_screen.class);
            startActivity(intent);
            finish();
        }


        btnsignup = findViewById(R.id.signup);
        btnsignin = findViewById(R.id.signin);
        btnguest = findViewById(R.id.continuee);


        btnsignup.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(launch_screen.this, sign_up.class);
                activityChangeIntent.putExtra("clientid", createid());
                startActivity(activityChangeIntent);
            }
        });
        btnsignin.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(launch_screen.this, sign_in.class);
                activityChangeIntent.putExtra("clientid", createid());
                startActivity(activityChangeIntent);
            }
        });
        btnguest.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(launch_screen.this, find.class);
                activityChangeIntent.putExtra("clientid", createid());
                startActivity(activityChangeIntent);
            }
        });
    }

    private String createid() {
        DocumentReference ref = db.collection("client").document();
        HashMap<String, Object> m =new HashMap<String, Object>();
        clientid = ref.getId();
        m.put("id", clientid);
        m.put("signed", false);
        ref.set(m);

        return clientid;
    }

}