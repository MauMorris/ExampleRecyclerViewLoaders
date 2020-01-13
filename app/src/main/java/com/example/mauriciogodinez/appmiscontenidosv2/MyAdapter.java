package com.example.mauriciogodinez.appmiscontenidosv2;

import android.content.Context;
import android.database.Cursor;
import android.provider.CallLog;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.example.mauriciogodinez.appmiscontenidosv2.MainActivity.colContentResolver;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private Cursor contactList;
    private Context mContext;
    public static CallbackFromList mCallback;

    public MyAdapter(Context context, CallbackFromList callbackFromList) {
        mContext = context;
        mCallback = callbackFromList;
    }

    public void setData(Cursor listInformation) {
        contactList = listInformation;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (contactList == null)
            return 0;
        return contactList.getCount();
    }

    @Override
    public void onBindViewHolder(MyViewHolder contactViewHolder, int i) {
        contactList.moveToPosition(i);
        //Creamos el Formatter para obtener una fecha legible
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy HH:mm", Locale.ENGLISH);

        //Obtengo los datos a partir del indice
        String numero = contactList.getString(contactList.getColumnIndex(colContentResolver[0]));
        long fecha = contactList.getLong(contactList.getColumnIndex(colContentResolver[1]));//Epoch UNIX
        int tipo = contactList.getInt(contactList.getColumnIndex(colContentResolver[2]));
        String duracion = contactList.getString(contactList.getColumnIndex(colContentResolver[3]));
        String geoCode = contactList.getString(contactList.getColumnIndex(colContentResolver[4]));
        //Validando tipo de llamada
        String tipoLlamada = returnTipoLlamada(tipo);

        contactViewHolder.setViewNumero(mContext.getResources().getString(R.string.etiqueta_numero, numero));
        contactViewHolder.setViewFecha(mContext.getResources().getString(R.string.etiqueta_fecha, formatter.format(new Date(fecha))));
        contactViewHolder.setViewTipo(mContext.getResources().getString(R.string.etiqueta_tipo, tipo));
        contactViewHolder.setViewDuracion(mContext.getResources().getString(R.string.etiqueta_duracion, duracion + "s"));
        contactViewHolder.setViewGeocode(mContext.getResources().getString(R.string.etiqueta_geoCode, geoCode));
        contactViewHolder.setViewLlamada(mContext.getResources().getString(R.string.etiqueta_llamada, tipoLlamada));
    }

    private String returnTipoLlamada(int tipo) {
        switch (tipo) {
            case CallLog.Calls.INCOMING_TYPE:
                return mContext.getResources().getString(R.string.tipo_llamada_entrada);
            case CallLog.Calls.MISSED_TYPE:
                return mContext.getResources().getString(R.string.tipo_llamada_perdida);
            case CallLog.Calls.OUTGOING_TYPE:
                return mContext.getResources().getString(R.string.tipo_llamada_salida);
            case Call.STATE_CONNECTING:
                return mContext.getResources().getString(R.string.tipo_llamada_conectando);
            default:
                return mContext.getResources().getString(R.string.tipo_llamada_undefined);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.my_text_view;
        LayoutInflater inflater = LayoutInflater.from(context);

        View itemView = inflater.inflate(layoutIdForListItem, viewGroup, false);

        return new MyViewHolder(itemView);
    }
}