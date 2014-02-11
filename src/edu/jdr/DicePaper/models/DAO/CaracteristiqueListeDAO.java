package edu.jdr.DicePaper.models.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import edu.jdr.DicePaper.models.table.CaracteristiqueListe;
import edu.jdr.DicePaper.models.DAOBase;

import java.util.ArrayList;

/**
 * Created by paulyves on 1/28/14.
 */
public class CaracteristiqueListeDAO extends DAOBase {
    public CaracteristiqueListeDAO(Context pContext){
        super(pContext);
    }
    public static final String TABLE_NAME = "caracteristique_liste";
    public static final String KEY = "caracteristique_liste_id";
    public static final String NOM = "nom";
    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " ( " + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            " "+NOM+" TEXT NOT NULL, "+UniversDAO.KEY+
            " TEXT REFERENCES "+UniversDAO.TABLE_NAME+"("+UniversDAO.KEY+") ON DELETE CASCADE);";
    public static final String TABLE_DROP = "DROP TABLE IF EXIST " + TABLE_NAME + ";";

    public long createCaracListe(CaracteristiqueListe carac){
        ContentValues value = new ContentValues();
        value.put(CaracteristiqueListeDAO.NOM, carac.getNom());
        value.put(UniversDAO.KEY, carac.getNomUnivers());
        return mDb.insert(CaracteristiqueListeDAO.TABLE_NAME, null, value);
    }

    public CaracteristiqueListe getCaracListe(int id){
        Cursor c = mDb.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY+" = ?", new String[]{String.valueOf(id)});
        if(c.moveToFirst()){
            CaracteristiqueListe result = new CaracteristiqueListe(c.getInt(0), c.getString(1), c.getString(2));
            return result;
        } else {
            return null;
        }
    }

    /**
     *
     * @param caracId
     * @return number of rows affected
     */
    public int deleteCarac(int caracId){
        return mDb.delete(TABLE_NAME, KEY + " = ?", new String[]{String.valueOf(caracId)});
    }

    public ArrayList<CaracteristiqueListe> getAllCaracList(String univ){
        Cursor c = mDb.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+UniversDAO.KEY+" = ?"+" GROUP BY "+NOM, new String[]{univ});
        ArrayList<CaracteristiqueListe> results = new ArrayList<CaracteristiqueListe>();
        while (c.moveToNext()){
            results.add(new CaracteristiqueListe(c.getInt(0), c.getString(1), c.getString(2)));
        }
        return results;
    }

}
