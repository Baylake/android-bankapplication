package com.example.bank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
/**
 * \brief класс реализует активити, которая показывает все курсы валют
 *
 */
public class AllCurrencyRatesActivity extends AppCompatActivity {
    ///Адаптер для этой активити
    private AllCurrencyRatesRecyclerViewAdapter adapter;
    ///Обновление при свайпе
    private SwipeRefreshLayout mSwipeRefreshLayout;
    ///Курсы валют
    private CurrencyRate currencyRate=new CurrencyRate();
    private RecyclerView recyclerView;
    /**
     * Задается обновление при свайпе
     *
     * Инициализируется градиентный фон
     *
     * Делается запрос на все курсы валют за 2 последних дня
     *
     * В RecyclerView передается адаптер
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_currency_rates);
        getSupportActionBar().hide();
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
    /**
     * Функция которая обновляет курсы при свайпе
     *
     */
    public void update (){
        DataBase dataBase=new DataBase();
        currencyRate.clear();
        currencyRate.addCurrencyRate("ALL",2);
        AllCurrencyRatesRecyclerViewAdapter adapter = new AllCurrencyRatesRecyclerViewAdapter(this,currencyRate);
        recyclerView.setAdapter(adapter);
    }

}