package com.example.fptentrytest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String VEHICLE_TABLE = "VEHICLE_TABLE";
    public static final String ID_COLUMN = "ID_VEHICLE";
    public static final String COLUMN_NAME_VEHICLE = "NAME_VEHICLE";
    public static final String COLUMN_TYPE_VEHICLE = "TYPE_VEHICLE";
    public static final String COLUMN_PRICE_VEHICLE = "PRICE_VEHICLE";

    public static final List<Integer> idList = new ArrayList<>();

    public DataBaseHelper(@Nullable Context context) {
        super(context, "vehicle.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + VEHICLE_TABLE + " (" + ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME_VEHICLE + " TEXT, " + COLUMN_TYPE_VEHICLE + " TEXT, " + COLUMN_PRICE_VEHICLE + " INTEGER)";

        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addOne(Vehicle vehicle){

        if (idList.contains(vehicle.getId())){
            return false;
        }

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_NAME_VEHICLE,vehicle.getName());
        contentValues.put(COLUMN_TYPE_VEHICLE,vehicle.getType());
        contentValues.put(COLUMN_PRICE_VEHICLE,vehicle.getPrice());

        long insert = database.insert(VEHICLE_TABLE, null, contentValues);
        if (insert == -1){
            return false;
        }else {
            idList.add(vehicle.getId());
            return true;
        }
    }
    public boolean deleteOne(Vehicle vehicle){

        if (!idList.contains(vehicle.getId())){
            return false;
        }
        SQLiteDatabase database = this.getWritableDatabase();
        String queryString = "DELETE FROM " + VEHICLE_TABLE + " WHERE " + ID_COLUMN + " = " + vehicle.getId();

        Cursor cursor = database.rawQuery(queryString, null);

        if (cursor.moveToFirst()){
            return true;
        }else {
            return false;
        }
    }
    public boolean updateOne(Vehicle vehicle, String id){
        if (!idList.contains(vehicle.getId())){
            return false;
        }else {
            SQLiteDatabase database = this.getWritableDatabase();

            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_NAME_VEHICLE, vehicle.getName());
            contentValues.put(COLUMN_TYPE_VEHICLE, vehicle.getType());
            contentValues.put(COLUMN_PRICE_VEHICLE, vehicle.getPrice());

            database.update(VEHICLE_TABLE, contentValues, "ID_VEHICLE = ?", new String[]{id});
            return true;
        }
    }
    public List<Vehicle> getEveryOne(){
        List<Vehicle> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " + VEHICLE_TABLE;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(queryString, null);
        if (cursor.moveToFirst()){
            do {
                int vehicleID = cursor.getInt(0);
                String vehicleName = cursor.getString(1);
                String vehicleType = cursor.getString(2);
                int vehiclePrice = cursor.getInt(3);

                Vehicle vehicle = new Vehicle(vehicleID,vehicleName,vehicleType,vehiclePrice);
                returnList.add(vehicle);

            }while (cursor.moveToNext());
        }
        cursor.close();
        database.close();

        return returnList;
    }
    public List<Vehicle> getEveryOneByName(){
        List<Vehicle> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " + VEHICLE_TABLE + " ORDER BY " + COLUMN_NAME_VEHICLE + " DESC";
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(queryString, null);
        if (cursor.moveToFirst()){
            do {
                int vehicleID = cursor.getInt(0);
                String vehicleName = cursor.getString(1);
                String vehicleType = cursor.getString(2);
                int vehiclePrice = cursor.getInt(3);

                Vehicle vehicle = new Vehicle(vehicleID,vehicleName,vehicleType,vehiclePrice);
                returnList.add(vehicle);

            }while (cursor.moveToNext());
        }
        cursor.close();
        database.close();

        return returnList;
    }
    public List<Vehicle> getEveryOneByPrice(){
        List<Vehicle> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " + VEHICLE_TABLE + " ORDER BY " + COLUMN_PRICE_VEHICLE + " DESC";
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(queryString, null);
        if (cursor.moveToFirst()){
            do {
                int vehicleID = cursor.getInt(0);
                String vehicleName = cursor.getString(1);
                String vehicleType = cursor.getString(2);
                int vehiclePrice = cursor.getInt(3);

                Vehicle vehicle = new Vehicle(vehicleID,vehicleName,vehicleType,vehiclePrice);
                returnList.add(vehicle);

            }while (cursor.moveToNext());
        }
        cursor.close();
        database.close();

        return returnList;
    }
    public List<Vehicle> searchEveryoneByName(String name){
        List<Vehicle> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " + VEHICLE_TABLE + " WHERE " + COLUMN_NAME_VEHICLE + " like '%" + name + "%'";
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(queryString, null);
        if (cursor.moveToFirst()){
            do {
                int vehicleID = cursor.getInt(0);
                String vehicleName = cursor.getString(1);
                String vehicleType = cursor.getString(2);
                int vehiclePrice = cursor.getInt(3);

                Vehicle vehicle = new Vehicle(vehicleID,vehicleName,vehicleType,vehiclePrice);
                returnList.add(vehicle);

            }while (cursor.moveToNext());
        }
        cursor.close();
        database.close();

        return returnList;
    }
    public List<Vehicle> searchEveryOneIsBiggerThanX(int x){
        List<Vehicle> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " + VEHICLE_TABLE + " WHERE " + COLUMN_PRICE_VEHICLE + " >= " + x;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(queryString, null);
        if (cursor.moveToFirst()){
            do {
                int vehicleID = cursor.getInt(0);
                String vehicleName = cursor.getString(1);
                String vehicleType = cursor.getString(2);
                int vehiclePrice = cursor.getInt(3);

                Vehicle vehicle = new Vehicle(vehicleID,vehicleName,vehicleType,vehiclePrice);
                returnList.add(vehicle);

            }while (cursor.moveToNext());
        }
        cursor.close();
        database.close();

        return returnList;
    }
}
