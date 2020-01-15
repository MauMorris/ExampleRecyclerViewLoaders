package com.example.mauriciogodinez.appmiscontenidosv2;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.database.Cursor;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.example.mauriciogodinez.appmiscontenidosv2.databinding.ActivityMainBinding;
import com.example.mauriciogodinez.appmiscontenidosv2.ui.AdapterItemCallback;
import com.example.mauriciogodinez.appmiscontenidosv2.ui.MyAdapter;
import com.example.mauriciogodinez.appmiscontenidosv2.vm.CallLogsViewModel;

public class MainActivity extends AppCompatActivity implements AdapterItemCallback {

    private MyAdapter mAdapter;
    private CallLogsViewModel mCallLogsViewModel;

    private ActivityMainBinding mMainBinding;
    private boolean setViewsFromCreation;
    private static final String DATA_REQUESTED = "dataRequested";

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setViews(mMainBinding);

        mCallLogsViewModel = ViewModelProviders.of(this).get(CallLogsViewModel.class);

        subscribeUi(mCallLogsViewModel.getCurrentData());

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(DATA_REQUESTED)) {
                if (!savedInstanceState.getBoolean(DATA_REQUESTED))
                    getCallLagsContentProvider();
            }
        } else
            getCallLagsContentProvider();
    }

    private void setViews(ActivityMainBinding mMainBinding) {
        mMainBinding.recyclerView.setHasFixedSize(true);
        // usa linear layout manager
        LinearLayoutManager mLayoutManager = new
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mMainBinding.recyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new MyAdapter(this, this);
        mMainBinding.recyclerView.setAdapter(mAdapter);
    }

    private void subscribeUi(MutableLiveData<Cursor> currentData) {
        currentData.observe(this, new Observer<Cursor>() {
            public void onChanged(@Nullable Cursor cursor) {
                if (cursor != null) {
                    mMainBinding.loadingIndicatorProgressBar.setVisibility(View.INVISIBLE);
                    mAdapter.setData(cursor);
                }
            }
        });
    }

    private void getCallLagsContentProvider() {
        mMainBinding.loadingIndicatorProgressBar.setVisibility(View.VISIBLE);
        mCallLogsViewModel.getListToPopulate(MainActivity.this);
        setViewsFromCreation = true;
    }

    @Override
    public void clickListenerFromAdapterItem(String data) {
        Toast.makeText(this, data, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(DATA_REQUESTED, setViewsFromCreation);
    }
}