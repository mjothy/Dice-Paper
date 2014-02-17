package edu.jdr.DicePaper.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import edu.jdr.DicePaper.R;
import edu.jdr.DicePaper.activity.UniversDefinition;
import edu.jdr.DicePaper.activity.UniversEnter;
import edu.jdr.DicePaper.models.DAO.UniversDAO;

import java.util.ArrayList;

/**
 * This class is user to list the existing universe
 * the goals can be loading (to modify), deleting or entering (to play)
 * Created by paulyves on 1/21/14.
 */
public class ListUniverseDialog extends DialogFragment {
    ArrayList<String> listUniv = null;
    int goal;
    public static final int LOAD = 1;
    public static final int DELETE = 2;
    public static final int ENTER = 3;
    private String univToDelete;

    /**
     *
     * @param title title of the list
     * @param goal the goal of the list, listener will do different thing depending on it
     * @return
     */
    public static ListUniverseDialog newinstance(int title, int goal){
        ListUniverseDialog dialog = new ListUniverseDialog();
        Bundle args = new Bundle();
        args.putInt("title", title);
        dialog.setArguments(args);
        dialog.goal = goal;
        return dialog;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        int title = getArguments().getInt("title");

        UniversDAO univManager = new UniversDAO(getActivity());
        univManager.open();
        listUniv = univManager.getAllUnivers();
        univManager.close();
        ArrayAdapter<String> ad = new ArrayAdapter<String>(getActivity(), R.layout.list_univers_dialog, R.id.univList);
        ad.addAll(listUniv);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title)
                .setSingleChoiceItems(ad, -1, this.loadListener);
        return builder.create();
    }

    private Dialog.OnClickListener loadListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            //Bouton de modification de l'univers
            if(goal == LOAD){
                Intent universeBuilder = new Intent(getActivity(), UniversDefinition.class);
                universeBuilder.putExtra("universeName", listUniv.get(which));
                startActivity(universeBuilder);
            }
            //Bouton de supression de l'univers
            if(goal == DELETE){
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Êtes vous certain de vouloir supprimer " + listUniv.get(which) + "?");
                builder.setPositiveButton("oui", deleteConfirmListener);
                builder.setNegativeButton("non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });
                univToDelete = listUniv.get(which);
                builder.show();
            }
            //Bouton d'entree dans l'univers
            if(goal == ENTER){
                Intent universeEnter = new Intent(getActivity(), UniversEnter.class);
                universeEnter.putExtra("universeName", listUniv.get(which));
                startActivity(universeEnter);
            }
        }
    };

    private Dialog.OnClickListener deleteConfirmListener = new Dialog.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            UniversDAO univManager = new UniversDAO(getActivity());
            univManager.open();
            int success = univManager.deleteUnivers(univToDelete);
            univManager.close();
            if(success>0){
                Toast.makeText(getActivity(), "Vous avez bien supprimé "+univToDelete, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Erreur sur la suppression de "+univToDelete, Toast.LENGTH_SHORT).show();
            }
            univToDelete = null;
            dismiss();
        }
    };

}