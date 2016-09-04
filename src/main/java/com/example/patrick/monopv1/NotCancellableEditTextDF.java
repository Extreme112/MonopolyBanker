package com.example.patrick.monopv1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Patrick on 9/2/2016.
 */
public class NotCancellableEditTextDF extends DialogFragment {
    private NotCancellableEditTextDFInterface notCancellableEditTextDFInterface;
    EditText editText;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        notCancellableEditTextDFInterface = (NotCancellableEditTextDFInterface) context;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = "Number of Players";

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.amountopay, null);
        editText = (EditText) v.findViewById(R.id.editText_amountToPay);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setCancelable(false);
        builder.setView(v)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
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
                        notCancellableEditTextDFInterface.performActions(Integer.parseInt(editText.getText().toString()));
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

    public interface NotCancellableEditTextDFInterface{
        void performActions(int numberOfPlayers);
    }
}
