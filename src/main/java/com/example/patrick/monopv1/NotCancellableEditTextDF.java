package com.example.patrick.monopv1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Patrick on 9/2/2016.
 */
public class NotCancellableEditTextDF extends DialogFragment {
    private NotCancellableEditTextDFInterface notCancellableEditTextDFInterface;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        notCancellableEditTextDFInterface = (NotCancellableEditTextDFInterface) context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = "Number of Players";

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.amountopay, null);
        final EditText editText = (EditText) v.findViewById(R.id.editText_amountToPay);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setCancelable(false);
        builder.setView(v)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        notCancellableEditTextDFInterface.performActions(Integer.parseInt(editText.getText().toString()));
                    }
                });
        return builder.create();
    }

    public interface NotCancellableEditTextDFInterface{
        void performActions(int numberOfPlayers);
    }
}
