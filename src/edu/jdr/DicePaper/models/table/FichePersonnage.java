package edu.jdr.DicePaper.models.table;

/**
 * Created by paulyves on 1/28/14.
 */
public class FichePersonnage {
    private String nomFiche;
    private String nomUnivers;
    private int favori;

    public FichePersonnage(){

    }
    public FichePersonnage(String nomFiche, int favori) {
        this.favori = favori;
        this.nomFiche = nomFiche;
    }
    public FichePersonnage(String nomFiche, int favori, String univ) {
        this.favori = favori;
        this.nomFiche = nomFiche;
        this.nomUnivers = univ;
    }

    public String getNomFiche() {
        return nomFiche;
    }

    public void setNomFiche(String nomFiche) {
        this.nomFiche = nomFiche;
    }

    public String getNomUnivers() {
        return nomUnivers;
    }

    public void setNomUnivers(String nomUnivers) {
        this.nomUnivers = nomUnivers;
    }

    public int getFavori() {
        return favori;
    }

    public void setFavori(int favori) {
        this.favori = favori;
    }
}
