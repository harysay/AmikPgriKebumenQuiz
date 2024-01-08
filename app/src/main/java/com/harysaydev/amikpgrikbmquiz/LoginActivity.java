package com.harysaydev.amikpgrikbmquiz;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.iid.FirebaseInstanceId;
import com.harysaydev.amikpgrikbmquiz.chat.Home.MainChatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private DatabaseReference storeDefaultDatabaseReference2;

    private EditText userEmail, userPassword;
    private Button loginButton;
    private TextView linkSingUp, linkForgotPassword,resendEmail, copyrightTV;
    private String getNomorHPInputan,header_phone;
    private SharedPreferences sharedPreferences;
    private DatabaseReference cekVerifikasiDatabaseReference;

    private ProgressDialog progressDialog;

    //Firebase Auth
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    Date currentTime = Calendar.getInstance().getTime();
    private DatabaseReference userDatabaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_punyaakun);
        sharedPreferences = getSharedPreferences("Content_main", Context.MODE_PRIVATE);//reference to shared preference file
        Intent intent = getIntent();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        //getSudahPunyaAkun = getIntent().getExtras().get("disableleaderboard").toString();
        //getNomorHPInputan = getIntent().getExtras().get("inputannomorhp").toString();
        header_phone = sharedPreferences.getString("phone", "");
        getNomorHPInputan = intent.getStringExtra("inputannomorhp");
        userDatabaseReference = FirebaseDatabase.getInstance().getReference().child("users");

        userEmail = findViewById(R.id.inputEmail);
        userPassword = findViewById(R.id.inputPassword);
        loginButton = findViewById(R.id.loginButton);
        linkSingUp = findViewById(R.id.linkSingUp);
        resendEmail = findViewById(R.id.linkResendEmail);
        linkForgotPassword = findViewById(R.id.linkForgotPassword);
        progressDialog = new ProgressDialog(this);

        //Copyright text
        copyrightTV = findViewById(R.id.copyrightTV);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        copyrightTV.setText("Amik PGRI Kebumen: Harysay © " + year);

        //redirect to FORGOT PASS activity
        linkForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d( TAG, "onClick: go to FORGOT Activity");
                Intent intent = new Intent(LoginActivity.this, ForgotPassActivity.class);
                startActivity(intent);
