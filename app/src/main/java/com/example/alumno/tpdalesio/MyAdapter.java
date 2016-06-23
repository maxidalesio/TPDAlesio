package com.example.alumno.tpdalesio;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * Created by alumno on 02/06/2016.
 */
public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private ArrayList<Noticia> listaNoticias;
    MyViewHolder holder;
    ClickItem objClick;
    Handler colaMensajes;
    ExecutorService executorService;

    public MyAdapter(ArrayList<Noticia> noticias, ClickItem ci, Handler h, ExecutorService ex){
        this.listaNoticias = noticias;
        objClick= ci;
        colaMensajes= h;
        executorService= ex;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_noticia, parent, false);
        holder = new MyViewHolder(v, this.objClick);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Noticia n= listaNoticias.get(position);
        holder.position = position;
        holder.tvDescripcion.setText(n.getDescripcion());
        holder.tvTitulo.setText(n.getTitulo());
        holder.tvFecha.setText(n.getFecha());
        //TODO llamar al hilo para poder ver la imagen
        if (n.getImagen() != null){
            Bitmap bmp = BitmapFactory.decodeByteArray(n.getImagen(), 0, n.getImagen().length);
            holder.ivImagen.setImageBitmap(bmp);
        } else {
            if (n.getImgPath() != null) {
                holder.ivImagen.setImageResource(R.drawable.rss_logo);
                Thread hiloImagen = new Thread(new HiloTraerDatos(n.getImgPath(), colaMensajes, true, position));
                executorService.execute(hiloImagen);
            }
        }
    }

    @Override
    public int getItemCount() {
        return listaNoticias.size();
    }
}
