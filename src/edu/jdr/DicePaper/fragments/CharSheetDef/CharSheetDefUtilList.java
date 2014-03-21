package edu.jdr.DicePaper.fragments.CharSheetDef;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import edu.jdr.DicePaper.R;
import edu.jdr.DicePaper.models.DAO.Liste.UtilitaireListeDAO;
import edu.jdr.DicePaper.models.table.Liste.UtilitaireListe;

import java.util.ArrayList;

/**
 * Created by paulyves on 2/8/14.
 */
public class CharSheetDefUtilList extends Fragment {
    private String universeName;

    private ArrayList<UtilitaireListe> utilList;
    private ArrayAdapter<UtilitaireListe> utilListAdapter;

    private int componentId;
    private int componentPosition;

    /**
     * Method to instanciate this fragment
     * Only this method should be used to create this kind of fragment
     * @return
     */
    public static CharSheetDefUtilList newInstance(){
        CharSheetDefUtilList fragment = new CharSheetDefUtilList();
        Bundle args = new Bundle();
        // insert here some argument if you want them in bundle
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.char_sheet_def_util_list, container, false);
        universeName = getActivity().getIntent().getExtras().getString("universeName");
        TextView title = (TextView) v.findViewById(R.id.univTitle);
        title.setText(universeName);
        setUtils(v);
        return v;
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
                componentId = utilToDelete.getListeId();
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

    private Dialog.OnClickListener deleteConfirmListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
        UtilitaireListeDAO utilManager = new UtilitaireListeDAO(getActivity());
        utilManager.open();
        utilManager.delete(componentId);
        utilManager.close();
        utilListAdapter.remove(utilList.get(componentPosition));
        utilList.remove(componentPosition);
        utilListAdapter.notifyDataSetChanged();
    }
};
}