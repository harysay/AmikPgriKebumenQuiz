package com.harysaydev.amikpgrikbmquiz;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.harysaydev.amikpgrikbmquiz.helper.CheckNetwork;
import com.harysaydev.amikpgrikbmquiz.screenshoot.FileUtil;
import com.harysaydev.amikpgrikbmquiz.screenshoot.ScreenshotUtil;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Result extends AppCompatActivity implements View.OnClickListener {
    TextView judulHasil,totalSoal, correct, incorrect, attempted, score, nilai, you,namapengguna;
    Button fullPageScreenshot;
    private LinearLayout rootContent;
    private static final String TAG = "ResultMenu";
    FirebaseDatabase databaseFb;
    DatabaseReference tabel_skor;
    String nomor_phone,header_name;
    //private ImageView imageView;
    int cor = 0, incorr = 0, attempt = 0, scor = 0, jumSoal = 0;
    int x = 0;
    private static final int def = 0;
    private Toast mToastToShow;
    private Bitmap bitmap;
    Date currentTime = Calendar.getInstance().getTime();
    String kodeMakul;
    float nilaiAkhirUser;
    ProgressBar progressBar;
    String namapeng,kodeunikpengguna;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        SharedPreferences shared = getSharedPreferences("Score", Context.MODE_PRIVATE);
        SharedPreferences sharedPreferences = getSharedPreferences("Content_main", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        nomor_phone = sharedPreferences.getString("phone", "0895325322911");
        header_name = sharedPreferences.getString("name", "xyz");
        kodeunikpengguna = sharedPreferences.getString("kodeunik","tidak terdeteksi");
        progressBar = new ProgressBar(Result.this);
//        int grafika = shared.getInt("Grafikakomputer", 0);
//        int pbo = shared.getInt("Pbo", 0);
//        int kecerdasan = shared.getInt("Kecerdasanbuatan", 0);
//        int rpl = shared.getInt("Rpl", 0);


        Intent intent = getIntent();
        cor = intent.getIntExtra("correct", 0);
        attempt = intent.getIntExtra("attemp", 0);
        jumSoal = intent.getIntExtra("jumlahsoal",0);
        kodeMakul = intent.getStringExtra("kodemakul");
        databaseFb = FirebaseDatabase.getInstance();
        tabel_skor  = databaseFb.getReference("ResultPerTest");

        incorr = attempt - cor;
        scor = 10 * cor; //nilai benar dikali 10
        rootContent = (LinearLayout) findViewById(R.id.root_content);
        fullPageScreenshot = (Button) findViewById(R.id.shareResult);
        //imageView = (ImageView) findViewById(R.id.image_viewScreenshoot);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        judulHasil = (TextView) findViewById(R.id.judulHasil);
        totalSoal = (TextView) findViewById(R.id.totalsoal);
        correct = (TextView) findViewById(R.id.correct);
        incorrect = (TextView) findViewById(R.id.incorrect);
        attempted = (TextView) findViewById(R.id.attempted);
        score = (TextView) findViewById(R.id.score);
        nilai = (TextView) findViewById(R.id.nilai);
        you = (TextView) findViewById(R.id.you);
        namapengguna = (TextView) findViewById(R.id.namaPengguna);
        String buatJudul = intent.getStringExtra("makul");
        namapeng = intent.getStringExtra("jeneng");
        fullPageScreenshot.setOnClickListener(this);
        judulHasil.setText("Statistik "+buatJudul);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            namapengguna.setText(namapeng);
        }else{
            namapengguna.setText(namapeng +"("+kodeunikpengguna+")");
        }
        totalSoal.setText("  " +jumSoal);
        attempted.setText("  " + attempt);
        correct.setText("  " + cor);
        incorrect.setText("  " + incorr);
        score.setText("Points  :    " + scor);
        nilaiAkhirUser = (float)cor / jumSoal * 100 ; //gunakan jumlah soal sebagai pembagi karena bisa jadi user baru mengikuti 2 soal benar semua
//        tambahDaftarMengikuti(kodeMakul,String.format ("%.2f", nilaiAkhirUser));
//        if (kodeMakul.equals("kepribadian")){
//            x1 = (float)cor * 5 ;
//        }else{
//            x1 = (float)cor / attempt * 100 ;
//        }
        nilai.setText(String.format ("%.2f", nilaiAkhirUser));
        if (nilaiAkhirUser < 40)
            you.setText("Kamu harus belajar lagi!");
        else if (nilaiAkhirUser < 75)
            you.setText("Masih kurang belajar, tingkatkan lagi!");
        else if (nilaiAkhirUser < 90)
            you.setText("Anda Berhasil Passing Grade");
        else if (nilaiAkhirUser >= 90){
            you.setText("You are a brilliant!");
        }
        if (CheckNetwork.isInternetAvailable(Result.this)) //returns true if internet available
        {
            submitNilai(kodeMakul, nilaiAkhirUser);
        }else {
            try {
                AlertDialog alertDialog = new AlertDialog.Builder(Result.this).create();
                alertDialog.setTitle("No Internet");
                alertDialog.setMessage("Nyalakan kembali koneksi internet Anda untuk mengirim nilai (tidak mengapa pindah aplikasi jika sudah sampai sini)");
                alertDialog.setIcon(android.R.drawable.stat_sys_warning);
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int n) {
                        // Buka pengaturan jaringan
                        startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
                        dialog.cancel();
                    }
                });
                alertDialog.show();
            } catch (Exception e) {
                Log.d(TAG, "Log tidak konek: "+e.getMessage());
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        // Jika pengguna kembali dengan koneksi internet, lanjutkan submit nilai
        if (CheckNetwork.isInternetAvailable(Result.this)) //returns true if internet available
        {
        submitNilai(kodeMakul, nilaiAkhirUser);
        }else {
            try {
                AlertDialog alertDialog = new AlertDialog.Builder(Result.this).create();
                alertDialog.setTitle("No Internet");
                alertDialog.setMessage("Nyalakan kembali koneksi internet Anda untuk mengirim nilai (tidak mengapa pindah aplikasi jika sudah sampai sini)");
                alertDialog.setIcon(android.R.drawable.stat_sys_warning);
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int n) {
                        // Buka pengaturan jaringan
                        startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
                        dialog.cancel();
                    }
                });
                alertDialog.show();
            } catch (Exception e) {
                Log.d(TAG, "Log tidak konek: "+e.getMessage());
            }
        }

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shareResult:
                float nilaiAkhir = (float)cor / jumSoal * 100;
                int nilaiTanpaDesimal = (int) (nilaiAkhir * 100);//dilakukan seperti ini supaya pengguna tidak mengubah2 nilai manual sendiri
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "Saya "+namapeng+kodeunikpengguna+nilaiTanpaDesimal+" mengerjakan soal dengan benar ("+cor+") dari total soal seharusnya ("+jumSoal+")");
                    // Mengatur tipe konten ke teks
                    sendIntent.setType("text/plain");
                    // Mengatur paket WhatsApp untuk menentukan aplikasi yang akan menangani intent
                    sendIntent.setPackage("com.whatsapp");
                    try {
                        // Memulai intent untuk berbagi ke WhatsApp
                        startActivity(sendIntent);
                    } catch (ActivityNotFoundException e) {
                        // Tangani jika WhatsApp tidak terinstall
                        Toast.makeText(this, "WhatsApp tidak terpasang pada perangkat ini", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Versi Android di bawah 13 (sebelum Android 13)
                    System.out.println("Perangkat berjalan pada versi Android sebelum Android 13");
                    bitmap = ScreenshotUtil.getInstance().takeScreenshotForScreen(Result.this); // Take ScreenshotUtil for activity
                    requestPermissionAndSave();
                }

                //fullPageScreenshot.setBackgroundColor(getResources().getColor(R.color.warnashareresult));
                break;
        }
    }
    private void requestPermissionAndSave() {

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        if (bitmap != null) {
                            String path = Environment.getExternalStorageDirectory().toString() + "/result.png";
                            FileUtil.getInstance().storeBitmap(bitmap, path);
                            Uri imgUri = Uri.parse(path);
                            Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                            whatsappIntent.setType("text/plain");
                            whatsappIntent.setPackage("com.whatsapp");
                            whatsappIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.sharing_text));
                            whatsappIntent.putExtra(Intent.EXTRA_STREAM, imgUri);
                            whatsappIntent.setType("image/png");
                            whatsappIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            try {
                                startActivity(whatsappIntent);
                            } catch (android.content.ActivityNotFoundException ex) {
                                Toast.makeText(Result.this, "Whatsapp have not been installed.", Toast.LENGTH_SHORT).show();
                            }
                            //shareScreenshot(saveFile);//finally share screenshot
