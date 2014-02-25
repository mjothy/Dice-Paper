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
import edu.jdr.DicePaper.models.table.Valeur.CaracteristiqueValeur;
import edu.jdr.DicePaper.models.table.Valeur.ModificateurValeur;

import java.util.HashMap;
import java.util.List;

/**
 * Created by mario on 20/02/14.
 */
public class CaracValeurExpListAdapter<T,S> extends ExpListAdapter {
    public CaracValeurExpListAdapter(Context context, List<T> listDataHeader,
                                  HashMap<T, List<S>> listChildData) {
        super(context,listDataHeader,listChildData);
    }
    public CaracValeurExpListAdapter(Context context, HashMap<T, List<S>> listChildData) {
        super(context,listChildData);
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = getChild(groupPosition, childPosition).toString();

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_carac_valeur_group, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.components);
        txtListChild.setText(childText);

        Button modify = (Button) convertView.findViewById(R.id.modifyButton);
        modify.setFocusable(false);
        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModificateurValeur modif = (ModificateurValeur) getChild(groupPosition, childPosition);
                ((CharSheetSwipper) _context).getFragCaracValeur().callBackModifyModif(modif);
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
            convertView = infalInflater.inflate(R.layout.list_carac_valeur_group, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.components);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);
        Button modify = (Button) convertView.findViewById(R.id.modifyButton);
        modify.setFocusable(false);
        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CaracteristiqueValeur carac = (CaracteristiqueValeur) getGroup(groupPosition);
                ((CharSheetSwipper) _context).getFragCaracValeur().callBackModifyCarac(carac);
            }
        });
        return convertView;
    }
}