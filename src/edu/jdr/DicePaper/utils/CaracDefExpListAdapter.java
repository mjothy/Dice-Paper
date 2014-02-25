package edu.jdr.DicePaper.utils;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import edu.jdr.DicePaper.R;
import edu.jdr.DicePaper.activity.CharSheetDefSwipper;
import edu.jdr.DicePaper.fragments.CharSheetDef.CreateDialog.CreateModifDialog;
import edu.jdr.DicePaper.models.table.Liste.CaracteristiqueListe;
import edu.jdr.DicePaper.models.table.Liste.ModificateurListe;

import java.util.HashMap;
import java.util.List;

/**
 * Created by paulyves on 2/9/14.
 * add int attributes for the header and children layouts
 * add attribute to see if we need to create buttons and what will be their use
 * once this is done, rename this to MightyExpandableListAdapterOfDoom
 */
public class CaracDefExpListAdapter<T,S> extends ExpListAdapter {
    public CaracDefExpListAdapter(Context context, List<T> listDataHeader,
                                  HashMap<T, List<S>> listChildData) {
        super(context,listDataHeader,listChildData);
    }
    public CaracDefExpListAdapter(Context context, HashMap<T, List<S>> listChildData) {
        super(context,listChildData);
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = getChild(groupPosition, childPosition).toString();

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_child, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.childs);
        txtListChild.setText(childText);

        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ModificateurListe mod = (ModificateurListe) getChild(groupPosition, childPosition);
                ((CharSheetDefSwipper) _context).getFragCaracList().callBackDeleteMod(mod);
                return false;
            }
        });

        return convertView;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = getGroup(groupPosition).toString();
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_define_group, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.components);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);
        Button add = (Button) convertView.findViewById(R.id.addButton);
        add.setFocusable(false);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CaracteristiqueListe carac = (CaracteristiqueListe) getGroup(groupPosition);
                FragmentTransaction ft = ((Activity)_context).getFragmentManager().beginTransaction();
                Fragment prev = ((Activity)_context).getFragmentManager().findFragmentByTag("dialog");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);
                CreateModifDialog dialog = CreateModifDialog.newInstance(R.string.addModif, carac.getListeId());
                dialog.show(((Activity)_context).getFragmentManager(),"dialog");
            }
        });
        Button del = (Button) convertView.findViewById(R.id.delButton);
        del.setFocusable(false);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CaracteristiqueListe carac = (CaracteristiqueListe) getGroup(groupPosition);
                ((CharSheetDefSwipper) _context).getFragCaracList().callBackDeleteCarac(carac);
            }
        });
        return convertView;
    }
}
