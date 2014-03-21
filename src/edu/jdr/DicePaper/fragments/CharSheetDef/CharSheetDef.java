package edu.jdr.DicePaper.fragments.CharSheetDef;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import edu.jdr.DicePaper.R;
import edu.jdr.DicePaper.fragments.CharSheetDef.CreateDialog.CreateCaracDialog;
import edu.jdr.DicePaper.fragments.CharSheetDef.CreateDialog.CreateCompDialog;
import edu.jdr.DicePaper.fragments.CharSheetDef.CreateDialog.CreateJaugeDialog;
import edu.jdr.DicePaper.fragments.CharSheetDef.CreateDialog.CreateUtilitaireDialog;

/**
 * Class to define the character sheet builder where user can define
 * various character sheet elements such as characteristics, skills, gauges...
 * List of created elements should be displayed with fragments
 * Created by paulyves on 1/23/14.
 */
public class CharSheetDef extends Fragment {

    private String universeName;
    private static final int CREATECARAC = 0;
    private static final int CREATEJAUGE = 1;
    private static final int CREATECOMP  = 2;
    private static final int CREATEUTIL = 3;

    /**
     * Method to instanciate this fragment
     * Only this method should be used to create this kind of fragment
     * @return
     */
    public static CharSheetDef newInstance(){
        CharSheetDef fragment = new CharSheetDef();
        Bundle args = new Bundle();
        // insert here some argument if you want them in bundle
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.char_sheet_def, container, false);

        universeName = getActivity().getIntent().getExtras().getString("universeName");
        TextView title = (TextView) v.findViewById(R.id.univTitle);
        title.setText(universeName);

        Button addCharac = (Button) v.findViewById(R.id.addCharac);
        addCharac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogType(CREATECARAC);
            }
        });
        Button addJauge = (Button) v.findViewById((R.id.addJauge));
        addJauge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogType(CREATEJAUGE);
            }
        });
        Button addUtil = (Button) v.findViewById((R.id.addUtil));
        addUtil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogType(CREATEUTIL);
            }
        });
        Button addComp = (Button) v.findViewById((R.id.addComp));
        addComp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogType(CREATECOMP);
            }
        });
        return v;
    }

    public void showDialogType(int type){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        DialogFragment dial = null;
        switch (type){
            case CREATECARAC :
                dial = CreateCaracDialog.newInstance(R.string.addCharac, universeName);
                break;
            case CREATEJAUGE :
                dial = CreateJaugeDialog.newInstance(R.string.addJauge, universeName);
                break;
            case CREATECOMP :
                dial = CreateCompDialog.newInstance(R.string.addComp, universeName);
                break;
            case CREATEUTIL :
                dial = CreateUtilitaireDialog.newInstance(R.string.addComp, universeName);
                break;
        }
        dial.show(getFragmentManager(),"dialog");
    }
}