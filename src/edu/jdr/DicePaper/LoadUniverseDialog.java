package edu.jdr.DicePaper;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * This class is user to list the existing universe
 * the goals can be loading (to modify), deleting or entering (to play)
 * Created by paulyves on 1/21/14.
 */
public class LoadUniverseDialog extends DialogFragment {
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
    public static LoadUniverseDialog newinstance(int title, int goal){
        LoadUniverseDialog dialog = new LoadUniverseDialog();
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
        ArrayAdapter<String> ad = new ArrayAdapter<String>(getActivity(), R.layout.loaduniversedialog, R.id.univList);
        ad.addAll(listUniv);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title)
                .setSingleChoiceItems(ad, -1, this.loadListener);
        return builder.create();
    }

    private Dialog.OnClickListener loadListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
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