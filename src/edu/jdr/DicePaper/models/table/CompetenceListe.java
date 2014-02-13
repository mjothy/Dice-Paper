package edu.jdr.DicePaper.models.table;

/**
 * Created by paulyves on 1/30/14.
 */
public class CompetenceListe {
    private int competenceListeId;
    private String nom;
    private String nomUnivers;

    public CompetenceListe(int competenceListeId, String nom, String nomUnivers) {
        this.competenceListeId = competenceListeId;
        this.nom = nom;
        this.nomUnivers = nomUnivers;
    }

    public CompetenceListe(String nom, String nomUnivers) {
        this.nom = nom;
        this.nomUnivers = nomUnivers;
    }

    public int getCompetenceListeId() {
        return competenceListeId;
    }

    public void setCompetenceListeId(int competenceListeId) {
        this.competenceListeId = competenceListeId;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNomUnivers() {
        return nomUnivers;
    }

    public void setNomUnivers(String nomUnivers) {
        this.nomUnivers = nomUnivers;
    }

    @Override
    public String toString(){
        return nom;
    }
}
