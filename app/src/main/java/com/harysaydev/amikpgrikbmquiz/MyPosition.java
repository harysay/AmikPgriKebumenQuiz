package com.harysaydev.amikpgrikbmquiz;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.harysaydev.amikpgrikbmquiz.adapter.CustomAdapter;
import com.harysaydev.amikpgrikbmquiz.model.Skor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class MyPosition extends AppCompatActivity {
    private static final String TAG = "MyPosition";
    //private ListView lvMyPosition;
    ArrayList<HashMap<String, String>> myPositionArrayList;
    private FirebaseDatabase database;
    private DatabaseReference tabel_skor;
    String totalMyScore,header_name,header_phone, nilaiGrafika, nilaiPbo,nilaiKecerdasanBuatan, nilaiRpl;
    int z,y = 0;
    DatabaseReference databaseReference;
    ProgressDialog progressDialog;
    List<Skor> list = new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter ;
    private Toast mToastToShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //lvMyPosition = (ListView) findViewById(R.id.recyclerViewMySkor);
        setContentView(R.layout.activity_my_position);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewMySkor);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MyPosition.this));



        database = FirebaseDatabase.getInstance();
        tabel_skor  = database.getReference("Skor");
        SharedPreferences sharedPreferences = getSharedPreferences("Content_main", Context.MODE_PRIVATE);
        SharedPreferences sharedcore = getSharedPreferences("Score", Context.MODE_PRIVATE);
        //Set name,email,image in  the navigation side drawer to those we enter in the login page
        header_name = sharedPreferences.getString("name", "xyz");
        header_phone = sharedPreferences.getString("phone", "0895325322911");
        float nilGrafika = sharedcore.getFloat("getNilaiGrafikakomputer",0);
        nilaiGrafika = Float.toString(nilGrafika);
        float nilPbo = sharedcore.getFloat("getNilaiPbo",0);
        nilaiPbo = Float.toString(nilPbo);
        float nilKecerdasanBuatan = sharedcore.getFloat("getNilaiKecerdasanbuatan",0);
        nilaiKecerdasanBuatan = Float.toString(nilKecerdasanBuatan);
        float nilRpl = sharedcore.getFloat("getNilaiRpl",0);
        nilaiRpl = Float.toString(nilRpl);
        int jumTotal = sharedcore.getInt("Komputer", 0) + sharedcore.getInt("Kebangsaan", 0) + sharedcore.getInt("Pengetahuankampus", 0)+sharedcore.getInt("Pengetahuanumum", 0)+sharedcore.getInt("Ekonomiakuntansi", 0) + sharedcore.getInt("Bahasainggris", 0)+sharedcore.getInt("Literatur", 0)+sharedcore.getInt("Intelegensi", 0)+sharedcore.getInt("Kepribadian", 0)+sharedcore.getInt("Profesi", 0)+sharedcore.getInt("Grafikakomputer", 0)+sharedcore.getInt("Kecerdasanbuatan", 0)+sharedcore.getInt("Rpl", 0)+sharedcore.getInt("Pbo", 0);
        totalMyScore = Integer.toString(jumTotal);
        //insertScore();

        progressDialog = new ProgressDialog(MyPosition.this);
        progressDialog.setMessage("Loading Data Score All User");
        progressDialog.show();
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tabel_skor.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //String coba = header_name;
                if(dataSnapshot.child(header_phone).exists()){
                    if(z==0) {
                        showToast();
                        //Toast.makeText(MyPosition.this, "Nomor handphone sudah terdaftar, data skor yang tertampil adalah skor sebelumnya!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Skor myskor = new Skor(header_name,nilaiGrafika,nilaiPbo,nilaiKecerdasanBuatan,nilaiRpl,totalMyScore);
                    tabel_skor.child(header_phone).setValue(myskor);
                    z++;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //databaseReference = FirebaseDatabase.getInstance().getReference().child("Skor");
        tabel_skor.addValueEventListener(new ValueEventListener() { //addListenerForSingleValueEven
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    Skor skors = dataSnapshot.getValue(Skor.class);

                    list.add(skors);
                    Log.d("Aufruf", "onItemClick"+list);
                }
                //Collections.reverse(list);
                Collections.sort(list,(o1, o2) -> {
                    if(Integer.parseInt(o1.getJumlahPoint()) > Integer.parseInt(o2.getJumlahPoint()))
                        return 1;
                    if(Integer.parseInt(o1.getJumlahPoint()) < Integer.parseInt(o2.getJumlahPoint()))
                        return -1;
                    return 0;
                });
                Collections.reverse(list);
                adapter = new CustomAdapter(MyPosition.this, list);
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                progressDialog.dismiss();

            }

        });
        //tarikDataScore();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_myposition);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//munculkan panah kiri

    }

    public void showToast() {
        // Set the toast and duration
        int toastDurationInMilliSeconds = 10000;
        mToastToShow = Toast.makeText(this, "Nomor handphone Anda saat ini sudah terdaftar, data skor yang tertampil adalah skor yang di-Submit sebelumnya!", Toast.LENGTH_LONG);

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

//    public void insertScore() {
//        tabel_skor.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if(dataSnapshot.child(header_phone).exists()){
//                    if(z==0) {
//                        showToast();
//                        //Toast.makeText(MyPosition.this, "Nomor handphone sudah terdaftar, data skor yang tertampil adalah skor sebelumnya!", Toast.LENGTH_SHORT).show();
//                    }
//                }else{
//                    Skor myskor = new Skor(header_name,totalMyScore);
//                    tabel_skor.child(header_phone).setValue(myskor);
//                    z++;
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }

    public void tarikDataScore() {

//        //INITIALIZE FIREBASE DB
//        db = FirebaseDatabase.getInstance().getReference();
//        helper = new FbHelper(db);
//        db.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//                //ADAPTER
//                adapter = new CustomAdapter(MyPosition.this, helper.retrieve());
//                lvMyPosition.setAdapter(adapter);
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });


