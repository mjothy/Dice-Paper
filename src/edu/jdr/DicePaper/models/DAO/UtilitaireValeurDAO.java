package edu.jdr.DicePaper.models.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import edu.jdr.DicePaper.models.DAOBase;
import edu.jdr.DicePaper.models.table.UtilitaireListe;
import edu.jdr.DicePaper.models.table.UtilitaireValeur;

import java.util.ArrayList;

/**
 * Created by paulyves on 2/19/14.
 */
public class UtilitaireValeurDAO extends DAOBase {
    public UtilitaireValeurDAO(Context pContext){
        super(pContext);
    }
    public static final String TABLE_NAME = "utilitaire_valeur";
    public static final String KEY = "utilitaire_valeur_id";
    public static final String VALEUR = "valeur";
    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " ( " + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            " "+VALEUR+" TEXT, "+UtilitaireListeDAO.KEY+
            " INTEGER REFERENCES "+UtilitaireListeDAO.TABLE_NAME+"("+UtilitaireListeDAO.KEY+") ON DELETE CASCADE, "+
            ""+FichePersonnageDAO.KEY+" TEXT NOT NULL REFERENCES "+FichePersonnageDAO.TABLE_NAME+"("+FichePersonnageDAO.KEY+") ON DELETE CASCADE);";
    public static final String TABLE_DROP = "DROP TABLE IF EXIST " + TABLE_NAME + ";";

    public long createUtilitaireValeur(UtilitaireValeur utilitaire){
        ContentValues value = new ContentValues();
        value.put(UtilitaireValeurDAO.VALEUR, utilitaire.getValue());
        value.put(FichePersonnageDAO.KEY, utilitaire.getFiche());
        value.put(UtilitaireListeDAO.KEY, utilitaire.getRelatedList().getListeId());
        return mDb.insert(TABLE_NAME, null, value);
    }

    public long updateUtilitaireValeur(UtilitaireValeur utilitaire){
        ContentValues value = new ContentValues();
        value.put(UtilitaireValeurDAO.VALEUR, utilitaire.getValue());
        return mDb.update(UtilitaireValeurDAO.TABLE_NAME, value, KEY+" = ?", new String[]{Integer.toString(utilitaire.getKey())});
    }

    /**
     * Delete a UtilitaireValeur
     * @param id
     * @return the number of rows affected
     */
    public int delete(int id){
        return mDb.delete(TABLE_NAME, KEY + " = ?", new String[]{String.valueOf(id)});
    }

    public UtilitaireValeur getUtilitaireValeur(int id){
        Cursor c = mDb.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY+" = ?", new String[]{String.valueOf(id)});
        if(c.moveToFirst()){
            UtilitaireValeur result = new UtilitaireValeur(c.getInt(0), c.getString(1), c.getString(3));
            return result;
        } else {
            return null;
        }
    }

    /**
     * Get all the utilitaireValeur related to a character sheet, fed with the
     * corresponding utilitaireListe
     * @param charName the name of the character we want the utils
     * @return
     */
    public ArrayList<UtilitaireValeur> getAllUtilitaireValeur(String charName){
        String outerKey = UtilitaireListeDAO.KEY;
        String joinTable = UtilitaireListeDAO.TABLE_NAME;
        Cursor c = mDb.rawQuery("SELECT "+KEY+", "+VALEUR+", "+FichePersonnageDAO.KEY+
                ", "+joinTable+"."+outerKey+", "+UtilitaireListeDAO.NOM+", "+UniversDAO.KEY+
                " FROM "+TABLE_NAME+" JOIN "+joinTable+" ON "+TABLE_NAME+"."+outerKey+" = "+joinTable+"."+outerKey+
                " WHERE "+FichePersonnageDAO.KEY+" = ?", new String[]{charName});
        ArrayList<UtilitaireValeur> results = new ArrayList<UtilitaireValeur>();
        UtilitaireListe utilListe;
        UtilitaireValeur utilitaireValeur;
        while (c.moveToNext()){
            utilListe = new UtilitaireListe(c.getInt(3), c.getString(4), c.getString(5));
            utilitaireValeur = new UtilitaireValeur(c.getInt(0), c.getString(1), c.getString(2), utilListe);
            results.add(utilitaireValeur);
        }
        return results;
    }
}
