package com.baatu.servicedemo.data.db;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.baatu.servicedemo.data.model.User;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM UserList")
    List<User> getAllUser();

    @Insert
    (onConflict = OnConflictStrategy.REPLACE)
    void insertUserList(List<User> datumList);

}
