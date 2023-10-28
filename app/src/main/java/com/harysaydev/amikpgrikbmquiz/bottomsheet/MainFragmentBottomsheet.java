package com.harysaydev.amikpgrikbmquiz.bottomsheet;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.harysaydev.amikpgrikbmquiz.MainActivity;
import com.harysaydev.amikpgrikbmquiz.Questions;
import com.harysaydev.amikpgrikbmquiz.R;

import java.io.InputStream;

public class MainFragmentBottomsheet extends Fragment {

    private LinearLayout mBottomSheet;
    Button btnMengertiLanjut;
    private ImageView mLeftArrow;
    String strtext,getpembahasan;
    TextView textJawaban,textPembahasan;
    ImageView jawabanGambar;
    private ImageView mRightArrow;
    ProgressDialog mProgressDialog;
    //onSomeEventListener someEventListener;
    private BottomSheetBehavior bottomSheetBehavior;

    public MainFragmentBottomsheet() {
        // Required empty public constructor
    }

    public static MainFragmentBottomsheet newInstance() {
        return new MainFragmentBottomsheet();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        strtext = getArguments().getString("jawaban");
        getpembahasan = getArguments().getString("pembahasan");
        View view = inflater.inflate(R.layout.bottomsheet_fragment_main, container, false);
        View test1View = view.findViewById(R.id.bottom_sheet);
        btnMengertiLanjut = (Button) test1View.findViewById(R.id.tmbl_mengertidanlanjutkan);
        textJawaban = (TextView) test1View.findViewById(R.id.jawabpembahasan);
        jawabanGambar = (ImageView)test1View.findViewById(R.id.jawabanGambar);
        textPembahasan = (TextView) test1View.findViewById(R.id.pembahasan);
        // find container view
        mBottomSheet = view.findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(mBottomSheet);
        // find arrows
        mLeftArrow = view.findViewById(R.id.bottom_sheet_left_arrow);
        mRightArrow = view.findViewById(R.id.bottom_sheet_right_arrow);
//        textJawaban.setText(strtext);
        setImageView();
        textPembahasan.setText(getpembahasan);
        initializeBottomSheet();
        //Questions listener=new Questions();
       // btnMengertiLanjut.setOnClickListener(listener);
        btnMengertiLanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Questions m1 = (Questions) getActivity();
//                m1.f1("oke");
                //getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                closeBottomSheet();
                //btn_bottom_sheet.setText("Expand sheet");
                //someEventListener.someEvent();
                //View viewmain = getActivity().findViewById(R.id.OptionA);
                //Questions soalku = new Questions();
//                soalku.soal();
                //tidak menciptakan objek baru mainclass nya namun memanggil getactivity
                Questions soal = (Questions)getActivity();
                soal.soal();
            }
        });
        return view;
    }

    public void initializeBottomSheet() {
        // init the bottom sheet behavior
        //BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(mBottomSheet);
        //bottomSheetBehavior.setHideable(true);
        // change the state of the bottom sheet
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        // change the state of the bottom sheet
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        // set callback for changes
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                if (isAdded()) {
                    transitionBottomSheetBackgroundColor(slideOffset);
                    animateBottomSheetArrows(slideOffset);
                }
            }
        });
    }

    public void setImageView(){
        if(strtext.length()>4){
            String coba = strtext.substring(0, 4);
            //Log.d(TAG, "isitabel: " + coba);
            if(coba.equals("http")){
                mProgressDialog = new ProgressDialog(getActivity());
                mProgressDialog.setMessage("Loading jawaban...");
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.show();
                jawabanGambar.setVisibility(View.VISIBLE);
                textJawaban.setText("");
                new downloadImageJawaban().execute(strtext);
                mProgressDialog.dismiss();
            }else{
                jawabanGambar.setVisibility(View.INVISIBLE);
                textJawaban.setText(strtext);
            }
        }else{
            jawabanGambar.setVisibility(View.INVISIBLE);
            textJawaban.setText(strtext);
        }
    }

    public void closeBottomSheet(){
        //BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(mBottomSheet);
        Questions m1 = (Questions) getActivity();
                m1.f1("oke");
        bottomSheetBehavior.setHideable(true);//Important to add
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    public void finishFragment(){
        getActivity().getSupportFragmentManager().popBackStack();
    }

    public void transitionBottomSheetBackgroundColor(float slideOffset) {
        int colorFrom = getResources().getColor(R.color.colorAccent);
        int colorTo = getResources().getColor(R.color.white);
//        mBottomSheet.setBackgroundColor(interpolateColor(slideOffset,
//                colorFrom, colorTo));
        mBottomSheet.setBackgroundColor(colorTo);
    }

    public void animateBottomSheetArrows(float slideOffset) {
        mLeftArrow.setRotation(slideOffset * -180);
        mRightArrow.setRotation(slideOffset * 180);
    }

//    public String getText() {
//        return "oke";
//    }

    // Helper method to interpolate colors
    private int interpolateColor(float fraction, int startValue, int endValue) {
        int startA = (startValue >> 24) & 0xff;
        int startR = (startValue >> 16) & 0xff;
        int startG = (startValue >> 8) & 0xff;
        int startB = startValue & 0xff;
        int endA = (endValue >> 24) & 0xff;
        int endR = (endValue >> 16) & 0xff;
        int endG = (endValue >> 8) & 0xff;
        int endB = endValue & 0xff;
        return ((startA + (int) (fraction * (endA - startA))) << 24) |
                ((startR + (int) (fraction * (endR - startR))) << 16) |
                ((startG + (int) (fraction * (endG - startG))) << 8) |
                ((startB + (int) (fraction * (endB - startB))));
    }

    // DownloadImage AsyncTask
    private class downloadImageJawaban extends AsyncTask<String, Void, Bitmap> {
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
            jawabanGambar.setImageBitmap(result);
        }
    }
}
