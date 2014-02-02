package edu.jdr.DicePaper.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.FrameLayout;
import edu.jdr.DicePaper.R;


/**
 * Created by paulyves on 2/2/14.
 */
public class CharSheetDefSwipper extends Activity {
    private String universeName;
    private FrameLayout mFrameLayout;
    private CharSheetDef fragCompoDef;
    private CharSheetDefCompoList fragCompoList;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.char_sheet_def_swipper);
        universeName = getIntent().getExtras().getString("universeName");
        mFrameLayout = (FrameLayout) findViewById(R.id.Switcher);
        setupFragments();
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
        ft.commit();
    }

    public void goToCompoList() {
        showFragment(fragCompoList);
    }
    public void goToCompoDefine(){
        showFragment(fragCompoDef);
    }

}