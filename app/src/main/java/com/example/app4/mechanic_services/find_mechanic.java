package com.example.app4.mechanic_services;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.app4.R;
import com.firebase.geofire.GeoFireUtils;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQueryBounds;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class find_mechanic extends FragmentActivity implements OnMapReadyCallback {

    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private LatLng latLng;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private Marker marker;
    private Date currentTime;
    private FirebaseFirestore db;
    private EditText userlocation, mechaniclocation;
    private Button btnrequest;
    private String clientid, mechanicid, requestid, address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_mechanic);

        userlocation = findViewById(R.id.userlocation);
        mechaniclocation = findViewById(R.id.mechaniclocation);
        btnrequest = findViewById(R.id.request);
        db = FirebaseFirestore.getInstance();

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        }

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();

                Geocoder geocoder = new Geocoder(getApplicationContext());
                try {
                    List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                    address = addresses.get(0).getLocality() + ":";
                    address += addresses.get(0).getCountryName();

                    userlocation.setText(address);

                    latLng = new LatLng(latitude, longitude);

                    mechaniclocation.setText(address);

                    if (marker != null) {
                        marker.remove();
                    }
                    marker = mMap.addMarker(new MarkerOptions().position(latLng).title(address));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        btnrequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getmechanic();
                sendrequest();
            }
        });
    }

    private void sendrequest() {
        HashMap<String, Object> m = new HashMap<String, Object>();
        Intent intent = getIntent();
        clientid = intent.getStringExtra("clientid");

        DocumentReference ref = db.collection("request").document();
        requestid = ref.getId();

        m.put("requestid", requestid);
        m.put("clientid", clientid);
        m.put("mechanicid", mechanicid);
        m.put("clientlocation", latLng);
        m.put("mechanic", mechaniclocation.getText().toString().trim());
        m.put("date", currentTime = Calendar.getInstance().getTime());
        m.put("confirm", false);
        ref.set(m);

    }

    private void getmechanic() {
        Query query = db.collection("mechanic").whereEqualTo("free", true);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<DocumentSnapshot> documentSnapshots = task.getResult().getDocuments();
                    for (DocumentSnapshot documentSnapshot : documentSnapshots) {
                        Toast.makeText(getApplicationContext(),documentSnapshot.getId() + " => " + documentSnapshot.getData(), Toast.LENGTH_LONG).show();
                    }
                } else {

                    Toast.makeText(getApplicationContext(),"no mechanic", Toast.LENGTH_LONG).show();
                }
            }
        });
        /*
        * // Initialize Firebase
FirebaseApp.initializeApp(context);

// Get the Firebase Cloud Messaging instance
FirebaseMessaging firebaseMessaging = FirebaseMessaging.getInstance();

// Set the message payload
Map<String, String> messagePayload = new HashMap<>();
messagePayload.put("title", "New Request");
messagePayload.put("body", "You have received a new request. Would you like to accept or refuse?");

// Set the Firebase ID of the user to send the notification to
String userId = "user_firebase_id";

// Set the data payload
Map<String, String> dataPayload = new HashMap<>();
dataPayload.put("request_id", "request_firebase_id");

// Create the message
Message message = Message.builder()
        .putAllData(messagePayload)
        .putAllData(dataPayload)
        .setToken(userId)
        .build();

// Send the message
String response = firebaseMessaging.send(message);

*/
       /*
        GeoPoint center = new GeoPoint(latLng.latitude,latLng.longitude);

        double radius = 10;

        Query query = db.collection("mechanic").whereNear(address, center).limit(50);

// Retrieve the documents
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<DocumentSnapshot> documentSnapshots = task.getResult().getDocuments();
                    for (DocumentSnapshot documentSnapshot : documentSnapshots) {
                        Log.d(TAG, documentSnapshot.getId() + " => " + documentSnapshot.getData());
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });*/
/*
        double lat = 51.5074;
        double lng = 0.1278;
        String hash = GeoFireUtils.getGeoHashForLocation(new GeoLocation(lat, lng));

        // Add the hash and the lat/lng to the document. We will use the hash
        // for queries and the lat/lng for distance comparisons.
        Map<String, Object> updates = new HashMap<>();
        updates.put("geohash", hash);
        updates.put("lat", lat);
        updates.put("lng", lng);

        DocumentReference londonRef = db.collection("cities").document("LON");
        londonRef.update(updates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
        // [END fs_geo_add_hash]
    }

    final GeoLocation center = new GeoLocation(51.5074, 0.1278);
    final double radiusInM = 50 * 1000;

    List<GeoQueryBounds> bounds = GeoFireUtils.getGeoHashQueryBounds(center, radiusInM);
    final List<Task<QuerySnapshot>> tasks = new ArrayList<>();
        for(GeoQueryBounds b :bounds){
        Query q = db.collection("client")
                .orderBy("geohash")
                .startAt(b.startHash)
                .endAt(b.endHash);

        tasks.add(q.get());
    }

    // Collect all the query results together into a single list
        Tasks.whenAllComplete(tasks)
            .addOnCompleteListener(new OnCompleteListener<List<Task<?>>>() {
        @Override
        public void onComplete (@NonNull Task < List < Task < ? >>> t){
            List<DocumentSnapshot> matchingDocs = new ArrayList<>();

            for (Task<QuerySnapshot> task : tasks) {
                QuerySnapshot snap = task.getResult();
                for (DocumentSnapshot doc : snap.getDocuments()) {
                    double lat = doc.getDouble("lat");
                    double lng = doc.getDouble("lng");

                    // We have to filter out a few false positives due to GeoHash
                    // accuracy, but most will match
                    GeoLocation docLocation = new GeoLocation(lat, lng);
                    double distanceInM = GeoFireUtils.getDistanceBetween(docLocation, center);
                    if (distanceInM <= radiusInM) {
                        matchingDocs.add(doc);
                    }
                }
            }

            // matchingDocs contains the results
            // ...
        }
    });
    // [END fs_geo_query_hashes]
 */
/*
        DatabaseReference temp2 = FirebaseDatabase.getInstance().getReference("users/A");
        GeoFire geofire=new GeoFire(temp2.child("A_location"));
        GeoQuery geoQuery=geofire.queryAtLocation(new GeoLocation(lat,lng),10);

        geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
            @Override
            public void onKeyEntered(String key, GeoLocation location) {
                temp2.child(key).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {


                        Person person1 = dataSnapshot.getValue(Person.class);

                        String name = person1.getName();
                        String imageUri = person1.getImageUri();
                        System.out.print(name + "   " + imageUri);
                        Toast.makeText(getActivity(),name,Toast.LENGTH_LONG).show();

                        personList.add(new Person(name, imageUri));

                        RVAdapter adapter=new RVAdapter(getActivity(),personList);
                        rv.setAdapter(adapter);

                    }*/
}




    @Override
    protected void onStop() {
        super.onStop();
        locationManager.removeUpdates(locationListener);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap=googleMap;
    }
}