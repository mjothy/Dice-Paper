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
import edu.jdr.DicePaper.models.DAO.Liste.UtilitaireListeDAO;
import edu.jdr.DicePaper.models.DAO.Valeur.EquipementDAO;
import edu.jdr.DicePaper.models.table.Liste.UtilitaireListe;
import edu.jdr.DicePaper.models.table.Valeur.Equipement;

/**
 * Created by mario on 04/03/14.
 */
public class CreateInventaireDialog extends DialogFragment {
    private Button cancel = null;
    private Button validate = null;
    private EditText name = null;
    private EditText description = null;
    private String univName = null;
    private String charName = null;

    public static CreateInventaireDialog newInstance(int title, String univName, String charName){
        CreateInventaireDialog dialog = new CreateInventaireDialog();
        Bundle args = new Bundle();
        args.putInt("title", title);
        dialog.setArguments(args);
        dialog.univName = univName;
        dialog.charName = charName;
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.create_equipement_dialog, container, false);
        cancel = (Button) v.findViewById(R.id.cancel);
        validate = (Button) v.findViewById(R.id.validate);
        name = (EditText) v.findViewById(R.id.name);
        description = (EditText) v.findViewById(R.id.description);
        validate.setOnClickListener(validateListener);
        cancel.setOnClickListener(cancelListener);
        getDialog().setTitle(getString(R.string.addEquipement));
        return v;
    }

    private View.OnClickListener validateListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String tempName = name.getText().toString();
            String tempDescription = description.getText().toString();
            long result = -1;
            if(!tempName.isEmpty()){
                Equipement equipement = new Equipement(tempName, tempDescription, charName);
                EquipementDAO manager = new EquipementDAO(getActivity());
                manager.open();
                result = manager.createEquipement(equipement);
                manager.close();
            }
            if(result != -1){
                Toast.makeText(getActivity(), getText(R.string.successCreateInv), Toast.LENGTH_SHORT).show();
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
