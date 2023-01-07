package com.example.currencyconverter;

import static org.chromium.base.ContextUtils.getApplicationContext;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavHost;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.Adapters.TransactionsAdapter;
import com.example.currencyconverter.databinding.FragmentHomeBinding;
import com.model.CurrencyModel;
import com.model.OpenPositionModel;
import com.model.TransactionModel;
import com.model.UserModel;
import com.model.UserRelationModel;
import com.vm.CurrencyActivityViewModel;
import com.vm.UserDataViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Home extends Fragment {
    private TransactionsAdapter transactions_list_adapter;
    private ArrayList<TransactionModel> transactions = new ArrayList<>();
    private FragmentHomeBinding binding;
    private Context context;


    private UserDataViewModel viewModel;
    private UserDataViewModel userData;

    private TextView UserName;
    private TextView UserBalance;
    private TextView UserProfit;

    public Home(Context context) {

        this.context = context;


    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        transactions.clear();


        loadPersonalInformationsToUI();
        loadLastTransactionsToUI();

        if (CurrencyActivityViewModel.getInstance().getCurrencyList().getValue() != null) {
            loadProfit();
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        RecyclerView list = view.findViewById(R.id.transaction_list);
        ProgressBar loading_last_transactions = view.findViewById(R.id.last_transactions_loading);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        UserName = view.findViewById(R.id.userName);
        UserBalance = view.findViewById(R.id.balance);
        UserProfit = view.findViewById(R.id.profit);


        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        list.setLayoutManager(mLayoutManager);


        loading_last_transactions.setVisibility(View.GONE);


        Drawable divider = ContextCompat.getDrawable(list.getContext(), R.drawable.divider);
        DividerItemDecoration decoration = new DividerItemDecoration(list.getContext(), DividerItemDecoration.HORIZONTAL);
        decoration.setDrawable(divider);

        list.addItemDecoration(decoration);


        transactions_list_adapter = new TransactionsAdapter(transactions);
        list.setAdapter(transactions_list_adapter);
        list.setItemAnimator(new DefaultItemAnimator());

        return view;
    }

    private void loadPersonalInformationsToUI() {
        userData.getInstance().userPersonalInfo(context).observe(this, new Observer<UserModel>() {
            @Override
            public void onChanged(UserModel userModel) {
                UserName.setText(userModel.getName());
                UserBalance.setText("$" + String.valueOf(Math.round(userModel.getBalance() * 100.0) / 100.0));
                UserProfit.setText("Profit: " + "$" + String.valueOf(Math.round(userModel.getProfit() * 100.0) / 100.0));
            }
        });
    }

    private void loadProfit() {
        ArrayList<CurrencyModel> listGroup = new ArrayList<CurrencyModel>();
        listGroup = CurrencyActivityViewModel.getInstance().getCurrencyList().getValue();
        ArrayList<OpenPositionModel> active_positions = (ArrayList<OpenPositionModel>) UserDataViewModel.getInstance().openPositions(context).getValue();
        List<OpenPositionModel> updatedOpenPositions = new ArrayList<OpenPositionModel>();
        for (int i = 0; i < active_positions.size(); i++) {
            for (int j = 0; j < listGroup.size(); j++) {
                if (Objects.equals(listGroup.get(j).getShortName(), active_positions.get(i).getShortName())) {


                    if (!Objects.equals(listGroup.get(j).getPrice(), active_positions.get(i).getOriginalPrice())) {
                        double actualPrice = Double.parseDouble(listGroup.get(j).getPrice());
                        double beforePrice = Double.parseDouble(active_positions.get(i).getOriginalPrice());
                        double changedValue = ((actualPrice - beforePrice) / beforePrice) * 100;
                        double dollar_course = (1 / Double.parseDouble(listGroup.get(i).getPrice()));

                        OpenPositionModel openPositionAfterChange = new OpenPositionModel(listGroup.get(j).getShortName(), String.valueOf(Math.round(changedValue * 10.00) / 10.00), String.valueOf(Math.round(active_positions.get(i).getQuantity() * dollar_course * 10.00) / 10.00), listGroup.get(j).getFullName(), active_positions.get(i).getOriginalPrice(), active_positions.get(i).getQuantity());
                        openPositionAfterChange.setID(active_positions.get(i).getID());
                        updatedOpenPositions.add(openPositionAfterChange);

                    } else {
                        updatedOpenPositions.add(active_positions.get(i));
                    }

                }
            }
        }
        UserDataViewModel.getInstance().updateOpenPositionList(context, updatedOpenPositions);
    }

    private void loadLastTransactionsToUI() {
        viewModel.getInstance().lastTransactions(context).observe(this, new Observer<List<TransactionModel>>() {
            @Override
            public void onChanged(List<TransactionModel> transactionModels) {
                if (transactionModels != null) {
                    if (transactions.size() > 0) {
                        transactions.clear();
                    }


                    for (int i = 0; i < transactionModels.size(); i++) {
                        transactions.add(transactionModels.get(i));
                        if (i == 5) {

                            break;
                        }
                    }


                    transactions_list_adapter.notifyDataSetChanged();
                }
            }
        });
    }

}