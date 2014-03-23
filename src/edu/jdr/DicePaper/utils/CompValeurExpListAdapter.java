package edu.jdr.DicePaper.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import edu.jdr.DicePaper.R;
import edu.jdr.DicePaper.activity.CharSheetSwipper;
import edu.jdr.DicePaper.models.table.Valeur.CompetenceValeur;
import edu.jdr.DicePaper.models.table.Valeur.Specialisation;

import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by paulyves on 2/25/14.
 */
public class CompValeurExpListAdapter<T,S> extends ExpListAdapter {
    public CompValeurExpListAdapter(Context context, List<T> listDataHeader,
                                    TreeMap<T, List<S>> listChildData) {
        super(context,listDataHeader,listChildData);
    }
    public CompValeurExpListAdapter(Context context, TreeMap<T, List<S>> listChildData) {
        super(context,listChildData);
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final Specialisation spe = (Specialisation) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_spe_component, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.childs);
        txtListChild.setText(spe.toString());
        Button modify = (Button) convertView.findViewById(R.id.modifyButton);
        modify.setFocusable(false);
        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CharSheetSwipper) _context).getFragCompValeur().callBackModifySpe(spe);
            }
        });
        Button delete = (Button) convertView.findViewById(R.id.delButton);
        delete.setFocusable(false);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CharSheetSwipper) _context).getFragCompValeur().callBackDeleteSpe(spe);
            }
        });
        return convertView;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        final CompetenceValeur comp = (CompetenceValeur) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_comp_valeur_component, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.components);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(comp.getRelatedList().toString()+" : "+comp.getBaseValue());
        Button modify = (Button) convertView.findViewById(R.id.modifyButton);
        modify.setFocusable(false);
        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CharSheetSwipper) _context).getFragCompValeur().callBackModifyComp(comp);
            }
        });
        Button addSpe = (Button) convertView.findViewById(R.id.addSpeButton);
        addSpe.setFocusable(false);
        addSpe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CharSheetSwipper) _context).getFragCompValeur().callBackAddSpe(comp);
            }
        });
        return convertView;
    }
}
