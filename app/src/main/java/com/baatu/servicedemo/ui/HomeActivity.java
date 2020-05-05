package com.baatu.servicedemo.ui;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baatu.servicedemo.R;
import com.baatu.servicedemo.data.model.User;
import com.baatu.servicedemo.databinding.ActivityHomeBinding;
import com.baatu.servicedemo.service.BoundService;
import com.baatu.servicedemo.utils.Constant;
import com.baatu.servicedemo.viewmodel.HomeViewModel;
import com.baatu.servicedemo.viewmodel.ViewModelProviderFactory;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class HomeActivity extends DaggerAppCompatActivity {

    private UserListAdapter mUserListAdapter;
    private HomeViewModel mHomeViewModel;
    private ActivityHomeBinding mActivityHomeBinding;
    private ServiceConnection mServiceConnection;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mActivityHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        initRecyclerView();
        initViewModel();
    }

    private void initViewModel() {
        mHomeViewModel = ViewModelProviders.of(this, providerFactory).get(HomeViewModel.class);
        mActivityHomeBinding.setViewModel(mHomeViewModel);
        mServiceConnection = mHomeViewModel.getServiceConnection();

        mHomeViewModel.getStartServiceListener().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    Intent intent = new Intent(HomeActivity.this, BoundService.class);
                    bindService(intent,mServiceConnection, Context.BIND_AUTO_CREATE);
                }
            }
        });

        mHomeViewModel.getUserList().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> userList) {
                if(userList != null && userList.size() > 0){
                    mActivityHomeBinding.setIsVisible(true);
                    mUserListAdapter.refreshData(userList);
                }else {
                    mActivityHomeBinding.setIsVisible(false);
                    Toast.makeText(HomeActivity.this, Constant.USER_DATA_NOT_AVAILABLE,
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        mHomeViewModel.getNetworkStatus().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String networkStatus) {
                if(networkStatus.equals(Constant.OFFLINE_DATA)){
                    Toast.makeText(HomeActivity.this, Constant.INTERNET_NOT_AVAILABLE,
                            Toast.LENGTH_LONG).show();
                    mActivityHomeBinding.setNetworkStatus(networkStatus);
                }else {
                    mActivityHomeBinding.setNetworkStatus(networkStatus);
                }
            }
        });
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = mActivityHomeBinding.recyclerview;
        mUserListAdapter = new UserListAdapter(new ArrayList<User>(),this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mUserListAdapter);
    }

}
