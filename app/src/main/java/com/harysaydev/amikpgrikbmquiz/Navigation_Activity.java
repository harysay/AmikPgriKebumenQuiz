package com.harysaydev.amikpgrikbmquiz;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.harysaydev.amikpgrikbmquiz.helper.CheckNetwork;
import com.harysaydev.amikpgrikbmquiz.socialmedia.main.main.MainActivity_Socialmedia;
import com.harysaydev.amikpgrikbmquiz.chat.Home.MainChatActivity;

public class Navigation_Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private String getSudahPunyaAkunDariLogin, nope,bolehAksesSoal="boleh";
    private String kodeAksesUas = "uas";
    private SharedPreferences sharedPreferences;
    TextView nav_header_nam, nav_header_emal;
    ImageView nav_header_imag;
    public final static String Message = "com.harysaydev.amikpgrikbmquiz.MESSAGE";
    Button komputer, kebangsaan, kampus, pengetahuanumum, ekonomiakuntansi, bahasainggris, literatur, intelegensi, kepribadian, profesi, grafikakomp,kecerdasan,rpl,pbo,strukturdata,pemroginternet,jarkom,desainweb;
    private ProgressDialog progressBar;
    MediaPlayer mediaPlayer;
    private static final String TAG = "NavigationMenu";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_);
        // Ambil versi aplikasi dari build.gradle
        String versionName = BuildConfig.VERSION_NAME;
        getSudahPunyaAkunDariLogin = getIntent().getExtras().get("disableleaderboard").toString();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("AMIK PGRI ("+versionName+")");
        setSupportActionBar(toolbar);
        sharedPreferences = getSharedPreferences("Content_main", Context.MODE_PRIVATE);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        SharedPreferences sp = getSharedPreferences("Score", Context.MODE_PRIVATE);
        //To play background sound
        if (sp.getInt("Sound", 0) == 0) {
            mediaPlayer = MediaPlayer.create(this, R.raw.abc);
            mediaPlayer.start();
            mediaPlayer.setLooping(true);
        }

        //Set name,email,image in  the navigation side drawer to those we enter in the login page
        String nav_header_name = sharedPreferences.getString("name", "xyz");
        String nav_header_email = sharedPreferences.getString("email", "abc@gmail.com");
        String nav_header_gender = sharedPreferences.getString("gender", "Male");
        nope = sharedPreferences.getString("phone","");

        View header = navigationView.getHeaderView(0);//Used to get a reference to navigation header
        nav_header_nam = (TextView) header.findViewById(R.id.nav_header_name);
        nav_header_emal = (TextView) header.findViewById(R.id.nav_header_email);
        nav_header_imag = (ImageView) header.findViewById(R.id.nav_header_image);
        nav_header_nam.setText(nav_header_name);
        nav_header_emal.setText(nav_header_email);
        if (nav_header_gender.equals("Male")) {
            nav_header_imag.setImageResource(R.drawable.man);
        } else {
            nav_header_imag.setImageResource(R.drawable.female);
        }
        komputer = (Button) findViewById(R.id.idkomputer);
        kebangsaan = (Button) findViewById(R.id.idkebangsaan);
        kampus = (Button) findViewById(R.id.idkampus);
        pengetahuanumum = (Button) findViewById(R.id.idpengetahuanumum);
        ekonomiakuntansi = (Button) findViewById(R.id.idekonomiakuntansi);
        bahasainggris = (Button) findViewById(R.id.idinggris);
        literatur = (Button) findViewById(R.id.idliteratur);
        intelegensi = (Button) findViewById(R.id.idintelegensi);
        kepribadian = (Button) findViewById(R.id.idkepribadian);
        profesi = (Button) findViewById(R.id.idprofesi);
        grafikakomp = (Button) findViewById(R.id.idgrafikom);
        kecerdasan = (Button) findViewById(R.id.idkecerdasan);
        rpl = (Button) findViewById(R.id.idrpl);
        pbo = (Button) findViewById(R.id.idpbo);
        strukturdata = (Button) findViewById(R.id.idstrukturdata);
        pemroginternet = (Button) findViewById(R.id.idpemrogramaninternet);
        jarkom = (Button) findViewById(R.id.idjarkom);
        desainweb = (Button) findViewById(R.id.iddesainweb);

        komputer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //To show button click
                progressBar = new ProgressDialog(v.getContext());//Create new object of progress bar type
                progressBar.setCancelable(false);//Progress bar cannot be cancelled by pressing any wher on screen
                progressBar.setMessage("Mempersiapkan Pertanyaan...");//Title shown in the progress bar
                progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);//Style of the progress bar
                progressBar.setProgress(0);//attributes
                progressBar.setMax(100);//attributes
                progressBar.show();//show the progress bar
                //This handler will add a delay of 3 seconds
                new Handler().postDelayed(() -> {
                    //Intent start to open the navigation drawer activity
                    progressBar.cancel();//Progress bar will be cancelled (hide from screen) when this run function will execute after 3.5seconds
                    Intent intent = new Intent(Navigation_Activity.this, Questions.class);
                    intent.putExtra(Message, "komputer");//by this statement we are sending the name of the button that invoked the new Questions.java activity "Message" is defined by the name of the package + MESSAGE
                    startActivity(intent);
                }, 1000);
            }
        });


        kebangsaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //To show button click
                new Handler().postDelayed(new Runnable() {@Override public void run(){}}, 400);

                progressBar = new ProgressDialog(v.getContext());//Create new object of progress bar type
                progressBar.setCancelable(false);//Progress bar cannot be cancelled by pressing any wher on screen
                progressBar.setMessage("Mempersiapkan Pertanyaan ...");//Title shown in the progress bar
                progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);//Style of the progress bar
                progressBar.setProgress(0);//attributes
                progressBar.setMax(100);//attributes
                progressBar.show();//show the progress bar
                //This handler will add a delay of 3 seconds
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Intent start to open the navigation drawer activity
                        progressBar.cancel();//Progress bar will be cancelled (hide from screen) when this run function will execute after 3.5seconds
                        Intent intent = new Intent(Navigation_Activity.this, Questions.class);
                        intent.putExtra(Message, "kebangsaan");
                        startActivity(intent);
                    }
                }, 2000);
            }
        });


        kampus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //To show button click
                new Handler().postDelayed(new Runnable() {@Override public void run(){}}, 400);

                progressBar = new ProgressDialog(v.getContext());//Create new object of progress bar type
                progressBar.setCancelable(false);//Progress bar cannot be cancelled by pressing any wher on screen
                progressBar.setMessage("Mempersiapkan Pertanyaan ...");//Title shown in the progress bar
                progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);//Style of the progress bar
                progressBar.setProgress(0);//attributes
                progressBar.setMax(100);//attributes
                progressBar.show();//show the progress bar
                //This handler will add a delay of 3 seconds
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Intent start to open the navigation drawer activity
                        progressBar.cancel();//Progress bar will be cancelled (hide from screen) when this run function will execute after 3.5seconds
                        Intent intent = new Intent(Navigation_Activity.this, Questions.class);
                        intent.putExtra(Message, "kampus");
                        startActivity(intent);
                    }
                }, 2000);
            }
        });


        pengetahuanumum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //To show button click
                new Handler().postDelayed(new Runnable() {@Override public void run(){}}, 400);

                progressBar = new ProgressDialog(v.getContext());//Create new object of progress bar type
                progressBar.setCancelable(false);//Progress bar cannot be cancelled by pressing any wher on screen
                progressBar.setMessage("Mempersiapkan Pertanyaan ...");//Title shown in the progress bar
                progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);//Style of the progress bar
                progressBar.setProgress(0);//attributes
                progressBar.setMax(100);//attributes
                progressBar.show();//show the progress bar
                //This handler will add a delay of 3 seconds
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Intent start to open the navigation drawer activity
                        progressBar.cancel();//Progress bar will be cancelled (hide from screen) when this run function will execute after 3.5seconds
                        Intent intent = new Intent(Navigation_Activity.this, Questions.class);
                        intent.putExtra(Message, "pengetahuanumum");
                        startActivity(intent);
                    }
                }, 2000);
            }
        });


        ekonomiakuntansi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //To show button click
                new Handler().postDelayed(new Runnable() {@Override public void run(){}}, 400);

                progressBar = new ProgressDialog(v.getContext());//Create new object of progress bar type
                progressBar.setCancelable(false);//Progress bar cannot be cancelled by pressing any wher on screen
                progressBar.setMessage("Mempersiapkan Pertanyaan ...");//Title shown in the progress bar
                progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);//Style of the progress bar
                progressBar.setProgress(0);//attributes
                progressBar.setMax(100);//attributes
                progressBar.show();//show the progress bar
                //This handler will add a delay of 3 seconds
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Intent start to open the navigation drawer activity
                        progressBar.cancel();//Progress bar will be cancelled (hide from screen) when this run function will execute after 3.5seconds
                        Intent intent = new Intent(Navigation_Activity.this, Questions.class);
                        intent.putExtra(Message, "ekonomiakuntansi");
                        startActivity(intent);
                    }
                }, 2000);
            }
        });


        bahasainggris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //To show button click
                new Handler().postDelayed(new Runnable() {@Override public void run(){}}, 400);

                progressBar = new ProgressDialog(v.getContext());//Create new object of progress bar type
                progressBar.setCancelable(false);//Progress bar cannot be cancelled by pressing any wher on screen
                progressBar.setMessage("Mempersiapkan Pertanyaan ...");//Title shown in the progress bar
                progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);//Style of the progress bar
                progressBar.setProgress(0);//attributes
                progressBar.setMax(100);//attributes
                progressBar.show();//show the progress bar
                //This handler will add a delay of 3 seconds
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Intent start to open the navigation drawer activity
                        progressBar.cancel();//Progress bar will be cancelled (hide from screen) when this run function will execute after 3.5seconds
                        Intent intent = new Intent(Navigation_Activity.this, Questions.class);
                        intent.putExtra(Message, "bahasainggris");
                        startActivity(intent);
                    }
                }, 2000);
            }
        });


        literatur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //To show button click
                new Handler().postDelayed(new Runnable() {@Override public void run(){}}, 400);

                progressBar = new ProgressDialog(v.getContext());//Create new object of progress bar type
                progressBar.setCancelable(false);//Progress bar cannot be cancelled by pressing any wher on screen
                progressBar.setMessage("Mempersiapkan Pertanyaan ...");//Title shown in the progress bar
                progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);//Style of the progress bar
                progressBar.setProgress(0);//attributes
                progressBar.setMax(100);//attributes
                progressBar.show();//show the progress bar
                //This handler will add a delay of 3 seconds
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Intent start to open the navigation drawer activity
                        progressBar.cancel();//Progress bar will be cancelled (hide from screen) when this run function will execute after 3.5seconds
                        Intent intent = new Intent(Navigation_Activity.this, Questions.class);
                        intent.putExtra(Message, "literatur");
                        startActivity(intent);
                    }
                }, 2000);
            }
        });


        intelegensi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //To show button click
                new Handler().postDelayed(new Runnable() {@Override public void run(){}}, 400);

                progressBar = new ProgressDialog(v.getContext());//Create new object of progress bar type
                progressBar.setCancelable(false);//Progress bar cannot be cancelled by pressing any wher on screen
                progressBar.setMessage("Mempersiapkan Pertanyaan ...");//Title shown in the progress bar
                progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);//Style of the progress bar
                progressBar.setProgress(0);//attributes
                progressBar.setMax(100);//attributes
                progressBar.show();//show the progress bar
                //This handler will add a delay of 3 seconds
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Intent start to open the navigation drawer activity
                        progressBar.cancel();//Progress bar will be cancelled (hide from screen) when this run function will execute after 3.5seconds
                        Intent intent = new Intent(Navigation_Activity.this, Questions.class);
                        intent.putExtra(Message, "intelegensi");
                        startActivity(intent);
                    }
                }, 2000);
            }
        });


        kepribadian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //To show button click
                new Handler().postDelayed(new Runnable() {@Override public void run(){}}, 400);

                progressBar = new ProgressDialog(v.getContext());//Create new object of progress bar type
                progressBar.setCancelable(false);//Progress bar cannot be cancelled by pressing any wher on screen
                progressBar.setMessage("Mempersiapkan Pertanyaan ...");//Title shown in the progress bar
                progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);//Style of the progress bar
                progressBar.setProgress(0);//attributes
                progressBar.setMax(100);//attributes
                progressBar.show();//show the progress bar
                //This handler will add a delay of 3 seconds
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Intent start to open the navigation drawer activity
                        progressBar.cancel();//Progress bar will be cancelled (hide from screen) when this run function will execute after 3.5seconds
                        Intent intent = new Intent(Navigation_Activity.this, Questions.class);
                        intent.putExtra(Message, "kepribadian");
                        startActivity(intent);
                    }
                }, 2000);
            }
        });


        profesi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddItemDialog(Navigation_Activity.this,v);
            }
        });

        grafikakomp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckNetwork.isInternetAvailable(Navigation_Activity.this)) //returns true if internet available
                {
                    showDialogGrafika(Navigation_Activity.this,v);
                }else {
                    try {
                        AlertDialog alertDialog = new AlertDialog.Builder(Navigation_Activity.this).create();

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

        kecerdasan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckNetwork.isInternetAvailable(Navigation_Activity.this)) //returns true if internet available
                {
                    showDialogKecerdasan(Navigation_Activity.this,v);
                }else {
                    try {
                        AlertDialog alertDialog = new AlertDialog.Builder(Navigation_Activity.this).create();

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

        rpl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckNetwork.isInternetAvailable(Navigation_Activity.this)) //returns true if internet available
                {
                    showDialogRpl(Navigation_Activity.this,v);
                }else {
                    try {
                        AlertDialog alertDialog = new AlertDialog.Builder(Navigation_Activity.this).create();

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

        pbo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckNetwork.isInternetAvailable(Navigation_Activity.this)) //returns true if internet available
                {
                    showDialogPbo(Navigation_Activity.this,v);
                }else {
                    try {
                        AlertDialog alertDialog = new AlertDialog.Builder(Navigation_Activity.this).create();

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

        strukturdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckNetwork.isInternetAvailable(Navigation_Activity.this)) //returns true if internet available
                {
                    showDialogStrukturData(Navigation_Activity.this,v);
                }else {
                    try {
                        AlertDialog alertDialog = new AlertDialog.Builder(Navigation_Activity.this).create();

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

        pemroginternet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckNetwork.isInternetAvailable(Navigation_Activity.this)) //returns true if internet available
                {
                    showDialogPemrogInternet(Navigation_Activity.this,v);
                }else {
                    try {
                        AlertDialog alertDialog = new AlertDialog.Builder(Navigation_Activity.this).create();

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
        jarkom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckNetwork.isInternetAvailable(Navigation_Activity.this)) //returns true if internet available
                {
                    showDialogJarkom(Navigation_Activity.this,v);
                }else {
                    try {
                        AlertDialog alertDialog = new AlertDialog.Builder(Navigation_Activity.this).create();

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
        desainweb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckNetwork.isInternetAvailable(Navigation_Activity.this)) //returns true if internet available
                {
                    showDialogDesainWeb(Navigation_Activity.this,v);
                }else {
                    try {
                        AlertDialog alertDialog = new AlertDialog.Builder(Navigation_Activity.this).create();

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

    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Klik BACK lagi untuk keluar", Toast.LENGTH_SHORT).show();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 4000);
    }

    private String cekHakAksesSoal(String nohp,String kodemakul){
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference().child("users").child(nohp);
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("yangpernahdiikuti").exists()) {
                    // Jika "yangpernahdiikuti" sudah ada
                    if(dataSnapshot.child("yangpernahdiikuti/"+kodemakul).exists()){
//                        boolean rplValue = dataSnapshot.child("yangpernahdiikuti/"+kodemakul).getValue(Boolean.class);
//                        if (rplValue) {
//                            // Jika rpl bernilai true, lakukan sesuatu
//                            bolehAksesSoal = "tidak";
//                            System.out.println("Data 'rpl' sudah ada dan bernilai true");
//                        }
                        bolehAksesSoal = "tidak";
                        System.out.println("Data "+kodemakul+" sudah ada");
                    }else {
                        bolehAksesSoal = "boleh";
                    }

                }else {
                    bolehAksesSoal = "boleh";
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Tangani kesalahan jika terjadi
                System.out.println("Error: " + databaseError.getMessage());
            }
        });
        return bolehAksesSoal;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_scorecard) {
            Intent intent = new Intent(this, ScoreCard.class);
            startActivity(intent);

        }
//        else if(id == R.id.nav_myprofile){
//            Intent intent = new Intent(this, Profile.class);
//            startActivity(intent);
//
//        }
        else if(id == R.id.nav_chat){
            Intent intent = new Intent(this, MainChatActivity.class);
            startActivity(intent);

        }
        else if (id == R.id.nav_scorekompetisi) {
            DialogInterface.OnClickListener dialogSetujuClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            if(getSudahPunyaAkunDariLogin.equals("0")){
                                Toast.makeText(getApplicationContext(), "Anda tidak memeiliki Izin lagi mengakses Leaderboard", Toast.LENGTH_SHORT).show();
                            }else{
                                //Yes button clicked
                                /*  startActivity(new Intent(this,Setting.class));*/
                                startActivity(new Intent(Navigation_Activity.this, MyPosition.class));
                            }
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            //No button clicked
                            break;
                    }
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder(Navigation_Activity.this);
            builder.setMessage("Jika Anda ingin melihat posisi skor dari semua user artinya Anda yakin akan mensubmit My Score Anda sekarang sehingga tidak bisa diubah lagi. Apakah ingin melanjutkan?").setPositiveButton("Ya", dialogSetujuClickListener)
                    .setNegativeButton("Batal", dialogSetujuClickListener).show();


        }else if (id == R.id.nav_Setting) {
              /*  startActivity(new Intent(this,Setting.class));*/
            startActivity(new Intent(this, Setting_activity.class));

        }else if (id == R.id.nav_socialmedia) {
            /*  startActivity(new Intent(this,Setting.class));*/
            startActivity(new Intent(this, MainActivity_Socialmedia.class));

        } else if (id == R.id.nav_share) {
            //shareApplication();
            Intent intent = new Intent(android.content.Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, "Amik PGRI Kebumen Quiz");
            intent.putExtra(Intent.EXTRA_TEXT, ""+getText(R.string.email_content)+getText(R.string.link));
            Intent chooser = Intent.createChooser(intent, "Share using");
            startActivity(chooser);
        } else if (id == R.id.nav_feedback) {
            String url = "https://docs.google.com/forms/d/1b16ed2y1NdxwQrdzcEns9zn0NjKo-jUYLCWny4XcP-w/edit?usp=drivesdk";
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));

        } else if (id == R.id.nav_aboutus) {
            Intent i = new Intent(this, AboutUs.class);
            startActivity(i);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sp = getSharedPreferences("Score", Context.MODE_PRIVATE);
        if (sp.getInt("Sound", 0) == 0)
            mediaPlayer.pause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        SharedPreferences sp = getSharedPreferences("Score", Context.MODE_PRIVATE);
        if (sp.getInt("Sound", 0) == 0)
            mediaPlayer.start();
    }

    private void showAddItemDialog(Context c,View v) {
        final EditText taskEditText = new EditText(c);
        AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle("Halaman Terbatas!")
                .setMessage("Masukan kode untuk mengikuti quiz")
                .setView(taskEditText)
                .setPositiveButton("Oke", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String task = String.valueOf(taskEditText.getText());
                        if(task.equals("dosen")){
                            //To show button click
                            new Handler().postDelayed(new Runnable() {@Override public void run(){}}, 400);
                            progressBar = new ProgressDialog(v.getContext());//Create new object of progress bar type
                            progressBar.setCancelable(false);//Progress bar cannot be cancelled by pressing any wher on screen
                            progressBar.setMessage("Mempersiapkan Pertanyaan ...");//Title shown in the progress bar
                            progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);//Style of the progress bar
                            progressBar.setProgress(0);//attributes
                            progressBar.setMax(100);//attributes
                            progressBar.show();//show the progress bar
                            //This handler will add a delay of 3 seconds
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    //Intent start to open the navigation drawer activity
                                    progressBar.cancel();//Progress bar will be cancelled (hide from screen) when this run function will execute after 3.5seconds
                                    Intent intent = new Intent(Navigation_Activity.this, Questions.class);
                                    intent.putExtra(Message, "profesi");
                                    startActivity(intent);
                                }
                            }, 2000);
                        }else{
                            Toast.makeText(Navigation_Activity.this, "Mohon maaf Anda tidak memiliki hak akses ke sini, silahkan hubungi Admin!", Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setNegativeButton("Tutup", null)
                .create();
        dialog.show();
    }

    private void showDialogGrafika(Context c,View v) {
        final EditText taskGrafika = new EditText(c);
        taskGrafika.requestFocus(); // Meminta fokus pada EditText
        taskGrafika.setFocusableInTouchMode(true); // Memastikan fokus pada EditText saat dialog ditampilkan
        AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle("Halaman Terbatas!")
                .setMessage("Masukan kode untuk mengikuti ujian")
                .setView(taskGrafika)
                .setPositiveButton("Oke", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String task = String.valueOf(taskGrafika.getText());
                        getKodeAkses(task,v,"Grafika Komputer","grafikakomp","sekaliikutgrafika");
                    }
                })
                .setNegativeButton("Tutup", null)
                .create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE); // Menampilkan keyboard saat dialog ditampilkan
        dialog.show();
    }

    private void showDialogPbo(Context c,View v) {
        final EditText taskPbo = new EditText(c);
        taskPbo.requestFocus(); // Meminta fokus pada EditText
        taskPbo.setFocusableInTouchMode(true); // Memastikan fokus pada EditText saat dialog ditampilkan
        AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle("Halaman Terbatas!")
                .setMessage("Masukan kode untuk mengikuti ujian")
                .setView(taskPbo)
                .setPositiveButton("Oke", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String task = String.valueOf(taskPbo.getText());
                        if(task.contains(kodeAksesUas)){
                            getKodeAkses(task, v, "UAS PBO", "uaspbo", "sekaliikutpbo");
                        }else {
                            getKodeAkses(task, v, "Pemrograman Berorientasi Objek", "pbo", "sekaliikutpbo");
                        }
                    }
                })
                .setNegativeButton("Tutup", null)
                .create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE); // Menampilkan keyboard saat dialog ditampilkan
        dialog.show();
    }
    private void showDialogKecerdasan(Context c,View v) {
        final EditText taskKecerdasan = new EditText(c);
        taskKecerdasan.requestFocus();
        taskKecerdasan.setFocusableInTouchMode(true);
        AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle("Halaman Terbatas!")
                .setMessage("Masukan kode untuk mengikuti ujian")
                .setView(taskKecerdasan)
                .setPositiveButton("Oke", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String task = String.valueOf(taskKecerdasan.getText());
                        getKodeAkses(task,v,"Kecerdasan Buatan","kecerdasan","sekaliikutkecerdasan");
                    }
                })
                .setNegativeButton("Tutup", null)
                .create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE); // Menampilkan keyboard saat dialog ditampilkan
        dialog.show();
    }
    private void showDialogRpl(Context c,View v) {
        final EditText taskRpl = new EditText(c);
        taskRpl.requestFocus();
        taskRpl.setFocusableInTouchMode(true);
        AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle("Halaman Terbatas!")
                .setMessage("Masukan kode untuk mengikuti ujian")
                .setView(taskRpl)
                .setPositiveButton("Oke", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String task = String.valueOf(taskRpl.getText());
                        getKodeAkses(task,v,"RPL","rpl","sekaliikutrpl");
                    }
                })
                .setNegativeButton("Tutup", null)
                .create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE); // Menampilkan keyboard saat dialog ditampilkan
        dialog.show();
    }
    private void showDialogStrukturData(Context c,View v) {
        final EditText taskStrukturData = new EditText(c);
        taskStrukturData.requestFocus();
        taskStrukturData.setFocusableInTouchMode(true);
        AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle("Halaman Terbatas!")
                .setMessage("Masukan kode untuk mengikuti ujian")
                .setView(taskStrukturData)
                .setPositiveButton("Oke", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String task = String.valueOf(taskStrukturData.getText());
                        if(task.contains(kodeAksesUas)){
                            getKodeAkses(task, v, "UAS Struktur Data", "uasstrukturdata", "sekaliikutstruktur");
                        }else {
                            getKodeAkses(task, v, "Struktur Data", "strukturdata", "sekaliikutstruktur");
                        }
                    }
                })
                .setNegativeButton("Tutup", null)
                .create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE); // Menampilkan keyboard saat dialog ditampilkan
        dialog.show();
    }
    private void showDialogPemrogInternet(Context c,View v) {
        final EditText taskPemrogInternet = new EditText(c);
        taskPemrogInternet.requestFocus();
        taskPemrogInternet.setFocusableInTouchMode(true);
        AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle("Halaman Terbatas!")
                .setMessage("Masukan kode untuk mengikuti ujian")
                .setView(taskPemrogInternet)
                .setPositiveButton("Oke", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String task = String.valueOf(taskPemrogInternet.getText());
                        if(task.contains(kodeAksesUas)) {
                            getKodeAkses(task, v, "UAS Pemrograman Internet", "uaspemrogramaninternet", "sekaliikutinternet");
                        }else {
                            getKodeAkses(task, v, "Pemrograman Internet", "pemrogramaninternet", "sekaliikutinternet");
                        }
                    }
                })
                .setNegativeButton("Tutup", null)
                .create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE); // Menampilkan keyboard saat dialog ditampilkan
        dialog.show();
    }
    private void showDialogJarkom(Context c,View v) {
        final EditText taskJarkom = new EditText(c);
        taskJarkom.requestFocus();
        taskJarkom.setFocusableInTouchMode(true);
        AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle("Halaman Terbatas!")
                .setMessage("Masukan kode untuk mengikuti ujian")
                .setView(taskJarkom)
                .setPositiveButton("Oke", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String task = String.valueOf(taskJarkom.getText());
                        getKodeAkses(task,v,"Jaringan Komputer","jaringankomputer","sekaliikutjarkom");
                    }
                })
                .setNegativeButton("Tutup", null)
                .create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE); // Menampilkan keyboard saat dialog ditampilkan
        dialog.show();
    }
    private void showDialogDesainWeb(Context c,View v) {
        final EditText taskDesainWeb = new EditText(c);
        taskDesainWeb.requestFocus();
        taskDesainWeb.setFocusableInTouchMode(true);
        AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle("Halaman Terbatas!")
                .setMessage("Masukan kode untuk mengikuti ujian")
                .setView(taskDesainWeb)
                .setPositiveButton("Oke", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String task = String.valueOf(taskDesainWeb.getText());
                        getKodeAkses(task,v,"Desain Web","desainweb","sekaliikutdesainweb");
                    }
                })
                .setNegativeButton("Tutup", null)
                .create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE); // Menampilkan keyboard saat dialog ditampilkan
        dialog.show();
    }

    private boolean getKodeAkses(String kodeInputan,View v,String makul, String valueMakulIntent, String sekaliikutkode){
        cekHakAksesSoal(nope,valueMakulIntent);
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference(); // Mendapatkan referensi ke Realtime Database
        // Menampilkan ProgressDialog sebelum mengecek data di Firebase
        progressBar = new ProgressDialog(Navigation_Activity.this);
        progressBar.setCancelable(false);
        progressBar.setMessage("Mengecek hak akses akses "+makul);
        progressBar.show();
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String kodeAkses = dataSnapshot.child("kodeakses").getValue(String.class);
                // Lakukan pengecekan kondisi if-else di sini
                if (kodeAkses != null && kodeAkses.equals(kodeInputan)) {
                    //awalnya passnya sayamengerjakandenganjujur
                    //To show button click
//                            cekHakAksesSoal();
                    if(sharedPreferences.getString(sekaliikutkode,"1").equals("0")){
                        Toast.makeText(Navigation_Activity.this, "Anda tidak diizinkan mengikuti lebih dari 1x", Toast.LENGTH_LONG).show();
                        progressBar.dismiss();
                    }
                    else {
                        if (bolehAksesSoal.equals("boleh")) {
                            if(kodeInputan.contains(kodeAksesUas)){
                                progressBar.dismiss();
                                Intent intent = new Intent(Navigation_Activity.this, QuestionsUas.class);
                                intent.putExtra(Message, valueMakulIntent);
                                startActivity(intent);
                            }else {
                                progressBar.dismiss();
                                Intent intent = new Intent(Navigation_Activity.this, Questions.class);
                                intent.putExtra(Message, valueMakulIntent);
                                startActivity(intent);
                            }
                        }else if(bolehAksesSoal.equals("tidak")){
                            Toast.makeText(Navigation_Activity.this, "Anda tidak diizinkan mengikuti soal ini untuk kedua kalinya", Toast.LENGTH_SHORT).show();
                            progressBar.dismiss();
                        }
                    }
                } else {
                    // Lakukan tindakan jika kondisi tidak terpenuhi
                    Toast.makeText(Navigation_Activity.this, "Mohon maaf Anda tidak memiliki hak akses ke sini, silahkan hubungi Admin!", Toast.LENGTH_LONG).show();
                    progressBar.dismiss();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Tangani kesalahan bila terjadi
                progressBar.dismiss();
                Toast.makeText(Navigation_Activity.this, "Tidak bisa ada response dari server!", Toast.LENGTH_LONG).show();
            }
        });
        return true;
    }
}

