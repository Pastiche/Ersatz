
package com.example.android.ersatz.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.ersatz.model.db.ProfileContract.ProfileEntry;

public class ProfileDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = ProfileDbHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "ersatz.db";

    private static final int DATABASE_VERSION = 1;


    public ProfileDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_PROFILES_TABLE = "CREATE TABLE " + ProfileEntry.TABLE_NAME + " ("
                + ProfileEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ProfileEntry.COLUMN_PAGE_URL + " TEXT NOT NULL, "
                + ProfileEntry.COLUMN_PAGE_ID + " TEXT NOT NULL);";

        db.execSQL(SQL_CREATE_PROFILES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}