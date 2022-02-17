package com.ericknathan.libri.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
                "id_user INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "surname TEXT," +
                "email TEXT UNIQUE," +
                "username TEXT UNIQUE," +
                "password TEXT," +
                "created_at DATETIME DEFAULT CURRENT_TIMESTAMP," +
                "updated_at DATETIME DEFAULT CURRENT_TIMESTAMP" +
            ")"
        );

        sqLiteDatabase.execSQL(
            "CREATE TABLE IF NOT EXISTS tbl_book (" +
                "id_book INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_user INTEGER," +
                "title TEXT," +
                "author TEXT," +
                "description TEXT," +
                "photo TEXT," +
                "created_at DATETIME DEFAULT CURRENT_TIMESTAMP," +
                "updated_at DATETIME DEFAULT CURRENT_TIMESTAMP," +
                "FOREIGN KEY (id_user) REFERENCES tbl_user(id_user)" +
            ")"
        );

        Log.d("SQLITE", "BANCO DE DADOS CRIADO! " + DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addUser(String name, String surname, String email, String username, String password) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        
        try {
            sqLiteDatabase.beginTransaction();

            ContentValues values = new ContentValues();

            values.put("name", name);
            values.put("surname", surname);
            values.put("email", email);
            values.put("username", username);
            values.put("password", password);

            sqLiteDatabase.insertOrThrow("tbl_user", null, values);
            sqLiteDatabase.setTransactionSuccessful();

            return true;
        } catch(Exception error) {
            Log.d("SQLITE ERROR [addUser]", error.getMessage());
            return false;
        } finally {
            if(sqLiteDatabase.isOpen()) {
                sqLiteDatabase.endTransaction();
            }
        }
    }

    public boolean registerBook(int id_user, String title, String author, String description, String photo) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        try {
            sqLiteDatabase.beginTransaction();

            ContentValues values = new ContentValues();

            values.put("id_user", id_user);
            values.put("title", title);
            values.put("author", author);
            values.put("description", description);
            values.put("photo", photo);

            sqLiteDatabase.insertOrThrow("tbl_book", null, values);
            sqLiteDatabase.setTransactionSuccessful();

            return true;
        } catch(Exception error) {
            Log.d("SQLITE ERROR [addBook]", error.getMessage());
            return false;
        } finally {
            if(sqLiteDatabase.isOpen()) {
                sqLiteDatabase.endTransaction();
            }
        }
    }

    public int login(String username, String password) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(
                "SELECT * FROM tbl_user WHERE username = ? AND password = ?",
                new String[]{username, password}
        );

        int user_id = 0;
        try {
            if(cursor.moveToFirst()) {
                user_id = cursor.getInt(cursor.getColumnIndex("id_user"));
                return user_id;
            }

            return user_id;
        }
        catch(Exception exception) {
            Log.d("SQLITE ERROR - ", exception.getMessage());
        }
        finally {
            if(cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return user_id;
    }

    public static SQLProvider getInstance(Context context) {
        if(INSTANCE == null) {
            INSTANCE = new SQLProvider(context);
        }

        return INSTANCE;
    }
}
