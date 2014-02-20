package edu.jdr.DicePaper.models.table.Valeur;

import edu.jdr.DicePaper.models.table.Liste.ModificateurListe;

/**
 * Created by paulyves on 2/19/14.
 */
public class ModificateurValeur {
    protected int key;
    protected float value;
    protected String fiche;
    protected ModificateurListe relatedList;

    public ModificateurValeur(int key, float value, String fiche) {
        this.key = key;
        this.value = value;
        this.fiche = fiche;
    }

    public ModificateurValeur(float value, String fiche, ModificateurListe relatedList) {
        this.value = value;
        this.fiche = fiche;
        this.relatedList = relatedList;
    }

    public ModificateurValeur(int key, float value, String fiche, ModificateurListe relatedList) {
        this.key = key;
        this.value = value;
        this.fiche = fiche;
        this.relatedList = relatedList;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public String getFiche() {
        return fiche;
    }

    public void setFiche(String fiche) {
        this.fiche = fiche;
    }

    public ModificateurListe getRelatedList() {
        return relatedList;
    }

    public void setRelatedList(ModificateurListe relatedList) {
        this.relatedList = relatedList;
    }

    @Override
    public String toString(){
        return relatedList.toString()+" : "+Float.toString(value);
    }
}
