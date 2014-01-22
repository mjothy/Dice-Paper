package edu.jdr.DicePaper;

import android.app.*;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created with IntelliJ IDEA.
 * User: mario
 * Date: 19/01/14
 * Time: 16:39
 * To change this template use File | Settings | File Templates.
 */
public class UniversMenu extends Activity {
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
        setContentView(R.layout.universmenu);

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
                dial = LoadUniverseDialog.newinstance(R.string.listUniverse, LoadUniverseDialog.LOAD);
                break;
            case DELETEDIALOG :
                dial = LoadUniverseDialog.newinstance(R.string.listUniverse, LoadUniverseDialog.DELETE);
                break;
            case ENTERDIALOG :
                dial = LoadUniverseDialog.newinstance(R.string.listUniverse, LoadUniverseDialog.ENTER);
                break;
        }
        dial.show(getFragmentManager(),"dialog");
    }


}