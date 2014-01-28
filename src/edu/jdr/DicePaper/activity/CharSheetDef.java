package edu.jdr.DicePaper.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import edu.jdr.DicePaper.R;

/**
 * Class to define the character sheet builder where user can define
 * various character sheet elements such as characteristics, skills, gauges...
 * List of created elements should be displayed with fragments
 * Created by paulyves on 1/23/14.
 */
public class CharSheetDef extends Activity {
    private String universeName;
    private Button addCharac;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.char_sheet_def);
        universeName = getIntent().getExtras().getString("universeName");
        TextView title = (TextView) findViewById(R.id.univTitle);
        title.setText(universeName);
        addCharac = (Button) findViewById(R.id.addCharac);
    }
}