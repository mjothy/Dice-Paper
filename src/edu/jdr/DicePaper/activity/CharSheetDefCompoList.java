package edu.jdr.DicePaper.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import edu.jdr.DicePaper.R;
import edu.jdr.DicePaper.models.DAO.JaugeListeDAO;
import edu.jdr.DicePaper.models.table.JaugeListe;

import java.util.ArrayList;

/**
 * Created by paulyves on 2/2/14.
 */
public class CharSheetDefCompoList extends Fragment {
    private String universeName;
    private ArrayList<JaugeListe> jaugeList;
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

        setJauge();
        ArrayAdapter<JaugeListe> ad = new ArrayAdapter<JaugeListe>(getActivity(), R.layout.char_sheet_def_compo_list, R.id.jaugeList);
        ad.addAll(jaugeList);
        return v;
    }

    private void setJauge(){
        JaugeListeDAO jaugeManager = new JaugeListeDAO(getActivity());
        jaugeManager.open();
        jaugeList = jaugeManager.getAllJaugeListe(universeName);
        jaugeManager.close();
    }
}