package com.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "transactions")
public class TransactionModel {
    @PrimaryKey(autoGenerate = true)
    public int transactionsID;

    @ColumnInfo(name = "short_name")
    private String name;
    @ColumnInfo(name = "price")
    private String value;
    @ColumnInfo(name = "full_name")
    private String state;
    public int userID;


    public TransactionModel(String name, String value, String state) {
        this.name = name;
        this.value = value;
        this.state = state;


    }

    public String getName() {
        return this.name;
    }

    public String getValue() {
        return this.value;
    }

    public String getState() {
        return this.state;
    }

}
