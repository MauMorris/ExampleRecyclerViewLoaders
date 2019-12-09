package com.example.mauriciogodinez.appmiscontenidosv2;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


/**
 * Created by mauriciogodinez on 31/03/17.
 */

public class ViewHolder extends RecyclerView.ViewHolder {
    // each data item is just a string in this case
    protected TextView vNumero;
    protected TextView vFecha;
    protected TextView vTipo;
    protected TextView vDuracion;
    protected TextView vGeocode;
    protected TextView vLlamada;

    public ViewHolder(View v) {
        super(v);
        vNumero = (TextView) v.findViewById(R.id.text_view_numero);
        vFecha = (TextView) v.findViewById(R.id.text_view_fecha);
        vTipo = (TextView) v.findViewById(R.id.text_view_tipo);
        vDuracion = (TextView) v.findViewById(R.id.text_view_duracion);
        vGeocode = (TextView) v.findViewById(R.id.text_view_geocode);
        vLlamada = (TextView) v.findViewById(R.id.text_view_llamada);
    }
}
