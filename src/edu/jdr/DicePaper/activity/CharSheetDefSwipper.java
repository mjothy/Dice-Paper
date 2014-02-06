package edu.jdr.DicePaper.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import edu.jdr.DicePaper.R;
import edu.jdr.DicePaper.fragments.CharSheetDef;
import edu.jdr.DicePaper.fragments.CharSheetDefCompoList;
import edu.jdr.DicePaper.utils.OnSwipeTouchListener;


/**
 * Created by paulyves on 2/2/14.
 */
public class CharSheetDefSwipper extends Activity {
    private String universeName;
    private FrameLayout mFrameLayout;
    private CharSheetDef fragCompoDef;
    private CharSheetDefCompoList fragCompoList;
    public OnSwipeTouchListener onSwipeTouchListener;
    private int currentFrag;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.char_sheet_def_swipper);
        universeName = getIntent().getExtras().getString("universeName");
        mFrameLayout = (FrameLayout) findViewById(R.id.Switcher);
        setupFragments();
        onSwipeTouchListener = new OnSwipeTouchListener(this){
            public void onSwipeRight() {
                switchFragment(1);
            }

            public void onSwipeLeft() {
                switchFragment(-1);
            }

            public void onSwipeTop() {
            }

            public void onSwipeBottom() {
            }
        };
        mFrameLayout.setOnTouchListener(onSwipeTouchListener);
        goToCompoDefine();

    }

    private void setupFragments() {
        if (this.fragCompoDef == null) {
            this.fragCompoDef = CharSheetDef.newInstance();
        }
        if(fragCompoList == null) {
            fragCompoList = CharSheetDefCompoList.newInstance();
        }
    }

    private void showFragment(final Fragment fragment) {
        if (fragment == null){
            return;
        }
        final FragmentManager fm = getFragmentManager();
        final FragmentTransaction ft = fm.beginTransaction();
        // We can also animate the changing of fragment
        ft.replace(R.id.Switcher, fragment);
        ft.setCustomAnimations(FragmentTransaction.TRANSIT_FRAGMENT_OPEN,FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        ft.commit();
    }

    public void goToCompoList() {
        currentFrag = 0;
        showFragment(fragCompoList);
    }
    public void goToCompoDefine(){
        currentFrag = 1;
        showFragment(fragCompoDef);
    }

    public void switchFragment(int i){
        currentFrag = (currentFrag+2+i)%2;
        switch (currentFrag){
            case 0 :
                goToCompoList();
                break;
            case 1 :
                goToCompoDefine();
                break;
        }
    }

    //to handle swipe and other touch listeners
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev){
        onSwipeTouchListener.getGestureDetector().onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }
}