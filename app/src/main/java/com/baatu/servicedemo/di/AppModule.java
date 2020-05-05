package com.baatu.servicedemo.di;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.baatu.servicedemo.data.db.UserDao;
import com.baatu.servicedemo.data.db.UserDetailRoomDatabase;
import com.baatu.servicedemo.data.model.User;
import com.baatu.servicedemo.data.remote.APIService;
import com.baatu.servicedemo.utils.AppUtils;
import com.baatu.servicedemo.utils.SingleLiveEvent;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*This modules provides dependencies for HomeViewModel, UserListRepository and UserDetailRoomDatabase*/

@Module
public class AppModule {
    @Singleton
    @Provides
    Context provideContext(Application application) {
        return application;
    }
    @Provides
    UserDetailRoomDatabase provideDb(Context context){
        return UserDetailRoomDatabase.getDatabase(context);
    }

    @Provides
    UserDao provideUserDao(UserDetailRoomDatabase database){
        return database.userDao();
    }

    @Provides
    Executor provideExecutor(){
        return Executors.newSingleThreadExecutor();
    }

    @Provides
    SingleLiveEvent<Boolean> provideBooleanLiveData(){
        return new SingleLiveEvent<>();
    }

    @Provides
    MutableLiveData<List<User>> provideUserListLiveData(){
        return new MutableLiveData<>();
    }

    @Provides
    MutableLiveData<String> provideNetworkStatusLiveData(){
        return new MutableLiveData<>();
    }

    @Singleton
    @Provides
    AppUtils provideAppUtils(Context context){
    return new AppUtils(context);
    }

    @Singleton
    @Provides
    APIService provideApiService(){
        Retrofit.Builder retrofit = new Retrofit.Builder();
        retrofit.baseUrl(APIService.BASE_URL)
                .client(new OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.build().create(APIService.class);
    }
}
