package com.example.currencyconverter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.Adapters.OpenPositionAdapter;
import com.example.currencyconverter.databinding.FragmentHomeBinding;
import com.model.OpenPositionModel;
import com.model.TransactionModel;
import com.vm.UserDataViewModel;

import java.util.ArrayList;
import java.util.List;


public class OpenPositions extends Fragment {
    private ArrayList<OpenPositionModel> openPositions = new ArrayList<>();
    private UserDataViewModel viewModel;
    private Context context;
    private OpenPositionAdapter adapter;

    public OpenPositions(Context context) {
        this.context = context;

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(UserDataViewModel.class);
        openPositions.clear();
        UserDataViewModel.getInstance().openPositions(context).observe(this, new Observer<List<OpenPositionModel>>() {
            @Override
            public void onChanged(List<OpenPositionModel> openPositionsList) {
                if (openPositionsList != null) {
                    if (openPositions.size() > 0) {
                        openPositions.clear();
                    }

                    openPositions.addAll(openPositionsList);


                    adapter.notifyDataSetChanged();
                }
            }
        });

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_open_positions, container, false);
        com.example.currencyconverter.databinding.FragmentHomeBinding binding = FragmentHomeBinding.inflate(getLayoutInflater());
        RecyclerView list = view.findViewById(R.id.open_position_list);


        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        list.setLayoutManager(manager);
        list.setHasFixedSize(true);

        Drawable divider = ContextCompat.getDrawable(list.getContext(), R.drawable.divider);
        DividerItemDecoration decoration = new DividerItemDecoration(list.getContext(), DividerItemDecoration.HORIZONTAL);
        decoration.setDrawable(divider);

        list.addItemDecoration(decoration);

        adapter = new OpenPositionAdapter(openPositions,context);

        list.setAdapter(adapter);


        return view;
    }
}