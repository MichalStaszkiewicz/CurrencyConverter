package com.example.currencyconverter;

import static org.chromium.base.ContextUtils.getApplicationContext;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;

import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.appcompat.widget.SearchView;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.currencyconverter.databinding.ActivityMainBinding;
import com.model.CurrencyModel;
import com.model.ExpandableContent;
import com.model.OpenPositionModel;
import com.model.UserModel;
import com.vm.CurrencyActivityViewModel;
import com.vm.UserDataViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {


    private ActivityMainBinding binding;
    private ArrayList<CurrencyModel> listGroup = new ArrayList<CurrencyModel>();
    private HashMap<String, ExpandableContent> listChild = new HashMap<String, ExpandableContent>();
    private Toolbar tool_bar;


    private CurrencyActivityViewModel mMainActivityViewModel;


    @Override


    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());


        tool_bar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(tool_bar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);


        SearchView searchView = findViewById(R.id.app_bar_search);

        searchView.setVisibility(View.INVISIBLE);


        switchScreen(new Home(getApplicationContext()));

        UserDataViewModel.getInstance().openPositions(getApplicationContext()).observe(this, new Observer<List<OpenPositionModel>>() {
            @Override
            public void onChanged(List<OpenPositionModel> openPositionsList) {
                double profit = 0;
                for(int i = 0;i<openPositionsList.size();i++) {
                    profit+=Double.parseDouble(openPositionsList.get(i).getProfit());

                }
                UserModel profileBeforeChange = UserDataViewModel.getInstance().getUserState().getValue();
                if(profileBeforeChange!=null){
                    UserModel updateProfit = new UserModel(profileBeforeChange.getName(),profileBeforeChange.getBalance(),profit,profileBeforeChange.getID());
                    UserDataViewModel.getInstance().updateUserState(getApplicationContext(),updateProfit );
                }


            }
        });

        CurrencyActivityViewModel.getInstance().getCurrency().observe(MainActivity.this, currencyModel -> {


            listGroup = currencyModel;


            CurrencyActivityViewModel.getInstance().getDescription(listGroup).observe(MainActivity.this, descriptionList -> {
                listChild = descriptionList;


                int selected_bottom_navigation_item = binding.bottomNavigationView.getSelectedItemId();
                if (selected_bottom_navigation_item == R.id.currency_card_short_name) {

                    switchScreen(new Currency(searchView,getApplicationContext()));
                }

            });


        });


        bottomNavBar(searchView);


    }


    private void bottomNavBar(SearchView searchView) {
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {


            switch (item.getItemId()) {
                case R.id.home:
                    searchView.setVisibility(View.INVISIBLE);
                    switchScreen(new Home(getApplicationContext()));

                    break;
                case R.id.currency_card_short_name:
                    searchView.setVisibility(View.VISIBLE);
                    switchScreen(new Currency(searchView, getApplicationContext()));
                    break;
                case R.id.profile:
                    searchView.setVisibility(View.INVISIBLE);
                    switchScreen(new OpenPositions(getApplicationContext()));
                    break;


            }
            return true;
        });
    }

    private void switchScreen(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);

        transaction.commit();


    }

    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }


}