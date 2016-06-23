package com.example.alumno.tpdalesio;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        }
        return false;
    }
}
