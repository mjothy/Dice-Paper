package edu.jdr.DicePaper.models.table.Valeur;

/**
 * Created by paulyves on 2/20/14.
 */
public class Equipement {
    protected int key;
    protected String nom;
    protected String description;
    protected String fiche;

    public Equipement(int key, String nom, String description) {
        this.key = key;
        this.nom = nom;
        this.description = description;
    }

    public Equipement(String nom, String description, String fiche) {
        this.nom = nom;
        this.description = description;
        this.fiche = fiche;
    }

    public Equipement(int key, String nom, String description, String fiche) {
        this.key = key;
        this.nom = nom;
        this.description = description;
        this.fiche = fiche;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFiche() {
        return fiche;
    }

    public void setFiche(String fiche) {
        this.fiche = fiche;
    }
    @Override
    public String toString(){
        return nom+" : "+description;
    }
}
