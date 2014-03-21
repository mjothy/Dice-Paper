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
import edu.jdr.DicePaper.activity.CharSheetSwipper;
import edu.jdr.DicePaper.models.DAO.Valeur.EquipementDAO;
import edu.jdr.DicePaper.models.table.Valeur.Equipement;

/**
 * Created by mario on 04/03/14.
 */
public class UpdateInventaireDialog extends DialogFragment {
    private EditText name = null;
    private EditText description = null;
    private Equipement equipement = null;

    public static UpdateInventaireDialog newInstance(int title, Equipement equipement){
        UpdateInventaireDialog dialog = new UpdateInventaireDialog();
        Bundle args = new Bundle();
        args.putInt("title", title);
        dialog.setArguments(args);
        dialog.equipement = equipement;
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.create_equipement_dialog, container, false);
        Button cancel = (Button) v.findViewById(R.id.cancel);
        Button validate = (Button) v.findViewById(R.id.validate);
        name = (EditText) v.findViewById(R.id.name);
        name.setText(equipement.getNom());
        description = (EditText) v.findViewById(R.id.description);
        description.setText(equipement.getDescription());
        validate.setOnClickListener(validateListener);
        cancel.setOnClickListener(cancelListener);
        getDialog().setTitle(getString(R.string.updateEquipement));
        return v;
    }

    private View.OnClickListener validateListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String tempName = name.getText().toString();
            String tempDescription = description.getText().toString();
            long result = -1;
            if(!(tempDescription.isEmpty()) && !(tempName.isEmpty())){
                equipement.setNom(tempName);
                equipement.setDescription(tempDescription);
                EquipementDAO manager = new EquipementDAO(getActivity());
                manager.open();
                result = manager.updateEquipement(equipement);
                manager.close();
            }
            if(result != -1){
                Toast.makeText(getActivity(), getText(R.string.successModifInv), Toast.LENGTH_SHORT).show();
                ((CharSheetSwipper)getActivity()).getFragInventaire().setInventaire();
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