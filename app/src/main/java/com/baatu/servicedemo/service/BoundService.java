package com.baatu.servicedemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.baatu.servicedemo.data.UserListRepository;

public class BoundService extends Service {
    private static final String TAG = "BoundService";
    private final IBinder mBinder = new MyBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public void fetchUserList(UserListRepository userListRepository) {
        Log.d(TAG, "fetchUserList: Service Started");
        userListRepository.fetchUserList();
    }

    public class MyBinder extends Binder{
        public BoundService getBoundService(){
            return BoundService.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
}
