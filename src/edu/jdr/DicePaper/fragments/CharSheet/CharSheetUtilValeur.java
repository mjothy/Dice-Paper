package edu.jdr.DicePaper.fragments.CharSheet;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import edu.jdr.DicePaper.R;
import edu.jdr.DicePaper.fragments.CharSheet.UpdateDialog.UpdateUtilDialog;
import edu.jdr.DicePaper.models.DAO.Valeur.UtilitaireValeurDAO;
import edu.jdr.DicePaper.models.table.FichePersonnage;
import edu.jdr.DicePaper.models.table.Valeur.UtilitaireValeur;
import edu.jdr.DicePaper.utils.UtilitaireValeurAdapter;

import java.util.ArrayList;

/**
 * Created by paulyves on 2/22/14.
 */
public class CharSheetUtilValeur extends Fragment {
    private String universeName;
    private String charName;

    private ListView utilValeurView;

    /**
     * Method to instanciate this fragment
     * Only this method should be used to create this kind of fragment
     * @return
     */
    public static CharSheetUtilValeur newInstance(){
        CharSheetUtilValeur fragment = new CharSheetUtilValeur();
        Bundle args = new Bundle();
        // insert here some argument if you want them in bundle
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.char_sheet_def_util_list, container, false);
        universeName = getActivity().getIntent().getExtras().getString("universeName");
        charName = getActivity().getIntent().getExtras().getString("charName");
        TextView title = (TextView) v.findViewById(R.id.univTitle);
        title.setText(charName+" ("+universeName+")");
        utilValeurView = (ListView) v.findViewById(R.id.utilList);
        setUtil();
        return v;
    }

    public void setUtil(){
        FichePersonnage fichePersonnage = new FichePersonnage(charName,0,universeName);
        UtilitaireValeurDAO manager = new UtilitaireValeurDAO(getActivity());
        manager.open();
        manager.initializeNewValues(fichePersonnage);
        ArrayList<UtilitaireValeur> utilList = manager.getAllUtilitaireValeur(charName);
        manager.close();
        UtilitaireValeurAdapter<UtilitaireValeur> adapter = new UtilitaireValeurAdapter<UtilitaireValeur>(getActivity(),
                                                                    R.layout.list_utilitaire_valeur_component, utilList);
        utilValeurView.setAdapter(adapter);
    }

    public void callBackModifyUtil(final UtilitaireValeur util){
        FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();
        Fragment prev = getActivity().getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        UpdateUtilDialog dialog = UpdateUtilDialog.newInstance(R.string.modifUtil, util);
        dialog.show(getActivity().getFragmentManager(),"dialog");
    }
}