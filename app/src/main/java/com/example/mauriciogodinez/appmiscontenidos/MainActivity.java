package com.example.mauriciogodinez.appmiscontenidos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.content.ContentResolver;
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

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(mLayoutManager);

        RecyclerView.Adapter mAdapter = new MyAdapter(createList());
        mRecyclerView.setAdapter(mAdapter);

    }

    private List<InformacionLlamada> createList() {

        List<InformacionLlamada> result = new ArrayList<>();

        Uri direccionUriLlamada = CallLog.Calls.CONTENT_URI;

        String[] campos = {
                CallLog.Calls.NUMBER,
                CallLog.Calls.DATE,
                CallLog.Calls.TYPE,
                CallLog.Calls.DURATION,
                CallLog.Calls.GEOCODED_LOCATION,
        };

        try {
//try para el nuevo content resolver
            ContentResolver contentResolver = getContentResolver();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy HH:mm", Locale.ENGLISH);
            Cursor registros = contentResolver.query(direccionUriLlamada, campos, null, null, CallLog.Calls.DATE + " DESC");

            InformacionLlamada ii = new InformacionLlamada();

            while (registros.moveToNext()) {

                //Obtengo los datos a partir del indice obtenido
                String numero = registros.getString(registros.getColumnIndex(campos[0]));
                Long fecha = registros.getLong(registros.getColumnIndex(campos[1]));//Epoch UNIX
                int tipo = registros.getInt(registros.getColumnIndex(campos[2]));
                String duracion = registros.getString(registros.getColumnIndex(campos[3]));
                String geoCode = registros.getString(registros.getColumnIndex(campos[4]));
                //Validando tipo de llamada
                switch (tipo) {
                    case CallLog.Calls.INCOMING_TYPE:
                        ii.setSetTipo(getResources().getString(R.string.entrada));
                        break;
                    case CallLog.Calls.MISSED_TYPE:
                        ii.setSetTipo(getResources().getString(R.string.perdida));
                        break;
                    case CallLog.Calls.OUTGOING_TYPE:
                        ii.setSetTipo(getResources().getString(R.string.salida));
                        break;
                    case Call.STATE_CONNECTING:
                        ii.setSetTipo("no");
                        break;
                    default:
                        ii.setSetTipo(getResources().getString(R.string.undefined));
                }

                ii.setNumero(getResources().getString(R.string.etiqueta_numero, numero));
                ii.setFecha(getResources().getString(R.string.etiqueta_fecha, formatter.format(new Date(fecha))));
                ii.setTipo(getResources().getString(R.string.etiqueta_tipo, tipo));
                ii.setDuracion(getResources().getString(R.string.etiqueta_duracion, duracion + "s"));
                ii.setGeocode(getResources().getString(R.string.etiqueta_geoCode, geoCode));
                ii.setSetTipo(getResources().getString(R.string.etiqueta_llamada, ii.getSetTipo()));

                result.add(new InformacionLlamada(ii.getNumero(), ii.getFecha(),
                        ii.getTipo(), ii.getDuracion(),
                        ii.getGeocode(), ii.getSetTipo()));
            }
            registros.close();
        } catch (Exception e) {
            Log.v(TAG, e.getMessage());
        }
        return result;
    }
}
