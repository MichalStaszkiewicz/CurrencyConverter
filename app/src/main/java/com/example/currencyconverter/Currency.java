package com.example.currencyconverter;


import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.SearchView;

import com.Adapters.ExpandableAdapter;
import com.model.CurrencyModel;
import com.model.ExpandableContent;
import com.model.OpenPositionModel;
import com.model.TransactionModel;
import com.model.UserModel;
import com.model.UserRelationModel;
import com.vm.CurrencyActivityViewModel;
import com.vm.UserDataViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Currency extends Fragment {
    private ExpandableListView expandableListView;
    private ArrayList<CurrencyModel> listGroup = new ArrayList<CurrencyModel>();
    private HashMap<String, ExpandableContent> listChild = new HashMap<String, ExpandableContent>();
    private ExpandableAdapter adapter;
    private ProgressBar progressBar;
    private SearchView searchView;
    private TextView loadingText;

    private Context context;
    private ArrayList<TransactionModel> transactions = new ArrayList<>();


    public Currency(SearchView searchView, Context context) {

        this.searchView = searchView;
        this.context = context;

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        UserDataViewModel.getInstance().userPersonalInfo(context).observe(this, new Observer<UserModel>() {
            @Override
            public void onChanged(UserModel userModel) {


            }
        });


    }

    private void setSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                searchView.clearFocus();
                return false;
            }


            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }


        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_currency, container, false);


        expandableListView = view.findViewById(R.id.currencies_list);
        progressBar = view.findViewById(R.id.loading_currencies_progress_bar);
        loadingText = view.findViewById(R.id.loading_currencies_text);

            if (CurrencyActivityViewModel.getInstance().getCurrencyList().getValue() != null) {

                System.out.println(Looper.getMainLooper().isCurrentThread());
                listGroup = CurrencyActivityViewModel.getInstance().getCurrencyList().getValue();
                listChild = CurrencyActivityViewModel.getInstance().getDescriptionData().getValue();

                progressBar.setVisibility(View.GONE);
                loadingText.setVisibility(View.GONE);
                searchView.setSubmitButtonEnabled(true);

                adapter = new ExpandableAdapter(listGroup, listChild, requireActivity().getApplicationContext(), UserDataViewModel.getInstance(), UserDataViewModel.getInstance().getUserState().getValue());
                setSearchView();


            }



        expandableListView.setAdapter(adapter);
        expandableListView.setGroupIndicator(null);


        return view;

    }

}