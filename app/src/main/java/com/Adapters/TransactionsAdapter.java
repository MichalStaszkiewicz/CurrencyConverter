package com.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.currencyconverter.R;
import com.model.TransactionModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.ViewHolder> {
    private List<TransactionModel> transactionList;

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, value, state;

        ViewHolder(View view) {
            super(view);
            state = (TextView) view.findViewById(R.id.state);
            name = (TextView) view.findViewById(R.id.name);
            value = (TextView) view.findViewById(R.id.value);
        }
    }

    public TransactionsAdapter(ArrayList<TransactionModel> transactionList) {
        this.transactionList = transactionList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.transaction_card, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        TransactionModel transaction = transactionList.get(position);

        holder.name.setText(transaction.getName());
        holder.state.setText(transaction.getState());

        if(transaction.getValue().charAt(0) =='-' || Objects.equals(transaction.getState(), "BUY")){
               holder.value.setText("$" +"-"+transaction.getValue());
        }else{
            holder.value.setText("$"+transaction.getValue());
        }

    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }
}