package edu.jdr.DicePaper.models.table.Liste;

/**
 * Created by paulyves on 2/8/14.
 */
public class ModificateurListe {
    private int modificateurListeId;
    private String nomMod;
    private int caracteristiqueListeId;

    public ModificateurListe(String nomMod, int caracteristiqueListeId) {
        this.nomMod = nomMod;
        this.caracteristiqueListeId = caracteristiqueListeId;
    }

    public ModificateurListe(int modificateurListeId, String nomMod, int caracteristiqueListeId) {
        this.modificateurListeId = modificateurListeId;
        this.nomMod = nomMod;
        this.caracteristiqueListeId = caracteristiqueListeId;
    }

    public int getModificateurListeId() {
        return modificateurListeId;
    }

    public void setModificateurListeId(int modificateurListeId) {
        this.modificateurListeId = modificateurListeId;
    }

    public String getNomMod() {
        return nomMod;
    }

    public void setNomMod(String nomMod) {
        this.nomMod = nomMod;
    }

    public int getCaracteristiqueListeId() {
        return caracteristiqueListeId;
    }

    public void setCaracteristiqueListeId(int caracteristiqueListeId) {
        this.caracteristiqueListeId = caracteristiqueListeId;
    }

    @Override
    public String toString(){
        return nomMod;
    }
}
