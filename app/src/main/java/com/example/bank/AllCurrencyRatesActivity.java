package com.example.bank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;

public class AllCurrencyRatesActivity extends AppCompatActivity {

    private AllCurrencyRatesRecyclerViewAdapter adapter;
    SwipeRefreshLayout mSwipeRefreshLayout;
    CurrencyRate currencyRate=new CurrencyRate();
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_currency_rates);

        mSwipeRefreshLayout = findViewById(R.id.swipeToRefresh);

        View view = findViewById(R.id.ConstraintLayoutCurrencyRate);
        view.setBackground(AnimatedBackground.createGradient());


        // Устанавливаем градиентный фон для корневого представления
//        View view = findViewById(R.id.ConstraintLayoutMainActivity);
//        view.setBackground(AnimatedBackground.createGradient());

        //ArrayList<BankCard> cards = new ArrayList<>();
        DataBase dataBase=new DataBase();

        currencyRate.addCurrencyRate("ALL",2);

        LinearLayoutManager layoutManager = new LinearLayoutManager(AllCurrencyRatesActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView = findViewById(R.id.allCurrencyRecyclerView);

        recyclerView.setLayoutManager(layoutManager);
        adapter = new AllCurrencyRatesRecyclerViewAdapter(this,currencyRate);

        recyclerView.setAdapter(adapter);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                update();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }
    public void update (){
        DataBase dataBase=new DataBase();
        currencyRate.clear();
        currencyRate.addCurrencyRate("ALL",2);
        AllCurrencyRatesRecyclerViewAdapter adapter = new AllCurrencyRatesRecyclerViewAdapter(this,currencyRate);
        recyclerView.setAdapter(adapter);
    }

}