package edu.jdr.DicePaper.models.DAO.Valeur;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import edu.jdr.DicePaper.models.DAO.FichePersonnageDAO;
import edu.jdr.DicePaper.models.DAO.Liste.JaugeListeDAO;
import edu.jdr.DicePaper.models.DAO.Liste.UtilitaireListeDAO;
import edu.jdr.DicePaper.models.DAO.UniversDAO;
import edu.jdr.DicePaper.models.DAOBase;
import edu.jdr.DicePaper.models.table.Liste.JaugeListe;
import edu.jdr.DicePaper.models.table.Liste.UtilitaireListe;
import edu.jdr.DicePaper.models.table.Valeur.JaugeValeur;
import edu.jdr.DicePaper.models.table.Valeur.UtilitaireValeur;

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
            JaugeValeur result = new JaugeValeur(c.getInt(0), c.getInt(1), c.getInt(2), c.getString(4));
            return result;
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
                " WHERE "+FichePersonnageDAO.KEY+" = ?", new String[]{charName});
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
}
