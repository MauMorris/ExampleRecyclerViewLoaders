package com.example.mauriciogodinez.appmiscontenidosv2.repo;

import android.database.Cursor;

public interface CallLogsRepositoryCallback {
    void callbackFromCallLogsRepository(Cursor cursor);
}
