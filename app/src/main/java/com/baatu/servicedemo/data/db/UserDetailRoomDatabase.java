package com.baatu.servicedemo.data.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.baatu.servicedemo.data.model.User;

@Database(entities = {User.class}, version = 1,exportSchema = false)
public abstract class UserDetailRoomDatabase extends RoomDatabase {

    private static final String DB_NME = "UserDetail";

    public abstract UserDao userDao();

    private static volatile UserDetailRoomDatabase INSTANCE;

    public static UserDetailRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (UserDetailRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            UserDetailRoomDatabase.class, DB_NME)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
