package com.ericknathan.libri.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class SQLProvider extends SQLiteOpenHelper {

    private static final String DB_NAME = "libri";
    private static final int DB_VERSION = 1;
    private static SQLProvider INSTANCE;

    public SQLProvider(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
            "CREATE TABLE IF NOT EXISTS tbl_user (" +
                "id_user INTEGER PRIMARY KEY," +
                "name TEXT," +
                "surname TEXT," +
                "email TEXT," +
                "password TEXT," +
                "created_at DATETIME DEFAULT CURRENT_TIMESTAMP," +
                "updated_at DATETIME DEFAULT CURRENT_TIMESTAMP" +
            ")"
        );
        Log.d("SQLITE", "BANCO DE DADOS CRIADO! " + DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addUser(String name, String surname, String email, String password, String created_at) {
        SQLiteDatabase database = this.getWritableDatabase();
        
        try {
            ContentValues values = new ContentValues();

            values.put("name", name);
            values.put("surname", surname);
            values.put("email", email);
            values.put("password", password);
            values.put("created_at", created_at);

            database.insertOrThrow("tbl_user", null, values);
            database.setTransactionSuccessful();

            return true;
        } catch(Exception e) {
            Log.d("SQLITE ERROR [addUser]", e.getMessage());
            return false;
        } finally {
            if(database.isOpen()) {
                database.endTransaction();
            }
        }
    }

    public static SQLProvider getInstance(Context context) {
        if(INSTANCE == null) {
            INSTANCE = new SQLProvider(context);
        }

        return INSTANCE;
    }
}
