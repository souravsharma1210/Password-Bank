package souravsharma.com.passwordbank.data;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import static souravsharma.com.passwordbank.data.PasswordContract.PasswordEntry.TABLE_NAME;

/**
 * Created by sourav sharma on 01-07-2017.
 */

public class PasswordProvider extends ContentProvider {

    public static final int CODE_PASSWORD = 100;
    public static final int CODE_PASSWORD_WITH_ID=101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private static final String DEBUG_TAG = PasswordProvider.class.getSimpleName();
    private PasswordDbHelper mOpenHelper;

    public static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        // content://com.androidapp.passwordbank/password

        final String authority = PasswordContract.CONTENT_AUTHORITY;
        matcher.addURI(authority, PasswordContract.PATH_PASSWORD, CODE_PASSWORD);
        matcher.addURI(authority,PasswordContract.PATH_PASSWORD + "/*", CODE_PASSWORD_WITH_ID);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new PasswordDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;

        switch (sUriMatcher.match(uri)) {

            case CODE_PASSWORD:
                cursor = mOpenHelper.getReadableDatabase().query(
                        TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;


            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        Log.d(DEBUG_TAG,"inside insert");
        Uri returnUri=null;
        switch (sUriMatcher.match(uri)) {
            case CODE_PASSWORD:
                Log.d(DEBUG_TAG,"inside insert2");
                db.beginTransaction();
                long _id;
                try {
                    _id = db.insert(TABLE_NAME, null, contentValues);
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                if (_id > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                    returnUri = ContentUris.withAppendedId(PasswordContract.PasswordEntry.CONTENT_URI, _id);

                }
                else {
                    //throw new android.database.SQLException("Failed to insert row into " + uri);

                }

        }
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        int id;

        switch (sUriMatcher.match(uri)) {

            case CODE_PASSWORD_WITH_ID:
                String id1 = uri.getPathSegments().get(1);
                id = mOpenHelper.getWritableDatabase().delete(
                       TABLE_NAME,
                        PasswordContract.PasswordEntry.COLUMN_PASSWORD_NAME+"=?",new String[]{id1});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (id != 0) {
            // A task was deleted, set notification
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of tasks deleted
        return id;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        int tasksUpdated;

        // match code
        int match = sUriMatcher.match(uri);

        switch (match) {
            case CODE_PASSWORD_WITH_ID:
                //update a single task by getting the id
                String id = uri.getPathSegments().get(1);
                //using selections
                tasksUpdated = mOpenHelper.getWritableDatabase().update(TABLE_NAME, contentValues, PasswordContract.PasswordEntry.COLUMN_PASSWORD_NAME+"=?", new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (tasksUpdated != 0) {
            //set notifications if a task was updated
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // return number of tasks updated
        return tasksUpdated;
    }
    @Override
    @TargetApi(11)
    public void shutdown() {
        mOpenHelper.close();
        super.shutdown();
    }

}
