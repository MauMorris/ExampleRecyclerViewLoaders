package com.example.mauriciogodinez.appmiscontenidosv2.domain;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.mauriciogodinez.appmiscontenidosv2.repo.CallLogsRepositoryCallback;
import com.example.mauriciogodinez.appmiscontenidosv2.repo.CallLogsRepository;


public class CallLogsUseCase implements CallLogsRepositoryCallback {
    private static final String LOG_TAG = CallLogsUseCase.class.getSimpleName();
    private CallLogsUseCaseCallback myCallbackFromCallLogsViewModel;

    public CallLogsUseCase(CallLogsUseCaseCallback myCallbackFromCallLogsViewModel) {
        this.myCallbackFromCallLogsViewModel = myCallbackFromCallLogsViewModel;
    }

    @SuppressLint("StaticFieldLeak")
    public void createList(final Context context, final Uri uri,
                                              final String[] contentResolverQueryColumn) {

        CallLogsRepository mRepository = new CallLogsRepository(this);
        mRepository.getCallLogs(context, uri, contentResolverQueryColumn);
    }

    @Override
    public void callbackFromCallLogsRepository(Cursor cursor) {
        myCallbackFromCallLogsViewModel.callBackFromCallLogsDomain(cursor);
    }
}