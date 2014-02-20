package edu.jdr.DicePaper.models.table.Valeur;

import edu.jdr.DicePaper.models.table.Liste.CaracteristiqueListe;

import java.util.ArrayList;

/**
 * Created by paulyves on 2/19/14.
 */
public class CaracteristiqueValeur {
    protected int key;
    protected float baseValue;
    protected float modifiedValue;
    protected String fiche;
    protected CaracteristiqueListe relatedList;
    protected ArrayList<ModificateurValeur> linkedModificateur;

    public CaracteristiqueValeur(float baseValue, float modifiedValue, String fiche, CaracteristiqueListe relatedList) {
        this.baseValue = baseValue;
        this.modifiedValue = modifiedValue;
        this.fiche = fiche;
        this.relatedList = relatedList;
    }

    public CaracteristiqueValeur(int key, float baseValue, float modifiedValue, String fiche) {
        this.key = key;
        this.baseValue = baseValue;
        this.modifiedValue = modifiedValue;
        this.fiche = fiche;
    }

    public CaracteristiqueValeur(int key, float baseValue, float modifiedValue, String fiche, CaracteristiqueListe relatedList) {
        this.key = key;
        this.baseValue = baseValue;
        this.modifiedValue = modifiedValue;
        this.fiche = fiche;
        this.relatedList = relatedList;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public float getBaseValue() {
        return baseValue;
    }

    public void setBaseValue(float baseValue) {
        this.baseValue = baseValue;
    }

    public float getModifiedValue() {
        return modifiedValue;
    }

    public void setModifiedValue(float modifiedValue) {
        this.modifiedValue = modifiedValue;
    }

    public String getFiche() {
        return fiche;
    }

    public void setFiche(String fiche) {
        this.fiche = fiche;
    }

    public CaracteristiqueListe getRelatedList() {
        return relatedList;
    }

    public void setRelatedList(CaracteristiqueListe relatedList) {
        this.relatedList = relatedList;
    }

    public ArrayList<ModificateurValeur> getLinkedModificateur() {
        return linkedModificateur;
    }

    public void setLinkedModificateur(ArrayList<ModificateurValeur> linkedModificateur) {
        this.linkedModificateur = linkedModificateur;
    }

    @Override
    public String toString(){
        return Float.toString(baseValue)+" ("+Float.toString(modifiedValue)+")";
    }
}
