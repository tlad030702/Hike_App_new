package com.example.hike_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.hike_app.models.HikeModel;
import com.example.hike_app.models.ObservationsModel;

public class DatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "HikeApp.db";
    private static final int DATABASE_VERSION = 2;

    private static final String TABLE_HIKE = "HikeDetail";
    private static final String COLUMN_ID = "hike_id";
    private static final String COLUMN_NAME = "hike_name";
    private static final String COLUMN_LOCATION = "hike_location";
    private static final String COLUMN_DATE = "hike_date";
    private static final String COLUMN_PARKING = "hike_parking";
    private static final String COLUMN_LENGTH = "hike_length";
    private static final String COLUMN_LEVEL = "hike_level";
    private static final String COLUMN_DESCRIPTION = "hike_description";

    private static final String TABLE_HIKE_OBSERVATION = "HikeObservation";
    private static final String COLUMN_OBS_ID = "obs_id";
    private static final String COLUMN_OBS = "obs";
    private static final String COLUMN_OBS_DATE = "obs_date";
    private static final String COLUMN_OBS_CMT = "obs_cmt";
    private static final String COLUMN_OBS_FOREIGN = "obs_foreign";
    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryHike = "CREATE TABLE " + TABLE_HIKE +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_LOCATION + " TEXT, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_PARKING + " BOOL, " +
                COLUMN_LENGTH + " REAL, " +
                COLUMN_LEVEL + " TEXT, " +
                COLUMN_DESCRIPTION + " BLOB);";
        db.execSQL(queryHike);

        String queryObservation = "CREATE TABLE " + TABLE_HIKE_OBSERVATION +
                " (" + COLUMN_OBS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_OBS + " TEXT, " +
                COLUMN_OBS_DATE + " TEXT NOT NULL, " +
                COLUMN_OBS_CMT + " TEXT, " +
                COLUMN_OBS_FOREIGN + " TEXT, " +
                "FOREIGN KEY (" + COLUMN_OBS_FOREIGN + ") REFERENCES " + TABLE_HIKE + "(" + COLUMN_ID + "));";
        db.execSQL(queryObservation);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HIKE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HIKE_OBSERVATION);
        onCreate(db);
    }
    public void addHike(HikeModel hikeModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, hikeModel.getHikeName());
        cv.put(COLUMN_LOCATION, hikeModel.getHikeLocation());
        cv.put(COLUMN_DATE, hikeModel.getHikeDate());
        cv.put(COLUMN_PARKING, hikeModel.isHikeParking());
        cv.put(COLUMN_LENGTH, hikeModel.getHikeLength());
        cv.put(COLUMN_LEVEL, hikeModel.getHikeLevel());
        cv.put(COLUMN_DESCRIPTION, hikeModel.getDescription());
        long result = db.insert(TABLE_HIKE, null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Successfully", Toast.LENGTH_SHORT).show();
        }
    }
    public Cursor dataHike(){
        String query = "SELECT * FROM " + TABLE_HIKE;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
    public Cursor dataHikeById(int hikeId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_HIKE + " WHERE " + COLUMN_ID + " = ?";

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, new String[] { String.valueOf(hikeId) });
        }

        return cursor;
    }

    public void updateHikeData(String hikeId, String name, String location, double length, String date, int isParking, String level, String description){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_LOCATION, location);
        cv.put(COLUMN_LENGTH, length);
        cv.put(COLUMN_DATE, date);
        cv.put(COLUMN_PARKING, isParking);
        cv.put(COLUMN_LEVEL, level);
        cv.put(COLUMN_DESCRIPTION, description);

        long result = db.update(TABLE_HIKE, cv, "hike_id=?", new String[] { hikeId });
        if(result == -1){
            Toast.makeText(context, "Failed to Update", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteHikeData(String hikeId){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_HIKE,"hike_id=?", new String[] { hikeId });
        if(result == -1){
            Toast.makeText(context, "Failed to Update", Toast.LENGTH_SHORT).show();
        } else {
            System.out.println(result);
            Toast.makeText(context, "Successfully", Toast.LENGTH_SHORT).show();
        }
    }
    public void deleteAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_HIKE);
    }
    public void addObservation(String hikeId, ObservationsModel observation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_OBS, observation.getNameObs());
        values.put(COLUMN_OBS_DATE, observation.getDateObs());
        values.put(COLUMN_OBS_CMT, observation.getCmtObs());
        values.put(COLUMN_OBS_FOREIGN, hikeId);

        // Thêm quan sát vào cơ sở dữ liệu
        long result = db.insert(TABLE_HIKE_OBSERVATION, null, values);
        if(result == -1){
            Toast.makeText(context, "Failed to Add", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully", Toast.LENGTH_SHORT).show();
        }
    }
    public Cursor dataObsByIdHike(int hikeId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_HIKE_OBSERVATION + " WHERE " + COLUMN_OBS_FOREIGN + " = ?";

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, new String[] { String.valueOf(hikeId) });
        }

        return cursor;
    }
    public Cursor dataObsByIdObs(int hikeObs) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_HIKE_OBSERVATION + " WHERE " + COLUMN_OBS_ID + " = ?";

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, new String[] { String.valueOf(hikeObs) });
        }

        return cursor;
    }
    public void updateObsData(String obsID, String name, String added, String cmt){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_OBS, name);
        cv.put(COLUMN_OBS_DATE, added);
        cv.put(COLUMN_OBS_CMT, cmt);

        long result = db.update(TABLE_HIKE_OBSERVATION, cv, "obs_id=?", new String[] { obsID });
        if(result == -1){
            Toast.makeText(context, "Failed to Update", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully", Toast.LENGTH_SHORT).show();
        }
    }
    public void deleteObsData(String obsID){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_HIKE_OBSERVATION,"obs_id=?", new String[] { obsID });
        if(result == -1){
            Toast.makeText(context, "Failed to Update", Toast.LENGTH_SHORT).show();
        } else {
            System.out.println(result);
            Toast.makeText(context, "Successfully", Toast.LENGTH_SHORT).show();
        }
    }
}
