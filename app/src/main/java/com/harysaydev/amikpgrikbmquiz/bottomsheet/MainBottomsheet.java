package com.harysaydev.amikpgrikbmquiz.bottomsheet;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.harysaydev.amikpgrikbmquiz.R;


public class MainBottomsheet extends AppCompatActivity {
    Button vTombolBenar,vTombolSalah;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottomsheet_main);
        vTombolBenar = (Button) findViewById(R.id.pilihbenar);
        vTombolBenar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        vTombolSalah = (Button) findViewById(R.id.pilihsalah);
        vTombolSalah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("lemparan", "Nilai From Activity");
                MainFragmentBottomsheet fragment = MainFragmentBottomsheet.newInstance();
                fragment.setArguments(bundle);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_fragment_container, fragment)
                        .commit();
            }
        });

    }
}
