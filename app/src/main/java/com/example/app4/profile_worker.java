package com.example.app4;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.app4.data.client;
import com.example.app4.data.garage;
import com.example.app4.data.mechanic;
import com.example.app4.data.request;
import com.example.app4.data.station;
import com.example.app4.data.tow;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;


public class profile_worker extends AppCompatActivity {
    private static final int REQUEST_CODE_SELECT_IMAGE = 1;
    private static final String TAG = "MyFragment";
    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "my_channel";
    private FirebaseFirestore db;
    private FirebaseUser user;
    private FirebaseAuth auth;
    private FirebaseStorage storage;
    private StorageReference storageRef;
    private TextView fullname;
    private Button btnhome, btnlistrequests, btnprofile, btngoback, btneditprofile, btnseevehicles, btnlogout, btndelete, btnlanguage;
    private ImageView pfp;
    private String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_worker);

        btngoback = findViewById(R.id.goback);
        btnprofile = findViewById(R.id.profile);
        btnlistrequests = findViewById(R.id.list_requests);
        btneditprofile = findViewById(R.id.edit_profile);
        btnlogout = findViewById(R.id.logout);
        btnlanguage = findViewById(R.id.language);
        pfp = findViewById(R.id.pfp);

        fullname = findViewById(R.id.fullname);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        userid = user.getUid();
/*      db.collection("client").document(userid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null && document.exists()) {
                        client client = document.toObject(client.class);
                        if (client!=null){
                            if (client.getLastname() != null){
                                lastname.setText(client.getLastname());
                            } else {
                                lastname.setText("");
                            }
                            if (client.getFirstname() != null){
                                firstname.setText(client.getFirstname());
                            } else {
                                firstname.setText("");
                            }
                            if (client.getPhone() != null){
                                fphone.setText(client.getPhone().substring(0, client.getPhone().length() - 9));
                                phone.setText(client.getPhone().substring(client.getPhone().length() - 9, client.getPhone().length()));

                            } else {
                                fphone.setText("");
                                phone.setText("");
                            }
                            if (client.getEmail() != null) {
                                email.setText(client.getEmail());
                            } else {
                                email.setText("");
                            }
                            if (client.getAddress().getCountry() != null){
                                country.setText(client.getAddress().getCountry());
                            } else {
                                country.setText("");
                            }
                            if (client.getAddress().getCity() != null){
                                city.setText(client.getAddress().getCity());
                            } else {
                                city.setText("");
                            }
                            if (client.getAddress().getState() != null){
                                state.setText(client.getAddress().getState());
                            } else {
                                state.setText("");
                            }
                        }
                    } else {
                        Toast.makeText(profile.this, "Please Try Later", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(profile.this, "Check Your Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

 */
        btnlistrequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(profile_worker.this, list_requests_worker.class);
                profile_worker.this.startActivity(activityChangeIntent);
            }
        });
        btngoback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btneditprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(profile_worker.this, edit_profile.class);
                profile_worker.this.startActivity(activityChangeIntent);
            }
        });
        btnlanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLanguage();
            }
        });
        btnlogout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                auth.signOut();
                Intent intent = new Intent(getApplicationContext(), launch_screen.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        pfp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(profile_worker.this, "Click On Edit Profile", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void changeLanguage() {
        Locale currentLocale = getResources().getConfiguration().locale;

        Locale newLocale = currentLocale.equals(Locale.ENGLISH) ? new Locale("ar") : Locale.ENGLISH;

        Configuration configuration = new Configuration();
        configuration.setLocale(newLocale);

        getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());

        recreate();
    }
}
