package edu.jdr.DicePaper.utils.wifip2p;

import android.content.Context;
import android.net.wifi.p2p.WifiP2pDevice;
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
public class PeersAdapter<T> extends ArrayAdapter<T> {
    private final Context context;
    private int layoutResourceId;
    private final List<T> values;

    public PeersAdapter(Context context, int layoutResourceId, List<T> values) {
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
        //get the device
        final WifiP2pDevice tempValue = ((WifiP2pDevice) values.get(position));
        // set elements
        TextView device = (TextView) convertView.findViewById(R.id.univList);
        device.setText(tempValue.toString().split("\n")[0]);

        return convertView;
    }
}
