package com.example.bank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
//TODO Сделать фрагмент с полной информацией о картах и возможных действиях(grid)
public class MainActivity extends AppCompatActivity {
    //public class MainActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {
    private String login="valerik228";
    private MainRecyclerViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Устанавливаем градиентный фон для корневого представления
        View view = findViewById(R.id.ConstraintLayoutMainActivity);
        view.setBackground(AnimatedBackground.createGradient());

        ArrayList<BankCard> cards = new ArrayList<>();
        DataBase dataBase=new DataBase();
        cards=dataBase.getBankCards(login);
        CurrencyRate currencyRate=new CurrencyRate();
       // int count=0;
       // while(currencyRate.currencies.size()<4 && count <10){
        //    currencyRate.clear();
            currencyRate.addCurrencyRate("USD",2);
            currencyRate.addCurrencyRate("EUR",2);
        //    count++;
        //}

        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.RecyclerView1);

        recyclerView.setLayoutManager(layoutManager);
        adapter = new MainRecyclerViewAdapter(this, cards,currencyRate);
        //adapter = new MyRecyclerViewAdapter(this, viewColors, pages);
        //adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }
//    @Override
//    public void onItemClick(View view, int position) {
//        Toast.makeText(this, "Ну типа соси ",Toast.LENGTH_SHORT ).show();
//        //Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on item position " + position, Toast.LENGTH_SHORT).show();
//    }
}