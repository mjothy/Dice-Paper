package edu.jdr.DicePaper.models.table.Valeur;

import edu.jdr.DicePaper.models.table.Liste.JaugeListe;

/**
 * Created by paulyves on 2/20/14.
 */
public class JaugeValeur {
    protected int key;
    protected int currentValue;
    protected int maxValue;
    protected String fiche;
    protected JaugeListe relatedList;

    public JaugeValeur(int currentValue, int maxValue, String fiche, JaugeListe relatedList) {
        this.currentValue = currentValue;
        this.maxValue = maxValue;
        this.fiche = fiche;
        this.relatedList = relatedList;
    }

    public JaugeValeur(int key, int currentValue, int maxValue, String fiche) {
        this.key = key;
        this.currentValue = currentValue;
        this.maxValue = maxValue;
        this.fiche = fiche;
    }

    public JaugeValeur(int key, int currentValue, int maxValue, String fiche, JaugeListe relatedList) {
        this.key = key;
        this.currentValue = currentValue;
        this.maxValue = maxValue;
        this.fiche = fiche;
        this.relatedList = relatedList;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(int currentValue) {
        this.currentValue = currentValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public String getFiche() {
        return fiche;
    }

    public void setFiche(String fiche) {
        this.fiche = fiche;
    }

    public JaugeListe getRelatedList() {
        return relatedList;
    }

    public void setRelatedList(JaugeListe relatedList) {
        this.relatedList = relatedList;
    }
    @Override
    public String toString(){
        return currentValue+"/"+maxValue;
    }
}
