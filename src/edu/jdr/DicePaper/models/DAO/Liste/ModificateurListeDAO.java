package edu.jdr.DicePaper.models.DAO.Liste;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import edu.jdr.DicePaper.models.DAOBase;
import edu.jdr.DicePaper.models.table.Liste.ModificateurListe;

import java.util.ArrayList;

/**
 * Created by paulyves on 2/8/14.
 */
public class ModificateurListeDAO extends DAOBase {
    public ModificateurListeDAO(Context pContext){
        super(pContext);
    }
    public static final String TABLE_NAME = "modificateur_liste";
    public static final String KEY = "modificateur_liste_id";
    public static final String NOM = "nom";
    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " ( " + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            " "+NOM+" TEXT NOT NULL, "+CaracteristiqueListeDAO.KEY+
            " INTEGER REFERENCES "+CaracteristiqueListeDAO.TABLE_NAME+"("+CaracteristiqueListeDAO.KEY+") ON DELETE CASCADE);";
    public static final String TABLE_DROP = "DROP TABLE IF EXIST " + TABLE_NAME + ";";

    public long createModListe(ModificateurListe mod){
        ContentValues value = new ContentValues();
        value.put(ModificateurListeDAO.NOM, mod.getNomMod());
        value.put(CaracteristiqueListeDAO.KEY, mod.getCaracteristiqueListeId());
        return mDb.insert(ModificateurListeDAO.TABLE_NAME, null, value);
    }

    public ModificateurListe getModListe(int id){
        Cursor c = mDb.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY+" = ?", new String[]{String.valueOf(id)});
        if(c.moveToFirst()){
            ModificateurListe result = new ModificateurListe(c.getInt(0), c.getString(1), c.getInt(2));
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
    public int delete(int id){
        return mDb.delete(TABLE_NAME, KEY + " = ?", new String[]{String.valueOf(id)});
    }

    /**
     * Return all the ModificateurListe related to a given CaracteristiqueListe
     * @param caracId the id of the CaracteristiqueListe
     * @return ArrayList of ModificateurListe
     */
    public ArrayList<ModificateurListe> getAllModList(int caracId){
        Cursor c = mDb.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+CaracteristiqueListeDAO.KEY+" = ?",
                                new String[]{String.valueOf(caracId)});

        ArrayList<ModificateurListe> results = new ArrayList<ModificateurListe>();
        while (c.moveToNext()){
            results.add(new ModificateurListe(c.getInt(0), c.getString(1), c.getInt(2)));
        }
        return results;
    }
}
