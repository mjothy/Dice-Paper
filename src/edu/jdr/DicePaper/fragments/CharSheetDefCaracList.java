package edu.jdr.DicePaper.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;
import edu.jdr.DicePaper.R;
import edu.jdr.DicePaper.models.DAO.Liste.CaracteristiqueListeDAO;
import edu.jdr.DicePaper.models.DAO.Liste.ModificateurListeDAO;
import edu.jdr.DicePaper.models.table.Liste.CaracteristiqueListe;
import edu.jdr.DicePaper.models.table.Liste.ModificateurListe;
import edu.jdr.DicePaper.models.table.Valeur.CaracteristiqueValeur;
import edu.jdr.DicePaper.utils.CaracDefExpListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by paulyves on 2/8/14.
 */
public class CharSheetDefCaracList extends Fragment {
    private String universeName;

    private ArrayList<CaracteristiqueListe> caracList;
    private ExpandableListView caracListView;
    private int componentId;
    private int componentPosition;

    /**
     * Method to instanciate this fragment
     * Only this method should be used to create this kind of fragment
     * @return
     */
    public static CharSheetDefCaracList newInstance(){
        CharSheetDefCaracList fragment = new CharSheetDefCaracList();
        Bundle args = new Bundle();
        // insert here some argument if you want them in bundle
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.char_sheet_def_carac_list, container, false);
        universeName = getActivity().getIntent().getExtras().getString("universeName");
        TextView title = (TextView) v.findViewById(R.id.univTitle);
        title.setText(universeName);
        caracListView = (ExpandableListView) v.findViewById(R.id.caracList);
        setCarac();
        return v;
    }

    public void setCarac(){
        CaracteristiqueListeDAO caracManager = new CaracteristiqueListeDAO(getActivity());
        caracManager.open();
        caracList = caracManager.getAllCaracList(universeName);
        caracManager.close();
        ModificateurListeDAO modManager = new ModificateurListeDAO(getActivity());
        modManager.open();
        for(CaracteristiqueListe carac : caracList){
            carac.setLinkedModificateur(modManager.getAllModList(carac.getListeId()));
        }
        modManager.close();

        HashMap<CaracteristiqueListe,List<ModificateurListe>> theMap = new HashMap<CaracteristiqueListe, List<ModificateurListe>>();
        for(CaracteristiqueListe carac : caracList){
            theMap.put(carac, carac.getLinkedModificateur());
        }
        CaracDefExpListAdapter<CaracteristiqueListe, ModificateurListe> adapter = new CaracDefExpListAdapter<CaracteristiqueListe, ModificateurListe>(getActivity(), theMap);

        caracListView.setAdapter(adapter);
    }

    public void callBackDeleteCarac(final CaracteristiqueListe carac){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getActivity().getString(R.string.askDeleteConfirmation)+" "+ carac.getNom() + "?");
        builder.setPositiveButton(getActivity().getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CaracteristiqueListeDAO manager = new CaracteristiqueListeDAO(getActivity());
                manager.open();
                manager.deleteCarac(carac.getListeId());
                manager.close();
                setCarac();
            }
        });
        builder.setNegativeButton(getActivity().getString(R.string.no), null);
        builder.show();
    }

    public void callBackDeleteMod(final ModificateurListe modificateur){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getActivity().getString(R.string.askDeleteConfirmation)+" "+ modificateur.getNomMod()+ "?");
        builder.setPositiveButton(getActivity().getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ModificateurListeDAO manager = new ModificateurListeDAO(getActivity());
                manager.open();
                manager.delete(modificateur.getModificateurListeId());
                manager.close();
                setCarac();
            }
        });
        builder.setNegativeButton(getActivity().getString(R.string.no), null);
        builder.show();
    }
}