package edu.jdr.DicePaper.fragments.CharSheet;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import edu.jdr.DicePaper.R;
import edu.jdr.DicePaper.fragments.CharSheet.UpdateDialog.CreateInventaireDialog;
import edu.jdr.DicePaper.fragments.CharSheet.UpdateDialog.UpdateInventaireDialog;
import edu.jdr.DicePaper.fragments.CharSheet.UpdateDialog.UpdateUtilDialog;
import edu.jdr.DicePaper.fragments.CharSheetDef.CreateDialog.CreateUtilitaireDialog;
import edu.jdr.DicePaper.models.DAO.Liste.CaracteristiqueListeDAO;
import edu.jdr.DicePaper.models.DAO.Valeur.EquipementDAO;
import edu.jdr.DicePaper.models.DAO.Valeur.UtilitaireValeurDAO;
import edu.jdr.DicePaper.models.table.FichePersonnage;
import edu.jdr.DicePaper.models.table.Liste.CaracteristiqueListe;
import edu.jdr.DicePaper.models.table.Valeur.CompetenceValeur;
import edu.jdr.DicePaper.models.table.Valeur.Equipement;
import edu.jdr.DicePaper.models.table.Valeur.Specialisation;
import edu.jdr.DicePaper.models.table.Valeur.UtilitaireValeur;
import edu.jdr.DicePaper.utils.CompValeurExpListAdapter;
import edu.jdr.DicePaper.utils.InventaireAdapter;
import edu.jdr.DicePaper.utils.UtilitaireValeurAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by mario on 04/03/14.
 */
public class CharSheetInventory extends Fragment {
    private String universeName;
    private String charName;
    private Button addEquipement;
    private ArrayList<Equipement> invList;
    private ListView equipementView;

    /**
     * Method to instanciate this fragment
     * Only this method should be used to create this kind of fragment
     * @return
     */
    public static CharSheetInventory newInstance(){
        CharSheetInventory fragment = new CharSheetInventory();
        Bundle args = new Bundle();
        // insert here some argument if you want them in bundle
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.char_sheet_inv, container, false);
        universeName = getActivity().getIntent().getExtras().getString("universeName");
        charName = getActivity().getIntent().getExtras().getString("charName");
        TextView title = (TextView) v.findViewById(R.id.univTitle);
        title.setText(charName+" ("+universeName+")");
        addEquipement= (Button) v.findViewById(R.id.addEquipement);
        addEquipement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment prev = getFragmentManager().findFragmentByTag("dialog");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);

                DialogFragment dial = null;
                dial = CreateInventaireDialog.newInstance(R.string.addEquipement, universeName, charName);
                dial.show(getFragmentManager(),"dialog");
            }
        });
        equipementView = (ListView) v.findViewById(R.id.inventaire);
        setInventaire();
        return v;
    }

    public void setInventaire(){
        FichePersonnage fichePersonnage = new FichePersonnage(charName,0,universeName);
        EquipementDAO manager = new EquipementDAO(getActivity());
        manager.open();
        invList = manager.getAllEquipement(charName);
        manager.close();

        InventaireAdapter<Equipement> inventaireAdapter = new InventaireAdapter<Equipement>(getActivity(),
                R.layout.list_utilitaire_valeur_component,invList);

        equipementView.setAdapter(inventaireAdapter);

    }

    public void callBackModifyUtil(final Equipement equipement){
        FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();
        Fragment prev = getActivity().getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        UpdateInventaireDialog dialog = UpdateInventaireDialog.newInstance(R.string.updateEquipement, equipement);
        dialog.show(getActivity().getFragmentManager(),"dialog");
    }

    public void callBackDeleteEquipement(final Equipement equipement){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getActivity().getString(R.string.askDeleteConfirmation)+" "+ equipement.getNom() + "?");
        builder.setPositiveButton(getActivity().getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EquipementDAO manager = new EquipementDAO(getActivity());
                manager.open();
                manager.delete(equipement.getKey());
                manager.close();
                setInventaire();
            }
        });
        builder.setNegativeButton(getActivity().getString(R.string.no), null);
        builder.show();
    }
}