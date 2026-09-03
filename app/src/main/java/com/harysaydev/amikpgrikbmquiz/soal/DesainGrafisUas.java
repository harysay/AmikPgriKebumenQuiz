package com.harysaydev.amikpgrikbmquiz.soal;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;

/**
 * Dibuat dengan meniru struktur PemBerorientasiObjekUas.java
 */
public class DesainGrafisUas extends SQLiteOpenHelper {
    private static final String Database_path = "/data/data/com.harysaydev.amikpgrikbmquiz/databases/";
    // Nama file database di folder assets
    private static final String Database_name = "desaingrafisuas.db";
    // Nama tabel di dalam database
    private static final String Table_name = "desaingrafis";
    private static final String uid = "_id";
    private static final String uidSqliteFromweb = "Idfromweb";
    private static final String Question = "Question";
    private static final String OptionA = "OptionA";
    private static final String OptionB = "OptionB";
    private static final String OptionC = "OptionC";
    private static final String OptionD = "OptionD";
    private static final String Answer = "Answer";
    private static final String Conclusion = "Conclusion";
    private static final int version = 1;
    public SQLiteDatabase sqlite;
    private Context context;

    // Constructor
    public DesainGrafisUas(Context context) {
        super(context, Database_name, null, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tidak perlu kode karena database sudah ada di assets
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Tidak perlu kode
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.disableWriteAheadLogging();
    }

    public void createDatabase() {
        createDB();
    }

    private void createDB() {
        boolean dbexist = DBexists();
        if (!dbexist) {
            this.getReadableDatabase();
            copyDBfromResource();
        }
    }

    private void copyDBfromResource() {
        InputStream is;
        OutputStream os;
        String filePath = Database_path + Database_name;
        try {
            is = context.getAssets().open(Database_name);
            os = new FileOutputStream(filePath);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
            os.flush();
            is.close();
            os.close();
        } catch (IOException e) {
            throw new Error("Problem copying database file:");
        }
    }

    public void openDatabase() throws SQLException {
        String myPath = Database_path + Database_name;
        sqlite = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    private boolean DBexists() {
        File dbFile = this.context.getDatabasePath(Database_name);
        return dbFile.exists();
    }

    public String readQuestion(int i) {
        String Ans = "";
        Cursor c = sqlite.rawQuery("SELECT " + Question + " FROM " + Table_name + " WHERE " + uid + " = " + i + "", null);
        if (c.moveToFirst())
            Ans = c.getString(0);
        else
            Ans = "";
        c.close();
        return Ans;
    }

    public String readOptionA(int i) {
        String Ans = "";
        Cursor c = sqlite.rawQuery("SELECT " + OptionA + " FROM " + Table_name + " WHERE " + uid + " = " + i + "", null);
        if (c.moveToFirst())
            Ans = c.getString(0);
        else
            Ans = "";
        c.close();
        return Ans;
    }

    public String readOptionB(int i) {
        String Ans = "";
        Cursor c = sqlite.rawQuery("SELECT " + OptionB + " FROM " + Table_name + " WHERE " + uid + " = " + i + "", null);
        if (c.moveToFirst())
            Ans = c.getString(0);
        else
            Ans = "";
        c.close();
        return Ans;
    }

    public String readOptionC(int i) {
        String Ans = "";
        Cursor c = sqlite.rawQuery("SELECT " + OptionC + " FROM " + Table_name + " WHERE " + uid + " = " + i + "", null);
        if (c.moveToFirst())
            Ans = c.getString(0);
        else
            Ans = "";
        c.close();
        return Ans;
    }

    public String readOptionD(int i) {
        String Ans = "";
        Cursor c = sqlite.rawQuery("SELECT " + OptionD + " FROM " + Table_name + " WHERE " + uid + " = " + i + "", null);
        if (c.moveToFirst())
            Ans = c.getString(0);
        else
            Ans = "";
        c.close();
        return Ans;
    }

    public String readAnswer(int i) {
        String Ans = "";
        Cursor c = sqlite.rawQuery("SELECT " + Answer + " FROM " + Table_name + " WHERE " + uid + " = " + i + "", null);
        if (c.moveToFirst())
            Ans = c.getString(0);
        else
            Ans = "";
        c.close();
        return Ans;
    }

    public String readPembahasan(int i) {
        String pembahasan = "";
        Cursor c = sqlite.rawQuery("SELECT " + Conclusion + " FROM " + Table_name + " WHERE " + uid + " = " + i + "", null);
        if (c.moveToFirst())
            pembahasan = c.getString(0);
        else
            pembahasan = "";
        c.close();
        return pembahasan;
    }

    public int bacaJumlahRecord() {
        Cursor c = sqlite.rawQuery("SELECT " + uid + " FROM " + Table_name, null);
        int jumRecord = c.getCount();
        c.close();
        return jumRecord;
    }

    public void insertQuestion(String idFromWeb, String pertanyaan, String optionA, String optionB, String optionC, String optionD, String answer, String conclusion) {
        if (cekDuplikasi(idFromWeb).equals("false")) {
            String SQLiteDataBaseQueryHolder = "INSERT INTO " + Table_name + " (Idfromweb,Question,OptionA,OptionB,OptionC,OptionD,Answer,Conclusion) VALUES('" + idFromWeb + "','" + pertanyaan + "','" + optionA + "','" + optionB + "','" + optionC + "','" + optionD + "','" + answer + "','" + conclusion + "');";
            sqlite.execSQL(SQLiteDataBaseQueryHolder);
        }
    }

    public String cekDuplikasi(String id_dariWeb) {
        String duplikasi = "false";
        Cursor cursor = sqlite.rawQuery("SELECT " + uidSqliteFromweb + " FROM " + Table_name + " WHERE " + uidSqliteFromweb + " = ?", new String[]{id_dariWeb});
        if (cursor.getCount() > 0) {
            duplikasi = "true";
        }
        cursor.close();
        return duplikasi;
    }
}