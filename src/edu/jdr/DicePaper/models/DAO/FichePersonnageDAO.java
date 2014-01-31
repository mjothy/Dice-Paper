package edu.jdr.DicePaper.models.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import edu.jdr.DicePaper.models.DAOBase;
import edu.jdr.DicePaper.models.table.FichePersonnage;

import java.util.ArrayList;

/**
 * Created by paulyves on 1/28/14.
 */
public class FichePersonnageDAO extends DAOBase {

    public FichePersonnageDAO(Context pContext){
        super(pContext);
    }

    public static final String TABLE_NAME = "fiche_personnage";
    public static final String KEY = "nom_fiche";
    public static final String FAVORI = "favori";
    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " ( " + KEY + " TEXT PRIMARY KEY," +
                                              " "+FAVORI+" INTEGER, "+UniversDAO.KEY+
                                              " TEXT REFERENCES "+UniversDAO.TABLE_NAME+"("+UniversDAO.KEY+") ON DELETE CASCADE);";
    public static final String TABLE_DROP = "DROP TABLE IF EXIST " + TABLE_NAME + ";";

    public long CreateFiche(FichePersonnage perso){
        ContentValues value = new ContentValues();
        value.put(FichePersonnageDAO.KEY, perso.getNomFiche());
        value.put(FichePersonnageDAO.FAVORI, perso.getFavori());
        value.put(UniversDAO.KEY, perso.getNomUnivers());
        return mDb.insert(FichePersonnageDAO.TABLE_NAME, null, value);
    }

    public FichePersonnage getFiche(String nomFiche){
        Cursor c = mDb.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY+" = ?", new String[]{nomFiche});
        if(c.moveToFirst()){
            FichePersonnage fiche = new FichePersonnage();
            fiche.setNomFiche(c.getString(0));
            fiche.setFavori(c.getInt(1));
            fiche.setNomUnivers(c.getString(2));
            return fiche;
        } else {
            return null;
        }
    }

    public ArrayList<FichePersonnage> getAllFiche(){
        Cursor c = mDb.rawQuery("SELECT * FROM "+TABLE_NAME, null);
        ArrayList<FichePersonnage> results = new ArrayList<FichePersonnage>();
        while (c.moveToNext()){
            results.add(new FichePersonnage(c.getString(0),c.getInt(1),c.getString(2)));
        }
        return results;
    }
}
