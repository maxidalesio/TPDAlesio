package com.example.alumno.tpdalesio;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by alumno on 02/06/2016.
 */
public class MyViewHolder extends RecyclerView.ViewHolder {

    ImageView ivImagen;
    TextView tvFecha;
    TextView tvTitulo;
    TextView tvDescripcion;
    int Position;

    public MyViewHolder(View itemView) {
        super(itemView);
        tvFecha = (TextView) itemView.findViewById(R.id.tvFecha);
        tvTitulo = (TextView) itemView.findViewById(R.id.tvTitulo);
        tvDescripcion = (TextView) itemView.findViewById(R.id.tvDescripcion);
        ivImagen = (ImageView) itemView.findViewById(R.id.imgvNoticia);
    }
}
