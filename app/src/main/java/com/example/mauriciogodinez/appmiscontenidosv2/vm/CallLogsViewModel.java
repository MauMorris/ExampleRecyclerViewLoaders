package com.example.mauriciogodinez.appmiscontenidosv2.vm;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;

import com.example.mauriciogodinez.appmiscontenidosv2.domain.CallLogsUseCase;
import com.example.mauriciogodinez.appmiscontenidosv2.domain.CallLogsUseCaseCallback;

public class CallLogsViewModel extends AndroidViewModel implements CallLogsUseCaseCallback {

    private static final String LOG_TAG = CallLogsViewModel.class.getSimpleName();
    final private MutableLiveData<Cursor> currentData = new MutableLiveData<>();

    //Creamos la URI del ContentProvider que utilizaremos
    private Uri direccionUriLlamada = CallLog.Calls.CONTENT_URI;
    //en el arreglo indicamos las columnas que regresará en el query del ContentResolver
    public static final String[] colContentResolver = {
            CallLog.Calls.NUMBER,
            CallLog.Calls.DATE,
            CallLog.Calls.TYPE,
            CallLog.Calls.DURATION,
            CallLog.Calls.GEOCODED_LOCATION,
    };

    public CallLogsViewModel(Application application) {
        super(application);
    }

    public MutableLiveData<Cursor> getCurrentData() {
        return currentData;
    }

    public void getListToPopulate(Context context) {
        CallLogsUseCase mCallLogsCases = new CallLogsUseCase(this);
        mCallLogsCases.createList(context, direccionUriLlamada, colContentResolver);
    }

    @Override
    public void callBackFromCallLogsDomain(Cursor cursor) {
        currentData.setValue(cursor);
    }
}