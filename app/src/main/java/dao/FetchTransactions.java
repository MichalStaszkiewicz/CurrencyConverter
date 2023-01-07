package dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.model.OpenPositionModel;
import com.model.TransactionModel;
import com.model.UserModel;


import java.util.List;

@Dao
public interface FetchTransactions {

    @Transaction
    @Query("SELECT * From user")
    LiveData<UserModel> getUserPersonalData();
    @Query("SELECT * From transactions")
    LiveData<List<TransactionModel>> getTransactions();
    @Query("SELECT * From active_positions")
    LiveData<List<OpenPositionModel>> getActivePositions();
    @Transaction
    @Insert(entity = TransactionModel.class ,onConflict = OnConflictStrategy.REPLACE)
    void insertTransaction(TransactionModel transaction);
    @Insert(entity = OpenPositionModel.class)
    void insertOpenPosition(OpenPositionModel openPosition);
    @Update(entity = UserModel.class ,onConflict = OnConflictStrategy.REPLACE)
    void updateUserStateInDb(UserModel user);
    @Delete
    void closeOpenPositionInDb(OpenPositionModel openPosition);
    @Update
    void updateOpenPosition(List<OpenPositionModel> openPosition);



}
