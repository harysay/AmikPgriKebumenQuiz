package com.harysaydev.amikpgrikbmquiz;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
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
import com.google.firebase.iid.FirebaseInstanceId;
import com.harysaydev.amikpgrikbmquiz.helper.CheckNetwork;
import com.harysaydev.amikpgrikbmquiz.intro.MyIntro;
import com.harysaydev.amikpgrikbmquiz.model.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

//import android.content.DialogInterface;

public class MainActivity extends AppCompatActivity {
    private String TAG = MainActivity.class.getClass().getSimpleName();
    //Firebase
    private FirebaseAuth mAuth;
    private FirebaseUser userku;
    private DatabaseReference storeDefaultDatabaseReference,skornomorhp;
    private SharedPreferences sharedPreferences;
    private GoogleApiClient mGoogleApiClient;
    //variables
    private ProgressDialog progressDialog;
    private DatabaseReference cekVerifikasiDatabaseReference;
    private Context myContext = MainActivity.this;
    String tidakbolehdaftar="";
    public boolean isFirstStart;
    Button show, show2, getStarted, Continue, helpmain2,punyaakun;
    EditText edit_password, edit_name, edit_email, edit_phone, edit_password2;
    TextView name_display, forget;
    private final String Default = "";
    String[] Gender = {"Male", "Female"};
    String gender;
    final static String TARGET_BASE_PATH = "/sdcard/appname/voices/";
    Spinner spinner;
    MediaPlayer mediaPlayer;
    ImageView icon_user;
    private ProgressDialog progressBar;//Create a circular progressBar Dialog
    String save_name,save_email, save_kunc,save_phone;
    String name_file,pass_file,email_file,phone_numb,gender_file, deviceId;
    private String userID;
    int z = 0;
    private DatabaseReference userDatabaseReference;
    Date currentTime = Calendar.getInstance().getTime();
//    long timestampCreate;
    //private android.content.Context ActivityContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        getSudahPunyaAkunDariLogin = getIntent().getExtras().get("disableleaderboard").toString();
        sharedPreferences = getSharedPreferences("Content_main", Context.MODE_PRIVATE);//reference to shared preference file
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        // connection failed, should be handled
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();

        mAuth = FirebaseAuth.getInstance();
        //userku = mAuth.getCurrentUser();
        //Creating a shared preference file  to save the name ,mail address,password and also for setting the correct xml file
        name_file = sharedPreferences.getString("name", Default);
        pass_file = sharedPreferences.getString("password", Default);
        email_file = sharedPreferences.getString("email", Default);
        phone_numb = sharedPreferences.getString("phone",Default);
        gender_file = sharedPreferences.getString("gender", Default);
        SharedPreferences sp = getSharedPreferences("Score", Context.MODE_PRIVATE);
        progressDialog = new ProgressDialog(myContext);

        //To play background sound
//        if (sp.getInt("Sound", 0) == 0) {
//            mediaPlayer = MediaPlayer.create(this, R.raw.abc);
//            mediaPlayer.start();
//            mediaPlayer.setLooping(true);
//        }
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                //  Intro App Initialize SharedPreferences
                SharedPreferences getSharedPreferences = PreferenceManager
                        .getDefaultSharedPreferences(getBaseContext());

                //  Create a new boolean and preference and set it to true
                isFirstStart = getSharedPreferences.getBoolean("firstStart", true);

