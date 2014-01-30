package edu.jdr.DicePaper.models;

/**
 * Created by mario on 30/01/14.
 */
public class JaugeListe {
    private int jaugeListeId;
    private String nom;
    private String nomUnivers;

    public JaugeListe(String nom, String nomUnivers) {
        this.nom = nom;
        this.nomUnivers = nomUnivers;
    }

    public JaugeListe(int jaugeListeId, String nom, String nomUnivers) {
        this.jaugeListeId = jaugeListeId;
        this.nom = nom;
        this.nomUnivers = nomUnivers;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getJaugeListeId() {
        return jaugeListeId;
    }

    public void setJaugeListeId(int jaugeListeId) {
        this.jaugeListeId = jaugeListeId;
    }

    public String getNomUnivers() {
        return nomUnivers;
    }

    public void setNomUnivers(String nomUnivers) {
        this.nomUnivers = nomUnivers;
    }
}
