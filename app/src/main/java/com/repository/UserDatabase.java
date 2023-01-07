package com.repository;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.model.OpenPositionModel;
import com.model.TransactionModel;
import com.model.UserModel;

import dao.FetchTransactions;

@Database(version = 11, entities = {TransactionModel.class, UserModel.class, OpenPositionModel.class}, exportSchema = true)
public abstract class UserDatabase extends RoomDatabase {
    private static UserDatabase instance;

    public abstract FetchTransactions userData();

    public synchronized static UserDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), UserDatabase.class, "currency.db").createFromAsset("assets/currency.db").addCallback(roomCallback).build();

        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);


        }

    };

}
