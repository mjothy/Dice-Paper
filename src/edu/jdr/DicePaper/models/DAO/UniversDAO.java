package edu.jdr.DicePaper.models.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import edu.jdr.DicePaper.models.DAOBase;

import java.util.ArrayList;

/**
 * This is the manager of universe
 * Since universe table is only made of a string as primary key
 * there is no Univers class to simplify que result of queries
 * Created by paulyves on 1/21/14.
 */
public class UniversDAO extends DAOBase {

    public UniversDAO(Context pContext){
        super(pContext);
    }

    public static final String TABLE_NAME = "univers";
    public static final String KEY = "nom_univers";
    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " ( " + KEY + " TEXT PRIMARY KEY);";
    public static final String TABLE_DROP = "DROP TABLE IF EXIST " + TABLE_NAME + ";";

    /**
     *
     * @param univName
     * @return the long corresponding to the row id of insertion (-1 if fail)
     */
    public long createUnivers(String univName){
        ContentValues value = new ContentValues();
        value.put(UniversDAO.KEY, univName);
        return mDb.insert(UniversDAO.TABLE_NAME, null, value);
    }

    public String readUnivers(String univName){
        Cursor c = mDb.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY+" = ?", new String[]{univName});
        if(c.moveToFirst()){
            return c.getString(0);
        } else {
            return null;
        }
    }

    /**
     *
     * @param univName
     * @return number of rows affected
     */
    public int deleteUnivers(String univName){
        return mDb.delete(TABLE_NAME, KEY + " = ?", new String[]{univName});
    }

    public void updateUnivers(String oldName, String newName){
        ContentValues value = new ContentValues();
        value.put(UniversDAO.KEY, newName);
        mDb.update(TABLE_NAME, value, KEY + " = ?", new String[]{oldName});
    }

    public ArrayList<String> getAllUnivers(){
        Cursor c = mDb.rawQuery("SELECT * FROM "+TABLE_NAME, null);
        ArrayList<String> results = new ArrayList<String>();
        while (c.moveToNext()){
            results.add(c.getString(0));
        }
        return results;
    }

}
