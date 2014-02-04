package edu.jdr.DicePaper.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import edu.jdr.DicePaper.R;
import edu.jdr.DicePaper.activity.CharSheetDefSwipper;
import edu.jdr.DicePaper.models.DAO.JaugeListeDAO;
import edu.jdr.DicePaper.models.table.JaugeListe;

import java.util.ArrayList;

/**
 * Created by paulyves on 2/2/14.
 */
public class CharSheetDefCompoList extends Fragment {
    private String universeName;
    private ArrayList<JaugeListe> jaugeList;
    private Class componentClass;
    private int componentId;
    /**
     * Method to instanciate this fragment
     * Only this method should be used to create this kind of fragment
     * @return
     */
    public static CharSheetDefCompoList newInstance(){
        CharSheetDefCompoList fragment = new CharSheetDefCompoList();
        Bundle args = new Bundle();
        // insert here some argument if you want them in bundle
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.char_sheet_def_compo_list, container, false);
        universeName = getActivity().getIntent().getExtras().getString("universeName");
        TextView title = (TextView) v.findViewById(R.id.univTitle);
        title.setText(universeName);

        Button theSlider = (Button) v.findViewById(R.id.slide);
        theSlider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSheetDefSwipper swipper = ((CharSheetDefSwipper) getActivity());
                swipper.goToCompoDefine();
            }
        });
        ListView listView = (ListView) v.findViewById(R.id.jaugeList);
        setJauge();
        ArrayAdapter<JaugeListe> ad = new ArrayAdapter<JaugeListe>(getActivity(), R.layout.list_component);
        ad.addAll(jaugeList);
        listView.setAdapter(ad);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                JaugeListe jaugeToDelete = (JaugeListe) parent.getItemAtPosition(position);
                componentClass = JaugeListe.class;
                componentId = jaugeToDelete.getJaugeListeId();
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Êtes vous certain de vouloir supprimer " + jaugeToDelete.getNom() + "?");
                builder.setPositiveButton("oui", deleteConfirmListener);
                builder.setNegativeButton("non", null);
                builder.show();
                return false;
            }
        });
        return v;
    }

    private void setJauge(){
        JaugeListeDAO jaugeManager = new JaugeListeDAO(getActivity());
        jaugeManager.open();
        jaugeList = jaugeManager.getAllJaugeListe(universeName);
        jaugeManager.close();
    }

    private Dialog.OnClickListener deleteConfirmListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if(componentClass.equals(JaugeListe.class)){
                Toast.makeText(getActivity(), "Vous avez bien supprimé "+componentId, Toast.LENGTH_SHORT).show();
            }
        }
    };

}