package edu.jdr.DicePaper;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Class to define the character sheet builder where user can define
 * various character sheet elements such as characteristics, skills, gauges...
 * List of created elements should be displayed with fragments
 * Created by paulyves on 1/23/14.
 */
public class CharSheetBuilder extends Activity {
    private String universeName;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.charsheetbuilder);
        universeName = getIntent().getExtras().getString("universeName");
        TextView title = (TextView) findViewById(R.id.univTitle);
        title.setText(universeName);
    }
}