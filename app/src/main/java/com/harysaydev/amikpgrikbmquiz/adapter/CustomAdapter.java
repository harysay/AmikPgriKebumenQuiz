package com.harysaydev.amikpgrikbmquiz.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.harysaydev.amikpgrikbmquiz.R;
import com.harysaydev.amikpgrikbmquiz.model.Skor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by harysay
 * 1. where WE INFLATE OUR MODEL LAYOUT INTO VIEW ITEM
 * 2. THEN BIND DATA
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    Context context;
    List<Skor> MainImageUploadInfoList;
    private DatabaseReference databaseReference,isiTabelSkor,childNoHPGender;

    public CustomAdapter(Context context, List<Skor> TempList) {

        this.MainImageUploadInfoList = TempList;

        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myposition_list_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Skor studentDetails = MainImageUploadInfoList.get(position);

        //holder.gambarWong.setImageResource(R.drawable.man);
        holder.mySoreName.setText(studentDetails.getJeneng());
        holder.myScoreValue.setText(studentDetails.getJumlahPoint());

    }

    @Override
    public int getItemCount() {

        return MainImageUploadInfoList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mySoreName;
        public TextView myScoreValue;
        //public ImageView gambarWong;

        public ViewHolder(View itemView) {

            super(itemView);
            //gambarWong = (ImageView) itemView.findViewById(R.id.userMyScoreimageView);
            mySoreName = (TextView) itemView.findViewById(R.id.valNamaMyScore);
            myScoreValue = (TextView) itemView.findViewById(R.id.valScoreValue);
        }
    }
}