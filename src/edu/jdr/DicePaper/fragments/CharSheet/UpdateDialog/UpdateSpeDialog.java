package edu.jdr.DicePaper.fragments.CharSheet.UpdateDialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import edu.jdr.DicePaper.R;
import edu.jdr.DicePaper.models.DAO.Valeur.SpecialisationDAO;
import edu.jdr.DicePaper.models.table.Valeur.Specialisation;

/**
 * Created by paulyves on 2/25/14.
 */
public class UpdateSpeDialog extends DialogFragment {
    private EditText name = null;
    private Specialisation specialisation = null;

    public static UpdateSpeDialog newInstance(int title, Specialisation specialisation){
        UpdateSpeDialog dialog = new UpdateSpeDialog();
        Bundle args = new Bundle();
        args.putInt("title", title);
        dialog.setArguments(args);
        dialog.specialisation = specialisation;
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.create_carac_valeur_dialog, container, false);
        Button cancel = (Button) v.findViewById(R.id.cancel);
        Button validate = (Button) v.findViewById(R.id.validate);
        name = (EditText) v.findViewById(R.id.name);
        name.setText(String.valueOf(specialisation.getValue()));
        validate.setOnClickListener(validateListener);
        cancel.setOnClickListener(cancelListener);
        getDialog().setTitle(getString(R.string.update)+" "+specialisation.getNom());
        return v;
    }

    private View.OnClickListener validateListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String tempError = name.getText().toString();
            long result = -1;
            if(!tempError.isEmpty()){
                float value = Float.valueOf(tempError);
                specialisation.setValue(value);
                SpecialisationDAO manager = new SpecialisationDAO(getActivity());
                manager.open();
                result = manager.updateSpecialisation(specialisation);
                manager.close();
            }
            if(result != -1){
                Toast.makeText(getActivity(), getText(R.string.successModifSpe), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), getText(R.string.errorModif), Toast.LENGTH_SHORT).show();
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