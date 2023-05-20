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

        //int[] colors = {Color.parseColor("#1aaa6f"),Color.parseColor("#44ccb6"),Color.WHITE,Color.WHITE};
        // Устанавливаем градиентный фон для корневого представления
        View view = findViewById(R.id.ConstraintLayoutMainActivity);
        view.setBackground(AnimatedBackground.createGradient());

        ArrayList<BankCard> cards = new ArrayList<>();
        DataBase dataBase=new DataBase();
        cards=dataBase.getBankCards(login);
        CurrencyRate currencyRate=new CurrencyRate();
        currencyRate.addCurrencyRate("USD",2);
        currencyRate.addCurrencyRate("EUR",2);
//        BankCard card1 = new BankCard("1234 5678 9012 3456", "MIR","Dmitro Eblan","1.12.2022","000","0000","1000");
//        BankCard card2 = new BankCard("1234 5678 9012 3456", "MIR","Dmitro Eblan","1.12.2022","000","0000","1000");
//        cards.add(card1);
//        cards.add(card2);



//        ArrayList<String> pages = new ArrayList<>();
//        pages.add("Кошелек");
//        pages.add("Финансы");
//        pages.add("Camel");
//        pages.add("Sheep");
//        pages.add("Goat");
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