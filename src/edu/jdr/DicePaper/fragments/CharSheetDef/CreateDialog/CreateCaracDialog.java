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
import edu.jdr.DicePaper.models.DAO.Liste.CaracteristiqueListeDAO;
import edu.jdr.DicePaper.models.table.Liste.CaracteristiqueListe;

/**
 * Created by paulyves on 1/30/14.
 */
public class CreateCaracDialog extends DialogFragment {
    private EditText name = null;
    private String univName = null;

    public static CreateCaracDialog newInstance(int title, String univName){
        CreateCaracDialog dialog = new CreateCaracDialog();
        Bundle args = new Bundle();
        args.putInt("title", title);
        dialog.setArguments(args);
        dialog.univName = univName;
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
        getDialog().setTitle(getString(R.string.addCharac));
        return v;
    }

    private View.OnClickListener validateListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String caracName = name.getText().toString();
            long result = -1;
            if(!caracName.isEmpty()){
                CaracteristiqueListe carac = new CaracteristiqueListe(caracName, univName);
                CaracteristiqueListeDAO caracManager = new CaracteristiqueListeDAO(getActivity());
                caracManager.open();
                result = caracManager.createCaracListe(carac);
                caracManager.close();
            }
            if(result != -1){
                Toast.makeText(getActivity(), getText(R.string.successCreateCarac), Toast.LENGTH_SHORT).show();
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