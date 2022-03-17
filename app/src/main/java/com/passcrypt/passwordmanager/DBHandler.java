package com.passcrypt.passwordmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;


public class DBHandler extends SQLiteOpenHelper {
    private Context con;
    public DBHandler(Context context) {
        super(context,(new SP()).path(context),null, 1);
        con = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + "table1" + " ("
                + "title" + " TEXT, "
                + "website" + " TEXT,"
                + "username" + " TEXT,"
                + "password" + " TEXT,"
                + "notes" + " TEXT)";

        String hashtable = "CREATE TABLE " + "table2" + " (" + "hash" + " TEXT)";
        db.execSQL(query);
        db.execSQL(hashtable);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + "table1");
        onCreate(db);
    }
    //Create
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addval(Tileobj tileobj){
        EncryptDecrypt e = new EncryptDecrypt(con);
        SQLiteDatabase dbw = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("title",tileobj.getText1());
        contentValues.put("website",e.encrypt(tileobj.getweb()) );
        contentValues.put("username",e.encrypt(tileobj.getuser()) );
        contentValues.put("password",e.encrypt(tileobj.getpass()) );
        contentValues.put("notes",e.encrypt(tileobj.getnote()) );
        dbw.insert("table1", null, contentValues);
        dbw.close();
    }
    //Read
    //All parts of this code is not useful
    //This code was modified to check if Title i.e. given entry is already present in database or not
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Boolean readval(String title){
        EncryptDecrypt d = new EncryptDecrypt(con);
        SQLiteDatabase dbr = this.getReadableDatabase();
        Boolean b = true;

        Tileobj rthis = null;
        Cursor cursor = dbr.rawQuery("SELECT * FROM " + "table1", null);
        int i = 0;
        int row = 0;
        while (cursor.moveToPosition(i)){
            if ( cursor.getString(0).equals(title) ){
                row = i;
                b = false;
            }
            i++;
        }
        if (cursor.moveToPosition(row)) {
            rthis = new Tileobj("", cursor.getString(0) , d.decrypt(cursor.getString(1)) ,d.decrypt(cursor.getString(2)) ,d.decrypt(cursor.getString(3)) ,d.decrypt(cursor.getString(4)) );
        }
        cursor.close();
//        return rthis;
        return b;
    }
    //Update
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void updateval(String title, Tileobj tileobj){
        EncryptDecrypt e = new EncryptDecrypt(con);
        SQLiteDatabase dbw = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title",tileobj.getText1());
        values.put("website",e.encrypt( tileobj.getweb() ) );
        values.put("username",e.encrypt( tileobj.getuser() ) );
        values.put("password",e.encrypt(tileobj.getpass()) );
        values.put("notes",e.encrypt(tileobj.getnote()) );
        dbw.update("table1", values, "title = ?", new String[]{title});
        dbw.close();
    }
    //Delete
    public void delval(String title){
        SQLiteDatabase dbw = this.getWritableDatabase();
        dbw.delete("table1", "title = ?", new String[]{title});
        dbw.close();
    }
    //Read All Values
    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<Tileobj> readallval(){
        EncryptDecrypt d = new EncryptDecrypt(con);
        SQLiteDatabase dbr = this.getReadableDatabase();
        ArrayList<Tileobj> arrayList = new ArrayList<>();
        Tileobj rthis = null;
        Cursor cursor = dbr.rawQuery("SELECT * FROM " + "table1", null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                rthis = new Tileobj("",cursor.getString(0) ,d.decrypt(cursor.getString(1)) ,d.decrypt(cursor.getString(2)) ,d.decrypt(cursor.getString(3)) ,d.decrypt(cursor.getString(4)) );
                arrayList.add(rthis);
                cursor.moveToNext();
            }
        }
        dbr.close();
        return arrayList;
    }
    //add pass hash
    public void puthash(){
        if (!(new Hash()).h(con).isEmpty()){
            SQLiteDatabase dbw = this.getWritableDatabase();
            SQLiteDatabase dbr = this.getReadableDatabase();
            Cursor cursor = dbr.rawQuery("SELECT * FROM " + "table2", null);
            if (!cursor.moveToFirst()){
                ContentValues contentValues = new ContentValues();
                contentValues.put("hash", (new Hash()).h(con));
                dbw.insert("table2", null, contentValues);
            }
            dbw.close();
            dbr.close();
        }
    }
    //Get hash from db to Login
    public String gethashfromdb(){
        SQLiteDatabase dbr = this.getReadableDatabase();
        Cursor cursor = dbr.rawQuery("SELECT * FROM " + "table2", null);
        cursor.moveToFirst();
        String res = cursor.getString(0);
        dbr.close();
        return res;
    }
}

