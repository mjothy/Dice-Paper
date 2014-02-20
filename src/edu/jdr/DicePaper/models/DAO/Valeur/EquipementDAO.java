package edu.jdr.DicePaper.models.DAO.Valeur;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import edu.jdr.DicePaper.models.DAO.FichePersonnageDAO;
import edu.jdr.DicePaper.models.DAO.UniversDAO;
import edu.jdr.DicePaper.models.DAOBase;
import edu.jdr.DicePaper.models.table.Liste.CaracteristiqueListe;
import edu.jdr.DicePaper.models.table.Valeur.Equipement;

import java.util.ArrayList;

/**
 * Created by paulyves on 2/20/14.
 */
public class EquipementDAO extends DAOBase {
    public EquipementDAO(Context pContext){
        super(pContext);
    }

    public static final String TABLE_NAME = "equipement";
    public static final String KEY = "equipement_id";
    public static final String NOM = "nom";
    public static final String DESCRIPTION = "description";
    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " ( " + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            " "+NOM+" TEXT NOT NULL, "+DESCRIPTION+" TEXT, "+ FichePersonnageDAO.KEY+
            " TEXT REFERENCES "+FichePersonnageDAO.TABLE_NAME+"("+FichePersonnageDAO.KEY+") ON DELETE CASCADE);";
    public static final String TABLE_DROP = "DROP TABLE IF EXIST " + TABLE_NAME + ";";

    public long createEquipement(Equipement stuff){
        ContentValues value = new ContentValues();
        value.put(NOM, stuff.getNom());
        value.put(DESCRIPTION, stuff.getDescription());
        value.put(FichePersonnageDAO.KEY, stuff.getFiche());
        return mDb.insert(TABLE_NAME, null, value);
    }

    public long updateEquipement(Equipement stuff){
        ContentValues value = new ContentValues();
        value.put(NOM, stuff.getNom());
        value.put(DESCRIPTION, stuff.getDescription());
        return mDb.update(TABLE_NAME, value, KEY+" = ?", new String[]{Integer.toString(stuff.getKey())});
    }

    public Equipement getEquipement(int id){
        Cursor c = mDb.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY+" = ?", new String[]{String.valueOf(id)});
        if(c.moveToFirst()){
            Equipement result = new Equipement(c.getInt(0), c.getString(1), c.getString(2), c.getString(3));
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

    public ArrayList<Equipement> getAllEquipement(String charName){
        Cursor c = mDb.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+UniversDAO.KEY+" = ?"+" GROUP BY "+NOM, new String[]{charName});
        ArrayList<Equipement> results = new ArrayList<Equipement>();
        while (c.moveToNext()){
            results.add(new Equipement(c.getInt(0), c.getString(1), c.getString(2), c.getString(3)));
        }
        return results;
    }
}
