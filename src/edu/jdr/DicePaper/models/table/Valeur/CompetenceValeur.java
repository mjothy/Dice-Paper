package edu.jdr.DicePaper.models.table.Valeur;

import edu.jdr.DicePaper.models.table.Liste.CompetenceListe;

import java.util.ArrayList;

/**
 * Created by paulyves on 2/19/14.
 */
public class CompetenceValeur implements Comparable<CompetenceValeur>{
    protected int key;
    protected float baseValue;
    protected float modifiedValue;
    protected String fiche;
    protected CompetenceListe relatedList;
    protected ArrayList<Specialisation> linkedSpecialisation;

    public CompetenceValeur(float baseValue, float modifiedValue, String fiche, CompetenceListe relatedList) {
        this.baseValue = baseValue;
        this.modifiedValue = modifiedValue;
        this.fiche = fiche;
        this.relatedList = relatedList;
    }

    public CompetenceValeur(int key, float baseValue, float modifiedValue, String fiche) {
        this.key = key;
        this.baseValue = baseValue;
        this.modifiedValue = modifiedValue;
        this.fiche = fiche;
    }

    public CompetenceValeur(int key, float baseValue, float modifiedValue, String fiche, CompetenceListe relatedList) {
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

    public CompetenceListe getRelatedList() {
        return relatedList;
    }

    public void setRelatedList(CompetenceListe relatedList) {
        this.relatedList = relatedList;
    }

    public ArrayList<Specialisation> getLinkedSpecialisation() {
        return linkedSpecialisation;
    }

    public void setLinkedSpecialisation(ArrayList<Specialisation> linkedSpecialisation) {
        this.linkedSpecialisation = linkedSpecialisation;
    }

    @Override
    public String toString(){
        return Float.toString(baseValue)+" ("+Float.toString(modifiedValue)+")";
    }

    @Override
    public int compareTo(CompetenceValeur other){
        return relatedList.compareTo(other.relatedList);
    }
}
