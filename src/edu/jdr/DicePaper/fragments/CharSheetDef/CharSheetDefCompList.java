package edu.jdr.DicePaper.fragments.CharSheetDef;

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
import edu.jdr.DicePaper.models.DAO.Liste.CompetenceListeDAO;
import edu.jdr.DicePaper.models.table.Liste.CompetenceListe;

import java.util.ArrayList;

/**
 * Created by paulyves on 2/13/14.
 */
public class CharSheetDefCompList extends Fragment {
    private String universeName;

    private ArrayList<CompetenceListe> compList;
    private ArrayAdapter<CompetenceListe> compListAdapter;

    private int componentId;
    private int componentPosition;

    /**
     * Method to instanciate this fragment
     * Only this method should be used to create this kind of fragment
     * @return
     */
    public static CharSheetDefCompList newInstance(){
        CharSheetDefCompList fragment = new CharSheetDefCompList();
        Bundle args = new Bundle();
        // insert here some argument if you want them in bundle
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.char_sheet_def_comp_list, container, false);
        universeName = getActivity().getIntent().getExtras().getString("universeName");
        TextView title = (TextView) v.findViewById(R.id.univTitle);
        title.setText(universeName);
        setComp(v);
        return v;
    }

    private void setComp(View v){
        ListView compListView = (ListView) v.findViewById(R.id.skillList);
        CompetenceListeDAO compManager = new CompetenceListeDAO(getActivity());
        compManager.open();
        compList = compManager.getAllCompetence(universeName);
        compManager.close();
        compListAdapter = new ArrayAdapter<CompetenceListe>(getActivity(), R.layout.list_component);
        compListAdapter.addAll(compList);
        compListView.setAdapter(compListAdapter);
        compListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                CompetenceListe compToDelete = (CompetenceListe) parent.getItemAtPosition(position);
                componentId = compToDelete.getListeId();
                componentPosition = position;
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(getString(R.string.askDeleteConfirmation)+" "+ compToDelete.getNom() + "?");
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
            CompetenceListeDAO manager = new CompetenceListeDAO(getActivity());
            manager.open();
            manager.delete(componentId);
            manager.close();
            compListAdapter.remove(compList.get(componentPosition));
            compList.remove(componentPosition);
            compListAdapter.notifyDataSetChanged();
        };
    };
}