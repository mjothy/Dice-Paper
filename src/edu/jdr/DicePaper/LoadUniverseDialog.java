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

import java.util.ArrayList;

/**
 * Created by paulyves on 1/21/14.
 */
public class LoadUniverseDialog extends DialogFragment {

    public static LoadUniverseDialog newinstance(int title){
        LoadUniverseDialog dialog = new LoadUniverseDialog();
        Bundle args = new Bundle();
        args.putInt("title", title);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.loaduniversedialog, container, false);

        return v;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        UniversDAO univManager = new UniversDAO(getActivity());
        univManager.open();
        ArrayList<String> list = univManager.getAllUnivers();
        univManager.close();
        ArrayAdapter<String> ad = new ArrayAdapter<String>(getActivity(), R.layout.loaduniversedialog, R.id.univList);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.listUniverse)
                .setSingleChoiceItems(ad, 1, this.loadListener);
        return getDialog();
    }

    private Dialog.OnClickListener loadListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

        }
    };
}