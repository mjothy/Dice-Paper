package edu.jdr.DicePaper;

import android.app.*;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

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
    private final int CREATEDIALOG = 1;
    private final int LOADIALOG = 2;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.universmenu);

        createUniverse = (Button) findViewById(R.id.NewUniverse);
        loadUniverse = (Button) findViewById(R.id.LoadUniverse);

        createUniverse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogType(CREATEDIALOG);
            }
        });
        loadUniverse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UniversDAO manager = new UniversDAO(v.getContext());
                manager.open();
                ArrayList<String> list = manager.getAllUnivers();
                manager.close();
                Toast.makeText(v.getContext(), "Ca marche "+ list.get(0), android.widget.Toast.LENGTH_SHORT).show();
                //showDialogType(LOADIALOG);
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
                dial = LoadUniverseDialog.newinstance(R.string.listUniverse);
                break;
        }
        dial.show(ft,"dialog");
    }


}