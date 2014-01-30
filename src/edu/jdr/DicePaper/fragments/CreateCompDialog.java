package edu.jdr.DicePaper.fragments;

import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import edu.jdr.DicePaper.R;
import edu.jdr.DicePaper.models.CaracteristiqueListe;
import edu.jdr.DicePaper.models.CaracteristiqueListeDAO;
import edu.jdr.DicePaper.models.CompetenceListe;
import edu.jdr.DicePaper.models.CompetenceListeDAO;

/**
 * Created by paulyves on 1/30/14.
 */
public class CreateCompDialog extends DialogFragment {
    private Button cancel = null;
    private Button validate = null;
    private EditText name = null;
    private String univName = null;

    public static CreateCompDialog newInstance(int title, String univName){
        CreateCompDialog dialog = new CreateCompDialog();
        Bundle args = new Bundle();
        args.putInt("title", title);
        dialog.setArguments(args);
        dialog.univName = univName;
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.create_component_dialog, container, false);
        cancel = (Button) v.findViewById(R.id.cancel);
        validate = (Button) v.findViewById(R.id.validate);
        name = (EditText) v.findViewById(R.id.name);
        validate.setOnClickListener(validateListener);
        cancel.setOnClickListener(cancelListener);
        getDialog().setTitle(getString(R.string.addCharac));
        return v;
    }

    private View.OnClickListener validateListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String compName = name.getText().toString();
            long result = -1;
            if(!compName.isEmpty()){
                CompetenceListe carac = new CompetenceListe(compName, univName);
                CompetenceListeDAO caracManager = new CompetenceListeDAO(getActivity());
                caracManager.open();
                result = caracManager.createCompetenceListe(carac);
                caracManager.close();
            }
            if(result != -1){
                Toast.makeText(getActivity(), getText(R.string.successCreateComp), Toast.LENGTH_SHORT).show();
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