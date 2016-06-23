package com.example.alumno.tpdalesio;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Noticia> noticias = new ArrayList<Noticia>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        Noticia n = new Noticia();

        n.setFecha("26/05/2016");
        n.setDescripcion("save me pls");
        n.setTitulo("Cierre de cuatrimestre");

        Noticia n2 = new Noticia();
        n2.setFecha("27/05/2016");
        n2.setDescripcion("can't save you");
        n2.setTitulo("Cierre de cuatrimestre 2");

        noticias.add(n);
        noticias.add(n2);
        */
        RecyclerView list = (RecyclerView) findViewById(R.id.rvLista);

        MyAdapter miAdapter = new MyAdapter(noticias);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        list.setLayoutManager(layoutManager);

        list.setAdapter(miAdapter);
    }
}
