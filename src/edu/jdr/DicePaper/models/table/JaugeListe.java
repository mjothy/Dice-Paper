package edu.jdr.DicePaper.models.table;

/**
 * Created by mario on 30/01/14.
 */
public class JaugeListe {
    private int jaugeListeId;
    private String nom;
    private String nomUnivers;
    private int min;
    private int max;

    public JaugeListe(String nom, String nomUnivers, int min, int max) {
        this.nom = nom;
        this.nomUnivers = nomUnivers;
        this.min = min;
        this.max = max;
    }

    public JaugeListe(int jaugeListeId, String nom, String nomUnivers, int min, int max) {
        this.jaugeListeId = jaugeListeId;
        this.nom = nom;
        this.nomUnivers = nomUnivers;
        this.min = min;
        this.max = max;
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
    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    @Override
    public String toString(){
        return nom+" (de "+min+" à "+max+")";
    }
}
