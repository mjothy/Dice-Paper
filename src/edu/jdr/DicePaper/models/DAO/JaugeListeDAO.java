package edu.jdr.DicePaper.models.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import edu.jdr.DicePaper.models.DAOBase;
import edu.jdr.DicePaper.models.table.JaugeListe;

import java.util.ArrayList;

/**
 * Created by mario on 30/01/14.
 */
public class JaugeListeDAO extends DAOBase {
    public JaugeListeDAO(Context pContext){
        super(pContext);
    }
    public static final String TABLE_NAME = "jauge_liste";
    public static final String KEY = "jauge_liste_id";
    public static final String NOM = "nom";
    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " ( " + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            " "+NOM+" TEXT NOT NULL, "+UniversDAO.KEY+
            " TEXT REFERENCES "+UniversDAO.TABLE_NAME+"("+UniversDAO.KEY+") ON DELETE CASCADE);";
    public static final String TABLE_DROP = "DROP TABLE IF EXIST " + TABLE_NAME + ";";

    public long createJaugeListe(JaugeListe jauge){
        ContentValues value = new ContentValues();
        value.put(JaugeListeDAO.NOM, jauge.getNom());
        value.put(UniversDAO.KEY, jauge.getNomUnivers());
        return mDb.insert(JaugeListeDAO.TABLE_NAME, null, value);
    }

    public long updateJaugeListe(JaugeListe jauge){
        ContentValues value = new ContentValues();
        value.put(JaugeListeDAO.NOM, jauge.getNom());
        value.put(UniversDAO.KEY, jauge.getNomUnivers());
        return mDb.update(JaugeListeDAO.TABLE_NAME, value, KEY+" = ?", new String[]{Integer.toString(jauge.getJaugeListeId())});
    }

    public JaugeListe getJaugeListe(int id){
        Cursor c = mDb.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY+" = ?", new String[]{String.valueOf(id)});
        if(c.moveToFirst()){
            JaugeListe result = new JaugeListe(c.getInt(0), c.getString(1), c.getString(2));
            return result;
        } else {
            return null;
        }
    }

    public ArrayList<JaugeListe> getAllJaugeListe(String univ){
        Cursor c = mDb.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+UniversDAO.KEY+" = ?", new String[]{univ});
        ArrayList<JaugeListe> results = new ArrayList<JaugeListe>();
        while (c.moveToNext()){
            results.add(new JaugeListe(c.getInt(0), c.getString(1), c.getString(2)));
        }
        return results;
    }
}