package edu.jdr.DicePaper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
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
    private final int CREAUNIVPOPUP = 1;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.universmenu);

        createUniverse = (Button) findViewById(R.id.NewUniverse);
        loadUniverse = (Button) findViewById(R.id.LoadUniverse);

        createUniverse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyDialogFragment().show(getFragmentManager(), "MyDialog");
            }
        });
    }


}