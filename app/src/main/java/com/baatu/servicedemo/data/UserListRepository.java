package com.baatu.servicedemo.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.baatu.servicedemo.data.db.UserDao;
import com.baatu.servicedemo.data.model.User;
import com.baatu.servicedemo.data.remote.APIService;
import com.baatu.servicedemo.utils.Constant;

import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserListRepository {
    @Inject
    public UserDao mUserDao;
    @Inject
    public MutableLiveData<List<User>> mUserListLiveData;
    @Inject
    public MutableLiveData<String> mNetworkStatus;
    @Inject
    public Executor mDatabaseReadWriteExecutor;
    @Inject
    public APIService mApiService;

    @Inject
    public UserListRepository() {
    }

    public void fetchUserList() {
        mApiService.getUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                List<User> userList = response.body();
                if (userList != null && userList.size() > 0) {
                    mUserListLiveData.postValue(userList);
                    mNetworkStatus.postValue(Constant.SUCCESSFUL);
                    insertUserDetail(userList);
                } else {
                    mNetworkStatus.postValue(Constant.UN_SUCCESSFUL);
                }
            }
            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                mNetworkStatus.postValue(Constant.UN_SUCCESSFUL);
            }
        });
    }
    public void insertUserDetail(final List<User> userDetail){
        mDatabaseReadWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mUserDao.insertUserList(userDetail);
            }
        });
    }

    public LiveData<List<User>> getUserList() {
        return mUserListLiveData;
    }

    public MutableLiveData<String> getNetworkStatus() {
        return mNetworkStatus;
    }

    public void fetchOfflineUserList() {
        mDatabaseReadWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mUserListLiveData.postValue(mUserDao.getAllUser());
                mNetworkStatus.postValue(Constant.OFFLINE_DATA);
            }
        });
    }
}
