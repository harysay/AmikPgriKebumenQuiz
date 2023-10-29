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
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.FragmentManager;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.harysaydev.amikpgrikbmquiz.bottomsheet.MainFragmentBottomsheet;
import com.harysaydev.amikpgrikbmquiz.helper.CheckNetwork;
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
import com.harysaydev.amikpgrikbmquiz.soal.PemrogramanInternet;
import com.harysaydev.amikpgrikbmquiz.soal.Pengetahuankampus;
import com.harysaydev.amikpgrikbmquiz.soal.Pengetahuanumum;
import com.harysaydev.amikpgrikbmquiz.soal.Profesi;
import com.harysaydev.amikpgrikbmquiz.soal.RekPerangkatLunak;
import com.harysaydev.amikpgrikbmquiz.soal.StrukturData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Questions extends AppCompatActivity implements View.OnClickListener {
    private long timeCountInMilliSeconds = 1 * 61000;
    private boolean isScreenCaptured = false;
    private long duamenit = 2 * 60500; //supaya terlihat oleh user saat perhitungan awal dimulai dari 120 maka agak ditambahkan 1 detik
    //ArcProgress donutProgress;
    //String HttpJSonURL = "http://10.28.11.11/jsonkuis/SubjectFullForm.php";
//    private static final String Database_path = "/data/data/com.harysaydev.amikpgrikbmquiz/databases/";
//    private static final String Database_name = "profesi.db";//NAME of database stored in Assets folder
    public SQLiteDatabase sqlite;
    //private static final String Table_name = "profesi";//name of table
    String HttpJSonURL = "http://imanajilesson.000webhostapp.com/api_soal/getsoal?idmapel=";
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
    String playpertama ="0";
    //Objects of different classes
    com.harysaydev.amikpgrikbmquiz.soal.Literatur Literatur;
    com.harysaydev.amikpgrikbmquiz.soal.Kebangsaan Kebangsaan;
    com.harysaydev.amikpgrikbmquiz.soal.Profesi Profesi;
    com.harysaydev.amikpgrikbmquiz.soal.Komputer Komputer;
    com.harysaydev.amikpgrikbmquiz.soal.Kepribadian Kepribadian;
    com.harysaydev.amikpgrikbmquiz.soal.Bahasainggris Bahasainggris;
    com.harysaydev.amikpgrikbmquiz.soal.Pengetahuanumum Pengetahuanumum;
    com.harysaydev.amikpgrikbmquiz.soal.Pengetahuankampus Pengetahuankampus;
    com.harysaydev.amikpgrikbmquiz.soal.PemBerorientasiObjek PemBerorientasiObjek;
    com.harysaydev.amikpgrikbmquiz.soal.Intelegensi Intelegensi;
    com.harysaydev.amikpgrikbmquiz.soal.Grafikakomputer Grafikakomputer;
    com.harysaydev.amikpgrikbmquiz.soal.Kecerdasanbuatan Kecerdasanbuatan;
    com.harysaydev.amikpgrikbmquiz.soal.RekPerangkatLunak RekPerangkatLunak;
    com.harysaydev.amikpgrikbmquiz.soal.StrukturData StrukDat;
    com.harysaydev.amikpgrikbmquiz.soal.PemrogramanInternet PemroInternet;
    com.harysaydev.amikpgrikbmquiz.soal.JaringanKomputer Jarkom;
    com.harysaydev.amikpgrikbmquiz.soal.Desainweb Desain;
    int jumSoal=50,totalRecord,backsebelummulai;
    com.harysaydev.amikpgrikbmquiz.soal.Ekonomiakuntansi Ekonomiakuntansi;
    public int visibility = 0, komputer = 0, kebangsaan = 0, kampus = 0, pengetahuanumum = 0, ekonomiakuntansi = 0, bahasainggris = 0, literatur = 0, intelegensi = 0, kepribadian = 0, profesi = 0,grafikom = 0,kecerdasan = 0,rpl = 0,pbo = 0,struktur=0,pemrograminternet=0, i, j = 0, soalKe = 0, l = 0;
    String global = null, Ques, Opta, Optb, Optc, Optd, Pembahas;
    ArrayList<Integer> list = new ArrayList<Integer>();
    Toast toast;
    MediaPlayer mediaPlayer;
    private ProgressBar progressBarCircle;
    private TextView textViewTime;
    private CountDownTimer countDownTimer;
    ProgressDialog progressDialog;
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
        showLockScreenDialog();
        sharedku = getSharedPreferences("Content_main", Context.MODE_PRIVATE);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();//recieving the intent send by the Navigation activity
        get = intent.getStringExtra(Navigation_Activity.Message);//converting that intent message to string using the getStringExtra() method
        if(get.equals("strukturdata")){
            idMapel = "1";
            namaMapel = "Struktur Data";
        } else if(get.equals("bahasainggris")){
            idMapel = "2";
            namaMapel = "Bahasa Inggris";
        }else if(get.equals("pemrogramanterstruktur")){
            idMapel = "3";
            namaMapel = "Pemrograman Terstruktur";
        }else if(get.equals("pengetahuanumum")){
            idMapel = "4";
            namaMapel = "Pengetahuan Umum";
        }else if(get.equals("pbo")){
            idMapel = "5";
            namaMapel = "Pemrograman Berorientasi Objek";
        }else if(get.equals("kecerdasan")){
            idMapel = "6";
            namaMapel = "Kecerdasan Buatan";
        }else if(get.equals("rpl")){
            idMapel = "7";
            namaMapel = "Rekayasa Perangkat Lunak";
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
        }else if(get.equals("komputer")){
            idMapel = "16";
            namaMapel = "Komputer";
        }else if(get.equals("pemrogramaninternet")){
            idMapel = "17";
            namaMapel = "Pemrograman Internet";
        }else if(get.equals("jaringankomputer")){
            idMapel = "18";
            namaMapel = "Jaringan Komputer";
        }else if(get.equals("desainweb")){
            idMapel = "19";
            namaMapel = "Desain Web";
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
        //fragment = MainFragmentBottomsheet.newInstance();
        //Now the linking of all the database files with the Question activity
        Literatur = new Literatur(this);
        Literatur.createDatabase();
        Literatur.openDatabase();
        Literatur.getWritableDatabase();

        Kepribadian = new Kepribadian(this);
        Kepribadian.createDatabase();
        Kepribadian.openDatabase();
        Kepribadian.getWritableDatabase();

        Komputer = new Komputer(this);
        Komputer.createDatabase();
        Komputer.openDatabase();
        Komputer.getWritableDatabase();

        Profesi = new Profesi(this);
        Profesi.createDatabase();
        Profesi.openDatabase();
        Profesi.getWritableDatabase();

        Bahasainggris = new Bahasainggris(this);
        Bahasainggris.createDatabase();
        Bahasainggris.openDatabase();
        Bahasainggris.getWritableDatabase();

        Pengetahuanumum = new Pengetahuanumum(this);
        Pengetahuanumum.createDatabase();
        Pengetahuanumum.openDatabase();
        Pengetahuanumum.getWritableDatabase();

        Pengetahuankampus = new Pengetahuankampus(this);
        Pengetahuankampus.createDatabase();
        Pengetahuankampus.openDatabase();
        Pengetahuankampus.getWritableDatabase();

        Intelegensi = new Intelegensi(this);
        Intelegensi.createDatabase();
        Intelegensi.openDatabase();
        Intelegensi.getWritableDatabase();

        Ekonomiakuntansi = new Ekonomiakuntansi(this);
        Ekonomiakuntansi.createDatabase();
        Ekonomiakuntansi.openDatabase();
        Ekonomiakuntansi.getWritableDatabase();

        Kebangsaan = new Kebangsaan(this);
        Kebangsaan.createDatabase();
        Kebangsaan.openDatabase();
        Kebangsaan.getWritableDatabase();

        Grafikakomputer = new Grafikakomputer(this);
        Grafikakomputer.createDatabase();
        Grafikakomputer.openDatabase();
        Grafikakomputer.getWritableDatabase();

        Kecerdasanbuatan = new Kecerdasanbuatan(this);
        Kecerdasanbuatan.createDatabase();
        Kecerdasanbuatan.openDatabase();
        Kecerdasanbuatan.getWritableDatabase();

        RekPerangkatLunak = new RekPerangkatLunak(this);
        RekPerangkatLunak.createDatabase();
        RekPerangkatLunak.openDatabase();
        RekPerangkatLunak.getWritableDatabase();

        PemBerorientasiObjek = new PemBerorientasiObjek(this);
        PemBerorientasiObjek.createDatabase();
        PemBerorientasiObjek.openDatabase();
        PemBerorientasiObjek.getWritableDatabase();

        StrukDat = new StrukturData(this);
        StrukDat.createDatabase();
        StrukDat.openDatabase();
        StrukDat.getWritableDatabase();

        PemroInternet = new PemrogramanInternet(this);
        PemroInternet.createDatabase();
        PemroInternet.openDatabase();
        PemroInternet.getWritableDatabase();

        Jarkom = new JaringanKomputer(this);
        Jarkom.createDatabase();
        Jarkom.openDatabase();
        Jarkom.getWritableDatabase();

        Desain = new Desainweb(this);
        Desain.createDatabase();
        Desain.openDatabase();
        Desain.getWritableDatabase();

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

 //UNTUK SEMENTARA OPSI UPDATE SOAL DARI SERVER DITIADAKAN KARENA API NYA TERHAPUS
//            if (CheckNetwork.isInternetAvailable(Questions.this)) //returns true if internet available
//            {
//                getDataFromServer(idMapel);
//            }else {
//                Toast.makeText(this, "Internet tidak tersedia, tidak bisa update soal!", Toast.LENGTH_LONG).show();
//            }


        //}
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
        else if (get.equals("pbo") && shared.getInt("Pbo", 0) < l) {
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
                }else if(get.equals("pbo")){
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
                Intent intent = new Intent(Questions.this, Result.class);
                intent.putExtra("correct", l);
                intent.putExtra("jumlahsoal", jumSoal);
                intent.putExtra("attemp", soalKe);
                intent.putExtra("makul", namaMapel);
                intent.putExtra("kodemakul",get);
                intent.putExtra("jeneng",sharedku.getString("name",Default));
                float x1 = (float)l / soalKe * 100 ;
                    if (get.equals("grafikakomp")){
                        editor.putFloat("getNilaiGrafikakomputer", x1).apply();
                    }else if(get.equals("pbo")){
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
//script di bawah digunakan ketika ingin menghubungkan dengan soal yang ada di server
//    public void getDataFromServer(String idmapel){
//        progressDialog = new ProgressDialog(Questions.this);
//        progressDialog.setTitle("Download Soal");
//        progressDialog.setMessage("Mohon Tunggu");
//        progressDialog.show();
//        //koneksi ke file read_all.php, jika menggunakan localhost gunakan ip sesuai dengan ip kamu
//        AndroidNetworking.get(HttpJSonURL+idmapel)
//                .setPriority(Priority.MEDIUM)
//                .build()
//                .getAsJSONObject(new JSONObjectRequestListener() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Log.d(TAG, "onResponse: " + response); //untuk log pada onresponse
//                        // do anything with response
//                        {
//                            //mengambil data dari JSON array pada read_all.php
//                            try {
//                                JSONArray jsonArr = response.getJSONArray("data");
//                                if(jsonArr.length()>0) {
//                                    //acak soal dari server
//                                    Random rnd = new Random();
//                                    for (int iz = jsonArr.length() - 1; iz >= 0; iz--) {
//                                        int j = rnd.nextInt(iz + 1);
//                                        // Simple swap
//                                        Object object = jsonArr.get(j);
//                                        jsonArr.put(j, jsonArr.get(iz));
//                                        jsonArr.put(iz, object);
//                                    }
//                                    for (int i = 0; i < jsonArr.length(); i++) {
//                                        JSONObject jsonObject = (JSONObject) jsonArr.get(i);
//                                        //adding the product to product list
//                                        String tempIdSoal = jsonObject.getString("id");
//                                        String tempPertanyaan = jsonObject.getString("soal");
//                                        String tempFile = jsonObject.getString("file");
//                                        String tempTypeFile = jsonObject.getString("tipe_file");
//                                        String tempOpsiA = jsonObject.getString("opsi_a").replace("#", "");
//                                        //String replaceOpsiA = tempOpsiA.replace( "#", "" );
//                                        String tempOpsiB = jsonObject.getString("opsi_b").replace("#", "");
//                                        String tempOpsiC = jsonObject.getString("opsi_c").replace("#", "");
//                                        String tempOpsiD = jsonObject.getString("opsi_d").replace("#", "");
//                                        String tempJawaban = jsonObject.getString("jawaban");
//                                        String tempPembahasan = jsonObject.getString("conclusion");
//
//                                        if (tempTypeFile.equals("image/jpeg")) {
//                                            //masukan data ke SQLite dalam bentuk URL
//                                            String urlPertanyGambar = "http://imanajilesson.000webhostapp.com/upload/gambar_soal/" + tempFile;
//                                            String urlOpsiGambar = "http://imanajilesson.000webhostapp.com/upload/gambar_opsi/";
//                                            if (idmapel.equals("2")) {
//                                                Bahasainggris.insertQuestion(tempIdSoal, urlPertanyGambar, urlOpsiGambar + tempOpsiA, urlOpsiGambar + tempOpsiB, urlOpsiGambar + tempOpsiC, urlOpsiGambar + tempOpsiD, tempJawaban, tempPembahasan);
//                                            } else if (idmapel.equals("4")) {
//                                                Pengetahuanumum.insertQuestion(tempIdSoal, urlPertanyGambar, urlOpsiGambar + tempOpsiA, urlOpsiGambar + tempOpsiB, urlOpsiGambar + tempOpsiC, urlOpsiGambar + tempOpsiD, tempJawaban, tempPembahasan);
//                                            } else if (idmapel.equals("5")) {
//                                                PemBerorientasiObjek.insertQuestion(tempIdSoal, urlPertanyGambar, urlOpsiGambar + tempOpsiA, urlOpsiGambar + tempOpsiB, urlOpsiGambar + tempOpsiC, urlOpsiGambar + tempOpsiD, tempJawaban, tempPembahasan);
//                                            } else if (idmapel.equals("6")) {
//                                                Kecerdasanbuatan.insertQuestion(tempIdSoal, urlPertanyGambar, urlOpsiGambar + tempOpsiA, urlOpsiGambar + tempOpsiB, urlOpsiGambar + tempOpsiC, urlOpsiGambar + tempOpsiD, tempJawaban, tempPembahasan);
//                                            } else if (idmapel.equals("7")) {
//                                                RekPerangkatLunak.insertQuestion(tempIdSoal, urlPertanyGambar, urlOpsiGambar + tempOpsiA, urlOpsiGambar + tempOpsiB, urlOpsiGambar + tempOpsiC, urlOpsiGambar + tempOpsiD, tempJawaban, tempPembahasan);
//                                            } else if (idmapel.equals("8")) {
//                                                Kebangsaan.insertQuestion(tempIdSoal, urlPertanyGambar, urlOpsiGambar + tempOpsiA, urlOpsiGambar + tempOpsiB, urlOpsiGambar + tempOpsiC, urlOpsiGambar + tempOpsiD, tempJawaban, tempPembahasan);
//                                            } else if (idmapel.equals("9")) {
//                                                Intelegensi.insertQuestion(tempIdSoal, urlPertanyGambar, urlOpsiGambar + tempOpsiA, urlOpsiGambar + tempOpsiB, urlOpsiGambar + tempOpsiC, urlOpsiGambar + tempOpsiD, tempJawaban, tempPembahasan);
//                                            } else if (idmapel.equals("10")) {
//                                                Kepribadian.insertQuestion(tempIdSoal, urlPertanyGambar, urlOpsiGambar + tempOpsiA, urlOpsiGambar + tempOpsiB, urlOpsiGambar + tempOpsiC, urlOpsiGambar + tempOpsiD, tempJawaban, tempPembahasan);
//                                            } else if (idmapel.equals("11")) {
//                                                Profesi.insertQuestion(tempIdSoal, urlPertanyGambar, urlOpsiGambar + tempOpsiA, urlOpsiGambar + tempOpsiB, urlOpsiGambar + tempOpsiC, urlOpsiGambar + tempOpsiD, tempJawaban, tempPembahasan);
//                                            } else if (idmapel.equals("12")) {
//                                                Pengetahuankampus.insertQuestion(tempIdSoal, urlPertanyGambar, urlOpsiGambar + tempOpsiA, urlOpsiGambar + tempOpsiB, urlOpsiGambar + tempOpsiC, urlOpsiGambar + tempOpsiD, tempJawaban, tempPembahasan);
//                                            } else if (idmapel.equals("13")) {
//                                                Ekonomiakuntansi.insertQuestion(tempIdSoal, urlPertanyGambar, urlOpsiGambar + tempOpsiA, urlOpsiGambar + tempOpsiB, urlOpsiGambar + tempOpsiC, urlOpsiGambar + tempOpsiD, tempJawaban, tempPembahasan);
//                                            } else if (idmapel.equals("14")) {
//                                                Literatur.insertQuestion(tempIdSoal, urlPertanyGambar, urlOpsiGambar + tempOpsiA, urlOpsiGambar + tempOpsiB, urlOpsiGambar + tempOpsiC, urlOpsiGambar + tempOpsiD, tempJawaban, tempPembahasan);
//                                            } else if (idmapel.equals("15")) {
//                                                Grafikakomputer.insertQuestion(tempIdSoal, urlPertanyGambar, urlOpsiGambar + tempOpsiA, urlOpsiGambar + tempOpsiB, urlOpsiGambar + tempOpsiC, urlOpsiGambar + tempOpsiD, tempJawaban, tempPembahasan);
//                                            } else if (idmapel.equals("16")) {
//                                                Komputer.insertQuestion(tempIdSoal, urlPertanyGambar, urlOpsiGambar + tempOpsiA, urlOpsiGambar + tempOpsiB, urlOpsiGambar + tempOpsiC, urlOpsiGambar + tempOpsiD, tempJawaban, tempPembahasan);
//                                            } else if (idmapel.equals("17")) {
//                                                PemroInternet.insertQuestion(tempIdSoal, urlPertanyGambar, urlOpsiGambar + tempOpsiA, urlOpsiGambar + tempOpsiB, urlOpsiGambar + tempOpsiC, urlOpsiGambar + tempOpsiD, tempJawaban, tempPembahasan);
//                                            }else if (idmapel.equals("18")) {
//                                                Jarkom.insertQuestion(tempIdSoal, urlPertanyGambar, urlOpsiGambar + tempOpsiA, urlOpsiGambar + tempOpsiB, urlOpsiGambar + tempOpsiC, urlOpsiGambar + tempOpsiD, tempJawaban, tempPembahasan);
//                                            }else if (idmapel.equals("19")) {
//                                                Desain.insertQuestion(tempIdSoal, urlPertanyGambar, urlOpsiGambar + tempOpsiA, urlOpsiGambar + tempOpsiB, urlOpsiGambar + tempOpsiC, urlOpsiGambar + tempOpsiD, tempJawaban, tempPembahasan);
//                                            }else if (idmapel.equals("1")) {
//                                                StrukDat.insertQuestion(tempIdSoal, urlPertanyGambar, urlOpsiGambar + tempOpsiA, urlOpsiGambar + tempOpsiB, urlOpsiGambar + tempOpsiC, urlOpsiGambar + tempOpsiD, tempJawaban, tempPembahasan);
//                                            } else {
//                                                Toast.makeText(Questions.this, "Permintaan tidak terdefinisi!!", Toast.LENGTH_LONG).show();
//                                            }
//                                        } else {
//                                            //masukan data ke SQLite
//                                            if (idmapel.equals("2")) {
//                                                Bahasainggris.insertQuestion(tempIdSoal, tempPertanyaan, tempOpsiA, tempOpsiB, tempOpsiC, tempOpsiD, tempJawaban, tempPembahasan);
//                                            } else if (idmapel.equals("4")) {
//                                                Pengetahuanumum.insertQuestion(tempIdSoal, tempPertanyaan, tempOpsiA, tempOpsiB, tempOpsiC, tempOpsiD, tempJawaban, tempPembahasan);
//                                            } else if (idmapel.equals("5")) {
//                                                PemBerorientasiObjek.insertQuestion(tempIdSoal, tempPertanyaan, tempOpsiA, tempOpsiB, tempOpsiC, tempOpsiD, tempJawaban, tempPembahasan);
//                                            } else if (idmapel.equals("6")) {
//                                                Kecerdasanbuatan.insertQuestion(tempIdSoal, tempPertanyaan, tempOpsiA, tempOpsiB, tempOpsiC, tempOpsiD, tempJawaban, tempPembahasan);
//                                            } else if (idmapel.equals("7")) {
//                                                RekPerangkatLunak.insertQuestion(tempIdSoal, tempPertanyaan, tempOpsiA, tempOpsiB, tempOpsiC, tempOpsiD, tempJawaban, tempPembahasan);
//                                            } else if (idmapel.equals("8")) {
//                                                Kebangsaan.insertQuestion(tempIdSoal, tempPertanyaan, tempOpsiA, tempOpsiB, tempOpsiC, tempOpsiD, tempJawaban, tempPembahasan);
//                                            } else if (idmapel.equals("9")) {
//                                                Intelegensi.insertQuestion(tempIdSoal, tempPertanyaan, tempOpsiA, tempOpsiB, tempOpsiC, tempOpsiD, tempJawaban, tempPembahasan);
//                                            } else if (idmapel.equals("10")) {
//                                                Kepribadian.insertQuestion(tempIdSoal, tempPertanyaan, tempOpsiA, tempOpsiB, tempOpsiC, tempOpsiD, tempJawaban, tempPembahasan);
//                                            } else if (idmapel.equals("11")) {
//                                                Profesi.insertQuestion(tempIdSoal, tempPertanyaan, tempOpsiA, tempOpsiB, tempOpsiC, tempOpsiD, tempJawaban, tempPembahasan);
//                                            } else if (idmapel.equals("12")) {
//                                                Pengetahuankampus.insertQuestion(tempIdSoal, tempPertanyaan, tempOpsiA, tempOpsiB, tempOpsiC, tempOpsiD, tempJawaban, tempPembahasan);
//                                            } else if (idmapel.equals("13")) {
//                                                Ekonomiakuntansi.insertQuestion(tempIdSoal, tempPertanyaan, tempOpsiA, tempOpsiB, tempOpsiC, tempOpsiD, tempJawaban, tempPembahasan);
//                                            } else if (idmapel.equals("14")) {
//                                                Literatur.insertQuestion(tempIdSoal, tempPertanyaan, tempOpsiA, tempOpsiB, tempOpsiC, tempOpsiD, tempJawaban, tempPembahasan);
//                                            } else if (idmapel.equals("15")) {
//                                                Grafikakomputer.insertQuestion(tempIdSoal, tempPertanyaan, tempOpsiA, tempOpsiB, tempOpsiC, tempOpsiD, tempJawaban, tempPembahasan);
//                                            } else if (idmapel.equals("16")) {
//                                                Komputer.insertQuestion(tempIdSoal, tempPertanyaan, tempOpsiA, tempOpsiB, tempOpsiC, tempOpsiD, tempJawaban, tempPembahasan);
//                                            } else if (idmapel.equals("17")) {
//                                                PemroInternet.insertQuestion(tempIdSoal, tempPertanyaan, tempOpsiA, tempOpsiB, tempOpsiC, tempOpsiD, tempJawaban, tempPembahasan);
//                                            } else if (idmapel.equals("18")) {
//                                                Jarkom.insertQuestion(tempIdSoal, tempPertanyaan, tempOpsiA, tempOpsiB, tempOpsiC, tempOpsiD, tempJawaban, tempPembahasan);
//                                            }else if (idmapel.equals("19")) {
//                                                Desain.insertQuestion(tempIdSoal, tempPertanyaan, tempOpsiA, tempOpsiB, tempOpsiC, tempOpsiD, tempJawaban, tempPembahasan);
//                                            }else if (idmapel.equals("1")) {
//                                                StrukDat.insertQuestion(tempIdSoal, tempPertanyaan, tempOpsiA, tempOpsiB, tempOpsiC, tempOpsiD, tempJawaban, tempPembahasan);
//                                            } else {
//                                                Toast.makeText(Questions.this, "Permintaan tidak terdefinisi!!", Toast.LENGTH_LONG).show();
//                                            }
//                                        }
//                                        //                                    }
//                                    }
//                                    //Toast.makeText(Questions.this, "Berhasil memuat soal terbaru!!", Toast.LENGTH_LONG).show();
//                                    progressDialog.dismiss();
//                                }else{
//                                    Toast.makeText(Questions.this, "Tidak ada soal terbaru dari server!!", Toast.LENGTH_LONG).show();
//                                    progressDialog.dismiss();
//                                }
//                            } catch (JSONException e) {
//                                progressDialog.dismiss();
//                                e.printStackTrace();
//                                Toast.makeText(Questions.this, "Kesalahan server,tidak bisa update!", Toast.LENGTH_LONG).show();
//                            }
//                        }
//                    } @Override
//                    public void onError(ANError error) {
//                        Log.d(TAG, "onError: " + error); //untuk log pada onerror
//                        // handle error
//                        progressDialog.dismiss();
//                        Toast.makeText(Questions.this, "Kesalahan server, silahkan ULANGI beberapa saat lagi atau hubungi Admin!", Toast.LENGTH_LONG).show();
//                    }
//                });
//    }

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
                if (global.equals("A")) {
                    if(get.equals("pbo")||get.equals("grafikakomp")||get.equals("rpl")||get.equals("kecerdasan")||get.equals("strukturdata")||get.equals("pemrogramaninternet")||get.equals("jaringankomputer")||get.equals("desainweb")){
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
                            Toast.makeText(Questions.this, "Kurang TEPAT, klik mengerti dan lanjutkan!", Toast.LENGTH_LONG).show();
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


                } else if (global.equals("B")) {
                    if(get.equals("pbo")||get.equals("grafikakomp")||get.equals("rpl")||get.equals("kecerdasan")||get.equals("strukturdata")||get.equals("pemrogramaninternet")||get.equals("jaringankomputer")||get.equals("desainweb")){
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
                            Toast.makeText(Questions.this, "Kurang TEPAT, klik mengerti dan lanjutkan!", Toast.LENGTH_LONG).show();
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

                } else if (global.equals("C")) {
                    if(get.equals("pbo")||get.equals("grafikakomp")||get.equals("rpl")||get.equals("kecerdasan")||get.equals("strukturdata")||get.equals("pemrogramaninternet")||get.equals("jaringankomputer")||get.equals("desainweb")){
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
                            Toast.makeText(Questions.this, "Kurang TEPAT, klik mengerti dan lanjutkan!", Toast.LENGTH_LONG).show();
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

                } else if (global.equals("D")) {
                    if(get.equals("pbo")||get.equals("grafikakomp")||get.equals("rpl")||get.equals("kecerdasan")||get.equals("strukturdata")||get.equals("pemrogramaninternet")||get.equals("jaringankomputer")||get.equals("desainweb")){
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
                            Toast.makeText(Questions.this, "Kurang TEPAT, klik mengerti dan lanjutkan!", Toast.LENGTH_LONG).show();
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
        if (get.equals("komputer")) { //c1
            if (komputer == 0) {
                totalRecord = Komputer.bacaJumlahRecord();
                if(totalRecord<jumSoal){
                    jumSoal = totalRecord;
                }
                for (i = 1; i <= totalRecord; i++) {
                    list.add(new Integer(i));
                }
                Collections.shuffle(list); //soal diacak
                Log.d(TAG, "errorSoal: " + list); //untuk log pada onerror
                komputer=1; //agar pengacakan dan total record tidak dijalankan lagi
            }
            if(soalKe<jumSoal){
                Ques = Komputer.readQuestion(list.get(j));
                Opta = Komputer.readOptionA(list.get(j));
                Optb = Komputer.readOptionB(list.get(j));
                Optc = Komputer.readOptionC(list.get(j));
                Optd = Komputer.readOptionD(list.get(j));
                global = Komputer.readAnswer(list.get(j));
                Pembahas = Komputer.readPembahasan(list.get(j));
                j++;
            }else {
                variable=0; // untuk memenuhkan syarat mengakhiri soal
                countDownTimer.cancel();
                actionAfterFinishCount();
            }
        } else if (get.equals("kebangsaan")) {//c2
            if (kebangsaan == 0) {
                totalRecord = Kebangsaan.bacaJumlahRecord();
                if(totalRecord<jumSoal){
                    jumSoal = totalRecord;
                }
                for (i = 1; i <= totalRecord; i++) {
                    list.add(new Integer(i));
                }
                Collections.shuffle(list);
                kebangsaan=1;//agar pengacakan dan total record tidak dijalankan lagi
            }
            if(soalKe<jumSoal){
                Ques = Kebangsaan.readQuestion(list.get(j));
                Opta = Kebangsaan.readOptionA(list.get(j));
                Optb = Kebangsaan.readOptionB(list.get(j));
                Optc = Kebangsaan.readOptionC(list.get(j));
                Optd = Kebangsaan.readOptionD(list.get(j));
                global = Kebangsaan.readAnswer(list.get(j));
                Pembahas = Kebangsaan.readPembahasan(list.get(j));
                j++;
            }else {
                variable=0; // untuk memenuhkan syarat mengakhiri soal
                countDownTimer.cancel();
                actionAfterFinishCount();
            }

        } else if (get.equals("kampus")) { //c3
            if (kampus == 0) {
                totalRecord = Pengetahuankampus.bacaJumlahRecord();
                if(totalRecord<jumSoal){
                    jumSoal = totalRecord;
                }
                for (i = 1; i <= totalRecord; i++) {
                    list.add(new Integer(i));
                }
                Collections.shuffle(list);
                kampus=1;
            }
            if(soalKe<jumSoal){
                Ques = Pengetahuankampus.readQuestion(list.get(j));
                Opta = Pengetahuankampus.readOptionA(list.get(j));
                Optb = Pengetahuankampus.readOptionB(list.get(j));
                Optc = Pengetahuankampus.readOptionC(list.get(j));
                Optd = Pengetahuankampus.readOptionD(list.get(j));
                global = Pengetahuankampus.readAnswer(list.get(j));
                Pembahas = Pengetahuankampus.readPembahasan(list.get(j));
                j++;
            }else {
                variable=0; // untuk memenuhkan syarat mengakhiri soal
                countDownTimer.cancel();
                actionAfterFinishCount();
            }
        } else if (get.equals("pengetahuanumum")) { //c4
            if (pengetahuanumum == 0) {
                totalRecord = Pengetahuanumum.bacaJumlahRecord();
                if(totalRecord<jumSoal){
                    jumSoal = totalRecord;
                }
                for (i = 1; i <= totalRecord; i++) {
                    list.add(new Integer(i));
                }
                Collections.shuffle(list);
                pengetahuanumum=1;
            }
            if(soalKe<jumSoal){
                Ques = Pengetahuanumum.readQuestion(list.get(j));
                Opta = Pengetahuanumum.readOptionA(list.get(j));
                Optb = Pengetahuanumum.readOptionB(list.get(j));
                Optc = Pengetahuanumum.readOptionC(list.get(j));
                Optd = Pengetahuanumum.readOptionD(list.get(j));
                global = Pengetahuanumum.readAnswer(list.get(j));
                Pembahas = Pengetahuanumum.readPembahasan(list.get(j));
                j++;
            }else {
                variable=0; // untuk memenuhkan syarat mengakhiri soal
                countDownTimer.cancel();
                actionAfterFinishCount();
            }
        } else if (get.equals("ekonomiakuntansi")) { //c5
            if (ekonomiakuntansi == 0) {
                totalRecord = Ekonomiakuntansi.bacaJumlahRecord();
                if(totalRecord<jumSoal){
                    jumSoal = totalRecord;
                }
                for (i = 1; i <= totalRecord; i++) {
                    list.add(new Integer(i));
                }
                Collections.shuffle(list);
                ekonomiakuntansi=1;
            }
            if(soalKe<jumSoal){
                Ques = Ekonomiakuntansi.readQuestion(list.get(j));
                Opta = Ekonomiakuntansi.readOptionA(list.get(j));
                Optb = Ekonomiakuntansi.readOptionB(list.get(j));
                Optc = Ekonomiakuntansi.readOptionC(list.get(j));
                Optd = Ekonomiakuntansi.readOptionD(list.get(j));
                global = Ekonomiakuntansi.readAnswer(list.get(j));
                Pembahas = Ekonomiakuntansi.readPembahasan(list.get(j));
                j++;
            }else {
                variable=0; // untuk memenuhkan syarat mengakhiri soal
                countDownTimer.cancel();
                actionAfterFinishCount();
            }
        } else if (get.equals("bahasainggris")) { //c6
            if (bahasainggris == 0) {
                totalRecord = Bahasainggris.bacaJumlahRecord();
                if(totalRecord<jumSoal){
                    jumSoal = totalRecord;
                }
                for (i = 1; i <= totalRecord; i++) {
                    list.add(new Integer(i));
                }
                Collections.shuffle(list);
                bahasainggris=1;
            }
            if(soalKe<jumSoal){
                Ques = Bahasainggris.readQuestion(list.get(j));
                Opta = Bahasainggris.readOptionA(list.get(j));
                Optb = Bahasainggris.readOptionB(list.get(j));
                Optc = Bahasainggris.readOptionC(list.get(j));
                Optd = Bahasainggris.readOptionD(list.get(j));
                global = Bahasainggris.readAnswer(list.get(j));
                Pembahas = Bahasainggris.readPembahasan(list.get(j));
                j++;
            }else {
                variable=0; // untuk memenuhkan syarat mengakhiri soal
                countDownTimer.cancel();
                actionAfterFinishCount();
            }

        } else if (get.equals("literatur")) { //c7
            if (literatur == 0) {
                totalRecord = Literatur.bacaJumlahRecord();
                if(totalRecord<jumSoal){
                    jumSoal = totalRecord;
                }
                for (i = 1; i <= jumSoal; i++) {
                    list.add(new Integer(i));
                }
                Collections.shuffle(list);
                literatur=1;
            }
            if(soalKe<jumSoal){
                Ques = Literatur.readQuestion(list.get(j));
                Opta = Literatur.readOptionA(list.get(j));
                Optb = Literatur.readOptionB(list.get(j));
                Optc = Literatur.readOptionC(list.get(j));
                Optd = Literatur.readOptionD(list.get(j));
                global = Literatur.readAnswer(list.get(j));
                Pembahas = Literatur.readPembahasan(list.get(j));
                j++;
            }else {
                variable=0; // untuk memenuhkan syarat mengakhiri soal
                countDownTimer.cancel();
                actionAfterFinishCount();
            }
        } else if (get.equals("intelegensi")) { //c8
            if (intelegensi == 0) {
                totalRecord = Intelegensi.bacaJumlahRecord();
                if(totalRecord<jumSoal){
                    jumSoal = totalRecord;
                }
                for (i = 1; i <= totalRecord; i++) {
                    list.add(new Integer(i));
                }
                Collections.shuffle(list);
                intelegensi=1;
            }
            if(soalKe<jumSoal){
                Ques = Intelegensi.readQuestion(list.get(j));
                Opta = Intelegensi.readOptionA(list.get(j));
                Optb = Intelegensi.readOptionB(list.get(j));
                Optc = Intelegensi.readOptionC(list.get(j));
                Optd = Intelegensi.readOptionD(list.get(j));
                global = Intelegensi.readAnswer(list.get(j));
                Pembahas = Intelegensi.readPembahasan(list.get(j));
                j++;
            }else {
                variable=0; // untuk memenuhkan syarat mengakhiri soal
                countDownTimer.cancel();
                actionAfterFinishCount();
            }
        } else if (get.equals("kepribadian")) { //c9
            if (kepribadian == 0) {
                totalRecord = Kepribadian.bacaJumlahRecord();
                if(totalRecord<jumSoal){
                    jumSoal = totalRecord;
                }
                for (i = 1; i <= jumSoal; i++) {
                    list.add(new Integer(i));
                }
                Collections.shuffle(list);
                kepribadian=1;
            }
            if(soalKe<jumSoal){
                Ques = Kepribadian.readQuestion(list.get(j));
                Opta = Kepribadian.readOptionA(list.get(j));
                Optb = Kepribadian.readOptionB(list.get(j));
                Optc = Kepribadian.readOptionC(list.get(j));
                Optd = Kepribadian.readOptionD(list.get(j));
                global = Kepribadian.readAnswer(list.get(j));
                Pembahas = Kepribadian.readPembahasan(list.get(j));
                j++;
            }else {
                variable=0; // untuk memenuhkan syarat mengakhiri soal
                countDownTimer.cancel();
                actionAfterFinishCount();
            }
        } else if (get.equals("profesi")) { //c10
            if (profesi == 0) {
                totalRecord = Profesi.bacaJumlahRecord();
                if(totalRecord<jumSoal){
                    jumSoal = totalRecord;
                }
                for (i = 1; i <= jumSoal; i++) {
                    list.add(new Integer(i));
                }
                Collections.shuffle(list);
                profesi=1;
            }
            if(soalKe<jumSoal){
                Ques = Profesi.readQuestion(list.get(j));
                Opta = Profesi.readOptionA(list.get(j));
                Optb = Profesi.readOptionB(list.get(j));
                Optc = Profesi.readOptionC(list.get(j));
                Optd = Profesi.readOptionD(list.get(j));
                global = Profesi.readAnswer(list.get(j));
                Pembahas = Profesi.readPembahasan(list.get(j));
                j++;
            }else {
                variable=0; // untuk memenuhkan syarat mengakhiri soal
                countDownTimer.cancel();
                actionAfterFinishCount();
            }
        } else if (get.equals("grafikakomp")) { //c11
            if (grafikom == 0) {
                totalRecord = Grafikakomputer.bacaJumlahRecord();
                if(totalRecord<jumSoal){
                    jumSoal = totalRecord;
                }
                for (i = 1; i <= jumSoal; i++) {
                    list.add(new Integer(i));
                }
                Collections.shuffle(list);
                grafikom=1;
            }
            if(soalKe<jumSoal){
                Ques = Grafikakomputer.readQuestion(list.get(j));
                Opta = Grafikakomputer.readOptionA(list.get(j));
                Optb = Grafikakomputer.readOptionB(list.get(j));
                Optc = Grafikakomputer.readOptionC(list.get(j));
                Optd = Grafikakomputer.readOptionD(list.get(j));
                global = Grafikakomputer.readAnswer(list.get(j));
                Pembahas = Grafikakomputer.readPembahasan(list.get(j));
                j++;
            }else {
                variable=0; // untuk memenuhkan syarat mengakhiri soal
                countDownTimer.cancel();
                actionAfterFinishCount();
            }
        }else if (get.equals("kecerdasan")) { //c12
            if (kecerdasan == 0) {
                totalRecord = Kecerdasanbuatan.bacaJumlahRecord();
                if(totalRecord<jumSoal){
                    jumSoal = totalRecord;
                }
                for (i = 1; i <= jumSoal; i++) {
                    list.add(new Integer(i));
                }
                Collections.shuffle(list);
                kecerdasan=1;
            }
            if(soalKe<jumSoal){
                Ques = Kecerdasanbuatan.readQuestion(list.get(j));
                Opta = Kecerdasanbuatan.readOptionA(list.get(j));
                Optb = Kecerdasanbuatan.readOptionB(list.get(j));
                Optc = Kecerdasanbuatan.readOptionC(list.get(j));
                Optd = Kecerdasanbuatan.readOptionD(list.get(j));
                global = Kecerdasanbuatan.readAnswer(list.get(j));
                Pembahas = Kecerdasanbuatan.readPembahasan(list.get(j));
                j++;
            }else {
                variable=0; // untuk memenuhkan syarat mengakhiri soal
                countDownTimer.cancel();
                actionAfterFinishCount();
            }
        } else if (get.equals("rpl")) { //c13
            if (rpl == 0) {
                totalRecord = RekPerangkatLunak.bacaJumlahRecord();
                if(totalRecord<jumSoal){
                    jumSoal = totalRecord;
                }
                for (i = 1; i <= jumSoal; i++) {
                    list.add(new Integer(i));
                }
                Collections.shuffle(list);
                rpl=1;
            }
            if(soalKe<jumSoal){
                Ques = RekPerangkatLunak.readQuestion(list.get(j));
                Opta = RekPerangkatLunak.readOptionA(list.get(j));
                Optb = RekPerangkatLunak.readOptionB(list.get(j));
                Optc = RekPerangkatLunak.readOptionC(list.get(j));
                Optd = RekPerangkatLunak.readOptionD(list.get(j));
                global = RekPerangkatLunak.readAnswer(list.get(j));
                Pembahas = RekPerangkatLunak.readPembahasan(list.get(j));
                j++;
            }else {
                variable=0; // untuk memenuhkan syarat mengakhiri soal
                countDownTimer.cancel();
                actionAfterFinishCount();
            }
        }else if (get.equals("pbo")) { //c14
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
        }else if (get.equals("strukturdata")) { //c15
            if (struktur == 0) {
                totalRecord = StrukDat.bacaJumlahRecord();
                if(totalRecord<jumSoal){
                    jumSoal = totalRecord;
                }
                for (i = 1; i <= jumSoal; i++) {
                    list.add(new Integer(i));
                }
                Collections.shuffle(list);
                struktur=1;
            }
            if(soalKe<jumSoal){
                Ques = StrukDat.readQuestion(list.get(j));
                Opta = StrukDat.readOptionA(list.get(j));
                Optb = StrukDat.readOptionB(list.get(j));
                Optc = StrukDat.readOptionC(list.get(j));
                Optd = StrukDat.readOptionD(list.get(j));
                global = StrukDat.readAnswer(list.get(j));
                Pembahas = StrukDat.readPembahasan(list.get(j));
                j++;
            }else {
                variable=0; // untuk memenuhkan syarat mengakhiri soal
                countDownTimer.cancel();
                actionAfterFinishCount();
            }
        }else if (get.equals("pemrogramaninternet")) { //c16
            if (pemrograminternet == 0) {
                totalRecord = PemroInternet.bacaJumlahRecord();
                if(totalRecord<jumSoal){
                    jumSoal = totalRecord;
                }
                for (i = 1; i <= jumSoal; i++) {
                    list.add(new Integer(i));
                }
                Collections.shuffle(list);
                pemrograminternet=1;
            }
            if(soalKe<jumSoal){
                Ques = PemroInternet.readQuestion(list.get(j));
                Opta = PemroInternet.readOptionA(list.get(j));
                Optb = PemroInternet.readOptionB(list.get(j));
                Optc = PemroInternet.readOptionC(list.get(j));
                Optd = PemroInternet.readOptionD(list.get(j));
                global = PemroInternet.readAnswer(list.get(j));
                Pembahas = PemroInternet.readPembahasan(list.get(j));
                j++;
            }else {
                variable=0; // untuk memenuhkan syarat mengakhiri soal
                countDownTimer.cancel();
                actionAfterFinishCount();
            }
        }else if (get.equals("jaringankomputer")) { //c17
            if (struktur == 0) {
                totalRecord = Jarkom.bacaJumlahRecord();
                if(totalRecord<jumSoal){
                    jumSoal = totalRecord;
                }
                for (i = 1; i <= jumSoal; i++) {
                    list.add(new Integer(i));
                }
                Collections.shuffle(list);
                struktur=1;
            }
            if(soalKe<jumSoal){
                Ques = Jarkom.readQuestion(list.get(j));
                Opta = Jarkom.readOptionA(list.get(j));
                Optb = Jarkom.readOptionB(list.get(j));
                Optc = Jarkom.readOptionC(list.get(j));
                Optd = Jarkom.readOptionD(list.get(j));
                global = Jarkom.readAnswer(list.get(j));
                Pembahas = Jarkom.readPembahasan(list.get(j));
                j++;
            }else {
                variable=0; // untuk memenuhkan syarat mengakhiri soal
                countDownTimer.cancel();
                actionAfterFinishCount();
            }
        }else if (get.equals("desainweb")) { //c18
            if (struktur == 0) {
                totalRecord = Desain.bacaJumlahRecord();
                if(totalRecord<jumSoal){
                    jumSoal = totalRecord;
                }
                for (i = 1; i <= jumSoal; i++) {
                    list.add(new Integer(i));
                }
                Collections.shuffle(list);
                struktur=1;
            }
            if(soalKe<jumSoal){
                Ques = Desain.readQuestion(list.get(j));
                Opta = Desain.readOptionA(list.get(j));
                Optb = Desain.readOptionB(list.get(j));
                Optc = Desain.readOptionC(list.get(j));
                Optd = Desain.readOptionD(list.get(j));
                global = Desain.readAnswer(list.get(j));
                Pembahas = Desain.readPembahasan(list.get(j));
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
                mProgressDialog = new ProgressDialog(Questions.this);
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

//    private void getAllSqlite(){
//        String myPath = Database_path + Database_name;
//        sqlite = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
//        Cursor cursor = sqlite.rawQuery("select * from " + Table_name, null);
//
//        List<String> fileName = new ArrayList<>();
//        if (cursor.moveToFirst()){
//            fileName.add(cursor.getString(cursor.getColumnIndex("OptionA")));
//            while(cursor.moveToNext()){
//                fileName.add(cursor.getString(cursor.getColumnIndex("OptionA")));
//            }
//        }
//        Log.d(TAG, "isitabel: " + fileName);
//        cursor.close();
//        sqlite.close();
//    }

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
            BitmapDrawable bdrawable = new BitmapDrawable(Questions.this.getResources(),result);
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
            BitmapDrawable bdrawable = new BitmapDrawable(Questions.this.getResources(),result);
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
            BitmapDrawable bdrawable = new BitmapDrawable(Questions.this.getResources(),result);
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
            BitmapDrawable bdrawable = new BitmapDrawable(Questions.this.getResources(),result);
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
