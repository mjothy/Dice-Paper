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
import edu.jdr.DicePaper.models.table.Valeur.UtilitaireValeur;

import java.util.List;

/**
 * Created by paulyves on 2/22/14.
 */
public class UtilitaireValeurAdapter<T> extends ArrayAdapter<T> {
    private final Context context;
    private int layoutResourceId;
    private final List<T> values;

    public UtilitaireValeurAdapter(Context context,int layoutResourceId, List<T> values) {
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
        //get the UtilitaireValeur
        final UtilitaireValeur tempValue = ((UtilitaireValeur) values.get(position));
        // set elements
        TextView utilTitle = (TextView) convertView.findViewById(R.id.utilTitle);
        TextView utilValue = (TextView) convertView.findViewById(R.id.utilValue);
        utilTitle.setText(tempValue.getRelatedList().getNom());
        utilValue.setText(tempValue.getValue());
        Button modif = (Button) convertView.findViewById(R.id.modifyButton);
        modif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CharSheetSwipper)context).getFragUtilValeur().callBackModifyUtil(tempValue);
            }
        });
        return convertView;
    }

}
