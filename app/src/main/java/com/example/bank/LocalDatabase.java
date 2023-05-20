package com.example.bank;

import android.content.Context;
import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Delete;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.PrimaryKey;
import androidx.room.Query;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Entity(tableName = "LoginAndPassword")
public class LocalDatabase {

    @PrimaryKey
    public int row_id;

    @ColumnInfo(name = "user_login")
    public String userLogin;

    @ColumnInfo(name = "user_password")
    public String userPassword;
    LocalDatabase(){};
    public LocalDatabase(int id, String login, String pass){
        this.row_id=id;
        this.userLogin=login;
        this.userPassword=pass;
    };

    @Database(entities = {LocalDatabase.class}, version = 1, exportSchema = true)
    public static abstract class AppDatabase extends RoomDatabase {

        public abstract UserDao userDao();

        private static volatile AppDatabase INSTANCE;
        private static final int NUMBER_OF_THREADS = 4;
         static final ExecutorService databaseWriteExecutor =
                Executors.newFixedThreadPool(NUMBER_OF_THREADS);

         static AppDatabase getDatabase(final Context context) {
            if (INSTANCE == null) {
                synchronized (AppDatabase.class) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context,
                                        AppDatabase.class, "user_database")
                                .build();
                        Log.i("database","New");
                        }
                    }

                }

            return INSTANCE;
        }
    }
}
