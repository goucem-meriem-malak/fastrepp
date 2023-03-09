package com.example.app4.others;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app4.R;
import com.example.app4.data.client;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
public class sign_up extends AppCompatActivity{
    private FirebaseFirestore db;
    private EditText firstname,lastname,phone,password,address;
    private Button signup,signin;
    private client client;
    private LatLng clocation= new LatLng(0,0);
    private String cid, cfirstname, clastname, cphone, cpassword, caddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        signup = findViewById(R.id.signup);
        signin = findViewById(R.id.signin);
        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        address = findViewById(R.id.address);
        db = FirebaseFirestore.getInstance();


        signup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                    HashMap<String, Object> m =new HashMap<String, Object>();
                    DocumentReference ref = db.collection("client").document();

                    cid = ref.getId();
                cfirstname = firstname.getText().toString().trim();
                clastname="kkkkoko";
                cphone = phone.getText().toString().trim();
                cpassword = password.getText().toString().trim();
                caddress = address.getText().toString().trim();
                client = new client(cid,cfirstname,clastname,caddress,Integer.parseInt(cphone),cpassword,"");
                    m.put("id", client.getId());
                    m.put("firstname",client.getFirstname());
                    m.put("lastname",client.getLastname());
                    m.put("phone",client.getPhone());
                    m.put("password",client.getPassword());
                    m.put("address",client.getAddress());
                    ref.set(m);

                Intent activityChangeIntent = new Intent(sign_up.this, home.class);

                sign_up.this.startActivity(activityChangeIntent);
            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent activityChangeIntent = new Intent(sign_up.this, sign_in.class);

                sign_up.this.startActivity(activityChangeIntent);
            }
        });
    }
}