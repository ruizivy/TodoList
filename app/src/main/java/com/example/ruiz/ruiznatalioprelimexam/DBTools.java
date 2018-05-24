package com.example.ruiz.ruiznatalioprelimexam;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Ruiz on 7/22/2017.
 */

public class DBTools extends SQLiteOpenHelper {

    private Context context;
    private static int version = 2;
    private static String db_name = "DBToDoList";
    private String tbl_name = "tblTodoList";

    private String fld_id = "ID";
    private String fld_title = "title";
    private String fld_details = "details";
    private String fld_fromdate = "fromdate";
    private  String fld_fromtime = "fromtime";
    private  String fld_todate ="todate";
    private String fld_totime ="totime";
    private  String fld_priority ="priority";

    SQLiteDatabase db;

    public DBTools(Context appContext) {

        super(appContext, db_name, null, version);
        this.context = appContext;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + tbl_name + " ( " + fld_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                fld_title + " TEXT, " + fld_details + " TEXT , " + fld_fromdate + " TEXT, " +
                fld_fromtime + " TEXT, " + fld_todate + " TEXT, " + fld_totime + " TEXT , " + fld_priority + " INT )";

        db.execSQL(query);
        //query = "INSERT INTO " + tbl_name + " ( " + fld_name + "," + fld_password + " ) " + " VALUES('Ivy Rose','Ruiz') ";
        //db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String query = "DROP TABLE IF EXIST " + tbl_name;
        db.execSQL(query);
        onCreate(db);
    }

    public void openConnection() {

        db = this.getWritableDatabase();
    }

    public void closeconnection() {

        this.close();
    }

    public Cursor getAlluser() {

        openConnection();
        return db.query(tbl_name, new String[]{fld_id, fld_title,fld_details , fld_fromdate , fld_fromtime , fld_todate , fld_totime, fld_priority}, null, null, null, null, null, null);

    }
    public Cursor executeQuery(){
        db = this.getReadableDatabase();
        String query = "SELECT * FROM " +tbl_name+ " ORDER BY " + fld_priority + " DESC;";
        return  db.rawQuery(query , null);
    }

    public Cursor getUser(int id) {
        db = this.getWritableDatabase();
        String query = "SELECT * FROM "+tbl_name + " WHERE ID = " + id + ";";

        return db.rawQuery(query, null);
    }

    public long insertUser(String title, String details , String fromdate , String fromtime, String todate , String totime , int priority) {

        openConnection();
        ContentValues values = new ContentValues();
        values.put(fld_title, title);
        values.put(fld_details, details);
        values.put(fld_fromdate , fromdate);
        values.put(fld_fromtime , fromtime);
        values.put(fld_todate , todate);
        values.put(fld_totime, totime);
        values.put(fld_priority , priority);

        long success = db.insert(tbl_name, null, values);
        return success;
    }

    public long updateUser(int id, String title, String details , String fromdate , String fromtime , String todate, String totime , int priority) {
        openConnection();
        ContentValues values = new ContentValues();
        values.put(fld_id, id);
        values.put(fld_title, title);
        values.put(fld_details , details);
        values.put(fld_fromdate , fromdate);
        values.put(fld_fromtime , fromtime);
        values.put(fld_todate, todate);
        values.put(fld_totime , totime);
        values.put(fld_priority, priority);

        long success = db.update(tbl_name, values, fld_id + "=?", new String[]{id + ""});

        return success;
    }

    public boolean delete(int id) {

        String query = "DELETE FROM " + tbl_name + " WHERE " + fld_id + " = " + id;

        try {
            openConnection();
            db.execSQL(query);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
    public Cursor count(){
        db = this.getReadableDatabase();
        String count = "SELECT Count(*) FROM " + tbl_name + "ORDER BY " + fld_priority + " DESC;";
        return  db.rawQuery(count , null);
    }
    public ArrayList<TodoList> getData() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<TodoList> td_list= new ArrayList<TodoList>();
        Cursor result = db.rawQuery("select * from "+ tbl_name , null);
        while(result.moveToNext()){
           /* td_list.add( new TodoList(result.getString(result.getInt(result.getColumnIndex(fld_id)),
                    result.getString(result.getColumnIndex(fld_title)),
                    result.getString(result.getColumnIndex(fld_details)),
                    result.getString(result.getColumnIndex(fld_fromdate)),
                    result.getString(result.getColumnIndex(fld_todate)),
                    result.getString(result.getColumnIndex(fld_fromtime)),
                    result.getString(result.getColumnIndex(fld_totime)),
                    result.getInt(result.getColumnIndex(fld_priority))));*/
        }
        return td_list;
    }
}

