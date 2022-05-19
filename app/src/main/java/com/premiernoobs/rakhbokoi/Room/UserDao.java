package com.premiernoobs.rakhbokoi.Room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM User_Table")
    List<LocalUser> getUserList();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(LocalUser user);

    @Update
    void updateUser(LocalUser user);

    @Delete
    void deleteUser(LocalUser user);

}
