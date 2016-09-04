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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Patrick on 8/30/2016.
 */
public class P2PAmountDF extends DialogFragment{
    private P2PEditTextInterface p2PEditTextInterface;
    EditText editText;
    String method;
    boolean toAllPlayers;
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
        method = args.getString("method");
        toAllPlayers = args.getBoolean("toAllPlayers");
        Log.d("df","method = " + method);
        Log.d("df","title = " + title);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.amountopay, null);
        editText = (EditText) v.findViewById(R.id.editText_amountToPay);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setView(v)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                    }

                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();    //super.onStart() is where dialog.show() is actually called on the underlying dialog, so we have to do it after this point
        AlertDialog d = (AlertDialog)getDialog();
        if(d != null)
        {
            Button positiveButton = d.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Boolean wantToCloseDialog = false;
                    //Do stuff, possibly set wantToCloseDialog to true then...
                    if(!editText.getText().toString().equals("") && Integer.parseInt(editText.getText().toString()) > 0){
                        p2PEditTextInterface.performActions(Integer.parseInt(editText.getText().toString()),method,toAllPlayers);
                        wantToCloseDialog = true;
                    } else {
                        Toast.makeText(getActivity(),"Enter amount greater than 0.",Toast.LENGTH_SHORT).show();
                    }

                    if(wantToCloseDialog)
                        dismiss();
                    //else dialog stays open. Make sure you have an obvious way to close the dialog especially if you set cancellable to false.
                }
            });
        }

    }

    public interface P2PEditTextInterface {
        void performActions(int price, String method, boolean toAllPlayers);
    }
}
