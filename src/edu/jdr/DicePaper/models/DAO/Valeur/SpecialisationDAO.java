package edu.jdr.DicePaper.models.DAO.Valeur;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import edu.jdr.DicePaper.models.DAO.FichePersonnageDAO;
import edu.jdr.DicePaper.models.DAO.Liste.CaracteristiqueListeDAO;
import edu.jdr.DicePaper.models.DAO.Liste.JaugeListeDAO;
import edu.jdr.DicePaper.models.DAOBase;
import edu.jdr.DicePaper.models.table.Liste.ModificateurListe;
import edu.jdr.DicePaper.models.table.Valeur.JaugeValeur;
import edu.jdr.DicePaper.models.table.Valeur.Specialisation;

import java.util.ArrayList;

/**
 * Created by paulyves on 2/20/14.
 */
public class SpecialisationDAO extends DAOBase {
    public SpecialisationDAO(Context pContext){
        super(pContext);
    }

    public static final String TABLE_NAME = "specialisation";
    public static final String KEY = "specialisation_id";
    public static final String NOM = "nom";
    public static final String VALUE = "valeur";
    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " ( " + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            " "+NOM+" TEXT NOT NULL, "+VALUE+" REAL, "+ CompetenceValeurDAO.KEY+
            " INTEGER REFERENCES "+CompetenceValeurDAO.TABLE_NAME+"("+CompetenceValeurDAO.KEY+") ON DELETE CASCADE);";
    public static final String TABLE_DROP = "DROP TABLE IF EXIST " + TABLE_NAME + ";";

    public long createSpecialisation(Specialisation spe){
        ContentValues value = new ContentValues();
        value.put(NOM, spe.getNom());
        value.put(VALUE, spe.getValue());
        value.put(CompetenceValeurDAO.KEY, spe.getCompetenceId());
        return mDb.insert(TABLE_NAME, null, value);
    }

    public long updateSpecialisation(Specialisation spe){
        ContentValues value = new ContentValues();
        value.put(NOM, spe.getNom());
        value.put(VALUE, spe.getValue());
        return mDb.update(TABLE_NAME, value, KEY+" = ?", new String[]{Integer.toString(spe.getKey())});
    }

    public Specialisation getSpecialisation(int id){
        Cursor c = mDb.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY+" = ?", new String[]{String.valueOf(id)});
        if(c.moveToFirst()){
            Specialisation result = new Specialisation(c.getInt(0), c.getString(1), c.getFloat(2), c.getInt(3));
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
     * Return all the Specialisation related to a given CompetenceValeur
     * @param compId the id of the CaracteristiqueListe
     * @return ArrayList of ModificateurListe
     */
    public ArrayList<Specialisation> getAllModList(int compId){
        Cursor c = mDb.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+CompetenceValeurDAO.KEY+" = ?",
                new String[]{String.valueOf(compId)});

        ArrayList<Specialisation> results = new ArrayList<Specialisation>();
        while (c.moveToNext()){
            results.add(new Specialisation(c.getInt(0), c.getString(1), c.getFloat(2), c.getInt(3)));
        }
        return results;
    }
}
