package edu.jdr.DicePaper.models;

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
        db.execSQL(FichePersonnageDAO.TABLE_CREATE);
        db.execSQL(CaracteristiqueListeDAO.TABLE_CREATE);
        db.execSQL(JaugeListeDAO.TABLE_CREATE);
        db.execSQL(UtilitaireListeDAO.TABLE_CREATE);
        db.execSQL(CompetenceListeDAO.TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL(UniversDAO.TABLE_DROP);
        db.execSQL(FichePersonnageDAO.TABLE_DROP);
        db.execSQL(CaracteristiqueListeDAO.TABLE_DROP);
        db.execSQL(JaugeListeDAO.TABLE_DROP);
        db.execSQL(UtilitaireListeDAO.TABLE_DROP);
        db.execSQL(CompetenceListeDAO.TABLE_DROP);
        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db){
        super.onOpen(db);
        if (!db.isReadOnly()) {
            //allow foreign key constaints such as ON DELETE...
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }
}