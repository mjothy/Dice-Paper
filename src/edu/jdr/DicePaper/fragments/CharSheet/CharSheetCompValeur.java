package edu.jdr.DicePaper.fragments.CharSheet;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;
import edu.jdr.DicePaper.R;
import edu.jdr.DicePaper.fragments.CharSheet.UpdateDialog.UpdateCompetenceDialog;
import edu.jdr.DicePaper.fragments.CharSheet.UpdateDialog.UpdateSpeDialog;
import edu.jdr.DicePaper.models.DAO.Valeur.CompetenceValeurDAO;
import edu.jdr.DicePaper.models.DAO.Valeur.SpecialisationDAO;
import edu.jdr.DicePaper.models.table.FichePersonnage;
import edu.jdr.DicePaper.models.table.Valeur.CompetenceValeur;
import edu.jdr.DicePaper.models.table.Valeur.Specialisation;
import edu.jdr.DicePaper.utils.CompValeurExpListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by paulyves on 2/25/14.
 */
public class CharSheetCompValeur extends Fragment {
    private String universeName;
    private String charName;

    private ExpandableListView compValeurView;

    /**
     * Method to instantiate this fragment
     * Only this method should be used to create this kind of fragment
     * @return
     */
    public static CharSheetCompValeur newInstance(){
        CharSheetCompValeur fragment = new CharSheetCompValeur();
        Bundle args = new Bundle();
        // insert here some argument if you want them in bundle
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.char_sheet_comp_list, container, false);
        universeName = getActivity().getIntent().getExtras().getString("universeName");
        charName = getActivity().getIntent().getExtras().getString("charName");
        TextView title = (TextView) v.findViewById(R.id.univTitle);
        title.setText(charName+" ("+universeName+")");
        compValeurView = (ExpandableListView) v.findViewById(R.id.skillList);
        setComp();
        return v;
    }

    public void setComp(){
        CompetenceValeurDAO manager = new CompetenceValeurDAO(getActivity());
        FichePersonnage fichePersonnage = new FichePersonnage(charName,0,universeName);
        manager.open();
        manager.initializeNewValues(fichePersonnage);
        ArrayList<CompetenceValeur> compList = manager.getAllCompetenceValeur(charName);
        manager.close();
        SpecialisationDAO speManager = new SpecialisationDAO(getActivity());
        speManager.open();
        for(CompetenceValeur comp : compList){
            comp.setLinkedSpecialisation(speManager.getAllSpecialisation(comp.getKey()));
        }
        speManager.close();

        TreeMap<CompetenceValeur,List<Specialisation>> theMap = new TreeMap<CompetenceValeur, List<Specialisation>>();
        for(CompetenceValeur comp : compList){
            theMap.put(comp, comp.getLinkedSpecialisation());
        }
        CompValeurExpListAdapter<CompetenceValeur, Specialisation> adapter = new CompValeurExpListAdapter<CompetenceValeur, Specialisation>(getActivity(), theMap);

        compValeurView.setAdapter(adapter);
    }

    public void callBackModifyComp(CompetenceValeur comp){
        FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();
        Fragment prev = getActivity().getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        UpdateCompetenceDialog dialog = UpdateCompetenceDialog.newInstance(R.string.modifComp, comp);
        dialog.show(getActivity().getFragmentManager(),"dialog");
    }

    public void callBackAddSpe(CompetenceValeur comp){
        FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();
        Fragment prev = getActivity().getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        CreateSpeDialog dialog = CreateSpeDialog.newInstance(R.string.addSpe, comp.getKey());
        dialog.show(getActivity().getFragmentManager(),"dialog");
    }

    public void callBackModifySpe(Specialisation spe){
        FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();
        Fragment prev = getActivity().getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        UpdateSpeDialog dialog = UpdateSpeDialog.newInstance(R.string.modifSpe, spe);
        dialog.show(getActivity().getFragmentManager(),"dialog");
    }

    public void callBackDeleteSpe(final Specialisation spe){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getActivity().getString(R.string.askDeleteConfirmation)+" "+ spe.getNom() + "?");
        builder.setPositiveButton(getActivity().getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SpecialisationDAO manager = new SpecialisationDAO(getActivity());
                manager.open();
                manager.delete(spe.getKey());
                manager.close();
                setComp();
            }
        });
        builder.setNegativeButton(getActivity().getString(R.string.no), null);
        builder.show();
    }
}