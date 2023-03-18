package com.example.app4;
import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.TimeUnit;


public class first_screen extends Activity {

        private FirebaseAuth auth;
        private FirebaseUser user;
         private FirebaseFirestore db;

        private EditText edtPhone, edtOTP;

        private Button verifyOTPBtn, generateOTPBtn;

        private String verificationId, uid, phone;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.first_screen);

            db = FirebaseFirestore.getInstance();
            auth = FirebaseAuth.getInstance();
            user = auth.getCurrentUser();

            edtPhone = findViewById(R.id.idEdtPhoneNumber);
            edtOTP = findViewById(R.id.idEdtOtp);
            verifyOTPBtn = findViewById(R.id.idBtnVerify);
            generateOTPBtn = findViewById(R.id.idBtnGetOtp);

            // setting onclick listener for generate OTP button.
            generateOTPBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (TextUtils.isEmpty(edtPhone.getText().toString())) {
                        Toast.makeText(first_screen.this, "Please enter a valid phone number.", Toast.LENGTH_SHORT).show();
                    } else {
                        phone = "+213" + edtPhone.getText().toString();
                        sendVerificationCode(phone);
                    }
                }
            });

            // initializing on click listener
            // for verify otp button
            verifyOTPBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // validating if the OTP text field is empty or not.
                    if (TextUtils.isEmpty(edtOTP.getText().toString())) {
                        // if the OTP text field is empty display
                        // a message to user to enter OTP
                        Toast.makeText(first_screen.this, "Please enter OTP", Toast.LENGTH_SHORT).show();
                    } else {
                        // if OTP field is not empty calling
                        // method to verify the OTP.
                        verifyCode(edtOTP.getText().toString());
                    }
                }
            });
        }

        private void signInWithCredential(PhoneAuthCredential credential) {
            // inside this method we are checking if
            // the code entered is correct or not.
            auth.signInWithCredential(credential)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                verifyifuserexist();
                                Intent i = new Intent(first_screen.this, com.example.app4.others.home.class);
                                startActivity(i);
                                finish();
                            } else {
                                Toast.makeText(first_screen.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }

    private void verifyifuserexist() {
            db.collection("client").document(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Toast.makeText(getApplicationContext(), "already a user", Toast.LENGTH_LONG).show();
                    } else {
                        createclient();
                    }
                } else {
                    Log.w(TAG, "Error getting document", task.getException());
                }
            }
        });

    }

    private void createclient() {

        DocumentReference ref = db.collection("client").document(user.getUid());
        HashMap<String, Object> m =new HashMap<String, Object>();
        m.put("id", user.getUid());
        m.put("phone", phone);
        ref.set(m);
    }


    private void sendVerificationCode(String number) {
            PhoneAuthOptions options =
                    PhoneAuthOptions.newBuilder(auth)
                            .setPhoneNumber(number)
                            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                            .setActivity(this)
                            .setCallbacks(mCallBack)
                            .build();
            PhoneAuthProvider.verifyPhoneNumber(options);

        }

        private PhoneAuthProvider.OnVerificationStateChangedCallbacks

                mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {


            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                verificationId = s;
            }

            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                final String code = phoneAuthCredential.getSmsCode();

                if (code != null) {

                    edtOTP.setText(code);
                    verifyCode(code);
                }
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(first_screen.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        };
        private void verifyCode(String code) {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);

            signInWithCredential(credential);
        }
    }
