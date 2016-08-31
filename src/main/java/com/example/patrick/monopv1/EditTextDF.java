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
import android.widget.EditText;

/**
 * Created by Patrick on 8/30/2016.
 */
public class EditTextDF extends DialogFragment{
    private EditTextDFInterface editTextDFInterface;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        editTextDFInterface = (EditTextDFInterface)context;
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

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setView(v)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        editTextDFInterface.performActions(Integer.parseInt(editText.getText().toString()),method,toAllPlayers);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        return builder.create();
    }

    public interface EditTextDFInterface {
        void performActions(int price, String method, boolean toAllPlayers);
    }
}
