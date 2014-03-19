package edu.jdr.DicePaper.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import edu.jdr.DicePaper.R;

public class MainMenu extends Activity {

    protected Button versUnivers;
    protected Button versPartage;
    protected Button versUniversio;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        versUnivers = (Button) findViewById(R.id.univers);
        versUnivers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenu.this, UniversMaster.class);
                startActivity(intent);
            }
        });
        versPartage = (Button) findViewById(R.id.sharing);
        versPartage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSharing = new Intent(MainMenu.this, SharingMenu.class);
                startActivity(intentSharing);
            }
        });
        versUniversio = (Button) findViewById(R.id.io);
        versUniversio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentIO = new Intent(MainMenu.this, UniversIO.class);
                startActivity(intentIO);
            }
        });
    }
}
