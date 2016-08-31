package com.example.patrick.monopv1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Patrick on 8/30/2016.
 */
public class ListDF extends DialogFragment{

    private ListDFInterface listDFInterface;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listDFInterface = (ListDFInterface) context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle args = getArguments();
        final CharSequence[] cs = args.getCharSequenceArray("cs");
        String title = args.getString("title");
        final String method = args.getString("method");
//        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                String selectedName = getString(i);
//                listDFInterface.performActions(selectedName,method);
//            }
//        };

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setItems(cs, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String selectedName = (String) cs[i];
                listDFInterface.performActions(selectedName,method);
            }
        });

        return builder.create();
    }

    public interface ListDFInterface{
        void performActions(String selectedName, String method);
    }
}
