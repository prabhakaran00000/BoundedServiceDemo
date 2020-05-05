package com.baatu.servicedemo.viewmodel;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.baatu.servicedemo.data.UserListRepository;
import com.baatu.servicedemo.data.model.User;
import com.baatu.servicedemo.service.BoundService;
import com.baatu.servicedemo.utils.AppUtils;
import com.baatu.servicedemo.utils.Constant;
import com.baatu.servicedemo.utils.SingleLiveEvent;

import java.util.List;

import javax.inject.Inject;

public class HomeViewModel extends ViewModel {
    private static final String TAG = "HomeViewModel";
    @Inject
    public Context mContext;
    @Inject
    public UserListRepository mUserListRepository;
    @Inject
    public SingleLiveEvent<Boolean> mStartServiceListener;

    @Inject
    public AppUtils appUtils;

    @Inject
    public HomeViewModel() {

    }

    public MutableLiveData<String> getNetworkStatus() {
        return mUserListRepository.getNetworkStatus();
    }

    public LiveData<List<User>> getUserList() {
        return mUserListRepository.getUserList();
    }

    public ServiceConnection getServiceConnection() {
        return serviceConnection;
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder iBinder) {
            Log.d(TAG, "ServiceConnection: connected to service.");
            BoundService.MyBinder binder = (BoundService.MyBinder) iBinder;
            binder.getBoundService().fetchUserList(mUserListRepository);
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            Log.d(TAG, "ServiceConnection: disconnected from service.");
        }
    };

    public MutableLiveData<Boolean> getStartServiceListener() {
        return mStartServiceListener;
    }

    public void onStartButtonClick(View view) {
        if(appUtils.isNetworkConnected()){
            mStartServiceListener.postValue(true);
        }else{
            fetchOfflineUserList();
            mUserListRepository.getNetworkStatus().postValue(Constant.OFFLINE_DATA);
        }
    }

    public void fetchOfflineUserList() {
        mUserListRepository.fetchOfflineUserList();
    }
}
