package com.example.divyank.dad;

/**
 * Created by Divyank on 23-06-2016.
 */

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Divyank on 12-06-2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private int j=0;
    // Database Name
    private static final String DATABASE_NAME = "locationManager";

    // locationduration table name
    private static final String TABLE_LOCATION = "locationduration";

    // locationduration Table Columns names
    private static final String ID = "id";
    private static final String KEY_LONGITUDE = "longitude";
    private static final String KEY_LATITUDE = "latitude";

    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_LOCATION = "location";

    private static final String KEY_IMAGE_PATH = "eventimages";



    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {

        String CREATE_LOCATION_TABLE = " CREATE TABLE " + TABLE_LOCATION + "("
                + ID +" INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_LONGITUDE + " REAL," + KEY_LATITUDE + " REAL" + " );";

        db.execSQL(CREATE_LOCATION_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String event_date=getDateTime();
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATION);
        db.execSQL("DROP TABLE IF EXISTS " + event_date);
        // Create tables again
        onCreate(db);
    }

    void addEvent(EventAdder eventadder)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String event_date=getDateTime();

        event_date=event_date.replace("-","");

        event_date="_"+event_date;
        Log.d("event inserted",event_date);

        String CREATE_EVENT_TABLE = " CREATE TABLE IF NOT EXISTS "+event_date+" ("+ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+KEY_TITLE+" TEXT, "+KEY_DESCRIPTION+" TEXT,"+KEY_LOCATION+" TEXT ); ";

        db.execSQL(CREATE_EVENT_TABLE);

        values.put(KEY_TITLE, eventadder.get_title());
        values.put(KEY_DESCRIPTION, eventadder.get_description());
        values.put(KEY_LOCATION, eventadder.get_location());
        // Inserting Row
        db.insert(event_date, null, values);
        Log.d("1", "event inserted");
        db.close(); // Closing database connection

    }

    void addEventImages(ArrayList<String> imgspaths)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        int i=0;
        int imgsize=0;
        if(imgspaths!=null)
            imgsize = imgspaths.size();

        String event_date=getDateTime();
        String event_date_1=event_date.replace("-", "");
        event_date="_"+event_date_1+"images";
        Log.d("imagesize", String.valueOf(imgsize));



        try {
            Cursor cur = db.rawQuery("SELECT * FROM "+event_date,null);
            cur.moveToLast();
            j = cur.getInt(0);
            j++;

            if(imgspaths==null)
            {
                values.put(ID, j);
                db.insert(event_date, null, values);
            }

            while(imgsize!=0)
            {
                Log.d("i",String.valueOf(i));
                Log.d("j",String.valueOf(j));
                values.put(ID, j);
                Log.d("img", imgspaths.get(i));
                values.put(KEY_IMAGE_PATH, imgspaths.get(i));
                i++;
                imgsize--;
                db.insert(event_date, null, values);
            }


            db.close();

        }catch (SQLiteException se)
        {
            String CREATE_EVENT_TABLE = " CREATE TABLE IF NOT EXISTS "+event_date+" ("+ID +" INTEGER ,"+KEY_IMAGE_PATH+" TEXT ); ";
            db.execSQL(CREATE_EVENT_TABLE);
            j=0;

            if(imgspaths==null)
            {
                values.put(ID, j);
                db.insert(event_date, null, values);
            }

            Log.d("imagesize",String.valueOf(j));
            while(imgsize!=0)
            {
                Log.d("i",String.valueOf(i));
                Log.d("j",String.valueOf(j));
                values.put(ID, j);
                Log.d("img", imgspaths.get(i));
                values.put(KEY_IMAGE_PATH, imgspaths.get(i));
                i++;
                imgsize--;
                db.insert(event_date, null, values);
            }

            db.close();
        }

    }

    void addLocation(LocationDuration location_dur)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;
        ContentValues values = new ContentValues();

        Cursor cur = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_LOCATION, null);
        cur.moveToNext();
        int count = cur.getInt(0);

        Log.d("Count", Integer.toString(count));

        if(count == 0)
        {
            values.put(KEY_LONGITUDE, location_dur.get_longitude());
            values.put(KEY_LATITUDE, location_dur.get_latitude());
            // Inserting Row
            db.insert(TABLE_LOCATION, null, values);
            db.close(); // Closing database connection
        }
        else
        {


            Cursor cur_1 = db.rawQuery("SELECT * FROM "+TABLE_LOCATION+" WHERE "+ID+"="+count+" " , null);
            cur_1.moveToFirst();

            Log.d("lng:", cur_1.getString(1));
            Log.d("lat:", cur_1.getString(2));
            double rounded1 = (double) Math.round(location_dur.get_longitude() * 10000) / 10000;
            double rounded2 = (double) Math.round(location_dur.get_latitude() * 10000) / 10000;
            Log.d("lng1:", Double.toString(rounded1));
            Log.d("lat1:", Double.toString(rounded2));

            if(!(cur_1.getString(1).equals(Double.toString(rounded1)) && cur_1.getString(2).equals(Double.toString(rounded2))))
            {
                values.put(KEY_LONGITUDE, location_dur.get_longitude());
                values.put(KEY_LATITUDE, location_dur.get_latitude());
                // Inserting Row

                db.insert(TABLE_LOCATION, null, values);
                db.close(); // Closing database connection
            }
            else
                db.close();
        }


    }


    void removelocation(int position)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_LOCATION + " WHERE ID =" + position);
    }

    public ArrayList<ArrayList<String>> getAlllocations()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ArrayList<String>> array_list = new ArrayList<>();
        Cursor cur = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_LOCATION, null);
        // Select All Query
        cur.moveToFirst();
        int count = cur.getInt(0);
        if(count>0)
        {
            Cursor cursor =  db.rawQuery( "SELECT * FROM "+TABLE_LOCATION, null );
            cursor.moveToFirst();

             while(cursor.isAfterLast() == false)
            {
                ArrayList<String> locations = new ArrayList<String>();
                locations.add(cursor.getString(1));
                locations.add(cursor.getString(2));
                array_list.add(locations);
                cursor.moveToNext();
            }
            return array_list;
        }
        else
        {
            return null;
        }


    }

    public ArrayList<ArrayList<String>> getEvents(String TABLE_EVENTS) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ArrayList<String>> array_list = new ArrayList<>();

        try {
            Cursor cur = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_EVENTS, null);
            // Select All Query
            cur.moveToFirst();
            int count = cur.getInt(0);
            if (count > 0) {
                Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_EVENTS, null);
                cursor.moveToFirst();

                while (cursor.isAfterLast() == false) {
                    ArrayList<String> event_data = new ArrayList<String>();
                    event_data.add(cursor.getString(1));
                    event_data.add(cursor.getString(2));
                    event_data.add(cursor.getString(3));
                    array_list.add(event_data);
                    cursor.moveToNext();
                }
             Log.d("size of arraylist",String.valueOf(array_list.size()));
                return array_list;
            }

        }catch (SQLiteException e){
                return null;

        }
        return null;
    }

    public ArrayList<String> getEventsImages(String TABLE_EVENTS,int position) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> array_list = new ArrayList<>();

        try {
            Cursor cur = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_EVENTS+" WHERE id="+position, null);
            // Select All Query
            cur.moveToFirst();

            int count = cur.getInt(0);
            Log.d("count", String.valueOf(count));
            if (count > 0) {
                Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_EVENTS+" WHERE id="+position, null);
                cursor.moveToFirst();

                if(cursor.getString(1)==null)
                    return null;

                while (cursor.isAfterLast() == false)
                {
                    array_list.add(cursor.getString(1));
                    cursor.moveToNext();
                }

                return array_list;
            }

        }catch (SQLiteException e){
            return null;

        }
        return null;
    }

    public void droptable(String tname)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DELETE FROM " + tname);
    }

    void addReminder(String msg,String date)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String event_date="reminders";

        String CREATE_EVENT_TABLE = " CREATE TABLE IF NOT EXISTS "+event_date+" ("+ID +" INTEGER PRIMARY KEY AUTOINCREMENT, msg TEXT, date TEXT ); ";

        db.execSQL(CREATE_EVENT_TABLE);

        Log.d("msg", msg);
        Log.d("date",date);

        values.put("msg", msg);
        values.put("date", date);
        // Inserting Row
        db.insert(event_date, null, values);
        db.close(); // Closing database connection
    }

    void removereminder(int position)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM reminders WHERE ID =" + position);
    }

    public ArrayList<ArrayList<String>> getReminders()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ArrayList<String>> array_list = new ArrayList<>();

        try {
            Cursor cur = db.rawQuery("SELECT COUNT(*) FROM reminders", null);
            // Select All Query
            cur.moveToFirst();
            int count = cur.getInt(0);
            if (count > 0) {
                Cursor cursor = db.rawQuery("SELECT * FROM reminders", null);
                cursor.moveToFirst();

                while (cursor.isAfterLast() == false) {
                    ArrayList<String> event_data = new ArrayList<String>();
                    event_data.add(cursor.getString(1));
                    event_data.add(cursor.getString(2));
                    array_list.add(event_data);
                    cursor.moveToNext();
                }
                Log.d("size of arraylist",String.valueOf(array_list.size()));
                return array_list;
            }

        }catch (SQLiteException e){
            return null;

        }
        return null;
    }

    void addEvent1(EventAdder eventadder,String date)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String event_date=date;

        event_date="_"+event_date;
        Log.d("event inserted",event_date);

        String CREATE_EVENT_TABLE = " CREATE TABLE IF NOT EXISTS "+event_date+" ("+ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+KEY_TITLE+" TEXT, "+KEY_DESCRIPTION+" TEXT,"+KEY_LOCATION+" TEXT ); ";

        db.execSQL(CREATE_EVENT_TABLE);

        values.put(KEY_TITLE, eventadder.get_title());
        values.put(KEY_DESCRIPTION, eventadder.get_description());
        values.put(KEY_LOCATION, eventadder.get_location());
        // Inserting Row
        db.insert(event_date, null, values);
        Log.d("1", "event inserted");
        db.close(); // Closing database connection

    }

    void addEventImages1(ArrayList<String> imgspaths,String date)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        int i=0;
        int imgsize=0;
        if(imgspaths!=null)
            imgsize = imgspaths.size();

        String event_date=date;
        event_date="_"+event_date+"images";
        Log.d("imagesize", String.valueOf(imgsize));



        try {
            Cursor cur = db.rawQuery("SELECT * FROM "+event_date,null);
            cur.moveToLast();
            j = cur.getInt(0);
            j++;

            if(imgspaths==null)
            {
                values.put(ID, j);
                db.insert(event_date, null, values);
            }

            while(imgsize!=0)
            {
                Log.d("i",String.valueOf(i));
                Log.d("j",String.valueOf(j));
                values.put(ID, j);
                Log.d("img", imgspaths.get(i));
                values.put(KEY_IMAGE_PATH, imgspaths.get(i));
                i++;
                imgsize--;
                db.insert(event_date, null, values);
            }


            db.close();

        }catch (SQLiteException se)
        {
            String CREATE_EVENT_TABLE = " CREATE TABLE IF NOT EXISTS "+event_date+" ("+ID +" INTEGER ,"+KEY_IMAGE_PATH+" TEXT ); ";
            db.execSQL(CREATE_EVENT_TABLE);
            j=0;

            if(imgspaths==null)
            {
                values.put(ID, j);
                db.insert(event_date, null, values);
            }

            Log.d("imagesize",String.valueOf(j));
            while(imgsize!=0)
            {
                Log.d("i",String.valueOf(i));
                Log.d("j",String.valueOf(j));
                values.put(ID, j);
                Log.d("img", imgspaths.get(i));
                values.put(KEY_IMAGE_PATH, imgspaths.get(i));
                i++;
                imgsize--;
                db.insert(event_date, null, values);
            }

            db.close();
        }

    }

}

