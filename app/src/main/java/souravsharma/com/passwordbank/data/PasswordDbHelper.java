package souravsharma.com.passwordbank.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sourav sharma on 01-07-2017.
 */

public class PasswordDbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "password.db";
    private static final int DATABASE_VERSION =10;
    public PasswordDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_PASSWORD_TABLE =

        "CREATE TABLE " + PasswordContract.PasswordEntry.TABLE_NAME + " (" +
                PasswordContract.PasswordEntry.COLUMN_PASSWORD_NAME +" TEXT PRIMARY KEY NOT NULL, "+
                PasswordContract.PasswordEntry.COLUMN_USER_ID + " TEXT NOT NULL, "+
                PasswordContract.PasswordEntry.COLUMN_PASSWORD + " TEXT NOT NULL, "+
                PasswordContract.PasswordEntry.COLUMN_URL+ " TEXT NOT NULL, "+
                PasswordContract.PasswordEntry.COLUMN_NOTES+" TEXT NOT NULL)";

        sqLiteDatabase.execSQL(SQL_CREATE_PASSWORD_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PasswordContract.PasswordEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
