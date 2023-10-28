package com.harysaydev.amikpgrikbmquiz;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class ScoreCard extends AppCompatActivity {
    TextView totPoints, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11,a12,a13,a14,a15,a16,a17,a18;
    String totalMyScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_card);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SharedPreferences sharedPreferences = getSharedPreferences("Score", Context.MODE_PRIVATE);
        a1 = (TextView) findViewById(R.id.scorecomputer);
        a2 = (TextView) findViewById(R.id.scorekebangsaan);
        a3 = (TextView) findViewById(R.id.scorepengetahuankampus);
        a4 = (TextView) findViewById(R.id.scorepengetahuanumum);
        a5 = (TextView) findViewById(R.id.scoreekonomiakuntansi);
        a6 = (TextView) findViewById(R.id.scoreinggris);
        a7 = (TextView) findViewById(R.id.scoreliteratur);
        a8 = (TextView) findViewById(R.id.scoreintelegensi);
        a9 = (TextView) findViewById(R.id.scorekepribadian);
        a10 = (TextView) findViewById(R.id.scoreprofesi);
        a11 = (TextView) findViewById(R.id.scoregrafikakomputer);
        a12 = (TextView) findViewById(R.id.scorepbo);
        a13 = (TextView) findViewById(R.id.scorekecerdasanbuat);
        a14 = (TextView) findViewById(R.id.scorerpl);
        a15 = (TextView) findViewById(R.id.scorestrukturdata);
        a16 = (TextView) findViewById(R.id.scorepemroginternet);
        a17 = (TextView) findViewById(R.id.scorejarkom);
        a18 = (TextView) findViewById(R.id.scoredesainweb);
        totPoints = (TextView) findViewById(R.id.totPoint);
        int jumTotal = sharedPreferences.getInt("Komputer", 0) +
                sharedPreferences.getInt("Kebangsaan", 0) +
                sharedPreferences.getInt("Pengetahuankampus", 0)+
                sharedPreferences.getInt("Pengetahuanumum", 0)+
                sharedPreferences.getInt("Ekonomiakuntansi", 0) +
                sharedPreferences.getInt("Bahasainggris", 0)+
                sharedPreferences.getInt("Literatur", 0)+
                sharedPreferences.getInt("Intelegensi", 0)+
                sharedPreferences.getInt("Kepribadian", 0)+
                sharedPreferences.getInt("Profesi", 0)+
                sharedPreferences.getInt("Grafikakomputer", 0)+
                sharedPreferences.getInt("Pbo", 0)+
                sharedPreferences.getInt("Kecerdasanbuatan", 0)+
                sharedPreferences.getInt("Rpl", 0)+
                sharedPreferences.getInt("StrukturData", 0)+
                sharedPreferences.getInt("PemrogInternet", 0)+
                sharedPreferences.getInt("JaringanKomp", 0)+
                sharedPreferences.getInt("DesainWeb", 0);
        totalMyScore = Integer.toString(jumTotal);
        try {
            a1.setText("" + sharedPreferences.getInt("Komputer", 0));
            a2.setText("" + sharedPreferences.getInt("Kebangsaan", 0));
            a3.setText("" + sharedPreferences.getInt("Pengetahuankampus", 0));
            a4.setText("" + sharedPreferences.getInt("Pengetahuanumum", 0));
            a5.setText("" + sharedPreferences.getInt("Ekonomiakuntansi", 0));
            a6.setText("" + sharedPreferences.getInt("Bahasainggris", 0));
            a7.setText("" + sharedPreferences.getInt("Literatur", 0));
            a8.setText("" + sharedPreferences.getInt("Intelegensi", 0));
            a9.setText("" + sharedPreferences.getInt("Kepribadian", 0));
            a10.setText("" + sharedPreferences.getInt("Profesi", 0));
            a11.setText("" + sharedPreferences.getInt("Grafikakomputer", 0));
            a12.setText("" + sharedPreferences.getInt("Pbo", 0));
            a13.setText("" + sharedPreferences.getInt("Kecerdasanbuatan", 0));
            a14.setText("" + sharedPreferences.getInt("Rpl", 0));
            a15.setText("" + sharedPreferences.getInt("StrukturData", 0));
            a16.setText("" + sharedPreferences.getInt("PemrogInternet", 0));
            a17.setText("" + sharedPreferences.getInt("JaringanKomp", 0));
            a18.setText("" + sharedPreferences.getInt("DesainWeb", 0));
            totPoints.setText(totalMyScore);
        } catch (Exception e) {
            Toast.makeText(ScoreCard.this, "" + e, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
