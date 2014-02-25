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
import edu.jdr.DicePaper.models.DAO.Valeur.CaracteristiqueValeurDAO;
import edu.jdr.DicePaper.models.table.Valeur.CaracteristiqueValeur;

/**
 * Created by mario on 20/02/14.
 */
public class UpdateCaracDialog extends DialogFragment {
    private Button cancel = null;
    private Button validate = null;
    private EditText name = null;
    private CaracteristiqueValeur caracteristiqueValeur = null;

    public static UpdateCaracDialog newInstance(int title, CaracteristiqueValeur caracteristiqueValeur){
        UpdateCaracDialog dialog = new UpdateCaracDialog();
        Bundle args = new Bundle();
        args.putInt("title", title);
        dialog.setArguments(args);
        dialog.caracteristiqueValeur = caracteristiqueValeur;
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.create_carac_valeur_dialog, container, false);
        cancel = (Button) v.findViewById(R.id.cancel);
        validate = (Button) v.findViewById(R.id.validate);
        name = (EditText) v.findViewById(R.id.name);
        name.setText(String.valueOf(caracteristiqueValeur.getBaseValue()));
        validate.setOnClickListener(validateListener);
        cancel.setOnClickListener(cancelListener);
        getDialog().setTitle(getString(R.string.update)+" "+caracteristiqueValeur.getRelatedList().getNom());
        return v;
    }

    private View.OnClickListener validateListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String tempError = name.getText().toString();
            long result = -1;
            if(!tempError.isEmpty()){
                float caracValeur = Float.valueOf(tempError);
                caracteristiqueValeur.setBaseValue(caracValeur);
                CaracteristiqueValeurDAO caracManager = new CaracteristiqueValeurDAO(getActivity());
                caracManager.open();
                result = caracManager.updateCaracteristiqueValeur(caracteristiqueValeur);
                caracManager.close();
            }
            if(result != -1){
                Toast.makeText(getActivity(), getText(R.string.successModifCarac), Toast.LENGTH_SHORT).show();
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