                //  Check either activity or app is open very first time or not and do action
                if (isFirstStart) {

                    //  Launch application introduction screen
                    Intent i = new Intent(MainActivity.this, MyIntro.class);
                    startActivity(i);
                    SharedPreferences.Editor e = getSharedPreferences.edit();
                    e.putBoolean("firstStart", false);
                    e.apply();
                }
            }
        });
        t.start();
        if (name_file.equals(Default) || pass_file.equals(Default) || email_file.equals(Default) || gender_file.equals(Default)|| phone_numb.equals(Default)) {
            //mediaPlayer.stop();
            setContentView(R.layout.activity_main);

            show = (Button) findViewById(R.id.show);  //Show button in password
            show.setText("SHOW");
            edit_password = (EditText) findViewById(R.id.password);   //Password EditText
            edit_email = (EditText) findViewById(R.id.email);   //email EditText
            edit_name = (EditText) findViewById(R.id.name);   //name EditText
            edit_phone = (EditText) findViewById(R.id.phoneNumb); //phone EditText

            show.setOnClickListener(new showOrHidePassword());//invoking the showOrHidePassword class to show the password
            //punyaakun = (Button) findViewById(R.id.sudahpunyaakun);

            //Spinner for choosing the gender
            spinner = (Spinner) findViewById(R.id.spinner);
            punyaakun = (Button) findViewById(R.id.sudahpunyaakun);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, R.layout.spinner, Gender);
            adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(new spinner());
            //

            getStarted = (Button) findViewById(R.id.getStarted);
            FirebaseApp.initializeApp(this);
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference tabel_user = database.getReference("User");
            deviceId = Settings.Secure.getString(getContentResolver(),
                    Settings.Secure.ANDROID_ID);

            getStarted.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    save_name = edit_name.getText().toString();
                    save_email = edit_email.getText().toString();
                    save_kunc = edit_password.getText().toString();
                    save_phone = edit_phone.getText().toString();
                    if (CheckNetwork.isInternetAvailable(MainActivity.this)) //returns true if internet available
                    {
                        // pass input parameter through this Method
                        registerAccount(save_name, save_email, save_phone, save_kunc);
                    }else {
                        try {
                            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();

                            alertDialog.setTitle("No Internet");
                            alertDialog.setMessage("Check your internet connectivity and try again");
                            alertDialog.setIcon(android.R.drawable.stat_sys_warning);
                            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int n) {
                                    dialog.cancel();
                                }
                            });
                            alertDialog.show();
                        } catch (Exception e) {
                            Log.d(TAG, "Log tidak konek: "+e.getMessage());
                        }
                    }
                }
            });
            punyaakun.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CheckNetwork.isInternetAvailable(MainActivity.this)) //returns true if internet available
                    {
                        save_name = edit_name.getText().toString();
                        save_email = edit_email.getText().toString();
                        save_kunc = edit_password.getText().toString();
                        save_phone = edit_phone.getText().toString();
                        DialogInterface.OnClickListener dialogSetujuClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case DialogInterface.BUTTON_POSITIVE:
                                        //Yes button clicked
                                        /*  startActivity(new Intent(this,Setting.class));*/
//                                    if (userku != null) {
                                        //String UID = userku.getUid();
//                                        phone_numb = sharedPreferences.getString("phone","");
//                                        Log.d(TAG, phone_numb);
                                        if (TextUtils.isEmpty(save_phone)){
                                            Toast.makeText(MainActivity.this, "Isikan nomor Anda yang pernah terdaftar", Toast.LENGTH_LONG).show();
                                            //Toasty.error(myContext, "Your mobile number is required.", Toast.LENGTH_SHORT).show();
                                        }else if (save_phone.length() < 10){
                                            Toast.makeText(MainActivity.this, "Nomor HP minimal 10 karakter", Toast.LENGTH_LONG).show();
                                            //Toasty.error(myContext, "Mobile number should be min 10 characters.", Toast.LENGTH_SHORT).show();
                                        }else{
                                            //user tidak diizinkan akses leaderboard
                                            userDatabaseReference = FirebaseDatabase.getInstance().getReference().child("users");
                                            userDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot datSnapshot) {
                                                    if (datSnapshot.child(save_phone).exists()) {
                                                        Toast.makeText(MainActivity.this, "Nomor Anda terdeteksi pernah mendaftar!", Toast.LENGTH_SHORT).show();
                                                        userDatabaseReference.child(save_phone).child("submit_permit").setValue("notpermit")
                                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                          @Override
                                                                                          public void onSuccess(Void aVoid) {
                                                                                              Intent loginku = new Intent(MainActivity.this, LoginActivity.class);
                                                                                              loginku.putExtra("inputannomorhp",save_phone);
                                                                                              loginku.putExtra("disableleaderboard", "0");
                                                                                              startActivity(loginku);
                                                                                              // break;
                                                                                          }
                                                                                      }
                                                                );
                                                    }
                                                    else{
                                                        Toast.makeText(MainActivity.this, "Data dengan nomor tersebut tidak ditemukan, silahkan hubungi admin!", Toast.LENGTH_LONG).show();
                                                    }

                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });

                                        }
