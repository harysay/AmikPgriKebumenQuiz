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
 * Created by imanajihari@gmail.com on 08-04-2016.
 */
public class Bahasainggris extends SQLiteOpenHelper {
    private static final String Database_path = "/data/data/com.harysaydev.amikpgrikbmquiz/databases/";
    private static final String Database_name = "english.db";//NAME of database stored in Assets folder
    private static final String Table_name = "english";//name of table
    private static final String uid = "_id";//name of column1
    private static final String uidSqliteFromweb = "Idfromweb";//name of column2
    private static final String Question = "Question";//name of column2
    private static final String OptionA = "OptionA";//name of column3
    private static final String OptionB = "OptionB";//name of column4
    private static final String OptionC = "OptionC";//name of column5
    private static final String OptionD = "OptionD";//name of column6
    private static final String Answer = "Answer";//name of column7
    private static final String Conclusion = "Conclusion";
    private static final int version = 1;//version of database signifies if there is any upgradation or not
    public SQLiteDatabase sqlite;//object of type SQLiteDatabase
    private Context context;//Context object to get context from Question Activity

    public Bahasainggris(Context context) {//constructor
        super(context, Database_name, null, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //No code because we have already created the database
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.disableWriteAheadLogging();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //No code because we have already created the database
    }

    public void createDatabase() {
        createDB();
    }

    private void createDB() {

        boolean dbexist = DBexists();//calling the function to check db exists or not
        if (!dbexist)//if database doesnot exist
        {

            this.getReadableDatabase();//Create an empty file
            copyDBfromResource();//copy the database file information of assets folder to newly create file
        }
    }

    private void copyDBfromResource() {

        InputStream is;
        OutputStream os;
        String filePath = Database_path + Database_name;
        try {
            is = context.getAssets().open(Database_name);//reading purpose
            os = new FileOutputStream(filePath);//writing purpose
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);//writing to file
            }
            os.flush();//flush the outputstream
            is.close();//close the inputstream
            os.close();//close the outputstream

        } catch (IOException e) {
            throw new Error("Problem copying database file:");
        }
    }

    public void openDatabase() throws SQLException//called by onCreate method of Questions Activity
    {

        String myPath = Database_path + Database_name;
        sqlite = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    private boolean DBexists()//Check whether the db file exists or not
    {
//        SQLiteDatabase db = null;
//        try {
//            String databasePath = Database_path + Database_name;
//            db = SQLiteDatabase.openDatabase(databasePath, null, SQLiteDatabase.OPEN_READWRITE);
//            db.setLocale(Locale.getDefault());
//            db.setVersion(1);
//            db.setLockingEnabled(true);
//        } catch (SQLException e) {
//            Log.e("Sqlite", "Database not found");
//        }
//        if (db != null)
//            db.close();///close the opened file
//        return db != null ? true : false;
        File dbFile = this.context.getDatabasePath(Database_name);
        return dbFile.exists();

    }

    public String readQuestion(int i)//Used to read the data from the Des.db file where id is given and we choose id randomly
    {
        String Ans = "";//string that contains the required field  note that Ans is just a local string not related to Answer or Option...
        Cursor c = sqlite.rawQuery("SELECT " + Question + " FROM " + Table_name + " WHERE " + uid + " = " + i + "", null);//cursor to that query
        if (c.moveToFirst())
            Ans = c.getString(0);
        else
            Ans = "";
        return Ans;
    }

    public String readOptionA(int i)//Used to read the data from the Des.db file where id is given and we choose id randomly
    {
        String Ans = "";//string that contains the required field  note that Ans is just a local string not related to Answer or Option...
        Cursor c = sqlite.rawQuery("SELECT " + OptionA + " FROM " + Table_name + " WHERE " + uid + " = " + i + "", null);//cursor to that query
        if (c.moveToFirst())
            Ans = c.getString(0);
        else
            Ans = "";
        return Ans;
    }

    public String readOptionB(int i)//Used to read the data from the Des.db file where id is given and we choose id randomly
    {
        String Ans = "";//string that contains the required field  note that Ans is just a local string not related to Answer or Option...
        Cursor c = sqlite.rawQuery("SELECT " + OptionB + " FROM " + Table_name + " WHERE " + uid + " = " + i + "", null);//cursor to that query
        if (c.moveToFirst())
            Ans = c.getString(0);
        else
            Ans = "";
        return Ans;
    }

    public String readOptionC(int i)//Used to read the data from the Des.db file where id is given and we choose id randomly
    {
        String Ans = "";//string that contains the required field  note that Ans is just a local string not related to Answer or Option...
        Cursor c = sqlite.rawQuery("SELECT " + OptionC + " FROM " + Table_name + " WHERE " + uid + " = " + i + "", null);//cursor to that query
        if (c.moveToFirst())
            Ans = c.getString(0);
        else
            Ans = "";
        return Ans;
    }

    public String readOptionD(int i)//Used to read the data from the Des.db file where id is given and we choose id randomly
    {
        String Ans = "";//string that contains the required field  note that Ans is just a local string not related to Answer or Option...
        Cursor c = sqlite.rawQuery("SELECT " + OptionD + " FROM " + Table_name + " WHERE " + uid + " = " + i + "", null);//cursor to that query
        if (c.moveToFirst())
            Ans = c.getString(0);
        else
            Ans = "";
        return Ans;
    }

    public String readAnswer(int i)//Used to read the data from the Des.db file where id is given and we choose id randomly
    {

        String Ans = "";//string that contains the required field
        Cursor c = sqlite.rawQuery("SELECT " + Answer + " FROM " + Table_name + " WHERE " + uid + " = " + i + "", null);//cursor to that query
        if (c.moveToFirst())
            Ans = c.getString(0);
        else
            Ans = "";
        return Ans;
    }

    public String readPembahasan(int i)//Used to read the data from the Des.db file where id is given and we choose id randomly
    {
        String pembahasan = "";//string that contains the required field
        Cursor c = sqlite.rawQuery("SELECT " + Conclusion + " FROM " + Table_name + " WHERE " + uid + " = " + i + "", null);//cursor to that query
        if (c.moveToFirst())
            pembahasan = c.getString(0);
        else
            pembahasan = "";
        return pembahasan;
    }

//    public String bacaJumlahRecord()//Untuk mengetahui jumlah record yang ada
//    {
//        String jumRecord = "";//string that contains the required field
//        Cursor c = sqlite.rawQuery("SELECT COUNT(*) FROM " + Table_name + "", null);//cursor to that query
//        if (c.moveToFirst())
//            jumRecord = c.getString(0);
//        else
//            jumRecord = "";
//        return jumRecord;
//    }

    public int bacaJumlahRecord()//Untuk mengetahui jumlah record yang ada
    {
        int jumRecord ;//string that contains the required field
        Cursor c = sqlite.rawQuery("SELECT " + uid + " FROM " + Table_name, null);//cursor to that query
        jumRecord = c.getCount();
        return jumRecord;
    }

    public void insertQuestion(String idFromWeb, String pertanyaan, String optionA,String optionB,String optionC, String optionD, String answer, String conclusion)//Used to read the data from the Des.db file where id is given and we choose id randomly
    {
        if(cekDuplikasi(idFromWeb)=="false"){
            String SQLiteDataBaseQueryHolder = "INSERT INTO "+Table_name+" (Idfromweb,Question,OptionA,OptionB,OptionC,OptionD,Answer,Conclusion) VALUES('"+idFromWeb+"','"+pertanyaan+"','"+optionA+"','"+optionB+"','"+optionC+"','"+optionD+"','"+answer+"','"+conclusion+"');";
            sqlite.execSQL(SQLiteDataBaseQueryHolder);
        }
    }

    public String cekDuplikasi(String id_dariWeb)//Used to read the data from the Des.db file where id is given and we choose id randomly
    {
        String duplikasi = "";//string that contains the required field
        Cursor c = sqlite.rawQuery("SELECT " + uid + " FROM " + Table_name + " WHERE " + uid + " = " + id_dariWeb + "", null);//cursor to that query
        if (c.getCount()>0) {
            duplikasi = "true";
        }
        else {
            Cursor d = sqlite.rawQuery("SELECT " + uidSqliteFromweb + " FROM " + Table_name + " WHERE " + uidSqliteFromweb + " = " + id_dariWeb + "", null);
            if(d.getCount()>1){
                duplikasi = "true";
            }else {
                duplikasi = "false";
            }
        }
        return duplikasi;
    }

//    public void insertQuestion(String idFromWeb, String pertanyaan, String optionA,String optionB,String optionC, String optionD, String answer, String conclusion)//Used to read the data from the Des.db file where id is given and we choose id randomly
//    {
//        if(CheckIsDataAlreadyInDBorNot(Table_name,uidSqliteFromweb,idFromWeb)==false){
//            String SQLiteDataBaseQueryHolder = "INSERT INTO "+Table_name+" (Idfromweb,Question,OptionA,OptionB,OptionC,OptionD,Answer,Conclusion) VALUES('"+idFromWeb+"','"+pertanyaan+"','"+optionA+"','"+optionB+"','"+optionC+"','"+optionD+"','"+answer+"','"+conclusion+"');";
//            sqlite.execSQL(SQLiteDataBaseQueryHolder);
//        }
//    }
//
//    public boolean CheckIsDataAlreadyInDBorNot(String TableName, String idfieldSqlite, String fieldValueWeb) {
//        //SQLiteDatabase sqldb = EGLifeStyleApplication.sqLiteDatabase;
//        String Query = "Select * from " + TableName + " where " + idfieldSqlite + " = " + fieldValueWeb;
//        Cursor cursor = sqlite.rawQuery(Query, null);
//        if(cursor.getCount() == 0){
//            cursor.close();
//            return false;
//        }
//        cursor.close();
//        return true;
//    }
}
