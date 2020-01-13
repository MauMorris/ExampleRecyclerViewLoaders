package com.example.mauriciogodinez.appmiscontenidosv2;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
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
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks <Cursor>,
        CallbackFromList {
    private static final String TAG = "ContentPLlamadas";

    //Creamos la URI del ContentProvider que utilizaremos
    private Uri direccionUriLlamada = CallLog.Calls.CONTENT_URI;
    private MyAdapter mAdapter;
    private CallLogsViewModel mCallLogsViewModel;
    private Cursor cursor;
    private static final int CALL_LOGS_LOADER_ID = 1;
    private ProgressBar mLoadingIndicator;

    //en el arreglo indicamos las columnas que regresará en el query del ContentResolver
    public static final String[] colContentResolver = {
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
        mLoadingIndicator = findViewById(R.id.loading_indicator_progress_bar);

        // usa linear layout manager
        LinearLayoutManager mLayoutManager = new
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new MyAdapter(this, this);
        mRecyclerView.setAdapter(mAdapter);

        CallLogsViewModelFactory factory = new CallLogsViewModelFactory(cursor);
        mCallLogsViewModel = ViewModelProviders.of(this, factory).get(CallLogsViewModel.class);

        mCallLogsViewModel.getCurrentData().observe(this, new Observer<Cursor>() {
            public void onChanged(@Nullable Cursor cursor) {
                if (cursor != null) {
                    mAdapter.setData(cursor);
                }
            }
        });

        getSupportLoaderManager().initLoader(CALL_LOGS_LOADER_ID, null, this);
    }

    @SuppressLint("StaticFieldLeak")
    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle bundle) {
        return new AsyncTaskLoader<Cursor>(this) {

            @Override
            protected void onStartLoading() {
                mLoadingIndicator.setVisibility(View.VISIBLE);
                forceLoad();
            }

            @Nullable
            @Override
            public Cursor loadInBackground() {
                cursor = createList();
                return cursor;
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        mCallLogsViewModel.getCurrentData().setValue(cursor);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
    }

    private Cursor createList() {

        Cursor registros;
        try {
            //Consultamos con la URI dada para regresar los registros ordenados DESC con ref Calls.DATE
            registros = getContentResolver().query(direccionUriLlamada, colContentResolver, null,
                    null, CallLog.Calls.DATE + " DESC");
        } catch (Exception e) {
            Log.v(TAG, e.getMessage());
            registros = null;
        }
        return registros;
    }

    @Override
    public void clickListenerFromList(String data) {
        Toast.makeText(this, data, Toast.LENGTH_LONG).show();
    }
}