//                                    }else{
//                                       // mAuth = FirebaseAuth.getInstance();
//                                        Toast.makeText(MainActivity.this, "Anda telah terblokir, silahkan hubungi Admin!", Toast.LENGTH_SHORT).show();
//                                        finish();
//                                        startActivity(getIntent());
//                                    }


                                    case DialogInterface.BUTTON_NEGATIVE:
                                        //No button clicked
                                        break;
                                }
                            }
                        };
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("Anda akan login dengan akun lama sehingga tidak diizinkan submit skor ke leaderboard. Apakah ingin melanjutkan?").setPositiveButton("Ya", dialogSetujuClickListener)
                                .setNegativeButton("Batal", dialogSetujuClickListener).show();
                    }else {
                        try {
                            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();

                            alertDialog.setTitle("No Internet");
                            alertDialog.setMessage("Check your internet connectivity and try again");
                            alertDialog.setIcon(android.R.drawable.stat_sys_warning);
                            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int n) {
                                    dialog.cancel();
                                }
                            });
                            alertDialog.show();
                        } catch (Exception e) {
                            Log.d(TAG, "Log tidak konek: "+e.getMessage());
                        }
                    }

                }
            });


        }
//        else if(getSudahPunyaAkunDariLogin.equals("1")){
//
//        }
        else {
            //mediaPlayer.stop();
            setContentView(R.layout.activity_main_second);
            icon_user = (ImageView) findViewById(R.id.image_icon);
            if (gender_file.equals("Male")) {
                icon_user.setImageResource(R.drawable.man);
            } else {
                icon_user.setImageResource(R.drawable.female);
            }


            name_display = (TextView) findViewById(R.id.name_display);
            name_display.setText(name_file);
            edit_password2 = (EditText) findViewById(R.id.password2);
            edit_password2.requestFocus();
            show2 = (Button) findViewById(R.id.show2);
            show2.setText("SHOW");
            show2.setOnClickListener(new showOrHidePassword2());
            forget = (TextView) findViewById(R.id.forget);
            forget.setOnClickListener(new tampilkanPesanLupapassword());
            Continue = (Button) findViewById(R.id.Continue);
//            helpmain2 = (Button) findViewById(R.id.helpmain);
//            helpmain2.setVisibility(View.INVISIBLE);

            try {
                Continue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View ve) {
                        String nomerhape =sharedPreferences.getString("phone", "");
                        String kuncentersimpan =sharedPreferences.getString("password", "");
                        String kuncen = edit_password2.getText().toString();
                        if (CheckNetwork.isInternetAvailable(MainActivity.this)) //returns true if internet available
                        {
                            if(kuncen.equals(kuncentersimpan)){
                                //String klikContinue = "sudahpunyaakun";
                                checkVerifiedEmail(nomerhape,ve);
                            } else {
                                Toast.makeText(MainActivity.this, "Password yang Anda masukan salah", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            try {
                                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();

                                alertDialog.setTitle("No Internet");
                                alertDialog.setMessage("Check your internet connectivity and try again");
                                alertDialog.setIcon(android.R.drawable.stat_sys_warning);
                                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int n) {
                                        dialog.cancel();
                                    }
                                });
                                alertDialog.show();
                            } catch (Exception e) {
                                Log.d(TAG, "Log tidak konek: "+e.getMessage());
                            }
                        }
                    }
                });
            } catch (Exception e) {
                Toast.makeText(MainActivity.this, "Warning", Toast.LENGTH_SHORT).show();
            }

        }
    }

    /** checking email verified or NOT */
    private void checkVerifiedEmail(String nohptersimpan,View vie) {
        progressBar = new ProgressDialog(MainActivity.this);//Create new object of progress bar type
        progressBar.setCancelable(false);//Progress bar cannot be cancelled by pressing any where on screen
        progressBar.setMessage("Please Wait...");//Title shown in the progress bar
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);//Style of the progress bar
        progressBar.setProgress(0);//attributes
        progressBar.setMax(100);//attributes
        progressBar.show();//show the progress bar
        cekVerifikasiDatabaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(nohptersimpan);
        cekVerifikasiDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //cek apakah sudah ada di database
                String statusVerifikasi ="", izinAksesLeaderboard="";
                izinAksesLeaderboard = dataSnapshot.child("submit_permit").getValue(String.class);
                statusVerifikasi = dataSnapshot.child("verified").getValue(String.class);
                if(statusVerifikasi.equals("true")){
                    if (izinAksesLeaderboard.equals("notpermit")) {
                        progressBar.cancel();//Progress bar will be cancelled (hide from screen)
                        //user tidak diizinkanmeng akses leaderboard
                        Intent intent = new Intent(MainActivity.this, Navigation_Activity.class);
                        intent.putExtra("disableleaderboard", "0");
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }else{
                        progressBar.cancel();//Progress bar will be cancelled (hide from screen)
                        //user diizinkan akses leaderboard
                        Intent intent = new Intent(MainActivity.this, Navigation_Activity.class);
                        intent.putExtra("disableleaderboard", "1");
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                }else {
                    progressBar.cancel();//Progress bar will be cancelled (hide from screen)
                    Toast.makeText(MainActivity.this, "Email belum terverifikasi, lakukan verifikasi melalui email yang Anda daftarkan", Toast.LENGTH_LONG).show();
                    //user diizinkan akses leaderboard
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    intent.putExtra("disableleaderboard", "1");
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    //Used to show the help by triggering a toast
    public void showHelp(View view) {
        if (name_file.equals(Default) || pass_file.equals(Default) || email_file.equals(Default) || gender_file.equals(Default)|| phone_numb.equals(Default)) {
            Toast toast_help = new Toast(getApplicationContext());
            toast_help.setGravity(Gravity.CENTER, 0, 0);
            toast_help.setDuration(Toast.LENGTH_LONG);
            LayoutInflater inflater = getLayoutInflater();
            View appear = inflater.inflate(R.layout.toast_help, (ViewGroup) findViewById(R.id.linear));
            toast_help.setView(appear);
            toast_help.show();
        }else {
            Toast.makeText(this, "Anda lupa password berarti skor yang telah Anda peroleh hilang!, silahkan daftar ulang dengan nomor HP yang baru", Toast.LENGTH_LONG).show();
        }


    }

    private void registerAccount(final String name, final String email, final String mobile, final String password) {

        //Validation for empty fields
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(MainActivity.this, "Masukan nama lengkap Anda", Toast.LENGTH_LONG).show();
            //Toasty.error(myContext, "Your name is required.", Toast.LENGTH_SHORT).show();
        } else if (name.length() < 3 || name.length() > 40){
            Toast.makeText(MainActivity.this, "Jumlah karakter untuk nama antara 3 sampai 40", Toast.LENGTH_LONG).show();
            //Toasty.error(myContext, "Your name should be 3 to 40 numbers of characters.", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(email)){
            Toast.makeText(MainActivity.this, "Masukan email valid Anda", Toast.LENGTH_LONG).show();
            //Toasty.error(myContext, "Your email is required.", Toast.LENGTH_SHORT).show();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(MainActivity.this, "Email yang Anda masukan tidak valid", Toast.LENGTH_LONG).show();
            //Toasty.error(myContext, "Your email is not valid.", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(mobile)){
            Toast.makeText(MainActivity.this, "Masukan nomor handphone valid Anda", Toast.LENGTH_LONG).show();
            //Toasty.error(myContext, "Your mobile number is required.", Toast.LENGTH_SHORT).show();
        } else if (mobile.length() < 10){
            Toast.makeText(MainActivity.this, "Nomor HP minimal 10 karakter", Toast.LENGTH_LONG).show();
            //Toasty.error(myContext, "Mobile number should be min 10 characters.", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)){
            Toast.makeText(MainActivity.this, "Masukan password Anda", Toast.LENGTH_LONG).show();
            //Toasty.error(myContext, "Please fill this password field", Toast.LENGTH_SHORT).show();
        } else if (password.length() < 6){
            Toast.makeText(MainActivity.this, "Jumlah karakter untuk password minimal 6 karakter", Toast.LENGTH_LONG).show();
            //Toasty.error(myContext, "Create a password at least 6 characters long.", Toast.LENGTH_SHORT).show();
        }else {
            if (FirebaseAuth.getInstance().getCurrentUser() != null) {
//                Go to login
                mAuth.signOut();
                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {


                            }
                        });
                Intent mainIntent =  new Intent(myContext, LoginActivity.class);
                mainIntent.putExtra("inputannomorhp",mobile);
                mainIntent.putExtra("disableleaderboard", "0");
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(mainIntent);
                Toast.makeText(MainActivity.this, "Anda terdeteksi pernah memiliki akun!", Toast.LENGTH_SHORT).show();
                finish();
            }
            else{
                userRegistration(name,email,mobile,password);
            }

        }

    }

    private void registerSuccessPopUp() {
        // Custom Alert Dialog
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this);
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.register_success_popup, null);

        //ImageButton imageButton = view.findViewById(R.id.successIcon);
        //imageButton.setImageResource(R.drawable.logout);
        builder.setCancelable(false);

        builder.setView(view);
        builder.show();
    }


    //Used to add some time so that user cannot directly press and exity out of the activity
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Klik BACK lagi untuk keluar", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
                mAuth.signOut();
            }
        }, 4000);

    }


    //class to show or hide password on button click in main activity
    class showOrHidePassword implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (show.getText().toString() == "SHOW") {
                edit_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                show.setText("HIDE");

            } else {

                edit_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                show.setText("SHOW");
            }
        }
    }

    //class to show or hide password on button click in main activity second
    class showOrHidePassword2 implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (show2.getText().toString() == "SHOW") {
                edit_password2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                show2.setText("HIDE");

            } else {

                edit_password2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                show2.setText("SHOW");
            }
        }
    }
    class tampilkanPesanLupapassword implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), "Untuk sementara menu lupa password dinonaktifkan karena Anda tidak diijinkan menginstall ulang Aplikasi dengan tujuan mereset skor", Toast.LENGTH_LONG).show();
        }
    }


    //Spinner class to select spinner and also add gender
    class spinner implements AdapterView.OnItemSelectedListener {


        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            gender = parent.getItemAtPosition(position).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            //When nothing is selected
            Toast.makeText(getApplicationContext(), "Please Enter the gender", Toast.LENGTH_SHORT).show();
        }
    }

    private String getDate(long timeStamp){
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timeStamp);
        Date d = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return sdf.format(d);
    }

    private void userRegistration(final String name, final String email, final String mobile, final String password){
        //NOw ready to create a user a/c
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                String deviceToken = FirebaseInstanceId.getInstance().getToken();
//                                FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( RegisterActivity.this,  new OnSuccessListener<InstanceIdResult>() {
//                                    @Override
//                                    public void onSuccess(InstanceIdResult instanceIdResult) {
//                                        deviceToken = instanceIdResult.getToken();
//                                        Log.e("Token",deviceToken);
//                                    }
//                                });

                // get and link storage
//                        if (FirebaseAuth.getInstance().getCurrentUser() == null){
//                            mAuth.signOut();
//                        }
                if (task.isSuccessful()){
                    //String current_userID =  mAuth.getCurrentUser().getUid();
                    //Get datasnapshot at your "users" root node
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users");
                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    storeDefaultDatabaseReference = FirebaseDatabase.getInstance().getReference().child("users");
                                    storeDefaultDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            String triggerDeviceId = "0";
                                            for(DataSnapshot ds : dataSnapshot.getChildren()){
                                                //UserInformation uInfo = new UserInformation();
                                                //String darids = ds.getValue().toString();
                                                //uInfo.setDevice_id(ds.child("device_id").getValue(UserInformation.class).getDevice_id());
                                                String idPerangkat = ds.child("device_id").getValue(String.class);
                                                //uInfo.setDevice_id(ds.getValue(UserInformation.class).getDevice_id());//ngeset username di objek
                                                if(deviceId.equals(idPerangkat)){
                                                    triggerDeviceId = "1";
                                                    break;
                                                }
                                            }
                                            if (dataSnapshot.child(save_phone).exists()) {
                                                Toast.makeText(MainActivity.this, "Nomor Anda terdeteksi pernah mendaftar!", Toast.LENGTH_SHORT).show();
                                            }
                                            else if(triggerDeviceId.equals("1")) {
                                                Toast.makeText(MainActivity.this, "Anda terblokir karena mencoba mendaftar dengan nomor yang berbeda!", Toast.LENGTH_LONG).show();
                                            }
                                            else{

                                                storeDefaultDatabaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(save_phone);
                                                skornomorhp = FirebaseDatabase.getInstance().getReference().child("Skor");
                                                //DatabaseReference dariFirebase = storeDefaultDatabaseReference.child("user_mobile");
                                                skornomorhp.addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                        //cek apakah sudah ada di database
                                                        if (dataSnapshot.child(save_phone).exists()) {
                                                            if (z == 0) {
                                                                Toast.makeText(MainActivity.this, "Nomor handphone Anda sudah pernah submit ke Leaderboard!", Toast.LENGTH_SHORT).show();
                                                            }
                                                        } else {
                                                            storeDefaultDatabaseReference.child("user_name").setValue(name);
                                                            storeDefaultDatabaseReference.child("access_rights").setValue("user");
                                                            storeDefaultDatabaseReference.child("submit_permit").setValue("permit");
                                                            storeDefaultDatabaseReference.child("user_kunci").setValue(password);
                                                            storeDefaultDatabaseReference.child("new_pass").setValue("");
                                                            storeDefaultDatabaseReference.child("verified").setValue("false");
                                                            storeDefaultDatabaseReference.child("search_name").setValue(name.toLowerCase());
                                                            storeDefaultDatabaseReference.child("user_mobile").setValue(mobile);
                                                            storeDefaultDatabaseReference.child("user_email").setValue(email);
                                                            storeDefaultDatabaseReference.child("user_nickname").setValue("");
                                                            storeDefaultDatabaseReference.child("user_gender").setValue(gender);
                                                            storeDefaultDatabaseReference.child("user_profession").setValue("tidak");
                                                            storeDefaultDatabaseReference.child("created_at").setValue(ServerValue.TIMESTAMP);
                                                            storeDefaultDatabaseReference.child("tanggal_dibuat").setValue(currentTime.toString());
                                                            storeDefaultDatabaseReference.child("tanggal_login").setValue("");
                                                            storeDefaultDatabaseReference.child("user_status").setValue("Hi, saya pengguna baru di AmikPGRIQuiz");
                                                            storeDefaultDatabaseReference.child("user_image").setValue("default_image"); // Original image
                                                            storeDefaultDatabaseReference.child("device_token").setValue(deviceToken);
                                                            storeDefaultDatabaseReference.child("device_id").setValue(deviceId);
                                                            storeDefaultDatabaseReference.child("user_thumb_image").setValue("default_image")
                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                            if (task.isSuccessful()){
                                                                                // SENDING VERIFICATION EMAIL TO THE REGISTERED USER'S EMAIL
                                                                                userku = mAuth.getCurrentUser();
                                                                                if (userku != null){
                                                                                    userku.sendEmailVerification()
                                                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                @Override
                                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                                    if (task.isSuccessful()){
                                                                                                        registerSuccessPopUp();
                                                                                                        // LAUNCH activity after certain time period
                                                                                                        new Timer().schedule(new TimerTask(){
                                                                                                            public void run() {
                                                                                                                MainActivity.this.runOnUiThread(new Runnable() {
                                                                                                                    public void run() {
                                                                                                                        //mAuth.signOut();
                                                                                                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                                                                                                        editor.putString("name", name);
                                                                                                                        editor.putString("password", password);
                                                                                                                        editor.putString("email", email);
                                                                                                                        editor.putString("phone", mobile);
                                                                                                                        editor.putString("gender", gender);
                                                                                                                        editor.commit();
                                                                                                                        z++;

                                                                                                                        Intent regIntent =  new Intent(myContext, LoginActivity.class);
                                                                                                                        regIntent.putExtra("inputannomorhp",mobile);
                                                                                                                        regIntent.putExtra("disableleaderboard", "1");
                                                                                                                        regIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                                                                                        startActivity(regIntent);
                                                                                                                        finish();
                                                                                                                        Toast.makeText(MainActivity.this, "Cek email Anda dan lakukan verifikasi", Toast.LENGTH_LONG).show();
                                                                                                                        //Toasty.info(myContext, "Please check your email & verify.", Toast.LENGTH_LONG).show();

                                                                                                                    }
                                                                                                                });
                                                                                                            }
                                                                                                        }, 4000);


                                                                                                    } else {
                                                                                                        mAuth.signOut();
                                                                                                    }
                                                                                                }
                                                                                            });
                                                                                }

                                                                            }
                                                                        }
                                                                    });

                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(DatabaseError databaseError) {
                                                        Log.w(TAG, "error saat cek eksis nomor hp", databaseError.toException());
                                                    }
                                                });
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    //handle databaseError
                                }
                            });
                } else {
                    String message = task.getException().getMessage();
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    Toast.makeText(MainActivity.this, "Authentication failed."+message,
                            Toast.LENGTH_SHORT).show();
                    mAuth.signOut();
                    Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                            new ResultCallback<Status>() {
                                @Override
                                public void onResult(Status status) {


                                }
                            });
                    Toast.makeText(MainActivity.this, "Anda terdeteksi telah memiliki akun, silahkan login dengan akun Anda!", Toast.LENGTH_SHORT).show();
                    Intent mainIntent =  new Intent(myContext, LoginActivity.class);
                    mainIntent.putExtra("inputannomorhp",mobile);
                    mainIntent.putExtra("disableleaderboard", "0");
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(mainIntent);
                    finish();
                    //Toasty.error(myContext, "Error occurred : " + message, Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        });


        //config progressbar
        progressDialog.setTitle("Creating new account");
        progressDialog.setMessage("Please wait a moment....");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
    }


    public void showDialog(View view) {
        int[][] states = new int[][]{
                new int[]{android.R.attr.state_pressed}, // pressed
                new int[]{android.R.attr.state_enabled}
        };

        int[] colors = new int[]{
                Color.parseColor("#9B1D20"), // red
                Color.parseColor("#AAFAC8") //light green

        };

        ColorStateList list = new ColorStateList(states, colors);
        forget.setTextColor(list);

        AlertDialog.Builder alertDialog;//Create a dialog object
        alertDialog = new AlertDialog.Builder(MainActivity.this);
        //EditText to show up in the AlertDialog so that the user can enter the email address
        final EditText editTextDialog = new EditText(MainActivity.this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        editTextDialog.setLayoutParams(layoutParams);
        editTextDialog.setHint("Email");
        //Adding EditText to Dialog Box
        alertDialog.setView(editTextDialog);
        alertDialog.setTitle("Enter Email");
        final SharedPreferences sharedPreferences = getSharedPreferences("Content_main", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        alertDialog.setPositiveButton("AGREE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String email_dialog = editTextDialog.getText().toString();
                if (sharedPreferences.getString("email", Default).equals(email_dialog)) {
                    //We are setting the values of Prefrences in sharedPrefrences to Default
                    editor.putString("name", Default);
                    editor.putString("email", Default);
                    editor.putString("password", Default);
                    editor.putString("gender", Default);
                    editor.commit();

                    //This intent will call the package manager and restart the current activity
                    Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                } else {
                    Toast.makeText(MainActivity.this, "Enter correct Email Address", Toast.LENGTH_SHORT).show();
                }
            }

        });
        alertDialog.setNegativeButton("DISAGREE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //When the Disagree button is pressed
            }
        });
        //Showing up the alert dialog box
        alertDialog.show();
    }
    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        SharedPreferences sp = getSharedPreferences("Score", Context.MODE_PRIVATE);
//        if (sp.getInt("Sound", 0) == 0)
//            mediaPlayer.pause();
//    }

//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        SharedPreferences sp = getSharedPreferences("Score", Context.MODE_PRIVATE);
//        if (sp.getInt("Sound", 0) == 0)
//            mediaPlayer.start();
//    }
private String collectPhoneNumbers(Map<String,Object> users, String token) {

    ArrayList<String> phoneNumbers = new ArrayList<>();
    ArrayList<String> tokenUser = new ArrayList<>();

    //iterate through each user, ignoring their UID
    for (Map.Entry<String, Object> entry : users.entrySet()){

        //Get user map
        Map singleUser = (Map) entry.getValue();
        //Get phone field and append to list
        phoneNumbers.add((String) singleUser.get("user_mobile"));
        tokenUser.add((String) singleUser.get("device_token"));
        if(singleUser.get("user_mobile").equals(save_phone)||singleUser.get("device_token").equals(token)){
            tidakbolehdaftar="tidakboleh";
            break;
        }else{
            tidakbolehdaftar="boleh";
        }
    }
    return tidakbolehdaftar;
//    System.out.println(phoneNumbers.toString());
//    System.out.println(tokenUser.toString());
}

}
