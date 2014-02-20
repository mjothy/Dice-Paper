package edu.jdr.DicePaper.models.DAO.Valeur;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import edu.jdr.DicePaper.models.DAO.FichePersonnageDAO;
import edu.jdr.DicePaper.models.DAO.Liste.CaracteristiqueListeDAO;
import edu.jdr.DicePaper.models.DAO.Liste.CompetenceListeDAO;
import edu.jdr.DicePaper.models.DAO.UniversDAO;
import edu.jdr.DicePaper.models.DAOBase;
import edu.jdr.DicePaper.models.table.Liste.CompetenceListe;
import edu.jdr.DicePaper.models.table.Valeur.CompetenceValeur;

import java.util.ArrayList;

/**
 * Created by paulyves on 2/19/14.
 */
public class CompetenceValeurDAO extends DAOBase {
    public CompetenceValeurDAO(Context pContext){
        super(pContext);
    }
    public static final String TABLE_NAME = "competence_valeur";
    public static final String KEY = "competence_valeur_id";
    public static final String BASEVALUE = "valeur_base";
    public static final String MODIFIEDVALUE = "valeur_actuelle";
    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " ( " + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            " "+BASEVALUE+" REAL, "+MODIFIEDVALUE+" REAL, "+ CaracteristiqueListeDAO.KEY+
            " INTEGER REFERENCES "+ CompetenceListeDAO.TABLE_NAME+"("+CompetenceListeDAO.KEY+") ON DELETE CASCADE, "+
            ""+ FichePersonnageDAO.KEY+" TEXT NOT NULL REFERENCES "+FichePersonnageDAO.TABLE_NAME+"("+FichePersonnageDAO.KEY+") ON DELETE CASCADE);";
    public static final String TABLE_DROP = "DROP TABLE IF EXIST " + TABLE_NAME + ";";

    public long createCompetenceValeur(CompetenceValeur comp){
        ContentValues value = new ContentValues();
        value.put(BASEVALUE, comp.getBaseValue());
        value.put(MODIFIEDVALUE, comp.getModifiedValue());
        value.put(FichePersonnageDAO.KEY, comp.getFiche());
        value.put(CompetenceListeDAO.KEY, comp.getRelatedList().getListeId());
        return mDb.insert(TABLE_NAME, null, value);
    }

    public long updateCompetenceValeur(CompetenceValeur comp){
        ContentValues value = new ContentValues();
        value.put(BASEVALUE, comp.getBaseValue());
        value.put(MODIFIEDVALUE, comp.getModifiedValue());
        return mDb.update(UtilitaireValeurDAO.TABLE_NAME, value, KEY+" = ?", new String[]{Integer.toString(comp.getKey())});
    }

    /**
     * Delete a CompetenceValeur
     * @param id
     * @return the number of rows affected
     */
    public int delete(int id){
        return mDb.delete(TABLE_NAME, KEY + " = ?", new String[]{String.valueOf(id)});
    }
    public CompetenceValeur getCompetenceValeur(int id){
        Cursor c = mDb.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY+" = ?", new String[]{String.valueOf(id)});
        if(c.moveToFirst()){
            CompetenceValeur result = new CompetenceValeur(c.getInt(0), c.getFloat(1), c.getFloat(2), c.getString(4));
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
    public ArrayList<CompetenceValeur> getAllCompetenceValeur(String charName){
        String outerKey = CompetenceListeDAO.KEY;
        String joinTable = CompetenceListeDAO.TABLE_NAME;
        Cursor c = mDb.rawQuery("SELECT "+KEY+", "+BASEVALUE+", "+MODIFIEDVALUE+", "+FichePersonnageDAO.KEY+
                ", "+joinTable+"."+outerKey+", "+CompetenceListeDAO.NOM+", "+ UniversDAO.KEY+
                " FROM "+TABLE_NAME+" JOIN "+joinTable+" ON "+TABLE_NAME+"."+outerKey+" = "+joinTable+"."+outerKey+
                " WHERE "+FichePersonnageDAO.KEY+" = ?", new String[]{charName});
        ArrayList<CompetenceValeur> results = new ArrayList<CompetenceValeur>();
        CompetenceListe compListe;
        CompetenceValeur compValeur;
        while (c.moveToNext()){
            compListe = new CompetenceListe(c.getInt(4), c.getString(5), c.getString(6));
            compValeur = new CompetenceValeur(c.getInt(0), c.getFloat(1), c.getFloat(2),
                    c.getString(3), compListe);
            results.add(compValeur);
        }
        return results;
    }
}
