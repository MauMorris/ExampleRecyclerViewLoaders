package com.example.mauriciogodinez.appmiscontenidosv2;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.database.Cursor;

public class CallLogsViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final Cursor mDatabase;

    public CallLogsViewModelFactory(Cursor database){
        mDatabase = database;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new CallLogsViewModel(mDatabase);
    }
}