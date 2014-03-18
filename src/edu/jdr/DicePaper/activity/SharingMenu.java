package edu.jdr.DicePaper.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import edu.jdr.DicePaper.R;
import edu.jdr.DicePaper.models.DAO.UniversDAO;
import edu.jdr.DicePaper.utils.wifip2p.PeersAdapter;
import edu.jdr.DicePaper.utils.wifip2p.WiFiDirectBroadcastReceiver;

import java.util.ArrayList;

/**
 * Created by paulyves on 3/16/14.
 */
public class SharingMenu extends Activity {
    WifiP2pManager mManager;
    WifiP2pManager.Channel mChannel;
    WiFiDirectBroadcastReceiver mReceiver;
    IntentFilter mIntentFilter;
    ListView listPeer;
    ArrayAdapter<WifiP2pDevice> peersAdapter;
    Button refreshPeers;
    TextView connectedTo;
    Spinner univSpinner;
    ArrayList<String> listUniv = null;
    Button sendUniv;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sharing_menu);

        //setting the broadcast receiver
        mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mManager.initialize(this, getMainLooper(), null);
        mReceiver = new WiFiDirectBroadcastReceiver(mManager, mChannel, this);

        //init the intent filter
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        //refresh peers on click
        refreshPeers = (Button) findViewById(R.id.refreshPeer);
        refreshPeers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(getApplicationContext(), "Liste des autres utilisateurs rafraichie", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int reasonCode) {
                        Toast.makeText(getApplicationContext(), "Erreur lors de la recherche d'autre utilisateurs", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        listPeer = (ListView) findViewById(R.id.listPeers);
        peersAdapter = new PeersAdapter<WifiP2pDevice>(this,R.layout.list_univers_dialog, mReceiver.getPeers());
        listPeer.setAdapter(peersAdapter);
        listPeer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                WifiP2pDevice device = (WifiP2pDevice) parent.getItemAtPosition(position);
                mReceiver.connectTo(device);
            }
        });
        connectedTo = (TextView) findViewById(R.id.connectedTo);

        //initializing universe list for sendin purpose
        univSpinner = (Spinner) findViewById(R.id.univList);
        UniversDAO univManager = new UniversDAO(this);
        univManager.open();
        listUniv = univManager.getAllUnivers();
        univManager.close();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        adapter.addAll(listUniv);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        univSpinner.setAdapter(adapter);
        sendUniv = (Button) findViewById(R.id.sendUniv);
    }

    /* register the broadcast receiver with the intent values to be matched */
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mReceiver, mIntentFilter);
    }
    /* unregister the broadcast receiver */
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mReceiver);
    }

    public ArrayAdapter<WifiP2pDevice> getPeersAdapter() {
        return peersAdapter;
    }

    public void connectedTo(WifiP2pDevice device){
        connectedTo.setText(" " +device.toString().split("\n")[0]);
    }

    public void connectedTo(String message){
        connectedTo.setText(" " + message);
    }

}