package edu.jdr.DicePaper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by paulyves on 1/19/14.
 *
 * Goal : grant a basis for DAO managers
 * gives a connection to the sqlite db through
 * the DatabaseHandler in the attributes
 *
 */
public class DAOBase {
    //version, change it on db upgrade
    protected final static int VERSION = 1;
    //name of the db file
    protected final static String DBNAME = "database.db";
    protected SQLiteDatabase mDb = null;
    protected DatabaseHandler mHandler = null;

    public DAOBase(Context pContext) {
        this.mHandler = new DatabaseHandler(pContext, DBNAME, null, VERSION);
    }

    public SQLiteDatabase open(){
        mDb = mHandler.getWritableDatabase();
        return mDb;
    }

    public void close(){
        mDb.close();
    }
    public SQLiteDatabase getmDb(){
        return mDb;
    }

}
