package com.example.alumno.tpdalesio;

/**
 * Created by Maxi on 23/6/2016.
 */
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;


public class DialogoHistorial extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());

        SharedPreferences preferences= getActivity().getSharedPreferences("HistorialRss", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor= preferences.edit();
        editor.remove("");
        editor.commit();
        Map preferencias= preferences.getAll();
        ArrayList<String> enlaces= new ArrayList<String>();
        for (Iterator it = preferencias.values().iterator(); it.hasNext();){
            enlaces.add((String) it.next());
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
