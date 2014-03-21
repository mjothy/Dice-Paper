package edu.jdr.DicePaper.utils.XML;

import android.content.Context;
import android.os.Environment;
import edu.jdr.DicePaper.models.DAO.Liste.*;
import edu.jdr.DicePaper.models.DAO.UniversDAO;
import edu.jdr.DicePaper.models.table.Liste.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * Created by mario on 19/03/14.
 */
public class Parser {
    private String nomUnivers;
    private Context pContext;

    public Parser(String nomUnivers, Context pContext) {
        this.nomUnivers = nomUnivers;
        this.pContext = pContext;
    }

    public boolean parse(){
        try {
            Document document = getDom();
            if(document==null){
                return false;
            }
            String univName = document.getDocumentElement().getAttribute("nom");
            UniversDAO universDAO = new UniversDAO(pContext);
            universDAO.open();
            if(universDAO.getAllUnivers().contains(univName)){
                return false;
            }
            universDAO.createUnivers(nomUnivers);
            universDAO.close();
            saveCarac(document);
            saveComp(document);
            saveJauge(document);
            saveUtil(document);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return true;
    }

    private void saveCarac(Document doc){
        NodeList caracteristiques = doc.getElementsByTagName("CaracteristiqueListe");

        CaracteristiqueListeDAO caracteristiqueListeDAO = new CaracteristiqueListeDAO(pContext);
        ModificateurListeDAO modificateurListeDAO = new ModificateurListeDAO(pContext);
        caracteristiqueListeDAO.open();
        modificateurListeDAO.open();

        for(int temp = 0; temp < caracteristiques.getLength(); temp++) {
            Element caracteristique = (Element) caracteristiques.item(temp);
            CaracteristiqueListe caracteristiqueListe = new CaracteristiqueListe(caracteristique.getAttribute("nom"), nomUnivers);
            long id = caracteristiqueListeDAO.createCaracListe(caracteristiqueListe);
            NodeList modificateurs = caracteristique.getElementsByTagName("ModificateurListe");
            for(int temp2 = 0; temp2 < modificateurs.getLength(); temp2++) {
                Element modificateur = (Element) modificateurs.item(temp2);
                ModificateurListe modificateurListe = new ModificateurListe(modificateur.getAttribute("nom"), (int)id);
                modificateurListeDAO.createModListe(modificateurListe);
            }
        }
        caracteristiqueListeDAO.close();
        modificateurListeDAO.close();
    }

    private void saveComp(Document doc){
        NodeList competences = doc.getElementsByTagName("CompetenceListe");

        CompetenceListeDAO competenceListeDAO = new CompetenceListeDAO(pContext);
        competenceListeDAO.open();

        for(int temp = 0; temp < competences.getLength(); temp++) {
            Element competence = (Element) competences.item(temp);
            CompetenceListe competenceListe = new CompetenceListe(competence.getAttribute("nom"), nomUnivers);
            competenceListeDAO.createCompetenceListe(competenceListe);
        }
        competenceListeDAO.close();
    }

    private void saveUtil(Document doc){
        NodeList utilitaires = doc.getElementsByTagName("UtilitaireListe");

        UtilitaireListeDAO utilitaireListeDAO = new UtilitaireListeDAO(pContext);
        utilitaireListeDAO.open();

        for(int temp = 0; temp < utilitaires.getLength(); temp++) {
            Element utilitaire = (Element) utilitaires.item(temp);
            UtilitaireListe utilitaireListe = new UtilitaireListe(utilitaire.getAttribute("nom"), nomUnivers);
            utilitaireListeDAO.createUtilitaireListe(utilitaireListe);
        }
        utilitaireListeDAO.close();
    }

    private void saveJauge(Document doc){
        NodeList jauges = doc.getElementsByTagName("JaugeListe");

        JaugeListeDAO jaugeListeDAO = new JaugeListeDAO(pContext);
        jaugeListeDAO.open();

        for(int temp = 0; temp < jauges.getLength(); temp++) {
            Element jauge = (Element) jauges.item(temp);
            String nom = jauge.getAttribute("nom");
            int min = Integer.parseInt(jauge.getAttribute("min"));
            int max = Integer.parseInt(jauge.getAttribute("max"));
            JaugeListe jaugeListe = new JaugeListe(nom, nomUnivers, min, max);
            jaugeListeDAO.createJaugeListe(jaugeListe);
        }
        jaugeListeDAO.close();
    }

    private Document getDom() throws ParserConfigurationException, IOException, SAXException {
        File file = new File(getAlbumStorageDir("jdr"),nomUnivers+".xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(file);
        if(doc==null){
            return null;
        }
        //optional, but recommended
        //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
        doc.getDocumentElement().normalize();

        return doc;
    }

    private File getAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS), albumName);
        file.mkdirs();

        return file;
    }
}
