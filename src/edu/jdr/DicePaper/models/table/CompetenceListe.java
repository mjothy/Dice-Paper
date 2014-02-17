package edu.jdr.DicePaper.models.table;

/**
 * Created by paulyves on 1/30/14.
 */
public class CompetenceListe extends MasterListe{
    public CompetenceListe(int competenceListeId, String nom, String nomUnivers) {
        super(competenceListeId, nom, nomUnivers);
    }

    public CompetenceListe(String nom, String nomUnivers) {
        super(nom,nomUnivers);
    }
}
