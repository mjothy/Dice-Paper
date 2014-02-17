package edu.jdr.DicePaper.models.table;

/**
 * Created by mario on 06/02/14.
 */
public class MasterListe {
    protected int id;
    protected String nom;
    protected String nomUnivers;

    public MasterListe(){

    }

    public MasterListe(String nom, String nomUnivers) {
        this.nom = nom;
        this.nomUnivers = nomUnivers;
    }

    public MasterListe(int id, String nom, String nomUnivers) {
        this.id = id;
        this.nom = nom;
        this.nomUnivers = nomUnivers;
    }

    public int getListeId() {
        return id;
    }

    public void setListeId(int id) {
        this.id = id;
    }

    public String getNomUnivers() {
        return nomUnivers;
    }

    public void setNomUnivers(String nomUnivers) {
        this.nomUnivers = nomUnivers;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString(){
        return nom;
    }
}
