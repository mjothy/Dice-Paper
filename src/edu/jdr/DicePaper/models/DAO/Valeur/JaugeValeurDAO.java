package edu.jdr.DicePaper.models.DAO.Valeur;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import edu.jdr.DicePaper.models.DAO.FichePersonnageDAO;
import edu.jdr.DicePaper.models.DAO.Liste.JaugeListeDAO;
import edu.jdr.DicePaper.models.DAO.UniversDAO;
import edu.jdr.DicePaper.models.DAOBase;
import edu.jdr.DicePaper.models.table.FichePersonnage;
import edu.jdr.DicePaper.models.table.Liste.JaugeListe;
import edu.jdr.DicePaper.models.table.Valeur.JaugeValeur;

import java.util.ArrayList;

/**
 * Created by paulyves on 2/20/14.
 */
public class JaugeValeurDAO extends DAOBase {
    public JaugeValeurDAO(Context pContext){
        super(pContext);
    }

    public static final String TABLE_NAME = "jauge_valeur";
    public static final String KEY = "jauge_valeur_id";
    public static final String CURRENTVALUE = "valeur_actuelle";
    public static final String MAXVALUE = "valeur_max";
    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " ( " + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            " "+CURRENTVALUE+" INTEGER, "+MAXVALUE+" INTEGER, "+ JaugeListeDAO.KEY+
            " INTEGER REFERENCES "+JaugeListeDAO.TABLE_NAME+"("+JaugeListeDAO.KEY+") ON DELETE CASCADE, "+
            ""+ FichePersonnageDAO.KEY+" TEXT NOT NULL REFERENCES "+FichePersonnageDAO.TABLE_NAME+"("+FichePersonnageDAO.KEY+") ON DELETE CASCADE);";
    public static final String TABLE_DROP = "DROP TABLE IF EXIST " + TABLE_NAME + ";";

    public long createJaugeValeur(JaugeValeur jauge){
        ContentValues value = new ContentValues();
        value.put(CURRENTVALUE, jauge.getCurrentValue());
        value.put(MAXVALUE, jauge.getMaxValue());
        value.put(FichePersonnageDAO.KEY, jauge.getFiche());
        value.put(JaugeListeDAO.KEY, jauge.getRelatedList().getListeId());
        return mDb.insert(TABLE_NAME, null, value);
    }

    public long updateJaugeValeur(JaugeValeur jauge){
        ContentValues value = new ContentValues();
        value.put(CURRENTVALUE, jauge.getCurrentValue());
        value.put(MAXVALUE, jauge.getMaxValue());
        return mDb.update(TABLE_NAME, value, KEY+" = ?", new String[]{Integer.toString(jauge.getKey())});
    }

    /**
     * Delete a JaugeValeur
     * @param id
     * @return the number of rows affected
     */
    public int delete(int id){
        return mDb.delete(TABLE_NAME, KEY + " = ?", new String[]{String.valueOf(id)});
    }

    public JaugeValeur getJaugeValeur(int id){
        Cursor c = mDb.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY+" = ?", new String[]{String.valueOf(id)});
        if(c.moveToFirst()){
            return new JaugeValeur(c.getInt(0), c.getInt(1), c.getInt(2), c.getString(4));
        } else {
            return null;
        }
    }

    /**
     * Get all the jaugeValeur related to a character sheet, fed with the
     * corresponding jaugeListe
     * @param charName the name of the character we want the jauge
     * @return
     */
    public ArrayList<JaugeValeur> getAllJaugeValeur(String charName){
        String outerKey = JaugeListeDAO.KEY;
        String joinTable = JaugeListeDAO.TABLE_NAME;
        Cursor c = mDb.rawQuery("SELECT "+KEY+", "+CURRENTVALUE+", "+MAXVALUE+", "+FichePersonnageDAO.KEY+
                ", "+joinTable+"."+outerKey+", "+JaugeListeDAO.NOM+", "+UniversDAO.KEY+", "+
                JaugeListeDAO.MIN+", "+JaugeListeDAO.MAX+
                " FROM "+TABLE_NAME+" JOIN "+joinTable+" ON "+TABLE_NAME+"."+outerKey+" = "+joinTable+"."+outerKey+
                " WHERE "+FichePersonnageDAO.KEY+" = ? ORDER BY "+JaugeListeDAO.NOM, new String[]{charName});
        ArrayList<JaugeValeur> results = new ArrayList<JaugeValeur>();
        JaugeListe jaugeListe;
        JaugeValeur jaugeValeur;
        while (c.moveToNext()){
            jaugeListe = new JaugeListe(c.getInt(4), c.getString(5), c.getString(6), c.getInt(7), c.getInt(8));
            jaugeValeur = new JaugeValeur(c.getInt(0), c.getInt(1), c.getInt(2), c.getString(3), jaugeListe);
            results.add(jaugeValeur);
        }
        return results;
    }

    /**
     * Create new values of jauge for JaugeListe of the
     * matching universe with no matching JaugeValue
     * @param character
     */
    public void initializeNewValues(FichePersonnage character){
        String outerKey = JaugeListeDAO.KEY;
        String outerTable = JaugeListeDAO.TABLE_NAME;
        Cursor c = mDb.rawQuery("SELECT * FROM "+outerTable+
                " WHERE "+outerKey+" NOT IN ( SELECT "+outerTable+"."+outerKey+" FROM "+
                TABLE_NAME+" JOIN "+outerTable+" ON "+TABLE_NAME+"."+outerKey+" = "+outerTable+"."+outerKey+
                " WHERE "+FichePersonnageDAO.KEY+" = ?) AND " +
                UniversDAO.KEY+" = ?"
                , new String[]{character.getNomFiche(), character.getNomUnivers()});
        if(c.getCount()>0){
            ArrayList<JaugeListe> tempResults = new ArrayList<JaugeListe>();
            while (c.moveToNext()){
                tempResults.add(new JaugeListe(c.getInt(0), c.getString(1), c.getString(4), c.getInt(2), c.getInt(3)));
            }
            ArrayList<JaugeValeur> newValues = new ArrayList<JaugeValeur>();
            for(JaugeListe result : tempResults){
                newValues.add(new JaugeValeur(result.getMin(),result.getMin(),character.getNomFiche(),result));
            }
            for(JaugeValeur newValue : newValues){
                createJaugeValeur(newValue);
            }
        }
    }
}
