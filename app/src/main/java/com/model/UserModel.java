package com.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user")
public class UserModel {

    @PrimaryKey(autoGenerate = false)
    public int userID;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "balance")
    private double balance;
    @ColumnInfo(name = "profit")
    private double profit;

    public UserModel(String name, double balance, double profit,int userID) {
        this.name = name;
        this.balance = balance;
        this.profit = profit;
        this.userID = userID;

    }

    public double getBalance() {
        return balance;
    }

    public double getProfit() {
        return profit;
    }

    public int getID() {

        return this.userID;
    }

    public String getName() {
        return name;
    }
}
