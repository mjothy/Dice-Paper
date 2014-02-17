package edu.jdr.DicePaper.models.table;

import edu.jdr.DicePaper.models.DAO.ModificateurListeDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by paulyves on 1/28/14.
 */
public class CaracteristiqueListe extends MasterListe{
    private List<ModificateurListe> linkedModificateur;

    public CaracteristiqueListe(String nom, String nomUnivers) {
        super(nom,  nomUnivers);
    }

    public CaracteristiqueListe(int id, String nom, String nomUnivers) {
        super(id,nom,nomUnivers);
    }

    public List<ModificateurListe> getLinkedModificateur() {
        return linkedModificateur;
    }

    public void setLinkedModificateur(ArrayList<ModificateurListe> linkedModificateur) {
        this.linkedModificateur = linkedModificateur;
    }
}
