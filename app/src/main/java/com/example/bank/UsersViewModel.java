package com.example.bank;

import android.app.Application;

import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class UsersViewModel extends AndroidViewModel {


        private UsersRepository mRepository;

        private LiveData<List<LocalDatabase>> mAllUsers;

        public UsersViewModel (Application application) {
            super(application);
            mRepository = new UsersRepository(application);
            mAllUsers = mRepository.getAllUsers();
        }


    LiveData<List<LocalDatabase>> getAllUsers() throws InterruptedException {
        if (mAllUsers == null) {
            mAllUsers = mRepository.getAllUsers();
        }
        return mAllUsers; }

        public void insert(LocalDatabase user) { mRepository.insert(user); }

    public void deleteAllUsers(){mRepository.deleteAllUsers();}

}
