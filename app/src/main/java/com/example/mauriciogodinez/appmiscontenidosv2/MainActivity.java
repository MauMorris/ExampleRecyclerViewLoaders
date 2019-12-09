package com.example.mauriciogodinez.appmiscontenidosv2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.CallLog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "ContentPLlamadas";

    //Creamos la URI del ContentProvider que utilizaremos
    private Uri direccionUriLlamada = CallLog.Calls.CONTENT_URI;

    //en el arreglo indicamos las columnas que regresará en el query del ContentResolver
    private String[] colContentResolver = {
            CallLog.Calls.NUMBER,
            CallLog.Calls.DATE,
            CallLog.Calls.TYPE,
            CallLog.Calls.DURATION,
            CallLog.Calls.GEOCODED_LOCATION,
    };

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        // usa linear layout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        RecyclerView.Adapter mAdapter = new MyAdapter(createList());
        mRecyclerView.setAdapter(mAdapter);
    }

    private List<InformacionLlamada> createList() {

        List<InformacionLlamada> result = new ArrayList<>();

        try {
            //Creamos el Formatter para obtener una fecha legible
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy HH:mm", Locale.ENGLISH);

            //Consultamos con la URI dada para regresar los registros ordenados DESC con ref Calls.DATE
            Cursor registros = getContentResolver().query(direccionUriLlamada, colContentResolver, null,
                    null, CallLog.Calls.DATE + " DESC");

            //crea objeto donde estaremos guardando la informacion obtenida del Content Provider
            InformacionLlamada infoLlamada = new InformacionLlamada();

            if (registros != null) {
                while (registros.moveToNext()) {
                    //Obtengo los datos a partir del indice
                    String numero = registros.getString(registros.getColumnIndex(colContentResolver[0]));
                    long fecha = registros.getLong(registros.getColumnIndex(colContentResolver[1]));//Epoch UNIX
                    int tipo = registros.getInt(registros.getColumnIndex(colContentResolver[2]));
                    String duracion = registros.getString(registros.getColumnIndex(colContentResolver[3]));
                    String geoCode = registros.getString(registros.getColumnIndex(colContentResolver[4]));
                    //Validando tipo de llamada
                    String tipoLlamada = returnTipoLlamada(tipo);

                    infoLlamada.setNumero(getResources().getString(R.string.etiqueta_numero, numero));
                    infoLlamada.setFecha(getResources().getString(R.string.etiqueta_fecha, formatter.format(new Date(fecha))));
                    infoLlamada.setTipo(getResources().getString(R.string.etiqueta_tipo, tipo));
                    infoLlamada.setDuracion(getResources().getString(R.string.etiqueta_duracion, duracion + "s"));
                    infoLlamada.setGeocode(getResources().getString(R.string.etiqueta_geoCode, geoCode));
                    infoLlamada.setDescripcionTipo(getResources().getString(R.string.etiqueta_llamada, tipoLlamada));

                    //agrega el objeto al arreglo y despues lo setea para volver a utilizarlo  evitando crear objetos duplicados
                    result.add(infoLlamada);
                    infoLlamada = new InformacionLlamada();
                }
                //liberamos la memoria del cursor
                registros.close();
            }
        } catch (Exception e) {
            Log.v(TAG, e.getMessage());
        }
        return result;
    }

    /**
     * Método que valida el tipo de llamada que regresa el ContentProvider y asigna
     * el string correspondiente (entrada, perdida, salida, conectando, no definida)
     *
     * @param tipo registro CallLog.Calls.TYPE proporcionado por el ContentProvider
     * @return string asignado al tipo
     */
    private String returnTipoLlamada(int tipo) {
        switch (tipo) {
            case CallLog.Calls.INCOMING_TYPE:
                return getResources().getString(R.string.tipo_llamada_entrada);
            case CallLog.Calls.MISSED_TYPE:
                return getResources().getString(R.string.tipo_llamada_perdida);
            case CallLog.Calls.OUTGOING_TYPE:
                return getResources().getString(R.string.tipo_llamada_salida);
            case Call.STATE_CONNECTING:
                return getResources().getString(R.string.tipo_llamada_conectando);
            default:
                return getResources().getString(R.string.tipo_llamada_undefined);
        }
    }
}