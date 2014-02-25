package edu.jdr.DicePaper.fragments.CharSheet;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;
import edu.jdr.DicePaper.R;
import edu.jdr.DicePaper.fragments.CharSheet.UpdateDialog.UpdateCaracDialog;
import edu.jdr.DicePaper.fragments.CharSheet.UpdateDialog.UpdateModifDialog;
import edu.jdr.DicePaper.models.DAO.Valeur.CaracteristiqueValeurDAO;
import edu.jdr.DicePaper.models.DAO.Valeur.ModificateurValeurDAO;
import edu.jdr.DicePaper.models.table.FichePersonnage;
import edu.jdr.DicePaper.models.table.Valeur.CaracteristiqueValeur;
import edu.jdr.DicePaper.models.table.Valeur.ModificateurValeur;
import edu.jdr.DicePaper.utils.CaracValeurExpListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by mario on 20/02/14.
 */
public class CharSheetCaracValeur extends Fragment {
    private String universeName;
    private String charName;

    private ArrayList<CaracteristiqueValeur> caracList;
    private ExpandableListView caracValeurView;

    /**
     * Method to instanciate this fragment
     * Only this method should be used to create this kind of fragment
     * @return
     */
    public static CharSheetCaracValeur newInstance(){
        CharSheetCaracValeur fragment = new CharSheetCaracValeur();
        Bundle args = new Bundle();
        // insert here some argument if you want them in bundle
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.char_sheet_def_carac_list, container, false);
        universeName = getActivity().getIntent().getExtras().getString("universeName");
        charName = getActivity().getIntent().getExtras().getString("charName");
        TextView title = (TextView) v.findViewById(R.id.univTitle);
        title.setText(charName+" ("+universeName+")");
        caracValeurView = (ExpandableListView) v.findViewById(R.id.caracList);
        setCarac();
        return v;
    }

    public void setCarac(){
        CaracteristiqueValeurDAO caracManager = new CaracteristiqueValeurDAO(getActivity());
        FichePersonnage fichePersonnage = new FichePersonnage(charName,0,universeName);
        caracManager.open();
        caracManager.initializeNewValues(fichePersonnage);
        caracList = caracManager.getAllCaracteristiqueValeur(charName);
        caracManager.close();
        ModificateurValeurDAO modManager = new ModificateurValeurDAO(getActivity());
        modManager.open();
        modManager.initializeNewValues(fichePersonnage);
        for(CaracteristiqueValeur carac : caracList){
            carac.setLinkedModificateur(modManager.getAllModificateurValeur(charName, carac.getRelatedList().getListeId()));
        }
        modManager.close();

        HashMap<CaracteristiqueValeur,List<ModificateurValeur>> theMap = new HashMap<CaracteristiqueValeur, List<ModificateurValeur>>();
        for(CaracteristiqueValeur carac : caracList){
            theMap.put(carac, carac.getLinkedModificateur());
        }
        CaracValeurExpListAdapter<CaracteristiqueValeur, ModificateurValeur> adapter = new CaracValeurExpListAdapter<CaracteristiqueValeur, ModificateurValeur>(getActivity(), theMap);

        caracValeurView.setAdapter(adapter);
    }

    public void callBackModifyCarac(final CaracteristiqueValeur carac){
        FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();
        Fragment prev = getActivity().getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        UpdateCaracDialog dialog = UpdateCaracDialog.newInstance(R.string.modifCarac, carac);
        dialog.show(getActivity().getFragmentManager(),"dialog");
    }

    public void callBackModifyModif(final ModificateurValeur modif){
        FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();
        Fragment prev = getActivity().getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        UpdateModifDialog dialog = UpdateModifDialog.newInstance(R.string.modifModif, modif);
        dialog.show(getActivity().getFragmentManager(),"dialog");
    }
}
