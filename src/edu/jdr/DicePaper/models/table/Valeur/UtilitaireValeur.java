package edu.jdr.DicePaper.models.table.Valeur;

import edu.jdr.DicePaper.models.table.Liste.UtilitaireListe;

/**
 * Created by paulyves on 2/19/14.
 */
public class UtilitaireValeur {
    protected UtilitaireListe relatedList;
    protected int key;
    protected String value;
    protected String fiche;

    public UtilitaireValeur(String value, String fiche, UtilitaireListe relatedList) {
        this.value = value;
        this.fiche = fiche;
        this.relatedList = relatedList;
    }

    public UtilitaireValeur(int key, String value, String fiche) {
        this.key = key;
        this.value = value;
        this.fiche = fiche;
    }

    public UtilitaireValeur(int key, String value, String fiche, UtilitaireListe relatedList) {
        this.key = key;
        this.value = value;
        this.fiche = fiche;
        this.relatedList = relatedList;
    }

    public UtilitaireListe getRelatedList() {
        return relatedList;
    }

    public void setRelatedList(UtilitaireListe relatedList) {
        this.relatedList = relatedList;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getFiche() {
        return fiche;
    }

    public void setFiche(String fiche) {
        this.fiche = fiche;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    @Override
    public String toString(){
        return value;
    }
}
