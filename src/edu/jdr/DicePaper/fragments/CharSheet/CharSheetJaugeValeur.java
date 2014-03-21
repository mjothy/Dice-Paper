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
import edu.jdr.DicePaper.fragments.CharSheet.UpdateDialog.UpdateJaugeDialog;
import edu.jdr.DicePaper.models.DAO.Valeur.JaugeValeurDAO;
import edu.jdr.DicePaper.models.table.FichePersonnage;
import edu.jdr.DicePaper.models.table.Valeur.JaugeValeur;
import edu.jdr.DicePaper.utils.JaugeValeurAdapter;

import java.util.ArrayList;

/**
 * Created by paulyves on 2/25/14.
 */
public class CharSheetJaugeValeur extends Fragment {
    private String universeName;
    private String charName;

    private ListView jaugeValeurView;

    /**
     * Method to instanciate this fragment
     * Only this method should be used to create this kind of fragment
     * @return
     */
    public static CharSheetJaugeValeur newInstance(){
        CharSheetJaugeValeur fragment = new CharSheetJaugeValeur();
        Bundle args = new Bundle();
        // insert here some argument if you want them in bundle
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.char_sheet_def_jauge_list, container, false);
        universeName = getActivity().getIntent().getExtras().getString("universeName");
        charName = getActivity().getIntent().getExtras().getString("charName");
        TextView title = (TextView) v.findViewById(R.id.univTitle);
        title.setText(charName+" ("+universeName+")");
        jaugeValeurView = (ListView) v.findViewById(R.id.jaugeList);
        setJauge();
        return v;
    }

    public void setJauge(){
        FichePersonnage fichePersonnage = new FichePersonnage(charName,0,universeName);
        JaugeValeurDAO manager = new JaugeValeurDAO(getActivity());
        manager.open();
        manager.initializeNewValues(fichePersonnage);
        ArrayList<JaugeValeur> jaugeList = manager.getAllJaugeValeur(charName);
        manager.close();
        JaugeValeurAdapter<JaugeValeur> adapter = new JaugeValeurAdapter<JaugeValeur>(getActivity(),
                R.layout.list_jauge_valeur_component, jaugeList);
        jaugeValeurView.setAdapter(adapter);
    }

    public void callBackModifyJauge(JaugeValeur jauge){
        FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();
        Fragment prev = getActivity().getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        UpdateJaugeDialog dialog = UpdateJaugeDialog.newInstance(R.string.modifJauge, jauge);
        dialog.show(getActivity().getFragmentManager(),"dialog");
    }

    public void callBackSaveJauge(JaugeValeur jauge){
        JaugeValeurDAO manager = new JaugeValeurDAO(getActivity());
        manager.open();
        manager.updateJaugeValeur(jauge);
        manager.close();
    }
}