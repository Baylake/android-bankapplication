package com.example.bank;


import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class UsersRepository {

        private UserDao mUserDao;
        private LiveData<List<LocalDatabase>> mUsers;

        // Note that in order to unit test the WordRepository, you have to remove the Application
        // dependency. This adds complexity and much more code, and this sample is not about testing.
        // See the BasicSample in the android-architecture-components repository at
        // https://github.com/googlesamples
        UsersRepository(Application application) {
            LocalDatabase.AppDatabase db = LocalDatabase.AppDatabase.getDatabase(application);
            mUserDao = db.userDao();
            mUsers = mUserDao.getAll();
        }

        // Room executes all queries on a separate thread.
        // Observed LiveData will notify the observer when the data has changed.
        LiveData<List<LocalDatabase>> getAllUsers() {
            return mUsers;
        }

        // You must call this on a non-UI thread or your app will throw an exception. Room ensures
        // that you're not doing any long running operations on the main thread, blocking the UI.
        void insert(LocalDatabase user) {
            LocalDatabase.AppDatabase.databaseWriteExecutor.execute(() -> {
                mUserDao.insert(user);
            });
        }
    void deleteAllUsers() {
        LocalDatabase.AppDatabase.databaseWriteExecutor.execute(() -> {
            mUserDao.deleteAllUsers();
        });
    }
    }

