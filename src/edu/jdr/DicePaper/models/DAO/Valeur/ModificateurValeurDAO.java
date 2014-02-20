package edu.jdr.DicePaper.models.DAO.Valeur;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import edu.jdr.DicePaper.models.DAO.FichePersonnageDAO;
import edu.jdr.DicePaper.models.DAO.Liste.CaracteristiqueListeDAO;
import edu.jdr.DicePaper.models.DAO.Liste.ModificateurListeDAO;
import edu.jdr.DicePaper.models.DAO.Liste.UtilitaireListeDAO;
import edu.jdr.DicePaper.models.DAO.UniversDAO;
import edu.jdr.DicePaper.models.DAOBase;
import edu.jdr.DicePaper.models.table.FichePersonnage;
import edu.jdr.DicePaper.models.table.Liste.ModificateurListe;
import edu.jdr.DicePaper.models.table.Valeur.ModificateurValeur;

import java.util.ArrayList;

/**
 * Created by paulyves on 2/19/14.
 */
public class ModificateurValeurDAO extends DAOBase {
    public ModificateurValeurDAO(Context pContext){
        super(pContext);
    }
    public static final String TABLE_NAME = "modificateur_valeur";
    public static final String KEY = "modificateur_valeur_id";
    public static final String VALEUR = "valeur";
    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " ( " + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            " "+VALEUR+" REAL, "+ ModificateurListeDAO.KEY+
            " INTEGER REFERENCES "+ModificateurListeDAO.TABLE_NAME+"("+ModificateurListeDAO.KEY+") ON DELETE CASCADE, "+
            ""+ FichePersonnageDAO.KEY+" TEXT NOT NULL REFERENCES "+FichePersonnageDAO.TABLE_NAME+"("+FichePersonnageDAO.KEY+") ON DELETE CASCADE);";
    public static final String TABLE_DROP = "DROP TABLE IF EXIST " + TABLE_NAME + ";";

    public long createModificateurValeur(ModificateurValeur mod){
        ContentValues value = new ContentValues();
        value.put(VALEUR, mod.getValue());
        value.put(FichePersonnageDAO.KEY, mod.getFiche());
        value.put(ModificateurListeDAO.KEY, mod.getRelatedList().getCaracteristiqueListeId());
        return mDb.insert(TABLE_NAME, null, value);
    }

    public long updateModificateurValeur(ModificateurValeur mod){
        ContentValues value = new ContentValues();
        value.put(VALEUR, mod.getValue());
        return mDb.update(TABLE_NAME, value, KEY+" = ?", new String[]{Integer.toString(mod.getKey())});
    }

    /**
     * Delete a ModificateurValeur
     * @param id
     * @return the number of rows affected
     */
    public int delete(int id){
        return mDb.delete(TABLE_NAME, KEY + " = ?", new String[]{String.valueOf(id)});
    }

    public ModificateurValeur getModificateurValeur(int id){
        Cursor c = mDb.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY+" = ?", new String[]{String.valueOf(id)});
        if(c.moveToFirst()){
            ModificateurValeur result = new ModificateurValeur(c.getInt(0), c.getFloat(1), c.getString(3));
            return result;
        } else {
            return null;
        }
    }

    /**
     * Get all the ModificateurValeur related to a character sheet and a Caracteristique, fed with the
     * corresponding ModificateurListe
     * @param charName the name of the character we want the Modificateurs
     * @return
     */
    public ArrayList<ModificateurValeur> getAllModificateurValeur(String charName, int caracId){
        String outerKey = ModificateurListeDAO.KEY;
        String joinTable = ModificateurListeDAO.TABLE_NAME;
        Cursor c = mDb.rawQuery("SELECT "+KEY+", "+VALEUR+", "+FichePersonnageDAO.KEY+
                ", "+joinTable+"."+outerKey+", "+ UtilitaireListeDAO.NOM+", "+ CaracteristiqueListeDAO.KEY+
                " FROM "+TABLE_NAME+" JOIN "+joinTable+" ON "+TABLE_NAME+"."+outerKey+" = "+joinTable+"."+outerKey+
                " WHERE "+FichePersonnageDAO.KEY+" = ? "+" AND "+joinTable+"."+CaracteristiqueListeDAO.KEY+" = ?",
                new String[]{charName, Integer.toString(caracId)});
        ArrayList<ModificateurValeur> results = new ArrayList<ModificateurValeur>();
        ModificateurListe modListe;
        ModificateurValeur modValeur;
        while (c.moveToNext()){
            modListe = new ModificateurListe(c.getInt(3), c.getString(4), c.getInt(5));
            modValeur = new ModificateurValeur(c.getInt(0), c.getFloat(1), c.getString(2), modListe);
            results.add(modValeur);
        }
        return results;
    }

    /**
     * Create new values of modificateur for ModificateurListe of the
     * matching universe with no matching ModificateurValue
     * @param character
     */
    public void initializeNewValues(FichePersonnage character){
        String outerKey = ModificateurListeDAO.KEY;
        String outerTable = ModificateurListeDAO.TABLE_NAME;
        Cursor c = mDb.rawQuery("SELECT * FROM "+outerTable+
                " WHERE "+outerKey+" NOT IN ( SELECT "+outerTable+"."+outerKey+" FROM "+
                TABLE_NAME+" JOIN "+outerTable+" ON "+TABLE_NAME+"."+outerKey+" = "+outerTable+"."+outerKey+
                " JOIN "+CaracteristiqueListeDAO.TABLE_NAME+" ON "+outerTable+"."+CaracteristiqueListeDAO.KEY+" = "+
                CaracteristiqueListeDAO.TABLE_NAME+"."+CaracteristiqueListeDAO.KEY+
                " WHERE "+FichePersonnageDAO.KEY+" = ?) AND" +
                UniversDAO.KEY+" = ?"
                , new String[]{character.getNomFiche(), character.getNomUnivers()});
        if(c.getCount()>0){
            ArrayList<ModificateurListe> tempResults = new ArrayList<ModificateurListe>();
            while (c.moveToNext()){
                tempResults.add(new ModificateurListe(c.getInt(0), c.getString(1), c.getInt(2)));
            }
            ArrayList<ModificateurValeur> newValues = new ArrayList<ModificateurValeur>();
            for(ModificateurListe result : tempResults){
                newValues.add(new ModificateurValeur(0,character.getNomFiche(),result));
            }
            for(ModificateurValeur newValue : newValues){
                createModificateurValeur(newValue);
            }
        }
    }

}
