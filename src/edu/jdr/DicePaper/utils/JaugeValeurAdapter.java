package edu.jdr.DicePaper.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import edu.jdr.DicePaper.R;
import edu.jdr.DicePaper.activity.CharSheetSwipper;
import edu.jdr.DicePaper.models.table.Valeur.JaugeValeur;
import edu.jdr.DicePaper.models.table.Valeur.UtilitaireValeur;

import java.util.List;

/**
 * Created by paulyves on 2/25/14.
 */
public class JaugeValeurAdapter<T> extends ArrayAdapter<T> {
    private final Context context;
    private int layoutResourceId;
    private final List<T> values;

    public JaugeValeurAdapter(Context context,int layoutResourceId, List<T> values) {
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
        //get the JaugeValeur
        final JaugeValeur tempValue = ((JaugeValeur) values.get(position));
        // set elements
        TextView jaugeTitle = (TextView) convertView.findViewById(R.id.jaugeTitle);
        TextView jaugeMaxValue = (TextView) convertView.findViewById(R.id.jaugeMax);
        NumberPicker jaugeValue = (NumberPicker) convertView.findViewById(R.id.jaugeValue);
        jaugeTitle.setText(tempValue.getRelatedList().getNom());
        jaugeMaxValue.setText(String.valueOf(tempValue.getMaxValue()));

        jaugeValue.setMaxValue(tempValue.getRelatedList().getMax()-tempValue.getRelatedList().getMin());
        jaugeValue.setMinValue(0);
        jaugeValue.setValue(tempValue.getCurrentValue()-tempValue.getRelatedList().getMin());
        //this is used to get both positive and negative values on the NumberPicker
        jaugeValue.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int index) {
                return Integer.toString(index + tempValue.getRelatedList().getMin());
            }
        });
        jaugeValue.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        jaugeValue.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                tempValue.setCurrentValue(newVal+tempValue.getRelatedList().getMin());
                ((CharSheetSwipper)context).getFragJaugeValeur().callBackSaveJauge(tempValue);
            }
        });

        Button modif = (Button) convertView.findViewById(R.id.modifyButton);
        modif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CharSheetSwipper)context).getFragJaugeValeur().callBackModifyJauge(tempValue);
            }
        });
        return convertView;
    }
}
