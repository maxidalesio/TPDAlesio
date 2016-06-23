package com.example.alumno.tpdalesio;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class ListenerHistorial implements DialogInterface.OnClickListener {
    Activity activity;

    public ListenerHistorial(Activity a){
        activity= a;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        SharedPreferences historial= activity.getSharedPreferences("HistorialRss", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor= historial.edit();

        /*
        Map preferencias= historial.getAll();
        String[][] rss= new String[5][2];
        int i= 0;
        for (Iterator it = preferencias.keySet().iterator(); it.hasNext();){
            rss[i][0]= (String) it.next();
            rss[i][1]= (String) preferencias.get(rss[i][0]);
            i++;
        }
        */
        ArrayList<String> lista = new ArrayList<String>();
        for(int j=0; j<5; j++)
        {
            String datoStr = historial.getString("key_"+j, "nada");
            if(!datoStr.equals("nada")) {
                lista.add(historial.getString("key_"+j, null));
            }
        }

        String url="";
        switch (which){
            case 0:
                url= lista.get(0);
                break;
            case 1:
                url= lista.get(1);
                break;
            case 2:
                url= lista.get(2);
                break;
            case 3:
                url= lista.get(3);
                break;
            case 4:
                url= lista.get(4);
                break;
            case AlertDialog.BUTTON_NEUTRAL:
                Log.d("ListenerMenu", "Cancelar");
                break;
        }
        editor.commit();

        if (!url.equals("")) {
            Thread hiloDatos = new Thread(new HiloTraerDatos(url, MainActivity.colaMensajes, false));
            hiloDatos.start();
        }
    }
}
