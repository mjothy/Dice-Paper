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
import edu.jdr.DicePaper.activity.CharSheetDefSwipper;
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
public class CharSheetDefCompoList extends Fragment {
    private String universeName;

    private ArrayList<JaugeListe> jaugeList;
    private ArrayAdapter<JaugeListe> jaugeListAdapter;
    private ArrayList<UtilitaireListe> utilList;
    private ArrayAdapter<UtilitaireListe> utilListAdapter;
    private ArrayList<CompetenceListe> compList;
    private ExpandableListAdapter compListAdapter;

    private Class componentClass;
    private int componentId;
    private int componentPosition;
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
        setJauge(v);
        setUtils(v);
        setComp(v);
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

    private void setUtils(View v){
        ListView utilListView = (ListView) v.findViewById(R.id.utilList);
        utilListAdapter = new ArrayAdapter<UtilitaireListe>(getActivity(), R.layout.list_component);
        UtilitaireListeDAO utilManager = new UtilitaireListeDAO(getActivity());
        utilManager.open();
        utilList = utilManager.getAllUtilitaireListe(universeName);
        utilManager.close();
        utilListAdapter.addAll(utilList);
        utilListView.setAdapter(utilListAdapter);
        utilListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                UtilitaireListe utilToDelete = (UtilitaireListe) parent.getItemAtPosition(position);
                componentClass = UtilitaireListe.class;
                componentId = utilToDelete.getUtilitaireListeId();
                componentPosition = position;
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(getString(R.string.askDeleteConfirmation)+" "+ utilToDelete.getNom() + "?");
                builder.setPositiveButton(getString(R.string.yes), deleteConfirmListener);
                builder.setNegativeButton(getString(R.string.no), null);
                builder.show();
                return false;
            }
        });
    }

    private void setComp(View v){
        ExpandableListView listView = (ExpandableListView) v.findViewById(R.id.skillList);
        CompetenceListeDAO compManager = new CompetenceListeDAO(getActivity());
        compList = compManager.getAllCompetence(universeName);
        //todo
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
            if(componentClass.equals(UtilitaireListe.class)){
                UtilitaireListeDAO utilManager = new UtilitaireListeDAO(getActivity());
                utilManager.open();
                utilManager.delete(componentId);
                utilManager.close();
                utilListAdapter.remove(utilList.get(componentPosition));
                utilList.remove(componentPosition);
                utilListAdapter.notifyDataSetChanged();
            }
        }
    };

}