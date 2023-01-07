package com.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.currencyconverter.R;
import com.model.OpenPositionModel;
import com.model.TransactionModel;
import com.model.UserModel;
import com.vm.UserDataViewModel;

import java.util.ArrayList;

public class OpenPositionAdapter extends RecyclerView.Adapter<OpenPositionAdapter.ViewHolder> {

    private ArrayList<OpenPositionModel> list;
    private Context context;


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView short_name, profit, change, full_name;
        Button sell;

        ViewHolder(View view) {
            super(view);
            change = (TextView) view.findViewById(R.id.open_position_change);
            short_name = (TextView) view.findViewById(R.id.open_position_name_short);
            profit = (TextView) view.findViewById(R.id.open_position_profit);
            full_name = (TextView) view.findViewById(R.id.open_position_name_full);
            sell = (Button) view.findViewById(R.id.open_position_sell);
        }
    }

    public OpenPositionAdapter(ArrayList<OpenPositionModel> list, Context context) {
        this.context = context;
        this.list = list;

    }

    @NonNull
    @Override
    public OpenPositionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.open_position_card, parent, false);

        return new ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull OpenPositionAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        OpenPositionModel position_in_list = list.get(position);


        holder.sell.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                double balance_before_change = UserDataViewModel.getInstance().getUserState().getValue().getBalance();
                double balance_after_change = balance_before_change + (Double.parseDouble(list.get(position).getProfit()));
                UserModel user = UserDataViewModel.getInstance().getUserState().getValue();

                UserDataViewModel.getInstance().updateUserState(context, new UserModel(user.getName(), balance_after_change, user.getProfit(), user.getID()));
                UserDataViewModel.getInstance().closeOpenPosition(context, list.get(position));
                TransactionModel transaction = new TransactionModel(position_in_list.getShortName(),String.valueOf(Double.parseDouble(list.get(position).getProfit())),"SELL");
                UserDataViewModel.getInstance().insertLastTransactionData(context, transaction);
                list.remove(position);
                notifyDataSetChanged();

            }
        });
        holder.short_name.setText(position_in_list.getShortName());
        holder.change.setText("%" + position_in_list.getChange());
        holder.profit.setText("$" + position_in_list.getProfit());
        holder.full_name.setText(position_in_list.getFullName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
