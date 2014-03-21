package edu.jdr.DicePaper.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import edu.jdr.DicePaper.R;
import edu.jdr.DicePaper.activity.CharSheetSwipper;
import edu.jdr.DicePaper.models.table.Valeur.Equipement;

import java.util.List;

/**
 * Created by mario on 04/03/14.
 */
public class InventaireAdapter<T> extends ArrayAdapter<T> {
    private final Context context;
    private int layoutResourceId;
    private final List<T> values;

    public InventaireAdapter(Context context,int layoutResourceId, List<T> values) {
        super(context, layoutResourceId, values);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layoutResourceId, parent, false);
        }
        //get the Inventaire
        final Equipement tempValue = ((Equipement) values.get(position));
        // set elements
        TextView equipementTitle = (TextView) convertView.findViewById(R.id.utilTitle);
        TextView equipementValue = (TextView) convertView.findViewById(R.id.utilValue);
        equipementTitle.setText(tempValue.getNom());
        equipementValue.setText(tempValue.getDescription());

        Button modif = (Button) convertView.findViewById(R.id.modifyButton);
        modif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CharSheetSwipper)context).getFragInventaire().callBackModifyUtil(tempValue);
            }
        });

        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ((CharSheetSwipper)context).getFragInventaire().callBackDeleteEquipement(tempValue);
                return false;
            }
        });

        return convertView;
    }

}
