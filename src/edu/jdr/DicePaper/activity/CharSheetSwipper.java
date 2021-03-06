package edu.jdr.DicePaper.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import edu.jdr.DicePaper.R;
import edu.jdr.DicePaper.fragments.CharSheet.*;
import edu.jdr.DicePaper.utils.OnSwipeTouchListener;

/**
 * Created by mario on 20/02/14.
 */
public class CharSheetSwipper extends Activity{
    private CharSheetJaugeValeur fragJaugeValeur;
    private CharSheetUtilValeur fragUtilValeur;
    private CharSheetCaracValeur fragCaracValeur;
    private CharSheetCompValeur fragCompValeur;
    private CharSheetInventory fragInventory;
    private DiceRoll fragDiceRoll;
    private OnSwipeTouchListener onSwipeTouchListener;
    private int currentFrag;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swipper);
        FrameLayout mFrameLayout = (FrameLayout) findViewById(R.id.Switcher);
        setupFragments();
        onSwipeTouchListener = new OnSwipeTouchListener(this){
            public void onSwipeRight() {
                switchFragment(-1);
            }

            public void onSwipeLeft() {
                switchFragment(1);
            }
        };
        mFrameLayout.setOnTouchListener(onSwipeTouchListener);
        goToCaracList(1);

    }

    private void setupFragments() {
        if(fragJaugeValeur == null) {
            fragJaugeValeur = CharSheetJaugeValeur.newInstance();
        }
        if(fragUtilValeur == null) {
            fragUtilValeur = CharSheetUtilValeur.newInstance();
        }
        if(fragCaracValeur == null){
            fragCaracValeur = CharSheetCaracValeur.newInstance();
        }
        if(this.fragCompValeur == null){
            fragCompValeur = CharSheetCompValeur.newInstance();
        }
        if(this.fragInventory == null){
            fragInventory = CharSheetInventory.newInstance();
        }
        if(this.fragDiceRoll == null){
            fragDiceRoll = DiceRoll.newInstance();
        }
    }

    private void showFragment(final Fragment fragment, int i) {
        if (fragment == null){
            return;
        }
        final FragmentManager fm = getFragmentManager();
        final FragmentTransaction ft = fm.beginTransaction();
        // We can also animate the changing of fragment
        if(i>0){
            ft.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right);
        }else{
            ft.setCustomAnimations(R.animator.slide_in_right, R.animator.slide_out_left);
        }
        ft.replace(R.id.Switcher, fragment);
        ft.commit();
    }


    public void goToCaracList(int i){
        currentFrag = 0;
        showFragment(fragCaracValeur, i);
    }

    public void goToUtilList(int i){
        currentFrag = 1;
        showFragment(fragUtilValeur, i);
    }

    public void goToJaugeList(int i) {
        currentFrag = 2;
        showFragment(fragJaugeValeur, i);
    }

    public void goToCompList(int i){
        currentFrag = 3;
        showFragment(fragCompValeur, i);
    }

    public void goToInventory(int i){
        currentFrag = 4;
        showFragment(fragInventory, i);
    }

    public void goToDiceRoll(int i){
        currentFrag = 5;
        showFragment(fragDiceRoll, i);
    }

    public void switchFragment(int i){
        currentFrag = (currentFrag+6+i)%6;
        switch (currentFrag){
            case 0 :
                goToCaracList(i);
                break;
            case 1 :
                goToUtilList(i);
                break;
            case 2 :
                goToJaugeList(i);
                break;
            case 3 :
                goToCompList(i);
                break;
            case 4 :
                goToInventory(i);
                break;
            case 5 :
                goToDiceRoll(i);
                break;
        }
    }

    //to handle swipe and other touch listeners
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev){
        onSwipeTouchListener.getGestureDetector().onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    public CharSheetCaracValeur getFragCaracValeur() {
        return fragCaracValeur;
    }

    public CharSheetUtilValeur getFragUtilValeur() {
        return fragUtilValeur;
    }

    public CharSheetJaugeValeur getFragJaugeValeur() {
        return fragJaugeValeur;
    }

    public CharSheetCompValeur getFragCompValeur() {
        return fragCompValeur;
    }

    public CharSheetInventory getFragInventaire() {
        return fragInventory;
    }
}
