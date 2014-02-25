package edu.jdr.DicePaper.models.DAO.Valeur;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import edu.jdr.DicePaper.models.DAO.FichePersonnageDAO;
import edu.jdr.DicePaper.models.DAO.Liste.CaracteristiqueListeDAO;
import edu.jdr.DicePaper.models.DAO.UniversDAO;
import edu.jdr.DicePaper.models.DAOBase;
import edu.jdr.DicePaper.models.table.FichePersonnage;
import edu.jdr.DicePaper.models.table.Liste.CaracteristiqueListe;
import edu.jdr.DicePaper.models.table.Valeur.CaracteristiqueValeur;
import edu.jdr.DicePaper.models.table.Valeur.CompetenceValeur;

import java.util.ArrayList;

/**
 * Created by paulyves on 2/19/14.
 */
public class CaracteristiqueValeurDAO extends DAOBase{
    public CaracteristiqueValeurDAO(Context pContext){
        super(pContext);
    }

    public static final String TABLE_NAME = "caracteristique_valeur";
    public static final String KEY = "caracteristique_valeur_id";
    public static final String BASEVALUE = "valeur_base";
    public static final String MODIFIEDVALUE = "valeur_actuelle";
    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " ( " + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            " "+BASEVALUE+" REAL, "+MODIFIEDVALUE+" REAL, "+ CaracteristiqueListeDAO.KEY+
            " INTEGER REFERENCES "+CaracteristiqueListeDAO.TABLE_NAME+"("+CaracteristiqueListeDAO.KEY+") ON DELETE CASCADE, "+
            ""+ FichePersonnageDAO.KEY+" TEXT NOT NULL REFERENCES "+FichePersonnageDAO.TABLE_NAME+"("+FichePersonnageDAO.KEY+") ON DELETE CASCADE);";
    public static final String TABLE_DROP = "DROP TABLE IF EXIST " + TABLE_NAME + ";";

    public long createCaracteristiqueValeur(CaracteristiqueValeur carac){
        ContentValues value = new ContentValues();
        value.put(BASEVALUE, carac.getBaseValue());
        value.put(MODIFIEDVALUE, carac.getModifiedValue());
        value.put(FichePersonnageDAO.KEY, carac.getFiche());
        value.put(CaracteristiqueListeDAO.KEY, carac.getRelatedList().getListeId());
        return mDb.insert(TABLE_NAME, null, value);
    }

    public long updateCaracteristiqueValeur(CaracteristiqueValeur carac){
        ContentValues value = new ContentValues();
        value.put(BASEVALUE, carac.getBaseValue());
        value.put(MODIFIEDVALUE, carac.getModifiedValue());
        return mDb.update(TABLE_NAME, value, KEY+" = ?", new String[]{Integer.toString(carac.getKey())});
    }

    /**
     * Delete a CaracteristiqueValeur
     * @param id
     * @return the number of rows affected
     */
    public int delete(int id){
        return mDb.delete(TABLE_NAME, KEY + " = ?", new String[]{String.valueOf(id)});
    }

    public CaracteristiqueValeur getCaracteristiqueValeur(int id){
        Cursor c = mDb.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY+" = ?", new String[]{String.valueOf(id)});
        if(c.moveToFirst()){
            CaracteristiqueValeur result = new CaracteristiqueValeur(c.getInt(0), c.getFloat(1), c.getFloat(2), c.getString(4));
            return result;
        } else {
            return null;
        }
    }

    /**
     * Get all the CaracteristiqueValeur related to a character sheet, fed with the
     * corresponding CaracteristiqueListe
     * @param charName the name of the character we want the utils
     * @return
     */
    public ArrayList<CaracteristiqueValeur> getAllCaracteristiqueValeur(String charName){
        String outerKey = CaracteristiqueListeDAO.KEY;
        String joinTable = CaracteristiqueListeDAO.TABLE_NAME;
        Cursor c = mDb.rawQuery("SELECT "+KEY+", "+BASEVALUE+", "+MODIFIEDVALUE+", "+FichePersonnageDAO.KEY+
                ", "+joinTable+"."+outerKey+", "+CaracteristiqueListeDAO.NOM+", "+ UniversDAO.KEY+
                " FROM "+TABLE_NAME+" JOIN "+joinTable+" ON "+TABLE_NAME+"."+outerKey+" = "+joinTable+"."+outerKey+
                " WHERE "+FichePersonnageDAO.KEY+" = ? ORDER BY "+CaracteristiqueListeDAO.NOM, new String[]{charName});
        ArrayList<CaracteristiqueValeur> results = new ArrayList<CaracteristiqueValeur>();
        CaracteristiqueListe caracListe;
        CaracteristiqueValeur caracValeur;
        while (c.moveToNext()){
            caracListe = new CaracteristiqueListe(c.getInt(4), c.getString(5), c.getString(6));
            caracValeur = new CaracteristiqueValeur(c.getInt(0), c.getFloat(1), c.getFloat(2),
                                                                             c.getString(3), caracListe);
            results.add(caracValeur);
        }
        return results;
    }

    /**
     * Create new values of caracteristique for CaracteristiqueListe of the
     * matching universe with no matching CaracteristiqueValue
     * @param character
     */
    public void initializeNewValues(FichePersonnage character){
        String outerKey = CaracteristiqueListeDAO.KEY;
        String outerTable = CaracteristiqueListeDAO.TABLE_NAME;
        Cursor c = mDb.rawQuery("SELECT * FROM "+outerTable+
                " WHERE "+outerKey+" NOT IN ( SELECT "+outerTable+"."+outerKey+" FROM "+
                    TABLE_NAME+" JOIN "+outerTable+" ON "+TABLE_NAME+"."+outerKey+" = "+outerTable+"."+outerKey+
                    " WHERE "+FichePersonnageDAO.KEY+" = ?) AND " +
                UniversDAO.KEY+" = ?"
                , new String[]{character.getNomFiche(), character.getNomUnivers()});
        if(c.getCount()>0){
            ArrayList<CaracteristiqueListe> tempResults = new ArrayList<CaracteristiqueListe>();
            while (c.moveToNext()){
                tempResults.add(new CaracteristiqueListe(c.getInt(0), c.getString(1), c.getString(2)));
            }
            ArrayList<CaracteristiqueValeur> newValues = new ArrayList<CaracteristiqueValeur>();
            for(CaracteristiqueListe result : tempResults){
                newValues.add(new CaracteristiqueValeur(0,0,character.getNomFiche(),result));
            }
            for(CaracteristiqueValeur newValue : newValues){
                createCaracteristiqueValeur(newValue);
            }
        }
    }

}
