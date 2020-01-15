package com.example.mauriciogodinez.appmiscontenidosv2.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.mauriciogodinez.appmiscontenidosv2.R;


/**
 * Created by mauriciogodinez on 31/03/17.
 */

public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    // each data item is just a string in this case
    private TextView viewNumero;
    private TextView viewFecha;
    private TextView viewTipo;
    private TextView viewDuracion;
    private TextView viewGeocode;
    private TextView viewLlamada;

    public MyViewHolder(View view) {
        super(view);

        viewNumero = view.findViewById(R.id.text_view_numero);
        viewFecha = view.findViewById(R.id.text_view_fecha);
        viewTipo = view.findViewById(R.id.text_view_tipo);
        viewDuracion = view.findViewById(R.id.text_view_duracion);
        viewGeocode = view.findViewById(R.id.text_view_geocode);
        viewLlamada = view.findViewById(R.id.text_view_llamada);

        view.setOnClickListener(this);
    }

    public void setViewNumero(String viewNumero) {
        this.viewNumero.setText(viewNumero);
    }

    public void setViewFecha(String viewFecha) {
        this.viewFecha.setText(viewFecha);
    }

    public void setViewTipo(String viewTipo) {
        this.viewTipo.setText(viewTipo);
    }

    public void setViewDuracion(String viewDuracion) {
        this.viewDuracion.setText(viewDuracion);
    }

    public void setViewGeocode(String viewGeocode) {
        this.viewGeocode.setText(viewGeocode);
    }

    public void setViewLlamada(String viewLlamada) {
        this.viewLlamada.setText(viewLlamada);
    }

    @Override
    public void onClick(View view) {
        String data = viewNumero.getText().toString() + "\n" +
                viewFecha.getText().toString() + "\n" +
                viewTipo.getText().toString() + "\n" +
                viewDuracion.getText().toString() + "\n" +
                viewGeocode.getText().toString() + "\n" +
                viewLlamada.getText().toString() + "\n";

        MyAdapter.mCallback.clickListenerFromAdapterItem(data);
    }
}