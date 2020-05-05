package com.baatu.servicedemo.di;


import com.baatu.servicedemo.ui.HomeActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(
            modules = HomeViewModelModule.class)
    abstract HomeActivity contributeHomeActivity();


}
