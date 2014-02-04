package edu.jdr.DicePaper.models.table;

/**
 * Created by mario on 30/01/14.
 */
public class UtilitaireListe {
    private int utilitaireListeId;
    private String nom;
    private String nomUnivers;

    public UtilitaireListe(String nom, String nomUnivers) {
        this.nom = nom;
        this.nomUnivers = nomUnivers;
    }

    public UtilitaireListe(int utilitaireListeId, String nom, String nomUnivers) {
        this.utilitaireListeId = utilitaireListeId;
        this.nom = nom;
        this.nomUnivers = nomUnivers;
    }

    public int getUtilitaireListeId() {
        return utilitaireListeId;
    }

    public void setUtilitaireListeId(int utilitaireListeId) {
        this.utilitaireListeId = utilitaireListeId;
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
