package com.harysaydev.amikpgrikbmquiz;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import androidx.appcompat.app.AppCompatActivity;

import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
    TextView judulHasil, correct, incorrect, attempted, score, nilai, you,namapengguna;
    Button fullPageScreenshot;
    private LinearLayout rootContent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        SharedPreferences shared = getSharedPreferences("Score", Context.MODE_PRIVATE);
        SharedPreferences sharedPreferences = getSharedPreferences("Content_main", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        nomor_phone = sharedPreferences.getString("phone", "0895325322911");
        header_name = sharedPreferences.getString("name", "xyz");
//        int grafika = shared.getInt("Grafikakomputer", 0);
//        int pbo = shared.getInt("Pbo", 0);
//        int kecerdasan = shared.getInt("Kecerdasanbuatan", 0);
//        int rpl = shared.getInt("Rpl", 0);


        Intent intent = getIntent();
        cor = intent.getIntExtra("correct", 0);
        attempt = intent.getIntExtra("attemp", 0);
        jumSoal = intent.getIntExtra("jumlahsoal",0);
        String kodeMakul = intent.getStringExtra("kodemakul");
        tambahDaftarMengikuti(kodeMakul);
        databaseFb = FirebaseDatabase.getInstance();
        tabel_skor  = databaseFb.getReference("ResultPerTest");


        incorr = attempt - cor;
        scor = 10 * cor; //nilai benar dikali 10
        rootContent = (LinearLayout) findViewById(R.id.root_content);
        fullPageScreenshot = (Button) findViewById(R.id.shareResult);
        //imageView = (ImageView) findViewById(R.id.image_viewScreenshoot);
        judulHasil = (TextView) findViewById(R.id.judulHasil);
        correct = (TextView) findViewById(R.id.correct);
        incorrect = (TextView) findViewById(R.id.incorrect);
        attempted = (TextView) findViewById(R.id.attempted);
        score = (TextView) findViewById(R.id.score);
        nilai = (TextView) findViewById(R.id.nilai);
        you = (TextView) findViewById(R.id.you);
        namapengguna = (TextView) findViewById(R.id.namaPengguna);
        String buatJudul = intent.getStringExtra("makul");
        String namapeng = intent.getStringExtra("jeneng");
        fullPageScreenshot.setOnClickListener(this);
        judulHasil.setText("Statistik "+buatJudul);
        namapengguna.setText(namapeng +"("+sharedPreferences.getString("kodeunik","tidak terdeteksi")+")");
        attempted.setText("  " + attempt);
        correct.setText("  " + cor);
        incorrect.setText("  " + incorr);
        score.setText("Points  :    " + scor);
        float x1 = (float)cor / jumSoal * 100 ;
//        if (kodeMakul.equals("kepribadian")){
//            x1 = (float)cor * 5 ;
//        }else{
//            x1 = (float)cor / attempt * 100 ;
//        }
        nilai.setText(String.format ("%.2f", x1));
        if (x1 < 40)
            you.setText("Kamu harus belajar lagi!");
        else if (x1 < 75)
            you.setText("Masih kurang belajar, tingkatkan lagi!");
        else if (x1 < 90)
            you.setText("Anda Berhasil Passing Grade");
        else if (x1 >= 90){
            you.setText("You are a brilliant!");
        }
        tabel_skor.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //ResultPerTest finishQuiz = new ResultPerTest();
                //String coba = header_name;
                if(kodeMakul.equals("grafikakomp")){
                    if(dataSnapshot.child(nomor_phone).child("resultGrafikaKomputer").exists()){
                        if(!dataSnapshot.child(nomor_phone).child("namaPengguna").exists()){
                            showToastDeteksi();
                            tabel_skor.child(nomor_phone).child("namaPengguna").setValue(header_name);
                        }else{
                            showToastDeteksi();
                        }
                    }
                    else {
                        tabel_skor.child(nomor_phone).child("resultGrafikaKomputer").setValue(String.valueOf(x1)+" ("+currentTime.toString()+")");
                        showToastSukses();
                    }
                }else if(kodeMakul.equals("pbo")){
                    if(dataSnapshot.child(nomor_phone).child("resultPBO").exists()){
                        if(!dataSnapshot.child(nomor_phone).child("namaPengguna").exists()){
                            showToastDeteksi();
                            tabel_skor.child(nomor_phone).child("namaPengguna").setValue(header_name);
                        }else{
                            showToastDeteksi();
                        }
                    }
                    else {
                        tabel_skor.child(nomor_phone).child("resultPBO").setValue(String.valueOf(x1)+" ("+currentTime.toString()+")");
                        showToastSukses();
                    }
                }else if(kodeMakul.equals("kecerdasan")){
                    if(dataSnapshot.child(nomor_phone).child("resultKecerdasanBuatan").exists()){
                        if(!dataSnapshot.child(nomor_phone).child("namaPengguna").exists()){
                            showToastDeteksi();
                            tabel_skor.child(nomor_phone).child("namaPengguna").setValue(header_name);
                        }else{
                            showToastDeteksi();
                        }
                    }
                    else {
                        tabel_skor.child(nomor_phone).child("resultKecerdasanBuatan").setValue(String.valueOf(x1)+" ("+currentTime.toString()+")");
                        showToastSukses();
                    }
                }else if(kodeMakul.equals("rpl")){
                    if(dataSnapshot.child(nomor_phone).child("resultRPL").exists()){
                        if(!dataSnapshot.child(nomor_phone).child("namaPengguna").exists()){
                            showToastDeteksi();
                            tabel_skor.child(nomor_phone).child("namaPengguna").setValue(header_name);
                        }else{
                            showToastDeteksi();
                        }
                    }else {
                        tabel_skor.child(nomor_phone).child("resultRPL").setValue(String.valueOf(x1)+" ("+currentTime.toString()+")");
                        showToastSukses();
                    }
                }else if(kodeMakul.equals("strukturdata")){
                    if(dataSnapshot.child(nomor_phone).child("resultStrukturData").exists()){
                        if(!dataSnapshot.child(nomor_phone).child("namaPengguna").exists()){
                            showToastDeteksi();
                            tabel_skor.child(nomor_phone).child("namaPengguna").setValue(header_name);
                        }else{
                            showToastDeteksi();
                        }
                    }else {
                        tabel_skor.child(nomor_phone).child("resultStrukturData").setValue(String.valueOf(x1)+" ("+currentTime.toString()+")");
                        showToastSukses();
                    }
                }else if(kodeMakul.equals("pemrogramaninternet")){
                    if(dataSnapshot.child(nomor_phone).child("resultPemrogramanInternet").exists()){
                        if(!dataSnapshot.child(nomor_phone).child("namaPengguna").exists()){
                            showToastDeteksi();
                            tabel_skor.child(nomor_phone).child("namaPengguna").setValue(header_name);
                        }else{
                            showToastDeteksi();
                        }
                    }else {
                        tabel_skor.child(nomor_phone).child("resultPemrogramanInternet").setValue(String.valueOf(x1)+" ("+currentTime.toString()+")");
                        showToastSukses();
                    }
                }else if(kodeMakul.equals("jaringankomputer")){
                    if(dataSnapshot.child(nomor_phone).child("resultJarkom").exists()){
                        if(!dataSnapshot.child(nomor_phone).child("namaPengguna").exists()){
                            showToastDeteksi();
                            tabel_skor.child(nomor_phone).child("namaPengguna").setValue(header_name);
                        }else{
                            showToastDeteksi();
                        }
                    }
                    else {
                        tabel_skor.child(nomor_phone).child("resultDesainWeb").setValue(String.valueOf(x1)+" ("+currentTime.toString()+")");
                        showToastSukses();
                    }
                }
                else if(kodeMakul.equals("desainweb")){
                    if(dataSnapshot.child(nomor_phone).child("resultDesainWeb").exists()){
                        if(!dataSnapshot.child(nomor_phone).child("namaPengguna").exists()){
                            showToastDeteksi();
                            tabel_skor.child(nomor_phone).child("namaPengguna").setValue(header_name);
                        }else{
                            showToastDeteksi();
                        }
                    }
                    else {
                        tabel_skor.child(nomor_phone).child("resultDesainWeb").setValue(String.valueOf(x1)+" ("+currentTime.toString()+")");
                        showToastSukses();
                    }
                }
                else{
                    showToast();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shareResult:
                //Toast.makeText(this, "Tunggu menu share muncul!", Toast.LENGTH_SHORT).show();
                //fullPageScreenshot.setBackgroundColor(getResources().getColor(R.color.light_gray));
                //takeScreenshot(ScreenshotType.FULL);
                float nilaiAkhir = (float)cor / jumSoal * 100;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    // Versi Android 13 atau di atasnya (Android T atau di atasnya)
                    // Lakukan sesuatu untuk Android 13 atau di atasnya
                    System.out.println("Perangkat berjalan pada Android 13 atau di atasnya");
                    Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                    whatsappIntent.setType("text/plain");
                    whatsappIntent.setPackage("com.whatsapp");
                    whatsappIntent.putExtra(Intent.EXTRA_TEXT, "Saya "+namapengguna.getText().toString()+" mendapat nilai akhir"+nilaiAkhir+" dengan jumlah benar ("+cor+")");
                    try {
                        startActivity(whatsappIntent);
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(Result.this, "Whatsapp have not been installed.", Toast.LENGTH_SHORT).show();
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

    /**
     * Requesting storage permission
     * Once the permission granted, screen shot captured
     * On permanent denial show toast
     */
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

    /*  Show screenshot Bitmap */
//    private void showScreenShotImage(Bitmap b) {
//        imageView.setImageBitmap(b);
//    }

    /*  Share Screenshot  */
    private void shareScreenshot(File file) {
        //Uri uri = Uri.fromFile(file);//Convert file path into Uri for sharing

    }
    public void tambahDaftarMengikuti(String kodemakul){
        if (nomor_phone != null) {
            DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference().child("users").child(nomor_phone);
            // Siapkan data yang ingin Anda tambahkan
            Map<String, Object> updates = new HashMap<>();
            updates.put("yangpernahdiikuti/"+kodemakul, true); // Menambah informasi kodemakul

            // Update data di Firebase Database
            databaseRef.updateChildren(updates)
                    .addOnSuccessListener(aVoid -> {
                        // Proses berhasil
                        System.out.println("Informasi "+kodemakul+" berhasil ditambahkan.");
                    })
                    .addOnFailureListener(e -> {
                        // Proses gagal
                        System.out.println("Gagal menambahkan informasi "+kodemakul+": " + e.getMessage());
                    });
        }
    }
    public void showToast() {
        // Set the toast and duration
        int toastDurationInMilliSeconds = 3000; //5000 = 5 detik
        mToastToShow = Toast.makeText(this, "Nilai ini tidak diambil untuk penilaian kuliah karena Anda pernah mengikuti sebelumnya!", Toast.LENGTH_SHORT);

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

    public void showToastDeteksi() {
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

    public void showToastSukses() {
        // Set the toast and duration
        int toastDurationInMilliSeconds = 10000;
        mToastToShow = Toast.makeText(this, "Sukses Input Nilai!", Toast.LENGTH_LONG);

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
