package com.example.alumno.tpdalesio;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import java.util.ArrayList;

public class ListenerHistorial implements DialogInterface.OnClickListener {

    Activity activity;

    public ListenerHistorial(Activity a){
        activity= a;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        SharedPreferences historial= activity.getSharedPreferences("HistorialRss", Context.MODE_PRIVATE);

        ArrayList<String> lista = new ArrayList<String>();
        for(int j=0; j<5; j++)
        {
            String datoStr = historial.getString("key_"+j, "nada");
            if(!datoStr.equals("nada")) {
                lista.add(historial.getString("key_"+j, null));
            }
        }

        String url="";
        url= lista.get(which);

        if (!url.equals("")) {
            Thread hiloDatos = new Thread(new HiloTraerDatos(url, MainActivity.colaMensajes, false));
            hiloDatos.start();
        }
    }
}
