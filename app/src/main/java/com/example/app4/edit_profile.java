package com.example.app4;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app4.data.address;
import com.example.app4.data.client;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;


public class edit_profile extends AppCompatActivity {
    private static final int REQUEST_CODE_SELECT_IMAGE = 1;
    private FirebaseFirestore db;
    private FirebaseUser user;
    private FirebaseAuth auth;
    private FirebaseStorage storage;
    private StorageReference storageRef;
    private EditText firstname, lastname, email, country, city, state, phone, fphone;
    private Button btnhome, btnlistrequests, btnprofile, btneditprofile, btngoback, brnsettings;
    private ImageView pfp;
    private String userid;
    private Uri imagepath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        btngoback = findViewById(R.id.goback);
        btnhome = findViewById(R.id.home);
        btnprofile = findViewById(R.id.profile);
        btnlistrequests = findViewById(R.id.list_requests);
        btneditprofile = findViewById(R.id.edit);
        brnsettings = findViewById(R.id.settings);
        pfp = findViewById(R.id.pfp);

        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        fphone = findViewById(R.id.fphone);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        country = findViewById(R.id.country);
        city = findViewById(R.id.city);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        userid = user.getUid();
/*
        db.collection("worker").document(userid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        btnhome.setVisibility(View.GONE);
                        btnlistrequests.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                Intent activityChangeIntent = new Intent(profile.this, list_requests_worker.class);
                                profile.this.startActivity(activityChangeIntent);
                                finish();
                            }
                        });
                        String type = document.get("type").toString();
                        if (type.equals("mechanic")) {
                            db.collection("mechanic").document(userid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {
                                            mechanic mechanic = document.toObject(mechanic.class);
                                            if (mechanic.getFirstname() == null) {
                                                firstname.setText("");
                                            } else {
                                                firstname.setText(mechanic.getFirstname());
                                            }
                                            if (mechanic.getLastname() == null) {
                                                lastname.setText("");
                                            } else {
                                                lastname.setText(mechanic.getLastname());
                                            }
                                            if (mechanic.getPhone() == null) {
                                                fphone.setText("");
                                                phone.setText("");
                                            } else {
                                                fphone.setText(mechanic.getPhone().substring(0, mechanic.getPhone().length() - 9));
                                                phone.setText(mechanic.getPhone().substring(mechanic.getPhone().length() - 9, mechanic.getPhone().length()));
                                            }
                                            if (mechanic.getEmail() == null) {
                                                email.setText("");
                                            } else {
                                                email.setText(mechanic.getEmail());
                                            }
                                            if(((Map<String, Object>) document.getData().get("address"))==null){
                                                country.setText("");
                                                city.setText("");
                                            } else {
                                                if (((Map<String, Object>) document.getData().get("address")).get("country") == null) {
                                                    country.setText("");
                                                } else {
                                                    country.setText(((Map<String, Object>) document.getData().get("address")).get("country").toString());
                                                }
                                                //state.setText(((Map<String, Object>)documentSnapshott.getData().get("address")).get("state").toString());
                                                if (((Map<String, Object>) document.getData().get("address")).get("city") == null) {
                                                    city.setText("");
                                                } else {
                                                    city.setText(((Map<String, Object>) document.getData().get("address")).get("city").toString());
                                                }
                                                //state.setText(((Map<String, Object>)documentSnapshott.getData().get("address")).get("state").toString());
                                            }
                                        }
                                    }
                                }
                            });
                        } else if (type.equals("station")) {
                            db.collection("station").document(userid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {

                                            station station = document.toObject(station.class);
                                            if (station.getName() == null) {
                                                firstname.setText("");
                                            } else {
                                                firstname.setText(station.getName());
                                            }
                                            if (station.getPhone() == null) {
                                                fphone.setText("");
                                                phone.setText("");
                                            } else {
                                                fphone.setText(station.getPhone().substring(0, station.getPhone().length() - 9));
                                                phone.setText(station.getPhone().substring(station.getPhone().length() - 9, station.getPhone().length()));
                                            }
                                            if (((Map<String, Object>) document.getData().get("address")).get("country") == null) {
                                                country.setText("");
                                            } else {
                                                country.setText(((Map<String, Object>) document.getData().get("address")).get("country").toString());
                                            }
                                            //state.setText(((Map<String, Object>)documentSnapshott.getData().get("address")).get("state").toString());
                                            if (((Map<String, Object>) document.getData().get("address")).get("city") == null) {
                                                city.setText("");
                                            } else {
                                                city.setText(((Map<String, Object>) document.getData().get("address")).get("city").toString());
                                            }
                                            //state.setText(((Map<String, Object>)documentSnapshott.getData().get("address")).get("state").toString());
                                        }
                                    }
                                }

                            });
                        } else if (type.equals("garage")) {
                            db.collection("garage").document(userid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {
                                            garage garage = document.toObject(garage.class);
                                            if (garage.getName() == null) {
                                                firstname.setText("");
                                            } else {
                                                firstname.setText(garage.getName());
                                            }
                                            if (garage.getPhone() == null) {
                                                fphone.setText("");
                                                phone.setText("");
                                            } else {
                                                fphone.setText(garage.getPhone().substring(0, garage.getPhone().length() - 9));
                                                phone.setText(garage.getPhone().substring(garage.getPhone().length() - 9, garage.getPhone().length()));
                                            }
                                            if (((Map<String, Object>) document.getData().get("address")).get("country") == null) {
                                                country.setText("");
                                            } else {
                                                country.setText(((Map<String, Object>) document.getData().get("address")).get("country").toString());
                                            }
                                            //state.setText(((Map<String, Object>)documentSnapshott.getData().get("address")).get("state").toString());
                                            if (((Map<String, Object>) document.getData().get("address")).get("city") == null) {
                                                city.setText("");
                                            } else {
                                                city.setText(((Map<String, Object>) document.getData().get("address")).get("city").toString());
                                            }
                                            //state.setText(((Map<String, Object>)documentSnapshott.getData().get("address")).get("state").toString());
                                        }
                                    }
                                }

                            });
                        } else if (type.equals("towtruck")) {
                            db.collection("towtruck").document(userid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {
                                            tow tow_truck = document.toObject(tow.class);
                                            if (tow_truck.getFirstname() == null) {
                                                firstname.setText("");
                                            } else {
                                                firstname.setText(tow_truck.getFirstname());
                                            }
                                            if (tow_truck.getLastname() == null) {
                                                lastname.setText("");
                                            } else {
                                                lastname.setText(tow_truck.getFirstname());
                                            }
                                            if (tow_truck.getPhone() == null) {
                                                fphone.setText("");
                                                phone.setText("");
                                            } else {
                                                fphone.setText(tow_truck.getPhone().substring(0, tow_truck.getPhone().length() - 9));
                                                phone.setText(tow_truck.getPhone().substring(tow_truck.getPhone().length() - 9, tow_truck.getPhone().length()));
                                            }
                                            if (((Map<String, Object>) document.getData().get("address")).get("country") == null) {
                                                country.setText("");
                                            } else {
                                                country.setText(((Map<String, Object>) document.getData().get("address")).get("country").toString());
                                            }
                                            //state.setText(((Map<String, Object>)documentSnapshott.getData().get("address")).get("state").toString());
                                            if (((Map<String, Object>) document.getData().get("address")).get("city") == null) {
                                                city.setText("");
                                            } else {
                                                city.setText(((Map<String, Object>) document.getData().get("address")).get("city").toString());
                                            }
                                            //state.setText(((Map<String, Object>)documentSnapshott.getData().get("address")).get("state").toString());
                                        }
                                    }
                                }

                            });
                        }
                    } else {
                        db.collection("client").document(userid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        client client = document.toObject(client.class);
                                        if (client.getFirstname() == null) {
                                            firstname.setText("");
                                        } else {
                                            firstname.setText(client.getFirstname());
                                        }
                                        if (client.getLastname() == null) {
                                            lastname.setText("");
                                        } else {
                                            lastname.setText(client.getLastname());
                                        }
                                        if (client.getPhone() == null) {
                                            fphone.setText("");
                                            phone.setText("");
                                        } else {
                                            fphone.setText(client.getPhone().substring(0, client.getPhone().length() - 9));
                                            phone.setText(client.getPhone().substring(client.getPhone().length() - 9, client.getPhone().length()));
                                        }
                                        if (client.getEmail() == null) {
                                            email.setText("");
                                        } else {
                                            email.setText(client.getEmail());
                                        }
                                        if (((Map<String, Object>) document.getData().get("address")).get("country") == null) {
                                            country.setText("");
                                        } else {
                                            country.setText(((Map<String, Object>) document.getData().get("address")).get("country").toString());
                                        }
                                        //state.setText(((Map<String, Object>)documentSnapshott.getData().get("address")).get("state").toString());
                                        if (((Map<String, Object>) document.getData().get("address")).get("city") == null) {
                                            city.setText("");
                                        } else {
                                            city.setText(((Map<String, Object>) document.getData().get("address")).get("city").toString());
                                        }
                                        //state.setText(((Map<String, Object>)documentSnapshott.getData().get("address")).get("state").toString());

                                    }
                                } else {
                                    Log.d(TAG, "Failed to get document.", task.getException());
                                }
                            }
                        });
                    }
                } else {
                    Log.d(TAG, "Failed to get document.", task.getException());
                }
            }
        });
*/
        getclientinfo();
        btnhome.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(edit_profile.this, home.class);
                edit_profile.this.startActivity(activityChangeIntent);
            }
        });
        brnsettings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(edit_profile.this, brnsettings);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_language: {
                                String languageCode = "ar";
                                Locale locale = new Locale(languageCode);
                                Configuration config = new Configuration(getResources().getConfiguration());
                                config.setLocale(locale);
                                getResources().updateConfiguration(config, getResources().getDisplayMetrics());
                                Intent intent = getIntent();
                                finish();
                                startActivity(intent);
                                return true;
                            }
                            case R.id.menu_logout: {
                                auth.signOut();
                                Intent activityChangeIntent = new Intent(edit_profile.this, launch_screen.class);
                                edit_profile.this.startActivity(activityChangeIntent);
                                finish();
                                return true;
                            }
                            case R.id.menu_help_center: {
                                Intent activityChangeIntent = new Intent(edit_profile.this, help_center.class);
                                edit_profile.this.startActivity(activityChangeIntent);
                                return true;
                            }
                            default:
                                return false;
                        }
                    }
                });
                popupMenu.show();
            }
        });
        btneditprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btneditprofile.getText().equals("Edit")) {
                    firstname.setEnabled(true);
                    lastname.setEnabled(true);
                    country.setEnabled(true);
                    city.setEnabled(true);
                    //    state.setEnabled(true);
                    email.setEnabled(true);
                    btneditprofile.setText("Save");
                } else {
                    Map<String, Object> update = new HashMap<>();
                    Map<String, Object> addresss = new HashMap<>();
                    if (!firstname.getText().toString().isEmpty()) {
                        update.put("firstname", firstname.getText().toString().trim());
                    }
                    if (!lastname.getText().toString().isEmpty()) {
                        update.put("lastname", lastname.getText().toString().trim());
                    }
                    if (!email.getText().toString().isEmpty()) {
                        Pattern rfc2822 = Pattern.compile(
                                "^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$");
                        if (!rfc2822.matcher(email.getText().toString()).matches() && !email.getText().toString().isEmpty()) {
                            Toast.makeText(edit_profile.this, "Email invalide", Toast.LENGTH_LONG).show();
                            email.setFocusable(true);
                        } else {
                            update.put("email", email.getText().toString().trim());
                        }
                    }
                    if (!country.getText().toString().isEmpty()) {
                        addresss.put("country", country.getText().toString().trim());
                    }
                    if (!city.getText().toString().isEmpty()) {
                        addresss.put("city", city.getText().toString().trim());
                    }
               /*     if (state.getText() != null){
                        addresss.put("state", state.getText().toString().trim());
                    }*/
                    if (!addresss.isEmpty()) {
                        update.put("address", addresss);
                    }
                    db.collection("client").document(userid).update(update);
                    btneditprofile.setText("Edit");
                    firstname.setEnabled(false);
                    lastname.setEnabled(false);
                    country.setEnabled(false);
                    city.setEnabled(false);
                    //    state.setEnabled(false);
                    email.setEnabled(false);
                }
            }
        });
        btnlistrequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(edit_profile.this, list_requests.class);
                edit_profile.this.startActivity(activityChangeIntent);
            }
        });
        btngoback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
  /*      btneditvehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(edit_profile.this, client_vehicles.class);
                edit_profile.this.startActivity(activityChangeIntent);
     /*           List<veh> carInfoList = new ArrayList<>();
                Query query = db.collection("client").document(userid).collection("vehicle");
                query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String type = document.getString("type");
                                String mark = document.getString("mark");
                                String model = document.getString("model");
                                veh carInfo = new veh(type, mark, model);
                                carInfoList.add(carInfo);
                            }
                        } else {
                            Toast.makeText(profile.this, "Erooor", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

   */
        pfp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoIntent = new Intent(Intent.ACTION_PICK);
                photoIntent.setType("image/*");
                startActivityForResult(photoIntent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1 && resultCode==RESULT_OK && data != null){
            imagepath = data.getData();
            try {
                imageinimageview();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void imageinimageview() throws IOException {
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagepath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        pfp.setImageBitmap(bitmap);
    }
    private void uploadimage(){
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        progressDialog.show();

        FirebaseStorage.getInstance().getReference("image/"+ UUID.randomUUID().toString()).putFile(imagepath).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()){
                    Toast.makeText(edit_profile.this, "donee", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(edit_profile.this, "cant be uploaded now", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        });
    }
    private void getclientinfo(){
        db.collection("client").document(userid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null && document.exists()) {
                        client client = document.toObject(client.class);
                        if (client != null) {
                            if (client.getLastname() != null) {
                                lastname.setText(client.getLastname());
                            }
                            if (client.getFirstname() != null) {
                                firstname.setText(client.getFirstname());
                            }
                            if (client.getPhone() != null) {
                                fphone.setText(client.getPhone().substring(0, client.getPhone().length() - 9));
                                phone.setText(client.getPhone().substring(client.getPhone().length() - 9, client.getPhone().length()));
                            }
                            if (client.getEmail() != null) {
                                email.setText(client.getEmail());
                            }
                            if (client.getAddress() != null) {
                                address address = (address) client.getAddress();
                                if (address.getCountry() != null) {
                                    country.setText(address.getCountry());
                                }
                                if (address.getCity() != null) {
                                    city.setText(address.getCity());
                                }
                                if (address.getState() != null) {
                                    //    state.setText(address.getState());
                                }
                            }
                        }
                    } else {
                        Toast.makeText(edit_profile.this, "Please Try Later", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(edit_profile.this, "Check Your Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
