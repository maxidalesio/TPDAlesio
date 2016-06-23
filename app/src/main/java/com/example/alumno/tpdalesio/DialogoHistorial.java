package com.example.alumno.tpdalesio;


import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import java.util.ArrayList;


public class DialogoHistorial extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());

        SharedPreferences historial= getActivity().getSharedPreferences("HistorialRss", Context.MODE_PRIVATE);

        ArrayList<String> enlaces= new ArrayList<String>();
        for(int j=0; j<5; j++)
        {
            String datoStr = historial.getString("key_"+j, "nada");
            if(!datoStr.equals("nada")) {
                enlaces.add(historial.getString("key_"+j, null));
            }
        }

        int cant= enlaces.size();
        String[]items= new String[cant];
        items= enlaces.toArray(items);

        ListenerHistorial listener= new ListenerHistorial(getActivity());
        builder.setItems(items, listener);
        builder.setNeutralButton("Cancelar", listener);

        AlertDialog ad= builder.create();

        return ad;
    }
}