//                Toast.makeText(LoginActivity.this, "Silahkan daftar ulang jika Anda lupa password!", Toast.LENGTH_LONG).show();
            }
        });

        //redirect to register activity
        linkSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d( TAG, "onClick: go to Register Activity");
                finish();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });

        //redirect to register activity
        resendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user!=null) {
                    user.sendEmailVerification();
                    Toast.makeText(LoginActivity.this, "Verification sent!, please check your email", Toast.LENGTH_LONG).show();
                }else{
                    mAuth.signOut();
                }
            }
        });


        /**
         * Login Button with Firebase
         */
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = userEmail.getText().toString();
                String password = userPassword.getText().toString();

                loginUserAccount(email, password);
            }
        });
    }

    private void loginUserAccount(String email, String password) {
        //just validation
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Enter email", Toast.LENGTH_LONG).show();
            //Toasty.error(this, "Email is required",Toast.LENGTH_SHORT, true).show();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this, "Your email is invalid", Toast.LENGTH_LONG).show();
            //Toasty.error(this, "Your email is not valid.",Toast.LENGTH_SHORT, true).show();
        } else if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Enter your password", Toast.LENGTH_LONG).show();
            //Toasty.error(this, "Password is required", Toast.LENGTH_SHORT, true).show();
        } else if (password.length() < 6){
            Toast.makeText(this, "Make sure your password consists of at least 6 characters", Toast.LENGTH_LONG).show();
            //Toasty.error(this, "May be your password had minimum 6 numbers of character.", Toast.LENGTH_SHORT, true).show();
        } else {

            //progress bar
            progressDialog.setMessage("Please wait...");
            progressDialog.show();
            progressDialog.setCanceledOnTouchOutside(false);



            // after validation checking, log in user a/c
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()){
                                // these lines for taking DEVICE TOKEN for sending device to device notification
                                user = mAuth.getCurrentUser();
                                boolean isVerified = false;
                                isVerified = user.isEmailVerified();
                                if (isVerified==true){
//                                    userDatabaseReference = FirebaseDatabase.getInstance().getReference().child("users");
//                                    userDatabaseReference.child(getNomorHPInputan).child("verified").setValue("true");
                                    if(header_phone.equals("")){
                                        header_phone=getNomorHPInputan;
                                    }
                                    cekVerifikasiDatabaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(header_phone);
                                    cekVerifikasiDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            if (dataSnapshot.exists()) {
                                                cekVerifikasiDatabaseReference.child("verified").setValue("true");
                                                cekVerifikasiDatabaseReference.child("tanggal_login").setValue(currentTime.toString());
                                                cekVerifikasiDatabaseReference.child("new_pass").setValue(password);
                                                String nama = "", passwo = "", emailku = "", phone = "", gende = "", izinAksesLeaderboard = "", kodeunik = "";
                                                //for (DataSnapshot zoneSnapshot: dataSnapshot.getChildren()) {
                                                //Log.d(TAG, zoneSnapshot.child("search_name").getValue(String.class));
                                                Log.d(TAG, "hasilpenarikan: " + dataSnapshot.child("search_name").getValue(String.class));
                                                nama = dataSnapshot.child("search_name").getValue(String.class);
                                                passwo = password;//dataSnapshot.child("user_kunci").getValue(String.class);
                                                emailku = dataSnapshot.child("user_email").getValue(String.class);
                                                phone = dataSnapshot.child("user_mobile").getValue(String.class);
                                                gende = dataSnapshot.child("user_gender").getValue(String.class);
                                                kodeunik = dataSnapshot.child("created_at").getValue().toString();
                                                izinAksesLeaderboard = dataSnapshot.child("submit_permit").getValue(String.class);

                                                if (izinAksesLeaderboard.equals("notpermit")) {
                                                    //user tidak diizinkanmeng akses leaderboard
                                                    Intent intent = new Intent(LoginActivity.this, Navigation_Activity.class);
                                                    intent.putExtra("disableleaderboard", "0");
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                    startActivity(intent);
                                                } else {
                                                    //user diizinkan akses leaderboard
                                                    Intent intent = new Intent(LoginActivity.this, Navigation_Activity.class);
                                                    intent.putExtra("disableleaderboard", "1");
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                    startActivity(intent);
                                                    finish();
                                                }

                                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                                editor.putString("name", nama);
                                                editor.putString("password", passwo);
                                                editor.putString("email", emailku);
                                                editor.putString("phone", phone);
                                                editor.putString("gender", gende);
                                                editor.putString("kodeunik", kodeunik);
                                                editor.commit();
                                            }else{
                                                Toast.makeText(LoginActivity.this, "Nomor Anda tidak terdaftar, silahkan hubungi admin!", Toast.LENGTH_LONG).show();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                            Toast.makeText(LoginActivity.this, "Error databse connection!", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }else{
                                    Toast.makeText(LoginActivity.this, "Email has not been verified, verify via the email you registered", Toast.LENGTH_LONG).show();
//                                    checkVerifiedEmail();
                                }

                            } else {
                                Toast.makeText(LoginActivity.this, "Maybe the email or password you entered is wrong. Please correct and try again", Toast.LENGTH_LONG).show();
                                //Toasty.error(LoginActivity.this, "Your email and password may be incorrect. Please check & try again.", Toast.LENGTH_SHORT, true).show();
                            }

                            progressDialog.dismiss();

                        }
                    });
        }
    }

    /** checking email verified or NOT */
    private void checkVerifiedEmail() {

        if (user != null) {

            cekVerifikasiDatabaseReference = FirebaseDatabase.getInstance().getReference().child("users");
            cekVerifikasiDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String triggerNotPermitLeaderboard = "1";
                    String trigerStatVerifikasi = "1";
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        //UserInformation uInfo = new UserInformation();
                        //String darids = ds.getValue().toString();
                        //uInfo.setDevice_id(ds.child("device_id").getValue(UserInformation.class).getDevice_id());
                        String izinAksesLeaderboard = ds.child("submit_permit").getValue(String.class);
                        String statusverifikasi = ds.child("verified").getValue(String.class);
                        //uInfo.setDevice_id(ds.getValue(UserInformation.class).getDevice_id());//ngeset username di objek
                        if (izinAksesLeaderboard.equals("notpermit")) {
                            triggerNotPermitLeaderboard = "0";
                            //user tidak diizinkanmengakses leaderboard
                            //break;
                        }
                        if (statusverifikasi.equals("true")) {
                            //tidak boleh login
                            trigerStatVerifikasi = "0";
                        }
                    }
                    if (trigerStatVerifikasi.equals("0")) {
                        //tidak boleh login karena belum verifikasi
                        if (triggerNotPermitLeaderboard.equals("0")) {
                            //Tidak diizinkan akses leaderboard
                            Intent intent = new Intent(LoginActivity.this, Navigation_Activity.class);
                            intent.putExtra("disableleaderboard", "0");
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(LoginActivity.this, Navigation_Activity.class);
                            intent.putExtra("disableleaderboard", "1");
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Email has not been verified, verify via the email you registered", Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }else{
            mAuth.signOut();
            Intent mainIntent =  new Intent(LoginActivity.this, MainActivity.class);
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(mainIntent);
            finish();
            Toast.makeText(LoginActivity.this, "Check your email and verify", Toast.LENGTH_LONG).show();
        }

//        user = mAuth.getCurrentUser();
//        boolean isVerified = false;
//        if (user != null) {
//            isVerified = user.isEmailVerified();
//        }
//        if (isVerified){
//            //String UID = mAuth.getCurrentUser().getUid();
//            //userDatabaseReference.child(UID).child("verified").setValue("true");
//            if(getSudahPunyaAkun.equals("0")){
//
//            }else {
//
//            }
//
//        } else {
//            if(getSudahPunyaAkun.equals("0")){
//                //mAuth.signOut();
//                Intent intent = new Intent(LoginActivity.this, Navigation_Activity.class);
//                intent.putExtra("disableleaderboard", "0");
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(intent);
//            }else {
//
//                //Toasty.info(LoginActivity.this, "Email is not verified. Please verify first", Toast.LENGTH_LONG, true).show();
//                mAuth.signOut();
//            }
//        }
    }



}