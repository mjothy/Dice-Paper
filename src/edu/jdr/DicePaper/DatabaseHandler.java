package edu.jdr.DicePaper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by paulyves on 1/19/14.
 *
 * Used to create the database or to connect to it
 */
public class DatabaseHandler extends SQLiteOpenHelper{
    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(UniversDAO.TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL(UniversDAO.TABLE_DROP);
        onCreate(db);
    }
}