package edu.jdr.DicePaper.fragments;

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
import edu.jdr.DicePaper.activity.UniversDefinition;
import edu.jdr.DicePaper.models.UniversDAO;

/**
 * Created by paulyves on 1/21/14.
 */
public class CreateUniversDialog extends DialogFragment {
    private Button cancel = null;
    private Button validate = null;
    private EditText name = null;

    public static CreateUniversDialog newInstance(int title){
        CreateUniversDialog dialog = new CreateUniversDialog();
        Bundle args = new Bundle();
        args.putInt("title", title);
        dialog.setArguments(args);
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
            String univName = name.getText().toString();
            long result = -1;
            if (!univName.isEmpty()){
                UniversDAO univManager = new UniversDAO(getActivity());
                univManager.open();
                result = univManager.createUnivers(univName);
                univManager.close();
            }
            if(result != -1){
                Intent universeBuilder = new Intent(getActivity(), UniversDefinition.class);
                universeBuilder.putExtra("universeName", univName);
                startActivity(universeBuilder);
            } else {
                Toast.makeText(getActivity(), "Erreur, échec de la création d'univers", Toast.LENGTH_SHORT).show();
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