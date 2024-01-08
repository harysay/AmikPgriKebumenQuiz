package com.harysaydev.amikpgrikbmquiz;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.Settings;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import com.androidnetworking.AndroidNetworking;
import com.google.android.material.snackbar.Snackbar;
import com.harysaydev.amikpgrikbmquiz.bottomsheet.MainFragmentBottomsheet;
import com.harysaydev.amikpgrikbmquiz.soal.Bahasainggris;
import com.harysaydev.amikpgrikbmquiz.soal.Desainweb;
import com.harysaydev.amikpgrikbmquiz.soal.Ekonomiakuntansi;
import com.harysaydev.amikpgrikbmquiz.soal.Grafikakomputer;
import com.harysaydev.amikpgrikbmquiz.soal.Intelegensi;
import com.harysaydev.amikpgrikbmquiz.soal.JaringanKomputer;
import com.harysaydev.amikpgrikbmquiz.soal.Kebangsaan;
import com.harysaydev.amikpgrikbmquiz.soal.Kecerdasanbuatan;
import com.harysaydev.amikpgrikbmquiz.soal.Kepribadian;
import com.harysaydev.amikpgrikbmquiz.soal.Komputer;
import com.harysaydev.amikpgrikbmquiz.soal.Literatur;
import com.harysaydev.amikpgrikbmquiz.soal.PemBerorientasiObjek;
import com.harysaydev.amikpgrikbmquiz.soal.PemBerorientasiObjekUas;
import com.harysaydev.amikpgrikbmquiz.soal.PemrogramanInternet;
import com.harysaydev.amikpgrikbmquiz.soal.Pengetahuankampus;
import com.harysaydev.amikpgrikbmquiz.soal.Pengetahuanumum;
import com.harysaydev.amikpgrikbmquiz.soal.Profesi;
import com.harysaydev.amikpgrikbmquiz.soal.RekPerangkatLunak;
import com.harysaydev.amikpgrikbmquiz.soal.StrukturData;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class QuestionsUas extends AppCompatActivity implements View.OnClickListener {
    private long timeCountInMilliSeconds = 1 * 61000;
    private boolean isScreenCaptured = false;
    private long duamenit = 2 * 60500; //supaya terlihat oleh user saat perhitungan awal dimulai dari 120 maka agak ditambahkan 1 detik
    public SQLiteDatabase sqlite;
    String idMapel,namaMapel,kodemakul;
    private static final String TAG = "Questions";
    int variable =1,penandabottonplay = 0;
    private SharedPreferences sharedku;
    TextView ques,soalYangKe,jumlahSoal,garing,info,lemparanParam;
    ImageView pertanyaanGambar;
    private final String Default = "";
    Button OptA, OptB, OptC, OptD;
    Button play_button;
    String get;
    com.harysaydev.amikpgrikbmquiz.soal.PemBerorientasiObjekUas PemBerorientasiObjek;
    int jumSoal=60,totalRecord,backsebelummulai;

    public int visibility = 0, komputer = 0, kebangsaan = 0, kampus = 0, pengetahuanumum = 0, ekonomiakuntansi = 0, bahasainggris = 0, literatur = 0, intelegensi = 0, kepribadian = 0, profesi = 0,grafikom = 0,kecerdasan = 0,rpl = 0,pbo = 0,struktur=0,pemrograminternet=0, i, j = 0, soalKe = 0, l = 0;
    String global = null, Ques, Opta, Optb, Optc, Optd, Pembahas;
    ArrayList<Integer> list = new ArrayList<Integer>();
    Toast toast;
    MediaPlayer mediaPlayer;
    private ProgressBar progressBarCircle;
    private TextView textViewTime;
    private CountDownTimer countDownTimer;
    ProgressDialog mProgressDialog;
    //MainFragmentBottomsheet fragment;
    View viewLemparan;

    private enum TimerStatus {
        STARTED,
        STOPPED
    }
    private TimerStatus timerStatus = TimerStatus.STOPPED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        backsebelummulai = 1;
        variable = 1;
        sharedku = getSharedPreferences("Content_main", Context.MODE_PRIVATE);

        Intent intent = getIntent();//recieving the intent send by the Navigation activity
        get = intent.getStringExtra(Navigation_Activity.Message);//converting that intent message to string using the getStringExtra() method
        if(get.equals("strukturdata")){
            idMapel = "1";
            namaMapel = "Struktur Data";
            showLockScreenDialog();
        } else if(get.equals("bahasainggris")){
            idMapel = "2";
            namaMapel = "Bahasa Inggris";
        }else if(get.equals("pemrogramanterstruktur")){
            idMapel = "3";
            namaMapel = "Pemrograman Terstruktur";
            showLockScreenDialog();
        }else if(get.equals("pengetahuanumum")){
            idMapel = "4";
            namaMapel = "Pengetahuan Umum";
        }else if(get.equals("uaspbo")){
            idMapel = "5";
            namaMapel = "UAS Pemrograman Berorientasi Objek";
            showLockScreenDialog();
        }else if(get.equals("kecerdasan")){
            idMapel = "6";
            namaMapel = "Kecerdasan Buatan";
            showLockScreenDialog();
        }else if(get.equals("rpl")){
            idMapel = "7";
            namaMapel = "Rekayasa Perangkat Lunak";
            showLockScreenDialog();
        }else if(get.equals("kebangsaan")){
            idMapel = "8";
            namaMapel = "Wawasan Kebangsaan";
        }else if(get.equals("intelegensi")){
            idMapel = "9";
            namaMapel = "Intelegensi";
        }else if(get.equals("kepribadian")){
            idMapel = "10";
            namaMapel = "Kepribadian";
        }else if(get.equals("profesi")){
            idMapel = "11";
            namaMapel = "Profesi";
        }else if(get.equals("kampus")){
            idMapel = "12";
            namaMapel = "Kampus";
        }else if(get.equals("ekonomiakuntansi")){
            idMapel = "13";
            namaMapel = "Ekonomi Akuntansi";
        }else if(get.equals("literatur")){
            idMapel = "14";
            namaMapel = "Metodologi Penelitian";
        }else if(get.equals("grafikakomp")){
            idMapel = "15";
            namaMapel = "Grafika Komputer";
            showLockScreenDialog();
        }else if(get.equals("komputer")){
            idMapel = "16";
            namaMapel = "Komputer";
        }else if(get.equals("pemrogramaninternet")){
            idMapel = "17";
            namaMapel = "Pemrograman Internet";
            showLockScreenDialog();
        }else if(get.equals("jaringankomputer")){
            idMapel = "18";
            namaMapel = "Jaringan Komputer";
            showLockScreenDialog();
        }else if(get.equals("desainweb")){
            idMapel = "19";
            namaMapel = "Desain Web";
            showLockScreenDialog();
        }else{
            idMapel = "0";
        }
        toast = new Toast(this);
        //attribute of the circular progress bar
        initViews();
        SharedPreferences sp = getSharedPreferences("Score", Context.MODE_PRIVATE);
        //To play background sound
        if (sp.getInt("Sound", 0) == 0) {
            mediaPlayer = MediaPlayer.create(this, R.raw.abc);
            mediaPlayer.start();
            mediaPlayer.setLooping(true);
        }

        PemBerorientasiObjek = new PemBerorientasiObjekUas(this);
        PemBerorientasiObjek.createDatabase();
        PemBerorientasiObjek.openDatabase();
        PemBerorientasiObjek.getWritableDatabase();


        //Till here we are linking the database file
        OptA = (Button) findViewById(R.id.OptionA);
        OptB = (Button) findViewById(R.id.OptionB);
        OptC = (Button) findViewById(R.id.OptionC);
        OptD = (Button) findViewById(R.id.OptionD);
        ques = (TextView) findViewById(R.id.Questions);
        pertanyaanGambar = (ImageView) findViewById(R.id.pertanyaanGambar);
        lemparanParam = (TextView)findViewById(R.id.lemparanParam);
        soalYangKe = (TextView) findViewById(R.id.soalKe);
        jumlahSoal = (TextView) findViewById(R.id.jumlahsoal);
        garing = (TextView) findViewById(R.id.garing);
        play_button = (Button) findViewById(R.id.play_button);//Play button to start the game
        info = (TextView) findViewById(R.id.pesansebelummulai);
        AndroidNetworking.initialize(getApplicationContext()); //inisialisasi FAN
        TextView pesanSebelumMulai = findViewById(R.id.pesansebelummulai);
        String teksHtml = "Jika Anda menekan tombol <b>PLAY</b> berarti Anda sudah yakin untuk memulai ujian dan <b>HARUS</b> <u>menyelesaikan sampai pertanyaan terakhir</u> tanpa berpindah aplikasi,tanpa membuka notifikasi maupun melakukan <i>splitscreen, screenshoot</i> dan <i>record screen</i>. <b>Disarankan memperpanjang waktu lockscreen lebih dari 1 menit</b>. Fokus pada soal masing-masing dan jangan sampai salah tekan jawaban, karena <b>tidak dapat kembali ke pertanyaan sebelumnya</b>. Perhatikan waktu yang diberikan untuk setiap pertanyaan, karena jika waktu habis otomatis lanjut ke pertanyaan selanjutya.<br>SELAMAT MENGERJAKAN DENGAN JUJUR";
        Spanned teksDiformat = Html.fromHtml(teksHtml);
        pesanSebelumMulai.setText(teksDiformat);
        //if(get.equals("pbo")||get.equals("komputer")||get.equals("ekonomiakuntansi")||get.equals("profesi")||get.equals("grafikakomp")){

    }

    private void initViews() {
        progressBarCircle = (ProgressBar)findViewById(R.id.progressBarCircle);
        textViewTime = (TextView) findViewById(R.id.textViewTime);
        setProgressBarValues();
    }

    private void actionAfterFinishCount() {
        //countDownTimer.cancel();
        final SharedPreferences shared = getSharedPreferences("Score", Context.MODE_PRIVATE);
        toast.cancel();
        SharedPreferences.Editor editorku = sharedku.edit();
        SharedPreferences.Editor editor = shared.edit();//here we are saving the data when the countdown timer will finish and it is saved in shared prefrence file that is defined in onCreate method as score
        editor.putInt("Questions", soalKe).commit();
        if (get.equals("komputer") && shared.getInt("Komputer", 0) < l)
            editor.putInt("Komputer", l * 10).apply();
        else if (get.equals("kebangsaan") && shared.getInt("Kebangsaan", 0) < l)
            editor.putInt("Kebangsaan", l * 10).apply();
        else if (get.equals("kampus") && shared.getInt("Pengetahuankampus", 0) < l)
            editor.putInt("Pengetahuankampus", l * 10).apply();
        else if (get.equals("pengetahuanumum") && shared.getInt("Pengetahuanumum", 0) < l)
            editor.putInt("Pengetahuanumum", l * 10).apply();
        else if (get.equals("ekonomiakuntansi") && shared.getInt("Ekonomiakuntansi", 0) < l)
            editor.putInt("Ekonomiakuntansi", l * 10).apply();
        else if (get.equals("bahasainggris") && shared.getInt("Bahasainggris", 0) < l)
            editor.putInt("Bahasainggris", l * 10).apply();
        else if (get.equals("literatur") && shared.getInt("Literatur", 0) < l)
            editor.putInt("Literatur", l * 10).apply();
        else if (get.equals("intelegensi") && shared.getInt("Intelegensi", 0) < l)
            editor.putInt("Intelegensi", l * 10).apply();
        else if (get.equals("kepribadian") && shared.getInt("Kepribadian", 0) < l)
            editor.putInt("Kepribadian", l * 10).apply();
        else if (get.equals("profesi") && shared.getInt("Profesi", 0) < l)
            editor.putInt("Profesi", l * 10).apply();
        else if (get.equals("kecerdasan") && shared.getInt("Kecerdasanbuatan", 0) < l) {
            editor.putInt("Kecerdasanbuatan", l * 10).apply();
            editorku.putString("sekaliikutkecerdasan", "0");
            editorku.commit();
        }
        else if (get.equals("rpl") && shared.getInt("Rpl", 0) < l) {
            editor.putInt("Rpl", l * 10).apply();
            editorku.putString("sekaliikutrpl", "0");
            editorku.commit();
        }
        else if (get.equals("uaspbo") && shared.getInt("Pbo", 0) < l) {
            editor.putInt("Pbo", l * 10).apply();
            editorku.putString("sekaliikutpbo", "0");
            editorku.commit();
        }
        else if (get.equals("strukturdata") && shared.getInt("Strukturdata", 0) < l) {
            editor.putInt("StrukturData", l * 10).apply();
            editorku.putString("sekaliikutstruktur", "0");
            editorku.commit();
        }
        else if (get.equals("grafikakomp") && shared.getInt("Grafikakomputer", 0) < l) {
            editor.putInt("Grafikakomputer", l * 10).apply();
            editorku.putString("sekaliikutgrafika", "0");
            editorku.commit();
        }
        else if (get.equals("pemrogramaninternet") && shared.getInt("PemrogInternet", 0) < l) {
            editor.putInt("PemrogInternet", l * 10).apply();
            editorku.putString("sekaliikutinternet", "0");
            editorku.commit();
        }
        else if (get.equals("jaringankomputer") && shared.getInt("JaringanKomp", 0) < l) {
            editor.putInt("JaringanKomp", l * 10).apply();
            editorku.putString("sekaliikutjarkom", "0");
            editorku.commit();
        }
        else if (get.equals("desainweb") && shared.getInt("DesainWeb", 0) < l) {
            editor.putInt("DesainWeb", l * 10).apply();
            editorku.putString("sekaliikutdesainweb", "0");
            editorku.commit();
        }
        if(variable==0) {
            if(penandabottonplay==0){
                variable=1;
            }else{
//                Toast.makeText(this, "Maaf Anda tidak diizinkan beralih aplikasi sehingga soal kami akhiri", Toast.LENGTH_LONG).show();
                countDownTimer.cancel();
                if (get.equals("grafikakomp")){
                    editorku.putString("sekaliikutgrafika", "0");
                }else if(get.equals("uaspbo")){
                    editorku.putString("sekaliikutpbo", "0");
                }else if(get.equals("rpl")){
                    editorku.putString("sekaliikutrpl", "0");
                }else if(get.equals("kecerdasan")){
                    editorku.putString("sekaliikutkecerdasan", "0");
                }else if(get.equals("strukturdata")){
                    editorku.putString("sekaliikutstruktur", "0");
                }else if(get.equals("pemrogramaninternet")){
                    editorku.putString("sekaliikutinternet", "0");
                }else if(get.equals("jaringankomputer")){
                    editorku.putString("sekaliikutjarkom", "0");
                }else if(get.equals("desainweb")){
                    editorku.putString("sekaliikutdesainweb", "0");
                }
                editorku.commit();
                Intent intent = new Intent(QuestionsUas.this, Result.class);
                intent.putExtra("correct", l);
                intent.putExtra("jumlahsoal", jumSoal);
                intent.putExtra("attemp", soalKe);
                intent.putExtra("makul", namaMapel);
                intent.putExtra("kodemakul",get);
                intent.putExtra("jeneng",sharedku.getString("name",Default));
                float x1 = (float)l / soalKe * 100 ;
                    if (get.equals("grafikakomp")){
                        editor.putFloat("getNilaiGrafikakomputer", x1).apply();
                    }else if(get.equals("uaspbo")){
                        editor.putFloat("getNilaiPbo", x1).apply();
                    }else if(get.equals("rpl")){
                        editor.putFloat("getNilaiRpl", x1).apply();
                    }else if(get.equals("kecerdasan")){
                        editor.putFloat("getNilaiKecerdasanbuatan", x1).apply();
                    }else if(get.equals("strukturdata")){
                        editor.putFloat("getNilaiStrukturdata", x1).apply();
                    }else if(get.equals("pemrogramaninternet")){
                        editor.putFloat("getNilaiPemrogInternet", x1).apply();
                    }else if(get.equals("jaringankomputer")){
                        editor.putFloat("getNilaiJaringanKomputer", x1).apply();
                    }else if(get.equals("desainweb")){
                        editor.putFloat("getNilaiDesainWeb", x1).apply();
                    }
                startActivity(intent);
                finish();
            }
        }
    }
    private void setProgressBarValues() { //khusus untuk matematika waktunya lebih lama
        if (get.equals("intelegensi")) {
            timeCountInMilliSeconds = 120000;
        }else{
            timeCountInMilliSeconds = 60000; //untuk mengeset berapa lama setiap soal
        }
        progressBarCircle.setMax((int) timeCountInMilliSeconds / 1000);
        progressBarCircle.setProgress((int) timeCountInMilliSeconds / 1000);
    }

    private String hmsTimeFormatter(long milliSeconds) {
        String hms;
        if (get.equals("intelegensi")) { //untuk membedakan bahwa untuk hitung2an waktunya 2x lipatnya
            hms = String.format("%03d",
                    //TimeUnit.MILLISECONDS.toHours(milliSeconds),
                    //TimeUnit.MILLISECONDS.toMinutes(milliSeconds) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milliSeconds)),
                    TimeUnit.MILLISECONDS.toSeconds(milliSeconds) );//120.0000 miliseccon atau 2 menit
//            float s = TimeUnit.MILLISECONDS.toSeconds(milliSeconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliSeconds));
//            System.out.println(s);
        }else{
            hms = String.format("%02d",
                    //TimeUnit.MILLISECONDS.toHours(milliSeconds),
                    //TimeUnit.MILLISECONDS.toMinutes(milliSeconds) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milliSeconds)),
                    TimeUnit.MILLISECONDS.toSeconds(milliSeconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliSeconds)));
        }
        return hms;
    }
    private void setTimerValuesActionBar(View ve) {
        int time;
        if (get.equals("intelegensi")) {
            time = 2;
        }else{
            time = 1; //untuk mengeset berapa lama setiap soal
        }
        timeCountInMilliSeconds = time * 60 * 1000;
        startcountDownActionBar(ve);
    }

    private void startcountDownActionBar(View vi){
        if (visibility == 0) {
            //showing the buttons which were previously invisible
            OptA.setVisibility(View.VISIBLE);
            OptB.setVisibility(View.VISIBLE);
            OptC.setVisibility(View.VISIBLE);
            OptD.setVisibility(View.VISIBLE);
            play_button.setVisibility(View.GONE);
            info.setVisibility(View.GONE);
            garing.setVisibility(View.VISIBLE);
            visibility = 1;
        }
    }
    private void actionPilihJawaban(View vie) {
        if(soalKe<=jumSoal){
            //timerStatus = TimerStatus.STARTED;
            if (global != null) {
                if (global.equals("C")) {
                    if(get.equals("uaspbo")||get.equals("grafikakomp")||get.equals("rpl")||get.equals("kecerdasan")||get.equals("strukturdata")||get.equals("pemrogramaninternet")||get.equals("jaringankomputer")||get.equals("desainweb")){
                        if (vie.getId() == R.id.OptionA) {
                            //Here we use the snackbar because if we use the toast then they will be stacked an user cannot idetify which questions answer is it showing
                            Snackbar.make(vie, "Jawaban Benar Tidak Ditampilkan! ☺", Snackbar.LENGTH_SHORT).show();
                            l++;
                            soal();
                        } else {
                            Snackbar.make(vie, "Jawaban Benar Tidak Ditampilkan! ☺", Snackbar.LENGTH_SHORT).show();
                            soal();
                        }
                    }else {
                        if (vie.getId() == R.id.OptionA) {
                            //Here we use the snackbar because if we use the toast then they will be stacked an user cannot idetify which questions answer is it showing
                            Snackbar.make(vie, "         Pilihan TEPAT! ☺", Snackbar.LENGTH_SHORT).show();
                            l++;
                            soal();
                        } else {
                            Toast.makeText(QuestionsUas.this, "Kurang TEPAT, klik mengerti dan lanjutkan!", Toast.LENGTH_LONG).show();
                            disableButton();
                            Bundle bundle = new Bundle();
                            bundle.putString("jawaban", Opta);
                            bundle.putString("pembahasan", Pembahas);
                            MainFragmentBottomsheet fragment = MainFragmentBottomsheet.newInstance();
                            fragment.setArguments(bundle);
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    //.add(R.id.bottom_sheet,fragment)
                                    .replace(R.id.bottomsheet_fragment_container, fragment)
                                    .commit();
                        }
                    }


                } else if (global.equals("D")) {
                    if(get.equals("uaspbo")||get.equals("grafikakomp")||get.equals("rpl")||get.equals("kecerdasan")||get.equals("strukturdata")||get.equals("pemrogramaninternet")||get.equals("jaringankomputer")||get.equals("desainweb")){
                        if (vie.getId() == R.id.OptionB) {
                            //Here we use the snackbar because if we use the toast then they will be stacked an user cannot idetify which questions answer is it showing
                            Snackbar.make(vie, "Jawaban Benar Tidak Ditampilkan! ☺", Snackbar.LENGTH_SHORT).show();
                            l++;
                            soal();
                        } else {
                            Snackbar.make(vie, "Jawaban Benar Tidak Ditampilkan! ☺", Snackbar.LENGTH_SHORT).show();
                            soal();
                        }
                    }else {
                        if (vie.getId() == R.id.OptionB) {
                            Snackbar.make(vie, "          Pilihan TEPAT! ☺", Snackbar.LENGTH_SHORT).show();
                            l++;
                            soal();
                        } else {
                            Toast.makeText(QuestionsUas.this, "Kurang TEPAT, klik mengerti dan lanjutkan!", Toast.LENGTH_LONG).show();
                            disableButton();
                            Bundle bundle = new Bundle();
                            bundle.putString("jawaban", Optb);
                            bundle.putString("pembahasan", Pembahas);
                            MainFragmentBottomsheet fragment = MainFragmentBottomsheet.newInstance();
                            fragment.setArguments(bundle);
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    //.add(R.id.bottom_sheet,fragment)
                                    .replace(R.id.bottomsheet_fragment_container, fragment)
                                    .commit();
                        }
                    }

                } else if (global.equals("A")) {
                    if(get.equals("uaspbo")||get.equals("grafikakomp")||get.equals("rpl")||get.equals("kecerdasan")||get.equals("strukturdata")||get.equals("pemrogramaninternet")||get.equals("jaringankomputer")||get.equals("desainweb")){
                        if (vie.getId() == R.id.OptionC) {
                            //Here we use the snackbar because if we use the toast then they will be stacked an user cannot idetify which questions answer is it showing
                            Snackbar.make(vie, "Jawaban Benar Tidak Ditampilkan! ☺", Snackbar.LENGTH_SHORT).show();
                            l++;
                            soal();
                        } else {
                            Snackbar.make(vie, "Jawaban Benar Tidak Ditampilkan! ☺", Snackbar.LENGTH_SHORT).show();
                            soal();
                        }
                    }else {
                        if (vie.getId() == R.id.OptionC) {

                            Snackbar.make(vie, "         Pilihan TEPAT! ☺", Snackbar.LENGTH_SHORT).show();
                            l++;
                            soal();
                        } else {
                            Toast.makeText(QuestionsUas.this, "Kurang TEPAT, klik mengerti dan lanjutkan!", Toast.LENGTH_LONG).show();
                            disableButton();
                            Bundle bundle = new Bundle();
                            bundle.putString("jawaban", Optc);
                            bundle.putString("pembahasan", Pembahas);
                            MainFragmentBottomsheet fragment = MainFragmentBottomsheet.newInstance();
                            fragment.setArguments(bundle);
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    //.add(R.id.bottom_sheet,fragment)
                                    .replace(R.id.bottomsheet_fragment_container, fragment)
                                    .commit();
                        }
                    }

                } else if (global.equals("B")) {
                    if(get.equals("uaspbo")||get.equals("grafikakomp")||get.equals("rpl")||get.equals("kecerdasan")||get.equals("strukturdata")||get.equals("pemrogramaninternet")||get.equals("jaringankomputer")||get.equals("desainweb")){
                        if (vie.getId() == R.id.OptionD) {
                            //Here we use the snackbar because if we use the toast then they will be stacked an user cannot idetify which questions answer is it showing
                            Snackbar.make(vie, "Jawaban Benar Tidak Ditampilkan! ☺", Snackbar.LENGTH_SHORT).show();
                            l++;
                            soal();
                        } else {
                            Snackbar.make(vie, "Jawaban Benar Tidak Ditampilkan! ☺", Snackbar.LENGTH_SHORT).show();
                            soal();
                        }
                    }else {
                        if (vie.getId() == R.id.OptionD) {
                            Snackbar.make(vie, "        Pilihan TEPAT! ☺", Snackbar.LENGTH_SHORT).show();
                            l++;
                            soal();
                        } else {
                            Toast.makeText(QuestionsUas.this, "Kurang TEPAT, klik mengerti dan lanjutkan!", Toast.LENGTH_LONG).show();
                            disableButton();
                            Bundle bundle = new Bundle();
                            bundle.putString("jawaban", Optd);
                            bundle.putString("pembahasan", Pembahas);
                            MainFragmentBottomsheet fragment = MainFragmentBottomsheet.newInstance();
                            fragment.setArguments(bundle);
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    //.add(R.id.bottom_sheet,fragment)
                                    .replace(R.id.bottomsheet_fragment_container, fragment)
                                    .commit();
                        }
                    }
                }
            }
            //taruh di sini



        }else{ //jika
            //znnzvb      bsoalKe++;
            variable=0; // untuk memenuhkan syarat mengakhiri soal
            countDownTimer.cancel();
            actionAfterFinishCount();
        }
    }

    public void soal(){
        enableButton();
        if (get.equals("uaspbo")) { //c14
            if (pbo == 0) {
                totalRecord = PemBerorientasiObjek.bacaJumlahRecord();
                if(totalRecord<jumSoal){
                    jumSoal = totalRecord;
                }
                for (i = 1; i <= jumSoal; i++) {
                    list.add(new Integer(i));
                }
                Collections.shuffle(list);
                pbo=1;
            }
            if(soalKe<jumSoal){
                Ques = PemBerorientasiObjek.readQuestion(list.get(j));
                Opta = PemBerorientasiObjek.readOptionA(list.get(j));
                Optb = PemBerorientasiObjek.readOptionB(list.get(j));
                Optc = PemBerorientasiObjek.readOptionC(list.get(j));
                Optd = PemBerorientasiObjek.readOptionD(list.get(j));
                global = PemBerorientasiObjek.readAnswer(list.get(j));
                Pembahas = PemBerorientasiObjek.readPembahasan(list.get(j));
                j++;
            }else {
                variable=0; // untuk memenuhkan syarat mengakhiri soal
                countDownTimer.cancel();
                actionAfterFinishCount();
            }
        }
        int cc = 1;
        int dd = soalKe+cc;
        soalYangKe.setText(Integer.toString(dd));
        jumlahSoal.setText(Integer.toString(jumSoal));

        if(Opta.length()>4){
            String coba = Opta.substring(0, 4);
            //Log.d(TAG, "isitabel: " + coba);
            if(coba.equals("http")){
                mProgressDialog = new ProgressDialog(QuestionsUas.this);
                mProgressDialog.setMessage("Loading soal...");
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.show();
                ques.setText("");
                OptA.setText("");
                OptB.setText("");
                OptC.setText("");
                OptD.setText("");
                pertanyaanGambar.setVisibility(View.VISIBLE);
                new DownloadImagePertanyaan().execute(Ques);
                new DownloadImageOptA().execute(Opta);
                new DownloadImageOptB().execute(Optb);
                new DownloadImageOptC().execute(Optc);
                new DownloadImageOptD().execute(Optd);
                mProgressDialog.dismiss();
            }else{
                ques.setText("" + Ques);
                OptA.setText(Opta);
                OptB.setText(Optb);
                OptC.setText(Optc);
                OptD.setText(Optd);
            }
        }else{
            ques.setText("" + Ques);
            OptA.setText(Opta);
            OptB.setText(Optb);
            OptC.setText(Optc);
            OptD.setText(Optd);
        }

        if(soalKe<jumSoal){
            soalKe++;
            //progressBarCircle.setProgress(100);
            startCountDownSoal(viewLemparan);
        }else {
            countDownTimer.cancel();
            //actionAfterFinishCount();
        }
    }//end method soal()

    public void f1(String s1){
        lemparanParam.setText(s1);
    }

    private void disableButton(){
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            OptA.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.custom_button_pilgan_disable) );
            OptB.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.custom_button_pilgan_disable) );
            OptC.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.custom_button_pilgan_disable) );
            OptD.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.custom_button_pilgan_disable) );
        } else {
            OptA.setBackground(ContextCompat.getDrawable(this, R.drawable.custom_button_pilgan_disable));
            OptB.setBackground(ContextCompat.getDrawable(this, R.drawable.custom_button_pilgan_disable));
            OptC.setBackground(ContextCompat.getDrawable(this, R.drawable.custom_button_pilgan_disable));
            OptD.setBackground(ContextCompat.getDrawable(this, R.drawable.custom_button_pilgan_disable));
        }
        OptA.setEnabled(false);
        OptB.setEnabled(false);
        OptC.setEnabled(false);
        OptD.setEnabled(false);
    }
    private void enableButton(){
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            OptA.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.custom_button_pilgan) );
            OptB.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.custom_button_pilgan) );
            OptC.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.custom_button_pilgan) );
            OptD.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.custom_button_pilgan) );
        } else {
            OptA.setBackground(ContextCompat.getDrawable(this, R.drawable.custom_button_pilgan));
            OptB.setBackground(ContextCompat.getDrawable(this, R.drawable.custom_button_pilgan));
            OptC.setBackground(ContextCompat.getDrawable(this, R.drawable.custom_button_pilgan));
            OptD.setBackground(ContextCompat.getDrawable(this, R.drawable.custom_button_pilgan));
        }
        OptA.setEnabled(true);
        OptB.setEnabled(true);
        OptC.setEnabled(true);
        OptD.setEnabled(true);
    }

    private void startCountDownSoal(final View vu) {
        if (get.equals("intelegensi")) { //jika soal matematika
            countDownTimer = new CountDownTimer(duamenit, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                        textViewTime.setText(hmsTimeFormatter(millisUntilFinished));
                        progressBarCircle.setProgress((int)(millisUntilFinished / 1000));
                }

                @Override
                public void onFinish() {
                    //textViewTime.setText(hmsTimeFormatter(timeCountInMilliSeconds));
                    // call to initialize the progress bar values
                    setProgressBarValues();
                    // changing the timer status to stopped
                    //timerStatus = TimerStatus.STOPPED;
                    //action untuk ganti soal
                    countDownTimer.cancel();
                    actionPilihJawaban(vu);
                }
            }.start();
        }else{
            countDownTimer = new CountDownTimer(timeCountInMilliSeconds, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                        textViewTime.setText(hmsTimeFormatter(millisUntilFinished));
                        progressBarCircle.setProgress((int) (millisUntilFinished / 1000));
                }

                @Override
                public void onFinish() {
                    textViewTime.setText(hmsTimeFormatter(timeCountInMilliSeconds));
                    // call to initialize the progress bar values
                    setProgressBarValues();
                    // changing the timer status to stopped
                    //timerStatus = TimerStatus.STOPPED;
                    //action untuk ganti soal
                    countDownTimer.cancel();
                    actionPilihJawaban(vu);
                }
            }.start();
        }
        countDownTimer.start();
    }

    public void onClick(View v) { //When this method is executed then there will be new question came and also same method for play button
        viewLemparan = v;
        switch (v.getId()){
            case R.id.play_button:
                //getAllSqlite();
                penandabottonplay =1;
                backsebelummulai = 0;
                setTimerValuesActionBar(v);
                soal();
                //actionPilihJawaban(v);
                break;
            case R.id.OptionA:
                countDownTimer.cancel();
                actionPilihJawaban(v);
                pertanyaanGambar.setVisibility(View.GONE);
                break;
            case R.id.OptionB:
                countDownTimer.cancel();
                actionPilihJawaban(v);
                pertanyaanGambar.setVisibility(View.GONE);
                break;
            case R.id.OptionC:
                countDownTimer.cancel();
                actionPilihJawaban(v);
                pertanyaanGambar.setVisibility(View.GONE);
                break;
            case R.id.OptionD:
                countDownTimer.cancel();
                actionPilihJawaban(v);
                pertanyaanGambar.setVisibility(View.GONE);
                break;
            default:
                countDownTimer.cancel();
                //startCountDownSoal(v);
                actionPilihJawaban(v);
                pertanyaanGambar.setVisibility(View.GONE);
        }
    }

    public void fragmentReplace() {
        if (getSupportFragmentManager().findFragmentByTag(new MainFragmentBottomsheet().getClass().getName()) != null) {
            getSupportFragmentManager().popBackStack(new MainFragmentBottomsheet().getClass().getName(),
                    FragmentManager.POP_BACK_STACK_INCLUSIVE);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    protected void onPause() { //handle saat pindah aplikasi
        super.onPause();
        if (penandabottonplay==1){
            variable =0;
        }
        SharedPreferences sp = getSharedPreferences("Score", Context.MODE_PRIVATE);
        if (sp.getInt("Sound", 0) == 0)
            mediaPlayer.pause();
    }
    @Override
    protected void onResume(){
        super.onResume();
        //variable =0;
        if(variable==0) {
            if(penandabottonplay==1) {
                Toast.makeText(this, "Maaf Anda tidak diizinkan beralih aplikasi sehingga soal kami akhiri", Toast.LENGTH_LONG).show();
                actionAfterFinishCount();
            }
        }else{
            //finish();
        }
        SharedPreferences sp = getSharedPreferences("Score", Context.MODE_PRIVATE);
        if (sp.getInt("Sound", 0) == 0)
            mediaPlayer.pause();
    }

    @Override
    protected void onStop() {
        // call the superclass method first
        super.onStop();

        if (penandabottonplay==1){
            variable =0;
        }
        SharedPreferences sp = getSharedPreferences("Score", Context.MODE_PRIVATE);
        if (sp.getInt("Sound", 0) == 0)
            mediaPlayer.pause();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (!hasFocus) {
            // Kehilangan fokus window, bisa jadi pengguna sedang mengambil tangkapan layar
            isScreenCaptured = true;
            if (isScreenCaptured) {
                // Aksi ketika tangkapan layar terdeteksi
//                Toast.makeText(this, "Fokus berakhir, soal selesai", Toast.LENGTH_LONG).show();
                actionAfterFinishCount();
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        variable =0;
        SharedPreferences sp = getSharedPreferences("Score", Context.MODE_PRIVATE);
        if (sp.getInt("Sound", 0) == 0)
            mediaPlayer.start();
    }


    // DownloadImage AsyncTask
    private class DownloadImagePertanyaan extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected Bitmap doInBackground(String... URL) {
            String imageURL = URL[0];
            Bitmap bitmap = null;
            try {
                InputStream input = new java.net.URL(imageURL).openStream();
                bitmap = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }
        @Override
        protected void onPostExecute(Bitmap result) {
            pertanyaanGambar.setImageBitmap(result);
        }
    }

    private class DownloadImageOptA extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected Bitmap doInBackground(String... URL) {
            String imageURL = URL[0];
            Bitmap bitmap = null;
            try {
                InputStream input = new java.net.URL(imageURL).openStream();
                bitmap = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }
        @Override
        protected void onPostExecute(Bitmap result) {
            BitmapDrawable bdrawable = new BitmapDrawable(QuestionsUas.this.getResources(),result);
            OptA.setBackground(bdrawable);
        }
    }
    private class DownloadImageOptB extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected Bitmap doInBackground(String... URL) {
            String imageURL = URL[0];
            Bitmap bitmap = null;
            try {
                InputStream input = new java.net.URL(imageURL).openStream();
                // Decode Bitmap
                bitmap = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }
        @Override
        protected void onPostExecute(Bitmap result) {
            BitmapDrawable bdrawable = new BitmapDrawable(QuestionsUas.this.getResources(),result);
            OptB.setBackground(bdrawable);
        }
    }
    private class DownloadImageOptC extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected Bitmap doInBackground(String... URL) {
            String imageURL = URL[0];
            Bitmap bitmap = null;
            try {
                InputStream input = new java.net.URL(imageURL).openStream();
                bitmap = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }
        @Override
        protected void onPostExecute(Bitmap result) {
            BitmapDrawable bdrawable = new BitmapDrawable(QuestionsUas.this.getResources(),result);
            OptC.setBackground(bdrawable);
        }
    }
    private class DownloadImageOptD extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected Bitmap doInBackground(String... URL) {
            String imageURL = URL[0];
            Bitmap bitmap = null;
            try {
                InputStream input = new java.net.URL(imageURL).openStream();
                bitmap = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }
        @Override
        protected void onPostExecute(Bitmap result) {
            BitmapDrawable bdrawable = new BitmapDrawable(QuestionsUas.this.getResources(),result);
            OptD.setBackground(bdrawable);
        }
    }

    private void showLockScreenDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Atur Waktu Lock Screen");
        builder.setMessage("Pastikan pengaturan untuk Lock Screen lebih dari 1 menit untuk memastikan layar tidak terkunci saat mengerjakan soal sehingga mengakibatkan soal berakhir");

        builder.setPositiveButton("Sudah", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Aksi jika pengguna memilih "Sudah"
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("Atur", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Aksi jika pengguna memilih "Atur"
                Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
                startActivity(intent);
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //Used to add some time so that user cannot directly press and exity out of the activity
    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        //variable = 1;
        //finish();
        if (doubleBackToExitPressedOnce) {
            if(backsebelummulai==1){
                super.onBackPressed();
            }
            return;
        }else {
            this.doubleBackToExitPressedOnce = true;
            if (backsebelummulai == 1) {
                super.onBackPressed();
            } else {
                Toast.makeText(this, "Anda harus menyelesaikan semua soal yang ada terlebih dahulu!", Toast.LENGTH_LONG).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce = false;
                    }
                }, 4000);
            }
        }
    }
}
