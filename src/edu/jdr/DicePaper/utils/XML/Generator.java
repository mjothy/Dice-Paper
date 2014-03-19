package edu.jdr.DicePaper.utils.XML;

import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import edu.jdr.DicePaper.models.DAO.Liste.*;
import edu.jdr.DicePaper.models.DAO.UniversDAO;
import edu.jdr.DicePaper.models.DAO.Valeur.CaracteristiqueValeurDAO;
import edu.jdr.DicePaper.models.DAO.Valeur.EquipementDAO;
import edu.jdr.DicePaper.models.table.Liste.*;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Created by mario on 19/03/14.
 */
public class Generator {
    String nomUnivers;
    Context pContext;

    public Generator(String nomUnivers, Context pContext) {
        this.nomUnivers = nomUnivers;
        this.pContext = pContext;
    }

    public boolean generate(){
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // root elements
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("universe");
            doc.appendChild(rootElement);
            rootElement.setAttribute("nom", nomUnivers);

            caracListFeeder(doc, rootElement);
            compListFeeder(doc, rootElement);
            utilListFeeder(doc, rootElement);
            jaugeListFeeder(doc, rootElement);

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(getAlbumStorageDir("jdr"),nomUnivers+".xml"));

            transformer.transform(source, result);

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
        return true;
    }

    private void caracListFeeder(Document doc, Element rootElement){
        CaracteristiqueListeDAO caracteristiqueListeDAO = new CaracteristiqueListeDAO(pContext);
        ModificateurListeDAO modificateurListeDAO = new ModificateurListeDAO(pContext);

        caracteristiqueListeDAO.open();
        ArrayList<CaracteristiqueListe> caracteristiqueListes = caracteristiqueListeDAO.getAllCaracList(nomUnivers);
        caracteristiqueListeDAO.close();

        modificateurListeDAO.open();
        for(CaracteristiqueListe caracteristiqueListe : caracteristiqueListes){
            caracteristiqueListe.setLinkedModificateur(modificateurListeDAO.getAllModList(caracteristiqueListe.getListeId()));
        }
        modificateurListeDAO.close();

        // caracLists element
        Element caracLists = doc.createElement("CaracteristiqueListes");
        rootElement.appendChild(caracLists);

        // caracList elements
        for(CaracteristiqueListe caracteristiqueListe : caracteristiqueListes){
            Element caracList = doc.createElement("CaracteristiqueListe");
            caracLists.appendChild(caracList);
            caracList.setAttribute("nom", caracteristiqueListe.getNom());
            for(ModificateurListe modificateurListe : caracteristiqueListe.getLinkedModificateur()){
                Element modList = doc.createElement("ModificateurListe");
                caracList.appendChild(modList);
                modList.setAttribute("nom",modificateurListe.getNomMod());
            }
        }
    }

    private void compListFeeder(Document doc, Element rootElement){
        CompetenceListeDAO competenceListeDAO = new CompetenceListeDAO(pContext);

        competenceListeDAO.open();
        ArrayList<CompetenceListe> competenceListes = competenceListeDAO.getAllCompetence(nomUnivers);
        competenceListeDAO.close();

        //competenceLists element
        Element compLists = doc.createElement("CompetenceListes");
        rootElement.appendChild(compLists);

        //competenceList elements
        for(CompetenceListe competenceListe : competenceListes){
            Element compList = doc.createElement("CompetenceListe");
            compLists.appendChild(compList);
            compList.setAttribute("nom", competenceListe.getNom());
        }
    }

    private void jaugeListFeeder(Document doc, Element rootElement){
        JaugeListeDAO jaugeListeDAO = new JaugeListeDAO(pContext);

        jaugeListeDAO.open();
        ArrayList<JaugeListe> jaugeListes = jaugeListeDAO.getAllJaugeListe(nomUnivers);
        jaugeListeDAO.close();


        //jaugeLists element
        Element jaugeLists = doc.createElement("JaugeLists");
        rootElement.appendChild(jaugeLists);

        //jaugeList elements
        for(JaugeListe jaugeListe : jaugeListes){
            Element jaugeList = doc.createElement("JaugeListe");
            jaugeLists.appendChild(jaugeList);
            jaugeList.setAttribute("nom", jaugeListe.getNom());
            jaugeList.setAttribute("min", Integer.toString(jaugeListe.getMin()));
            jaugeList.setAttribute("max", Integer.toString(jaugeListe.getMax()));
        }
    }

    private void utilListFeeder(Document doc, Element rootElement){
        UtilitaireListeDAO utilitaireListeDAO = new UtilitaireListeDAO(pContext);

        utilitaireListeDAO.open();
        ArrayList<UtilitaireListe> utilitaireListes = utilitaireListeDAO.getAllUtilitaireListe(nomUnivers);
        utilitaireListeDAO.close();

        //utilitaireLists element
        Element utilLists = doc.createElement("UtilitaireListes");
        rootElement.appendChild(utilLists);

        //utilitaireList elements
        for(UtilitaireListe utilitaireListe : utilitaireListes){
            Element utilList = doc.createElement("UtilitaireListe");
            utilLists.appendChild(utilList);
            utilList.setAttribute("nom",utilitaireListe.getNom());
        }
    }

    private File getAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS), albumName);
        if (!file.mkdirs()) {
            //display a message?
        }

        return file;
    }

}