//                            Toast.makeText(Result.this, getString(R.string.toast_message_screenshot_success) + " " + path, Toast.LENGTH_LONG).show();
                            Toast.makeText(Result.this, getString(R.string.toast_message_screenshot_success), Toast.LENGTH_LONG).show();
                            //shareScreenshot(saveFile);//finally share screenshot
                        } else {
                            Toast.makeText(Result.this, getString(R.string.toast_message_screenshot), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        // check for permanent denial of permission
                        if (response.isPermanentlyDenied()) {
                            // Izin ditolak secara permanen, buka pengaturan aplikasi untuk izin yang ditolak
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getPackageName(), null);
                            intent.setData(uri);
                            startActivity(intent);

                            // Tampilkan pesan yang sesuai
                            Toast.makeText(Result.this, "Izin ditolak secara permanen, harap izinkan untuk melanjutkan.", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void submitNilai(String kodeMakul, float x1){
        progressBar.setVisibility(View.VISIBLE);
        tambahDaftarMengikuti(kodeMakul,String.format ("%.2f", nilaiAkhirUser));
            tabel_skor.child(nomor_phone).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(kodeMakul.equals("grafikakomp")){
                    masukanData(dataSnapshot,"resultGrafikaKomputer","Grafika Komputer",x1);
                }else if(kodeMakul.equals("pbo")){
                    masukanData(dataSnapshot,"resultPBO","PBO",x1);
                }else if(kodeMakul.equals("kecerdasan")){
                    masukanData(dataSnapshot,"resultKecerdasanBuatan","Kecerdasan Buatan",x1);
                }else if(kodeMakul.equals("rpl")){
                    masukanData(dataSnapshot,"resultRPL","RPL",x1);
                }else if(kodeMakul.equals("strukturdata")){
                    masukanData(dataSnapshot,"resultStrukturData","Struktur Data",x1);
                }else if(kodeMakul.equals("pemrogramaninternet")){
                    masukanData(dataSnapshot,"resultPemrogramanInternet","Pemrograman Internet",x1);
                }else if(kodeMakul.equals("jaringankomputer")){
                    masukanData(dataSnapshot,"resultJarkom","Jaringan Komputer",x1);
                }else if(kodeMakul.equals("desainweb")){
                    masukanData(dataSnapshot,"resultDesainWeb","Desain Web",x1);
                }else{
                    showToast();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void masukanData(DataSnapshot dataSnapshot,String kodemakul, String namamakul,float x1){
        // Periksa apakah nomor telepon ada di dalam database
        if (dataSnapshot.exists()) {
            if (dataSnapshot.hasChild(kodemakul)) {
                // Data mata kuliah sudah ada
                Toast.makeText(getApplicationContext(), "Anda sudah pernah mengerjakan soal "+namamakul, Toast.LENGTH_SHORT).show();
            } else {
                // Data mata kuliah belum ada
                tabel_skor.child(nomor_phone).child(kodemakul).setValue(String.valueOf(x1)+" ("+currentTime.toString()+")");
                showToastSukses();
            }
        } else {
            // Data nomor telepon belum ada
            tabel_skor.child(nomor_phone).child("namaPengguna").setValue(header_name);
            tabel_skor.child(nomor_phone).child(kodemakul).setValue(String.valueOf(x1)+" ("+currentTime.toString()+")");
            showToastSukses();
        }
    }
    public void tambahDaftarMengikuti(String kodemakul,String nilaiAkhir){
        if (nomor_phone != null) {
            if (CheckNetwork.isInternetAvailable(Result.this)) //returns true if internet available
            {
                DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference().child("users").child(nomor_phone);
                // Lakukan pengecekan apakah kodemakul sudah ada di dalam yangpernahdiikuti
                databaseRef.child("yangpernahdiikuti").child(kodemakul).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // Jika kodemakul sudah ada
                            System.out.println("Informasi " + kodemakul + " sudah ada.");
                        } else {
                            // Jika kodemakul belum ada, tambahkan informasi baru
                            Map<String, Object> updates = new HashMap<>();
                            updates.put("yangpernahdiikuti/" + kodemakul, nilaiAkhir);
                            databaseRef.updateChildren(updates)
                                    .addOnSuccessListener(aVoid -> {
                                        // Proses berhasil
                                        System.out.println("Informasi " + kodemakul + " berhasil ditambahkan.");
                                    })
                                    .addOnFailureListener(e -> {
                                        // Proses gagal
                                        System.out.println("Gagal menambahkan informasi " + kodemakul + ": " + e.getMessage());
                                    });
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Tangani kesalahan jika terjadi
                        System.out.println("Error: " + databaseError.getMessage());
                    }
                });
            }else {
                try {
                    AlertDialog alertDialog = new AlertDialog.Builder(Result.this).create();
                    alertDialog.setTitle("No Internet");
                    alertDialog.setMessage("Nyalakan kembali koneksi internet Anda untuk mengirim nilai (tidak mengapa pindah aplikasi jika sudah sampai sini)");
                    alertDialog.setIcon(android.R.drawable.stat_sys_warning);
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int n) {
                            // Buka pengaturan jaringan
                            startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
                            dialog.cancel();
                        }
                    });
                    alertDialog.show();
                } catch (Exception e) {
                    Log.d(TAG, "Log tidak konek: "+e.getMessage());
                }
            }

        }
    }
    public void showToast() {
        // Set the toast and duration
        int toastDurationInMilliSeconds = 3000; //5000 = 5 detik
        mToastToShow = Toast.makeText(this, "Nilai ini tidak diambil untuk penilaian kuliah!", Toast.LENGTH_SHORT);

        // Set the countdown to display the toast
        CountDownTimer toastCountDown;
        toastCountDown = new CountDownTimer(toastDurationInMilliSeconds, 1000 /*Tick duration*/) {
            public void onTick(long millisUntilFinished) {
                mToastToShow.show();
            }
            public void onFinish() {
                mToastToShow.cancel();
            }
        };

        // Show the toast and starts the countdown
        mToastToShow.show();
        toastCountDown.start();
    }

    public void showToastSukses() {
        // Set the toast and duration
        int toastDurationInMilliSeconds = 10000;
        mToastToShow = Toast.makeText(this, "Nilai Anda telah terekam!", Toast.LENGTH_LONG);

        // Set the countdown to display the toast
        CountDownTimer toastCountDown;
        toastCountDown = new CountDownTimer(toastDurationInMilliSeconds, 1000 /*Tick duration*/) {
            public void onTick(long millisUntilFinished) {
                mToastToShow.show();
            }
            public void onFinish() {
                mToastToShow.cancel();
            }
        };

        // Show the toast and starts the countdown
        mToastToShow.show();
        toastCountDown.start();
    }
}
