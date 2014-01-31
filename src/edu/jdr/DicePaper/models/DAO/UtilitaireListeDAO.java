package edu.jdr.DicePaper.models.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import edu.jdr.DicePaper.models.DAOBase;
import edu.jdr.DicePaper.models.table.UtilitaireListe;

import java.util.ArrayList;

/**
 * Created by mario on 30/01/14.
 */
public class UtilitaireListeDAO extends DAOBase {
    public UtilitaireListeDAO(Context pContext){
        super(pContext);
    }
    public static final String TABLE_NAME = "utilitaire_liste";
    public static final String KEY = "utilitaire_liste_id";
    public static final String NOM = "nom";
    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " ( " + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            " "+NOM+" TEXT NOT NULL, "+UniversDAO.KEY+
            " TEXT REFERENCES "+UniversDAO.TABLE_NAME+"("+UniversDAO.KEY+") ON DELETE CASCADE);";
    public static final String TABLE_DROP = "DROP TABLE IF EXIST " + TABLE_NAME + ";";

    public long createUtilitaireListe(UtilitaireListe utilitaireListe){
        ContentValues value = new ContentValues();
        value.put(UtilitaireListeDAO.NOM, utilitaireListe.getNom());
        value.put(UniversDAO.KEY, utilitaireListe.getNomUnivers());
        return mDb.insert(UtilitaireListeDAO.TABLE_NAME, null, value);
    }

    public long updateUtilitaireListe(UtilitaireListe utilitaireListe){
        ContentValues value = new ContentValues();
        value.put(UtilitaireListeDAO.NOM, utilitaireListe.getNom());
        value.put(UniversDAO.KEY, utilitaireListe.getNomUnivers());
        return mDb.update(UtilitaireListeDAO.TABLE_NAME, value, KEY+" = ?", new String[]{Integer.toString(utilitaireListe.getUtilitaireListeId())});
    }

    public UtilitaireListe getUtilitaireListe(int id){
        Cursor c = mDb.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY+" = ?", new String[]{String.valueOf(id)});
        if(c.moveToFirst()){
            UtilitaireListe result = new UtilitaireListe(c.getInt(0), c.getString(1), c.getString(2));
            return result;
        } else {
            return null;
        }
    }

    public ArrayList<UtilitaireListe> getAllUtilitaireListe(String univ){
        Cursor c = mDb.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+UniversDAO.KEY+" = ?", new String[]{univ});
        ArrayList<UtilitaireListe> results = new ArrayList<UtilitaireListe>();
        while (c.moveToNext()){
            results.add(new UtilitaireListe(c.getInt(0), c.getString(1), c.getString(2)));
        }
        return results;
    }
}
