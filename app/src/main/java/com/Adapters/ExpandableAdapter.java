package com.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.company.app.utils.XAxisFormatter;
import com.company.app.utils.YAxisFormatter;
import com.example.currencyconverter.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.model.CurrencyModel;
import com.model.ExpandableContent;
import com.model.OpenPositionModel;
import com.model.TransactionModel;
import com.model.UserModel;
import com.vm.UserDataViewModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

public class ExpandableAdapter extends BaseExpandableListAdapter implements Filterable {
    private ArrayList<CurrencyModel> listGroup;
    private ArrayList<CurrencyModel> copy_of_listGroup;
    private TextInputEditText buyCurrency;
    private TextInputLayout currencyBuyTextInputLayout;
    private BigDecimal after_transaction;
    private Context context;
    private UserDataViewModel userData;
    private UserModel UserState;
    private LinearLayout purchase_information_box;
    private String currencyCount;
    private double available_money;


    private HashMap<String, ExpandableContent> listChild;

    @Override
    public Filter getFilter() {

        return currenciesFilter;
    }


    private void hideKeyboard(TextView v) {
        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        v.clearFocus();

    }


    private void onTypeListener(TextView conversion_result, int groupPosition, TextView bill) {

        buyCurrency.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                if (s.length() != 0) {
                    currencyCount = s.toString();
                    purchase_information_box.setVisibility(View.VISIBLE);
                    String currency_name = listGroup.get(groupPosition).getShortName();
                    BigDecimal course = BigDecimal.valueOf(Double.parseDouble(listGroup.get(groupPosition).getConversionPrice()));

                    BigDecimal result = BigDecimal.valueOf(Double.parseDouble(s.toString()));

                    result = result.multiply(course);
                    String result_str_holder = result.toString();
                    String result_str = "";
                    if (result_str_holder.charAt(0) != '0') {
                        for (int i = 0; i < result_str_holder.length(); i++) {
                            if (result_str_holder.charAt(i) == '.') {
                                result_str += result_str_holder.charAt(i);
                                result_str += result_str_holder.charAt(i + 1);
                                break;
                            } else {
                                result_str += result_str_holder.charAt(i);
                            }


                        }
                    } else {
                        for (int i = 0; i < result_str_holder.length(); i++) {
                            if (result_str_holder.charAt(i) != '.' && result_str_holder.charAt(i) != '0') {
                                result_str += result_str_holder.charAt(i);
                                break;
                            }
                            result_str += result_str_holder.charAt(i);

                        }
                    }


                    BigDecimal user_money = new BigDecimal(available_money);
                    BigDecimal user_money_after_transaction = user_money.subtract(result);

                    String user_money_after_transaction_holder = user_money_after_transaction.toString();
                    String user_money_after_transaction_result = "";

                    for (int i = 0; i < user_money_after_transaction_holder.length(); i++) {

                        if (user_money_after_transaction_holder.charAt(i) == '.') {

                            user_money_after_transaction_result += user_money_after_transaction_holder.charAt(i);
                            user_money_after_transaction_result += user_money_after_transaction_holder.charAt(i + 1);
                            break;
                        } else {
                            user_money_after_transaction_result += user_money_after_transaction_holder.charAt(i);
                        }


                    }

                    bill.setTextKeepState("$" + Math.round((available_money * 100.0) / 100.0) + " - " + "$ " + result_str + " = " + "$ " + user_money_after_transaction_result);
                    conversion_result.setTextKeepState(s + " " + currency_name + " =" + " $ " + result_str);
                    after_transaction = user_money_after_transaction;

                }

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

    }

