package edu.jdr.DicePaper.fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import edu.jdr.DicePaper.R;
import edu.jdr.DicePaper.models.DAO.Valeur.JaugeValeurDAO;
import edu.jdr.DicePaper.models.DAO.Valeur.UtilitaireValeurDAO;
import edu.jdr.DicePaper.models.table.Valeur.JaugeValeur;
import edu.jdr.DicePaper.models.table.Valeur.UtilitaireValeur;

/**
 * Created by paulyves on 2/25/14.
 */
public class UpdateJaugeDialog extends DialogFragment {
    private Button cancel = null;
    private Button validate = null;
    private EditText value = null;
    private JaugeValeur jaugeValeur = null;

    public static UpdateJaugeDialog newInstance(int title, JaugeValeur jaugeValeur){
        UpdateJaugeDialog dialog = new UpdateJaugeDialog();
        Bundle args = new Bundle();
        args.putInt("title", title);
        dialog.setArguments(args);
        dialog.jaugeValeur = jaugeValeur;
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.create_carac_valeur_dialog, container, false);
        cancel = (Button) v.findViewById(R.id.cancel);
        validate = (Button) v.findViewById(R.id.validate);
        value = (EditText) v.findViewById(R.id.name);
        value.setText(String.valueOf(jaugeValeur.getMaxValue()));
        validate.setOnClickListener(validateListener);
        cancel.setOnClickListener(cancelListener);
        getDialog().setTitle(getString(R.string.update) + " " + jaugeValeur.getRelatedList().getNom());
        return v;
    }

    private View.OnClickListener validateListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String tempValue = value.getText().toString();
            long result = -1;
            if(!tempValue.isEmpty()){
                jaugeValeur.setMaxValue(Integer.parseInt(tempValue));
                JaugeValeurDAO manager = new JaugeValeurDAO(getActivity());
                manager.open();
                result = manager.updateJaugeValeur(jaugeValeur);
                manager.close();
            }
            if(result != -1){
                Toast.makeText(getActivity(), getText(R.string.successModifJauge), Toast.LENGTH_SHORT).show();
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