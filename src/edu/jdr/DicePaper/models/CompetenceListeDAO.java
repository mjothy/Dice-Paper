package edu.jdr.DicePaper.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

/**
 * Created by paulyves on 1/30/14.
 */
public class CompetenceListeDAO extends DAOBase {
    public CompetenceListeDAO(Context pContext){
        super(pContext);
    }
    public static final String TABLE_NAME = "competence_liste";
    public static final String KEY = "competence_liste_id";
    public static final String NOM = "nom";
    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " ( " + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            " "+NOM+" TEXT NOT NULL, "+UniversDAO.KEY+
            " TEXT REFERENCES "+UniversDAO.TABLE_NAME+"("+UniversDAO.KEY+") ON DELETE CASCADE);";
    public static final String TABLE_DROP = "DROP TABLE IF EXIST " + TABLE_NAME + ";";

    public long createCompetenceListe(CompetenceListe comp){
        ContentValues value = new ContentValues();
        value.put(CompetenceListeDAO.NOM, comp.getNom());
        value.put(UniversDAO.KEY, comp.getNomUnivers());
        return mDb.insert(CompetenceListeDAO.TABLE_NAME, null, value);
    }

    public CompetenceListe getCompetenceListe(int id){
        Cursor c = mDb.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY+" = ?", new String[]{String.valueOf(id)});
        if(c.moveToFirst()){
            CompetenceListe result = new CompetenceListe(c.getInt(0), c.getString(1), c.getString(2));
            return result;
        } else {
            return null;
        }
    }

    /**
     *
     * @param id
     * @return number of rows affected
     */
    public int deleteCarac(int id){
        return mDb.delete(TABLE_NAME, KEY + " = ?", new String[]{String.valueOf(id)});
    }

    public ArrayList<CompetenceListe> getAllCompetence(String univ){
        Cursor c = mDb.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+UniversDAO.KEY+" = ?", new String[]{univ});
        ArrayList<CompetenceListe> results = new ArrayList<CompetenceListe>();
        while (c.moveToNext()){
            results.add(new CompetenceListe(c.getInt(0), c.getString(1), c.getString(2)));
        }
        return results;
    }
}
