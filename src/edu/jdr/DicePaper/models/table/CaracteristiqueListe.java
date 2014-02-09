package edu.jdr.DicePaper.models.table;

import edu.jdr.DicePaper.models.DAO.ModificateurListeDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by paulyves on 1/28/14.
 */
public class CaracteristiqueListe {
    private int caracteristiqueListeId;
    private String nom;
    private String nomUnivers;
    private List<ModificateurListe> linkedModificateur;

    public CaracteristiqueListe(String nom, String nomUnivers) {
        this.nom = nom;
        this.nomUnivers = nomUnivers;
    }

    public CaracteristiqueListe(int caracteristiqueListeId, String nom, String nomUnivers) {
        this.caracteristiqueListeId = caracteristiqueListeId;
        this.nom = nom;
        this.nomUnivers = nomUnivers;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getCaracteristiqueListeId() {
        return caracteristiqueListeId;
    }

    public void setCaracteristiqueListeId(int caracteristiqueListeId) {
        this.caracteristiqueListeId = caracteristiqueListeId;
    }

    public String getNomUnivers() {
        return nomUnivers;
    }

    public void setNomUnivers(String nomUnivers) {
        this.nomUnivers = nomUnivers;
    }

    public List<ModificateurListe> getLinkedModificateur() {
        return linkedModificateur;
    }

    public void setLinkedModificateur(ArrayList<ModificateurListe> linkedModificateur) {
        this.linkedModificateur = linkedModificateur;
    }

    @Override
    public String toString(){
        return nom;
    }
}
