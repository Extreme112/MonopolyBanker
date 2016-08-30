package com.example.patrick.monopv1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by Patrick on 8/29/2016.
 */
public class PurchaseDialog extends DialogFragment{
    private PurchaseDialogCommunicator purchaseDialogCommunicator;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        purchaseDialogCommunicator = (PurchaseDialogCommunicator)context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle args = getArguments();
        //String title = args.getString("title"," ");
        String message = args.getString("message"," ");
        final String propertyName  = args.getString("propertyName"," ");
        final int purchasePrice = args.getInt("purchasePrice",0);


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setMessage(message);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //needs to know the player purchasing, property name, property price
                purchaseDialogCommunicator.performActions(purchasePrice,propertyName);

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        return builder.create();
    }


    public interface PurchaseDialogCommunicator{
        void performActions(int purchasePrice, String propertyName);
    }
}

