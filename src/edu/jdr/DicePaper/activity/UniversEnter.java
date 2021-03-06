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
import edu.jdr.DicePaper.fragments.CharSheet.ListCharSheetDialog;
import edu.jdr.DicePaper.fragments.CharSheetDef.CreateDialog.CreateCharSheetDialog;

/**
 * Created by mario on 16/02/14.
 */
public class UniversEnter extends Activity {
    private String universeName;

    private final int CREATEDIALOG = 1;
    private final int LOADIALOG = 2;
    private final int DELETEDIALOG = 3;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.univers_enter);
        universeName = getIntent().getExtras().getString("universeName");
        TextView title = (TextView) findViewById(R.id.univTitle);
        title.setText(universeName);

        //initializing buttons
        Button createCharSheet = (Button) findViewById(R.id.NewCharSheet);
        Button loadCharSheet = (Button) findViewById(R.id.LoadCharSheet);
        Button deleteCharSheet = (Button) findViewById(R.id.DeleteCharSheet);

        createCharSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogType(CREATEDIALOG);
            }
        });
        loadCharSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogType(LOADIALOG);
            }
        });
        deleteCharSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogType(DELETEDIALOG);
            }
        });

    }

    //todo
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
                dial = CreateCharSheetDialog.newInstance(R.string.nameCharSheet, universeName);
                break;
            case LOADIALOG :
                dial = ListCharSheetDialog.newinstance(R.string.listCharSheet, ListCharSheetDialog.LOAD, universeName);
                break;
            case DELETEDIALOG :
                dial = ListCharSheetDialog.newinstance(R.string.listCharSheet, ListCharSheetDialog.DELETE, universeName);
                break;
        }
        dial.show(getFragmentManager(),"dialog");
    }
}