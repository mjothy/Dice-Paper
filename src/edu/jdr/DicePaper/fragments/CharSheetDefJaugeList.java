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
import android.widget.ExpandableListAdapter;
import edu.jdr.DicePaper.*;
import edu.jdr.DicePaper.models.DAO.CompetenceListeDAO;
import edu.jdr.DicePaper.models.DAO.JaugeListeDAO;
import edu.jdr.DicePaper.models.DAO.UtilitaireListeDAO;
import edu.jdr.DicePaper.models.table.CompetenceListe;
import edu.jdr.DicePaper.models.table.JaugeListe;
import edu.jdr.DicePaper.models.table.UtilitaireListe;

import java.util.ArrayList;

/**
 * Created by paulyves on 2/2/14.
 */
public class CharSheetDefJaugeList extends Fragment {
    private String universeName;

    private ArrayList<JaugeListe> jaugeList;
    private ArrayAdapter<JaugeListe> jaugeListAdapter;

    private Class componentClass;
    private int componentId;
    private int componentPosition;
    /**
     * Method to instanciate this fragment
     * Only this method should be used to create this kind of fragment
     * @return
     */
    public static CharSheetDefJaugeList newInstance(){
        CharSheetDefJaugeList fragment = new CharSheetDefJaugeList();
        Bundle args = new Bundle();
        // insert here some argument if you want them in bundle
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.char_sheet_def_jauge_list, container, false);
        universeName = getActivity().getIntent().getExtras().getString("universeName");
        TextView title = (TextView) v.findViewById(R.id.univTitle);
        title.setText(universeName);
        setJauge(v);
        return v;
    }

    private void setJauge(View v){
        ListView jaugeListView = (ListView) v.findViewById(R.id.jaugeList);
        JaugeListeDAO jaugeManager = new JaugeListeDAO(getActivity());
        jaugeManager.open();
        jaugeList = jaugeManager.getAllJaugeListe(universeName);
        jaugeManager.close();
        jaugeListAdapter = new ArrayAdapter<JaugeListe>(getActivity(), R.layout.list_component);
        jaugeListAdapter.addAll(jaugeList);
        jaugeListView.setAdapter(jaugeListAdapter);
        jaugeListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                JaugeListe jaugeToDelete = (JaugeListe) parent.getItemAtPosition(position);
                componentClass = JaugeListe.class;
                componentId = jaugeToDelete.getJaugeListeId();
                componentPosition = position;
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(getString(R.string.askDeleteConfirmation)+" "+ jaugeToDelete.getNom() + "?");
                builder.setPositiveButton(getString(R.string.yes), deleteConfirmListener);
                builder.setNegativeButton(getString(R.string.no), null);
                builder.show();
                return false;
            }
        });
    }





    private Dialog.OnClickListener deleteConfirmListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if(componentClass.equals(JaugeListe.class)){
                JaugeListeDAO jaugeManager = new JaugeListeDAO(getActivity());
                jaugeManager.open();
                jaugeManager.deleteJaugeList(componentId);
                jaugeManager.close();
                jaugeListAdapter.remove(jaugeList.get(componentPosition));
                jaugeList.remove(componentPosition);
                jaugeListAdapter.notifyDataSetChanged();
            }
        }
    };

}