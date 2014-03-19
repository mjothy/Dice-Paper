package edu.jdr.DicePaper.fragments.CharSheet;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import edu.jdr.DicePaper.R;
import edu.jdr.DicePaper.activity.UniversMaster;
import edu.jdr.DicePaper.fragments.CharSheet.UpdateDialog.UpdateUtilDialog;
import edu.jdr.DicePaper.models.DAO.Valeur.JaugeValeurDAO;
import edu.jdr.DicePaper.models.DAO.Valeur.UtilitaireValeurDAO;
import edu.jdr.DicePaper.models.table.FichePersonnage;
import edu.jdr.DicePaper.models.table.Valeur.UtilitaireValeur;
import edu.jdr.DicePaper.utils.UtilitaireValeurAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by mario on 25/02/14.
 */
public class DiceRoll extends Fragment {
    private String universeName;
    private String charName;
    private Button validate;
    private NumberPicker nbDice;
    private NumberPicker typeDice;
    private CheckBox sortDice;
    private CheckBox sumDice;
    private TextView viewResults;
    private Random RNG;
    private Bundle tempConf = new Bundle();
    /**
     * Method to instanciate this fragment
     * Only this method should be used to create this kind of fragment
     * @return
     */
    public static DiceRoll newInstance(){
        DiceRoll fragment = new DiceRoll();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dice_roll, container, false);
        universeName = getActivity().getIntent().getExtras().getString("universeName");
        charName = getActivity().getIntent().getExtras().getString("charName");
        TextView title = (TextView) v.findViewById(R.id.univTitle);
        title.setText(charName + " (" + universeName + ")");

        validate = (Button) v.findViewById(R.id.validateRoll);

        nbDice = (NumberPicker) v.findViewById(R.id.editNbDice);
        nbDice.setMinValue(1);
        nbDice.setMaxValue(50);
        if(tempConf.containsKey("nbDice")){
            nbDice.setValue(tempConf.getInt("nbDice"));
        }
        nbDice.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                tempConf.putInt("nbDice", newVal);
            }
        });

        typeDice = (NumberPicker) v.findViewById(R.id.typeDice);
        typeDice.setMinValue(2);
        typeDice.setMaxValue(100);
        if(tempConf.containsKey("typeDice")){
            typeDice.setValue(tempConf.getInt("typeDice"));
        } else {
            typeDice.setValue(6);
        }
        typeDice.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                tempConf.putInt("typeDice", typeDice.getValue());
            }
        });
        sortDice = (CheckBox) v.findViewById(R.id.sortDice);
        sumDice = (CheckBox) v.findViewById(R.id.sumDice);
        validate.setOnClickListener(validateListener);

        viewResults = (TextView) v.findViewById(R.id.results);
        if(tempConf.containsKey("results")){
            viewResults.setText(tempConf.getString("results"));
        }
        return v;
    }

    private View.OnClickListener validateListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int numberDice = nbDice.getValue();
            int typeOfDice = typeDice.getValue();
            String results = diceRolls(numberDice, typeOfDice);
            tempConf.putString("results", results);
            viewResults.setText(results);
        }
    };

    /**
     * Rolls 1 dice of the type specified
     * @param typeDice the type of the dice
     * @return a random number between 1 and typeDice
     */
    private int diceRoll(int typeDice){
        if(RNG==null){
            RNG = new Random(System.currentTimeMillis());
        }
        return (RNG.nextInt(typeDice)+1);
    }

    /**
     * Rolls a number nbDice of dice of the type specified
     * @param nbDice the number of dice to roll
     * @param typeDice the type of the dices to roll
     * @return A string which gives the results (only for graphical use)
     */
    private String diceRolls(int nbDice, int typeDice){
        String result = "";
        int somme=0;
        //if there is a few dice the string is [result][result]... ; else it is numberOfOccurence[result] ...
        if(nbDice<15){
            ArrayList<Integer> resultArray = new ArrayList<Integer>();
            //feeding the arraylist and computing the sum
            for(int i=0; i<nbDice; i++){
                int rand = diceRoll(typeDice);
                resultArray.add(rand);
                somme += rand;
            }
            //sorting the array for visibility
            if(sortDice.isChecked()){
                Collections.sort(resultArray);
            }
            //printing the string
            for(int i=0; i<nbDice; i++){
                result += "[" + resultArray.get(i) + "]  ";
            }
        }else{
            int[] resultArray = new int[typeDice];
            //feeding the array and computing the sum
            for(int i=0; i<nbDice; i++){;
                int temp = diceRoll(typeDice);
                resultArray[temp-1]++;
                somme += temp;
            }
            //printing the string
            for (int i=0; i<typeDice; i++){
                if(resultArray[i]!=0){
                    result += resultArray[i] + "x[" + (i+1) + "]  ";
                }
            }
        }
        if(sumDice.isChecked()){
            //printing the sum
            result+="\n Somme = "+ somme;
        }
        return result;
    }
}