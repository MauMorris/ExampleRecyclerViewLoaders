package com.example.mauriciogodinez.appmiscontenidosv2.domain;

import android.database.Cursor;

public interface CallLogsUseCaseCallback {
    void callBackFromCallLogsDomain(Cursor cursor);
}