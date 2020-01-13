package com.example.mauriciogodinez.appmiscontenidosv2;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.database.Cursor;

public class CallLogsViewModel extends ViewModel {
    private MutableLiveData<Cursor> currentData;

    public CallLogsViewModel(Cursor cursorData) {
        currentData = new MutableLiveData<>();
        currentData.setValue(cursorData);
    }

    public MutableLiveData<Cursor> getCurrentData() {
        return currentData;
    }
}