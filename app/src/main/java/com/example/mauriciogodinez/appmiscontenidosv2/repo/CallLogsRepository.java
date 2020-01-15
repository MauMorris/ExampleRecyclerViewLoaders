package com.example.mauriciogodinez.appmiscontenidosv2.repo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.CallLog;
import android.util.Log;

public class CallLogsRepository {
    private static final String LOG_TAG = CallLogsRepository.class.getSimpleName();
    private CallLogsRepositoryCallback mCallbackFromCallLogsUseCase;

    public CallLogsRepository(CallLogsRepositoryCallback mCallbackFromCallLogsUseCase) {
        this.mCallbackFromCallLogsUseCase = mCallbackFromCallLogsUseCase;
    }

    @SuppressLint("StaticFieldLeak")
    public void getCallLogs(final Context context,
                            final Uri uri,
                            final String[] contentResolverQueryColumn) {

        new AsyncTask<Void, Void, Cursor>(){
            @Override
            protected Cursor doInBackground(Void... voids) {
                Cursor registros;

                try {
                    //Consultamos con la URI dada para regresar los registros ordenados DESC con ref Calls.DATE
                    registros = context.getContentResolver().query(uri, contentResolverQueryColumn, null,
                            null, CallLog.Calls.DATE + " DESC");
                } catch (Exception e) {
                    Log.v(LOG_TAG, e.getMessage());
                    registros = null;
                }

                return registros;
            }

            @Override
            protected void onPostExecute(Cursor cursor) {
                mCallbackFromCallLogsUseCase.callbackFromCallLogsRepository(cursor);
            }
        }.execute();
    }
}