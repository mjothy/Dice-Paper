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
import edu.jdr.DicePaper.models.DAO.Valeur.ModificateurValeurDAO;
import edu.jdr.DicePaper.models.table.Valeur.ModificateurValeur;

/**
 * Created by mario on 20/02/14.
 */
public class UpdateModifDialog extends DialogFragment {
    private EditText name = null;
    private ModificateurValeur modificateurValeur = null;

    public static UpdateModifDialog newInstance(int title, ModificateurValeur modificateurValeur){
        UpdateModifDialog dialog = new UpdateModifDialog();
        Bundle args = new Bundle();
        args.putInt("title", title);
        dialog.setArguments(args);
        dialog.modificateurValeur = modificateurValeur;
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.create_carac_valeur_dialog, container, false);
        Button cancel = (Button) v.findViewById(R.id.cancel);
        Button validate = (Button) v.findViewById(R.id.validate);
        name = (EditText) v.findViewById(R.id.name);
        name.setText(String.valueOf(modificateurValeur.getValue()));
        validate.setOnClickListener(validateListener);
        cancel.setOnClickListener(cancelListener);
        getDialog().setTitle(getString(R.string.update)+" "+modificateurValeur.getRelatedList().getNomMod());
        return v;
    }

    private View.OnClickListener validateListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String tempError = name.getText().toString();
            long result = -1;
            if(!tempError.isEmpty()){
                float modValeur = Float.valueOf(tempError);
                modificateurValeur.setValue(modValeur);
                ModificateurValeurDAO manager = new ModificateurValeurDAO(getActivity());
                manager.open();
                result = manager.updateModificateurValeur(modificateurValeur);
                manager.close();
            }
            if(result != -1){
                Toast.makeText(getActivity(), getText(R.string.successModifModif), Toast.LENGTH_SHORT).show();
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