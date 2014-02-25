package edu.jdr.DicePaper.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import edu.jdr.DicePaper.R;
import edu.jdr.DicePaper.fragments.CharSheetDef.*;
import edu.jdr.DicePaper.utils.OnSwipeTouchListener;


/**
 * Created by paulyves on 2/2/14.
 */
public class CharSheetDefSwipper extends Activity {
    private String universeName;
    private FrameLayout mFrameLayout;
    private CharSheetDef fragCompoDef;
    private CharSheetDefJaugeList fragJaugeList;
    private CharSheetDefUtilList fragUtilList;
    private CharSheetDefCaracList fragCaracList;
    private CharSheetDefCompList fragCompList;
    public OnSwipeTouchListener onSwipeTouchListener;
    private int currentFrag;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swipper);
        universeName = getIntent().getExtras().getString("universeName");
        mFrameLayout = (FrameLayout) findViewById(R.id.Switcher);
        setupFragments();
        onSwipeTouchListener = new OnSwipeTouchListener(this){
            public void onSwipeRight() {
                switchFragment(-1);
            }

            public void onSwipeLeft() {
                switchFragment(1);
            }

            public void onSwipeTop() {
            }

            public void onSwipeBottom() {
            }
        };
        mFrameLayout.setOnTouchListener(onSwipeTouchListener);
        goToCompoDefine(1);

    }

    private void setupFragments() {
        if (this.fragCompoDef == null) {
            this.fragCompoDef = CharSheetDef.newInstance();
        }
        if(fragJaugeList == null) {
            fragJaugeList = CharSheetDefJaugeList.newInstance();
        }
        if(fragUtilList == null) {
            fragUtilList = CharSheetDefUtilList.newInstance();
        }
        if(this.fragCaracList == null){
            fragCaracList = CharSheetDefCaracList.newInstance();
        }
        if(this.fragCompList == null){
            fragCompList = CharSheetDefCompList.newInstance();
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

    public void goToJaugeList(int i) {
        currentFrag = 2;
        showFragment(fragJaugeList, i);
    }
    public void goToCompoDefine(int i){
        currentFrag = 0;
        showFragment(fragCompoDef, i);
    }
    public void goToUtilList(int i){
        currentFrag = 4;
        showFragment(fragUtilList, i);
    }
    public void goToCaracList(int i){
        currentFrag = 1;
        showFragment(fragCaracList, i);
    }
    public void goToCompList(int i){
        currentFrag = 3;
        showFragment(fragCompList, i);
    }

    public void switchFragment(int i){
        currentFrag = (currentFrag+5+i)%5;
        switch (currentFrag){
            case 0 :
                goToCompoDefine(i);
                break;
            case 1 :
                goToCaracList(i);
                break;
            case 2 :
                goToJaugeList(i);
                break;
            case 3 :
                goToCompList(i);
                break;
            case 4 :
                goToUtilList(i);
                break;
        }
    }

    //to handle swipe and other touch listeners
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev){
        onSwipeTouchListener.getGestureDetector().onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    public CharSheetDefCaracList getFragCaracList() {
        return fragCaracList;
    }
}