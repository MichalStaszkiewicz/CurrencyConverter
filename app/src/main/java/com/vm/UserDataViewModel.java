package com.vm;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.model.OpenPositionModel;
import com.model.TransactionModel;
import com.model.UserModel;
import com.repository.UserDatabase;

import java.util.List;

public class UserDataViewModel extends ViewModel {

    private MediatorLiveData<UserModel> mLiveUserData = new MediatorLiveData<>();
    private MediatorLiveData<List<TransactionModel>> mLiveLastTransactionsData = new MediatorLiveData<>();
    private MediatorLiveData<List<OpenPositionModel>> mLiveOpenPositionsData = new MediatorLiveData<>();

    private static UserDataViewModel instance;


    public static UserDataViewModel getInstance() {

        if (instance == null) {

            instance = new UserDataViewModel();

        }

        return instance;

    }

    public LiveData<UserModel> getUserState() {
        if (mLiveUserData != null) {
            return mLiveUserData;
        } else {
            return null;
        }


    }
    public LiveData<UserModel> userPersonalInfo(Context context) {

        UserDatabase db = UserDatabase.getInstance(context);

        final LiveData<UserModel> informations = db.userData().getUserPersonalData();
        mLiveUserData.addSource(informations, new Observer<UserModel>() {
            @Override
            public void onChanged(UserModel data) {
                if (data == null) {

                    mLiveUserData.setValue(null);
                } else {


                    mLiveUserData.removeSource(informations);
                    mLiveUserData.setValue(data);

                }
            }
        });

        return mLiveUserData;
    }

    public LiveData<List<TransactionModel>> lastTransactions(Context context) {

        UserDatabase db = UserDatabase.getInstance(context);

        final LiveData<List<TransactionModel>> transactions = db.userData().getTransactions();
        mLiveLastTransactionsData.addSource(transactions, new Observer<List<TransactionModel>>() {
            @Override
            public void onChanged(List<TransactionModel> transactionModels) {
                if (transactionModels == null) {

                    mLiveLastTransactionsData.setValue(null);
                } else {


                    mLiveLastTransactionsData.removeSource(transactions);
                    mLiveLastTransactionsData.setValue(transactionModels);

                }
            }
        });

        return mLiveLastTransactionsData;
    }

    public LiveData<List<OpenPositionModel>> openPositions(Context context) {

        UserDatabase db = UserDatabase.getInstance(context);

        final LiveData<List<OpenPositionModel>> open_positions = db.userData().getActivePositions();
        mLiveOpenPositionsData.addSource(open_positions, new Observer<List<OpenPositionModel>>() {
            @Override
            public void onChanged(List<OpenPositionModel> openPositions) {
                if (openPositions == null) {



                    mLiveOpenPositionsData.setValue(null);
                } else {


                    mLiveOpenPositionsData.removeSource(open_positions);
                    mLiveOpenPositionsData.setValue(openPositions);

                }
            }
        });

        return mLiveOpenPositionsData;
    }


    public void insertLastTransactionData(Context context, TransactionModel transaction) {


        new Thread(() -> {

            try {


                UserDatabase db = UserDatabase.getInstance(context);
                db.userData().insertTransaction(transaction);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        ).start();


    }

    public void insertOpenPosition(Context context, OpenPositionModel openPosition) {


        new Thread(() -> {

            try {


                UserDatabase db = UserDatabase.getInstance(context);
                db.userData().insertOpenPosition(openPosition);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        ).start();


    }
    public void closeOpenPosition(Context context, OpenPositionModel openPosition) {


        new Thread(() -> {

            try {


                UserDatabase db = UserDatabase.getInstance(context);
                db.userData().closeOpenPositionInDb(openPosition);


            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        ).start();


    }
    public void updateUserState(Context context, UserModel user) {


        new Thread(() -> {

            try {


                UserDatabase db = UserDatabase.getInstance(context);
              db.userData().updateUserStateInDb(user);


            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        ).start();


    }
    public void updateOpenPositionList(Context context, List<OpenPositionModel> openPositions) {


        new Thread(() -> {

            try {


                UserDatabase db = UserDatabase.getInstance(context);
                db.userData().updateOpenPosition(openPositions);




            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        ).start();


    }



}

