package com.example.huzaifa.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by huzaifa on 21-Mar-17.
 */

public class DataBaseHelper extends SQLiteOpenHelper {
    private Context mycontext;

    private String DB_PATH = "/data/data/com.example.huzaifa.myapplication/databases/";
    private static String DB_NAME = "calorie.sqlite";//the extension may be .sqlite or .db
    public SQLiteDatabase myDataBase;
    String Table_Name="Calories";
    /*private String DB_PATH = "/data/data/"
                        + mycontext.getApplicationContext().getPackageName()
                        + "/databases/";*/

    public DataBaseHelper(Context context) throws IOException {
        super(context,DB_NAME,null,1);
        this.mycontext=context;
        boolean dbexist = checkdatabase();
        if (dbexist) {
            //System.out.println("Database exists");
            opendatabase();
        } else {
            System.out.println("Database doesn't exist");
            createdatabase();
        }
    }

    public void createdatabase() throws IOException {
        boolean dbexist = checkdatabase();
        if(dbexist) {
            //System.out.println(" Database exists.");
        } else {
            this.getReadableDatabase();
            try {
                copydatabase();
            } catch(IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    private boolean checkdatabase() {
        //SQLiteDatabase checkdb = null;
        boolean checkdb = false;
        try {
            String myPath = DB_PATH + DB_NAME;
            File dbfile = new File(myPath);
            //checkdb = SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READWRITE);
            checkdb = dbfile.exists();
        } catch(SQLiteException e) {
            System.out.println("Database doesn't exist");
        }
        return checkdb;
    }

    private void copydatabase() throws IOException {
        //Open your local db as the input stream
        InputStream myinput = mycontext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outfilename = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myoutput = new FileOutputStream("/data/data/com.example.huzaifa.myapplication/databases/calorie.sqlite");

        // transfer byte to inputfile to outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myinput.read(buffer))>0) {
            myoutput.write(buffer,0,length);
        }

        //Close the streams
        myoutput.flush();
        myoutput.close();
        myinput.close();
    }

    public void opendatabase() throws SQLException {
        //Open the database
        String mypath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public synchronized void close() {
        if(myDataBase != null) {
            myDataBase.close();
        }
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
     myDataBase=db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public ArrayList<String> getFoodName(String match) {

            String selectQuery = "SELECT Food FROM "+Table_Name+" where Food like \""+match+"%\"";
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<String> data=new ArrayList<String>();
            if (cursor.moveToFirst()) {
                do {

                    data.add(cursor.getString(0));
                } while (cursor.moveToNext());
            }
            db.close();
            return data;


    }

    public float getCalorieFood(String match) {

        String selectQuery = "SELECT kCal FROM "+Table_Name+" where Food = \""+match+"\"";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        float calories=0;
        if (cursor.moveToFirst())
            calories=cursor.getFloat(0);

        db.close();
        return calories;


    }

    public ArrayList<String> getActivityName(String match) {

        String selectQuery = "SELECT Activity FROM "+Table_Name+" where Activity like \""+match+"%\"";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<String> data=new ArrayList<String>();
        if (cursor.moveToFirst()) {
            do {

                data.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        db.close();
        return data;


    }

    public float getCalorieActivity(String match) {

        String selectQuery = "SELECT weightfactor FROM "+Table_Name+" where Activity = \""+match+"\"";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        float calories=0;
        if (cursor.moveToFirst())
            calories=cursor.getFloat(0);

        db.close();
        return calories;


    }

    // Add your public helper methods to access and get content from the database.
    // You could return cursors by doing "return myDataBase.query(....)" so it'd be easy
    // to you to create adapters for your views.

}
