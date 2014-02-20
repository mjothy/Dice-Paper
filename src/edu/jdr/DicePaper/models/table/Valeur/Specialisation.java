package edu.jdr.DicePaper.models.table.Valeur;

/**
 * Created by paulyves on 2/20/14.
 */
public class Specialisation {
    protected int key;
    protected String nom;
    protected float value;
    protected int competenceId;

    public Specialisation(int key, String nom, float value, int competenceId) {
        this.key = key;
        this.nom = nom;
        this.value = value;
        this.competenceId = competenceId;
    }

    public Specialisation(String nom, float value, int competenceId) {
        this.nom = nom;
        this.value = value;
        this.competenceId = competenceId;
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

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public int getCompetenceId() {
        return competenceId;
    }

    public void setCompetenceId(int competenceId) {
        this.competenceId = competenceId;
    }

    @Override
    public String toString(){
        if(value!=0){
            return nom+" ("+value+")";
        }else{
            return nom;
        }
    }
}
