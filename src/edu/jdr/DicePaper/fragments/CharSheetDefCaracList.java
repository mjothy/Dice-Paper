package edu.jdr.DicePaper.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;
import edu.jdr.DicePaper.R;
import edu.jdr.DicePaper.models.DAO.CaracteristiqueListeDAO;
import edu.jdr.DicePaper.models.DAO.ModificateurListeDAO;
import edu.jdr.DicePaper.models.table.CaracteristiqueListe;
import edu.jdr.DicePaper.models.table.ModificateurListe;
import edu.jdr.DicePaper.utils.SimpleExpListAdapter;

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
            carac.setLinkedModificateur(modManager.getAllModList(carac.getCaracteristiqueListeId()));
        }
        modManager.close();

        HashMap<CaracteristiqueListe,List<ModificateurListe>> theMap = new HashMap<CaracteristiqueListe, List<ModificateurListe>>();
        for(CaracteristiqueListe carac : caracList){
            theMap.put(carac, carac.getLinkedModificateur());
        }
        SimpleExpListAdapter<CaracteristiqueListe, ModificateurListe> adapter = new SimpleExpListAdapter<CaracteristiqueListe, ModificateurListe>(getActivity(), theMap);

        caracListView.setAdapter(adapter);
    }
}