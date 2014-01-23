package edu.jdr.DicePaper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * This is the menu of Universe building, from here you can define :
 * - the character sheet template
 * - the dice rolls template
 * Created by paulyves on 1/23/14.
 */
public class UniversBuilderMenu extends Activity {
    private String universeName;
    private Button defineCharSheet;
    private Button defineDiceRoll;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.universbuildermenu);
        universeName = getIntent().getExtras().getString("universeName");
        TextView title = (TextView) findViewById(R.id.univTitle);
        title.setText(universeName);
        defineCharSheet = (Button) findViewById(R.id.defineCharSheet);
        defineDiceRoll = (Button) findViewById(R.id.defineDiceRoll);
        defineCharSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent charSheetBuilder = new Intent(UniversBuilderMenu.this, CharSheetBuilder.class);
                charSheetBuilder.putExtra("universeName", universeName);
                startActivity(charSheetBuilder);
            }
        });
    }
}