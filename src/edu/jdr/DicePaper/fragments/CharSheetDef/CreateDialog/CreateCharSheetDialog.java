package edu.jdr.DicePaper.fragments.CharSheetDef.CreateDialog;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import edu.jdr.DicePaper.R;
import edu.jdr.DicePaper.activity.CharSheetSwipper;
import edu.jdr.DicePaper.models.DAO.FichePersonnageDAO;
import edu.jdr.DicePaper.models.table.FichePersonnage;

/**
 * Created by mario on 20/02/14.
 */
public class CreateCharSheetDialog extends DialogFragment {
    private Button cancel = null;
    private Button validate = null;
    private EditText name = null;
    private String univName;

    public static CreateCharSheetDialog newInstance(int title, String univName){
        CreateCharSheetDialog dialog = new CreateCharSheetDialog();
        Bundle args = new Bundle();
        args.putInt("title", title);
        dialog.setArguments(args);
        dialog.univName = univName;
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.create_univers_dialog, container, false);
        cancel = (Button) v.findViewById(R.id.cancel);
        validate = (Button) v.findViewById(R.id.validUniverse);
        name = (EditText) v.findViewById(R.id.univName);
        validate.setOnClickListener(validateListener);
        cancel.setOnClickListener(cancelListener);


        getDialog().setTitle(getArguments().getInt("title"));
        return v;
    }

    //listeners
    private View.OnClickListener validateListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String ficheName = name.getText().toString();
            long result = -1;
            if (!ficheName.isEmpty()){
                FichePersonnage fichePersonnage = new FichePersonnage(ficheName, 0, univName);
                FichePersonnageDAO ficheManager = new FichePersonnageDAO(getActivity());
                ficheManager.open();
                result = ficheManager.createFiche(fichePersonnage);
                ficheManager.close();
            }
            if(result != -1){
                Intent charSheetBuilder = new Intent(getActivity(), CharSheetSwipper.class);
                charSheetBuilder.putExtra("universeName", univName);
                charSheetBuilder.putExtra("charName", ficheName);
                startActivity(charSheetBuilder);
            } else {
                Toast.makeText(getActivity(), getText(R.string.errorCreateUniverse), Toast.LENGTH_SHORT).show();
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