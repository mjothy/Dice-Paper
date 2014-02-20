package edu.jdr.DicePaper.utils;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;
import edu.jdr.DicePaper.R;
import edu.jdr.DicePaper.activity.CharSheetDefSwipper;
import edu.jdr.DicePaper.activity.CharSheetSwipper;
import edu.jdr.DicePaper.fragments.CreateModifDialog;
import edu.jdr.DicePaper.models.table.Liste.CaracteristiqueListe;
import edu.jdr.DicePaper.models.table.Liste.ModificateurListe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by mario on 20/02/14.
 */
public abstract class ExpListAdapter <T,S> extends BaseExpandableListAdapter {

    protected Context _context;
    protected List<T> _listDataHeader; // header titles
    // child data in format of header title, child title
    protected HashMap<T, List<S>> _listDataChild;

    public ExpListAdapter(Context context, List<T> listDataHeader,
                                     HashMap<T, List<S>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }
    public ExpListAdapter(Context context, HashMap<T, List<S>> listChildData) {
        this._context = context;
        _listDataHeader = new ArrayList<T>();
        for(T key : listChildData.keySet()){
            this._listDataHeader.add(key);
        }
        this._listDataChild = listChildData;
    }


    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public abstract View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent);

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public abstract View getGroupView(final int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent);

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}