package com.example.android.ersatz.model.db;


import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.example.android.ersatz.model.db.ProfileContract.ProfileEntry;

public class ProfileProvider extends ContentProvider {

    public static final String LOG_TAG = ProfileProvider.class.getSimpleName();

    private ProfileDbHelper mDbHelper;

    private static final int PROFILES = 100;
    private static final int PROFILE_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(ProfileContract.CONTENT_AUTHORITY, ProfileContract.PATH_PROFILES, PROFILES);
        sUriMatcher.addURI(ProfileContract.CONTENT_AUTHORITY, ProfileContract.PATH_PROFILES + "/#", PROFILE_ID);
    }


    @Override
    public boolean onCreate() {
        mDbHelper = new ProfileDbHelper(this.getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {

        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        Cursor cursor;

        int match = sUriMatcher.match(uri);
        switch (match) {
            case PROFILES:
                cursor = database.query(ProfileEntry.TABLE_NAME, projection,
                        selection, selectionArgs, null, null, sortOrder);
                break;
            case PROFILE_ID:
                selection = buildSelection();
                selectionArgs = getSelectionArgsFromUri(uri);
                cursor = database.query(ProfileEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PROFILES:
                return insertProfile(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertProfile(Uri requestUri, ContentValues values) {

        validateValues(values);

        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        long id = database.insert(ProfileEntry.TABLE_NAME, null, values);

        Uri insertedRowUri = buildUriOfInsertedRow(requestUri, id);
        if (insertedRowUri != null)
            notifyChanges(requestUri);

        return insertedRowUri;
    }

    private Uri buildUriOfInsertedRow(Uri uri, long id) {
        Uri insertedRowUri = null;

        if (id == -1)
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
        else
            insertedRowUri = ContentUris.withAppendedId(uri, id);
        return insertedRowUri;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection,
                      String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PROFILES:
                return updateProfile(uri, contentValues, selection, selectionArgs);
            case PROFILE_ID:
                selection = buildSelection();
                selectionArgs = getSelectionArgsFromUri(uri);
                return updateProfile(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    private int updateProfile(Uri requestUri, ContentValues values, String selection, String[] selectionArgs) {

        if (values.containsKey(ProfileEntry.COLUMN_PAGE_URL)) {
            if (!validatePageUrl(values))
                throw new IllegalArgumentException("Profile requires url");
        }

        if (values.containsKey(ProfileEntry.COLUMN_PAGE_ID)) {
            if (!validatePageId(values))
                throw new IllegalArgumentException("Profile requires page id");
        }

        if (values.size() == 0)
            return 0;

        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        int rowsUpdated = database.update(ProfileEntry.TABLE_NAME, values, selection, selectionArgs);
        if (rowsUpdated != 0)
            notifyChanges(requestUri);

        return rowsUpdated;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PROFILES:
                return deleteProfile(uri, selection, selectionArgs);
            case PROFILE_ID:
                selection = buildSelection();
                selectionArgs = getSelectionArgsFromUri(uri);
                return deleteProfile(uri, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
    }

    private int deleteProfile(Uri requestUri, String selection, String[] selectionArgs) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        int rowsDeleted = database.delete(ProfileEntry.TABLE_NAME, selection, selectionArgs);
        if (rowsDeleted != 0)
            notifyChanges(requestUri);
        return rowsDeleted;
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PROFILES:
                return ProfileEntry.CONTENT_LIST_TYPE;
            case PROFILE_ID:
                return ProfileEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }

    private void validateValues(ContentValues values) {
        if (!validatePageUrl(values))
            throw new IllegalArgumentException("Profile requires url");
        if (!validatePageId(values))
            throw new IllegalArgumentException("Profile requires page id");
    }

    private boolean validatePageUrl(ContentValues values) {
        String pageUrl = values.getAsString(ProfileEntry.COLUMN_PAGE_URL);
        return !(pageUrl == null);
    }

    private boolean validatePageId(ContentValues values) {
        String pageId = values.getAsString(ProfileEntry.COLUMN_PAGE_ID);
        return !(pageId == null);
    }

    private String buildSelection() {
        return ProfileEntry._ID + "=?";
    }

    private String[] getSelectionArgsFromUri(Uri uri) {
        return new String[]{String.valueOf(ContentUris.parseId(uri))};
    }

    private void notifyChanges(Uri requestUri) {
        getContext().getContentResolver().notifyChange(requestUri, null);
    }
}
