package edu.jdr.DicePaper.fragments.CharSheet;

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
import edu.jdr.DicePaper.models.DAO.Valeur.SpecialisationDAO;
import edu.jdr.DicePaper.models.table.Valeur.Specialisation;

/**
 * Created by paulyves on 2/25/14.
 */
public class CreateSpeDialog extends DialogFragment {
    private Button cancel = null;
    private Button validate = null;
    private EditText name = null;
    private int compId;

    public static CreateSpeDialog newInstance(int title, int compId){
        CreateSpeDialog dialog = new CreateSpeDialog();
        Bundle args = new Bundle();
        args.putInt("title", title);
        dialog.setArguments(args);
        dialog.compId = compId;
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
        getDialog().setTitle(getString(R.string.addSpe));
        return v;
    }

    private View.OnClickListener validateListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String compName = name.getText().toString();
            long result = -1;
            if(!compName.isEmpty()){
                Specialisation spe = new Specialisation(compName, 0 ,compId);
                SpecialisationDAO manager = new SpecialisationDAO(getActivity());
                manager.open();
                result = manager.createSpecialisation(spe);
                manager.close();
            }
            if(result != -1){
                Toast.makeText(getActivity(), getText(R.string.successCreateSpe), Toast.LENGTH_SHORT).show();
                ((CharSheetSwipper)getActivity()).getFragCompValeur().setComp();
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
