package com.example.patrick.monopv1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

/**
 * Created by Patrick on 8/30/2016.
 */
public class P2PAmountDF extends DialogFragment{
    private P2PEditTextInterface p2PEditTextInterface;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        p2PEditTextInterface = (P2PEditTextInterface)context;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();
        String title = args.getString("title","");
        final String method = args.getString("method");
        final boolean toAllPlayers = args.getBoolean("toAllPlayers");
        Log.d("df","method = " + method);
        Log.d("df","title = " + title);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.amountopay, null);
        final EditText editText = (EditText) v.findViewById(R.id.editText_amountToPay);
        editText.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setView(v)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        p2PEditTextInterface.performActions(Integer.parseInt(editText.getText().toString()),method,toAllPlayers);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        return builder.create();
    }

    public interface P2PEditTextInterface {
        void performActions(int price, String method, boolean toAllPlayers);
    }
}
