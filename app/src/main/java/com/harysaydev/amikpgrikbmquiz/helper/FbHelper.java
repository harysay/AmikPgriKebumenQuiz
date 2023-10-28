package com.harysaydev.amikpgrikbmquiz.helper;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.harysaydev.amikpgrikbmquiz.model.Skor;

import java.util.ArrayList;

/**
 * Created by Oclemy on 6/21/2016 for ProgrammingWizards Channel and http://www.camposha.com.
 * 1.SAVE DATA TO FIREBASE
 * 2. RETRIEVE
 * 3.RETURN AN ARRAYLIST
 */
public class FbHelper {

    DatabaseReference db;
    Boolean saved;
    ArrayList<Skor> skors=new ArrayList<>();

    /*
 PASS DATABASE REFRENCE
  */
    public FbHelper(DatabaseReference db) {
        this.db = db;
    }
    //WRITE IF NOT NULL
    public Boolean save(Skor skor)
    {
        if(skor==null)
        {
            saved=false;
        }else
        {
            try
            {
                db.child("Skor").push().setValue(skor);
                saved=true;

            }catch (DatabaseException e)
            {
                e.printStackTrace();
                saved=false;
            }
        }

        return saved;
    }

    //IMPLEMENT FETCH DATA AND FILL ARRAYLIST
    private void fetchData(DataSnapshot dataSnapshot)
    {
        skors.clear();

        for (DataSnapshot ds : dataSnapshot.getChildren())
        {
            Skor spacecraft=ds.getValue(Skor.class);
            skors.add(spacecraft);
        }
    }

    //RETRIEVE
    public ArrayList<Skor> retrieve()
    {
        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return skors;
    }


}