package com.example.bank;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM LoginAndPassword")
    LiveData<List<LocalDatabase>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(LocalDatabase user);

    @Delete
    void delete(LocalDatabase user);

    @Query("DELETE FROM LoginAndPassword")
    void deleteAllUsers();
}
