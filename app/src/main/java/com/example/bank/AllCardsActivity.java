package com.example.bank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class AllCardsActivity extends AppCompatActivity {
    private AllCardsRecyclerViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_cards);
        getSupportActionBar().hide();
        ArrayList<BankCard> bankCards = new ArrayList<>();
        DataBase dataBase=new DataBase();
        bankCards=dataBase.getBankCards("valerik228");

        Log.i("fragment",bankCards.toString());



        LinearLayoutManager layoutManager = new LinearLayoutManager(AllCardsActivity.this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.RecyclerView2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AllCardsRecyclerViewAdapter(this, bankCards);
        //adapter = new MyRecyclerViewAdapter(this, viewColors, pages);
        //adapter.setClickListener(this);

        recyclerView.setAdapter(adapter);


    }
}