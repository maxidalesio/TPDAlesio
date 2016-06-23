package com.example.alumno.tpdalesio;

import android.app.Activity;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.ExecutorService;

public class ListenerMyActivity implements View.OnClickListener {
    Activity act;
    Thread hiloDatos;
    Handler colaMensajes;
    ExecutorService executorService;

    public ListenerMyActivity (Activity a, Handler h, ExecutorService e){
        act= a;
        colaMensajes= h;
        executorService= e;
    }

    @Override
    public void onClick(View v) {
        if (v.getId()== R.id.RSSButton){
            TextView tvRss= (TextView) act.findViewById(R.id.RSSLink);
            hiloDatos= new Thread(new HiloTraerDatos(tvRss.getText().toString(), colaMensajes, false));
            executorService.execute(hiloDatos);
        }
    }
}
