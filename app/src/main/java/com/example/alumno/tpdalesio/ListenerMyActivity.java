package com.example.alumno.tpdalesio;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
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
            Message mensaje = new Message();
            mensaje.obj = tvRss.getText().toString();
            mensaje.arg1 = 3;
            colaMensajes.sendMessage(mensaje);
            hiloDatos= new Thread(new HiloTraerDatos(tvRss.getText().toString(), colaMensajes, false));
            executorService.execute(hiloDatos);
        }
    }
}