//        Query queryRef = tabel_skor.child("Skor").orderByChild("jumpoints").limitToFirst(100);
//        queryRef.addValueEventListener(new ValueEventListener() {
//
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
//                    Skor score=postSnapshot.getValue(Skor.class);
//                    Log.d(TAG," values is " + score.getJumlahPoint());
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
    }

//    private class GetAndSetSkor extends AsyncTask<Void, Void, Void> {
//
//        ProgressDialog myPositonPdLoading = new ProgressDialog(MyPosition.this);
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            myPositonPdLoading.setMessage("\tLoading...");
//            myPositonPdLoading.setCancelable(false);
//            myPositonPdLoading.show();
//        }
//
//        @Override
//        protected Void doInBackground(Void... arg0) {
//
//
////                    JSONObject jsonObjLemburDetail = new JSONObject(jsonStrLemburDetail);
////
////                    // Getting JSON Array node
////                    JSONArray lemburDetailList = jsonObjLemburDetail.getJSONArray("resultDataList");
////
////                    // looping through All Contacts
////                    for (int i = 0; i < lemburDetailList.length(); i++) {
////                        JSONObject lemDetail = lemburDetailList.getJSONObject(i);
////                        String lemburKalenderDate = lemDetail.getString("Calender_date");
////                        String lemburKalenderStatus = lemDetail.getString("Calender_status_name");
////                        String lemburShiftName = lemDetail.getString("Shift_name");
////                        String lemburTimeIn = lemDetail.getString("Time_in");
////                        String lembutTimeOut = lemDetail.getString("Time_from");
////                        String lemburDuration = lemDetail.getString("Time_duration");
////                        String lemburKonversi = lemDetail.getString("Conversi_overtime");
////                        String lembutReimburse = lemDetail.getString("Conversi_reimburse");
////                        String lemburUangLembur = lemDetail.getString("Conversi_amount_format");
////
////                        HashMap<String, String> lemburDetailArray = new HashMap<>();
////
////                        lemburDetailArray.put("kalender_date", lemburKalenderDate);
////                        lemburDetailArray.put("kalender_status", lemburKalenderStatus);
////                        lemburDetailArray.put("shift_name", lemburShiftName);
////                        lemburDetailArray.put("time_in", lemburTimeIn);
////                        lemburDetailArray.put("time_out", lembutTimeOut);
////                        lemburDetailArray.put("lembur_durasi", lemburDuration);
////                        lemburDetailArray.put("lembur_konversi", lemburKonversi);
////                        lemburDetailArray.put("lembur_reimburse", lembutReimburse);
////                        lemburDetailArray.put("uang_lembur", lemburUangLembur);
////                        lemburDetailArrayList.add(lemburDetailArray);
////                    }
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void result) {
//            super.onPostExecute(result);
////
////            ListAdapter adapter = new SimpleAdapter(LemburDetail.this, lemburDetailArrayList,
////                    R.layout.lembur_fragment_detail_list_item, new String[]{ "kalender_date","kalender_status","shift_name","time_in","time_out","lembur_durasi","lembur_konversi","lembur_reimburse","uang_lembur"},
////                    new int[]{R.id.valKalenderDate,R.id.valKalenderStatus,R.id.valJenisShift,R.id.valLemburDari,R.id.valLemburSampai,R.id.valLemburDurasi,R.id.valKonversiOt,R.id.valReimburseLembur,R.id.valUangLembur});
////            lvLemburDetail.setAdapter(adapter);
//            myPositonPdLoading.dismiss();
//        }
//    }
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