    private void actionListener() {
        buyCurrency.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == 0) {
                    hideKeyboard(v);


                }
                return true;

            }


        });

    }

    private final Filter currenciesFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<CurrencyModel> filteredList = new ArrayList<CurrencyModel>();

            if (constraint == null || constraint.length() == 0) {

                filteredList.addAll(copy_of_listGroup);

            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (CurrencyModel item : copy_of_listGroup) {

                    if (item.getShortName().toLowerCase().contains(filterPattern)) {

                        filteredList.add(item);
                    }
                }

            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listGroup = (ArrayList<CurrencyModel>) results.values;


            notifyDataSetChanged();

        }
    };

    public ExpandableAdapter(ArrayList<CurrencyModel> listGroup, HashMap<String, ExpandableContent> listChild, Context context, UserDataViewModel userData, UserModel UserState) {
        this.listChild = listChild;
        this.listGroup = listGroup;
        this.userData = userData;
        this.copy_of_listGroup = listGroup;
        this.context = context;
        this.UserState = UserState;
        this.available_money = UserState.getBalance();


    }


    @Override
    public int getGroupCount() {
        return listGroup.size();
    }


    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }


    @Override
    public CurrencyModel getGroup(int groupPosition) {
        return listGroup.get(groupPosition);
    }


    @Override
    public ExpandableContent getChild(int groupPosition, int childPosition) {
        return listChild.get(listGroup.get(groupPosition).getShortName());
    }


    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }


    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }


    @Override

    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.currency_card_item, parent, false);
        TextView full_name_view = convertView.findViewById(R.id.currency_card_full_name);
        TextView short_name_view = convertView.findViewById(R.id.currency_card_short_name);
        TextView price_view = convertView.findViewById(R.id.currency_card_price);

        ShapeableImageView image = convertView.findViewById(R.id.currency_card_flag);
        Drawable drawable = image.getResources().getDrawable(R.drawable.image);
        String full_name = getGroup(groupPosition).getFullName();
        String short_name = getGroup(groupPosition).getShortName();
        String price = getGroup(groupPosition).getPrice();


        image.setImageDrawable(drawable);
        full_name_view.setText(full_name);
        short_name_view.setText(short_name);
        price_view.setText(price + "$");


        return convertView;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)


    @Override

    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.on_expand, parent, false);
        buyCurrency = convertView.findViewById(R.id.buy_currency_text_field);
        TextView conversion_result = convertView.findViewById(R.id.convert_quantity);
        TextView bill = convertView.findViewById(R.id.purchase_information);
        LineChart chart = convertView.findViewById(R.id.graph);
        Button buyButton = (Button) convertView.findViewById(R.id.buy_currency_button);
        TextView availableFunds = convertView.findViewById(R.id.available_funds);
        purchase_information_box = convertView.findViewById(R.id.purchase_information_box);
        purchase_information_box.setVisibility(View.INVISIBLE);

        availableFunds.setText("Available Funds: " + "$" + Math.round(available_money * 100.0) / 100.0);
        currencyBuyTextInputLayout = convertView.findViewById(R.id.quantityOfCurrency);

        currencyBuyTextInputLayout.setError(getChild(groupPosition, childPosition).getErrorState());


        ArrayList<Entry> list = getChild(groupPosition, childPosition).getPoints();


        LineDataSet lineDataSet = new LineDataSet(list, "exchange rate");

        chart.getDescription().setEnabled(false);
        chart.getXAxis().setValueFormatter(new XAxisFormatter(getChild(groupPosition, childPosition).getDate()));
        chart.getXAxis().setTextSize(13);
        chart.getXAxis().setLabelCount(2);
        chart.setBorderWidth(2);
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);


        chart.getAxisRight().setEnabled(false);

        chart.getXAxis().setValueFormatter(new XAxisFormatter(getChild(groupPosition, childPosition).getDate()));

        chart.getAxisLeft().setValueFormatter(new YAxisFormatter());

        chart.getAxisLeft().setTextSize(15);

        ArrayList<LineDataSet> ilineDataSet = new ArrayList<LineDataSet>();

        ilineDataSet.add(lineDataSet);
        lineDataSet.setLineWidth(4f);
        lineDataSet.setDrawFilled(true);


      //  lineDataSet.setFillDrawable(ContextCompat.getDrawable(chart.getContext(), R.drawable.gradient));
        LineData lineData = new LineData(lineDataSet);
        lineData.setValueFormatter(new YAxisFormatter());
        lineData.setValueTextSize(11);


        chart.setData(lineData);
        chart.invalidate();
        chart.getLegend().setTextSize(15);
        onTypeListener(conversion_result, groupPosition, bill);
        actionListener();

        buyButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                buyCurrency.requestFocus();

                if (after_transaction != null) {
                    if (after_transaction.toString().charAt(0) == '-') {


                        getChild(groupPosition, childPosition).setErrorState("You have no enough money");


                    } else {


                        getChild(groupPosition, childPosition).setErrorState(null);

                        CurrencyModel currencyModel = getGroup(groupPosition);
                        double openPositionValue = Double.parseDouble(currencyModel.getConversionPrice()) * Integer.parseInt(currencyCount);
                        TransactionModel transactionModel = new TransactionModel(currencyModel.getShortName(), String.valueOf(Math.round(openPositionValue * 1000.0) / 1000.0), "BUY");
                        OpenPositionModel openPosition = new OpenPositionModel(currencyModel.getShortName(), "0", String.valueOf(Math.round(openPositionValue * 1000.0) / 1000.0), currencyModel.getFullName(), currencyModel.getPrice(), Double.parseDouble(currencyCount));
                        userData.insertLastTransactionData(context, transactionModel);
                        userData.insertOpenPosition(context, openPosition);

                        available_money = Double.parseDouble(String.valueOf(after_transaction));
                        userData.updateUserState(context, new UserModel(UserState.getName(), available_money, UserState.getProfit(), 1));


                        Toast.makeText(context, "Purchase Successfully Finished", Toast.LENGTH_SHORT).show();


                    }
                } else {


                    getChild(groupPosition, childPosition).setErrorState("This field cannot be null");

                }
                currencyBuyTextInputLayout.setError(getChild(groupPosition, childPosition).getErrorState());
                notifyDataSetChanged();


            }
        });


        return convertView;
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
