package edu.jdr.DicePaper.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.*;
import edu.jdr.DicePaper.R;
import edu.jdr.DicePaper.models.DAO.UniversDAO;
import edu.jdr.DicePaper.utils.XML.Generator;
import edu.jdr.DicePaper.utils.XML.Parser;

import java.util.ArrayList;

/**
 * Created by mario on 19/03/14.
 */
public class UniversIO extends Activity {

    private EditText univToImport;
    private Spinner univSpinner;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.universio);

        Button goExport = (Button) findViewById(R.id.exportUniv);
        goExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goExport();
            }
        });

        Button goImport = (Button) findViewById(R.id.importUniv);
        goImport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goImport();
            }
        });

        univToImport = (EditText) findViewById(R.id.univToImport);

        univSpinner = (Spinner) findViewById(R.id.univList);
        UniversDAO univManager = new UniversDAO(this);
        univManager.open();
        ArrayList<String> listUniv = univManager.getAllUnivers();
        univManager.close();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        adapter.addAll(listUniv);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        univSpinner.setAdapter(adapter);
    }

    public void goExport(){
        if(isExternalStorageWritable()){
            String univName = (String) univSpinner.getSelectedItem();
            Generator generator = new Generator(univName, this);
            generator.generate();
            Toast.makeText(getApplicationContext(), getString(R.string.successUnivExport), Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(), getString(R.string.failureUnivExport), Toast.LENGTH_SHORT).show();
        }
    }

    public void goImport(){
        if(isExternalStorageWritable()){
            String univName = univToImport.getText().toString();
            Parser parser = new Parser(univName, this);
            if(parser.parse()){
                Toast.makeText(getApplicationContext(), getString(R.string.successUnivImport), Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getApplicationContext(), getString(R.string.failureUnivImport), Toast.LENGTH_SHORT).show();
        }
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }
}