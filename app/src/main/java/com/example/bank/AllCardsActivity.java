package com.example.bank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class AllCardsActivity extends AppCompatActivity {
    private AllCardsRecyclerViewAdapter adapter;
    private Button buttonCashIn;
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

        buttonCashIn=findViewById(R.id.button12);
        buttonCashIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCashInActivity(view);
            }
        });
    }

    public void startCashInActivity(View v) {
        Intent intent = new Intent(this, CashInActivity.class);
        startActivity(intent);
    }
}