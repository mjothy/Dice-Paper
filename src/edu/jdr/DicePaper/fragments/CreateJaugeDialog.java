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
import edu.jdr.DicePaper.models.table.JaugeListe;
import edu.jdr.DicePaper.models.DAO.JaugeListeDAO;

/**
 * Created by mario on 30/01/14.
 */
public class CreateJaugeDialog extends DialogFragment {
    private Button cancel = null;
    private Button validate = null;
    private EditText name = null;
    private String univName = null;

    public static CreateJaugeDialog newInstance(int title, String univName){
        CreateJaugeDialog dialog = new CreateJaugeDialog();
        Bundle args = new Bundle();
        args.putInt("title", title);
        dialog.setArguments(args);
        dialog.univName = univName;
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
        getDialog().setTitle(getString(R.string.addJauge));
        return v;
    }

    private View.OnClickListener validateListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String jaugeName = name.getText().toString();
            long result = -1;
            if(!jaugeName.isEmpty()){
                JaugeListe jauge = new JaugeListe(jaugeName, univName);
                JaugeListeDAO jaugeManager = new JaugeListeDAO(getActivity());
                jaugeManager.open();
                result = jaugeManager.createJaugeListe(jauge);
                jaugeManager.close();
            }
            if(result != -1){
                Toast.makeText(getActivity(), getText(R.string.successCreateJauge), Toast.LENGTH_SHORT).show();
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
