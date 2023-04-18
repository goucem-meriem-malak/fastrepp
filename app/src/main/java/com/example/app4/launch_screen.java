package com.example.app4;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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

public class launch_screen extends AppCompatActivity {
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.l_screen);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser()!=null){
        userid = auth.getCurrentUser().getUid();}

        if (userid != null) {
                    db.collection("worker").document(userid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    workers();
                                } else {
                                    db.collection("client").document(userid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.isSuccessful()) {
                                                DocumentSnapshot document = task.getResult();
                                                if (document.exists()) {
                                                    clients();
                                                } else {
                                                    news();
                                                }
                                            }
                                        }
                                    });
                                }
                            }
                        }
                    });
        } else {
            news();
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ImageView myImageView = findViewById(R.id.main_logo);
                Animation slideUpAnimation = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.slide_up);
                myImageView.startAnimation(slideUpAnimation);
                TextView name = findViewById(R.id.app_name);
                name.setVisibility(View.INVISIBLE);
                Animation fadeinAnimation = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.fade_in);
                name.startAnimation(fadeinAnimation);
                name.setVisibility(View.VISIBLE);
            }
        }, 2000);
    }

    private void workers() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(launch_screen.this, list_requests_worker.class);
                launch_screen.this.startActivity(intent);
                finish();
            }
        }, 3000);
    }
    private void clients() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(launch_screen.this, home.class);
                launch_screen.this.startActivity(intent);
                finish();
            }
        }, 3000);
    }
    private void news() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(launch_screen.this, first_screen.class);
                launch_screen.this.startActivity(intent);
                finish();
            }
        }, 3000);
    }
}