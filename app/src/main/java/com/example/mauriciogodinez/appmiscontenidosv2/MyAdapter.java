package com.example.mauriciogodinez.appmiscontenidosv2;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<ViewHolder> {
    private List<InformacionLlamada> contactList;

    public MyAdapter(List<InformacionLlamada> contactList) {
        this.contactList = contactList;
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder contactViewHolder, int i) {
        InformacionLlamada ci = contactList.get(i);
        contactViewHolder.vNumero.setText(ci.getNumero());
        contactViewHolder.vFecha.setText(ci.getFecha());
        contactViewHolder.vTipo.setText(ci.getTipo());
        contactViewHolder.vDuracion.setText(ci.getDuracion());
        contactViewHolder.vGeocode.setText(ci.getGeocode());
        contactViewHolder.vLlamada.setText(ci.getSetTipo());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.my_text_view, viewGroup, false);

        return new ViewHolder(itemView);
    }
}