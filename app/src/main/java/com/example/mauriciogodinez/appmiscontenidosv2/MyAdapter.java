package com.example.mauriciogodinez.appmiscontenidosv2;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private List<InformacionLlamada> contactList;

    public MyAdapter(List<InformacionLlamada> contactList) {
        this.contactList = contactList;
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    @Override
    public void onBindViewHolder(MyViewHolder contactViewHolder, int i) {
        InformacionLlamada informacionContacto = contactList.get(i);

        contactViewHolder.setViewNumero(informacionContacto.getNumero());
        contactViewHolder.setViewFecha(informacionContacto.getFecha());
        contactViewHolder.setViewTipo(informacionContacto.getTipo());
        contactViewHolder.setViewDuracion(informacionContacto.getDuracion());
        contactViewHolder.setViewGeocode(informacionContacto.getGeocode());
        contactViewHolder.setViewLlamada(informacionContacto.getDescripcionTipo());
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.my_text_view, viewGroup, false);

        return new MyViewHolder(itemView);
    }
}