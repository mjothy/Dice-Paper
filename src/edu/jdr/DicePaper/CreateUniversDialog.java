package edu.jdr.DicePaper;

import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
        View v = inflater.inflate(R.layout.createuniversdialog, container, false);
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
            if (!univName.isEmpty()){
                Toast.makeText(getActivity(), "Univers :"+univName, Toast.LENGTH_SHORT).show();
            }
        }
    };

    private View.OnClickListener cancelListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dismiss();
        }
    };
}