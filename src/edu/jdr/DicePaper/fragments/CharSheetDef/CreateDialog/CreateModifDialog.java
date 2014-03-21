package edu.jdr.DicePaper.fragments.CharSheetDef.CreateDialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import edu.jdr.DicePaper.R;
import edu.jdr.DicePaper.activity.CharSheetDefSwipper;
import edu.jdr.DicePaper.models.DAO.Liste.ModificateurListeDAO;
import edu.jdr.DicePaper.models.table.Liste.ModificateurListe;

/**
 * Dialog class called inside CharSheetDefSwipper activity under CharSheetDefCaracList fragment
 * Called by the button defined by the expandableListView adapter
 * Created by paulyves on 2/9/14.
 */
public class CreateModifDialog extends DialogFragment {
    private EditText name = null;
    private int caracListId;

    public static CreateModifDialog newInstance(int title, int caracListId){
        CreateModifDialog dialog = new CreateModifDialog();
        Bundle args = new Bundle();
        args.putInt("title", title);
        dialog.setArguments(args);
        dialog.caracListId = caracListId;
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.create_component_dialog, container, false);
        Button cancel = (Button) v.findViewById(R.id.cancel);
        Button validate = (Button) v.findViewById(R.id.validate);
        name = (EditText) v.findViewById(R.id.name);
        validate.setOnClickListener(validateListener);
        cancel.setOnClickListener(cancelListener);
        getDialog().setTitle(getString(R.string.addModif));
        return v;
    }

    private View.OnClickListener validateListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String modName = name.getText().toString();
            long result = -1;
            if(!modName.isEmpty()){
                ModificateurListe modificateur = new ModificateurListe(modName, caracListId);
                ModificateurListeDAO manager = new ModificateurListeDAO(getActivity());
                manager.open();
                result = manager.createModListe(modificateur);
                manager.close();
            }
            if(result != -1){
                Toast.makeText(getActivity(), getText(R.string.successCreateModif), Toast.LENGTH_SHORT).show();
                ((CharSheetDefSwipper)getActivity()).getFragCaracList().setCarac();
            } else {
                Toast.makeText(getActivity(), getText(R.string.errorCreate), Toast.LENGTH_SHORT).show();
            }
            dismiss();
        }
    };

    private View.OnClickListener cancelListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dismiss();
        }
    };
}