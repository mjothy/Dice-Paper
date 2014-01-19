package edu.jdr.DicePaper;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by paulyves on 1/19/14.
 */
public class MyDialogFragment extends DialogFragment {
    Context mContext;
    public MyDialogFragment() {
        mContext = getActivity();
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setMessage("Are you sure?").setPositiveButton("Ok", null)
                .setNegativeButton("No way", null).create();
    }
}
