package edu.jdr.DicePaper.models.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import edu.jdr.DicePaper.models.DAOBase;
import edu.jdr.DicePaper.models.table.MasterListe;

import java.util.ArrayList;

/**
 * Created by mario on 06/02/14.
 */
public class MasterListeDAO extends DAOBase{

    public MasterListeDAO(Context pContext){
        super(pContext);
    }

    public static final String UTILITAIRE_LISTE = "utilitaire_liste";
    public static final String JAUGE_LISTE = "jauge_liste";
    public static final String COMPETENCE_LISTE = "competence_liste";
    public static final String CARACTERISTIQUE_LISTE = "caracteristique_liste";
    public static enum Table  {
        UTILITAIRE_LISTE,
        JAUGE_LISTE,
        COMPETENCE_LISTE,
        CARACTERISTIQUE_LISTE
    }

    public String TABLE_NAME = "default";
    public String KEY = TABLE_NAME+"_id";
    public String NOM = "nom";
    public String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " ( " + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            " "+NOM+" TEXT NOT NULL, "+UniversDAO.KEY+
            " TEXT REFERENCES "+UniversDAO.TABLE_NAME+"("+UniversDAO.KEY+") ON DELETE CASCADE);";
    public String TABLE_DROP = "DROP TABLE IF EXIST " + TABLE_NAME + ";";

    public long createMasterListe(MasterListe masterListe){
        ContentValues value = new ContentValues();
        value.put(NOM, masterListe.getNom());
        value.put(UniversDAO.KEY, masterListe.getNomUnivers());
        return mDb.insert(TABLE_NAME, null, value);
    }

    public long updateMasterListe(MasterListe masterListe){
        ContentValues value = new ContentValues();
        value.put(NOM, masterListe.getNom());
        value.put(UniversDAO.KEY, masterListe.getNomUnivers());
        return mDb.update(TABLE_NAME, value, KEY+" = ?", new String[]{Integer.toString(masterListe.getUtilitaireListeId())});
    }

    /**
     * Delete a MasterListe
     * @param id
     * @return the number of rows affected
     */
    public int delete(int id){
        return mDb.delete(TABLE_NAME, KEY + " = ?", new String[]{String.valueOf(id)});
    }

    public MasterListe getMasterListe(int id){
        Cursor c = mDb.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY+" = ?", new String[]{String.valueOf(id)});
        if(c.moveToFirst()){
            return new MasterListe(c.getInt(0), c.getString(1), c.getString(2));
        } else {
            return null;
        }
    }

    public ArrayList<MasterListe> getAllMasterListe(String univ){
        Cursor c = mDb.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+UniversDAO.KEY+" = ?", new String[]{univ});
        ArrayList<MasterListe> results = new ArrayList<MasterListe>();
        while (c.moveToNext()){
            results.add(new MasterListe(c.getInt(0), c.getString(1), c.getString(2)));
        }
        return results;
    }

    public void setTableName(Table table){
        String name;
        switch (table) {
            case UTILITAIRE_LISTE:
                name=UTILITAIRE_LISTE;
                break;
            case CARACTERISTIQUE_LISTE:
                name = CARACTERISTIQUE_LISTE;
                break;
            case COMPETENCE_LISTE:
                name = COMPETENCE_LISTE;
                break;
            case JAUGE_LISTE:
                name = JAUGE_LISTE;
                break;
            default:
                name = TABLE_NAME;
                break;
        }
        this.TABLE_NAME = name;
    }
}
