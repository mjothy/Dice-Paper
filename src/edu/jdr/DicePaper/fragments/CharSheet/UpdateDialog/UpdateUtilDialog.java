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
import edu.jdr.DicePaper.models.DAO.Valeur.UtilitaireValeurDAO;
import edu.jdr.DicePaper.models.table.Valeur.UtilitaireValeur;

/**
 * Created by paulyves on 2/22/14.
 */
public class UpdateUtilDialog extends DialogFragment {
    private EditText value = null;
    private UtilitaireValeur utilitaireValeur = null;

    public static UpdateUtilDialog newInstance(int title, UtilitaireValeur utilitaireValeur){
        UpdateUtilDialog dialog = new UpdateUtilDialog();
        Bundle args = new Bundle();
        args.putInt("title", title);
        dialog.setArguments(args);
        dialog.utilitaireValeur = utilitaireValeur;
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.create_component_dialog, container, false);
        Button cancel = (Button) v.findViewById(R.id.cancel);
        Button validate = (Button) v.findViewById(R.id.validate);
        value = (EditText) v.findViewById(R.id.name);
        value.setText(utilitaireValeur.getValue());
        validate.setOnClickListener(validateListener);
        cancel.setOnClickListener(cancelListener);
        getDialog().setTitle(getString(R.string.update)+" "+utilitaireValeur.getRelatedList().getNom());
        return v;
    }

    private View.OnClickListener validateListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String tempValue = value.getText().toString();
            long result = -1;
            if(!tempValue.isEmpty()){
                utilitaireValeur.setValue(tempValue);
                UtilitaireValeurDAO manager = new UtilitaireValeurDAO(getActivity());
                manager.open();
                result = manager.updateUtilitaireValeur(utilitaireValeur);
                manager.close();
            }
            if(result != -1){
                Toast.makeText(getActivity(), getText(R.string.successModifUtil), Toast.LENGTH_SHORT).show();
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