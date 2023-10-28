package com.harysaydev.amikpgrikbmquiz;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Setting_activity extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    private Toast mToastToShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final SharedPreferences sharedPreferences = getSharedPreferences("Score", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        final Button sound = (Button) findViewById(R.id.play_sound);
        Button reset = (Button) findViewById(R.id.reset);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (sharedPreferences.getInt("Sound", 0) == 0) {
            sound.setText("Mute Sound");
            mediaPlayer = MediaPlayer.create(this, R.raw.abc);
            mediaPlayer.start();
            mediaPlayer.setLooping(true);
        } else
            sound.setText("Play Sound");
        sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sound.getText().equals("Play Sound")) {
                    editor.putInt("Sound", 0).commit();
                    sound.setText("Mute Sound");
                    mediaPlayer = MediaPlayer.create(Setting_activity.this, R.raw.abc);
                    mediaPlayer.start();
                    mediaPlayer.setLooping(true);
                } else if (sound.getText().equals("Mute Sound")) {
                    editor.putInt("Sound", 1).commit();
                    sound.setText("Play Sound");
                    mediaPlayer.stop();
                }
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                editor.putInt("Komputer", 0);
//                editor.putInt("Kebangsaan", 0);
//                editor.putInt("Pengetahuankampus", 0);
//                editor.putInt("Pengetahuanumum", 0);
//                editor.putInt("Ekonomiakuntansi", 0);
//                editor.putInt("Bahasainggris", 0);
//                editor.putInt("Literatur", 0);
//                editor.putInt("Intelegensi", 0);
//                editor.putInt("Kepribadian", 0);
//                editor.putInt("Profesi", 0);
//                editor.commit();
//                Snackbar.make(v,"Highscore Reseted Successfully",Snackbar.LENGTH_LONG).show();
                showToast();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //finish();
    }

    public void showToast() {
        // Set the toast and duration
        int toastDurationInMilliSeconds = 10000;
        mToastToShow = Toast.makeText(this, "Satu nomor handphone hanya bisa menSubmit skor 1x!\n Anda tidak memiliki ijin untuk mereset skor, silahkan mendaftar kembali dengan nomor handphone yang berbeda atau hubungi pembuat aplikasi ini untuk melakukan reset skor :)\n Created by: Imanaji Hari Sayekti", Toast.LENGTH_LONG);

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

