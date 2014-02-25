package edu.jdr.DicePaper.fragments.CharSheet;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import edu.jdr.DicePaper.R;
import edu.jdr.DicePaper.activity.CharSheetSwipper;
import edu.jdr.DicePaper.models.DAO.FichePersonnageDAO;
import edu.jdr.DicePaper.models.table.FichePersonnage;

import java.util.ArrayList;

/**
 * Created by mario on 20/02/14.
 */
public class ListCharSheetDialog extends DialogFragment {
    ArrayList<FichePersonnage> listCharSheet = null;
    int goal;
    public static final int LOAD = 1;
    public static final int DELETE = 2;
    private String charSheetToDelete;
    private String univers;

    /**
     *
     * @param title title of the list
     * @param goal the goal of the list, listener will do different thing depending on it
     * @return
     */
    public static ListCharSheetDialog newinstance(int title, int goal, String univers){
        ListCharSheetDialog dialog = new ListCharSheetDialog();
        Bundle args = new Bundle();
        args.putInt("title", title);
        dialog.setArguments(args);
        dialog.goal = goal;
        dialog.univers = univers;
        return dialog;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        int title = getArguments().getInt("title");

        FichePersonnageDAO fichePersonnageDAO = new FichePersonnageDAO(getActivity());
        fichePersonnageDAO.open();
        listCharSheet = fichePersonnageDAO.getAllFiche(univers);
        fichePersonnageDAO.close();
        ArrayAdapter<FichePersonnage> ad = new ArrayAdapter<FichePersonnage>(getActivity(), R.layout.list_univers_dialog, R.id.univList);
        ad.addAll(listCharSheet);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title)
                .setSingleChoiceItems(ad, -1, this.loadListener);
        return builder.create();
    }

    private Dialog.OnClickListener loadListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            //Bouton de modification de la fiche de perso
            if(goal == LOAD){
                Intent charSheetBuilder = new Intent(getActivity(), CharSheetSwipper.class);
                charSheetBuilder.putExtra("universeName", listCharSheet.get(which).getNomUnivers());
                charSheetBuilder.putExtra("charName", listCharSheet.get(which).getNomFiche());
                startActivity(charSheetBuilder);
            }
            //Bouton de supression de la fiche de perso
            if(goal == DELETE){
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Êtes vous certain de vouloir supprimer " + listCharSheet.get(which) + "?");
                builder.setPositiveButton("oui", deleteConfirmListener);
                builder.setNegativeButton("non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });
                charSheetToDelete = listCharSheet.get(which).getNomFiche();
                builder.show();
            }
        }
    };

    private Dialog.OnClickListener deleteConfirmListener = new Dialog.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            FichePersonnageDAO charSheetManager = new FichePersonnageDAO(getActivity());
            charSheetManager.open();
            int success = charSheetManager.deleteFichePerso(charSheetToDelete);
            charSheetManager.close();
            if(success>0){
                Toast.makeText(getActivity(), "Vous avez bien supprimé " + charSheetToDelete, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Erreur sur la suppression de "+charSheetToDelete, Toast.LENGTH_SHORT).show();
            }
            charSheetToDelete = null;
            dismiss();
        }
    };

}