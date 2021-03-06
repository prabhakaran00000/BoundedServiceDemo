package com.baatu.servicedemo.di;

import androidx.lifecycle.ViewModelProvider;

import com.baatu.servicedemo.viewmodel.ViewModelProviderFactory;

import dagger.Binds;
import dagger.Module;


@Module
public abstract class ViewModelFactoryModule {

    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelProviderFactory viewModelFactory);

}
