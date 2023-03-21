package com.rezza.weatherapp.database.table;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.rezza.weatherapp.database.DatabaseManager;
import com.rezza.weatherapp.database.MasterDB;

import java.util.ArrayList;

public class FavoriteDB extends MasterDB {

    public static final String TAG          = "FavoriteDB";
    public static final String TABLE_NAME   = "FAVORITE_DB";

    public static final String NAME = "name";

    public String name = "";


    public String getCreateTable() {
        String create = "create table " + TABLE_NAME + " "
                + "(" +
                " " + NAME + " varchar(100) NULL" +
                " )";
        Log.d(TAG,create);
        return create;
    }

    @Override
    public String tableName() {
        return TABLE_NAME;
    }

    @SuppressLint("Range")
    @Override
    protected FavoriteDB build(Cursor res) {
        FavoriteDB db = new FavoriteDB();
        db.name = res.getString(res.getColumnIndex(NAME));
        return db;
    }

    @SuppressLint("Range")
    @Override
    protected void buildSingle(Cursor res) {
        this.name = res.getString(res.getColumnIndex(NAME));

    }

    public ContentValues createContentValues(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, name);
        return contentValues;
    }


    @Override
    public boolean insert(Context context) {
        return super.insert(context);
    }

    public void delete(Context context, String name) {
        super.delete(context, NAME+"='"+name+"'");
    }

    public void getData(Context context, String name){
        DatabaseManager pDB = new DatabaseManager(context);
        SQLiteDatabase db = pDB.getReadableDatabase();
        String query = "select *  from "+TABLE_NAME+" WHERE "+NAME+" ='"+name+"'";
        Cursor res = db.rawQuery( query , null);
        try {
            while (res.moveToNext()){
                buildSingle(res);
            }
            pDB.close();
        }catch (Exception e){
            Log.d(TAG,e.getMessage());
        }
        finally {
            res.close();
            pDB.close();
        }
    }

    public ArrayList<FavoriteDB> getALl(Context context){
        ArrayList<FavoriteDB> list = new ArrayList<>();
        DatabaseManager pDB = new DatabaseManager(context);
        SQLiteDatabase db = pDB.getReadableDatabase();
        String query = "select *  from "+TABLE_NAME;
        Cursor res = db.rawQuery( query , null);
        try {
            while (res.moveToNext()){
                list.add(build(res));
            }
            pDB.close();
        }catch (Exception e){
            Log.d(TAG,e.getMessage());
        }
        finally {
            res.close();
            pDB.close();
        }
        return list;
    }

}
