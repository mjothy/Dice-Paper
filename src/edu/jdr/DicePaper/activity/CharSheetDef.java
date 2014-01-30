package edu.jdr.DicePaper.activity;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import edu.jdr.DicePaper.R;
import edu.jdr.DicePaper.fragments.CreateCaracDialog;
import edu.jdr.DicePaper.fragments.CreateUniversDialog;
import edu.jdr.DicePaper.fragments.ListUniverseDialog;

/**
 * Class to define the character sheet builder where user can define
 * various character sheet elements such as characteristics, skills, gauges...
 * List of created elements should be displayed with fragments
 * Created by paulyves on 1/23/14.
 */
public class CharSheetDef extends Activity {

    private String universeName;
    private Button addCharac;
    private Button addJauge;

    private static final int CREATECARAC = 0;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.char_sheet_def);

        universeName = getIntent().getExtras().getString("universeName");
        TextView title = (TextView) findViewById(R.id.univTitle);
        title.setText(universeName);

        addCharac = (Button) findViewById(R.id.addCharac);

        addJauge = (Button) findViewById((R.id.addJauge));
        addCharac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogType(CREATECARAC);
            }
        });
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
        }
        dial.show(getFragmentManager(),"dialog");
    }
}