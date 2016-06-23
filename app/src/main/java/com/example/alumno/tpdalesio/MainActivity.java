package com.example.alumno.tpdalesio;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewDebug;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements ClickItem, Handler.Callback {

    ArrayList<Noticia> noticias = new ArrayList<Noticia>();
    MyAdapter miAdapter;
    static Handler colaMensajes;
    ExecutorService ex;
    SharedPreferences historial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**Shared Preference para recordar enlaces**/
        historial= getSharedPreferences("HistorialRss", Context.MODE_PRIVATE);

        /**Handler y Pool de Hilos**/
        colaMensajes= new Handler(this);
        ex= Executors.newFixedThreadPool(5);

        /**Recycler view y Adapter**/
        RecyclerView list = (RecyclerView) findViewById(R.id.rvLista);
        miAdapter = new MyAdapter(noticias, this, colaMensajes, ex);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(layoutManager);
        list.setAdapter(miAdapter);

        /**Listener a Main Activity**/
        ListenerMyActivity listener= new ListenerMyActivity(this, colaMensajes, ex);
        Button btnLeer= (Button) findViewById(R.id.RSSButton);
        btnLeer.setOnClickListener(listener);
    }

    @Override
    public void click(int posicion) {
        Noticia n= noticias.get(posicion);
        Intent i= new Intent(this, NoticiaActivity.class);
        i.putExtra("url", n.getLink());
        startActivity(i);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.arg1){
            case 1:
                noticias.clear();
                noticias.addAll((ArrayList<Noticia>) msg.obj);
                miAdapter.notifyDataSetChanged();
                break;
            case 2:
                byte[] imagen= (byte[])msg.obj;
                int posicion= msg.arg2;
                Noticia n= noticias.get(posicion);
                n.setImagen(imagen);
                miAdapter.notifyDataSetChanged();
                break;
            case 3:
                manejarHistorial(msg.obj.toString());
                break;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.itHistorial:
                DialogoHistorial dHistorial= new DialogoHistorial();
                dHistorial.show(getFragmentManager(), "Historial");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void manejarHistorial (String url) {
        if (url.startsWith("http://")) {
            historial = getSharedPreferences("HistorialRss", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = historial.edit();
            ArrayList<String> lista = new ArrayList<String>();
            boolean yaExiste = false;
            for (int i = 0; i < 5; i++) {
                String datoStr = historial.getString("key_" + i, "nada");
                if (!datoStr.equals("nada")) {
                    lista.add(historial.getString("key_" + i, null));
                }
            }
            for (String s : lista) {
                if (s.equals(url))
                    yaExiste = true;
            }
            if (!yaExiste) {
                editor.clear();
                editor.putString("key_0", url);
                //Log.d("Historial"+0, url);
                for (int i = 0; i < 4; i++) {
                    Log.d("Index", String.valueOf(i));
                    if (lista.size() != 0) {
                        int key = i + 1;
                        editor.putString("key_" + key, lista.get(0));
                        //Log.d("Historial"+key, lista.get(0));
                        lista.remove(0);
                    }
                }

            }
            editor.commit();
        }
    }
}
