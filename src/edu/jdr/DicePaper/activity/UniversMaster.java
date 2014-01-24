package edu.jdr.DicePaper.activity;

import android.app.*;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import edu.jdr.DicePaper.R;
import edu.jdr.DicePaper.fragments.CreateUniversDialog;
import edu.jdr.DicePaper.fragments.ListUniverseDialog;

/**
 * Created with IntelliJ IDEA.
 * User: mario
 * Date: 19/01/14
 * Time: 16:39
 * To change this template use File | Settings | File Templates.
 */
public class UniversMaster extends Activity {
    private Button createUniverse;
    private Button loadUniverse;
    private Button deleteUniverse;
    private Button enterUniverse;
    private final int CREATEDIALOG = 1;
    private final int LOADIALOG = 2;
    private final int DELETEDIALOG = 3;
    private final int ENTERDIALOG = 4;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.univers_master);

        //initializing buttons
        createUniverse = (Button) findViewById(R.id.NewUniverse);
        loadUniverse = (Button) findViewById(R.id.LoadUniverse);
        deleteUniverse = (Button) findViewById(R.id.DeleteUniverse);
        enterUniverse = (Button) findViewById(R.id.EnterUniverse);
        createUniverse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogType(CREATEDIALOG);
            }
        });
        loadUniverse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogType(LOADIALOG);
            }
        });
        deleteUniverse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogType(DELETEDIALOG);
            }
        });
        enterUniverse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogType(ENTERDIALOG);
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
            case CREATEDIALOG :
                dial = CreateUniversDialog.newInstance(R.string.nameUniverse);
                break;
            case LOADIALOG :
                dial = ListUniverseDialog.newinstance(R.string.listUniverse, ListUniverseDialog.LOAD);
                break;
            case DELETEDIALOG :
                dial = ListUniverseDialog.newinstance(R.string.listUniverse, ListUniverseDialog.DELETE);
                break;
            case ENTERDIALOG :
                dial = ListUniverseDialog.newinstance(R.string.listUniverse, ListUniverseDialog.ENTER);
                break;
        }
        dial.show(getFragmentManager(),"dialog");
    }


}