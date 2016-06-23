package com.example.alumno.tpdalesio;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by alumno on 02/06/2016.
 */
public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private List<Noticia> listaNoticias;

    public MyAdapter(List<Noticia> noticias){
        this.listaNoticias = noticias;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_noticia, parent, false);
        MyViewHolder holder = new MyViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvDescripcion.setText(listaNoticias.get(position).getDescripcion());
        holder.tvTitulo.setText(listaNoticias.get(position).getTitulo());
        holder.tvFecha.setText(listaNoticias.get(position).getFecha());
        //TODO llamar al hilo para poder ver la imagen
        //holder.ivImagen.setImageResource(R.drawable.rss_logo);
    }

    @Override
    public int getItemCount() {
        return listaNoticias.size();
    }
}
