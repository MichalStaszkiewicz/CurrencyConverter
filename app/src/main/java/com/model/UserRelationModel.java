package com.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class UserRelationModel {
    @Embedded public UserModel user;
    @Relation(parentColumn = "userID" ,
            entityColumn = "transactionsID")
    public List<TransactionModel> transactions;
}
